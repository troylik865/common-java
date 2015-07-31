package org.troy.system.test;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 基础测试类
 * @author wangj
 * 2013-5-30
 */
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml"})
@ActiveProfiles("production")
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseJunitCase extends AbstractTransactionalJUnit4SpringContextTests
{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
}
