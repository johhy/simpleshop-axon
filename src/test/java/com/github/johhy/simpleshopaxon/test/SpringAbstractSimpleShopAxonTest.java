package com.github.johhy.simpleshopaxon.test;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.johhy.simpleshopaxon.test.configuration.AxonTestConfig;
import com.github.johhy.simpleshopaxon.test.configuration.JpaTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AxonTestConfig.class, JpaTestConfig.class})
@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
abstract public class SpringAbstractSimpleShopAxonTest extends AbstractSimpleShopAxonTest {

}
