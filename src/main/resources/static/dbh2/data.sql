INSERT INTO `user` (`username`, `password`, `email`, `check_state`) VALUES
  ('xhan', 'xhanxhan', 'xhan@123.com', 1),
  ('xzhang', 'xzhangxzhang', 'xzhang@123.com', 1),
  ('xmma', 'xmmaxmma', 'xmma@123.com', 1),
  ('xyang', 'xyangxyang', 'xyang@123.com', 1),
  ('xtian', 'xtianxtian', 'xtian@123.com', 1),
  ('xluo', 'xluoxluo', 'xluo@123.com', 1),
  ('xlis', 'xlisxlis', 'xlis@123.com', 1);

INSERT INTO `current_relation`
(`host_id`, `friend_id`, `friend_name`) VALUES
  (1, 2, 'xzhang'), (1, 3, 'xmma'), (2, 3, 'xmma'),
  (3, 5, 'xtian'), (2, 1, 'xhan'), (3, 1, 'xhan'),
  (3, 2, 'xzhang'), (5, 3, 'xmma'), (7, 1, 'xhan'),
  (1, 7, 'xlis'), (5, 2, 'xzhang'), (2, 5, 'xtian'),
  (6, 7, 'xlis'), (7, 6, 'xluo');

INSERT INTO `delete_record`
(`raiser_id`, `acceptor_id`, `raise_time`)
VALUES (4, 7, current_timestamp());

INSERT INTO `raise_record`
(`raiser_id`, `acceptor_id`)
VALUES (4, 2), (4, 1), (6, 1), (6, 2);
