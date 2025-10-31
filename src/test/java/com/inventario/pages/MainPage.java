package com.inventario.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage {
    private WebDriver driver;

    private By skuAddInput = By.xpath("//form[@action='/add']//input[@name='sku']");
    private By skuUpdatedInput = By.xpath("//form[@action='/update']//input[@name='sku']");
    private By skuDeleteInput = By.xpath("//form[@action='/delete']//input[@name='sku']");

    private By nombreAddInput = By.xpath("//form[@action='/add']//input[@name='nombre']");

    private By precioAddInput = By.xpath("//form[@action='/add']//input[@name='precio']");

    private By cantidadAddInput = By.xpath("//form[@action='/add']//input[@name='cantidad']");
    private By cantidadUpdatedInput = By.xpath("//form[@action='/update']//input[@name='cantidad']");

    private By AddBtn = By.xpath("//button[text()='Agregar']");
    private By UpdateBtn = By.xpath("//button[text()='Actualizar']");
    private By DeleteBtn = By.xpath("//button[text()='Eliminar']");

    private By AlertTxt = By.xpath("//div");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void agregarProducto(String sku, String nombre, String precio, String cantidad) {
        driver.findElement(skuAddInput).sendKeys(sku);
        driver.findElement(nombreAddInput).sendKeys(nombre);
        driver.findElement(precioAddInput).sendKeys(precio);
        driver.findElement(cantidadAddInput).sendKeys(cantidad);
        driver.findElement(AddBtn).click();
    }

    public void actualizarCantidad(String sku, String cantidad) {
        driver.findElement(skuUpdatedInput).sendKeys(sku);
        driver.findElement(cantidadUpdatedInput).sendKeys(cantidad);
        driver.findElement(UpdateBtn).click();
    }

    public void eliminarProducto(String sku) {
        driver.findElement(skuDeleteInput).sendKeys(sku);
        driver.findElement(DeleteBtn).click();
    }
    public String validarPage() {
        return driver.findElement(By.tagName("h1")).getText();
    }

    public String obtenerMensaje() {
        return driver.findElement(AlertTxt).getText();
    }
    public String validarFormAdicion() {
        return driver.findElement(By.xpath("//h2[text()='Agregar producto']")).getText();
    }
    public String validarFormEliminar() {
        return driver.findElement(By.xpath("//h2[text()='Eliminar producto']")).getText();
    }
}
