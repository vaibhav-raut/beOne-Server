USE `shg`;

ALTER TABLE `shg`.`message_type` 
CHANGE COLUMN `message_type_id` `message_type_id` INT(10) UNSIGNED NOT NULL ,
ADD COLUMN `lang_id` INT(10) UNSIGNED NOT NULL AFTER `message_type_id`,
ADD INDEX `message_type_ibfk_1_idx` (`lang_id` ASC);
ALTER TABLE `shg`.`message_type` 
ADD CONSTRAINT `message_type_ibfk_1`
  FOREIGN KEY (`lang_id`)
  REFERENCES `shg`.`lang` (`lang_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `shg`.`message_type` 
CHANGE COLUMN `message_type_id` `message_type_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ;

ALTER TABLE `shg`.`doc` 
ADD COLUMN `g_ac_no` INT(10) NOT NULL AFTER `doc_type_id`;

