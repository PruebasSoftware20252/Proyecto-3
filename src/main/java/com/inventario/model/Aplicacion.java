package com.inventario.model;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Aplicacion {
    private static final Path STORE_FILE = Paths.get("data", "inventory.csv");
    public static void main(String[] args) {
        // 1) Cargar inventario existente desde disco
        Inventario inv = Storage.load(STORE_FILE);

        if (args.length == 0) {
            Consola io = new Consola();
            System.out.println("=== Registrar producto ===");
            String sku = io.askString("SKU");
            String name = io.askString("Nombre");
            double price = io.askNonNegativeDouble("Precio");
            int qty = io.askNonNegativeInt("Cantidad");

            try {
                Producto p = inv.addProducto(sku, name, price, qty);
                Storage.append(STORE_FILE, p); // 2) Guardar en disco
                System.out.println("Registrado: " + p);
                System.out.println("Total en inventario: " + inv.contar());
            } catch (IllegalArgumentException e) {
                System.err.println("Error: " + e.getMessage());
                System.exit(1);
            }
            return;
        }

        String command = args[0];
        if ("register".equalsIgnoreCase(command)) {
            String sku = null, name = null;
            Double price = null; Integer qty = null;

            for (int i = 1; i < args.length; i++) {
                switch (args[i]) {
                    case "--sku":
                        sku = nextArg(args, ++i, "--sku");
                        break;
                    case "--name":
                        name = nextArg(args, ++i, "--name");
                        break;
                    case "--price":
                        price = Double.valueOf(nextArg(args, ++i, "--price"));
                        break;
                    case "--qty":
                        qty   = Integer.valueOf(nextArg(args, ++i, "--qty"));
                        break;
                    default:
                    return;
                }
            }
            try {
                Producto p = inv.addProducto(sku, name, price != null ? price : -1, qty != null ? qty : -1);
                Storage.append(STORE_FILE, p);
                System.out.println("Registrado: " + p);
                System.out.println("Total en inventario: " + inv.contar());
            } catch (IllegalArgumentException e) {
                System.err.println("Error: " + e.getMessage());
                System.exit(1);
            }
            return;
        }

        // actualizar cantidad
        if ("update-qty".equalsIgnoreCase(command)) {
            String sku = null; Integer qty = null;
            for (int i = 1; i < args.length; i++) {
                switch (args[i]) {
                    case "--sku":
                        sku = nextArg(args, ++i, "--sku");
                        break;
                    case "--qty":
                        qty  = Integer.valueOf(nextArg(args, ++i, "--qty"));
                        break;
                    default: System.out.println("Flag desconocido: " + args[i]);
                    return;
                }
            }
            try {
                Producto updated = inv.updateCantidad(sku, qty != null ? qty : -1);
                Storage.saveAll(STORE_FILE, inv); // sobreescribe CSV con el inventario actualizado
                System.out.println("Cantidad actualizada: " + updated);
            } catch (IllegalArgumentException e) {
                System.err.println("Error: " + e.getMessage());
                System.exit(1);
            }
            return;
        }
    }

    private static String nextArg(String[] args, int index, String flag) {
        if (index >= args.length) throw new IllegalArgumentException("Falta valor para " + flag);
        return args[index];
    }


}
