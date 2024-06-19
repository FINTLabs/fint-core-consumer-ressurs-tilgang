package no.fintlabs.consumer.model.rettighet;

import no.fint.model.resource.ressurs.tilgang.IdentitetResource;
import no.fint.model.resource.ressurs.tilgang.IdentitetResources;
import no.fint.model.resource.ressurs.tilgang.RettighetResource;
import no.fint.model.resource.ressurs.tilgang.RettighetResources;
import no.fint.relations.FintLinker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

@Component
public class RettighetLinker extends FintLinker<RettighetResource> {

    public RettighetLinker() {
        super(RettighetResource.class);
    }

    public void mapLinks(RettighetResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public RettighetResources toResources(Collection<RettighetResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public RettighetResources toResources(Stream<RettighetResource> stream, int offset, int size, int totalItems) {
        RettighetResources resources = new RettighetResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(RettighetResource resource) {
        return getAllSelfHrefs(resource).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(RettighetResource resource) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(resource.getSystemId()) && !StringUtils.isEmpty(resource.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(resource.getSystemId().getIdentifikatorverdi(), "systemid"));
        }

        return builder.build();
    }

    int[] hashCodes(RettighetResource resource) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(resource.getSystemId()) && !StringUtils.isEmpty(resource.getSystemId().getIdentifikatorverdi())) {
            builder.add(resource.getSystemId().getIdentifikatorverdi().hashCode());
        }

        return builder.build().toArray();
    }
}