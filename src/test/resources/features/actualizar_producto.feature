Feature: Actualizar la cantidad de un producto
  Como encargado del almacén quiero modificar la cantidad de un producto existente
  para reflejar las entradas y salidas de los productos en tiempo real

  Background:
   Given ingresa el nombre "Laptop", el SKU "0001", el precio "850" y la cantidad "5" y guardar

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