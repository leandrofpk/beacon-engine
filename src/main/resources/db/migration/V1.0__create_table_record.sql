CREATE TABLE `record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `origin` varchar(20) NOT NULL,
  `output_value` longtext NOT NULL,
  `previous_output` varchar(255) NOT NULL,
  `seed_value` varchar(255) NOT NULL,
  `signature` longtext NOT NULL,
  `status` varchar(20) NOT NULL,
  `time` datetime NOT NULL,
  `version_beacon` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
