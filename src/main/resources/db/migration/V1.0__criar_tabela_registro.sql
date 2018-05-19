CREATE TABLE `registro` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `assinatura` longtext NOT NULL,
  `hora` datetime NOT NULL,
  `numero` longtext NOT NULL,
  `origem` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
