INSERT INTO roles (rol_id, nombre)
VALUES
    (2,'JEFE_DE_LINEA'),
    (3,'ADMINISTRADOR_DE_TIENDA'),
    (4,'ALMACENERO')
ON CONFLICT (rol_id) DO NOTHING;

SELECT setval(
    pg_get_serial_sequence('roles','rol_id'),
    (SELECT COALESCE(MAX(rol_id),0) FROM roles)
);