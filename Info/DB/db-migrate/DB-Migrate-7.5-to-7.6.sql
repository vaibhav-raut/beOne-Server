ALTER TABLE `shg`.`monthly_g_loan_ac` 
ADD COLUMN `loan_ac_name` VARCHAR(50) DEFAULT NULL AFTER `loan_source_id`;
					