CREATE TABLE vdf_precom (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  time_stamp datetime DEFAULT NULL,
  precommitment_value varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
