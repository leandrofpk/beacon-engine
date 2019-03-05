CREATE TABLE user (
  id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL,
  password VARCHAR(512) NOT NULL,
  chain INT NOT NULL,
  active BIT NULL,
  desc_entropy VARCHAR(512) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX user_UNIQUE (username ASC),
  INDEX user_username_index (username ASC));
