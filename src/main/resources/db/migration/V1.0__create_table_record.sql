CREATE TABLE `record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `origin` varchar(20) NOT NULL,
  `output_value` longtext NOT NULL,
  `previous_output_value` longtext NOT NULL,
  `seed_value` longtext NOT NULL,
  `signature_value` longtext NOT NULL,
  `status_code` varchar(20) NOT NULL,
  `time_stamp` datetime NOT NULL,
  `version_beacon` varchar(20) NOT NULL,
  `frequency` varchar(3) NOT NULL,
  `unix_time_stamp` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;