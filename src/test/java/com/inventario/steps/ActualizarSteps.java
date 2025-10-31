package com.inventario.steps;

import com.inventario.hooks.Hooks;
import com.inventario.pages.MainPage;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ActualizarSteps {
    private MainPage mainPage;
    private String skuCtx;
    private int cantNuevaCtx;

    public ActualizarSteps() {
    }

    @Before(order = 1)
    public void initPage() {
        mainPage = new MainPage(Hooks.driver);
    }

    @Given("que el usuario visualiza el inventario de productos")
    public void que_el_usuario_visualiza_el_inventario_de_productos() {
        Assertions.assertEquals("Inventario", mainPage.validarPage(), "Inventario cargado correctamente");
    }

    @When("modifica la cantidad del producto {string} a {string}")
    public void modifica_la_cantidad_del_producto_a(String sku, String cantidad) {
        this.skuCtx = sku == null ? null : sku.trim();
        this.cantNuevaCtx = Integer.parseInt(cantidad.trim());
        mainPage.actualizarCantidad(this.skuCtx, cantidad);
    }

    @Then("el sistema refleja la nueva cantidad correctamente")
    public void el_sistema_refleja_la_nueva_cantidad_correctamente() {
        int cantidadActual = obtenerCantidadPorSku(Hooks.driver, skuCtx);
        Assertions.assertEquals(cantNuevaCtx, cantidadActual,
                "Cantidad no coincide para SKU " + skuCtx);
    }

    @Then("el sistema muestra un mensaje de error y no actualiza el inventario")
    public void el_sistema_muestra_un_mensaje_de_error_y_no_actualiza_el_inventario() {
        assertTrue(mainPage.obtenerMensaje().startsWith("Error al actualizar producto"),
                "Mensaje no empieza como se esperaba. Fue: " + mainPage.obtenerMensaje());
    }

    private int obtenerCantidadPorSku(WebDriver driver, String skuBuscado) {
        WebElement tabla = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.tagName("table")));

        skuBuscado = skuBuscado.trim();

        for (WebElement fila : tabla.findElements(By.cssSelector("tbody tr"))) {
            List<WebElement> tds = fila.findElements(By.tagName("td"));
            if (tds.size() >= 4) {
                String skuTabla = tds.get(0).getText().trim();
                if (skuTabla.equals(skuBuscado)) {
                    return Integer.parseInt(tds.get(3).getText().trim());
                }
            }
        }
        Assertions.fail("No se encontró el SKU " + skuBuscado + " en la tabla.");
        return -1;
    }

}