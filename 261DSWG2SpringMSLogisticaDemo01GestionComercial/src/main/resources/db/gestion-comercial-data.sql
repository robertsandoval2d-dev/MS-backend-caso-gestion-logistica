INSERT INTO lineas_producto(linea_producto_id, nombre, descripcion)
VALUES 
(1, 'Cerámicas', 'Cerámicas para pisos y paredes de uso residencial y comercial'),
(2, 'Herramientas Manuales', 'Herramientas básicas para construcción y mantenimiento'),
(3, 'Materiales de Construcción', 'Cemento, arena, ladrillos y otros insumos esenciales'),
(4, 'Pinturas y Acabados', 'Pinturas, barnices y productos para acabado de superficies'),
(5, 'Gasfitería', 'Tuberías, conexiones y accesorios para instalaciones sanitarias'),
(6, 'Electricidad', 'Cables, interruptores y materiales para instalaciones eléctricas')
ON CONFLICT (linea_producto_id) DO NOTHING;

INSERT INTO productos(producto_id, categoria, descripcion, nombre, precio_venta, linea_producto_id)
VALUES
-- Cerámicas (1)
(1, 'Pisos', 'Cerámica para piso 60x60 cm alto tránsito', 'Cerámica Piso Gris 60x60', 65.00, 1),
(2, 'Paredes', 'Cerámica para pared acabado brillante', 'Cerámica Blanca Brillante', 42.00, 1),
(3, 'Decorativa', 'Cerámica tipo piedra decorativa', 'Cerámica Piedra Decorativa', 78.00, 1),
(4, 'Pisos', 'Cerámica antideslizante para exteriores', 'Cerámica Antideslizante', 68.00, 1),
(5, 'Paredes', 'Cerámica tipo mosaico', 'Cerámica Mosaico Azul', 49.00, 1),
(6, 'Pisos', 'Cerámica estilo madera', 'Cerámica Madera Roble', 74.00, 1),
(7, 'Decorativa', 'Cerámica diseño moderno geométrico', 'Cerámica Geométrica', 85.00, 1),

-- Herramientas Manuales (2)
(8, 'Herramientas', 'Martillo de acero con mango ergonómico', 'Martillo Profesional', 39.90, 2),
(9, 'Herramientas', 'Destornillador plano', 'Destornillador Plano', 12.90, 2),
(10, 'Herramientas', 'Destornillador estrella', 'Destornillador Phillips', 13.90, 2),
(11, 'Herramientas', 'Llave inglesa ajustable', 'Llave Inglesa 10"', 54.90, 2),
(12, 'Herramientas', 'Alicate de presión', 'Alicate Presión', 39.90, 2),
(13, 'Herramientas', 'Cinta métrica de 5m', 'Wincha 5m', 17.90, 2),
(14, 'Herramientas', 'Nivel de burbuja', 'Nivel 40cm', 27.90, 2),

-- Materiales de Construcción (3)
(15, 'Construcción', 'Cemento portland tipo I', 'Cemento Bolsa 42.5kg', 39.90, 3),
(16, 'Construcción', 'Arena fina para acabados', 'Arena Fina', 30.00, 3),
(17, 'Construcción', 'Arena gruesa para mezcla', 'Arena Gruesa', 32.00, 3),
(18, 'Construcción', 'Ladrillo king kong', 'Ladrillo KK', 1.80, 3),
(19, 'Construcción', 'Bloque de concreto', 'Bloque Concreto', 5.50, 3),
(20, 'Construcción', 'Grava para concreto', 'Grava', 6.00, 3),
(21, 'Construcción', 'Yeso para construcción', 'Yeso Bolsa', 22.00, 3),

-- Pinturas y Acabados (4)
(22, 'Pinturas', 'Pintura látex para interiores', 'Pintura Látex Blanca', 89.90, 4),
(23, 'Pinturas', 'Pintura esmalte sintético', 'Esmalte Azul', 74.90, 4),
(24, 'Acabados', 'Barniz para madera', 'Barniz Brillante', 54.90, 4),
(25, 'Acabados', 'Sellador para paredes', 'Sellador Acrílico', 49.90, 4),
(26, 'Pinturas', 'Pintura antihumedad', 'Pintura Antihumedad', 95.90, 4),
(27, 'Herramientas', 'Rodillo para pintar', 'Rodillo Profesional', 18.90, 4),
(28, 'Herramientas', 'Brocha de 2 pulgadas', 'Brocha 2"', 9.90, 4),

-- Gasfitería (5)
(29, 'Gasfitería', 'Tubería PVC 1/2 pulgada', 'Tubería PVC 1/2"', 11.90, 5),
(30, 'Gasfitería', 'Codo PVC 90 grados', 'Codo PVC 1/2"', 5.90, 5),
(31, 'Gasfitería', 'Tee PVC', 'Tee PVC 1/2"', 6.50, 5),
(32, 'Gasfitería', 'Llave de paso', 'Llave de Paso', 26.90, 5),
(33, 'Gasfitería', 'Sifón para lavatorio', 'Sifón Flexible', 17.90, 5),
(34, 'Gasfitería', 'Pegamento para PVC', 'Pegamento PVC', 14.90, 5),
(35, 'Gasfitería', 'Cinta teflón', 'Cinta Teflón', 3.90, 5),

-- Electricidad (6)
(36, 'Electricidad', 'Cable eléctrico 2.5 mm', 'Cable 2.5mm', 4.50, 6),
(37, 'Electricidad', 'Interruptor simple', 'Interruptor Simple', 7.50, 6),
(38, 'Electricidad', 'Tomacorriente doble', 'Tomacorriente Doble', 10.90, 6),
(39, 'Electricidad', 'Foco LED 9W', 'Foco LED 9W', 8.90, 6),
(40, 'Electricidad', 'Caja de distribución', 'Caja Eléctrica', 18.90, 6),
(41, 'Electricidad', 'Cinta aislante', 'Cinta Aislante', 3.90, 6),
(42, 'Electricidad', 'Breaker 20A', 'Breaker 20A', 22.90, 6)

ON CONFLICT (producto_id) DO NOTHING;

INSERT INTO proveedores (proveedor_id, mail_contacto, nombre, telefono)
VALUES
(1, 'ventas@distribuidoranacional.pe', 'Distribuidora Nacional SAC', '987654321'),
(2, 'contacto@ferremax.pe', 'FerreMax Perú', '986532147'),
(3, 'ventas@industrialandina.pe', 'Industrial Andina SAC', '985421369'),
(4, 'compras@multisuministros.pe', 'Multisuministros del Perú', '984312578'),
(5, 'ventas@importadoralima.pe', 'Importadora Lima Norte', '983214567'),
(6, 'contacto@constructoracentral.pe', 'Constructora Central Supply', '982145678')
ON CONFLICT (proveedor_id) DO NOTHING;

INSERT INTO productos_proveedores (producto_proveedor_id, precio, producto_id, proveedor_id)
VALUES

-- Cerámicas
(1, 45.50, 1, 1),
(2, 46.00, 1, 4),
(3, 44.80, 1, 5),

(4, 30.00, 2, 1),
(5, 31.20, 2, 4),

(6, 55.00, 3, 3),
(7, 54.20, 3, 6),

(8, 48.00, 4, 1),
(9, 47.50, 4, 5),
(10, 49.00, 4, 4),

(11, 35.00, 5, 4),
(12, 34.50, 5, 1),

(13, 52.00, 6, 1),
(14, 50.90, 6, 5),
(15, 51.50, 6, 3),

(16, 60.00, 7, 3),

-- Herramientas
(17, 25.00, 8, 2),
(18, 24.80, 8, 5),
(19, 25.40, 8, 4),

(20, 8.00, 9, 2),
(21, 7.90, 9, 4),

(22, 9.00, 10, 2),
(23, 8.70, 10, 4),
(24, 9.20, 10, 5),

(25, 35.00, 11, 5),

(26, 28.00, 12, 2),
(27, 27.50, 12, 4),
(28, 28.20, 12, 1),

(29, 12.00, 13, 2),
(30, 11.80, 13, 4),

(31, 18.00, 14, 5),
(32, 17.70, 14, 2),

-- Construcción
(33, 28.00, 15, 6),
(34, 27.50, 15, 3),
(35, 28.30, 15, 1),

(36, 22.00, 16, 6),

(37, 23.00, 17, 3),
(38, 22.70, 17, 6),

(39, 1.20, 18, 6),
(40, 1.10, 18, 3),
(41, 1.15, 18, 4),

(42, 3.50, 19, 6),

(43, 4.00, 20, 3),
(44, 4.10, 20, 4),
(45, 3.90, 20, 6),

(46, 15.00, 21, 6),
(47, 14.70, 21, 3),

-- Pinturas
(48, 65.00, 22, 4),
(49, 64.80, 22, 1),
(50, 65.20, 22, 5),

(51, 55.00, 23, 4),
(52, 54.50, 23, 1),

(53, 40.00, 24, 4),
(54, 39.50, 24, 5),

(55, 35.00, 25, 1),
(56, 34.80, 25, 4),

(57, 70.00, 26, 4),
(58, 69.20, 26, 5),
(59, 68.90, 26, 1),

(60, 12.00, 27, 2),
(61, 11.50, 27, 4),

(62, 6.00, 28, 4),

-- Gasfitería
(63, 8.00, 29, 2),
(64, 7.80, 29, 5),
(65, 8.10, 29, 4),

(66, 3.50, 30, 2),

(67, 4.00, 31, 5),
(68, 3.90, 31, 2),

(69, 18.00, 32, 2),
(70, 17.50, 32, 4),
(71, 18.20, 32, 5),

(72, 12.00, 33, 5),

(73, 10.00, 34, 2),
(74, 9.80, 34, 5),

(75, 2.00, 35, 5),

-- Electricidad
(76, 3.00, 36, 2),
(77, 3.20, 36, 5),
(78, 2.90, 36, 4),

(79, 5.00, 37, 2),
(80, 4.80, 37, 5),

(81, 7.00, 38, 5),
(82, 6.90, 38, 2),

(83, 6.00, 39, 2),
(84, 5.80, 39, 4),
(85, 6.10, 39, 5),

(86, 12.00, 40, 3),

(87, 2.50, 41, 2),
(88, 2.40, 41, 4),

(89, 15.00, 42, 5),
(90, 14.50, 42, 3),
(91, 14.80, 42, 2)
ON CONFLICT (producto_proveedor_id) DO NOTHING;

SELECT setval(
    pg_get_serial_sequence('lineas_producto','linea_producto_id'),
    (SELECT COALESCE(MAX(linea_producto_id),0) FROM lineas_producto)
);

SELECT setval(
    pg_get_serial_sequence('productos','producto_id'),
    (SELECT COALESCE(MAX(producto_id),0) FROM productos)
);

SELECT setval(
    pg_get_serial_sequence('proveedores','proveedor_id'),
    (SELECT COALESCE(MAX(proveedor_id),0) FROM proveedores)
);