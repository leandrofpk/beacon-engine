CREATE TABLE list_value (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  type varchar(255) DEFAULT NULL,
  uri varchar(255) DEFAULT NULL,
  value varchar(255) DEFAULT NULL,
  pulse_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK501fr5f1dx73u2yu62j4mwrwx (pulse_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
