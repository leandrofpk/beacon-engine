CREATE TABLE chain (
  id int(11) NOT NULL AUTO_INCREMENT,
  active bit(1) NOT NULL,
  chain_index bigint(20) NOT NULL,
  cipher_suite int(11) NOT NULL,
  cipher_suite_description varchar(255) NOT NULL,
  period int(11) NOT NULL,
  version varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO chain (active,chain_index,cipher_suite,cipher_suite_description,period,version)
VALUES(1,1,0,'SHA512 hashing and RSA signatures with PKCSv1.5',60000,'2.0');
