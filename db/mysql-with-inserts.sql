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

INSERT INTO `letta`.`users` (`login`,`password`,`role`,`mail`)
VALUES ('admin', '713bfda78870bf9d1b261f565286f85e97ee614efe5f0faf7c34e7ca4f65baca','ADMIN','admin1234@gmail.com');
INSERT INTO `letta`.`users` (`login`,`password`,`role`,`mail`)
VALUES ('normal', '7bf24d6ca2242430343ab7e3efb89559a47784eea1123be989c1b2fb2ef66e83','USER','user1234@gmail.com');

INSERT INTO `letta`.`event` (`id`,`title`,`description`,`category`,`eventdate`,`place`,`capacity`,`image`,`extension`,`managerid`)
VALUES ('0', 'Reunion de lectura','Este evento ha sido creado para que los amantes de la lectura puedan discutir sus libros favoritos.','libros','2023-05-12 00:00:00','Vigo','6','','',1);

INSERT INTO `letta`.`event` (`id`,`title`,`description`,`category`,`eventdate`,`place`,`capacity`,`image`,`extension`,`managerid`)
VALUES ('0', 'Peliculas de miedo','Este evento ha sido creado para que los amantes de las peliculas de miedo puedan discutir sus opiniones.','cine','2022-02-15 10:05:00','Ourense','10','','',1);

INSERT INTO `letta`.`event` (`id`,`title`,`description`,`category`,`eventdate`,`place`,`capacity`,`image`,`extension`,`managerid`)
VALUES ('0', 'Eurovisión 2022','Quedada para ver eurovisión','television','2022-02-25 15:00:00','Ourense','4','','',1);

INSERT INTO `letta`.`event` (`id`,`title`,`description`,`category`,`eventdate`,`place`,`capacity`,`image`,`extension`,`managerid`)
VALUES ('0', 'Cine al aire libre','Peliculas mudas al aire libre. Patrocinado por el concello','cine','2022-02-15 22:00:00','Ourense, en frente a la catedral','20','','',1);

INSERT INTO `letta`.`event` (`id`,`title`,`description`,`category`,`eventdate`,`place`,`capacity`,`image`,`extension`,`managerid`)
VALUES ('0', 'Clase de aerobic al aire libre','Clase impartida por Emilia Julieta','deportes','2022-05-26 18:00:00','Ourense, parque de facultad de economia','15','','',1);

INSERT INTO `letta`.`event` (`id`,`title`,`description`,`category`,`eventdate`,`place`,`capacity`,`image`,`extension`,`managerid`)
VALUES ('0', 'Visita por el mercado','Paseo por el mercado de ourense','ocio','2022-07-26 15:00:00','Mercado de Ourense','6','','',1);

INSERT INTO `letta`.`event` (`id`,`title`,`description`,`category`,`eventdate`,`place`,`capacity`,`image`,`extension`,`managerid`)
VALUES ('0', 'Maratón de Juego de Tronos','Quedada para ver la ultima temporada de juego de tronos y comentarla','series','2022-07-26 15:00:00','Mercado de Ourense','6','','',1);

INSERT INTO `letta`.`event` (`id`,`title`,`description`,`category`,`eventdate`,`place`,`capacity`,`image`,`extension`,`managerid`)
VALUES ('0', 'Clase de pintura','Clase impartida por la profesora Maria Antonieta','ocio','2022-04-02 17:00:00','Avenida de Marques Domingo n6','6','','',1);

INSERT INTO `letta`.`event` (`id`,`title`,`description`,`category`,`eventdate`,`place`,`capacity`,`image`,`extension`,`managerid`)
VALUES ('0', 'Clase de body combat','Clase impartida por la profesora Emilia Maria de los Dolores','deportes','2022-07-26 15:00:00','Avenida de Rodolfo Suarez n10','8','','',1);

INSERT INTO `letta`.`event` (`id`,`title`,`description`,`category`,`eventdate`,`place`,`capacity`,`image`,`extension`,`managerid`)
VALUES ('0', 'Concierto de rock clasico por parte del grupo ACTM','El grupo de ACTM es un grupo indi de rock neoclasico que junta las nuevas taecnologías con un estilo clásico. Es sin duda una revolución de la musica contemporánea','ocio','2023-10-8 18:00:00','Avenida de Maria Julieta numero 2009,Portal B,Atico C. Concello de Ourense, C.P.32004 Ourense,España','20','','',1);

INSERT INTO `letta`.`event` (`id`,`title`,`description`,`category`,`eventdate`,`place`,`capacity`,`image`,`extension`,`managerid`)
VALUES ('0', 'Detective Pikachu','Pika pika','cine','2023-10-16 18:00:00','Cines pontevella','5','','',1);


INSERT INTO `letta`.`users_event` (`user_id`,`event_id`)
VALUES(1,1);