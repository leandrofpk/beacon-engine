CREATE TABLE vdf_seed_public (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  description varchar(50) DEFAULT NULL,
  seed varchar(255) DEFAULT NULL,
  vdf_public_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY fk_seed_public_1 (vdf_public_id),
  CONSTRAINT fk_seed_public_ FOREIGN KEY (vdf_public_id) REFERENCES vdf_public (id) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
