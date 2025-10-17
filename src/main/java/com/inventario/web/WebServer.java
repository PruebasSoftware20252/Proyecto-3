package com.inventario.web;

import com.inventario.model.Inventario;
import com.inventario.model.Producto;
import com.inventario.model.Storage;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Web UI mínima para Inventario:
 *  - GET /         : lista los productos y muestra formularios para agregar/actualizar
 *  - POST /add     : agrega producto (sku, nombre, precio, cantidad)
 *  - POST /update  : actualiza cantidad (sku, cantidad)
 *
 * Persiste en data/inventory.csv usando Storage.
 */
public class WebServer {
    private static final Path STORE_FILE = Paths.get("data", "inventory.csv");
    private static Inventario inv;

    public static void main(String[] args) throws Exception {
        // Cargar inventario
        inv = Storage.load(STORE_FILE);

        // Asegurar carpeta data/
        Files.createDirectories(STORE_FILE.getParent());

        int port = 8080; // Azure App Service Java suele exponer en 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", WebServer::handleIndex);
        server.createContext("/add", WebServer::handleAdd);
        server.createContext("/update", WebServer::handleUpdate);

        server.setExecutor(null);
        System.out.println("Servidor iniciado en http://localhost:" + port);
        server.start();
    }

    private static void handleIndex(HttpExchange ex) throws IOException {
        if (!"GET".equalsIgnoreCase(ex.getRequestMethod())) {
            send(ex, 405, "Método no permitido");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<!doctype html><html><head><meta charset='utf-8'>")
          .append("<title>Inventario - Proyecto 3</title>")
          .append("<style>body{font-family:system-ui;margin:2rem;}table{border-collapse:collapse;width:100%;}th,td{border:1px solid #ddd;padding:.5rem;}th{background:#f3f3f3;text-align:left}form{margin:.5rem 0;padding:.5rem;border:1px solid #eee;background:#fafafa}</style>")
          .append("</head><body>")
          .append("<h1>Inventario</h1>");

        // Tabla
        sb.append("<table><thead><tr><th>SKU</th><th>Nombre</th><th>Precio</th><th>Cantidad</th></tr></thead><tbody>");
        for (Producto p : inv.all()) {
            sb.append("<tr>")
              .append("<td>").append(escape(p.getSku())).append("</td>")
              .append("<td>").append(escape(p.getNombre())).append("</td>")
              .append("<td>").append(p.getPrecio()).append("</td>")
              .append("<td>").append(p.getCantidad()).append("</td>")
              .append("</tr>");
        }
        sb.append("</tbody></table>");

        // Form agregar
        sb.append("<h2>Agregar producto</h2>")
          .append("<form method='post' action='/add'>")
          .append("<label>SKU <input name='sku' required></label> ")
          .append("<label>Nombre <input name='nombre' required></label> ")
          .append("<label>Precio <input type='number' step='0.01' min='0' name='precio' required></label> ")
          .append("<label>Cantidad <input type='number' min='0' name='cantidad' required></label> ")
          .append("<button>Agregar</button>")
          .append("</form>");

        // Form actualizar
        sb.append("<h2>Actualizar cantidad</h2>")
          .append("<form method='post' action='/update'>")
          .append("<label>SKU <input name='sku' required></label> ")
          .append("<label>Nueva cantidad <input type='number' min='0' name='cantidad' required></label> ")
          .append("<button>Actualizar</button>")
          .append("</form>");

        sb.append("</body></html>");

        send(ex, 200, sb.toString());
    }

    private static void handleAdd(HttpExchange ex) throws IOException {
        if (!"POST".equalsIgnoreCase(ex.getRequestMethod())) {
            send(ex, 405, "Método no permitido");
            return;
        }
        Map<String, String> form = parseForm(ex);
        try {
            String sku = required(form, "sku");
            String nombre = required(form, "nombre");
            double precio = Double.parseDouble(required(form, "precio"));
            int cantidad = Integer.parseInt(required(form, "cantidad"));

            inv.addProducto(sku, nombre, precio, cantidad);
            Storage.saveAll(STORE_FILE, inv);
            redirect(ex, "/");
        } catch (Exception e) {
            send(ex, 400, "Error: " + e.getMessage());
        }
    }

    private static void handleUpdate(HttpExchange ex) throws IOException {
        if (!"POST".equalsIgnoreCase(ex.getRequestMethod())) {
            send(ex, 405, "Método no permitido");
            return;
        }
        Map<String, String> form = parseForm(ex);
        try {
            String sku = required(form, "sku");
            int cantidad = Integer.parseInt(required(form, "cantidad"));

            // El proyecto ya trae esta operación en Inventario (según los tests).
            // Si el nombre difiere, ajusta aquí.
            inv.updateCantidad(sku, cantidad);

            Storage.saveAll(STORE_FILE, inv);
            redirect(ex, "/");
        } catch (Exception e) {
            send(ex, 400, "Error: " + e.getMessage());
        }
    }

    // Utils
    private static void send(HttpExchange ex, int code, String html) throws IOException {
        byte[] bytes = html.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().add("Content-Type", "text/html; charset=utf-8");
        ex.sendResponseHeaders(code, bytes.length);
        try (OutputStream os = ex.getResponseBody()) { os.write(bytes); }
    }

    private static void redirect(HttpExchange ex, String location) throws IOException {
        ex.getResponseHeaders().add("Location", location);
        ex.sendResponseHeaders(302, -1);
        ex.close();
    }

    private static Map<String, String> parseForm(HttpExchange ex) throws IOException {
        String body = new String(ex.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Map<String, String> map = new HashMap<>();
        for (String pair : body.split("&")) {
            if (pair.isBlank()) continue;
            String[] kv = pair.split("=", 2);
            String k = URLDecoder.decode(kv[0], StandardCharsets.UTF_8);
            String v = kv.length > 1 ? URLDecoder.decode(kv[1], StandardCharsets.UTF_8) : "";
            map.put(k, v);
        }
        return map;
    }

    private static String required(Map<String, String> map, String key) {
        String v = map.get(key);
        if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException("Falta " + key);
        return v.trim();
    }

    private static String escape(String s) {
        return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;");
    }
}
