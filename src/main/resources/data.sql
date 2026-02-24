-- =========================================================
-- Datos de prueba (H2) - Librería Coy
-- NOTA IMPORTANTE:
--   Las imágenes se sirven desde:
--     src/main/resources/static/portadas
--     src/main/resources/static/papeleria
--     src/main/resources/static/geek
--   Por eso, en BD guardamos SOLO el nombre del archivo (sin /portadas/...).
-- =========================================================

-- Libros
INSERT IGNORE INTO libros (titulo, autor, categoria, isbn, precio, portada, tipo) VALUES
('El segundo sexo', 'Simone de Beauvoir', 'Feminismo', '978-84-670-1000-1', 22.00, 'Feminismo_ElSegundoSexo.jpg', 'Libro'),
('Todos deberíamos ser feministas', 'Chimamanda Ngozi Adichie', 'Feminismo', '978-84-670-1000-2', 10.00, 'Feminismo_TodosDeberiamosSerFeministas.jpg', 'Libro'),
('La mujer invisible', 'Caroline Criado Pérez', 'Feminismo', '978-84-670-1000-3', 20.00, 'Feminismo_LaMujerInvisible.jpg', 'Libro'),
('El cuento de la criada', 'Margaret Atwood', 'Distopía feminista', '978-84-670-1000-4', 19.00, 'DistopiaFeminista_ElCuentoDeLaCriada.jpg', 'Libro'),
('Cien años de soledad', 'Gabriel García Márquez', 'Realismo mágico', '978-84-670-1000-5', 22.00, 'CienAñosDeSoledad.jpg', 'Libro'),
('El Quijote', 'Miguel de Cervantes', 'Novela', '978-84-670-1000-6', 25.99, 'ElQuijote.jpg', 'Libro'),
('1984', 'George Orwell', 'Distopía', '978-84-670-1000-7', 18.50, '1984.jpg', 'Libro');

-- Cómics
-- Inserta sólo si no existe un cómic con el mismo título (evita duplicados en reinicios)
INSERT INTO comics (titulo, autor, categoria, isbn, precio, portada, tipo)
SELECT 'Batman: Año Uno', 'Frank Miller', 'Superhéroes', '978-84-670-2000-1', 15.00, 'BatmanAñoUno.jpg', 'Comic' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM comics WHERE titulo = 'Batman: Año Uno');

INSERT INTO comics (titulo, autor, categoria, isbn, precio, portada, tipo)
SELECT 'Watchmen', 'Alan Moore', 'Novela gráfica', '978-84-670-2000-2', 19.99, 'Watchmen.jpg', 'Comic' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM comics WHERE titulo = 'Watchmen');

-- Papelería
INSERT IGNORE INTO papeleria (nombre, categoria, marca, tipo, precio, imagen) VALUES
('Agenda 2026', 'Organización', 'Genérica', 'Papeleria', 12.99, 'Agenda2026.jpg'),
('Bolígrafo BIC', 'Escritura', 'BIC', 'Papeleria', 1.25, 'BoligrafoBIC.jpg'),
('Carpeta de cartón', 'Archivo', 'Genérica', 'Papeleria', 2.50, 'CarpetaCarton.jpg'),
('Cuaderno A5 Oxford', 'Cuadernos', 'Oxford', 'Papeleria', 6.95, 'CuadernoA5Oxford.jpg'),
('Cuaderno Harry Potter', 'Cuadernos', 'Harry Potter', 'Papeleria', 9.95, 'CuadernoHarryPotter.jpg'),
('Set de notas adhesivas', 'Oficina', 'Genérico', 'Papeleria', 3.99, 'SetNotas.jpg'),
('Stabilo Pastel', 'Escritura', 'Stabilo', 'Papeleria', 8.99, 'StabiloPastel.jpg');



-- Geek
-- Inserta sólo si no existe un producto con el mismo nombre (evita duplicados en reinicios)
INSERT INTO productos_geek (nombre, categoria, franquicia, tipo, precio, stock, imagen)
SELECT 'Camiseta Stranger Things', 'Ropa', 'Stranger Things', 'Geek', 19.90, 20, 'CamisetaST.jpg' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM productos_geek WHERE nombre = 'Camiseta Stranger Things');

INSERT INTO productos_geek (nombre, categoria, franquicia, tipo, precio, stock, imagen)
SELECT 'Colgante Reliquias', 'Accesorios', 'Harry Potter', 'Geek', 9.90, 20, 'ColganteReliquias.jpg' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM productos_geek WHERE nombre = 'Colgante Reliquias');

INSERT INTO productos_geek (nombre, categoria, franquicia, tipo, precio, stock, imagen)
SELECT 'Figura Spider-Man', 'Figuras', 'Marvel', 'Geek', 24.90, 20, 'FiguraSpiderman.jpg' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM productos_geek WHERE nombre = 'Figura Spider-Man');

INSERT INTO productos_geek (nombre, categoria, franquicia, tipo, precio, stock, imagen)
SELECT 'Funko Harry', 'Figuras', 'Harry Potter', 'Geek', 14.90, 20, 'FunkoHarry.jpg' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM productos_geek WHERE nombre = 'Funko Harry');

INSERT INTO productos_geek (nombre, categoria, franquicia, tipo, precio, stock, imagen)
SELECT 'Lampara Pokeball', 'Decoración', 'Pokémon', 'Geek', 29.90, 20, 'LamparaPokeball.jpg' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM productos_geek WHERE nombre = 'Lampara Pokeball');

INSERT INTO productos_geek (nombre, categoria, franquicia, tipo, precio, stock, imagen)
SELECT 'Poster Mandalorian', 'Pósters', 'Star Wars', 'Geek', 7.90, 20, 'PosterMandalorian.jpg' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM productos_geek WHERE nombre = 'Poster Mandalorian');

INSERT INTO productos_geek (nombre, categoria, franquicia, tipo, precio, stock, imagen)
SELECT 'Taza Groot', 'Hogar', 'Marvel', 'Geek', 12.90, 20, 'TazaGroot.jpg' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM productos_geek WHERE nombre = 'Taza Groot');

INSERT IGNORE INTO blog_posts (titulo, slug, resumen, content_path, publicado)
VALUES
(
  'Bienvenida al blog de Librería Coy',
  'bienvenida-blog-libreria-coy',
  'Arrancamos este espacio para compartir reseñas, novedades y recomendaciones.',
  'bienvenida.html',
  true
);
