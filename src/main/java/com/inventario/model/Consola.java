package com.inventario.model;

import java.util.Scanner;

public class Consola {private final Scanner sc = new Scanner(System.in);

    public String askString(String label) {
        while (true) {
            System.out.print(label + ": ");
            String s = sc.nextLine();
            if (s != null && !s.trim().isEmpty()) return s.trim();
            System.out.println("  Valor obligatorio. Intenta de nuevo.");
        }
    }

    public double askNonNegativeDouble(String label) {
        while (true) {
            System.out.print(label + ": ");
            String line = sc.nextLine();
            try {
                double v = Double.parseDouble(line.trim());
                if (v < 0) { System.out.println("  Debe ser >= 0."); continue; }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("  Número inválido. Intenta de nuevo.");
            }
        }
    }

    public int askNonNegativeInt(String label) {
        while (true) {
            System.out.print(label + ": ");
            String line = sc.nextLine();
            try {
                int v = Integer.parseInt(line.trim());
                if (v < 0) { System.out.println("  Debe ser >= 0."); continue; }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("  Número inválido. Intenta de nuevo.");
            }
        }
    }
}
