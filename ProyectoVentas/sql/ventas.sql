-- =====================================================================
--  Script de la base de datos VENTAS (Oracle)
--  Ejecutar completo con el usuario que vaya a utilizar CadVentas.
-- =====================================================================

-- Borrado previo (ignorar los errores si los objetos no existen)
DROP TABLE pedido;
DROP TABLE producto;
DROP TABLE cliente;
DROP SEQUENCE pedido_seq;

CREATE TABLE cliente (
    cliente_id NUMBER(5),
    nombre     VARCHAR2(50)  NOT NULL,
    dni        CHAR(9)       NOT NULL,
    telefono   CHAR(9)       NOT NULL,

    CONSTRAINT cliente_pk PRIMARY KEY (cliente_id),
    CONSTRAINT cliente_nombre_uk UNIQUE (nombre),
    CONSTRAINT cliente_dni_uk UNIQUE (dni),
    CONSTRAINT cliente_telefono_ck CHECK (SUBSTR(telefono, 1, 1) IN ('6', '7', '9'))
);

CREATE TABLE producto (
    producto_id NUMBER(6),
    nombre      VARCHAR2(50)  NOT NULL,
    referencia  CHAR(8)       NOT NULL,
    precio      NUMBER(8,2)   NOT NULL,
    stock       NUMBER(5)     NOT NULL,
    categoria   VARCHAR2(20)  NOT NULL,

    CONSTRAINT producto_pk PRIMARY KEY (producto_id),
    CONSTRAINT producto_nombre_uk UNIQUE (nombre),
    CONSTRAINT producto_referencia_uk UNIQUE (referencia),
    CONSTRAINT producto_precio_ck CHECK (precio > 0),
    CONSTRAINT producto_stock_ck CHECK (stock >= 0)
);

CREATE TABLE pedido (
    pedido_id   NUMBER(7),
    cliente_id  NUMBER(5)     NOT NULL,
    producto_id NUMBER(6)     NOT NULL,
    codigo      VARCHAR2(20)  NOT NULL,
    cantidad    NUMBER(4)     NOT NULL,

    CONSTRAINT pedido_pk PRIMARY KEY (pedido_id),
    CONSTRAINT pedido_codigo_uk UNIQUE (codigo),
    CONSTRAINT pedido_cantidad_ck CHECK (cantidad > 0),
    CONSTRAINT pedido_cliente_fk FOREIGN KEY (cliente_id)
        REFERENCES cliente (cliente_id),
    CONSTRAINT pedido_producto_fk FOREIGN KEY (producto_id)
        REFERENCES producto (producto_id)
);

-- Secuencia que abastece la primary key de PEDIDO
CREATE SEQUENCE pedido_seq START WITH 1 INCREMENT BY 1 NOCACHE;

-- Procedimiento almacenado que utiliza CadVentas.insertarPedido (CallableStatement)
CREATE OR REPLACE PROCEDURE insertar_pedido (
    p_cliente_id  IN NUMBER,
    p_producto_id IN NUMBER,
    p_codigo      IN VARCHAR2,
    p_cantidad    IN NUMBER,
    p_registros   OUT NUMBER
) AS
BEGIN
    INSERT INTO pedido (pedido_id, cliente_id, producto_id, codigo, cantidad)
    VALUES (pedido_seq.NEXTVAL, p_cliente_id, p_producto_id, p_codigo, p_cantidad);
    p_registros := SQL%ROWCOUNT;
END;
/

-- Datos de prueba
INSERT INTO cliente VALUES (1, 'Ana Lopez',   '11111111A', '600111222');
INSERT INTO cliente VALUES (2, 'Luis Garcia', '22222222B', '944333444');

INSERT INTO producto VALUES (1, 'Teclado', 'REF00001', 25.50,  100, 'Perifericos');
INSERT INTO producto VALUES (2, 'Raton',   'REF00002', 12.00,  200, 'Perifericos');
INSERT INTO producto VALUES (3, 'Monitor', 'REF00003', 149.99, 30,  'Pantallas');

-- El producto 1 tiene un pedido (no se podra eliminar); el 2 y el 3 no tienen
INSERT INTO pedido VALUES (pedido_seq.NEXTVAL, 1, 1, 'PED-0001', 2);

COMMIT;
