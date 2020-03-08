USE `shg`;

ALTER TABLE `shg`.`tx_todo` 
ADD COLUMN `penalty_paid_am` DECIMAL(16,2) NULL DEFAULT NULL AFTER `penalty_am`;

ALTER TABLE `shg`.`g_ac` 
ADD COLUMN `c_m_outstanding_saving_am` DECIMAL(16,2) NULL DEFAULT NULL AFTER `c_m_saved_am`,
ADD COLUMN `a_m_outstanding_saving_am` DECIMAL(16,2) NULL DEFAULT NULL AFTER `a_m_saved_am`,
ADD COLUMN `outstanding_expenses_am` DECIMAL(16,2) NULL DEFAULT NULL AFTER `expenses_am`;

ALTER TABLE `shg`.`m_ac` 
ADD COLUMN `outstanding_saving_am` DECIMAL(16,2) NULL DEFAULT NULL AFTER `saved_am`;

ALTER TABLE `shg`.`monthly_g_ac` 
ADD COLUMN `c_m_outstanding_saving_am` DECIMAL(16,2) NULL DEFAULT NULL AFTER `c_m_saved_am`,
ADD COLUMN `a_m_outstanding_saving_am` DECIMAL(16,2) NULL DEFAULT NULL AFTER `a_m_saved_am`,
ADD COLUMN `outstanding_expenses_am` DECIMAL(16,2) NULL DEFAULT NULL AFTER `expenses_am`;

ALTER TABLE `shg`.`monthly_m_ac` 
ADD COLUMN `outstanding_saving_am` DECIMAL(16,2) NULL DEFAULT NULL AFTER `saved_am`;

ALTER TABLE `shg`.`bank_profile` 
CHANGE COLUMN `bank_name` `bank_name` VARCHAR(100) NOT NULL ,
CHANGE COLUMN `branch_name` `branch_name` VARCHAR(100) NOT NULL ;

ALTER TABLE `shg`.`member_contact` 
CHANGE COLUMN `first_name` `first_name` VARCHAR(30) NULL DEFAULT NULL ,
CHANGE COLUMN `middle_name` `middle_name` VARCHAR(30) NULL DEFAULT NULL ,
CHANGE COLUMN `last_name` `last_name` VARCHAR(30) NULL DEFAULT NULL ;

