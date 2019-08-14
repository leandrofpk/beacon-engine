CREATE TABLE pulse (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  certificate_id varchar(255) NOT NULL,
  chain_index bigint(20) NOT NULL,
  cipher_suite int(11) NOT NULL,
  ext_src_id varchar(255) NOT NULL,
  ext_status smallint(6)  NOT NULL,
  ext_value varchar(255) NOT NULL,
  local_random_value varchar(255) NOT NULL,
  output_value varchar(255) NOT NULL,
  period int(11) NOT NULL,
  precommitment_value varchar(255) NOT NULL,
  pulse_index bigint(20) NOT NULL,
  signature_value varchar(2048) NOT NULL,
  status_code int(11) NOT NULL,
  time_stamp datetime NOT NULL,
  uri varchar(255) NOT NULL,
  version varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK1t9peie90oq11xjedtedj2ea (time_stamp,chain_index)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
