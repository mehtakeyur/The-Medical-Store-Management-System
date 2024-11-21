-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 31, 2024 at 05:29 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pharmacy2`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `InsertRequest` (IN `c_id` VARCHAR(100), IN `med_name` VARCHAR(100), IN `med_quan` INT(100))   BEGIN
INSERT INTO request_list (c_id, med_name, med_quan, status)VALUES (c_id, med_name, med_quan, 'Pending');
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `UpdateMedicineQuantity` (IN `m_name` VARCHAR(255), IN `new_quantity` INT)   BEGIN
UPDATE medicines SET m_quantity = new_quantity WHERE m_name = m_name;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `admin_id` varchar(100) NOT NULL,
  `a_fname` varchar(500) NOT NULL,
  `a_lname` varchar(100) NOT NULL,
  `a_gender` varchar(100) NOT NULL,
  `a_birthdate` date NOT NULL,
  `a_phone_no` varchar(100) NOT NULL,
  `a_email` varchar(100) NOT NULL,
  `admin_pass` varchar(100) NOT NULL,
  `a_address` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`admin_id`, `a_fname`, `a_lname`, `a_gender`, `a_birthdate`, `a_phone_no`, `a_email`, `admin_pass`, `a_address`) VALUES
('deep52', 'deep', 'patel', 'male', '2006-07-17', '7862908018', 'deep@gmail.com', 'Deep@7610', 'JND'),
('rushil84', 'rushil', 'patel', 'male', '2005-11-29', '1510651216', 'rushilpatel@gmail.com', 'Rushil123', 'RJT');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `cust_id` varchar(100) NOT NULL,
  `c_fname` varchar(100) NOT NULL,
  `c_lname` varchar(100) NOT NULL,
  `c_gender` varchar(100) NOT NULL,
  `c_birthdate` date NOT NULL,
  `c_phone_no` varchar(100) NOT NULL,
  `c_email` varchar(100) NOT NULL,
  `customer_pass` varchar(100) NOT NULL,
  `c_address` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `medicines`
--

CREATE TABLE `medicines` (
  `m_id` int(100) NOT NULL,
  `m_name` varchar(100) NOT NULL,
  `m_price` varchar(100) NOT NULL,
  `m_quantity` int(100) NOT NULL,
  `m_mdate` date NOT NULL,
  `m_edate` date NOT NULL,
  `m_descr` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `medicines`
--

INSERT INTO `medicines` (`m_id`, `m_name`, `m_price`, `m_quantity`, `m_mdate`, `m_edate`, `m_descr`) VALUES
(1, 'paracetamol', '70.0', 0, '2024-08-19', '2025-08-19', 'used'),
(3, 'avsola', '200.0', 1980, '2024-08-16', '2026-05-17', 'TNF'),
(4, 'adderall', '500.0', 5, '2024-12-25', '2027-06-25', 'ADHD'),
(5, 'atenolol', '50.0', 10, '2024-07-18', '2026-08-19', 'yu'),
(6, 'cetrizine', '15', 200, '2023-02-20', '2025-02-20', 'use for allergies'),
(7, 'Paracetamol', '25', 0, '2023-05-01', '2025-04-30', 'Pain relief and fever reducer'),
(8, 'Metformin', '40', 180, '2023-03-10', '2026-03-10', 'Controlling blood sugar levels'),
(9, 'Amoxicillin', '50', 150, '2023-02-20', '2025-02-19', 'Antibiotic for bacterial infections'),
(10, 'Ibuprofen', '30', 250, '2023-06-15', '2026-06-14', 'Pain relief and anti-inflammatory'),
(11, 'Cetirizine', '20', 300, '2023-07-10', '2025-07-09', 'Antihistamine for allergy relief'),
(61, 'aspirin', '15.0', 10, '2025-06-19', '2026-08-19', 'Blood'),
(62, 'benzine', '20.0', 30, '2025-09-18', '2029-07-12', 'fever');

-- --------------------------------------------------------

--
-- Table structure for table `request_list`
--

CREATE TABLE `request_list` (
  `req_id` int(11) NOT NULL,
  `c_id` varchar(100) NOT NULL,
  `med_name` varchar(100) NOT NULL,
  `med_quan` varchar(100) NOT NULL,
  `status` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`cust_id`);

--
-- Indexes for table `medicines`
--
ALTER TABLE `medicines`
  ADD PRIMARY KEY (`m_id`);

--
-- Indexes for table `request_list`
--
ALTER TABLE `request_list`
  ADD PRIMARY KEY (`req_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `medicines`
--
ALTER TABLE `medicines`
  MODIFY `m_id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;

--
-- AUTO_INCREMENT for table `request_list`
--
ALTER TABLE `request_list`
  MODIFY `req_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
