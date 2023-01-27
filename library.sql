-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 24, 2023 at 01:51 PM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `library`
--

-- --------------------------------------------------------

--
-- Table structure for table `author`
--

CREATE TABLE `author` (
  `id` int(11) NOT NULL,
  `lastName` varchar(64) CHARACTER SET latin1 NOT NULL,
  `firstName` varchar(64) CHARACTER SET latin1 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `author`
--

INSERT INTO `author` (`id`, `lastName`, `firstName`) VALUES
(1, 'Munroe', 'Randall'),
(2, 'Mastrocola', 'Paola'),
(3, 'Amend', 'Lars'),
(4, 'Béata', 'Claude'),
(5, 'Smith', 'Tom Rob'),
(6, 'Tolkien', 'J. R. R.'),
(7, 'Rowling', 'J. K.'),
(8, 'Tiffany', 'John'),
(9, 'Thorne', 'Jack'),
(10, 'Miller', 'Ben'),
(11, 'Angier', 'Natalie'),
(12, 'Simblet', 'Sarah'),
(13, 'Heupel', 'Anna'),
(14, 'Sänger', 'Kyra'),
(15, 'Sänger', 'Christian'),
(16, 'Edwards', 'Betty'),
(17, 'King', 'Stephen'),
(18, 'Straub', 'Peter'),
(19, 'King', 'Owen'),
(20, 'Oliver', 'Jamie');

-- --------------------------------------------------------

--
-- Table structure for table `author_book`
--

CREATE TABLE `author_book` (
  `id` int(11) NOT NULL,
  `author_id` int(11) DEFAULT NULL,
  `book_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `author_book`
--

INSERT INTO `author_book` (`id`, `author_id`, `book_id`) VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 3),
(4, 4, 4),
(5, 5, 5),
(6, 6, 6),
(7, 6, 7),
(8, 6, 8),
(9, 6, 9),
(10, 7, 10),
(11, 8, 10),
(12, 9, 10),
(13, 10, 11),
(14, 11, 12),
(15, 7, 13),
(16, 7, 14),
(17, 7, 15),
(18, 7, 16),
(19, 7, 17),
(20, 7, 18),
(21, 7, 19),
(22, 7, 20),
(23, 12, 21),
(24, 13, 22),
(25, 14, 23),
(26, 15, 23),
(27, 16, 24),
(28, 17, 25),
(29, 18, 25),
(30, 17, 26),
(31, 19, 26),
(32, 20, 27);

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `edition` int(11) DEFAULT NULL,
  `copies` int(11) DEFAULT NULL,
  `publisher_id` int(11) DEFAULT NULL,
  `genre_id` int(11) DEFAULT NULL,
  `cover_img` varchar(500) DEFAULT NULL,
  `price` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`id`, `title`, `description`, `year`, `edition`, `copies`, `publisher_id`, `genre_id`, `cover_img`, `price`) VALUES
(1, 'Was wäre wenn?', NULL, 2016, 8, 3, 1, 16, 'M03328106901-large.jpeg', 13.37),
(2, 'Ich wär so gern ein Pinguin', NULL, 2010, 2, 2, 2, 3, '61745bzJqlL.jpeg', 3.83),
(3, 'Why not? Inspirationen für ein Leben ohne Wenn und Aber', NULL, 2017, 1, 1, 3, 21, 'why-not-lars-amend.png', 19.43),
(4, 'Das Wagnis der Liebe', NULL, 2014, 1, 1, 4, 4, 'Beata_CDas_Wagnis_der_Liebe_147955.jpeg', 10.27),
(5, 'Kind 44', 'Moskau 1953. Auf den Bahngleisen wird die Leiche eines kleinen Jungen gefunden, nackt, fürchterlich zugerichtet. Doch in der Sowjetunion der Stalinzeit gibt es offiziell keine Verbrechen. Und so wird der Mord zum Unfall erklärt. Der Geheimdienstoffizier Leo Demidow jedoch kann die Augen vor dem Offenkundigen nicht verschließen. Als der nächste Mord passiert, beginnt er auf eigene Faust zu ermitteln und bringt damit sich und seine Familie in tödliche Gefahr …', 2010, 2, 1, 5, 5, 'M03442472075-large.jpeg', 10.23),
(6, 'Der Herr der Ringe - Die Gefährten', NULL, 2012, NULL, 3, 6, 7, '9783608939811_cover.jpeg', 16.45),
(7, 'Der Herr der Ringe - Die zwei Türme', NULL, 2012, NULL, 3, 6, 7, 'M03608939822-large.jpeg', 16.45),
(8, 'Der Herr der Ringe - Die Rückkehr des Königs', NULL, 2012, NULL, 3, 6, 7, '9783608939835_cover_1.jpeg', 16.45),
(9, 'Der Hobbit', NULL, 2012, 16, 2, 6, 7, 'der-hobbit-gebundene.jpeg', 24.67),
(10, 'Harry Potter und das verwunschene Kind', NULL, 2021, NULL, 1, 7, 7, 'M0355155918X-large.jpeg', 20.56),
(11, 'Anybody Out There? Die faszinierende Suche nach außerirdischem Leben', NULL, 2017, 1, 1, 1, 4, 'M03328100962-large.jpeg', 8.99),
(12, 'Naturwissenschaft: Was man wissen muss, um die Welt zu verstehen', NULL, 2010, 2, 1, 8, 16, 'M03570011038-large.jpeg', 22.95),
(13, 'Harry Potter und der Stein der Weisen', NULL, 2018, NULL, 4, 7, 3, 'harry-potter-und-der-stein-der-weisen.jpeg', 10.29),
(14, 'Harry Potter und die Kammer des Schreckens', NULL, 2018, NULL, 5, 7, 3, 'harry-potter-und-die-kammer-des-schreckens.jpeg', 10),
(15, 'Harry Potter und der Gefangene von Askaban', NULL, 2018, NULL, 3, 7, 3, 'harry-potter-und-der-gefangene-von-askaban.jpg', 12.33),
(16, 'Harry Potter und der Feuerkelch', NULL, 2018, NULL, 3, 7, 3, 'harry-potter-und-der-feuerkelch.jpeg', 16),
(17, 'Harry Potter und der Orden des Phönix', NULL, 2018, NULL, 4, 7, 3, 'harry-potter-und-der-orden-des-phoenix.jpg', 17),
(18, 'Harry Potter und der Halbblutprinz', NULL, 2018, NULL, 3, 7, 3, 'harry-potter-und-der-halbblutprinz.jpg', 14),
(19, 'Harry Potter und die Heiligtümer des Todes', NULL, 2018, NULL, 3, 7, 3, 'harry-potter-und-die-heiligtuemer.jpg', 15.42),
(20, 'Phantastische Tierwesen und wo sie zu finden sind: Das Originaldrehbuch', NULL, 2018, NULL, 1, 7, 3, 'hogwarts-schulbuecher-phantastische-tierwesen.jpeg', 13.35),
(21, 'Anatomie für Künstler: Alles über das Aktzeichnen', NULL, 2021, NULL, 1, 9, 1, 'anatomie-fuer-kuenstler.jpg', 30.79),
(22, 'Fotografie - Inspired by life: Wie du in deinen Bildern Geschichten erzählst', NULL, 2021, 5, 1, 3, 15, 'fotografie-inspired-by-life.jpg', 25.7),
(23, 'Fotografieren für Einsteiger: Einfach fotografieren lernen. Der praktische Fotokurs für Anfänger', NULL, 2021, 2, 1, 9, 15, 'fotografieren-fuer-einsteiger.jpg', 17.37),
(24, 'Das neue Garantiert zeichnen lernen: Die Befreiung unserer schöpferischen Gestaltungskräfte', NULL, 2000, 19, 1, 10, 1, '9783498016692_L.jpeg', 25.7),
(25, 'Der Talisman', NULL, 2004, 4, 1, 11, 5, 'M03453877608-large.jpg', 13.37),
(26, 'Sleeping Beauties', NULL, 2019, NULL, 1, 11, 7, 'sleeping-beauties.jpeg', 13.35),
(27, 'ONE: Geniale One Pot Gerichte', NULL, 2022, 1, 1, 12, 10, 'ONE-Jamie-Oliver.jpeg', 27.99);

-- --------------------------------------------------------

--
-- Table structure for table `copy`
--

CREATE TABLE `copy` (
  `copy_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `copy`
--

INSERT INTO `copy` (`copy_id`, `book_id`) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 2),
(5, 2),
(6, 3),
(7, 4),
(8, 5),
(9, 6),
(10, 6),
(11, 6),
(12, 7),
(13, 7),
(14, 7),
(15, 8),
(16, 8),
(17, 8),
(18, 9),
(19, 9),
(20, 10),
(21, 11),
(22, 12),
(23, 13),
(24, 13),
(25, 13),
(26, 13),
(27, 14),
(28, 14),
(29, 14),
(30, 14),
(31, 14),
(32, 15),
(33, 15),
(34, 15),
(35, 16),
(36, 16),
(37, 16),
(38, 17),
(39, 17),
(40, 17),
(41, 17),
(42, 18),
(43, 18),
(44, 18),
(45, 19),
(46, 19),
(47, 19),
(48, 20),
(49, 21),
(50, 22),
(51, 23),
(52, 24),
(53, 25),
(54, 26),
(55, 27);

-- --------------------------------------------------------

--
-- Table structure for table `duration`
--

CREATE TABLE `duration` (
  `id` int(11) NOT NULL,
  `duration` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `duration`
--

INSERT INTO `duration` (`id`, `duration`) VALUES
(1, 14);

-- --------------------------------------------------------

--
-- Table structure for table `genre`
--

CREATE TABLE `genre` (
  `id` int(11) NOT NULL,
  `genre` varchar(100) CHARACTER SET latin1 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `genre`
--

INSERT INTO `genre` (`id`, `genre`) VALUES
(1, 'Kunst'),
(2, 'Biografie'),
(3, 'Jugendbücher'),
(4, 'Fachbücher'),
(5, 'Thriller'),
(6, 'Romane'),
(7, 'Fantasy'),
(8, 'Science-Fiction'),
(9, 'Sprachkurs'),
(10, 'Kochbücher'),
(11, 'Fitness'),
(12, 'Reiseführer'),
(13, 'Haus & Garten'),
(14, 'Kultur'),
(15, 'Fotografie'),
(16, 'Naturwissenschaften'),
(17, 'Technik'),
(18, 'Politik'),
(19, 'Religion'),
(20, 'Karrier'),
(21, 'Ratgeber');

-- --------------------------------------------------------

--
-- Table structure for table `patron`
--

CREATE TABLE `patron` (
  `id` int(11) NOT NULL,
  `firstName` varchar(32) DEFAULT NULL,
  `lastName` varchar(32) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `patron`
--

INSERT INTO `patron` (`id`, `firstName`, `lastName`, `email`, `phone`) VALUES
(1, 'Mi', 'Do', 'mido.cdq@gmail.com', '01234567'),
(16, 'Diana', 'Deutsch', 'd.deutsch@gmail.com', NULL),
(17, 'Pei', 'Cai', 'pei.cai@bulme.at', '0345678890'),
(20, 'Timy', 'Djon', NULL, NULL),
(21, 'a', 'b', NULL, NULL),
(22, 'Lily', 'Cai', 'lily.cai@gmail.com', NULL),
(23, 'tc', 'tc', 'tc@tc.at', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `publisher`
--

CREATE TABLE `publisher` (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET latin1 NOT NULL,
  `location` varchar(100) CHARACTER SET latin1 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `publisher`
--

INSERT INTO `publisher` (`id`, `name`, `location`) VALUES
(1, 'Penguin Verlag', 'München'),
(2, 'Pendo', 'München'),
(3, 'Gräfe und Unzer', 'München'),
(4, 'Riemann', 'München'),
(5, 'Goldmann', 'München'),
(6, 'Klett-Cotta', 'Stuttgart'),
(7, 'Carlsen', 'Hamburg'),
(8, 'C.Bertelsmann', 'München'),
(9, 'Vierfarben', 'Bonn'),
(10, 'Rowohlt', 'Hamburg'),
(11, 'Wilhelm Heyne', 'München'),
(12, 'Dorling Kindersley', 'London');

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `id` int(11) NOT NULL,
  `copy_id` int(11) DEFAULT NULL,
  `patron_id` int(11) DEFAULT NULL,
  `lend_date` date DEFAULT NULL,
  `return_date` date DEFAULT NULL,
  `new_lendDate` date DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `renewal_history` int(11) NOT NULL,
  `availability` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`id`, `copy_id`, `patron_id`, `lend_date`, `return_date`, `new_lendDate`, `duration`, `renewal_history`, `availability`) VALUES
(1, 1, 1, '2023-01-02', '2023-01-15', '2023-01-02', 6, 0, 1),
(3, 6, 1, '2022-12-01', '2022-12-14', '2022-12-01', 14, 0, 1),
(5, 27, 1, '2023-01-24', '2023-02-06', '2023-01-12', 14, 0, 1),
(7, 38, 1, '2023-01-05', '2023-01-18', '2023-01-05', 14, 0, 1),
(10, 39, 17, '2023-01-18', '2023-01-31', '2023-01-18', 14, 1, 1),
(21, 45, 17, '2023-01-24', '2023-02-06', NULL, NULL, 0, 1),
(26, 3, 21, '2023-01-24', '2023-02-06', NULL, NULL, 0, 1),
(27, 2, 22, '2023-01-24', '2023-02-06', NULL, NULL, 0, 1),
(29, 8, 23, '2023-01-24', '2023-02-06', NULL, NULL, 0, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `author`
--
ALTER TABLE `author`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `author_book`
--
ALTER TABLE `author_book`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `copy`
--
ALTER TABLE `copy`
  ADD PRIMARY KEY (`copy_id`);

--
-- Indexes for table `duration`
--
ALTER TABLE `duration`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `genre`
--
ALTER TABLE `genre`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `patron`
--
ALTER TABLE `patron`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `publisher`
--
ALTER TABLE `publisher`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `copy_id` (`copy_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `author`
--
ALTER TABLE `author`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `author_book`
--
ALTER TABLE `author_book`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `books`
--
ALTER TABLE `books`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `copy`
--
ALTER TABLE `copy`
  MODIFY `copy_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=56;

--
-- AUTO_INCREMENT for table `duration`
--
ALTER TABLE `duration`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `genre`
--
ALTER TABLE `genre`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `patron`
--
ALTER TABLE `patron`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `publisher`
--
ALTER TABLE `publisher`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
