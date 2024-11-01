/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author: nddmanh
 * Created: Oct 18, 2021
 */

CREATE TABLE `btlltm`.`users` (
  `userId` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `score` FLOAT NOT NULL,
  `win` INT NOT NULL,
  `draw` INT NOT NULL,
  `lose` INT NOT NULL,
  `avgCompetitor` FLOAT NOT NULL,
  `avgTime` FLOAT NOT NULL,
  PRIMARY KEY (`userId`));

CREATE TABLE `btlltm`.`games`(
    `gameId` INT NOT NULL AUTO_INCREMENT,
    `user1` VARCHAR(45) NOT NULL,
    `user2` VARCHAR(45) NOT NULL,
    `score1` INT NOT NULL,
    `score2` INT NOT NULL,
    `roomId` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`gameId`)
);