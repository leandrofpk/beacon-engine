CREATE TABLE entropy (
  id int(11) NOT NULL AUTO_INCREMENT,
  noise_source varchar(255) NOT NULL,
  period int(11) NOT NULL,
  raw_data varchar(255) NOT NULL,
  time_stamp datetime NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
