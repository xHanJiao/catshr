CREATE TABLE `user` (
  user_id INTEGER PRIMARY KEY AUTO_INCREMENT,
  account VARCHAR(14) UNIQUE NOT NULL ,
  username VARCHAR(7) NOT NULL ,
  `password` VARCHAR(14) NOT NULL ,
  email VARCHAR(50) NOT NULL ,
  check_state BOOLEAN NOT NULL ,
  register_date DATE NOT NULL DEFAULT current_timestamp()
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
  `accept_time` DATETIME ,
  `current_state` CHAR(4) NOT NULL DEFAULT 'wait'
);
CREATE TABLE `message`(
  `message_id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `owner_id` INTEGER NOT NULL ,
  `send_time` DATETIME NOT NULL ,
  `description` VARCHAR(140) NOT NULL ,
  `MESSAGE_TYPE` CHAR(1) NOT NULL ,
  `T_FILE_LOCATION` VARCHAR(255)
);
