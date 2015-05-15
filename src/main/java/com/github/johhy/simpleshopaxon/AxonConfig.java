package com.github.johhy.simpleshopaxon;


import java.util.ArrayList;
import java.util.List;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.CommandDispatchInterceptor;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation
					.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.annotation
					.AggregateAnnotationCommandHandlerFactoryBean;
import org.axonframework.commandhandling.gateway.CommandGatewayFactoryBean;
import org.axonframework.commandhandling.interceptors.BeanValidationInterceptor;
import org.axonframework.common.annotation.SpringBeanParameterResolverFactory;
import org.axonframework.common.jpa.ContainerManagedEntityManagerProvider;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation
					.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.jpa.JpaEventStore;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.github.johhy.simpleshopaxon.core.Customer;
import com.github.johhy.simpleshopaxon.core.Order;
import com.github.johhy.simpleshopaxon.core.ProductCell;
import com.github.johhy.simpleshopaxon.core.api.shared
						.ApplicationCommandGateway;



/**
 * The Class AxonConfig.
 * 
 * @author johhy
 */
@Configuration
@EntityScan(basePackages = {"com.github.johhy.simpleshopaxon.query.tables",
"org.axonframework.eventstore.jpa"})
public class AxonConfig {

	/**
	 * Validator.
	 *
	 * @return the local validator factory bean
	 */
    	@Bean
	public LocalValidatorFactoryBean validator(){
		 return new LocalValidatorFactoryBean();
	}

	/**
	 * Command gateway.
	 *
	 * @return the application command gateway
	 * @throws Exception the exception
	 */
	@Bean
	public ApplicationCommandGateway commandGateway() throws Exception {
		 CommandGatewayFactoryBean<ApplicationCommandGateway> factory = 
				 new CommandGatewayFactoryBean<ApplicationCommandGateway>();
		 factory.setCommandBus(commandBus());
		 factory.setGatewayInterface(ApplicationCommandGateway.class);
		 factory.afterPropertiesSet();
		 return factory.getObject();
	 }
	  
	 /**
 	 * Command bus.
 	 *
 	 * @return the command bus
 	 */
 	@Bean
	public CommandBus commandBus() {
 	   SimpleCommandBus scb = new SimpleCommandBus();
 	   scb.setDispatchInterceptors(commandInterceptors());
 	   return scb;
	 } 
	 
	 /**
 	 * Dispatch interceptors.
 	 *
 	 * @return the list
 	 */
 	private List<CommandDispatchInterceptor> commandInterceptors() {
		 List<CommandDispatchInterceptor>  cdi =
				 new ArrayList<CommandDispatchInterceptor>();
		 cdi.add(new BeanValidationInterceptor(validator()));
		 return cdi;
	 }
	 
	 /**
 	 * Event store.
 	 *
 	 * @return the event store
 	 */
 	@Bean
	public EventStore eventStore() {
		 return new JpaEventStore(entityManagerProvider());
	 }
	 
	 /**
 	 * Entity manager provider.
 	 *
 	 * @return the entity manager provider
 	 */
 	@Bean
	public EntityManagerProvider entityManagerProvider() {
		 return new ContainerManagedEntityManagerProvider();
	 }
	 	  
	 /**
 	 * Event bus.
 	 *
 	 * @return the event bus
 	 */
 	@Bean
	public EventBus eventBus() {
		 return new SimpleEventBus();
	 } 
	 
	 /**
 	 * Customer repo.
 	 *
 	 * @return the repository
 	 */
 	@Primary
	@Bean
	public Repository<Customer> customerRepo() {
		 EventSourcingRepository<Customer> repository = 
			 new EventSourcingRepository<Customer>(
				 Customer.class, eventStore());
		 repository.setEventBus(eventBus());
		 return repository;
	 } 
	
	 /**
 	 * Product cell repo.
 	 *
 	 * @return the repository
 	 */
 	@Bean
	public Repository<ProductCell> productCellRepo() {
		 EventSourcingRepository<ProductCell> repository = 
			 new EventSourcingRepository<ProductCell>(
				 ProductCell.class, eventStore());
		 repository.setEventBus(eventBus());
		 return repository;
	 } 
	 
	 /**
 	 * Order repo.
 	 *
 	 * @return the repository
 	 */
 	@Bean
	public Repository<Order> orderRepo() {
		 EventSourcingRepository<Order> repository = 
			 new EventSourcingRepository<Order>(Order.class, eventStore());
		 repository.setEventBus(eventBus());
		 return repository;
	 } 
	 
	
	 /**
 	 * Customer command handler.
 	 *
 	 * @return the aggregate annotation command handler
 	 */
 	@Bean
	public AggregateAnnotationCommandHandler<Customer> 
 		customerCommandHandler() {
		 @SuppressWarnings("unchecked")
		AggregateAnnotationCommandHandler<Customer> customerCommandHandler = 
				 AggregateAnnotationCommandHandler
				 	.subscribe(Customer.class, customerRepo(), commandBus());
		 return customerCommandHandler;
	 }
	 
	 /**
 	 * Order command handler.
 	 *
 	 * @return the aggregate annotation command handler
 	 */
 	@Bean
	public AggregateAnnotationCommandHandler<Order> 
 		orderCommandHandler() {
		 @SuppressWarnings("unchecked")
		AggregateAnnotationCommandHandler<Order> orderCommandHandler = 
				 AggregateAnnotationCommandHandler
				      .subscribe(Order.class, orderRepo(), commandBus());
		 return orderCommandHandler;
	 }
	 
	 /**
 	 * Product cell command handler.
 	 *
 	 * @return the aggregate annotation command handler
 	 * @throws Exception the exception
 	 */
 	@Bean
	public AggregateAnnotationCommandHandler<ProductCell> 
 		productCellCommandHandler() 
			 throws Exception {
		 AggregateAnnotationCommandHandlerFactoryBean<ProductCell> a =
			 new AggregateAnnotationCommandHandlerFactoryBean
			 	<ProductCell>();
		 a.setAggregateType(ProductCell.class);
		 a.setApplicationContext(ac);
		 a.setCommandBus(commandBus());
		 a.setRepository(productCellRepo());
		 a.setParameterResolverFactory(resolver());
		 a.afterPropertiesSet();
		 return a.getObject();
	 }
	 
	 /** The ac. */
 	@Autowired
	 private ApplicationContext ac;
	 
	 /**
 	 * Resolver.
 	 *
 	 * @return the spring bean parameter resolver factory
 	 */
 	@Bean
	public SpringBeanParameterResolverFactory resolver() {
		 SpringBeanParameterResolverFactory s =
				 new SpringBeanParameterResolverFactory();
		 s.setApplicationContext(ac);
		 return s;
	 }
	 
     /**
      * Annotation event listener bean post processor.
      *
      * @return the annotation event listener bean post processor
      */
     @Bean
    public AnnotationEventListenerBeanPostProcessor 
     	annotationEventListenerBeanPostProcessor() {
             AnnotationEventListenerBeanPostProcessor processor = 
            		 new AnnotationEventListenerBeanPostProcessor();
             processor.setEventBus(eventBus());
             return processor;
     }


}
