Feature: Eliminar un producto del inventario
  Como encargado del almacén, quiero eliminar un producto del inventario,
  para mantener la lista actualizada y sin registros innecesarios

  Background:
    Given ingresa el nombre "Laptop", el SKU "0001", el precio "850" y la cantidad "5" y guardar

  @issue-3
  Scenario: El usuario elimina un producto existente
    Given que el usuario esta en el formulario de eliminacion
    When selecciona el producto con SKU "0001" y lo elimina
    Then el sistema elimina el producto y muestra un mensaje de confirmación

  @issue-3
  Scenario: El usuario intentar eliminar un producto existente sin exito
    Given que el usuario esta en el formulario de eliminacion
    When selecciona el producto con SKU "0002" y lo elimina
    Then el sistema no elimina el producto