package com.digihealth.anesthesia.common.service;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

public class ActiveRecordConfig
{
	@Resource(name = "dataSource")
	private DataSource dataSource;

	@Bean(initMethod = "start", destroyMethod = "stop")
	public ActiveRecordPlugin init() {
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dataSource);
		return arp;
	}
}
