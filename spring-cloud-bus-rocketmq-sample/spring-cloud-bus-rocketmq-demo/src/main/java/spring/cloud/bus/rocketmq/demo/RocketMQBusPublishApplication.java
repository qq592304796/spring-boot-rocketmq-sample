package spring.cloud.bus.rocketmq.demo;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import lombok.extern.slf4j.Slf4j;
import spring.cloud.bus.rocketmq.common.event.Product;
import spring.cloud.bus.rocketmq.common.event.ProductEvent;

@Slf4j
@RemoteApplicationEventScan(basePackageClasses = ProductEvent.class)
@SpringBootApplication
public class RocketMQBusPublishApplication {


    public static void main(String[] args) {
        SpringApplication.run(RocketMQBusPublishApplication.class, args);
    }

    @Bean
    public PublishCustomRunner customRunner() {
        return new PublishCustomRunner();
    }
    
    @EventListener
    public void onProductEvent(ProductEvent event){
        log.info("ProductEvent - Source : {} , originService : {}, destinationService : {}",
                event.getSource(), event.getOriginService(), event.getDestinationService());
    }
    
    public static class PublishCustomRunner implements CommandLineRunner {
        
        @Autowired
        private ApplicationContext applicationContext;
        
        @Override
        public void run(String... args) {
            Product product = new Product(1L, "酱油", BigDecimal.valueOf(10));
            ProductEvent productEvent = new ProductEvent(this,
                    applicationContext.getId(), null,
                    ProductEvent.ET_UPDATE, product.getId());
            applicationContext.publishEvent(productEvent);
        }
    }

}