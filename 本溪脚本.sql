ALTER TABLE doc_pre_visit ADD COLUMN `glaucoma` INT(2) DEFAULT NULL COMMENT '�����';
ALTER TABLE doc_pre_visit ADD COLUMN `specialTreatmentOther` VARCHAR(40) DEFAULT NULL COMMENT '����Ŀǰ��ҩ';
ALTER TABLE doc_pre_visit ADD COLUMN `riskAssessment` INT(2) DEFAULT NULL COMMENT '��������';
ALTER TABLE doc_pre_visit ADD COLUMN `heartBoolHave` INT(2) DEFAULT NULL COMMENT '��Ѫ��ϵͳ';
ALTER TABLE doc_pre_visit ADD COLUMN `lungbreathHave` INT(2) DEFAULT NULL COMMENT '����ϵͳ';
ALTER TABLE doc_pre_visit ADD COLUMN `brainNerveHave` INT(2) DEFAULT NULL COMMENT '��ϵͳ';
ALTER TABLE doc_pre_visit ADD COLUMN `spineLimbHave` INT(2) DEFAULT NULL COMMENT '��������֫';
ALTER TABLE doc_pre_visit ADD COLUMN `bloodHave` INT(2) DEFAULT NULL COMMENT 'ѪҺϵͳ';
ALTER TABLE doc_pre_visit ADD COLUMN `kidneyHave` INT(2) DEFAULT NULL COMMENT '���༲��';
ALTER TABLE doc_pre_visit ADD COLUMN `digestionHave` INT(2) DEFAULT NULL COMMENT '����ϵͳ';
ALTER TABLE doc_pre_visit ADD COLUMN `endocrineHave` INT(2) DEFAULT NULL COMMENT '�ڷ���ϵͳ';
ALTER TABLE doc_pre_visit ADD COLUMN `infectiousHave` INT(2) DEFAULT NULL COMMENT '��Ⱦ��';
ALTER TABLE doc_pre_visit ADD COLUMN `drugAbuseCond` VARCHAR(40) DEFAULT NULL COMMENT 'ҩ������ʷ';


ALTER TABLE `doc_post_follow_record` ADD COLUMN `heartrate` VARCHAR(20) DEFAULT NULL COMMENT '����';
ALTER TABLE `doc_post_follow_record` ADD COLUMN `postDoctorAdviceFlag` VARCHAR(2) DEFAULT NULL COMMENT '����ҽ����ʶ';
ALTER TABLE `doc_post_follow_record` ADD COLUMN `postDoctorAdvice` VARCHAR(20) DEFAULT NULL COMMENT '����ҽ��';
ALTER TABLE `doc_post_follow_record` ADD COLUMN `postDoctorAdviceOther` VARCHAR(40) DEFAULT NULL COMMENT '��������ҽ��';
ALTER TABLE `doc_post_follow_record` ADD COLUMN `cognitiveDisordersOther` VARCHAR(100) DEFAULT NULL COMMENT '֫��о�/�˶��ϰ�����';
ALTER TABLE `doc_post_follow_record` ADD COLUMN `intraoperAwareOther` VARCHAR(100) DEFAULT NULL COMMENT '��������';


ALTER TABLE `doc_opt_nurse` ADD COLUMN `antibiotic` INT(1) DEFAULT NULL COMMENT '���п�����';
ALTER TABLE `doc_opt_nurse` ADD COLUMN `antibioticSign` VARCHAR(40) DEFAULT NULL COMMENT '������ʹ��ǩ��';
ALTER TABLE `doc_opt_nurse` ADD COLUMN `antibioticTime` DATETIME DEFAULT NULL COMMENT '������ʹ��ʱ��';
ALTER TABLE `doc_opt_nurse` ADD COLUMN `catheterizaSign` VARCHAR(40) DEFAULT NULL COMMENT '�������ǩ��';
ALTER TABLE `doc_opt_nurse` ADD COLUMN `catheterizaTime` DATETIME DEFAULT NULL COMMENT '�������ʱ��';
ALTER TABLE `doc_opt_nurse` ADD COLUMN `postOperPathSign` VARCHAR(40) DEFAULT NULL COMMENT '���������ǩ��';
ALTER TABLE `doc_opt_nurse` ADD COLUMN `postOperPathTime` DATETIME DEFAULT NULL COMMENT '���������ʱ��';
ALTER TABLE `doc_opt_nurse` ADD COLUMN `bloodSign` VARCHAR(100) DEFAULT NULL COMMENT '��Ѫǩ��';
ALTER TABLE `doc_opt_nurse` ADD COLUMN `bloodTime` DATETIME DEFAULT NULL COMMENT '��Ѫʱ��';