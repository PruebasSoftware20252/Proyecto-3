package com.inventario.model;

import java.io.*;
import java.nio.file.*;
import java.util.List;

public class Storage {
    public static Inventario load(Path file) {
        Inventario inv = new Inventario();
        if (!Files.exists(file)) return inv;

        try {
            List<String> lines = Files.readAllLines(file);
            for (String line : lines) {
                String trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("#")) continue;
                String[] parts = trimmed.split(";");
                if (parts.length != 4) continue;

                String sku = parts[0];
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);
                int qty = Integer.parseInt(parts[3]);

                try {
                    inv.addProducto(sku, name, price, qty);
                } catch (IllegalArgumentException ignore) {
                    // líneas duplicadas o inválidas: las omitimos
                }
            }
        } catch (IOException e) {
            System.err.println("No se pudo leer el archivo: " + e.getMessage());
        }
        return inv;
    }

    /**
     * Agrega (append) un producto al archivo. Crea carpeta/archivo si no existen.
     */
    public static void append(Path file, Producto p) {
        try {
            Path parent = file.getParent();
            if (parent != null) Files.createDirectories(parent);

            String line = String.format("%s;%s;%s;%s",
                    p.getSku(), p.getNombre(), Double.toString(p.getPrecio()), Integer.toString(p.getCantidad()));

            try (BufferedWriter bw = Files.newBufferedWriter(
                    file, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("No se pudo escribir el archivo: " + e.getMessage());
        }
    }
}
