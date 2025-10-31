Feature: Actualizar la cantidad de un producto

  @issue-2
  Scenario: El usuario actualiza la cantidad de un producto existente
    Given que el usuario visualiza el inventario de productos
    When modifica la cantidad del producto "0001" a "10"
    Then el sistema refleja la nueva cantidad correctamente

  @issue-2
  Scenario: El usuario intenta actualizar la cantidad de un producto existente sin exito
    Given que el usuario visualiza el inventario de productos
    When modifica la cantidad del producto "9999" a "10"
    Then el sistema muestra un mensaje de error y no actualiza el inventario