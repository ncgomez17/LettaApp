DROP DATABASE IF EXISTS `letta`;
CREATE DATABASE `letta`;

CREATE TABLE `letta`.`users` (
	`id` int AUTO_INCREMENT,
	`login` varchar(50) NOT NULL,
	`password` varchar(255) NOT NULL,
	`role` VARCHAR(10) NOT NULL, 
	`mail` VARCHAR(255) NOT NULL, 
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `letta`.`event` (
	`id` int AUTO_INCREMENT,
	`title` varchar(50) NOT NULL,
	`description` varchar(255) NOT NULL,
	`category` VARCHAR(10) 
                CHECK (category IN('deportes','cine','teatro','television','series','libros','ocio')) NOT NULL,
	`eventdate` datetime NOT NULL,
	`place` varchar(100) NOT NULL,
	`capacity` int NOT NULL,
	`image` blob,
	`extension` varchar(10),
	`managerid` int NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`managerid`) REFERENCES users(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `letta`.`users_event` (
	`id` int AUTO_INCREMENT,
	`user_id` int NOT NULL,
	`event_id` int NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`user_id`) REFERENCES users(`id`),
	FOREIGN KEY (`event_id`) REFERENCES event(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE USER IF NOT EXISTS 'daa'@'localhost' IDENTIFIED WITH mysql_native_password BY 'daa';
GRANT ALL ON `letta`.* TO 'daa'@'localhost';


