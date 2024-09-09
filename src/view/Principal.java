package view;

import controller.Prato;

import java.util.concurrent.Semaphore;

public class Principal {
    public static void main(String[] args) {
        Semaphore semaforo = new Semaphore(1);
        Prato[] pratos = new Prato[5];
        int len = pratos.length;

        for (int i = 0; i < len; i++) {
            double tmp = (i % 2 == 0 ? tempo(0.6, 1.2) : tempo(0.5, 0.8));
            String nome = (i % 2 == 0 ? "Lasanha à Bolonhesa" : "Sopa de Cebola");
            pratos[i] = new Prato(i, tmp, nome, semaforo);
        }

        for (Prato prato : pratos) {
            prato.start();
        }

        for (Prato prato : pratos) {
            try {
                prato.join();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }

        System.out.println("Todos os pratos foram cozidos e entregues!");
    }

    private static double tempo(double min, double max) {
        return min + (Math.random() * (max - min));
    }
}
