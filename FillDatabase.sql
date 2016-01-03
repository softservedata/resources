-- phpMyAdmin SQL Dump
-- version 3.5.3
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 03, 2016 at 11:28 PM
-- Server version: 5.5.25a
-- PHP Version: 5.4.4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `registrator_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `address`
--

CREATE TABLE IF NOT EXISTS `address` (
  `address_id` int(11) NOT NULL AUTO_INCREMENT,
  `building` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `city` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `district` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `flat` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `postcode` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `region` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `street` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`address_id`),
  KEY `FK_7rod8a71yep5vxasb0ms3osbg` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=6 ;

--
-- Dumping data for table `address`
--

INSERT INTO `address` (`address_id`, `building`, `city`, `district`, `flat`, `postcode`, `region`, `street`, `user_id`) VALUES
(1, '35', 'Львів', 'Галицький', '20', '79026', 'Львівська', 'Пастернака', 1),
(2, '17', 'Хмельницький', 'Семенівський', '17', '29000', 'Хмельницька', 'Героїв Майдану', 2),
(3, '30', 'Стрий', 'Стрийський', '0', '353567', 'Львівська', 'Героїв Майдану', 3),
(4, '45', 'Львів', 'Залізничний', '78', '79026', 'Львівська', 'Стрийська', 4),
(5, '34', 'Київ', 'Троєщина', '90', '4456767', 'Київська', 'Бандери', 5);

-- --------------------------------------------------------

--
-- Table structure for table `area`
--

CREATE TABLE IF NOT EXISTS `area` (
  `area_id` int(11) NOT NULL AUTO_INCREMENT,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `order_number` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  PRIMARY KEY (`area_id`),
  KEY `FK_j05enuc6gftyec9v9m07880bs` (`resource_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=261 ;

--
-- Dumping data for table `area`
--

INSERT INTO `area` (`area_id`, `latitude`, `longitude`, `order_number`, `resource_id`) VALUES
(5, 53.876, 30.01, 1, 2),
(6, 63.55, 33.76, 2, 2),
(7, 49.3552, 43.54, 3, 2),
(8, 50.345, 24.07699, 4, 2),
(9, 53.876, 30.01, 1, 3),
(10, 63.55, 33.76, 2, 3),
(11, 49.3552, 43.54, 3, 3),
(12, 50.345, 24.07699, 4, 3),
(14, 49.824805785820764, 24.02088761329651, 1, 4),
(15, 49.818589914319574, 24.020270705223083, 2, 4),
(16, 49.81786998450602, 24.024454951286316, 3, 4),
(17, 49.81819533872909, 24.02509868144989, 4, 4),
(18, 49.81843069999485, 24.025238156318665, 5, 4),
(19, 49.821317243571634, 24.02835488319397, 6, 4),
(20, 49.82167718417192, 24.028934240341187, 7, 4),
(21, 49.82184330969968, 24.029427766799927, 8, 4),
(22, 49.822037122094414, 24.02947336435318, 9, 4),
(23, 49.82368449610263, 24.0322145819664, 10, 4),
(24, 49.82492345051901, 24.030900299549103, 11, 4),
(25, 49.8252626001068, 24.030782282352448, 12, 4),
(26, 49.825636354041386, 24.030868113040924, 13, 4),
(27, 49.825882061073294, 24.0310075879097, 14, 4),
(28, 49.82606547536814, 24.031307995319366, 15, 4),
(29, 49.82619351889761, 24.03167814016342, 16, 4),
(30, 49.82677490147095, 24.031409919261932, 17, 4),
(31, 49.826819889116884, 24.031608402729034, 18, 4),
(32, 49.826972154685045, 24.03150513768196, 19, 4),
(33, 49.82717113737555, 24.031507819890976, 20, 4),
(34, 49.82737531006801, 24.03141662478447, 21, 4),
(35, 49.82754660683161, 24.03126373887062, 22, 4),
(36, 49.82767637670322, 24.031086713075638, 23, 4),
(37, 49.82776288975754, 24.03091236948967, 24, 4),
(38, 49.82756737003446, 24.03062805533409, 25, 4),
(39, 49.82745144203784, 24.030416160821915, 26, 4),
(40, 49.82736492842647, 24.03019353747368, 27, 4),
(41, 49.82731128990973, 24.02993604540825, 28, 4),
(42, 49.82727495410652, 24.029675871133804, 29, 4),
(43, 49.82723342744088, 24.02901068329811, 30, 4),
(44, 49.827191900739564, 24.0288282930851, 31, 4),
(45, 49.827127880338544, 24.02867004275322, 32, 4),
(46, 49.82704482671908, 24.028530567884445, 33, 4),
(47, 49.826027408307546, 24.02722030878067, 34, 4),
(48, 49.825837072555174, 24.026812613010406, 35, 4),
(49, 49.82576093804454, 24.026539027690887, 36, 4),
(50, 49.82556367989134, 24.025589525699615, 37, 4),
(51, 49.825477162903766, 24.025385677814484, 38, 4),
(52, 49.824978822042695, 24.024755358695984, 39, 4),
(53, 49.82492691124109, 24.024548828601837, 40, 4),
(54, 49.82490960762818, 24.024291336536407, 41, 4),
(55, 49.824961518448355, 24.023910462856293, 42, 4),
(56, 49.825340465747956, 24.02217909693718, 43, 4),
(57, 49.82533700505547, 24.02177944779396, 44, 4),
(58, 49.82526952150219, 24.02143746614456, 45, 4),
(59, 49.82516916117195, 24.021179974079132, 46, 4),
(60, 49.82504111493106, 24.02098685503006, 47, 4),
(61, 49.82490268618129, 24.020890295505524, 48, 4),
(62, 49.830536416279834, 24.019433856010437, 1, 5),
(63, 49.82987895088713, 24.01940166950226, 2, 5),
(64, 49.82986510941439, 24.02013123035431, 3, 5),
(65, 49.82824562976009, 24.020013213157654, 4, 5),
(66, 49.828017237241085, 24.019755721092224, 5, 5),
(67, 49.82787881700774, 24.019669890403748, 6, 5),
(68, 49.827892659048906, 24.019197821617126, 7, 5),
(69, 49.82768502801561, 24.01910126209259, 8, 5),
(70, 49.8265845686597, 24.01808202266693, 9, 5),
(71, 49.825504848356616, 24.020227789878845, 10, 5),
(72, 49.826058554086636, 24.020914435386658, 11, 5),
(73, 49.82594089214931, 24.021772742271423, 12, 5),
(74, 49.825497926994885, 24.021676182746887, 13, 5),
(75, 49.82542871332312, 24.021922945976257, 14, 5),
(76, 49.8254217919505, 24.022191166877747, 15, 5),
(77, 49.82500650778075, 24.024240374565125, 16, 5),
(78, 49.82502727207392, 24.02463734149933, 17, 5),
(79, 49.82556714056765, 24.0253347158432, 18, 5),
(80, 49.8256709607411, 24.025645852088928, 19, 5),
(81, 49.82575401671942, 24.02607500553131, 20, 5),
(82, 49.82661917468109, 24.02531325817108, 21, 5),
(83, 49.826030868950646, 24.024294018745422, 22, 5),
(84, 49.82590628564269, 24.023972153663635, 23, 5),
(85, 49.825850915180595, 24.023521542549133, 24, 5),
(86, 49.82614160939948, 24.021697640419006, 25, 5),
(87, 49.82659841107124, 24.02182638645172, 26, 5),
(88, 49.82632156208817, 24.02357518672943, 27, 5),
(89, 49.826397695716445, 24.023939967155457, 28, 5),
(90, 49.82738050088793, 24.025463461875916, 29, 5),
(91, 49.82756044896784, 24.025956988334656, 30, 5),
(92, 49.82759505429106, 24.026246666908264, 31, 5),
(93, 49.82824562976009, 24.02662217617035, 32, 5),
(94, 49.82805876323385, 24.028231501579285, 33, 5),
(95, 49.82805184223753, 24.028499722480774, 34, 5),
(96, 49.828591676976515, 24.02985155582428, 35, 5),
(97, 49.82902077208664, 24.02984082698822, 36, 5),
(98, 49.82895848432297, 24.02831733226776, 37, 5),
(99, 49.829124584847854, 24.02584969997406, 38, 5),
(100, 49.82873701606915, 24.025742411613464, 39, 5),
(101, 49.82929068480235, 24.02256667613983, 40, 5),
(102, 49.829595199904176, 24.022684693336487, 41, 5),
(103, 49.829927396010525, 24.021493792533875, 42, 5),
(104, 49.83030111390304, 24.02061402797699, 43, 5),
(105, 49.83012117601894, 24.020442366600037, 44, 5),
(106, 49.82140722897276, 24.036996960639954, 1, 6),
(107, 49.82051428950597, 24.0337997674942, 2, 6),
(108, 49.82037584780299, 24.033960700035095, 3, 6),
(109, 49.8202374057039, 24.033638834953308, 4, 6),
(110, 49.81865221543448, 24.035398364067078, 5, 6),
(111, 49.81853453548344, 24.03561294078827, 6, 6),
(112, 49.81819533872909, 24.035966992378235, 7, 6),
(113, 49.81730233998537, 24.03654634952545, 8, 6),
(114, 49.81652700988103, 24.03707206249237, 9, 6),
(115, 49.81701851522789, 24.037511944770813, 10, 6),
(116, 49.81682468272954, 24.037994742393494, 11, 6),
(117, 49.81706005066225, 24.038209319114685, 12, 6),
(118, 49.81745463551039, 24.039217829704285, 13, 6),
(119, 49.81756539559115, 24.03959333896637, 14, 6),
(120, 49.817198501852566, 24.040204882621765, 15, 6),
(121, 49.817655387970085, 24.040676951408386, 16, 6),
(122, 49.81833378667112, 24.039915204048157, 17, 6),
(123, 49.81865913777562, 24.03933584690094, 18, 6),
(124, 49.81928906667457, 24.040644764900208, 19, 6),
(125, 49.819579800323474, 24.0403550863266, 20, 6),
(126, 49.82050736743022, 24.03986155986786, 21, 6),
(127, 49.82033431521485, 24.03911054134369, 22, 6),
(128, 49.82070118517663, 24.03861701488495, 23, 6),
(129, 49.82084654797692, 24.038016200065613, 24, 6),
(130, 49.820756561533, 24.036954045295715, 25, 6),
(131, 49.82110266232434, 24.03683602809906, 26, 6),
(132, 49.82117188218552, 24.03711497783661, 27, 6),
(133, 49.830342637935054, 24.017170071601868, 1, 7),
(134, 49.83033571726554, 24.017009139060974, 2, 7),
(135, 49.8302249864184, 24.016773104667664, 3, 7),
(136, 49.83052949563803, 24.016461968421936, 4, 7),
(137, 49.83061946390421, 24.01656925678253, 5, 7),
(138, 49.83100701759922, 24.016000628471375, 6, 7),
(139, 49.83067482890792, 24.015313982963562, 7, 7),
(140, 49.83049489241418, 24.015013575553894, 8, 7),
(141, 49.83006581038147, 24.014541506767273, 9, 7),
(142, 49.82917303072685, 24.013307690620422, 10, 7),
(143, 49.828653965212546, 24.012623727321625, 11, 7),
(144, 49.82826293217971, 24.012430608272552, 12, 7),
(145, 49.82755352790022, 24.011588394641876, 13, 7),
(146, 49.82735281650853, 24.011910259723663, 14, 7),
(147, 49.82705520842929, 24.01151329278946, 15, 7),
(148, 49.82721093381542, 24.01122897863388, 16, 7),
(149, 49.82674721674495, 24.01070863008499, 17, 7),
(150, 49.82659841107124, 24.010601341724396, 18, 7),
(151, 49.82653958079489, 24.01100367307663, 19, 7),
(152, 49.82635962891727, 24.01100367307663, 20, 7),
(153, 49.82619351889761, 24.011068046092987, 21, 7),
(154, 49.82577132003031, 24.011416733264923, 22, 7),
(155, 49.82569864608306, 24.01152938604355, 23, 7),
(156, 49.82606547536814, 24.011926352977753, 24, 7),
(157, 49.82629733772676, 24.012167751789093, 25, 7),
(158, 49.82728706604395, 24.013369381427765, 26, 7),
(159, 49.827494698785486, 24.013830721378326, 27, 7),
(160, 49.82759505429106, 24.014093577861786, 28, 7),
(161, 49.827819988288645, 24.01371270418167, 29, 7),
(162, 49.829017311657424, 24.015155732631683, 30, 7),
(163, 49.829494848548315, 24.01520401239395, 31, 7),
(164, 49.82974745673612, 24.015177190303802, 32, 7),
(165, 49.82979936236477, 24.016561210155487, 33, 7),
(166, 49.829660947231304, 24.016550481319427, 34, 7),
(167, 49.82966786799738, 24.01698499917984, 35, 7),
(168, 49.83003120682587, 24.01695817708969, 36, 7),
(169, 49.83010041391234, 24.017060101032257, 37, 7),
(170, 49.83010041391234, 24.017183482646942, 38, 7),
(171, 49.834083114901524, 24.031050503253937, 1, 8),
(172, 49.83358140259826, 24.030143916606903, 2, 8),
(173, 49.83364022431007, 24.029773771762848, 3, 8),
(174, 49.83319732966256, 24.029274880886078, 4, 8),
(175, 49.832861695860984, 24.0299990773201, 5, 8),
(176, 49.832702528501706, 24.029864966869354, 6, 8),
(177, 49.832411873711294, 24.030589163303375, 7, 8),
(178, 49.832975880817784, 24.031533300876617, 8, 8),
(179, 49.833456838737106, 24.03218239545822, 9, 8),
(180, 49.81580012662576, 24.00971621274948, 1, 9),
(181, 49.815692823887034, 24.00950700044632, 2, 9),
(182, 49.81510438528329, 24.00923877954483, 3, 9),
(183, 49.81446747896643, 24.00773137807846, 4, 9),
(184, 49.814554015642855, 24.007082283496857, 5, 9),
(185, 49.8142355599105, 24.006856977939606, 6, 9),
(186, 49.814249405855485, 24.005564153194427, 7, 9),
(187, 49.81435671179478, 24.00472193956375, 8, 9),
(188, 49.81425979031161, 24.004684388637543, 9, 9),
(189, 49.81432901996225, 24.00396555662155, 10, 9),
(190, 49.81419402205178, 24.00403529405594, 11, 9),
(191, 49.81373364173027, 24.003885090351105, 12, 9),
(192, 49.81375094934041, 24.002602994441986, 13, 9),
(193, 49.81099549986449, 24.00186002254486, 14, 9),
(194, 49.81085703092696, 24.00516450405121, 15, 9),
(195, 49.81143167442836, 24.00663435459137, 16, 9),
(196, 49.81157014172188, 24.006698727607727, 17, 9),
(197, 49.81159091178173, 24.00709569454193, 18, 9),
(198, 49.81122397274533, 24.00709569454193, 19, 9),
(199, 49.81121704934054, 24.008565545082092, 20, 9),
(200, 49.81220016290549, 24.008962512016296, 21, 9),
(201, 49.812027080979774, 24.009777903556824, 22, 9),
(202, 49.81280248318182, 24.00984227657318, 23, 9),
(203, 49.813889409998446, 24.009681344032288, 24, 9),
(204, 49.81414556117156, 24.008983969688416, 25, 9),
(205, 49.814830931967606, 24.009466767311096, 26, 9),
(206, 49.814941698053815, 24.01014268398285, 27, 9),
(207, 49.81476862593268, 24.01063621044159, 28, 9),
(208, 49.81476862593268, 24.01112973690033, 29, 9),
(209, 49.83158142182773, 24.031163156032562, 1, 10),
(210, 49.83121117410042, 24.030637443065643, 2, 10),
(211, 49.83047413046795, 24.031817615032196, 3, 10),
(212, 49.83093089122566, 24.03243988752365, 4, 10),
(213, 49.825048036358154, 23.99779111146927, 1, 11),
(214, 49.824712346003544, 23.996562659740448, 2, 11),
(215, 49.82480924655124, 23.996090590953827, 3, 11),
(216, 49.82480924655124, 23.995849192142487, 4, 11),
(217, 49.824712346003544, 23.99555414915085, 5, 11),
(218, 49.82463967046541, 23.995436131954193, 6, 11),
(219, 49.82440433940222, 23.995135724544525, 7, 11),
(220, 49.82347338618063, 23.997174203395844, 8, 11),
(221, 49.82302347671588, 23.997211754322052, 9, 11),
(222, 49.82270507672028, 23.99800568819046, 10, 11),
(223, 49.82262201550723, 23.999169766902924, 11, 11),
(224, 49.823518376897, 23.99975448846817, 12, 11),
(225, 49.82375717307571, 23.99897664785385, 13, 11),
(226, 49.823860997133586, 23.99877279996872, 14, 11),
(227, 49.824058262229705, 23.99853140115738, 15, 11),
(228, 49.824175928746136, 23.998413383960724, 16, 11),
(229, 49.82434204569388, 23.998354375362396, 17, 11),
(230, 49.82483693238639, 23.998316824436188, 18, 11),
(231, 49.82483001092909, 23.997941315174103, 19, 11),
(232, 49.84013785290865, 24.021641314029694, 1, 12),
(233, 49.84057376478728, 24.021115601062775, 2, 12),
(234, 49.83799629223447, 24.016164243221283, 3, 12),
(235, 49.836923746395804, 24.01846557855606, 4, 12),
(236, 49.836685015214144, 24.019307792186737, 5, 12),
(237, 49.838598291410264, 24.022682011127472, 6, 12),
(238, 49.839546251930024, 24.021373093128204, 7, 12),
(239, 49.83955317128154, 24.0212282538414, 8, 12),
(240, 49.839684638772425, 24.02101904153824, 9, 12),
(241, 49.83974345306053, 24.021094143390656, 10, 12),
(242, 49.839791888302884, 24.021029770374298, 11, 12),
(243, 49.83992681479357, 24.01575654745102, 1, 13),
(244, 49.83994065338674, 24.015413224697113, 2, 13),
(245, 49.839266017361, 24.01476413011551, 3, 13),
(246, 49.839349049995214, 24.013433754444122, 4, 13),
(247, 49.83898232145154, 24.013315737247467, 5, 13),
(248, 49.83896848258411, 24.013664424419403, 6, 13),
(249, 49.83878511721673, 24.013648331165314, 7, 13),
(250, 49.838778197755296, 24.013798534870148, 8, 13),
(251, 49.838466820965586, 24.01376098394394, 9, 13),
(252, 49.8384806599766, 24.013546407222748, 10, 13),
(253, 49.83813814328994, 24.01350885629654, 11, 13),
(254, 49.83810354550992, 24.01370197534561, 12, 13),
(255, 49.83805856835887, 24.014152586460114, 13, 13),
(256, 49.83800667159413, 24.014136493206024, 14, 13),
(257, 49.83790287789755, 24.015085995197296, 15, 13),
(258, 49.83875397963245, 24.01673823595047, 16, 13),
(259, 49.83947705836027, 24.015863835811615, 17, 13),
(260, 49.839750372383826, 24.015890657901764, 18, 13);

-- --------------------------------------------------------

--
-- Table structure for table `discrete_parameters`
--

CREATE TABLE IF NOT EXISTS `discrete_parameters` (
  `discrete_parameter_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `unit_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `resource_type_id` int(11) NOT NULL,
  PRIMARY KEY (`discrete_parameter_id`),
  KEY `FK_itxbdpyec26wdkfoaltga8pau` (`resource_type_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=12 ;

--
-- Dumping data for table `discrete_parameters`
--

INSERT INTO `discrete_parameters` (`discrete_parameter_id`, `description`, `unit_name`, `resource_type_id`) VALUES
(1, 'периметер', 'м', 1),
(2, 'площа', 'га', 1),
(3, 'потужність', 'мВт', 2),
(4, 'напруженість', 'мВт', 2),
(11, 'площа', 'га', 2);

-- --------------------------------------------------------

--
-- Table structure for table `inquiry_list`
--

CREATE TABLE IF NOT EXISTS `inquiry_list` (
  `inquiry_list_id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `inquiry_type` enum('INPUT','OUTPUT') COLLATE utf8_unicode_ci NOT NULL,
  `to_user_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  `from_user_id` int(11) NOT NULL,
  PRIMARY KEY (`inquiry_list_id`),
  KEY `FK_cqtn406s2sbvribvsveeln8k1` (`to_user_id`),
  KEY `FK_j8gyy6aabddkoxp4jivw8fini` (`resource_id`),
  KEY `FK_37qp17x0dnyms8oxyo33jigpb` (`from_user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=2 ;

--
-- Dumping data for table `inquiry_list`
--

INSERT INTO `inquiry_list` (`inquiry_list_id`, `date`, `inquiry_type`, `to_user_id`, `resource_id`, `from_user_id`) VALUES
(1, '2015-12-30 16:06:00', 'OUTPUT', 2, 2, 3);

-- --------------------------------------------------------

--
-- Table structure for table `linear_parameters`
--

CREATE TABLE IF NOT EXISTS `linear_parameters` (
  `linear_parameter_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `unit_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `resource_type_id` int(11) NOT NULL,
  PRIMARY KEY (`linear_parameter_id`),
  KEY `FK_dv74cnpongab75t5q30ysncco` (`resource_type_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=4 ;

--
-- Dumping data for table `linear_parameters`
--

INSERT INTO `linear_parameters` (`linear_parameter_id`, `description`, `unit_name`, `resource_type_id`) VALUES
(1, 'cмуга радіочастот', 'МГц', 2),
(2, 'широта діапазону', 'кГц', 2),
(3, 'радіус дії', 'км', 2);

-- --------------------------------------------------------

--
-- Table structure for table `list_of_resouces`
--

CREATE TABLE IF NOT EXISTS `list_of_resouces` (
  `resources_id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `identifier` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `reason_inclusion` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `status` enum('ACTIVE','UNCHECKED','DENIDED','OBSOLETE') COLLATE utf8_unicode_ci NOT NULL,
  `registrator_id` int(11) NOT NULL,
  `tome_id` int(11) NOT NULL,
  `resource_type_id` int(11) NOT NULL,
  PRIMARY KEY (`resources_id`),
  UNIQUE KEY `UK_n9p3viaj7hq0gt1ifb4fetvfa` (`identifier`),
  KEY `FK_2bflnodo3qtvgos2ou0s9sp9` (`registrator_id`),
  KEY `FK_3dk2u1o6r3f41knbp4bw0u4e2` (`tome_id`),
  KEY `FK_764t63m3e5fl8seck12tyr8j` (`resource_type_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=14 ;

--
-- Dumping data for table `list_of_resouces`
--

INSERT INTO `list_of_resouces` (`resources_id`, `date`, `description`, `identifier`, `reason_inclusion`, `status`, `registrator_id`, `tome_id`, `resource_type_id`) VALUES
(2, '2015-12-08 15:33:01', 'радіочастоти', '123555', 'Паспорт громадянина україни...', 'ACTIVE', 2, 1, 2),
(3, '2015-12-08 15:33:01', 'суперрадіочастоти', '111111', 'Посвідчення водія ...', 'ACTIVE', 2, 1, 2),
(4, '2016-01-02 00:00:00', 'Стрийський парк', '79000-001', 'паспорт громадянина України КС 2234, виданий на ім’я Олександр Олександрович Архилюк Львівський....;\r\n', 'ACTIVE', 2, 1, 1),
(5, '2016-01-03 00:00:00', 'парк Культури', '79000-002', 'паспорт громадянина України КС 2234, виданий на ім’я Олександр Олександрович Архилюк Львівський....;\r\nволевиявлення людини;\r\nдоручення;\r\n', 'ACTIVE', 2, 1, 1),
(6, '2016-01-03 00:00:00', 'парк Залізна вода', '79000-003', 'паспорт громадянина України КС 2234, виданий на ім’я Олександр Олександрович Архилюк Львівський....;\r\nдоручення;\r\n', 'ACTIVE', 4, 2, 1),
(7, '2016-01-03 00:00:00', 'Студентський парк', '79000-004', 'волевиявлення людини;\r\nдоручення;\r\n', 'ACTIVE', 2, 1, 1),
(8, '2016-01-04 00:00:00', 'Ботанічний сад', '79000-005', 'паспорт громадянина України КС 2234, виданий на ім’я Олександр Олександрович Архилюк Львівський....;\r\nдоручення;\r\n', 'ACTIVE', 4, 2, 1),
(9, '2016-01-04 00:00:00', 'парк Горіховий гай', '79000-006', 'паспорт громадянина України КС 2234, виданий на ім’я Олександр Олександрович Архилюк Львівський....;\r\nдоручення;\r\n', 'ACTIVE', 2, 1, 1),
(10, '2016-01-04 00:00:00', 'Дендрарій', '79000-007', 'паспорт громадянина України КС 2234, виданий на ім’я Олександр Олександрович Архилюк Львівський....;\r\nдоручення;\r\n', 'ACTIVE', 4, 2, 1),
(11, '2016-01-04 00:00:00', 'парк Піскові озера', '79000-008', 'волевиявлення людини;\r\nдоручення;\r\n', 'ACTIVE', 2, 1, 1),
(12, '2016-01-04 00:00:00', 'парк Імені Франка', '79000-009', 'паспорт громадянина України КС 2234, виданий на ім’я Олександр Олександрович Архилюк Львівський....;\r\nдоручення;\r\n', 'ACTIVE', 4, 2, 1),
(13, '2016-01-04 00:00:00', 'сад Собору святого Юра', '79000-010', 'паспорт громадянина України КС 2234, виданий на ім’я Олександр Олександрович Архилюк Львівський....;\r\nдоручення;\r\n', 'ACTIVE', 2, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `passport_data`
--

CREATE TABLE IF NOT EXISTS `passport_data` (
  `passport_data_id` int(11) NOT NULL AUTO_INCREMENT,
  `number` int(11) NOT NULL,
  `published_by_data` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `seria` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`passport_data_id`),
  KEY `FK_b3ufslic16u2m3j35ksfp0ivb` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=6 ;

--
-- Dumping data for table `passport_data`
--

INSERT INTO `passport_data` (`passport_data_id`, `number`, `published_by_data`, `seria`, `user_id`) VALUES
(1, 2234, 'Львівський....', 'КС', 1),
(2, 123456, 'Хмельницьким МВ УМВС України в Хмельницький області 01 січня 1997 року', 'КК', 2),
(3, 123456, 'Стрийський МВ УМВС України в Львівській області 01 січня 1965 року', 'КК', 3),
(4, 1122456, 'Львівський....', 'КС', 4),
(5, 1126789, 'Київський....', 'КС', 5);

-- --------------------------------------------------------

--
-- Table structure for table `resource_discrete_values`
--

CREATE TABLE IF NOT EXISTS `resource_discrete_values` (
  `resource_discrete_value_id` int(11) NOT NULL AUTO_INCREMENT,
  `value` double NOT NULL,
  `discrete_parameter_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  PRIMARY KEY (`resource_discrete_value_id`),
  KEY `FK_mo277omjo0v4jv8269f2lsaen` (`discrete_parameter_id`),
  KEY `FK_g9upwtpejnv2fd6o4hvsshcgn` (`resource_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=27 ;

--
-- Dumping data for table `resource_discrete_values`
--

INSERT INTO `resource_discrete_values` (`resource_discrete_value_id`, `value`, `discrete_parameter_id`, `resource_id`) VALUES
(3, 100, 3, 2),
(4, 500.55, 3, 2),
(5, 23.54, 3, 2),
(6, 200, 4, 2),
(7, 3280.3, 1, 4),
(8, 53.46747, 2, 4),
(9, 3299, 1, 5),
(10, 21.91676, 2, 5),
(11, 1860.6, 1, 6),
(12, 14.8679, 2, 6),
(13, 1743.7, 1, 7),
(14, 7.07496, 2, 7),
(15, 482.9, 1, 8),
(16, 1.98205, 2, 8),
(17, 2195.6, 1, 9),
(18, 19.00922, 2, 9),
(19, 241.5, 1, 10),
(20, 0.72436, 2, 10),
(21, 932.4, 1, 11),
(22, 4.33756, 2, 11),
(23, 1299.1, 1, 12),
(24, 9.43888, 2, 12),
(25, 802.1, 1, 13),
(26, 3.1126, 2, 13);

-- --------------------------------------------------------

--
-- Table structure for table `resource_linear_values`
--

CREATE TABLE IF NOT EXISTS `resource_linear_values` (
  `resource_linear_param_id` int(11) NOT NULL AUTO_INCREMENT,
  `maximal_value` double NOT NULL,
  `minimal_value` double NOT NULL,
  `linear_parameter_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  PRIMARY KEY (`resource_linear_param_id`),
  KEY `FK_5s1a0lk9h75mnqyiq6g1wu5wp` (`linear_parameter_id`),
  KEY `FK_eyg8asvvonj51aepmy6y8fk9w` (`resource_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=7 ;

--
-- Dumping data for table `resource_linear_values`
--

INSERT INTO `resource_linear_values` (`resource_linear_param_id`, `maximal_value`, `minimal_value`, `linear_parameter_id`, `resource_id`) VALUES
(1, 2483.5, 2400, 1, 2),
(2, 5350, 5150, 2, 2),
(3, 2700, 2500, 1, 2),
(4, 2483.5, 1100, 3, 2),
(5, 5350, 1110, 2, 2),
(6, 2100, 9999, 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `resource_parameters`
--

CREATE TABLE IF NOT EXISTS `resource_parameters` (
  `resource_parameters_id` int(11) NOT NULL AUTO_INCREMENT,
  `parameter_id` int(11) NOT NULL,
  `resource_type_id` int(11) NOT NULL,
  PRIMARY KEY (`resource_parameters_id`),
  KEY `FK_tc2co2gdknt0kyt43e0wwejh9` (`parameter_id`),
  KEY `FK_1unvdmfastc818i00xvmglchl` (`resource_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `resource_types`
--

CREATE TABLE IF NOT EXISTS `resource_types` (
  `resource_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`resource_type_id`),
  UNIQUE KEY `UK_5fwgdwi603f06mf65x5fhv42a` (`type_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- Dumping data for table `resource_types`
--

INSERT INTO `resource_types` (`resource_type_id`, `type_name`) VALUES
(1, 'земельний'),
(2, 'радіочастотний');

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE IF NOT EXISTS `roles` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `type` enum('USER','REGISTRATOR','ADMIN') COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `UK_q9npl2ty4pngm2cussiul2qj5` (`type`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=4 ;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`role_id`, `description`, `type`) VALUES
(1, 'description', 'ADMIN'),
(2, 'description', 'REGISTRATOR'),
(3, 'description', 'USER');

-- --------------------------------------------------------

--
-- Table structure for table `tomes`
--

CREATE TABLE IF NOT EXISTS `tomes` (
  `tome_id` int(11) NOT NULL AUTO_INCREMENT,
  `identifier` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `registrator_id` int(11) NOT NULL,
  PRIMARY KEY (`tome_id`),
  UNIQUE KEY `UK_9p7abcvlsajlte75dt1mfoe7l` (`identifier`),
  KEY `FK_pnsd367apavsotihxdt51mo7v` (`registrator_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- Dumping data for table `tomes`
--

INSERT INTO `tomes` (`tome_id`, `identifier`, `registrator_id`) VALUES
(1, '12345', 2),
(2, '6789', 4);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `first_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `last_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `login` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `middle_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `role_id` int(11) NOT NULL,
  `status` enum('BLOCK','UNBLOCK','INACTIVE') COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UK_ow0gan20590jrb00upg3va2fn` (`login`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=6 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `email`, `first_name`, `last_name`, `login`, `middle_name`, `password`, `role_id`, `status`) VALUES
(1, 'oless.@gmail.com', 'Олександр', 'Архилюк', 'oleks', 'Олександрович', 'pass1', 1, 'UNBLOCK'),
(2, 'petro.@gmail.com', 'Петро', 'Петренко', 'petro', 'Петрович', 'pass2', 2, 'UNBLOCK'),
(3, 'ivan.@gmail.com', 'Юрій', 'Іванов', 'ivan', 'Іванович', 'pass3', 3, 'UNBLOCK'),
(4, 'vasyl.@gmail.com', 'Василь', 'Василюк', 'vasyl', 'Васильович', 'pass4', 2, 'UNBLOCK'),
(5, 'oleh.@gmail.com', 'Олег', 'Василюк', 'oleh', 'Олеговчич', 'pass5', 3, 'INACTIVE');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `address`
--
ALTER TABLE `address`
  ADD CONSTRAINT `FK_7rod8a71yep5vxasb0ms3osbg` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `area`
--
ALTER TABLE `area`
  ADD CONSTRAINT `FK_j05enuc6gftyec9v9m07880bs` FOREIGN KEY (`resource_id`) REFERENCES `list_of_resouces` (`resources_id`);

--
-- Constraints for table `discrete_parameters`
--
ALTER TABLE `discrete_parameters`
  ADD CONSTRAINT `FK_itxbdpyec26wdkfoaltga8pau` FOREIGN KEY (`resource_type_id`) REFERENCES `resource_types` (`resource_type_id`);

--
-- Constraints for table `inquiry_list`
--
ALTER TABLE `inquiry_list`
  ADD CONSTRAINT `FK_37qp17x0dnyms8oxyo33jigpb` FOREIGN KEY (`from_user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `FK_cqtn406s2sbvribvsveeln8k1` FOREIGN KEY (`to_user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `FK_j8gyy6aabddkoxp4jivw8fini` FOREIGN KEY (`resource_id`) REFERENCES `list_of_resouces` (`resources_id`);

--
-- Constraints for table `linear_parameters`
--
ALTER TABLE `linear_parameters`
  ADD CONSTRAINT `FK_dv74cnpongab75t5q30ysncco` FOREIGN KEY (`resource_type_id`) REFERENCES `resource_types` (`resource_type_id`);

--
-- Constraints for table `list_of_resouces`
--
ALTER TABLE `list_of_resouces`
  ADD CONSTRAINT `FK_764t63m3e5fl8seck12tyr8j` FOREIGN KEY (`resource_type_id`) REFERENCES `resource_types` (`resource_type_id`),
  ADD CONSTRAINT `FK_2bflnodo3qtvgos2ou0s9sp9` FOREIGN KEY (`registrator_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `FK_3dk2u1o6r3f41knbp4bw0u4e2` FOREIGN KEY (`tome_id`) REFERENCES `tomes` (`tome_id`);

--
-- Constraints for table `passport_data`
--
ALTER TABLE `passport_data`
  ADD CONSTRAINT `FK_b3ufslic16u2m3j35ksfp0ivb` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `resource_discrete_values`
--
ALTER TABLE `resource_discrete_values`
  ADD CONSTRAINT `FK_g9upwtpejnv2fd6o4hvsshcgn` FOREIGN KEY (`resource_id`) REFERENCES `list_of_resouces` (`resources_id`),
  ADD CONSTRAINT `FK_mo277omjo0v4jv8269f2lsaen` FOREIGN KEY (`discrete_parameter_id`) REFERENCES `discrete_parameters` (`discrete_parameter_id`);

--
-- Constraints for table `resource_linear_values`
--
ALTER TABLE `resource_linear_values`
  ADD CONSTRAINT `FK_eyg8asvvonj51aepmy6y8fk9w` FOREIGN KEY (`resource_id`) REFERENCES `list_of_resouces` (`resources_id`),
  ADD CONSTRAINT `FK_5s1a0lk9h75mnqyiq6g1wu5wp` FOREIGN KEY (`linear_parameter_id`) REFERENCES `linear_parameters` (`linear_parameter_id`);

--
-- Constraints for table `resource_parameters`
--
ALTER TABLE `resource_parameters`
  ADD CONSTRAINT `FK_1unvdmfastc818i00xvmglchl` FOREIGN KEY (`resource_type_id`) REFERENCES `resource_types` (`resource_type_id`),
  ADD CONSTRAINT `FK_tc2co2gdknt0kyt43e0wwejh9` FOREIGN KEY (`parameter_id`) REFERENCES `linear_parameters` (`linear_parameter_id`);

--
-- Constraints for table `tomes`
--
ALTER TABLE `tomes`
  ADD CONSTRAINT `FK_pnsd367apavsotihxdt51mo7v` FOREIGN KEY (`registrator_id`) REFERENCES `users` (`user_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
