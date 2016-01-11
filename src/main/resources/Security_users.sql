--
-- Скрипт сгенерирован Devart dbForge Studio for MySQL, Версия 6.3.358.0
-- Домашняя страница продукта: http://www.devart.com/ru/dbforge/mysql/studio
-- Дата скрипта: 04.01.2016 23:55:21
-- Версия сервера: 5.5.41
-- Версия клиента: 4.1
--


--
-- Описание для базы данных registrator_db
--
DROP DATABASE IF EXISTS registrator_db;
CREATE DATABASE IF NOT EXISTS registrator_db
	CHARACTER SET latin1
	COLLATE latin1_swedish_ci;

-- 
-- Отключение внешних ключей
-- 
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;

-- 
-- Установить режим SQL (SQL mode)
-- 
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 
-- Установка базы данных по умолчанию
--
USE registrator_db;

--
-- Описание для таблицы address
--
CREATE TABLE IF NOT EXISTS address (
  address_id INT(11) NOT NULL AUTO_INCREMENT,
  building VARCHAR(255) NOT NULL,
  city VARCHAR(255) NOT NULL,
  district VARCHAR(255) DEFAULT NULL,
  flat VARCHAR(255) DEFAULT NULL,
  postcode VARCHAR(255) NOT NULL,
  region VARCHAR(255) NOT NULL,
  street VARCHAR(255) NOT NULL,
  user_id INT(11) NOT NULL,
  PRIMARY KEY (address_id),
  INDEX FK_7rod8a71yep5vxasb0ms3osbg (user_id)
)
ENGINE = MYISAM
AUTO_INCREMENT = 7
AVG_ROW_LENGTH = 56
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Описание для таблицы area
--
CREATE TABLE IF NOT EXISTS area (
  area_id INT(11) NOT NULL AUTO_INCREMENT,
  latitude DOUBLE NOT NULL,
  longitude DOUBLE NOT NULL,
  order_number INT(11) NOT NULL,
  resource_id INT(11) NOT NULL,
  PRIMARY KEY (area_id),
  INDEX FK_j05enuc6gftyec9v9m07880bs (resource_id)
)
ENGINE = MYISAM
AUTO_INCREMENT = 1
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Описание для таблицы discrete_parameters
--
CREATE TABLE IF NOT EXISTS discrete_parameters (
  discrete_parameter_id INT(11) NOT NULL AUTO_INCREMENT,
  description VARCHAR(255) NOT NULL,
  unit_name VARCHAR(255) NOT NULL,
  resource_type_id INT(11) NOT NULL,
  PRIMARY KEY (discrete_parameter_id),
  INDEX FK_itxbdpyec26wdkfoaltga8pau (resource_type_id)
)
ENGINE = MYISAM
AUTO_INCREMENT = 1
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Описание для таблицы inquiry_list
--
CREATE TABLE IF NOT EXISTS inquiry_list (
  inquiry_list_id INT(11) NOT NULL AUTO_INCREMENT,
  date DATETIME NOT NULL,
  inquiry_type ENUM('INPUT','OUTPUT') NOT NULL,
  to_user_id INT(11) NOT NULL,
  resource_id INT(11) NOT NULL,
  from_user_id INT(11) NOT NULL,
  PRIMARY KEY (inquiry_list_id),
  INDEX FK_37qp17x0dnyms8oxyo33jigpb (from_user_id),
  INDEX FK_cqtn406s2sbvribvsveeln8k1 (to_user_id),
  INDEX FK_j8gyy6aabddkoxp4jivw8fini (resource_id)
)
ENGINE = MYISAM
AUTO_INCREMENT = 1
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Описание для таблицы linear_parameters
--
CREATE TABLE IF NOT EXISTS linear_parameters (
  linear_parameter_id INT(11) NOT NULL AUTO_INCREMENT,
  description VARCHAR(255) NOT NULL,
  unit_name VARCHAR(255) NOT NULL,
  resource_type_id INT(11) NOT NULL,
  PRIMARY KEY (linear_parameter_id),
  INDEX FK_dv74cnpongab75t5q30ysncco (resource_type_id)
)
ENGINE = MYISAM
AUTO_INCREMENT = 1
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Описание для таблицы list_of_resouces
--
CREATE TABLE IF NOT EXISTS list_of_resouces (
  resources_id INT(11) NOT NULL AUTO_INCREMENT,
  date DATETIME NOT NULL,
  description VARCHAR(255) DEFAULT NULL,
  identifier VARCHAR(255) NOT NULL,
  reason_inclusion VARCHAR(255) NOT NULL,
  status ENUM('ACTIVE','UNCHECKED','DENIDED','OBSOLETE') NOT NULL,
  registrator_id INT(11) NOT NULL,
  tome_id INT(11) NOT NULL,
  resource_type_id INT(11) NOT NULL,
  PRIMARY KEY (resources_id),
  INDEX FK_2bflnodo3qtvgos2ou0s9sp9 (registrator_id),
  INDEX FK_3dk2u1o6r3f41knbp4bw0u4e2 (tome_id),
  INDEX FK_764t63m3e5fl8seck12tyr8j (resource_type_id),
  UNIQUE INDEX UK_n9p3viaj7hq0gt1ifb4fetvfa (identifier)
)
ENGINE = MYISAM
AUTO_INCREMENT = 1
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Описание для таблицы passport_data
--
CREATE TABLE IF NOT EXISTS passport_data (
  passport_data_id INT(11) NOT NULL AUTO_INCREMENT,
  number INT(11) NOT NULL,
  published_by_data VARCHAR(255) NOT NULL,
  seria VARCHAR(255) NOT NULL,
  user_id INT(11) NOT NULL,
  PRIMARY KEY (passport_data_id),
  INDEX FK_b3ufslic16u2m3j35ksfp0ivb (user_id)
)
ENGINE = MYISAM
AUTO_INCREMENT = 4
AVG_ROW_LENGTH = 24
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Описание для таблицы resource_discrete_values
--
CREATE TABLE IF NOT EXISTS resource_discrete_values (
  resource_discrete_value_id INT(11) NOT NULL AUTO_INCREMENT,
  value DOUBLE NOT NULL,
  discrete_parameter_id INT(11) NOT NULL,
  resource_id INT(11) NOT NULL,
  PRIMARY KEY (resource_discrete_value_id),
  INDEX FK_g9upwtpejnv2fd6o4hvsshcgn (resource_id),
  INDEX FK_mo277omjo0v4jv8269f2lsaen (discrete_parameter_id)
)
ENGINE = MYISAM
AUTO_INCREMENT = 1
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Описание для таблицы resource_linear_values
--
CREATE TABLE IF NOT EXISTS resource_linear_values (
  resource_linear_param_id INT(11) NOT NULL AUTO_INCREMENT,
  maximal_value DOUBLE NOT NULL,
  minimal_value DOUBLE NOT NULL,
  linear_parameter_id INT(11) NOT NULL,
  resource_id INT(11) NOT NULL,
  PRIMARY KEY (resource_linear_param_id),
  INDEX FK_5s1a0lk9h75mnqyiq6g1wu5wp (linear_parameter_id),
  INDEX FK_eyg8asvvonj51aepmy6y8fk9w (resource_id)
)
ENGINE = MYISAM
AUTO_INCREMENT = 1
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Описание для таблицы resource_parameters
--
CREATE TABLE IF NOT EXISTS resource_parameters (
  resource_parameters_id INT(11) NOT NULL AUTO_INCREMENT,
  parameter_id INT(11) NOT NULL,
  resource_type_id INT(11) NOT NULL,
  PRIMARY KEY (resource_parameters_id),
  INDEX FK_1unvdmfastc818i00xvmglchl (resource_type_id),
  INDEX FK_tc2co2gdknt0kyt43e0wwejh9 (parameter_id)
)
ENGINE = MYISAM
AUTO_INCREMENT = 1
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Описание для таблицы resource_types
--
CREATE TABLE IF NOT EXISTS resource_types (
  resource_type_id INT(11) NOT NULL AUTO_INCREMENT,
  type_name VARCHAR(255) NOT NULL,
  PRIMARY KEY (resource_type_id),
  UNIQUE INDEX UK_5fwgdwi603f06mf65x5fhv42a (type_name)
)
ENGINE = MYISAM
AUTO_INCREMENT = 2
AVG_ROW_LENGTH = 20
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Описание для таблицы roles
--
CREATE TABLE IF NOT EXISTS roles (
  role_id INT(11) NOT NULL AUTO_INCREMENT,
  description VARCHAR(255) NOT NULL,
  type VARCHAR(255) NOT NULL,
  PRIMARY KEY (role_id),
  UNIQUE INDEX UK_q9npl2ty4pngm2cussiul2qj5 (type(1))
)
ENGINE = INNODB
AUTO_INCREMENT = 4
AVG_ROW_LENGTH = 5461
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Описание для таблицы tomes
--
CREATE TABLE IF NOT EXISTS tomes (
  tome_id INT(11) NOT NULL AUTO_INCREMENT,
  identifier VARCHAR(255) NOT NULL,
  registrator_id INT(11) NOT NULL,
  PRIMARY KEY (tome_id),
  INDEX FK_pnsd367apavsotihxdt51mo7v (registrator_id),
  UNIQUE INDEX UK_9p7abcvlsajlte75dt1mfoe7l (identifier)
)
ENGINE = MYISAM
AUTO_INCREMENT = 1
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Описание для таблицы users
--
CREATE TABLE IF NOT EXISTS users (
  user_id INT(11) NOT NULL AUTO_INCREMENT,
  email VARCHAR(255) NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  login VARCHAR(255) NOT NULL,
  middle_name VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  status ENUM('BLOCK','UNBLOCK','INACTIVE') NOT NULL,
  role_id INT(11) NOT NULL,
  PRIMARY KEY (user_id),
  UNIQUE INDEX UK_6dotkott2kjsp8vw4d0m25fb7 (email),
  UNIQUE INDEX UK_ow0gan20590jrb00upg3va2fn (login),
  CONSTRAINT FK_users_roles_role_id FOREIGN KEY (role_id)
    REFERENCES roles(role_id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
AUTO_INCREMENT = 4
AVG_ROW_LENGTH = 5461
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

-- 
-- Вывод данных для таблицы address
--
INSERT INTO address VALUES
(1, 'one', 'Lviv', 'Lvivslka', '21', '78901', 'west', 'Bandera', 1),
(2, 'two', 'Drohobuch', 'Lvivslka', '44', '90123', 'east', 'Ringo', 2),
(3, 'three', 'Lviv', 'Lvivslka', '88', '77901', 'center', 'Bumer', 3),
(4, 'one', 'Lviv', 'Lvivslka', '21', '78901', 'west', 'Bandera', 1),
(5, 'two', 'Drohobuch', 'Lvivslka', '44', '90123', 'east', 'Ringo', 2),
(6, 'three', 'Lviv', 'Lvivslka', '88', '77901', 'center', 'Bumer', 3);

-- 
-- Вывод данных для таблицы area
--

-- Таблица registrator_db.area не содержит данных

-- 
-- Вывод данных для таблицы discrete_parameters
--

-- Таблица registrator_db.discrete_parameters не содержит данных

-- 
-- Вывод данных для таблицы inquiry_list
--

-- Таблица registrator_db.inquiry_list не содержит данных

-- 
-- Вывод данных для таблицы linear_parameters
--

-- Таблица registrator_db.linear_parameters не содержит данных

-- 
-- Вывод данных для таблицы list_of_resouces
--

-- Таблица registrator_db.list_of_resouces не содержит данных

-- 
-- Вывод данных для таблицы passport_data
--
INSERT INTO passport_data VALUES
(1, 123456, 'Lviv', 'KB', 1),
(2, 654321, 'Lviv', 'KC', 2),
(3, 45612, 'Lviv', 'KH', 3);

-- 
-- Вывод данных для таблицы resource_discrete_values
--

-- Таблица registrator_db.resource_discrete_values не содержит данных

-- 
-- Вывод данных для таблицы resource_linear_values
--

-- Таблица registrator_db.resource_linear_values не содержит данных

-- 
-- Вывод данных для таблицы resource_parameters
--

-- Таблица registrator_db.resource_parameters не содержит данных

-- 
-- Вывод данных для таблицы resource_types
--
INSERT INTO resource_types VALUES
(1, '?????????');

-- 
-- Вывод данных для таблицы roles
--
INSERT INTO roles VALUES
(1, 'have admin access', 'ADMIN'),
(2, 'have registrator access', 'REGISTRATOR'),
(3, 'have user access', 'USER');

-- 
-- Вывод данных для таблицы tomes
--

-- Таблица registrator_db.tomes не содержит данных

-- 
-- Вывод данных для таблицы users
--
INSERT INTO users VALUES
(1, 'admin@gmail.com', 'Huan', 'Pedro', 'admin', 'Oliveira', 'admin', 'UNBLOCK', 1),
(2, 'registrator@gmail.com', 'Ivan', 'Shpulka', 'registrator', 'Petrovuch', 'registrator', 'UNBLOCK', 2),
(3, 'user@gmail.com', 'Hugo', 'Boss', 'user', 'Ivanovuch', 'user', 'UNBLOCK', 3);

-- 
-- Восстановить предыдущий режим SQL (SQL mode)
-- 
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;

-- 
-- Включение внешних ключей
-- 
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;