package no.fintlabs.consumer.model.identitet;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import no.fint.model.felles.kompleksedatatyper.Identifikator;
import no.fint.model.resource.ressurs.tilgang.IdentitetResource;
import no.fintlabs.cache.Cache;
import no.fintlabs.cache.CacheManager;
import no.fintlabs.cache.packing.PackingTypes;
import no.fintlabs.core.consumer.shared.resource.CacheService;
import no.fintlabs.core.consumer.shared.resource.ConsumerConfig;
import no.fintlabs.core.consumer.shared.resource.kafka.EntityKafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IdentitetService extends CacheService<IdentitetResource> {

    private final EntityKafkaConsumer<IdentitetResource> entityKafkaConsumer;

    private final IdentitetLinker linker;

    public IdentitetService(
            IdentitetConfig consumerConfig,
            CacheManager cacheManager,
            IdentitetKafkaConsumer entityKafkaConsumer,
            IdentitetLinker linker) {
        super(consumerConfig, cacheManager, entityKafkaConsumer);
        this.entityKafkaConsumer = entityKafkaConsumer;
        this.linker = linker;
    }

    @Override
    protected Cache<IdentitetResource> initializeCache(CacheManager cacheManager, ConsumerConfig<IdentitetResource> consumerConfig, String s) {
        return cacheManager.create(PackingTypes.POJO, consumerConfig.getOrgId(), consumerConfig.getResourceName());
    }

    @PostConstruct
    private void registerKafkaListener() {
        long retention = entityKafkaConsumer.registerListener(IdentitetResource.class, this::addResourceToCache);
        getCache().setRetentionPeriodInMs(retention);
    }

    private void addResourceToCache(ConsumerRecord<String, IdentitetResource> consumerRecord) {
        this.eventLogger.logDataRecieved();
        if (consumerRecord.value() == null) {
            getCache().remove(consumerRecord.key());
        } else {
            IdentitetResource applikasjonResource = consumerRecord.value();
            linker.mapLinks(applikasjonResource);
            getCache().put(consumerRecord.key(), applikasjonResource, linker.hashCodes(applikasjonResource));
        }
    }

    @Override
    public Optional<IdentitetResource> getBySystemId(String systemId) {
        return getCache().getLastUpdatedByFilter(systemId.hashCode(),
                (resource) -> Optional
                        .ofNullable(resource)
                        .map(IdentitetResource::getSystemId)
                        .map(Identifikator::getIdentifikatorverdi)
                        .map(systemId::equals)
                        .orElse(false)
        );
    }
}
