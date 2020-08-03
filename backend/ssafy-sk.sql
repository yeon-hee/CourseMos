# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `uid` varchar(128) NOT NULL,
  `email` varchar(128) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `nickname` varchar(30) DEFAULT NULL,
  `create_date` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`uid`),
  UNIQUE KEY `user_idx_unique_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


