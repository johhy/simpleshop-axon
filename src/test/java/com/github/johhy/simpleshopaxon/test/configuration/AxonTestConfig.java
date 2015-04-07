package com.github.johhy.simpleshopaxon.test.configuration;


import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.gateway.CommandGatewayFactoryBean;
import org.axonframework.common.jpa.ContainerManagedEntityManagerProvider;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.contextsupport.spring.AnnotationDriven;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.jpa.JpaEventStore;
import org.axonframework.repository.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.github.johhy.simpleshopaxon.core.commandgateway.CustomerCommandGateway;
import com.github.johhy.simpleshopaxon.core.commandgateway.ProductCellCommandGateway;
import com.github.johhy.simpleshopaxon.core.commandgateway.ProductCellCustomerTransferServiceCommandGateway;
import com.github.johhy.simpleshopaxon.core.domain.customer.Customer;
import com.github.johhy.simpleshopaxon.core.domain.customer.CustomerAggregateFactory;
import com.github.johhy.simpleshopaxon.core.domain.customer.CustomerLogicaImpl;
import com.github.johhy.simpleshopaxon.core.domain.customer.OrderLogicaImpl;
import com.github.johhy.simpleshopaxon.core.domain.productcell.ProductCell;
import com.github.johhy.simpleshopaxon.core.domain.productcell.ProductCellAggregateFactory;
import com.github.johhy.simpleshopaxon.core.domain.productcell.ProductCellLogicaImpl;


@Configuration
@ComponentScan("com.github.johhy.simpleshopaxon")
@AnnotationDriven
public class AxonTestConfig {

	 @Bean
	 public CustomerCommandGateway customerCommandGateway() throws Exception {
		 CommandGatewayFactoryBean<CustomerCommandGateway> cgfb = 
				 new CommandGatewayFactoryBean<CustomerCommandGateway>();
		 cgfb.setCommandBus(commandBus());
		 cgfb.setGatewayInterface(CustomerCommandGateway.class);
		 cgfb.afterPropertiesSet();
		 return cgfb.getObject();
	 }
	 
	 @Bean
	 public ProductCellCommandGateway productCellCommandGateway() throws Exception {
		 CommandGatewayFactoryBean<ProductCellCommandGateway> cgfb = 
				 new CommandGatewayFactoryBean<ProductCellCommandGateway>();
		 cgfb.setCommandBus(commandBus());
		 cgfb.setGatewayInterface(ProductCellCommandGateway.class);
		 cgfb.afterPropertiesSet();
		 return cgfb.getObject();
	 }
	 
	 @Bean
	 public ProductCellCustomerTransferServiceCommandGateway productCellCustomerTransferServiceCommandGateway() throws Exception {
		 CommandGatewayFactoryBean<ProductCellCustomerTransferServiceCommandGateway> cgfb = 
				 new CommandGatewayFactoryBean<ProductCellCustomerTransferServiceCommandGateway>();
		 cgfb.setCommandBus(commandBus());
		 cgfb.setGatewayInterface(ProductCellCustomerTransferServiceCommandGateway.class);
		 cgfb.afterPropertiesSet();
		 return cgfb.getObject();
	 }
	   
	 @Bean
	 public CommandBus commandBus() {
		 return new SimpleCommandBus();
	 } 
	 
	 @Bean
	 public EventStore eventStore() {
		 return new JpaEventStore(entityManagerProvider());
	 }
	 
	 @Bean
	 public EntityManagerProvider entityManagerProvider() {
		 return new ContainerManagedEntityManagerProvider();
	 }
	 	  
	 @Bean
	 public EventBus eventBus() {
		 return new SimpleEventBus();
	 } 
	 
	 @Bean
	 public Repository<Customer> customerRepo() {
		 EventSourcingRepository<Customer> repository = 
			 new EventSourcingRepository<Customer>(customerFactory(), eventStore());
		 repository.setEventBus(eventBus());
		 return repository;
	 } 
	 
	 @Bean
	 public Repository<ProductCell> productCellRepo() {
		 EventSourcingRepository<ProductCell> repository = 
			 new EventSourcingRepository<ProductCell>(productCellFactory(), eventStore());
		 repository.setEventBus(eventBus());
		 return repository;
	 } 
	 
	 @Bean
	 public ProductCellAggregateFactory productCellFactory() {
		 return new ProductCellAggregateFactory(ProductCellLogicaImpl.class);
	 }
	 
	 @Bean
	 public CustomerAggregateFactory customerFactory() {
		 return new CustomerAggregateFactory(CustomerLogicaImpl.class, OrderLogicaImpl.class);
	 }
	 
	 @SuppressWarnings("rawtypes")
	 @Bean
	 public AggregateAnnotationCommandHandler aggregateAnnotationCommandHandler() {
		 AggregateAnnotationCommandHandler
				 .subscribe(Customer.class, customerRepo(), commandBus());	
		 return AggregateAnnotationCommandHandler
				 .subscribe(ProductCell.class, productCellRepo(), commandBus());
	 }
	 

}
