-- phpMyAdmin SQL Dump
-- version 4.7.1
-- https://www.phpmyadmin.net/
--
-- Host: sql5.freemysqlhosting.net
-- Generation Time: Dec 14, 2021 at 08:14 PM
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
-- Table structure for table `TRANSACTION`
--

CREATE TABLE `TRANSACTION` (
  `ID` int(11) NOT NULL,
  `TENANT_ID` varchar(100) NOT NULL,
  `CLAIM_ID` varchar(100) NOT NULL,
  `AMOUNT` int(11) DEFAULT NULL,
  `COMMENTS` varchar(4000) DEFAULT NULL,
  `PAYER_ID` varchar(100) DEFAULT NULL,
  `TIME_CREATED` datetime DEFAULT NULL,
  `TRANSACTION_TYPE` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `TRANSACTION`
--

INSERT INTO `TRANSACTION` (`ID`, `TENANT_ID`, `CLAIM_ID`, `AMOUNT`, `COMMENTS`, `PAYER_ID`, `TIME_CREATED`, `TRANSACTION_TYPE`) VALUES
(6, 'c209d08b-0c02-4461-a0ce-7789934aed56', '010e7a87-9931-4da1-9377-e2af2e9a5b77', 200, 'Paid by insurer', 'XX00INSURER00XX', '2021-12-12 01:10:06', 1),
(7, 'c209d08b-0c02-4461-a0ce-7789934aed56', '2988b88d-c3a1-443e-b33a-fb4e7a7207fb', 200, 'Paid by insurer', 'XX00INSURER00XX', '2021-12-12 02:10:11', 1),
(8, 'c209d08b-0c02-4461-a0ce-7789934aed56', '05839cf3-2c29-4451-8b09-c599ced9ac03', 200, 'Paid by insurer', 'XX00INSURER00XX', '2021-12-12 02:13:41', 1),
(9, 'c209d08b-0c02-4461-a0ce-7789934aed56', '05839cf3-2c29-4451-8b09-c599ced9ac03', 200, 'Paid by insurer', 'XX00INSURER00XX', '2021-12-12 02:13:51', 1),
(10, 'c209d08b-0c02-4461-a0ce-7789934aed56', '813915be-f665-4240-8970-1e4f05c2bbde', 200, 'Paid by insurer', 'XX00INSURER00XX', '2021-12-12 02:17:12', 1),
(11, 'c209d08b-0c02-4461-a0ce-7789934aed56', '43b25aa5-fa2c-4d11-8875-ad8bdb98666c', 200, 'Payment from rom INSURER', 'XXXINSURER12HEALTH99XX', '2021-12-12 21:50:12', 1),
(12, 'c209d08b-0c02-4461-a0ce-7789934aed56', '43b25aa5-fa2c-4d11-8875-ad8bdb98666c', 200, 'Payment from rom INSURER', 'XXXINSURER12HEALTH99XX', '2021-12-12 21:51:43', 1),
(13, 'c209d08b-0c02-4461-a0ce-7789934aed56', '43b25aa5-fa2c-4d11-8875-ad8bdb98666c', 200, 'Payment from rom INSURER', 'XXXINSURER12HEALTH99XX', '2021-12-12 21:55:06', 1),
(14, 'c209d08b-0c02-4461-a0ce-7789934aed56', '1bcf9866-8980-49ec-9bc4-8790e283295c', 200, 'Payment from rom INSURER', 'XXXINSURER12HEALTH99XX', '2021-12-12 22:26:45', 1),
(15, 'c209d08b-0c02-4461-a0ce-7789934aed56', '1bcf9866-8980-49ec-9bc4-8790e283295c', 200, 'Payment from rom INSURER', 'XXXINSURER12HEALTH99XX', '2021-12-12 22:27:33', 1),
(16, 'c209d08b-0c02-4461-a0ce-7789934aed56', '1bcf9866-8980-49ec-9bc4-8790e283295c', 200, 'Payment from rom INSURER', 'XXXINSURER12HEALTH99XX', '2021-12-12 22:27:46', 1),
(17, 'c209d08b-0c02-4461-a0ce-7789934aed56', '1bcf9866-8980-49ec-9bc4-8790e283295c', 200, 'Payment from rom INSURER', 'XXXINSURER12HEALTH99XX', '2021-12-12 22:28:18', 1),
(18, 'c209d08b-0c02-4461-a0ce-7789934aed56', '1bcf9866-8980-49ec-9bc4-8790e283295c', 200, 'Payment from rom INSURER', 'XXXINSURER12HEALTH99XX', '2021-12-12 22:30:58', 1),
(19, 'c209d08b-0c02-4461-a0ce-7789934aed56', '1bcf9866-8980-49ec-9bc4-8790e283295c', 200, 'Payment from rom INSURER', 'XXXINSURER12HEALTH99XX', '2021-12-12 22:31:34', 1),
(20, 'c209d08b-0c02-4461-a0ce-7789934aed56', '1bcf9866-8980-49ec-9bc4-8790e283295c', 200, 'Payment from rom INSURER', 'XXXINSURER12HEALTH99XX', '2021-12-12 22:33:08', 1),
(21, 'c209d08b-0c02-4461-a0ce-7789934aed56', '1bcf9866-8980-49ec-9bc4-8790e283295c', 200, 'Payment from rom INSURER', 'XXXINSURER12HEALTH99XX', '2021-12-12 22:35:45', 1),
(22, 'c209d08b-0c02-4461-a0ce-7789934aed56', '1bcf9866-8980-49ec-9bc4-8790e283295c', 200, 'Payment from rom INSURER', 'XXXINSURER12HEALTH99XX', '2021-12-12 22:37:44', 1),
(23, 'c209d08b-0c02-4461-a0ce-7789934aed56', '1bcf9866-8980-49ec-9bc4-8790e283295c', 200, 'Payment from rom INSURER', 'XXXINSURER12HEALTH99XX', '2021-12-12 22:45:37', 1),
(24, 'c209d08b-0c02-4461-a0ce-7789934aed56', '1bcf9866-8980-49ec-9bc4-8790e283295c', 200, 'Payment from rom INSURER', 'XXXINSURER12HEALTH99XX', '2021-12-12 22:47:11', 1),
(25, 'c209d08b-0c02-4461-a0ce-7789934aed56', '1bcf9866-8980-49ec-9bc4-8790e283295c', 200, 'Payment from rom INSURER', 'XXXINSURER12HEALTH99XX', '2021-12-12 22:50:24', 1),
(26, 'c209d08b-0c02-4461-a0ce-7789934aed56', '1bcf9866-8980-49ec-9bc4-8790e283295c', 200, 'Payment from rom INSURER', 'XXXINSURER12HEALTH99XX', '2021-12-12 22:50:33', 1),
(27, 'c209d08b-0c02-4461-a0ce-7789934aed56', '1bcf9866-8980-49ec-9bc4-8790e283295c', 200, 'Payment from rom INSURER', 'XXXINSURER12HEALTH99XX', '2021-12-12 22:51:53', 1),
(28, 'c209d08b-0c02-4461-a0ce-7789934aed56', '1bcf9866-8980-49ec-9bc4-8790e283295c', 200, 'Payment from rom INSURER', 'XXXINSURER12HEALTH99XX', '2021-12-12 22:52:07', 1),
(29, 'c209d08b-0c02-4461-a0ce-7789934aed56', '1bcf9866-8980-49ec-9bc4-8790e283295c', 200, 'Payment from rom INSURER', 'XXXINSURER12HEALTH99XX', '2021-12-12 22:54:46', 1),
(30, 'c209d08b-0c02-4461-a0ce-7789934aed56', '1bcf9866-8980-49ec-9bc4-8790e283295c', 200, 'Payment from rom INSURER', 'XXXINSURER12HEALTH99XX', '2021-12-12 22:56:31', 1),
(31, 'c209d08b-0c02-4461-a0ce-7789934aed56', '1bcf9866-8980-49ec-9bc4-8790e283295c', 200, 'Payment from rom INSURER', 'XXXINSURER12HEALTH99XX', '2021-12-12 22:57:49', 1),
(32, 'c209d08b-0c02-4461-a0ce-7789934aed56', '1bcf9866-8980-49ec-9bc4-8790e283295c', 200, 'Payment from rom INSURER', 'XXXINSURER12HEALTH99XX', '2021-12-12 22:59:19', 1),
(33, 'c209d08b-0c02-4461-a0ce-7789934aed56', '1bcf9866-8980-49ec-9bc4-8790e283295c', 200, 'Payment from rom INSURER', 'XXXINSURER12HEALTH99XX', '2021-12-12 23:00:48', 1),
(34, 'c209d08b-0c02-4461-a0ce-7789934aed56', '1bcf9866-8980-49ec-9bc4-8790e283295c', 200, 'Payment from rom INSURER', 'XXXINSURER12HEALTH99XX', '2021-12-13 01:13:00', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `TRANSACTION`
--
ALTER TABLE `TRANSACTION`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `CLAIM_ID` (`CLAIM_ID`),
  ADD KEY `TENANT_ID` (`TENANT_ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `TRANSACTION`
--
ALTER TABLE `TRANSACTION`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `TRANSACTION`
--
ALTER TABLE `TRANSACTION`
  ADD CONSTRAINT `TRANSACTION_ibfk_2` FOREIGN KEY (`TENANT_ID`) REFERENCES `TENANT` (`ID`),
  ADD CONSTRAINT `TRANSACTION_ibfk_1` FOREIGN KEY (`CLAIM_ID`) REFERENCES `CLAIM` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
