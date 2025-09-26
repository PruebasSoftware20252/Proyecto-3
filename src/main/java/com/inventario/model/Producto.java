package com.inventario.model;


public class Producto {
    private final String sku;
    private final String nombre;
    private final double precio;
    private final int cantidad;

    public Producto(String sku, String nombre, double precio, int cantidad) {
        this.sku = sku;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getSku() { return sku; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getCantidad() { return cantidad; }

    @Override
    public String toString() {
        return "Producto{sku='" + sku + "', nombre='" + nombre + "', precio=" + precio + ", cantidad=" + cantidad + "}";
    }
}
