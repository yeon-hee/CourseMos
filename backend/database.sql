drop table if exists `user`;
 
CREATE TABLE `user` (
  `Email` varchar(128) NOT NULL,
  `Password` varchar(128) NOT NULL,
  `UserId` varchar(30) NOT NULL,
  `ProfilePhoto` varchar(200) DEFAULT NULL,
  `ProfileComment` varchar(200) DEFAULT NULL,
  `CreateDate` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`Email`),
  UNIQUE KEY `user_idx_unique_userid` (`UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



drop table if exists `countinfo`;
 
CREATE TABLE `countinfo` (
  `FollowingCount` int DEFAULT NULL,
  `FollowerCount` int DEFAULT NULL,
  `PostCount` int DEFAULT NULL,
  `TotalLikeCount` int DEFAULT NULL,
  `Email` varchar(128) NOT NULL,
  FOREIGN KEY (`Email`)
    REFERENCES `user`(`Email`)
    ON UPDATE CASCADE ON DELETE CASCADE
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



drop table if exists `post`;
 
CREATE TABLE `post` (
  `PostNo` int NOT NULL AUTO_INCREMENT,
  `UserId` varchar(30) DEFAULT NULL,
  `Contents` varchar(1000) DEFAULT NULL,
  `LikeCount` int DEFAULT NULL,
  `WriteDate` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`PostNo`),
  FOREIGN KEY (`UserId`)
    REFERENCES `User`(`UserId`)
    ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


drop table if exists `photo`;
 
CREATE TABLE `photo` (
  `PhotoUrl` varchar(128) DEFAULT NULL,
  `PostNo` int NOT NULL AUTO_INCREMENT,
  FOREIGN KEY (`PostNo`)
  REFERENCES `post`(`PostNo`)
  ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




drop table if exists `comment`;
 
CREATE TABLE `Comment` (
  `PostNo` int DEFAULT NULL,
  `CommentNo` int NOT NULL AUTO_INCREMENT,
  `Writer` varchar(128) DEFAULT NULL,
  `Content` varchar(300) DEFAULT NULL,
  `WriteDate` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`PostNo`, `CommentNo`),
  FOREIGN KEY (`PostNo`)
  REFERENCES `post`(`PostNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




drop table if exists `following`;
 
CREATE TABLE `following` (
  `Follower` varchar(30) DEFAULT NULL,
  `FollowingTarget` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`Follower`, `FollowingTarget`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




drop table if exists `alert`;
 
CREATE TABLE `alert` (
  `AlertNo` int not null auto_increment,
  `Email` varchar(30) DEFAULT NULL,
  `Message` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`Email`, AlertNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



drop table if exists `request`;
 
CREATE TABLE `request` (
  `Follower` varchar(30) DEFAULT NULL,
  `FollowingTarget` varchar(30) DEFAULT NULL,
  UNIQUE KEY `user_idx_unique_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


