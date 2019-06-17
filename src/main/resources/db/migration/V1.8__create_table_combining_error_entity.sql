CREATE TABLE processing_error_entity (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  chain varchar(20) NOT NULL,
  processing_error_type varchar(20) NOT NULL,
  qtd_sources_expected int(11) NOT NULL,
  timestamp bigint(20) NOT NULL,
  timestamp_date datetime NOT NULL,
  timestamp_error datetime NOT NULL,
  used_or_discarded_fonts varchar(20) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
