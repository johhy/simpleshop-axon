package com.github.johhy.simpleshopaxon.test;

import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

abstract public class AbstractSimpleShopAxonTest {

	@Rule
	public TestRule watcher = new TestWatcher() {
	   protected void starting(Description description) {
		  System.out.println();
		  System.out.println("***** Starting test: " + 
				  description.getMethodName() + " *****");
	   }
	};

}
