-- MySQL Script generated by MySQL Workbench
-- Wed Jul  3 13:58:17 2019
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema blogdemo
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema blogdemo
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `blogdemo` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `blogdemo` ;

-- -----------------------------------------------------
-- Table `blogdemo`.`comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `blogdemo`.`comments` (
  `id` INT(10) NOT NULL,
  `comment` VARCHAR(500) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `blogdemo`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `blogdemo`.`users` (
  `id` INT(10) NOT NULL,
  `username` VARCHAR(50) NULL DEFAULT NULL,
  `password` VARCHAR(50) NULL DEFAULT NULL,
  `firstName` VARCHAR(50) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `lastName` VARCHAR(50) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `blogdemo`.`posts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `blogdemo`.`posts` (
  `id` INT(10) NOT NULL,
  `post` VARCHAR(10000) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `user_id` INT(10) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_id` (`user_id` ASC) VISIBLE,
  CONSTRAINT `posts_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `blogdemo`.`users` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `blogdemo`.`post_comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `blogdemo`.`post_comments` (
  `post_id` INT(10) NULL DEFAULT NULL,
  `comment_id` INT(10) NOT NULL,
  PRIMARY KEY (`comment_id`),
  INDEX `post_id` (`post_id` ASC) VISIBLE,
  CONSTRAINT `post_comments_ibfk_1`
    FOREIGN KEY (`post_id`)
    REFERENCES `blogdemo`.`posts` (`id`),
  CONSTRAINT `post_comments_ibfk_2`
    FOREIGN KEY (`comment_id`)
    REFERENCES `blogdemo`.`comments` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `blogdemo`.`tags`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `blogdemo`.`tags` (
  `id` INT(10) NOT NULL,
  `category` VARCHAR(50) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `blogdemo`.`posts_tags`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `blogdemo`.`posts_tags` (
  `post_id` INT(10) NOT NULL,
  `tag_id` INT(10) NOT NULL,
  PRIMARY KEY (`tag_id`, `post_id`),
  INDEX `post_id` (`post_id` ASC) VISIBLE,
  CONSTRAINT `posts_tags_ibfk_1`
    FOREIGN KEY (`post_id`)
    REFERENCES `blogdemo`.`posts` (`id`),
  CONSTRAINT `posts_tags_ibfk_2`
    FOREIGN KEY (`tag_id`)
    REFERENCES `blogdemo`.`tags` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `blogdemo`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `blogdemo`.`roles` (
  `id` INT(10) NOT NULL,
  `name` VARCHAR(50) NULL DEFAULT NULL,
  `description` VARCHAR(500) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `blogdemo`.`roles_users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `blogdemo`.`roles_users` (
  `user_id` INT(10) NOT NULL,
  `role_id` INT(10) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`),
  INDEX `role_id` (`role_id` ASC) VISIBLE,
  CONSTRAINT `roles_users_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `blogdemo`.`users` (`id`),
  CONSTRAINT `roles_users_ibfk_2`
    FOREIGN KEY (`role_id`)
    REFERENCES `blogdemo`.`roles` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
