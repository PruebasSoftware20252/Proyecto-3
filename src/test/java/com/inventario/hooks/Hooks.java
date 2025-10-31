package com.inventario.hooks;

import com.inventario.web.WebServer;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.HttpURLConnection;
import java.net.URL;

public class Hooks {
    public static WebDriver driver;
    private static Thread serverThread;
    private static final String BASE_URL = "http://localhost:8080/";

    @Before(order = 0)
    public void setUp() throws Exception {
        // Levantar server solo una vez
        if (serverThread == null || !serverThread.isAlive()) {
            serverThread = new Thread(() -> {
                try {
                    WebServer.main(new String[]{}); // 8080
                } catch (java.net.BindException be) {
                    System.out.println(" Server ya estaba arriba. Continua…");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            serverThread.setDaemon(true);
            serverThread.start();
            waitForServer(BASE_URL, 30_000);
        }

        ChromeOptions options = new ChromeOptions();
        if ("true".equalsIgnoreCase(System.getenv("CI"))) {
            options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        } else {
            options.addArguments("--start-maximized");
        }

        driver = new ChromeDriver(options);
        driver.get(BASE_URL);
        System.out.println("Abriendo " + BASE_URL);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Navegador cerrado");
        }
    }

    private static void waitForServer(String url, long timeoutMs) throws Exception {
        long t0 = System.currentTimeMillis();
        Exception last = null;
        while (System.currentTimeMillis() - t0 < timeoutMs) {
            try {
                HttpURLConnection c = (HttpURLConnection) new URL(url).openConnection();
                c.setConnectTimeout(2000);
                c.setReadTimeout(2000);
                c.setRequestMethod("GET");
                int code = c.getResponseCode();
                if (code >= 200 && code < 500) return;
            } catch (Exception e) { last = e; }
            Thread.sleep(300);
        }
        if (last != null) throw last;
    }
}