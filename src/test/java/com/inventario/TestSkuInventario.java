package com.inventario;

import com.inventario.model.Inventario;
import com.inventario.model.Producto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TestSkuInventario {

    @Test
    void skuSoloDigitos_esValido() {
        Inventario inv = new Inventario();
        Producto p = inv.addProducto("0001", "Lapiz", 1.0, 10);
        assertEquals("0001", p.getSku());
    }

    @Test
    void skuConLetras_rechazado() {
        Inventario inv = new Inventario();
        assertThrows(IllegalArgumentException.class,
                () -> inv.addProducto("A001", "Lapiz", 1.0, 10));
    }

    @Test
    void skuConEspacios_rechazado() {
        Inventario inv = new Inventario();
        assertThrows(IllegalArgumentException.class,
                () -> inv.addProducto("12 34", "Lapiz", 1.0, 10));
    }

    @Test
    void skuVacioONull_rechazado() {
        Inventario inv = new Inventario();
        assertThrows(IllegalArgumentException.class,
                () -> inv.addProducto("", "Lapiz", 1.0, 10));
        assertThrows(IllegalArgumentException.class,
                () -> inv.addProducto(null, "Lapiz", 1.0, 10));
    }
}
