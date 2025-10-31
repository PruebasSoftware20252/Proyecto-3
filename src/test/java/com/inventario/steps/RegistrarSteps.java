package com.inventario.steps;

import com.inventario.hooks.Hooks;
import com.inventario.pages.MainPage;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class RegistrarSteps {

    private MainPage mainPage;

    public RegistrarSteps() {
    }
    @Before(order = 1)
    public void initPage() {
        mainPage = new MainPage(Hooks.driver);
    }

    @Given("que el usuario esta en el formulario de registro")
    public void que_el_usuario_esta_en_el_formulario_de_registro() {
        Assertions.assertEquals("Agregar producto",mainPage.validarFormAdicion(),"Formulario cargado correctamente");
        mainPage.eliminarProducto("0001");
    }
    @When("ingresa el nombre {string}, el SKU {string}, el precio {string} y la cantidad {string} y guardar")
    public void ingresa_el_nombre_el_sku_y_la_cantidad(String nombre, String sku, String precio, String cantidad) {
       mainPage.agregarProducto(sku, nombre, precio, cantidad);
    }
    @Then("el sistema muestra un mensaje de registro exitoso y guarda el producto")
    public void el_sistema_muestra_un_mensaje_de_registro_exitoso_y_guarda_el_producto() {
        Assertions.assertEquals("Adición del producto exitosa",mainPage.obtenerMensaje(),"Adición del producto exitosa");
    }

    @Then("el sistema muestra un mensaje de error de registro no exitoso y no guarda el producto")
    public void el_sistema_muestra_un_mensaje_de_error_de_registro_no_exitoso_y_no_guarda_el_producto() {
        assertTrue(mainPage.obtenerMensaje().startsWith("Error al agregar producto"),
                "Mensaje no empieza como se esperaba. Fue: " + mainPage.obtenerMensaje());

    }

}
