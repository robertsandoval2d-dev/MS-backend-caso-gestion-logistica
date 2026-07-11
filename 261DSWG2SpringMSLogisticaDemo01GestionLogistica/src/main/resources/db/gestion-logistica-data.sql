-- INSERT INTO almacenes (almacen_id, capacidad, nombre, ubicacion)
-- VALUES
-- (1,10000,'Almacén Lima','San Borja, Lima'),
-- (2,12000,'Almacén Lima Norte','Av. Perú 3580, San Martín de Porres, Lima')
-- ON CONFLICT (almacen_id) DO NOTHING;

-- INSERT INTO tiendas (tienda_id, almacen_id, ciudad, nombre, direccion)
-- VALUES
-- (1,1,'Lima','Ferreteria Peruana SA - SB','Av. Aviación 2450, San Borja, Lima'),
-- (2,2,'Lima','Ferretería Peruana SA - SMP','Av. Perú 3580, San Martín de Porres, Lima')
-- ON CONFLICT (tienda_id) DO NOTHING;

-- INSERT INTO zonas_almacen
-- (zona_almacen_id, categoria, capacidad_maxima, capacidad_actual, almacen_id)
-- VALUES
-- (1,'Pisos',600,410,1),
-- (2,'Paredes',300,180,1),
-- (3,'Decorativa',200,110,1),
-- (4,'Herramientas',1400,1060,1),
-- (5,'Construcción',2200,1710,1),
-- (6,'Pinturas',800,580,1),
-- (7,'Acabados',400,270,1),
-- (8,'Gasfitería',1800,1430,1),
-- (9,'Electricidad',2000,1620,1)
-- ON CONFLICT (zona_almacen_id) DO NOTHING;

-- INSERT INTO inventarios
-- (inventario_id, rotacion, stock, stock_min, zona_almacen_id, producto_id)
-- VALUES
-- (1,'MEDIA',120,30,1,1),
-- (2,'ALTA',150,40,1,4),
-- (3,'ALTA',140,35,1,6),
-- (4,'MEDIA',100,25,2,2),
-- (5,'MEDIA',80,20,2,5),
-- (6,'BAJA',60,15,3,3),
-- (7,'BAJA',50,10,3,7),
-- (8,'ALTA',200,50,4,8),
-- (9,'ALTA',180,40,4,9),
-- (10,'ALTA',170,40,4,10),
-- (11,'MEDIA',90,25,4,11),
-- (12,'MEDIA',85,20,4,12),
-- (13,'ALTA',160,30,4,13),
-- (14,'MEDIA',75,20,4,14),
-- (15,'ALTA',180,40,4,27),
-- (16,'ALTA',170,35,4,28),
-- (17,'ALTA',300,100,5,15),
-- (18,'ALTA',280,90,5,16),
-- (19,'ALTA',270,90,5,17),
-- (20,'ALTA',350,120,5,18),
-- (21,'MEDIA',200,60,5,19),
-- (22,'MEDIA',190,50,5,20),
-- (23,'BAJA',120,30,5,21),
-- (24,'ALTA',220,60,6,22),
-- (25,'MEDIA',150,40,6,23),
-- (26,'ALTA',210,50,6,26),
-- (27,'MEDIA',130,35,7,24),
-- (28,'MEDIA',140,30,7,25),
-- (29,'ALTA',260,70,8,29),
-- (30,'ALTA',240,60,8,30),
-- (31,'ALTA',230,60,8,31),
-- (32,'MEDIA',150,40,8,32),
-- (33,'MEDIA',140,35,8,33),
-- (34,'ALTA',200,50,8,34),
-- (35,'ALTA',210,50,8,35),
-- (36,'ALTA',300,80,9,36),
-- (37,'ALTA',260,70,9,37),
-- (38,'ALTA',250,70,9,38),
-- (39,'ALTA',280,80,9,39),
-- (40,'MEDIA',160,40,9,40),
-- (41,'ALTA',220,60,9,41),
-- (42,'MEDIA',150,40,9,42)
-- ON CONFLICT (inventario_id) DO NOTHING;

-- INSERT INTO asignaciones
-- (asignacion_id, activo, fecha_fin, fecha_inicio, linea_producto_id, nombre_linea, tienda_id, trabajador_id)
-- VALUES
-- (1,true,null,null,1,'Cerámicas',1,null),
-- (2,true,null,null,2,'Herramientas Manuales',1,null),
-- (3,true,null,null,3,'Materiales de Construcción',1,null),
-- (4,true,null,null,4,'Pinturas y Acabados',1,null),
-- (5,true,null,null,5,'Gasfitería',1,null),
-- (6,true,null,null,6,'Electricidad',1,null),
-- (7,true,null,null,1,'Cerámicas',2,null),
-- (8,true,null,null,2,'Herramientas Manuales',2,null),
-- (9,true,null,null,5,'Gasfitería',2,null)
-- ON CONFLICT (asignacion_id) DO NOTHING;

-- SELECT setval(
--     pg_get_serial_sequence('asignaciones','asignacion_id'),
--     (SELECT COALESCE(MAX(asignacion_id),0) FROM asignaciones)
-- );

-- Insertar almacenes
INSERT IGNORE INTO almacenes (almacen_id, capacidad, nombre, ubicacion)
VALUES
(1,10000,'Almacén Lima','San Borja, Lima'),
(2,12000,'Almacén Lima Norte','Av. Perú 3580, San Martín de Porres, Lima');

-- Insertar tiendas
INSERT IGNORE INTO tiendas (tienda_id, almacen_id, ciudad, nombre, direccion)
VALUES
(1,1,'Lima','Ferreteria Peruana SA - SB','Av. Aviación 2450, San Borja, Lima'),
(2,2,'Lima','Ferretería Peruana SA - SMP','Av. Perú 3580, San Martín de Porres, Lima');

-- Insertar zonas de almacén
INSERT IGNORE INTO zonas_almacen
(zona_almacen_id, categoria, capacidad_maxima, capacidad_actual, almacen_id)
VALUES
(1,'Pisos',600,410,1),
(2,'Paredes',300,180,1),
(3,'Decorativa',200,110,1),
(4,'Herramientas',1400,1060,1),
(5,'Construcción',2200,1710,1),
(6,'Pinturas',800,580,1),
(7,'Acabados',400,270,1),
(8,'Gasfitería',1800,1430,1),
(9,'Electricidad',2000,1620,1);

-- Insertar inventarios
INSERT IGNORE INTO inventarios
(inventario_id, rotacion, stock, stock_min, zona_almacen_id, producto_id)
VALUES
(1,'MEDIA',120,30,1,1),
(2,'ALTA',150,40,1,4),
(3,'ALTA',140,35,1,6),
(4,'MEDIA',100,25,2,2),
(5,'MEDIA',80,20,2,5),
(6,'BAJA',60,15,3,3),
(7,'BAJA',50,10,3,7),
(8,'ALTA',200,50,4,8),
(9,'ALTA',180,40,4,9),
(10,'ALTA',170,40,4,10),
(11,'MEDIA',90,25,4,11),
(12,'MEDIA',85,20,4,12),
(13,'ALTA',160,30,4,13),
(14,'MEDIA',75,20,4,14),
(15,'ALTA',180,40,4,27),
(16,'ALTA',170,35,4,28),
(17,'ALTA',300,100,5,15),
(18,'ALTA',280,90,5,16),
(19,'ALTA',270,90,5,17),
(20,'ALTA',350,120,5,18),
(21,'MEDIA',200,60,5,19),
(22,'MEDIA',190,50,5,20),
(23,'BAJA',120,30,5,21),
(24,'ALTA',220,60,6,22),
(25,'MEDIA',150,40,6,23),
(26,'ALTA',210,50,6,26),
(27,'MEDIA',130,35,7,24),
(28,'MEDIA',140,30,7,25),
(29,'ALTA',260,70,8,29),
(30,'ALTA',240,60,8,30),
(31,'ALTA',230,60,8,31),
(32,'MEDIA',150,40,8,32),
(33,'MEDIA',140,35,8,33),
(34,'ALTA',200,50,8,34),
(35,'ALTA',210,50,8,35),
(36,'ALTA',300,80,9,36),
(37,'ALTA',260,70,9,37),
(38,'ALTA',250,70,9,38),
(39,'ALTA',280,80,9,39),
(40,'MEDIA',160,40,9,40),
(41,'ALTA',220,60,9,41),
(42,'MEDIA',150,40,9,42);

-- Insertar asignaciones
INSERT IGNORE INTO asignaciones
(asignacion_id, activo, fecha_fin, fecha_inicio, linea_producto_id, nombre_linea, tienda_id, trabajador_id)
VALUES
(1,true,null,null,1,'Cerámicas',1,null),
(2,true,null,null,2,'Herramientas Manuales',1,null),
(3,true,null,null,3,'Materiales de Construcción',1,null),
(4,true,null,null,4,'Pinturas y Acabados',1,null),
(5,true,null,null,5,'Gasfitería',1,null),
(6,true,null,null,6,'Electricidad',1,null),
(7,true,null,null,1,'Cerámicas',2,null),
(8,true,null,null,2,'Herramientas Manuales',2,null),
(9,true,null,null,5,'Gasfitería',2,null);