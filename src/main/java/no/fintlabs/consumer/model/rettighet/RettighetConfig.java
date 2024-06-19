package no.fintlabs.consumer.model.rettighet;

import no.fint.model.resource.ressurs.tilgang.IdentitetResource;
import no.fint.model.resource.ressurs.tilgang.RettighetResource;
import no.fintlabs.core.consumer.shared.config.ConsumerProps;
import no.fintlabs.core.consumer.shared.resource.ConsumerConfig;
import org.springframework.stereotype.Component;

@Component
public class RettighetConfig extends ConsumerConfig<RettighetResource> {

    public RettighetConfig(ConsumerProps consumerProps) {
        super(consumerProps);
    }

    @Override
    protected String resourceName() {
        return "identitet";
    }
}
