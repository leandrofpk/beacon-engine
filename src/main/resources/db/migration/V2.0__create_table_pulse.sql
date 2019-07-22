CREATE TABLE pulse (
                         id bigint(20) NOT NULL,
                         certificate_id varchar(255) DEFAULT NULL,
                         chain_index bigint(20) NOT NULL,
                         cipher_suite int(11) NOT NULL,
                         local_random_value varchar(255) DEFAULT NULL,
                         output_value varchar(255) DEFAULT NULL,
                         period int(11) NOT NULL,
                         precommitment_value varchar(255) DEFAULT NULL,
                         pulse_index bigint(20) NOT NULL,
                         signature_value varchar(255) DEFAULT NULL,
                         status_code int(11) NOT NULL,
                         time_stamp datetime DEFAULT NULL,
                         uri varchar(255) DEFAULT NULL,
                         version varchar(255) DEFAULT NULL,
                         PRIMARY KEY (id),
                         UNIQUE KEY UK1t9peie90oq11xjedtedj2ea (time_stamp,chain_index)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
