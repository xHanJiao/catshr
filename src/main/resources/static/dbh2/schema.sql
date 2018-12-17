CREATE TABLE `user` (
  user_id INTEGER PRIMARY KEY AUTO_INCREMENT,
  account VARCHAR(14) UNIQUE NOT NULL ,
  username VARCHAR(7) NOT NULL ,
  `password` VARCHAR(14) NOT NULL ,
  email VARCHAR(50) NOT NULL ,
  check_state BOOLEAN NOT NULL ,
  register_date DATE NOT NULL
);
CREATE TABLE `current_relation` (
  `relation_id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `raiser_id` INTEGER NOT NULL ,
  `acceptor_id` INTEGER NOT NULL
);
CREATE TABLE `delete_record`(
  `relation_id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `raiser_id` INTEGER NOT NULL ,
  `acceptor_id` INTEGER NOT NULL ,
  `raise_time` DATETIME NOT NULL ,
);
CREATE TABLE `raise_record`(
  `relation_id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `raiser_id` INTEGER NOT NULL ,
  `acceptor_id` INTEGER NOT NULL ,
  `raise_time` DATETIME NOT NULL ,
  `accept_time` DATETIME NOT NULL ,
  `current_state` VARCHAR(10) NOT NULL DEFAULT 'not accept'
);
