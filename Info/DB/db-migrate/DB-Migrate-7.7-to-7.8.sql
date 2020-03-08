USE `shg`;

CREATE TABLE IF NOT EXISTS `shg`.`m_profiling_type` (
  `point_id` INT(11) NOT NULL AUTO_INCREMENT,
  `point` VARCHAR(45) NOT NULL,
  `profile_for` VARCHAR(25) NOT NULL,
  `category` VARCHAR(45) NULL,
  `value_type` VARCHAR(25) NOT NULL,
  `options` VARCHAR(1000) NULL,
  PRIMARY KEY (`point_id`))
ENGINE = InnoDB

ALTER TABLE `shg`.`g_ac` 
ADD COLUMN `net_profit_proj_am` DECIMAL(16,2) NULL DEFAULT NULL AFTER `net_profit_am`;

ALTER TABLE `shg`.`monthly_g_ac` 
ADD COLUMN `net_profit_proj_am` DECIMAL(16,2) NULL DEFAULT NULL AFTER `net_profit_am`;

ALTER TABLE `shg`.`message_type` 
CHANGE COLUMN `message_type` `message_type` VARCHAR(30) NOT NULL ,
CHANGE COLUMN `sms_format` `sms_format` VARCHAR(500) NULL DEFAULT NULL ,
CHANGE COLUMN `email_format` `email_format` VARCHAR(1000) NULL DEFAULT NULL ;

