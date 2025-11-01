package com.inventario.steps;

import com.inventario.hooks.Hooks;
import com.inventario.pages.MainPage;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import static com.inventario.util.TextUtil.normalizeAsciiLower;

public class EliminarSteps {

    private MainPage mainPage;

    public EliminarSteps() {
    }
    @Before(order = 1)
    public void initPage() {
        mainPage = new MainPage(Hooks.driver);
        mainPage.abrirHome();
    }

    @Given("que el usuario esta en el formulario de eliminacion")
    public void que_el_usuario_esta_en_el_formulario_de_elminicion() {
        Assertions.assertEquals("Eliminar producto",mainPage.validarFormEliminar(),"Formulario cargado correctamente");
    }
    @When("selecciona el producto con SKU {string} y lo elimina")
    public void selecciona_el_producto_con_sku_y_lo_elimina(String sku) {
        mainPage.eliminarProducto(sku);
    }
    @Then("el sistema elimina el producto y muestra un mensaje de confirmación")
    public void el_sistema_elimina_el_producto_y_muestra_un_mensaje_de_confirmacion() {
        Assertions.assertEquals(normalizeAsciiLower("Eliminaciòn del producto exitosa"),normalizeAsciiLower(mainPage.obtenerMensaje()),"Eliminación del producto exitosa");
    }
    @Then("el sistema no elimina el producto")
    public void el_sistema_no_elimina_el_producto() {
        Assertions.assertEquals("Error al eliminar producto",mainPage.obtenerMensaje(),"Eliminacion del producto no exitosa");
    }
}
