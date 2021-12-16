-- phpMyAdmin SQL Dump
-- version 4.7.1
-- https://www.phpmyadmin.net/
--
-- Host: sql5.freemysqlhosting.net
-- Generation Time: Dec 12, 2021 at 08:58 PM
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
-- Table structure for table `CPT_GROUP`
--

CREATE TABLE `CPT_GROUP` (
  `ID` varchar(100) NOT NULL,
  `Type` varchar(100) DEFAULT NULL,
  `DESCRIPTION` varchar(4000) DEFAULT NULL,
  `ADDITIONAL_INFO` varchar(4000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `CPT_GROUP`
--

INSERT INTO `CPT_GROUP` (`ID`, `Type`, `DESCRIPTION`, `ADDITIONAL_INFO`) VALUES
('24034dc9-31e8-4151-8fb0-fd8cf3f79692', NULL, 'EPO AND OTHER DIALYSIS-RELATED DRUGS', NULL),
('3a53e75b-1596-42b8-a897-c909cea08f4c', NULL, 'The physician self-referral prohibition does not apply to the following immunization and vaccine codes if they satisfy the conditions in §411.355(h):', NULL),
('455b0dcb-8adf-4770-9404-8a80d3fe7d0c', NULL, 'RADIATION THERAPY SERVICES AND SUPPLIES', NULL),
('4bb135cc-10af-4d8c-83f4-0f956e2cfaf7', NULL, '1CPT codes, descriptions and other data only are copyright 2020 American Medical Association. All Rights Reserved. Applicable FARS/HHSARS apply.', NULL),
('4e8b7e92-292c-4eda-b679-af7548434c68', NULL, 'Any future CPT or HCPCS code designated for a COVID-19 vaccine', NULL),
('6b9bf5e6-11a6-490d-8d2b-882684e9ce72', NULL, 'INCLUDE the following CPT and HCPCS codes:', NULL),
('8326b97e-dbcb-492b-89f3-e053b106caca', NULL, '2This list does not include codes for the following designated health service (DHS) categories: durable medical equipment and supplies; parenteral and enteral nutrients, equipment and supplies; prosthetics, orthotics, and prosthetic devices and supplies; home health services; outpatient prescription drugs; and inpatient and outpatient hospital services.  For the definitions of these DHS categories, refer to §411.351.  For more information, refer to the CMS Web site at http://www.cms.gov/Medicare/Fraud-and-Abuse/PhysicianSelfReferral/List_of_Codes.html.', NULL),
('8acdca70-30a9-4c45-b685-d4449fa50ed9', NULL, 'INCLUDE CPT codes for all clinical laboratory services in the 80000 series, except EXCLUDE CPT codes for the following blood component collection services:', NULL),
('9ad7bd63-097a-4bda-a42e-7c6a8a605531', NULL, 'INCLUDE the following CPT and HCPCS Level 2 codes for other clinical laboratory services:', NULL),
('a35a8c59-1166-4012-ba26-c9af7fe085da', NULL, 'The following codes for dialysis-related drugs furnished in or by an ESRD facility are eligible for use with the exception at §411.355(g):', NULL),
('a9052a50-c83d-4aa4-9b37-24cee53a7e62', NULL, 'The following tests,if performed for screening purposes, are eligible for use with the exception at §411.355(h):', NULL),
('b182fe4f-8ae2-4459-8f46-20b168bec38c', NULL, 'PHYSICAL THERAPY, OCCUPATIONAL THERAPY, AND OUTPATIENT SPEECH-LANGUAGE PATHOLOGY SERVICES', NULL),
('c2b4822d-a805-42ad-95f1-76bf9a96212c', NULL, 'RADIOLOGY AND CERTAIN OTHER IMAGING SERVICES', NULL),
('d4937951-820f-4edd-8fc7-8c516e22f429', NULL, 'INCLUDE the following CPT and HCPCS codes for physical therapy/occupational therapy/outpatient speech-language pathology services:', NULL),
('ee854bec-dc4f-4282-8e11-0eb110527f5c', NULL, 'PREVENTIVE SCREENING TESTS, IMMUNIZATIONS AND VACCINES', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `CPT_GROUP`
--
ALTER TABLE `CPT_GROUP`
  ADD PRIMARY KEY (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
