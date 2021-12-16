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
-- Table structure for table `CLAIM_CPT`
--

CREATE TABLE `CLAIM_CPT` (
  `CLAIM_ID` varchar(100) NOT NULL,
  `CPT_ID` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `CLAIM_CPT`
--

INSERT INTO `CLAIM_CPT` (`CLAIM_ID`, `CPT_ID`) VALUES
('010e7a87-9931-4da1-9377-e2af2e9a5b77', '902f97d5-3aa8-4a47-b830-28a69f06b4a9'),
('010e7a87-9931-4da1-9377-e2af2e9a5b77', '8d83a259-0894-431b-82c5-0b77ed495cb7'),
('2988b88d-c3a1-443e-b33a-fb4e7a7207fb', 'caff788c-eccf-4632-83bc-f70f458d232b'),
('2988b88d-c3a1-443e-b33a-fb4e7a7207fb', '097b201b-7357-4dd3-b003-49919b127c53'),
('05839cf3-2c29-4451-8b09-c599ced9ac03', 'caff788c-eccf-4632-83bc-f70f458d232b'),
('05839cf3-2c29-4451-8b09-c599ced9ac03', '097b201b-7357-4dd3-b003-49919b127c53'),
('813915be-f665-4240-8970-1e4f05c2bbde', 'caff788c-eccf-4632-83bc-f70f458d232b'),
('813915be-f665-4240-8970-1e4f05c2bbde', '097b201b-7357-4dd3-b003-49919b127c53'),
('56ffa941-6401-4f53-9f8d-dfb2211ae0d2', '30bf97c8-6b8e-406d-82ae-e9032dacf729'),
('56ffa941-6401-4f53-9f8d-dfb2211ae0d2', '92018459-2056-46a8-9b26-5e8ed80d11ae'),
('78a976fb-0020-428c-b63a-4dd17d76774d', '30bf97c8-6b8e-406d-82ae-e9032dacf729'),
('78a976fb-0020-428c-b63a-4dd17d76774d', '92018459-2056-46a8-9b26-5e8ed80d11ae'),
('1f18c226-00ab-4fee-92fe-fea2f48989ed', '30bf97c8-6b8e-406d-82ae-e9032dacf729'),
('1f18c226-00ab-4fee-92fe-fea2f48989ed', '92018459-2056-46a8-9b26-5e8ed80d11ae'),
('1bcf9866-8980-49ec-9bc4-8790e283295c', '30bf97c8-6b8e-406d-82ae-e9032dacf729'),
('1bcf9866-8980-49ec-9bc4-8790e283295c', '92018459-2056-46a8-9b26-5e8ed80d11ae'),
('7fd5492c-5b44-44b3-b792-1ed5f7324cbf', '30bf97c8-6b8e-406d-82ae-e9032dacf729'),
('7fd5492c-5b44-44b3-b792-1ed5f7324cbf', '92018459-2056-46a8-9b26-5e8ed80d11ae');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `CLAIM_CPT`
--
ALTER TABLE `CLAIM_CPT`
  ADD KEY `CLAIM_ID` (`CLAIM_ID`),
  ADD KEY `CPT_ID` (`CPT_ID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `CLAIM_CPT`
--
ALTER TABLE `CLAIM_CPT`
  ADD CONSTRAINT `CLAIM_CPT_ibfk_1` FOREIGN KEY (`CLAIM_ID`) REFERENCES `CLAIM` (`ID`),
  ADD CONSTRAINT `CLAIM_CPT_ibfk_2` FOREIGN KEY (`CPT_ID`) REFERENCES `CPT` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
