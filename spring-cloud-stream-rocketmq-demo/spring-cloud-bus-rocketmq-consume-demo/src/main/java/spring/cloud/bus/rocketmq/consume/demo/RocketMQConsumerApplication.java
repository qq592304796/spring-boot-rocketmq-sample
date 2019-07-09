package spring.cloud.bus.rocketmq.consume.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

import spring.cloud.bus.rocketmq.consume.demo.RocketMQConsumerApplication.MySink;

@SpringBootApplication
@EnableBinding({ MySink.class })
public class RocketMQConsumerApplication {

    public interface MySink {

        @Input("input1")
        SubscribableChannel input1();

        @Input("input2")
        SubscribableChannel input2();

        @Input("input3")
        SubscribableChannel input3();

        @Input("input4")
        SubscribableChannel input4();

//        @Input("input5")
//        PollableMessageSource input5();
        
        @Input("input6")
        SubscribableChannel input6();
    }

    public static void main(String[] args) {
        SpringApplication.run(RocketMQConsumerApplication.class, args);
    }

//    @Bean
//    public ConsumerCustomRunner customRunner() {
//        return new ConsumerCustomRunner();
//    }

//    public static class ConsumerCustomRunner implements CommandLineRunner {
//
//        @Autowired
//        private MySink mySink;
//
//        @Override
//        public void run(String... args) throws InterruptedException {
//            while (true) {
//                mySink.input5().poll(m -> {
//                    String payload = (String) m.getPayload();
//                    System.out.println("pull msg: " + payload);
//                }, new ParameterizedTypeReference<String>() {
//                });
//                Thread.sleep(2_000);
//            }
//        }
//    }

}