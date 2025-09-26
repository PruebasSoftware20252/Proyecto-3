package com.inventario.model;

import java.util.HashMap;
import java.util.Map;

public class Inventario {
    private final Map<String, Producto> data = new HashMap<>();

    public Producto addProducto(String sku, String nombre, double precio, int cantidad) {
        if (sku == null || sku.trim().isEmpty()) {
            throw new IllegalArgumentException("SKU es obligatorio");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre es obligatorio");
        }
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
        if (data.containsKey(sku)) {
            throw new IllegalArgumentException("Ya existe un producto con SKU: " + sku);
        }

        Producto p = new Producto(sku.trim(), nombre.trim(), precio, cantidad);
        data.put(p.getSku(), p);
        return p;
    }

    public Producto findBySku(String sku) { return data.get(sku); }

    public int contar() { return data.size(); }
}
