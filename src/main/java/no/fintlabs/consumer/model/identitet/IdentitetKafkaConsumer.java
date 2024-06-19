package no.fintlabs.consumer.model.identitet;

import no.fint.model.resource.ressurs.tilgang.IdentitetResource;
import no.fintlabs.core.consumer.shared.resource.kafka.EntityKafkaConsumer;
import no.fintlabs.kafka.common.ListenerBeanRegistrationService;
import no.fintlabs.kafka.entity.EntityConsumerFactoryService;
import no.fintlabs.kafka.entity.topic.EntityTopicService;
import org.springframework.stereotype.Service;

@Service
public class IdentitetKafkaConsumer extends EntityKafkaConsumer<IdentitetResource> {

    public IdentitetKafkaConsumer(
            EntityConsumerFactoryService entityConsumerFactoryService,
            ListenerBeanRegistrationService listenerBeanRegistrationService,
            EntityTopicService entityTopicService,
            IdentitetConfig identitetConfig) {
        super(entityConsumerFactoryService, listenerBeanRegistrationService, entityTopicService, identitetConfig);
    }
}
