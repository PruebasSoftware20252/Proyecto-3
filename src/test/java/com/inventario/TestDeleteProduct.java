package com.inventario;

import com.inventario.model.Inventario;
import com.inventario.model.Storage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

public class TestDeleteProduct {
    @TempDir
    Path temp;

    @Test
    void borrarYGuardar_persiste() {
        Path file = temp.resolve("inventory.csv");

        // 1) crear y guardar dos productos
        Inventario inv1 = Storage.load(file);
        inv1.addProducto("0001", "Lapiz", 1.2, 100);
        inv1.addProducto("0002", "Cuaderno", 3.5, 20);
        Storage.saveAll(file, inv1);

        // 2) cargar, borrar uno, y guardar
        Inventario inv2 = Storage.load(file);
        inv2.deleteProducto("0001");
        Storage.saveAll(file, inv2);

        // 3) volver a cargar y verificar
        Inventario inv3 = Storage.load(file);
        assertNull(inv3.findBySku("0001"));
        assertNotNull(inv3.findBySku("0002"));
        assertEquals(1, inv3.contar());
    }
}
