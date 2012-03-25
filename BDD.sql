-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le : Dim 25 Mars 2012 à 18:19
-- Version du serveur: 5.5.16
-- Version de PHP: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `projetjee`
--
CREATE DATABASE `projetjee` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `projetjee`;

-- --------------------------------------------------------

--
-- Structure de la table `amitie`
--

CREATE TABLE IF NOT EXISTS `amitie` (
  `idUser` int(11) NOT NULL,
  `idAmi` int(11) NOT NULL,
  PRIMARY KEY (`idUser`,`idAmi`),
  KEY `idAmi` (`idAmi`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `amitie`
--

INSERT INTO `amitie` (`idUser`, `idAmi`) VALUES
(1, 2);

-- --------------------------------------------------------

--
-- Structure de la table `file`
--

CREATE TABLE IF NOT EXISTS `file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `taille` double NOT NULL,
  `idUser` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idUser` (`idUser`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `file`
--

INSERT INTO `file` (`id`, `nom`, `taille`, `idUser`) VALUES
(1, 'f1', 10, 2),
(2, 'f2', 20, 2);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `motDePasse` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

--
-- Contenu de la table `user`
--

INSERT INTO `user` (`id`, `nom`, `prenom`, `email`, `motDePasse`) VALUES
(1, 'aa', 'aa', 'aa', 'aa'),
(2, 'bb', 'bb', 'bb', 'bb'),
(3, 'cc', 'cc', 'cc', 'cc'),
(4, 'gou', 'souf', 'aichane.souf@gmail.com', 'souf'),
(5, 'GOUCHENE', 'Malika', 'gouchen.m@gmail.com', 'gouchma'),
(6, 'GOUCHENE', 'Malika', 'gouchen.m@gmail.com', 'gouchma'),
(7, 'GOUCHENE', 'hh', 'gouchen.m@gmail.com', 'gouchma'),
(8, 'AICHANE', 'soufiane', 'email@', 'pass'),
(9, 'AICHANE', 'soufiane', 'email@', 'pass'),
(10, 'AICHANE', 'soufiane', 'email@', 'pass'),
(11, 'AICHANE', 'soufiane', 'email@', 'pass'),
(12, 'aaa', 'aaa', 'aaa', 'aaa'),
(13, 'AICHANE', 'Soufiane', 'aichane.souf@gmail.com', '3310');

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `amitie`
--
ALTER TABLE `amitie`
  ADD CONSTRAINT `amitie_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `amitie_ibfk_2` FOREIGN KEY (`idAmi`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `file`
--
ALTER TABLE `file`
  ADD CONSTRAINT `file_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `user` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
