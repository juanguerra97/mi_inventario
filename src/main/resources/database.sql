
# Script con la estructura de la base de datos

CREATE DATABASE mi_inventario; # crea la base de datos

USE mi_inventario; # selecciona la base de datos

# tabla con la informacion de los productos
CREATE TABLE Producto(
	id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(70) NOT NULL,
    marca VARCHAR(70) NOT NULL,
    UNIQUE INDEX id2_prod(nombre,marca)
);

# tabla para almacenar las categorias de productos
CREATE TABLE Categoria (
	nombre VARCHAR(70) PRIMARY KEY
);

# tabla para relacionar la tabla de Productos con la  tabla de Categorias
# la relacion es de varios a varios
CREATE TABLE CategoriaProducto(
	id_producto BIGINT UNSIGNED NOT NULL,
    nom_categoria VARCHAR(70) NOT NULL,
    INDEX ikeyprodfromcat(id_producto),
    FOREIGN KEY fkeyprodfromcat(id_producto) REFERENCES Producto(id) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX ikeycatfromprod(nom_categoria),
    FOREIGN KEY fkeycatfromprod(nom_categoria) REFERENCES Categoria(nombre) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY(id_producto,nom_categoria)
);

# tabla con la informacion de las presentaciones de los productos
# la relion es de uno a varios desde producto a presentacion
CREATE TABLE Presentacion(
	id_producto BIGINT UNSIGNED NOT NULL,
	nombre VARCHAR(70) NOT NULL, # nombre de la presentacion
    INDEX ikeyprodfrompre(id_producto),
    FOREIGN KEY fkeyprodfrompre(id_producto) REFERENCES Producto(id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY(id_producto,nombre)
);

# tabla para almacenar informacion de los lotes de los productos
# la relacion es de uno a varios desde producto a lote
CREATE TABLE Lote(
	id_producto BIGINT UNSIGNED NOT NULL,
	numero INTEGER UNSIGNED NOT NULL, # numero de lote
    fecha_vencimiento DATE DEFAULT NULL, # fecha de vencimiento de los productos en el lote
    INDEX ikeyprodfromlot(id_producto),
    FOREIGN KEY fkeyprodfromlot(id_producto) REFERENCES Producto(id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY(id_producto,numero)
);


# tabla para relacionar los lotes de un producto con sus presentaciones
# la relacion es de varios a varios
CREATE TABLE PresentacionLote(
	id_producto BIGINT UNSIGNED NOT NULL,
    nom_presentacion VARCHAR(70) NOT NULL,
    num_lote INTEGER UNSIGNED NOT NULL,
    cantidad INTEGER UNSIGNED NOT NULL DEFAULT 1,
    costo DECIMAL(12,2) UNSIGNED NOT NULL DEFAULT 0.0 CHECK(costo >= precio),
    precio DECIMAL(12,2) UNSIGNED NOT NULL DEFAULT 0.0 CHECK(precio <= costo),
    INDEX ikeyprefromprelot(id_producto,nom_presentacion),
    FOREIGN KEY fkeyprefromprelot(id_producto,nom_presentacion) REFERENCES Presentacion(id_producto,nombre) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX ikeylotfromprelot(id_producto,num_lote),
    FOREIGN KEY fkeylotfromprelot(id_producto,num_lote) REFERENCES Lote(id_producto,numero) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY(id_producto,nom_presentacion,num_lote)
);

# tabla con informacion de proveedores
CREATE TABLE Proveedor(
	nombre VARCHAR(70) PRIMARY KEY
);

# tabla para relacionar los proveedores con los productos
# la relacion es de varios a varios
CREATE TABLE ProveedorProducto(
	id_producto BIGINT UNSIGNED NOT NULL,
    nom_proveedor VARCHAR(70) NOT NULL,
    INDEX ikeyprodfromprovprod(id_producto),
    FOREIGN KEY fkeyprodfromprovprod(id_producto) REFERENCES Producto(id) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX ikeyprovfromprovprod(nom_proveedor),
    FOREIGN KEY fkeyprovfromprovprod(nom_proveedor) REFERENCES Proveedor(nom_proveedor) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY(id_producto,nom_proveedor)
);

# tabla con informacion de clientes
# se asume que todos los clientes son personas(no se aceptan instituciones como clientes)
CREATE TABLE Cliente(
	dpi VARCHAR(13) PRIMARY KEY CHECK(LENGTH(dpi) = 13), # Documento Personal de Identificacion (Guatemala)
    nombre VARCHAR(70) NOT NULL,
    apellido VARCHAR(70) NOT NULL,
    telefono VARCHAR(8) NOT NULL,
    email VARCHAR(30),
    departamento VARCHAR(20) NOT NULL,
    ciudad VARCHAR(40) NOT NULL,
    zona SMALLINT UNSIGNED NOT NULL CHECK(zona >= 1 AND zona <= 50),
    activo BOOL NOT NULL DEFAULT TRUE # indica si es un cliente activo(TRUE) o esta dado de baja(FALSE)
);

# tabla con informacion de las compras realizadas por la institucion
# se copian algunos datos de productos para que los registros no se pierdan en caso de que esos productos se eliminen
CREATE TABLE Compra(
	numero BIGINT UNSIGNED NOT NULL PRIMARY KEY,
    fecha DATE NOT NULL,
    total DECIMAL(12,2) UNSIGNED DEFAULT 0.0 NOT NULL
);

# tabla con los elementos de una compra ya que en una sola compra se pueden comprar diferentes productos
CREATE TABLE ItemCompra(
	num_compra BIGINT UNSIGNED NOT NULL, # compra a la que pertenece el item
    nom_producto VARCHAR(70) NOT NULL, # producto que se esta comprando
    marca_producto VARCHAR(70) NOT NULL, # marca del producto
    nom_presentacion VARCHAR(70) NOT NULL, # presentacion del producto que se compra
    lote_producto INTEGER UNSIGNED NOT NULL, # lote del producto en la presentacion comprada
    costo_unidad DECIMAL(12,2) UNSIGNED DEFAULT 0.0 NOT NULL, # costo por unidad de presentacion del producto
    cantidad INTEGER UNSIGNED DEFAULT 1 NOT NULL, # cantidad que se compra
    subtotal DECIMAL(12,2) UNSIGNED DEFAULT 0.0 NOT NULL,
    nom_proveedor VARCHAR(70) NOT NULL, # proveedor al que se hizo la compra
    INDEX ikeycomfromitcom(num_compra),
    FOREIGN KEY fkeycomfromitcom(num_compra) REFERENCES Compra(numero) ON DELETE CASCADE ON UPDATE CASCADE,
	INDEX ikeyprovfromitcom(nom_proveedor),
    FOREIGN KEY fkeyprovfromitcom(nom_proveedor) REFERENCES Proveedor(nombre) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY(num_compra,nom_producto,marca_producto,nom_presentacion,lote_producto,nom_proveedor)
);

# tabla con informacion de las ventas realizadas por los clientes
# se copian algunos datos de productos para que los registros no se pierdan en caso de que esos productos se eliminen
CREATE TABLE Venta(
	numero BIGINT UNSIGNED NOT NULL PRIMARY KEY,
    fecha DATE NOT NULL,
    total DECIMAL(12,2) UNSIGNED DEFAULT 0.0 NOT NULL
);

# tabla con los elementos de una venta ya que en una sola venta se pueden vender diferentes productos
CREATE TABLE ItemVenta(
	num_venta BIGINT UNSIGNED NOT NULL, # venta a la que pertenece el item
    nom_producto VARCHAR(70) NOT NULL, # producto que se esta comprando
    marca_producto VARCHAR(70) NOT NULL, # marca del producto
    nom_presentacion VARCHAR(70) NOT NULL, # presentacion del producto que se compra
    lote_producto INTEGER UNSIGNED NOT NULL, # lote del producto en la presentacion comprada
    precio_unidad DECIMAL(12,2) UNSIGNED DEFAULT 0.0 NOT NULL, # precio por unidad de presentacion del producto
    cantidad INTEGER UNSIGNED DEFAULT 1 NOT NULL, # cantidad que se vende
    subtotal DECIMAL(12,2) UNSIGNED DEFAULT 0.0 NOT NULL,
    dpi_cliente VARCHAR(13), # cliente al que se hizo la venta
    nombre_cliente VARCHAR(70), # si el cliente no esta registrado o no da su dpi, debe proveer su nombre
    INDEX ikeyvenfromitven(num_venta),
    FOREIGN KEY fkeyvenfromitven(num_venta) REFERENCES Venta(numero) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY fkeyclifromitven(dpi_cliente) REFERENCES Cliente(dpi) ON DELETE SET NULL ON UPDATE CASCADE,
    PRIMARY KEY(num_venta,nom_producto,marca_producto,nom_presentacion,lote_producto)
);

# trigger para evitar el borrado de productos para los cuales hayan registros con cantidades en existencia
DELIMITER //
CREATE TRIGGER del_prod_check_unidades BEFORE DELETE ON Producto
	FOR EACH ROW
		BEGIN
			IF EXISTS (SELECT id_producto FROM PresentacionLote WHERE id_producto = OLD.id AND cantidad > 0) THEN
				SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'NO se puede eliminar el producto ya que aun hay unidades en existencia';
            END IF;
		END;//
DELIMITER ;


# trigger para evitar el borrado de presentaciones para las cuales hayan existencias
DELIMITER //
CREATE TRIGGER del_pres_check_unidades BEFORE DELETE ON Presentacion
	FOR EACH ROW
		BEGIN
			IF EXISTS (SELECT id_producto FROM PresentacionLote WHERE id_producto = OLD.id_producto AND nom_presentacion = OLD.nombre AND cantidad > 0) THEN
				SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'NO se puede eliminar la presentacion ya que aun hay unidades en existencia';
            END IF;
		END;//
DELIMITER ;

# trigger para evitar el borrado de lotes para los cuales hayan existencias
DELIMITER //
CREATE TRIGGER del_lot_check_unidades BEFORE DELETE ON Lote
	FOR EACH ROW
		BEGIN
			IF EXISTS (SELECT id_producto FROM PresentacionLote WHERE id_producto = OLD.id_producto AND num_lote = OLD.numero AND cantidad > 0) THEN
				SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'NO se puede eliminar el lote ya que aun hay unidades en existencia';
            END IF;
		END;//
DELIMITER ;

# trigger para evitar el borrado de relaciones presentacion-lote 
#para las cuales hayan existencias
DELIMITER //
CREATE TRIGGER del_preslot_check_unidades BEFORE DELETE ON PresentacionLote
	FOR EACH ROW
		BEGIN
			IF OLD.cantidad > 0 THEN
				SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'NO se puede eliminar el elemento ya que aun hay unidades en existencia';
            END IF;
		END;//
DELIMITER ;