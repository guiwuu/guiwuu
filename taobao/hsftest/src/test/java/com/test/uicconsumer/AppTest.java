package com.test.uicconsumer;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taobao.hsf.hsfunit.HSFEasyStarter;
import com.taobao.hsf.hsfunit.util.ServiceUtil;
import com.taobao.lottery.base.hsf.ExperienceService;
import com.taobao.uic.common.domain.BaseUserDO;
import com.taobao.uic.common.service.userinfo.UicReadService;

public class AppTest {

	@Test
	public void testApp() throws Exception{
		HSFEasyStarter.start("d:/hsf/release", "");//在用到consumer bean前启动hsf
		String springResourcePath = "spring-hsf-uic-consumer.xml";
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				springResourcePath);
		 ExperienceService experienceService = ( ExperienceService) ctx.getBean("experienceService");
		System.out.println("user[id:10000L] nick:" + experienceService.queryRules(null));
	}
	
}