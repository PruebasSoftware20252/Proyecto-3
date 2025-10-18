package com.inventario;

import com.inventario.model.Inventario;
import com.inventario.model.Producto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestDeInventario {
    @Test
    void registrarProducto_valido_incrementaConteo() {
        Inventario inv = new Inventario();
        Producto p = inv.addProducto("0001", "Lapiz", 1.2, 100);

        assertNotNull(p);
        assertEquals(1, inv.contar());
        assertEquals("0001", inv.findBySku("0001").getSku());
    }

    @Test
    void registrarProducto_duplicado_lanzaExcepcion() {
        Inventario inv = new Inventario();
        inv.addProducto("0001", "Lapiz", 1.2, 100);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> inv.addProducto("0001", "Otro", 2.0, 10));

        assertTrue(ex.getMessage().toLowerCase().contains("sku"));
    }

    @Test
    void validaciones_basicas() {
        Inventario inv = new Inventario();

        assertThrows(IllegalArgumentException.class, () -> inv.addProducto(null, "A", 1.0, 1));
        assertThrows(IllegalArgumentException.class, () -> inv.addProducto("  ", "A", 1.0, 1));
        assertThrows(IllegalArgumentException.class, () -> inv.addProducto("X", null, 1.0, 1));
        assertThrows(IllegalArgumentException.class, () -> inv.addProducto("X", "  ", 1.0, 1));
        assertThrows(IllegalArgumentException.class, () -> inv.addProducto("X", "A", -0.01, 1));
        assertThrows(IllegalArgumentException.class, () -> inv.addProducto("X", "A", 1.0, -1));
    }
}
