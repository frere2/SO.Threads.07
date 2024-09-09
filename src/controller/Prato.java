package controller;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Prato extends Thread {
    private final int id;
    private final String nome;
    private final double tempoCozimento;
    private final Semaphore semaforoEntrega;

    public Prato(int id, double tempoCozimento, String nome, Semaphore semaforoEntrega) {
        this.id = id;
        this.tempoCozimento = tempoCozimento;
        this.nome = nome;
        this.semaforoEntrega = semaforoEntrega;
    }

    @Override
    public void run() {
        try {
            System.out.println("Iniciando o cozimento do prato " + nome + " (ID: " + id + ")");
            int totalPassos = (int) (tempoCozimento / 0.1);
            for (int i = 1; i <= totalPassos; i++) {
                TimeUnit.MILLISECONDS.sleep(100);
                double percentual = (i * 100.0) / totalPassos;
                System.out.printf("Prato %s (ID: %d) cozido %.1f%%\n", nome, id, percentual);
            }
            System.out.println("Prato " + nome + " (ID: " + id + ") está pronto!");

            semaforoEntrega.acquire();
            System.out.println("Entregando o prato " + nome + " (ID: " + id + ")");
            TimeUnit.MILLISECONDS.sleep(500);
            System.out.println("Prato " + nome + " (ID: " + id + ") foi entregue.");
            semaforoEntrega.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
