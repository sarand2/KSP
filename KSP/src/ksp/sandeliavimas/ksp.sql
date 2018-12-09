-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 09, 2018 at 09:19 PM
-- Server version: 10.1.37-MariaDB
-- PHP Version: 7.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ksp`
--

-- --------------------------------------------------------

--
-- Table structure for table `automobiliai`
--

DROP TABLE IF EXISTS `automobiliai`;
CREATE TABLE `automobiliai` (
  `id` int(11) NOT NULL,
  `gamintojas` varchar(20) NOT NULL,
  `modelis` varchar(20) NOT NULL,
  `gam_metai` date NOT NULL,
  `numeris` varchar(6) NOT NULL,
  `maks_apkrova` double(6,2) NOT NULL,
  `kategorija` enum('B','BE','C1','C1E') NOT NULL,
  `stovejimo_vieta` varchar(50) NOT NULL,
  `kuras` varchar(15) NOT NULL,
  `darb_turis` double(6,2) NOT NULL,
  `fk_marsrutas` int(3) NOT NULL DEFAULT '-1',
  `fk_kurjeris` int(3) NOT NULL DEFAULT '-1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `automobiliai`
--

INSERT INTO `automobiliai` (`id`, `gamintojas`, `modelis`, `gam_metai`, `numeris`, `maks_apkrova`, `kategorija`, `stovejimo_vieta`, `kuras`, `darb_turis`, `fk_marsrutas`, `fk_kurjeris`) VALUES
(2, 'Volkswagen', 'Crafter', '2011-01-15', 'KSP001', 750.00, 'B', 'Jonavos g. 50, Kaunas', 'Dyzelinas', 2499.00, -1, -1),
(3, 'Volkswagen', 'Crafter', '2014-06-09', 'KSP002', 1250.00, 'BE', 'Jonavos g. 50, Kaunas', 'Dyzelinas', 2499.00, -1, -1),
(4, 'Mercedes-benz', 'Sprinter', '2012-06-09', 'KSP003', 3000.00, 'C1', 'Jonavos g. 50, Kaunas', 'Dyzelinas', 2200.00, -1, -1),
(5, 'Mercedes-benz', 'Sprinter', '2015-04-08', 'KSP004', 3800.00, 'C1E', 'Jonavos g. 50, Kaunas', 'Dyzelinas', 2999.00, 7, 1);

-- --------------------------------------------------------

--
-- Table structure for table `kategorijos`
--

DROP TABLE IF EXISTS `kategorijos`;
CREATE TABLE `kategorijos` (
  `id` int(11) NOT NULL,
  `pavadinimas` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `kategorijos`
--

INSERT INTO `kategorijos` (`id`, `pavadinimas`) VALUES
(1, 'Telefonai'),
(2, 'Kompiuteriai'),
(3, 'Žaidimai'),
(4, 'Rūbai'),
(5, 'Sportui'),
(6, 'Baldai'),
(7, 'Buitinė technika');

-- --------------------------------------------------------

--
-- Table structure for table `kurjeriai`
--

DROP TABLE IF EXISTS `kurjeriai`;
CREATE TABLE `kurjeriai` (
  `id` int(11) NOT NULL,
  `asmens_kodas` varchar(11) NOT NULL,
  `vardas` varchar(15) NOT NULL,
  `pavarde` varchar(15) NOT NULL,
  `auto_kategorija` enum('B','BE','C1','C1E') NOT NULL,
  `buvimo_vieta` varchar(80) NOT NULL DEFAULT 'Studentu g. 50, Kaunas',
  `fk_automobilis` int(3) NOT NULL DEFAULT '-1',
  `fk_marsrutas` int(3) NOT NULL DEFAULT '-1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kurjeriai`
--

INSERT INTO `kurjeriai` (`id`, `asmens_kodas`, `vardas`, `pavarde`, `auto_kategorija`, `buvimo_vieta`, `fk_automobilis`, `fk_marsrutas`) VALUES
(1, '39101012514', 'Karolis', 'Dumbauskas', 'C1E', 'Studentu g. 50, Kaunas', 5, 7);

-- --------------------------------------------------------

--
-- Table structure for table `marsrutai`
--

DROP TABLE IF EXISTS `marsrutai`;
CREATE TABLE `marsrutai` (
  `id` int(3) NOT NULL,
  `data` date NOT NULL,
  `pradine_vieta` varchar(50) NOT NULL DEFAULT 'nenustatyta',
  `bendras_svoris` double(6,2) NOT NULL,
  `fk_uzsakytos_prekes` varchar(30) NOT NULL,
  `fk_kurjeris` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `marsrutai`
--

INSERT INTO `marsrutai` (`id`, `data`, `pradine_vieta`, `bendras_svoris`, `fk_uzsakytos_prekes`, `fk_kurjeris`) VALUES
(7, '2018-12-06', 'Studentu g. 50, Kaunas', 750.00, '1-2-3-4', 1);

-- --------------------------------------------------------

--
-- Table structure for table `prekes`
--

DROP TABLE IF EXISTS `prekes`;
CREATE TABLE `prekes` (
  `kodas` varchar(50) NOT NULL,
  `pavadinimas` varchar(255) NOT NULL,
  `kategorija` int(11) NOT NULL,
  `prekes_zenklas` varchar(255) NOT NULL,
  `kilmes_salis` varchar(50) NOT NULL,
  `svoris` double(6,2) NOT NULL,
  `ilgis` double(6,2) NOT NULL,
  `plotis` double(6,2) NOT NULL,
  `aukstis` double(6,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `prekes`
--

INSERT INTO `prekes` (`kodas`, `pavadinimas`, `kategorija`, `prekes_zenklas`, `kilmes_salis`, `svoris`, `ilgis`, `plotis`, `aukstis`) VALUES
('1', 'Logitech M706', 2, 'Logitech', 'Vokietija', 0.20, 0.25, 0.15, 0.10),
('2', 'Džemperis be užtrauktuko', 4, 'Proud', 'Kinija', 0.20, 0.40, 0.40, 0.20),
('3', 'Samsung protective case', 1, 'Strong', 'Kinija', 0.10, 0.30, 0.20, 0.05),
('5', 'Krepšinio kamuolys', 5, 'Spalding', 'Kinija', 0.20, 0.50, 0.50, 0.50),
('6', 'Šaldytuvas', 7, 'Beco', 'Vokietija', 50.00, 1.00, 1.00, 1.00);

-- --------------------------------------------------------

--
-- Table structure for table `sandeliai`
--

DROP TABLE IF EXISTS `sandeliai`;
CREATE TABLE `sandeliai` (
  `id` int(11) NOT NULL,
  `talpa` int(11) NOT NULL,
  `adresas` varchar(255) NOT NULL,
  `miestas` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `sandelio_prekes`
--

DROP TABLE IF EXISTS `sandelio_prekes`;
CREATE TABLE `sandelio_prekes` (
  `id` int(11) NOT NULL,
  `sandelio_id` int(11) NOT NULL,
  `prekes_kodas` varchar(50) NOT NULL,
  `kaina` double(6,2) NOT NULL,
  `kiekis` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `uzsakytos_prekes`
--

DROP TABLE IF EXISTS `uzsakytos_prekes`;
CREATE TABLE `uzsakytos_prekes` (
  `id` int(3) NOT NULL,
  `svoris` double(6,2) NOT NULL,
  `kiekis` int(4) NOT NULL,
  `pristatymo_adresas` varchar(50) CHARACTER SET utf8 COLLATE utf8_lithuanian_ci NOT NULL,
  `fk_preke` varchar(50) NOT NULL,
  `fk_vartotojas` int(3) NOT NULL DEFAULT '-1',
  `fk_marsrutas` int(3) NOT NULL DEFAULT '-1',
  `fk_automobilis` int(3) NOT NULL DEFAULT '-1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `uzsakytos_prekes`
--

INSERT INTO `uzsakytos_prekes` (`id`, `svoris`, `kiekis`, `pristatymo_adresas`, `fk_preke`, `fk_vartotojas`, `fk_marsrutas`, `fk_automobilis`) VALUES
(1, 10.00, 10, 'Gedimino pr. 11, 01034 Vilnius', '1', 1, 7, 5),
(2, 30.00, 2, 'Taikos pr. 74, 44029 Kaunas', '2', 2, 7, 5),
(3, 500.00, 1, 'Prancūzų g. 76, 44037 Kaunas', '3', 3, 7, 5),
(4, 90.00, 1, 'Medelyno g. 23, 62100 Alytus', '5', 5, 7, 5),
(5, 3100.00, 1, 'Vaižganto g. 13, Plungė', '6', 6, -1, -1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `automobiliai`
--
ALTER TABLE `automobiliai`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `kategorijos`
--
ALTER TABLE `kategorijos`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `kurjeriai`
--
ALTER TABLE `kurjeriai`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `marsrutai`
--
ALTER TABLE `marsrutai`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `prekes`
--
ALTER TABLE `prekes`
  ADD PRIMARY KEY (`kodas`),
  ADD KEY `fkc_kategorija` (`kategorija`);

--
-- Indexes for table `sandeliai`
--
ALTER TABLE `sandeliai`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sandelio_prekes`
--
ALTER TABLE `sandelio_prekes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fkc_sandeliai` (`sandelio_id`),
  ADD KEY `fkc_prekes` (`prekes_kodas`);

--
-- Indexes for table `uzsakytos_prekes`
--
ALTER TABLE `uzsakytos_prekes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fkc_preke` (`fk_preke`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `automobiliai`
--
ALTER TABLE `automobiliai`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `kategorijos`
--
ALTER TABLE `kategorijos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `kurjeriai`
--
ALTER TABLE `kurjeriai`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `marsrutai`
--
ALTER TABLE `marsrutai`
  MODIFY `id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `sandeliai`
--
ALTER TABLE `sandeliai`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sandelio_prekes`
--
ALTER TABLE `sandelio_prekes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `uzsakytos_prekes`
--
ALTER TABLE `uzsakytos_prekes`
  MODIFY `id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `prekes`
--
ALTER TABLE `prekes`
  ADD CONSTRAINT `fkc_kategorija` FOREIGN KEY (`kategorija`) REFERENCES `kategorijos` (`id`);

--
-- Constraints for table `sandelio_prekes`
--
ALTER TABLE `sandelio_prekes`
  ADD CONSTRAINT `fkc_prekes` FOREIGN KEY (`prekes_kodas`) REFERENCES `prekes` (`kodas`),
  ADD CONSTRAINT `fkc_sandeliai` FOREIGN KEY (`sandelio_id`) REFERENCES `sandeliai` (`id`);

--
-- Constraints for table `uzsakytos_prekes`
--
ALTER TABLE `uzsakytos_prekes`
  ADD CONSTRAINT `fkc_preke` FOREIGN KEY (`fk_preke`) REFERENCES `prekes` (`kodas`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
