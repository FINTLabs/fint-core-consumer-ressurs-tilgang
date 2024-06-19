package no.fintlabs.consumer.model.rettighet;

import no.fint.model.resource.ressurs.tilgang.IdentitetResource;
import no.fint.model.resource.ressurs.tilgang.RettighetResource;
import no.fintlabs.core.consumer.shared.resource.kafka.EntityKafkaConsumer;
import no.fintlabs.kafka.common.ListenerBeanRegistrationService;
import no.fintlabs.kafka.entity.EntityConsumerFactoryService;
import no.fintlabs.kafka.entity.topic.EntityTopicService;
import org.springframework.stereotype.Service;

@Service
public class RettighetKafkaConsumer extends EntityKafkaConsumer<RettighetResource> {

    public RettighetKafkaConsumer(
            EntityConsumerFactoryService entityConsumerFactoryService,
            ListenerBeanRegistrationService listenerBeanRegistrationService,
            EntityTopicService entityTopicService,
            RettighetConfig rettighetConfig) {
        super(entityConsumerFactoryService, listenerBeanRegistrationService, entityTopicService, rettighetConfig);
    }
}
