DROP DATABASE IF EXISTS eventos;
CREATE DATABASE eventos
CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

USE eventos;

-- =========================
-- TABLA: ROLES
-- =========================
CREATE TABLE rol (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
);

-- =========================
-- TABLA: USUARIOS
-- =========================
CREATE TABLE usuario (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    email VARCHAR(200) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol_id BIGINT UNSIGNED NOT NULL,
    
    -- AQUÍ SE AGREGA LA COLUMNA FOTO
    foto VARCHAR(255) DEFAULT 'default.png', 
    
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (rol_id) REFERENCES rol(id)
);

-- =========================
-- TABLA: EVENTOS
-- =========================
CREATE TABLE evento (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    fecha DATE NOT NULL,
    capacidad INT NOT NULL,
    activo BOOLEAN DEFAULT TRUE
);

-- =========================
-- TABLA: RESERVAS (IMPORTANTE)
-- =========================
CREATE TABLE reserva (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT UNSIGNED NOT NULL,
    evento_id BIGINT UNSIGNED NOT NULL,
    fecha_reserva TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (evento_id) REFERENCES evento(id) ON DELETE CASCADE
);

-- =========================
-- DATOS INICIALES
-- =========================

-- ROLES
INSERT INTO rol (nombre) VALUES 
('ADMIN'),
('ORGANIZADOR'),
('CLIENTE');

-- USUARIOS (password cambiado a texto plano para la prueba)
-- Nota: La columna 'foto' tomará el valor 'default.png' automáticamente para estos usuarios.
INSERT INTO usuario (nombre, email, password, rol_id) VALUES 
('Admin', 'admin@email.com', '12345', 1),
('Organizador', 'org@email.com', '12345', 2),
('Cliente', 'cliente@email.com', '12345', 3);

-- EVENTOS
INSERT INTO evento (nombre, descripcion, fecha, capacidad, activo) VALUES 
('Conferencia Tech', 'Evento de tecnologia', '2026-05-10', 100, TRUE),
('Taller Web', 'Curso practico', '2026-06-15', 50, TRUE),
('Seminario IA', 'Inteligencia Artificial', '2026-07-20', 80, TRUE);

-- RESERVAS
INSERT INTO reserva (usuario_id, evento_id) VALUES 
(3, 1),
(3, 2),
(2, 1);