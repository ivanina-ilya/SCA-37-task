DROP TABLE IF EXISTS USERS;
CREATE TABLE USERS
(
  id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  firstName VARCHAR(64) NOT NULL,
  lastName VARCHAR(64),
  birthday DATETIME,
  email VARCHAR(64) NOT NULL,
  roles VARCHAR(255),
  passwordHash VARCHAR(255)
);
CREATE UNIQUE INDEX users_email_uindex ON USERS (email);
-- ----------------------------------------------------

DROP TABLE IF EXISTS AUDITORIUMS;
CREATE TABLE AUDITORIUMS
(
  id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  name VARCHAR(64) NOT NULL,
  seats INT DEFAULT 0 NOT NULL,
  vipSeats VARCHAR(512)
);
CREATE UNIQUE INDEX AUDITORIUMS_name_uindex ON AUDITORIUMS (name);
-- ---------------------------------------------------

DROP TABLE IF EXISTS EVENTS;
CREATE TABLE EVENTS
(
  id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  price DECIMAL,
  rating VARCHAR(32),
  name VARCHAR(64) NOT NULL,
  duration BIGINT
);
CREATE UNIQUE INDEX EVENTS_name_uindex ON EVENTS (name);
-- ----------------------------------------------------

DROP TABLE IF EXISTS EventSchedule;
CREATE TABLE EventSchedule
(
  id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  event_id BIGINT NOT NULL,
  auditorium_id BIGINT NOT NULL,
  startDateTime DATETIME NOT NULL
);
CREATE UNIQUE INDEX EventSchedule_event_id_auditorium_id_startDateTime_uindex ON EventSchedule (event_id, auditorium_id, startDateTime);
-- ---------------------------------------------------

DROP TABLE IF EXISTS TICKETS;
CREATE TABLE TICKETS
(
  id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  user_id BIGINT,
  eventSchedule_id BIGINT NOT NULL,
  seat INT,
  price DECIMAL,
  added DATETIME DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS TICKETS_PREBOOK;
CREATE TABLE TICKETS_PREBOOK
(
  id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  user_id BIGINT,
  eventSchedule_id BIGINT NOT NULL,
  seat INT,
  price DECIMAL,
  added DATETIME DEFAULT CURRENT_TIMESTAMP
);

