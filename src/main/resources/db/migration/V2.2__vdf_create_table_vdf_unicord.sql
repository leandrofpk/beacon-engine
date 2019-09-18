CREATE TABLE vdf_unicorn (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  uri varchar(255) NULL,
  version varchar(255) NULL,
  certificate_id varchar(255) DEFAULT NULL,
  cipher_suite int(11) NOT NULL,
  iterations int(11) NOT NULL,
  period int(11) NOT NULL,
  pulse_index bigint(20) NOT NULL,
  signature_value varchar(2048) DEFAULT NULL,
  combination varchar(10) DEFAULT NULL,
  time_stamp datetime DEFAULT NULL,
  p varchar(2048) DEFAULT NULL,
  x varchar(2048) DEFAULT NULL,
  y varchar(2048) DEFAULT NULL,
  output_value varchar(255) NOT NULL,
  created_at TIMESTAMP(3) NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
