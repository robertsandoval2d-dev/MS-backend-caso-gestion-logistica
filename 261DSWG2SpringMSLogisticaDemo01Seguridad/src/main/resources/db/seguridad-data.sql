-- INSERT INTO roles (rol_id, nombre)
-- VALUES
--     (2,'JEFE_DE_LINEA'),
--     (3,'ADMINISTRADOR_DE_TIENDA'),
--     (4,'ALMACENERO')
-- ON CONFLICT (rol_id) DO NOTHING;

-- SELECT setval(
--     pg_get_serial_sequence('roles','rol_id'),
--     (SELECT COALESCE(MAX(rol_id),0) FROM roles)
-- );

IF NOT EXISTS (SELECT 1 FROM roles WHERE nombre = 'JEFE_DE_LINEA')
    INSERT INTO roles (nombre) VALUES ('JEFE_DE_LINEA');

IF NOT EXISTS (SELECT 1 FROM roles WHERE nombre = 'ADMINISTRADOR_DE_TIENDA')
    INSERT INTO roles (nombre) VALUES ('ADMINISTRADOR_DE_TIENDA');

IF NOT EXISTS (SELECT 1 FROM roles WHERE nombre = 'ALMACENERO')
    INSERT INTO roles (nombre) VALUES ('ALMACENERO');