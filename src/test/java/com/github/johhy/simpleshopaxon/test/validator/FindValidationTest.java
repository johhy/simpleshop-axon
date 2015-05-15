package com.github.johhy.simpleshopaxon.test.validator;


import org.axonframework.commandhandling.interceptors.JSR303ViolationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.johhy.simpleshopaxon.facade.FacadeCommandService;
import com.github.johhy.simpleshopaxon.test.SpringAbstractSimpleShopAxonTest;

public class FindValidationTest extends SpringAbstractSimpleShopAxonTest{

    
    @Autowired
    private FacadeCommandService fcs;
    
    @Test(expected=JSR303ViolationException.class)
    public void testProductExistsValidation() throws Exception {
	fcs.createProduct("p1", 10, 45.0);;
	fcs.createProduct("p1", 5, 4.0);
    }
    
    @Test(expected=JSR303ViolationException.class)
    public void testProductNotFoundValidation() throws Exception {
	fcs.changeCapacity("p1", 10);
    }
    
    @Test(expected=JSR303ViolationException.class)
    public void testCustomerNotFoundValidation() throws Exception {
	fcs.changeCustomerAddress("c1", "addr1");
    }
    
    @Test(expected=JSR303ViolationException.class)
    public void testOrderNotFoundValidation() throws Exception {
	fcs.changeOrderStatus("o1", 1);
    }
    
}
