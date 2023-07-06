-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Creato il: Giu 29, 2023 alle 17:49
-- Versione del server: 8.0.32
-- Versione PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db`
--

-- --------------------------------------------------------
-- Dropping tables if they already exist
-- --------------------------------------------------------

DROP TABLE IF EXISTS `transaction_seq`;
DROP TABLE IF EXISTS `transaction`;
DROP TABLE IF EXISTS `solarpanel`;
DROP TABLE IF EXISTS `sellers`;
DROP TABLE IF EXISTS `buyers`;


-- --------------------------------------------------------

--
-- Struttura della tabella `buyers`
--

CREATE TABLE `buyers` (
  `id` int NOT NULL,
  `email` varchar(500) NOT NULL,
  `name` varchar(255) NOT NULL,
  `address` varchar(500) NOT NULL,
  `password` varchar(255) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `battery_capacity` float NOT NULL,
  `battery_id` int NOT NULL,
  `role` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dump dei dati per la tabella `buyers`
--

INSERT INTO `buyers` (`id`, `email`, `name`, `address`, `password`, `status`, `battery_capacity`, `battery_id`, `role`) VALUES
(1, 'pirlo@gmail.com', 'Andrea Pirlo', 'Via del Calcio', '$2a$10$6HKRUkI3LVJnWHaIi4g77.YBF2iSKPIj/mdK7n1X3WqDYuSKGAJsG', 1, 1016.22, 0, 'USER'),
(8, 'luca@gmail.com', 'Luca Rossi', 'Via Roma 2', '$2a$10$tznz0g5D2WDS.08ZXh.A4eXK2GpORhNGl.cwifCNx3AscskaqeLO.', 1, 0, 0, 'USER');

-- --------------------------------------------------------

--
-- Struttura della tabella `sellers`
--

CREATE TABLE `sellers` (
  `id` int NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(500) NOT NULL,
  `address` varchar(1000) NOT NULL,
  `password` varchar(255) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `threshold` int NOT NULL,
  `battery_capacity` float NOT NULL,
  `battery_id` int NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `panels` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dump dei dati per la tabella `sellers`
--

INSERT INTO `sellers` (`id`, `email`, `name`, `address`, `password`, `status`, `threshold`, `battery_capacity`, `battery_id`, `role`, `panels`) VALUES
(1, 'simone@gmail.com', 'Simone Teglia', 'Via Del Serafico', '$2a$10$3BbJCe3kcnTRqQUGLCnjUemJp0BUlgJ5sH77TXotEPeeRJl6IaEo6', 1, 8000, 7999, 4, 'USER', 1),
(2, 'messi@gmail.com', 'Lionel Messi', 'Via Argentina 1', '$2a$10$hoW.0jo2H8Vcy6lxoMNl7OcjDGzlThOUMRHvdozhVYiEZmy9uA5TK', 1, 5000, 4999, 0, 'USER', 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `solarpanel`
--

CREATE TABLE `solarpanel` (
  `id` int NOT NULL,
  `power` int NOT NULL,
  `uid` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- --------------------------------------------------------

--
-- Struttura della tabella `transaction`
--

CREATE TABLE `transaction` (
  `id` int NOT NULL,
  `timestamp` int NOT NULL,
  `amount` int DEFAULT NULL,
  `price` int DEFAULT NULL,
  `buyerID` int DEFAULT NULL,
  `sellerID` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dump dei dati per la tabella `transaction`
--

INSERT INTO `transaction` (`id`, `timestamp`, `amount`, `price`, `buyerID`, `sellerID`) VALUES
(28102, 10, 926, 139, 8, 1),
(28103, 10, 1346, 202, 1, 2),
(28104, 12, 3158, 474, 1, 1),
(28105, 12, 4600, 691, 8, 2),
(29986, 11, 1114, 168, 8, 1),
(29987, 11, 1633, 245, 1, 2),
(29988, 13, 1016, 153, 1, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `transaction_seq`
--

CREATE TABLE `transaction_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `transaction_seq`
--

INSERT INTO `transaction_seq` (`next_val`) VALUES
(30051);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `buyers`
--
ALTER TABLE `buyers`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `sellers`
--
ALTER TABLE `sellers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email_UNIQUE` (`email`);

--
-- Indici per le tabelle `solarpanel`
--
ALTER TABLE `solarpanel`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `buyers`
--
ALTER TABLE `buyers`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT per la tabella `sellers`
--
ALTER TABLE `sellers`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT per la tabella `solarpanel`
--
ALTER TABLE `solarpanel`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `solarpanel`
--
ALTER TABLE `solarpanel`
  ADD CONSTRAINT `Solar Panel foreign key` FOREIGN KEY (`id`) REFERENCES `sellers` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
