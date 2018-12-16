CREATE TABLE `unchecked_user` (
  user_id INTEGER PRIMARY KEY AUTO_INCREMENT,
  account VARCHAR(14) UNIQUE NOT NULL ,
  username VARCHAR(7) NOT NULL ,
  `password` VARCHAR(14) NOT NULL ,
  email VARCHAR(50) NOT NULL ,
  register_date DATE NOT NULL
);

CREATE TABLE `user` (
  user_id INTEGER PRIMARY KEY AUTO_INCREMENT,
  account VARCHAR(14) UNIQUE NOT NULL ,
  username VARCHAR(7) NOT NULL ,
  `password` VARCHAR(14) NOT NULL ,
  email VARCHAR(50) NOT NULL ,
  register_date DATE NOT NULL
);
