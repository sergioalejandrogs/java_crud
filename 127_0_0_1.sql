-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 25-03-2022 a las 14:21:11
-- Versión del servidor: 10.4.22-MariaDB
-- Versión de PHP: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `prueba`
--
DROP DATABASE IF EXISTS `prueba`;
CREATE DATABASE IF NOT EXISTS `prueba` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `prueba`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `articulos`
--

CREATE TABLE `articulos` (
  `id_articulo` int(11) NOT NULL,
  `titulo` varchar(60) DEFAULT NULL,
  `descripcion` varchar(300) DEFAULT NULL,
  `precio` int(11) DEFAULT NULL,
  `foto` varchar(200) DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `oldfoto` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `articulos`
--

INSERT INTO `articulos` (`id_articulo`, `titulo`, `descripcion`, `precio`, `foto`, `id_usuario`, `oldfoto`) VALUES
(1, 'Bicicleta', 'BICICLETA CHECKPOINT ALR 5 2021', 2500000, 'images/photos/Bicicleta_bike.jpg', NULL, 'images/photos/Bicicleta_bike.jpg'),
(2, 'Macbook', 'Pantalla: 6.4, 1080 x 2400 pixels Procesador: Google Tensor RAM: 8GB  Almacenamiento: 128GB/256GB Expansion: sin microSD.', 3500000, 'images/photos/Macbook_macbook.jpg', NULL, 'images/photos/Macbook_macbook.jpg'),
(13, 'Smarthphone', 'Pixel 6', 456654, 'images/photos/Smarthphone_pixel6.jpg', NULL, 'images/photos/Smarthphone_pixel6.jpg');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id_usuario` int(11) NOT NULL,
  `nombre` varchar(60) DEFAULT NULL,
  `apellido` varchar(60) CHARACTER SET utf8 DEFAULT NULL,
  `correo` varchar(60) DEFAULT NULL,
  `edad` int(11) DEFAULT NULL,
  `foto` varchar(250) DEFAULT NULL,
  `oldfoto` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_usuario`, `nombre`, `apellido`, `correo`, `edad`, `foto`, `oldfoto`) VALUES
(1, 'Fulanito', 'Detal', 'guayando@guayando.com', 23, 'images/photos/Fulanito_Detal_bob.jpg', 'images/photos/Fulanito_Detal_bob.jpg'),
(2, 'Sergio', 'Gonzalez', 'sergioalejandrogs@gmail.com', 27, 'images/photos/Sergio_Gonzalez_20131102_142648-COLLAGE.jpg', 'images/photos/Sergio_Gonzalez_20131102_142648-COLLAGE.jpg'),
(10, 'Stromae', 'Maestro', 'stromae@maestro.com', 35, 'images/photos/Stromae_Maestro_py.jpg', 'images/photos/Stromae_Maestro_py.jpg');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `id_venta` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `id_articulo` int(11) NOT NULL,
  `fecha_venta` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `ventas`
--

INSERT INTO `ventas` (`id_venta`, `id_usuario`, `id_articulo`, `fecha_venta`) VALUES
(3, 1, 1, '2022-03-08'),
(4, 2, 2, '2022-02-22');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `articulos`
--
ALTER TABLE `articulos`
  ADD PRIMARY KEY (`id_articulo`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id_usuario`);

--
-- Indices de la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD PRIMARY KEY (`id_venta`),
  ADD KEY `id_usuario` (`id_usuario`),
  ADD KEY `id_articulo` (`id_articulo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `articulos`
--
ALTER TABLE `articulos`
  MODIFY `id_articulo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `id_venta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `articulos`
--
ALTER TABLE `articulos`
  ADD CONSTRAINT `articulos_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`);

--
-- Filtros para la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD CONSTRAINT `ventas_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`),
  ADD CONSTRAINT `ventas_ibfk_2` FOREIGN KEY (`id_articulo`) REFERENCES `articulos` (`id_articulo`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
