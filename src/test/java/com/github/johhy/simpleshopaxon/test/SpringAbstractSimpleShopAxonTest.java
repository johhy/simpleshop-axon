package com.github.johhy.simpleshopaxon.test;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.johhy.simpleshopaxon.Application;
import com.github.johhy.simpleshopaxon.test.config.ApplicationTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, ApplicationTestConfig.class})
@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
abstract public class SpringAbstractSimpleShopAxonTest extends AbstractSimpleShopAxonTest {

}
