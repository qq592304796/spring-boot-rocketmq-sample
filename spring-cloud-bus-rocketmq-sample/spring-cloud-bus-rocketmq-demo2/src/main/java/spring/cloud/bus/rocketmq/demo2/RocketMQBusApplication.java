package spring.cloud.bus.rocketmq.demo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.event.EventListener;

import lombok.extern.slf4j.Slf4j;
import spring.cloud.bus.rocketmq.common.event.ProductEvent;

@Slf4j
@RemoteApplicationEventScan(basePackageClasses = ProductEvent.class)
@SpringBootApplication
public class RocketMQBusApplication {


    public static void main(String[] args) {
        SpringApplication.run(RocketMQBusApplication.class, args);
    }

    @EventListener
    public void onProductEvent(ProductEvent event){
        log.info("ProductEvent - Source : {} , originService : {}, destinationService : {}",
                event.getSource(), event.getOriginService(), event.getDestinationService());
    }

}