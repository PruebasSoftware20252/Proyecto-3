Feature: Eliminar un producto del inventario

  @issue-3
  Scenario: El usuario elimina un producto existente
    Given que el usuario esta en el formulario de eliminacion
    When selecciona el producto con SKU "0001" y lo elimina
    Then el sistema elimina el producto y muestra un mensaje de confirmación

  @issue-3
  Scenario: El usuario intentar eliminar un producto existente sin exito
    Given que el usuario esta en el formulario de eliminacion
    When selecciona el producto con SKU "0001" y lo elimina
    Then el sistema no elimina el producto