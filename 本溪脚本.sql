ALTER TABLE doc_pre_visit ADD COLUMN `glaucoma` INT(2) DEFAULT NULL COMMENT '青光眼';
ALTER TABLE doc_pre_visit ADD COLUMN `specialTreatmentOther` VARCHAR(40) DEFAULT NULL COMMENT '其他目前用药';
ALTER TABLE doc_pre_visit ADD COLUMN `riskAssessment` INT(2) DEFAULT NULL COMMENT '风险评估';
ALTER TABLE doc_pre_visit ADD COLUMN `heartBoolHave` INT(2) DEFAULT NULL COMMENT '心血管系统';
ALTER TABLE doc_pre_visit ADD COLUMN `lungbreathHave` INT(2) DEFAULT NULL COMMENT '呼吸系统';
ALTER TABLE doc_pre_visit ADD COLUMN `brainNerveHave` INT(2) DEFAULT NULL COMMENT '神经系统';
ALTER TABLE doc_pre_visit ADD COLUMN `spineLimbHave` INT(2) DEFAULT NULL COMMENT '脊柱与四肢';
ALTER TABLE doc_pre_visit ADD COLUMN `bloodHave` INT(2) DEFAULT NULL COMMENT '血液系统';
ALTER TABLE doc_pre_visit ADD COLUMN `kidneyHave` INT(2) DEFAULT NULL COMMENT '肾脏疾病';
ALTER TABLE doc_pre_visit ADD COLUMN `digestionHave` INT(2) DEFAULT NULL COMMENT '消化系统';
ALTER TABLE doc_pre_visit ADD COLUMN `endocrineHave` INT(2) DEFAULT NULL COMMENT '内分泌系统';
ALTER TABLE doc_pre_visit ADD COLUMN `infectiousHave` INT(2) DEFAULT NULL COMMENT '传染病';
ALTER TABLE doc_pre_visit ADD COLUMN `drugAbuseCond` VARCHAR(40) DEFAULT NULL COMMENT '药物滥用史';


ALTER TABLE `doc_post_follow_record` ADD COLUMN `heartrate` VARCHAR(20) DEFAULT NULL COMMENT '心率';
ALTER TABLE `doc_post_follow_record` ADD COLUMN `postDoctorAdviceFlag` VARCHAR(2) DEFAULT NULL COMMENT '术后医嘱标识';
ALTER TABLE `doc_post_follow_record` ADD COLUMN `postDoctorAdvice` VARCHAR(20) DEFAULT NULL COMMENT '术后医嘱';
ALTER TABLE `doc_post_follow_record` ADD COLUMN `postDoctorAdviceOther` VARCHAR(40) DEFAULT NULL COMMENT '其他术后医嘱';
ALTER TABLE `doc_post_follow_record` ADD COLUMN `cognitiveDisordersOther` VARCHAR(100) DEFAULT NULL COMMENT '肢体感觉/运动障碍详情';
ALTER TABLE `doc_post_follow_record` ADD COLUMN `intraoperAwareOther` VARCHAR(100) DEFAULT NULL COMMENT '处置详情';


ALTER TABLE `doc_opt_nurse` ADD COLUMN `antibiotic` INT(1) DEFAULT NULL COMMENT '术中抗生素';
ALTER TABLE `doc_opt_nurse` ADD COLUMN `antibioticSign` VARCHAR(40) DEFAULT NULL COMMENT '抗生素使用签名';
ALTER TABLE `doc_opt_nurse` ADD COLUMN `antibioticTime` DATETIME DEFAULT NULL COMMENT '抗生素使用时间';
ALTER TABLE `doc_opt_nurse` ADD COLUMN `catheterizaSign` VARCHAR(40) DEFAULT NULL COMMENT '导尿添加签名';
ALTER TABLE `doc_opt_nurse` ADD COLUMN `catheterizaTime` DATETIME DEFAULT NULL COMMENT '导尿添加时间';
ALTER TABLE `doc_opt_nurse` ADD COLUMN `postOperPathSign` VARCHAR(40) DEFAULT NULL COMMENT '术后病理添加签名';
ALTER TABLE `doc_opt_nurse` ADD COLUMN `postOperPathTime` DATETIME DEFAULT NULL COMMENT '术后病理添加时间';
ALTER TABLE `doc_opt_nurse` ADD COLUMN `bloodSign` VARCHAR(100) DEFAULT NULL COMMENT '输血签名';
ALTER TABLE `doc_opt_nurse` ADD COLUMN `bloodTime` DATETIME DEFAULT NULL COMMENT '输血时间';