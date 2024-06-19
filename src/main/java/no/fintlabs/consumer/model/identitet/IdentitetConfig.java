package no.fintlabs.consumer.model.identitet;

import no.fint.model.resource.ressurs.tilgang.IdentitetResource;
import no.fintlabs.core.consumer.shared.config.ConsumerProps;
import no.fintlabs.core.consumer.shared.resource.ConsumerConfig;
import org.springframework.stereotype.Component;

@Component
public class IdentitetConfig extends ConsumerConfig<IdentitetResource> {

    public IdentitetConfig(ConsumerProps consumerProps) {
        super(consumerProps);
    }

    @Override
    protected String resourceName() {
        return "identitet";
    }
}
