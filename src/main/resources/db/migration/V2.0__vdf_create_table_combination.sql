CREATE TABLE combination (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  certificate_id varchar(255) DEFAULT NULL,
  cipher_suite int(11) NOT NULL,
  iterations int(11) NOT NULL,
  period int(11) NOT NULL,
  pulse_index bigint(20) NOT NULL,
  signature_value varchar(2048) DEFAULT NULL,
  status_code int(11) NOT NULL,
  combination varchar(10) DEFAULT NULL,
  time_stamp datetime DEFAULT NULL,
  x varchar(2048) DEFAULT NULL,
  y varchar(2048) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
