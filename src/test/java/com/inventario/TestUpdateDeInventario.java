package com.inventario;

import com.inventario.model.Inventario;
import com.inventario.model.Producto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestUpdateDeInventario {

    @Test
    void actualizarCantidad_ok() {
        Inventario inv = new Inventario();
        inv.addProducto("0001", "Lapiz", 1.2, 100);

        Producto updated = inv.updateCantidad("0001", 250);

        assertEquals(250, updated.getCantidad());
        assertEquals("Lapiz", updated.getNombre());
        assertEquals(1.2, updated.getPrecio(), 1e-9);
        assertEquals(1, inv.contar());
        assertEquals(250, inv.findBySku("0001").getCantidad());
    }

    @Test
    void actualizarCantidad_negativa_lanza() {
        Inventario inv = new Inventario();
        inv.addProducto("0001", "Lapiz", 1.2, 100);
        assertThrows(IllegalArgumentException.class, () -> inv.updateCantidad("0001", -5));
    }

    @Test
    void actualizarCantidad_skuNoExiste_lanza() {
        Inventario inv = new Inventario();
        assertThrows(IllegalArgumentException.class, () -> inv.updateCantidad("NOPE", 10));
    }


}
