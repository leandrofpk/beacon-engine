CREATE TABLE chain (
  id int(11) NOT NULL AUTO_INCREMENT,
  active bit(1) NOT NULL,
  chain_index bigint(20) NOT NULL,
  cipher_suite int(11) NOT NULL,
  cipher_suite_description varchar(255) NOT NULL,
  period int(11) NOT NULL,
  version_uri varchar(255) NOT NULL,
  version_pulse varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO chain (active,chain_index,cipher_suite,cipher_suite_description,period,version_uri, version_pulse)
VALUES(1,1,0,'SHA512 hashing and RSA signatures with PKCSv1.5',60000,'2.0','Version 2.0');
