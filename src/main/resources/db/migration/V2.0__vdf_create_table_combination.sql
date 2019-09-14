CREATE TABLE vdf_combination (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  uri varchar(255) NULL,
  version varchar(255) NULL,
  certificate_id varchar(255) DEFAULT NULL,
  cipher_suite int(11) NOT NULL,
  iterations int(11) NOT NULL,
  period int(11) NOT NULL,
  pulse_index bigint(20) NOT NULL,
  signature_value varchar(2048) DEFAULT NULL,
  status_code int(11) NOT NULL,
  combination varchar(10) DEFAULT NULL,
  time_stamp datetime DEFAULT NULL,
  created_at TIMESTAMP(3) NULL,
  p varchar(2048) DEFAULT NULL,
  x varchar(2048) DEFAULT NULL,
  y varchar(2048) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
