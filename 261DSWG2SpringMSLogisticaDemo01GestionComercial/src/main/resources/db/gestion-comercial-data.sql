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

INSERT INTO clientes(cliente_id, dni, mail, nombre, telefono)
VALUES
(1, '70123456', 'juan.perez@gmail.com', 'Juan Pérez García', '987654321'),
(2, '70123457', 'maria.lopez@gmail.com', 'María López Torres', '986543210'),
(3, '70123458', 'carlos.ramirez@gmail.com', 'Carlos Ramírez Díaz', '985432109'),
(4, '70123459', 'ana.gutierrez@gmail.com', 'Ana Gutiérrez Flores', '984321098'),
(5, '70123460', 'luis.sanchez@gmail.com', 'Luis Sánchez Rojas', '983210987'),
(6, '70123461', 'rosa.vargas@gmail.com', 'Rosa Vargas Castillo', '982109876'),
(7, '70123462', 'jose.mendoza@gmail.com', 'José Mendoza Ruiz', '981098765'),
(8, '70123463', 'carmen.herrera@gmail.com', 'Carmen Herrera León', '980987654'),
(9, '70123464', 'pedro.silva@gmail.com', 'Pedro Silva Quispe', '989876543'),
(10, '70123465', 'laura.castro@gmail.com', 'Laura Castro Medina', '988765432'),

(11, '70123466', 'miguel.flores@gmail.com', 'Miguel Flores Soto', '987123456'),
(12, '70123467', 'patricia.arias@gmail.com', 'Patricia Arias Gómez', '986234567'),
(13, '70123468', 'jorge.espinoza@gmail.com', 'Jorge Espinoza Ramos', '985345678'),
(14, '70123469', 'elena.morales@gmail.com', 'Elena Morales Peña', '984456789'),
(15, '70123470', 'ricardo.paredes@gmail.com', 'Ricardo Paredes Salazar', '983567890'),
(16, '70123471', 'lucia.navarro@gmail.com', 'Lucía Navarro Vega', '982678901'),
(17, '70123472', 'daniel.reyes@gmail.com', 'Daniel Reyes Campos', '981789012'),
(18, '70123473', 'sofia.delgado@gmail.com', 'Sofía Delgado Ortiz', '980890123'),
(19, '70123474', 'fernando.mejia@gmail.com', 'Fernando Mejía Torres', '989901234'),
(20, '70123475', 'claudia.ruiz@gmail.com', 'Claudia Ruiz Chávez', '988012345'),

(21, '70123476', 'edgar.valdez@gmail.com', 'Edgar Valdez Luna', '987111222'),
(22, '70123477', 'karina.cabrera@gmail.com', 'Karina Cabrera Ríos', '986222333'),
(23, '70123478', 'omar.guerrero@gmail.com', 'Omar Guerrero Flores', '985333444'),
(24, '70123479', 'diana.salinas@gmail.com', 'Diana Salinas Vega', '984444555'),
(25, '70123480', 'raul.marin@gmail.com', 'Raúl Marín Torres', '983555666'),
(26, '70123481', 'veronica.rios@gmail.com', 'Verónica Ríos Castro', '982666777'),
(27, '70123482', 'cesar.romero@gmail.com', 'César Romero León', '981777888'),
(28, '70123483', 'angela.martinez@gmail.com', 'Ángela Martínez Soto', '980888999'),
(29, '70123484', 'hugo.ponce@gmail.com', 'Hugo Ponce Ramos', '989111333'),
(30, '70123485', 'monica.cardenas@gmail.com', 'Mónica Cárdenas Díaz', '988222444'),

(31, '70123486', 'kevin.alvarez@gmail.com', 'Kevin Álvarez Torres', '987333555'),
(32, '70123487', 'pamela.nunez@gmail.com', 'Pamela Núñez García', '986444666'),
(33, '70123488', 'alexis.moreno@gmail.com', 'Alexis Moreno Salas', '985555777'),
(34, '70123489', 'gabriela.torres@gmail.com', 'Gabriela Torres Vega', '984666888'),
(35, '70123490', 'ivan.carrasco@gmail.com', 'Iván Carrasco Medina', '983777999'),
(36, '70123491', 'melissa.palma@gmail.com', 'Melissa Palma Rojas', '982888111'),
(37, '70123492', 'renato.fuentes@gmail.com', 'Renato Fuentes León', '981999222'),
(38, '70123493', 'paola.aguirre@gmail.com', 'Paola Aguirre Campos', '980111444'),
(39, '70123494', 'sergio.villanueva@gmail.com', 'Sergio Villanueva Ortiz', '989222555'),
(40, '70123495', 'valeria.quispe@gmail.com', 'Valeria Quispe Flores', '988333666')
ON CONFLICT (cliente_id) DO NOTHING;

INSERT INTO PEDIDOS (pedido_id, fecha_creacion, fecha_entrega, fecha_entrega_maxima, cliente_id, tienda_id, monto_total, estado)
VALUES
(1, '2026-06-01 10:15:00', NULL, '2026-06-08 18:00:00', 1, 1, 157.80, 'PENDIENTE'),
(2, '2026-05-15 09:30:00', '2026-05-20 14:20:00', '2026-05-22 18:00:00', 2, 1, 101.70, 'ENTREGADO'),
(3, '2026-05-01 11:45:00', '2026-05-12 16:10:00', '2026-05-10 18:00:00', 3, 1, 170.80, 'ENTREGADO CON RETRASO'),
(4, '2026-04-18 08:00:00', '2026-04-23 11:30:00', '2026-04-25 18:00:00', 4, 1, 119.80, 'ENTREGADO'),
(5, '2026-06-10 15:10:00', NULL, '2026-06-17 18:00:00', 5, 1, 94.80, 'PENDIENTE'),
(6, '2026-04-10 10:00:00', '2026-04-22 09:15:00', '2026-04-18 18:00:00', 6, 1, 128.60, 'ENTREGADO CON RETRASO'),
(7, '2026-05-25 13:00:00', '2026-05-29 10:45:00', '2026-05-30 18:00:00', 7, 1, 116.70, 'ENTREGADO'),
(8, '2026-06-12 09:00:00', NULL, '2026-06-19 18:00:00', 8, 1, 169.80, 'PENDIENTE'),
(9, '2026-03-01 09:00:00', '2026-03-15 16:30:00', '2026-03-10 18:00:00', 9, 1, 224.80, 'ENTREGADO CON RETRASO'),
(10, '2026-03-12 14:20:00', '2026-03-22 10:15:00', '2026-03-20 18:00:00', 10, 1, 153.70, 'ENTREGADO CON RETRASO'),
(11, '2026-04-05 08:45:00', '2026-04-18 11:40:00', '2026-04-15 18:00:00', 11, 1, 187.80, 'ENTREGADO CON RETRASO'),
(12, '2026-04-20 13:10:00', '2026-05-02 09:20:00', '2026-04-28 18:00:00', 12, 1, 142.60, 'ENTREGADO CON RETRASO'),
(13, '2026-05-03 11:30:00', '2026-05-16 15:00:00', '2026-05-12 18:00:00', 13, 1, 211.80, 'ENTREGADO CON RETRASO'),
(14, '2026-01-05 09:00:00', '2026-01-08 14:00:00', '2026-01-10 18:00:00', 14, 1, 129.80, 'ENTREGADO'),
(15, '2026-01-12 11:30:00', '2026-01-16 15:20:00', '2026-01-18 18:00:00', 15, 1, 184.90, 'ENTREGADO'),
(16, '2026-01-25 08:45:00', '2026-01-30 10:00:00', '2026-02-01 18:00:00', 16, 1, 95.80, 'ENTREGADO'),
(17, '2026-02-03 10:15:00', '2026-02-08 16:00:00', '2026-02-10 18:00:00', 17, 1, 218.70, 'ENTREGADO'),
(18, '2026-02-10 09:00:00', '2026-02-14 11:45:00', '2026-02-16 18:00:00', 18, 1, 149.90, 'ENTREGADO'),
(19, '2026-02-18 13:00:00', '2026-02-25 17:00:00', '2026-02-27 18:00:00', 19, 1, 73.90, 'ENTREGADO'),
(20, '2026-03-02 08:30:00', '2026-03-07 10:00:00', '2026-03-09 18:00:00', 20, 1, 259.80, 'ENTREGADO'),
(21, '2026-03-10 12:00:00', '2026-03-15 14:20:00', '2026-03-17 18:00:00', 21, 1, 167.90, 'ENTREGADO'),
(22, '2026-03-20 09:30:00', '2026-03-28 11:00:00', '2026-03-25 18:00:00', 22, 1, 88.80, 'ENTREGADO CON RETRASO'),
(23, '2026-04-01 10:00:00', '2026-04-05 15:00:00', '2026-04-07 18:00:00', 23, 1, 142.90, 'ENTREGADO'),
(24, '2026-04-10 14:00:00', '2026-04-16 16:30:00', '2026-04-18 18:00:00', 24, 1, 194.80, 'ENTREGADO'),
(25, '2026-04-18 08:15:00', '2026-04-24 09:45:00', '2026-04-26 18:00:00', 25, 1, 105.90, 'ENTREGADO'),
(26, '2026-05-02 11:00:00', '2026-05-06 13:20:00', '2026-05-08 18:00:00', 26, 1, 239.80, 'ENTREGADO'),
(27, '2026-05-08 09:00:00', '2026-05-14 12:00:00', '2026-05-16 18:00:00', 27, 1, 178.70, 'ENTREGADO'),
(28, '2026-05-15 15:00:00', '2026-05-25 17:00:00', '2026-05-22 18:00:00', 28, 1, 69.80, 'ENTREGADO CON RETRASO'),
(29, '2026-06-01 08:00:00', '2026-06-05 10:00:00', '2026-06-07 18:00:00', 29, 1, 287.80, 'ENTREGADO'),
(30, '2026-06-05 09:30:00', '2026-06-09 14:00:00', '2026-06-11 18:00:00', 30, 1, 209.90, 'ENTREGADO'),
(31, '2026-06-08 11:15:00', '2026-06-12 15:00:00', '2026-06-14 18:00:00', 31, 1, 118.80, 'ENTREGADO'),
(32, '2026-06-12 10:00:00', '2026-06-15 16:20:00', '2026-06-17 18:00:00', 32, 1, 156.90, 'ENTREGADO'),
(33, '2026-06-16 14:30:00', NULL, '2026-06-23 18:00:00', 33, 1, 199.80, 'PENDIENTE')
ON CONFLICT (pedido_id) DO NOTHING;

INSERT INTO DETALLE_PEDIDOS(detalle_pedido_id, cantidad, precio_unidad, pedido_id, producto_id)
VALUES
(1, 1, 65.00, 1, 1),
(2, 1, 39.90, 1, 8),
(3, 1, 52.90, 1, 6),
(4, 1, 74.90, 2, 23),
(5, 1, 17.90, 2, 33),
(6, 1, 8.90, 2, 39),
(7, 1, 85.00, 3, 7),
(8, 1, 39.90, 3, 12),
(9, 1, 22.00, 3, 21),
(10, 1, 23.90, 3, 42),
(11, 1, 89.90, 4, 22),
(12, 1, 18.90, 4, 40),
(13, 1, 11.00, 4, 29),
(14, 1, 54.90, 5, 11),
(15, 1, 39.90, 5, 15),
(16, 1, 65.00, 6, 1),
(17, 1, 49.90, 6, 25),
(18, 1, 13.80, 6, 10),
(19, 1, 74.90, 7, 6),
(20, 1, 27.90, 7, 14),
(21, 1, 13.90, 7, 10),
(22, 1, 95.90, 8, 26),
(23, 1, 39.90, 8, 12),
(24, 1, 22.00, 8, 21),
(25, 1, 12.00, 8, 27),
(26, 2, 65.00, 9, 1),
(27, 1, 54.90, 9, 11),
(28, 1, 39.90, 9, 8),
(29, 1, 89.90, 10, 22),
(30, 1, 39.90, 10, 12),
(31, 1, 13.90, 10, 10),
(32, 1, 10.00, 10, 38),
(33, 1, 95.90, 11, 26),
(34, 1, 39.90, 11, 15),
(35, 1, 27.90, 11, 14),
(36, 1, 24.10, 11, 42),
(37, 1, 74.90, 12, 23),
(38, 1, 49.90, 12, 25),
(39, 1, 17.80, 12, 33),
(40, 1, 85.00, 13, 7),
(41, 1, 65.00, 13, 1),
(42, 1, 39.90, 13, 8),
(43, 1, 21.90, 13, 21),
(44, 2, 39.90, 14, 8),
(45, 1, 49.90, 14, 25),
(46, 1, 89.90, 15, 22),
(47, 1, 54.90, 15, 11),
(48, 1, 40.10, 15, 24),
(49, 1, 65.00, 16, 1),
(50, 1, 17.90, 16, 33),
(51, 1, 12.90, 16, 9),
(52, 2, 89.90, 17, 22),
(53, 1, 38.90, 17, 12),
(54, 1, 95.90, 18, 26),
(55, 1, 39.90, 18, 8),
(56, 1, 14.10, 18, 34),
(57, 1, 39.90, 19, 8),
(58, 1, 22.00, 19, 21),
(59, 1, 12.00, 19, 27),
(60, 2, 65.00, 20, 1),
(61, 1, 89.90, 20, 22),
(62, 1, 39.90, 20, 15),
(63, 1, 95.90, 21, 26),
(64, 1, 39.90, 21, 12),
(65, 1, 32.10, 21, 17),
(66, 1, 74.90, 22, 23),
(67, 1, 13.90, 22, 10),
(68, 1, 89.90, 23, 22),
(69, 1, 39.90, 23, 8),
(70, 1, 13.10, 23, 13),
(71, 2, 65.00, 24, 1),
(72, 1, 64.80, 24, 22),
(73, 1, 54.90, 25, 24),
(74, 1, 39.90, 25, 12),
(75, 1, 11.10, 25, 29),
(76, 2, 89.90, 26, 22),
(77, 1, 65.00, 26, 1),
(78, 1, 40.00, 26, 24),
(79, 2, 74.90, 27, 23),
(80, 1, 28.90, 27, 14),
(81, 1, 39.90, 28, 15),
(82, 1, 17.90, 28, 33),
(83, 1, 12.00, 28, 27),
(84, 2, 95.90, 29, 26),
(85, 1, 89.90, 29, 22),
(86, 1, 6.10, 29, 28),
(87, 1, 95.90, 30, 26),
(88, 1, 74.90, 30, 23),
(89, 1, 39.10, 30, 25),
(90, 1, 65.00, 31, 1),
(91, 1, 39.90, 31, 8),
(92, 1, 13.90, 31, 10),
(93, 2, 54.90, 32, 11),
(94, 1, 39.90, 32, 12),
(95, 1, 7.20, 32, 37),
(96, 2, 89.90, 33, 22),
(97, 1, 19.90, 33, 40)
ON CONFLICT (detalle_pedido_id) DO NOTHING;

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