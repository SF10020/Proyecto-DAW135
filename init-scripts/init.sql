CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE
);

CREATE TABLE metodologias (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE proyectos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    usuario_id INT,
    metodologia_id INT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (metodologia_id) REFERENCES metodologias(id) ON DELETE CASCADE
);

CREATE TABLE simulaciones (
    id SERIAL PRIMARY KEY,
    proyecto_id INT NOT NULL,
    tiempo_estimado INT NOT NULL,
    costo_estimado DECIMAL(10,2) NOT NULL,
    calidad_estimada INT NOT NULL,
    fecha_simulacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (proyecto_id) REFERENCES proyectos(id) ON DELETE CASCADE
);

CREATE TABLE decisiones_simulacion (
    id SERIAL PRIMARY KEY,
    simulacion_id INT NOT NULL,
    descripcion TEXT NOT NULL,
    FOREIGN KEY (simulacion_id) REFERENCES simulaciones(id) ON DELETE CASCADE
);

-- DATOS DE PRUEBA
INSERT INTO usuarios (nombre, correo)
VALUES
    ('Ana Pérez', 'ana.perez@example.com'),
    ('Carlos López', 'carlos.lopez@example.com');

INSERT INTO metodologias (nombre)
VALUES
    ('Scrum'),
    ('Kanban'),
    ('Waterfall');

INSERT INTO proyectos (nombre, usuario_id, metodologia_id)
VALUES
    ('Sistema de Inventario', 1, 1),
    ('App de Reservas', 2, 2);

INSERT INTO simulaciones (proyecto_id, tiempo_estimado, costo_estimado, calidad_estimada)
VALUES
    (1, 30, 25000.00, 85),
    (2, 45, 32000.00, 90);

INSERT INTO decisiones_simulacion (simulacion_id, descripcion)
VALUES
    (1, 'Asignar un equipo de 5 desarrolladores en modalidad Scrum.'),
    (1, 'Reducir alcance de funcionalidades para priorizar tiempo.'),
    (2, 'Agregar pruebas automatizadas para mejorar calidad.'),
    (2, 'Implementar integración continua con Docker.');