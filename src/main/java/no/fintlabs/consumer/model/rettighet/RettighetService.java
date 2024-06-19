package no.fintlabs.consumer.model.rettighet;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import no.fint.model.felles.kompleksedatatyper.Identifikator;
import no.fint.model.resource.ressurs.tilgang.IdentitetResource;
import no.fint.model.resource.ressurs.tilgang.RettighetResource;
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
public class RettighetService extends CacheService<RettighetResource> {

    private final EntityKafkaConsumer<RettighetResource> entityKafkaConsumer;

    private final RettighetLinker linker;

    public RettighetService(
            RettighetConfig consumerConfig,
            CacheManager cacheManager,
            RettighetKafkaConsumer entityKafkaConsumer,
            RettighetLinker linker) {
        super(consumerConfig, cacheManager, entityKafkaConsumer);
        this.entityKafkaConsumer = entityKafkaConsumer;
        this.linker = linker;
    }

    @Override
    protected Cache<RettighetResource> initializeCache(CacheManager cacheManager, ConsumerConfig<RettighetResource> consumerConfig, String s) {
        return cacheManager.create(PackingTypes.POJO, consumerConfig.getOrgId(), consumerConfig.getResourceName());
    }

    @PostConstruct
    private void registerKafkaListener() {
        long retention = entityKafkaConsumer.registerListener(RettighetResource.class, this::addResourceToCache);
        getCache().setRetentionPeriodInMs(retention);
    }

    private void addResourceToCache(ConsumerRecord<String, RettighetResource> consumerRecord) {
        this.eventLogger.logDataRecieved();
        if (consumerRecord.value() == null) {
            getCache().remove(consumerRecord.key());
        } else {
            RettighetResource applikasjonResource = consumerRecord.value();
            linker.mapLinks(applikasjonResource);
            getCache().put(consumerRecord.key(), applikasjonResource, linker.hashCodes(applikasjonResource));
        }
    }

    @Override
    public Optional<RettighetResource> getBySystemId(String systemId) {
        return getCache().getLastUpdatedByFilter(systemId.hashCode(),
                (resource) -> Optional
                        .ofNullable(resource)
                        .map(RettighetResource::getSystemId)
                        .map(Identifikator::getIdentifikatorverdi)
                        .map(systemId::equals)
                        .orElse(false)
        );
    }
}
