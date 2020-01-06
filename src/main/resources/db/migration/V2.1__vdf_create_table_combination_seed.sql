CREATE TABLE vdf_combination_seed (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  cumulative_hash varchar(255) DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  seed varchar(255) DEFAULT NULL,
  seed_index int(11) NOT NULL,
  time_stamp datetime DEFAULT NULL,
  uri varchar(255) DEFAULT NULL,
  combination_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKauf9e7qmnaldrn9lx5grp60fu (combination_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
