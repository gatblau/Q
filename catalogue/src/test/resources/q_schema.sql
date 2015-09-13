/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Table Catalogue
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Catalogue`;

CREATE TABLE `Catalogue` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `author` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `time` datetime NOT NULL,
  `version` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# Table Event
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Event`;

CREATE TABLE `Event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `time` datetime NOT NULL,
  `type` varchar(255) NOT NULL,
  `step_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Event_Step` (`step_id`),
  CONSTRAINT `FK_Event_Step` FOREIGN KEY (`step_id`) REFERENCES `Step` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# Table Feature
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Feature`;

CREATE TABLE `Feature` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `time` datetime NOT NULL,
  `featureSet_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_Feature_Name` (`name`),
  KEY `FK_Feature_FeatureSet` (`featureSet_id`),
  CONSTRAINT `FK_Feature_FeatureSet` FOREIGN KEY (`featureSet_id`) REFERENCES `FeatureSet` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# Table FeatureSet
# ------------------------------------------------------------

DROP TABLE IF EXISTS `FeatureSet`;

CREATE TABLE `FeatureSet` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `time` datetime NOT NULL,
  `catalogue_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_FeatureSet_Name` (`name`),
  KEY `FK_FeatureSet_Catalogue` (`catalogue_id`),
  CONSTRAINT `FK_FeatureSet_Catalogue` FOREIGN KEY (`catalogue_id`) REFERENCES `Catalogue` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# Table Scenario
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Scenario`;

CREATE TABLE `Scenario` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `time` datetime NOT NULL,
  `feature_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Scenario_Feature` (`feature_id`),
  CONSTRAINT `FK_Scenario_Feature` FOREIGN KEY (`feature_id`) REFERENCES `Feature` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# Table Step
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Step`;

CREATE TABLE `Step` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `time` datetime NOT NULL,
  `scenario_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Step_Scenario` (`scenario_id`),
  CONSTRAINT `FK_Step_Scenario` FOREIGN KEY (`scenario_id`) REFERENCES `Scenario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
