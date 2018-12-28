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
  `host_id` INTEGER NOT NULL ,
  `friend_id` INTEGER NOT NULL ,
  `friend_account`VARCHAR(14) NOT NULL ,
  `friend_username` VARCHAR(7) NOT NULL ,
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
  `content` VARCHAR(140) NOT NULL ,
  `display_comments` TEXT ,
  `DTYPE` CHAR(2) NOT NULL ,
  `file_location` VARCHAR(255)
);
CREATE TABLE `comment` (
  `comment_id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `owner_id` INTEGER NOT NULL,
  `message_id` INTEGER NOT NULL,
  `content` VARCHAR(140) NOT NULL,
  `comments` TEXT,
  `comment_time` TIMESTAMP NOT NULL,
  `DTYPE` CHAR(1) NOT NULL
);
