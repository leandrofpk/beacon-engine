CREATE TABLE vdf_seed (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  origin varchar(50) DEFAULT NULL,
  seed varchar(255) DEFAULT NULL,
  vdf_pulse_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY fk_vdf_seed_1 (vdf_pulse_id),
  CONSTRAINT fk_vdf_seed_1 FOREIGN KEY (vdf_pulse_id) REFERENCES vdf_pulse (id) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
