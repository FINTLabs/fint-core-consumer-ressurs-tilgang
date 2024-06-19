package no.fintlabs.consumer.model.identitet;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import no.fint.antlr.FintFilterService;
import no.fint.model.resource.ressurs.tilgang.IdentitetResource;
import no.fint.relations.FintRelationsMediaType;
import no.fintlabs.consumer.config.RestEndpoints;
import no.fintlabs.core.consumer.shared.resource.ConsumerRestController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(name = "identitet", value = RestEndpoints.IDENTITET, produces = {FintRelationsMediaType.APPLICATION_HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class IdentitetController extends ConsumerRestController<IdentitetResource> {

    public IdentitetController(IdentitetService service, IdentitetLinker linker, FintFilterService oDataFilterService) {
        super(service, linker, oDataFilterService);
    }

    @PostConstruct
    private void registerIdentificators() {
        super.registerIdenficatorHandler("systemId", IdentitetResource::getSystemId);
    }
}
