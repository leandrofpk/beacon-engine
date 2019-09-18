CREATE TABLE vdf_unicorn_seed (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  cumulative_hash varchar(255) DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  seed varchar(255) DEFAULT NULL,
  seed_index int(11) NOT NULL,
  time_stamp TIMESTAMP(3) NULL,
  uri varchar(255) DEFAULT NULL,
  vdf_unicorn_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKb0lg55201cjw35oxdmju8uejd (vdf_unicorn_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
