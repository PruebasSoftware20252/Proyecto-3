package com.inventario.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

public class MainPage {
    private final WebDriver driver;
    private final Duration SHORT = Duration.ofSeconds(5);

    // Locators
    private final By skuAddInput       = By.xpath("//form[@action='/add']//input[@name='sku']");
    private final By skuUpdatedInput   = By.xpath("//form[@action='/update']//input[@name='sku']");
    private final By skuDeleteInput    = By.xpath("//form[@action='/delete']//input[@name='sku']");

    private final By nombreAddInput    = By.xpath("//form[@action='/add']//input[@name='nombre']");
    private final By precioAddInput    = By.xpath("//form[@action='/add']//input[@name='precio']");
    private final By cantidadAddInput  = By.xpath("//form[@action='/add']//input[@name='cantidad']");
    private final By cantidadUpdatedInput = By.xpath("//form[@action='/update']//input[@name='cantidad']");

    private final By addBtn            = By.xpath("//button[text()='Agregar']");
    private final By updateBtn         = By.xpath("//button[text()='Actualizar']");
    private final By deleteBtn         = By.xpath("//button[text()='Eliminar']");

    private final By alertTxt          = By.xpath("//div");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    private WebElement waitVisible(By locator) {
        return new WebDriverWait(driver, SHORT)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private WebElement waitRefreshedVisible(By locator) {
        return new WebDriverWait(driver, SHORT)
                .until(ExpectedConditions.refreshed(
                        ExpectedConditions.visibilityOfElementLocated(locator)));
    }

    private <T> T retryStale(int attempts, Function<WebDriver, T> action) {
        RuntimeException last = null;
        for (int i = 0; i < attempts; i++) {
            try {
                return action.apply(driver);
            } catch (StaleElementReferenceException e) {
                last = e;
            }
        }
        throw last != null ? last : new RuntimeException("retryStale failed");
    }

    private void typeClear(By locator, String value) {
        WebElement el = waitVisible(locator);
        el.clear();
        el.sendKeys(value);
    }
    public void abrirHome() {
        driver.get("http://localhost:8080/");
        // Evita stale en H1 tras la carga inicial
        waitRefreshedVisible(By.tagName("h1"));
    }

    public void agregarProducto(String sku, String nombre, String precio, String cantidad) {
        // Re-localiza y espera antes de cada interacción
        typeClear(skuAddInput, sku);
        typeClear(nombreAddInput, nombre);
        typeClear(precioAddInput, precio);
        typeClear(cantidadAddInput, cantidad);
        waitVisible(addBtn).click();
        waitRefreshedVisible(alertTxt);
    }

    public void actualizarCantidad(String sku, String cantidad) {
        typeClear(skuUpdatedInput, sku);
        typeClear(cantidadUpdatedInput, cantidad);
        waitVisible(updateBtn).click();
        waitRefreshedVisible(alertTxt);
    }

    public void eliminarProducto(String sku) {
        typeClear(skuDeleteInput, sku);
        waitVisible(deleteBtn).click();
        waitRefreshedVisible(alertTxt);
    }

    public String validarPage() {
        return retryStale(3, d -> waitRefreshedVisible(By.tagName("h1")).getText());
    }

    public String obtenerMensaje() {
        return retryStale(3, d -> waitRefreshedVisible(alertTxt).getText());
    }

    public String validarFormAdicion() {
        By h2Add = By.xpath("//h2[text()='Agregar producto']");
        return retryStale(3, d -> waitRefreshedVisible(h2Add).getText());
    }

    public String validarFormEliminar() {
        By h2Del = By.xpath("//h2[text()='Eliminar producto']");
        return retryStale(3, d -> waitRefreshedVisible(h2Del).getText());
    }
}