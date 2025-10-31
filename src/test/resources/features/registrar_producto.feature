Feature: Registrar un producto en el inventario
  Como encargado del almacén quiero registrar un nuevo producto con nombre, cantidad inicial y tipo de producto,
  para mantener el control del inventario.

  @issue-1
  Scenario: El usuario registra un nuevo producto sin exito
    Given que el usuario esta en el formulario de registro
    When ingresa el nombre "Laptop", el SKU "P001", el precio "850" y la cantidad "5" y guardar
    Then el sistema muestra un mensaje de error de registro no exitoso y no guarda el producto

  @issue-1
  Scenario: El usuario registra un nuevo producto exitosamente
    Given que el usuario esta en el formulario de registro
    When ingresa el nombre "Laptop", el SKU "0001", el precio "850" y la cantidad "5" y guardar
    Then el sistema muestra un mensaje de registro exitoso y guarda el producto

