package fr.pmu.gateways.event;

import fr.pmu.domain.entity.Race;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class CreateRaceKafkaConsumer implements EventBusGateway {
    private final Logger LOG = LoggerFactory.getLogger(CreateRaceKafkaConsumer.class);

    private final KafkaTemplate<String, Race> kafkaTemplate;


    public CreateRaceKafkaConsumer(KafkaTemplate<String, Race> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendCreateRaceMessage(Race race, String topicName) {
        CompletableFuture<SendResult<String, Race>> future =
                kafkaTemplate.send(topicName, race);
        future.whenComplete((result, throwable) -> LOG.info("Message [{}] delivered with offset {}",
                race,
                result.getRecordMetadata().offset()));

    }
}
