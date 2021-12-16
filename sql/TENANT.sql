-- phpMyAdmin SQL Dump
-- version 4.7.1
-- https://www.phpmyadmin.net/
--
-- Host: sql5.freemysqlhosting.net
-- Generation Time: Dec 12, 2021 at 08:59 PM
-- Server version: 5.5.62-0ubuntu0.14.04.1
-- PHP Version: 7.0.33-0ubuntu0.16.04.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sql5457498`
--

-- --------------------------------------------------------

--
-- Table structure for table `TENANT`
--

CREATE TABLE `TENANT` (
  `ID` varchar(100) NOT NULL,
  `NAME` varchar(500) NOT NULL,
  `ADDITIONAL_INFO` varchar(4000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `TENANT`
--

INSERT INTO `TENANT` (`ID`, `NAME`, `ADDITIONAL_INFO`) VALUES
('c209d08b-0c02-4461-a0ce-7789934aed56', 'CS673', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `TENANT`
--
ALTER TABLE `TENANT`
  ADD PRIMARY KEY (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
