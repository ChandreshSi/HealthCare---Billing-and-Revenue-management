-- phpMyAdmin SQL Dump
-- version 4.7.1
-- https://www.phpmyadmin.net/
--
-- Host: sql5.freemysqlhosting.net
-- Generation Time: Dec 12, 2021 at 08:57 PM
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
-- Table structure for table `CLAIM_ICD`
--

CREATE TABLE `CLAIM_ICD` (
  `CLAIM_ID` varchar(100) NOT NULL,
  `ICD_CODE` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `CLAIM_ICD`
--

INSERT INTO `CLAIM_ICD` (`CLAIM_ID`, `ICD_CODE`) VALUES
('010e7a87-9931-4da1-9377-e2af2e9a5b77', 'A00.1'),
('010e7a87-9931-4da1-9377-e2af2e9a5b77', 'A00.0'),
('2988b88d-c3a1-443e-b33a-fb4e7a7207fb', 'C00.1'),
('2988b88d-c3a1-443e-b33a-fb4e7a7207fb', 'C00.3'),
('05839cf3-2c29-4451-8b09-c599ced9ac03', 'C00.1'),
('05839cf3-2c29-4451-8b09-c599ced9ac03', 'C00.3'),
('813915be-f665-4240-8970-1e4f05c2bbde', 'C00.1'),
('813915be-f665-4240-8970-1e4f05c2bbde', 'C00.3'),
('56ffa941-6401-4f53-9f8d-dfb2211ae0d2', 'M00.0'),
('56ffa941-6401-4f53-9f8d-dfb2211ae0d2', 'M00.1'),
('56ffa941-6401-4f53-9f8d-dfb2211ae0d2', 'M00.2'),
('78a976fb-0020-428c-b63a-4dd17d76774d', 'M00.0'),
('78a976fb-0020-428c-b63a-4dd17d76774d', 'M00.1'),
('78a976fb-0020-428c-b63a-4dd17d76774d', 'M00.2'),
('1f18c226-00ab-4fee-92fe-fea2f48989ed', 'M00.0'),
('1f18c226-00ab-4fee-92fe-fea2f48989ed', 'M00.1'),
('1f18c226-00ab-4fee-92fe-fea2f48989ed', 'M00.2'),
('1bcf9866-8980-49ec-9bc4-8790e283295c', 'M00.0'),
('1bcf9866-8980-49ec-9bc4-8790e283295c', 'M00.1'),
('1bcf9866-8980-49ec-9bc4-8790e283295c', 'M00.2'),
('7fd5492c-5b44-44b3-b792-1ed5f7324cbf', 'M00.0'),
('7fd5492c-5b44-44b3-b792-1ed5f7324cbf', 'M00.1'),
('7fd5492c-5b44-44b3-b792-1ed5f7324cbf', 'M00.2');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `CLAIM_ICD`
--
ALTER TABLE `CLAIM_ICD`
  ADD KEY `CLAIM_ID` (`CLAIM_ID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `CLAIM_ICD`
--
ALTER TABLE `CLAIM_ICD`
  ADD CONSTRAINT `CLAIM_ICD_ibfk_1` FOREIGN KEY (`CLAIM_ID`) REFERENCES `CLAIM` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
