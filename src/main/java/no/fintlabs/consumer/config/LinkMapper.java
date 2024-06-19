package no.fintlabs.consumer.config;

import com.google.common.collect.ImmutableMap;
import no.fint.model.ressurs.tilgang.Identitet;
import no.fint.model.ressurs.tilgang.Rettighet;

import java.util.Map;

public class LinkMapper {

    public static Map<String, String> linkMapper() {
        return ImmutableMap.<String, String>builder()
                .put(Identitet.class.getName(), "/ressurs/tilgang/identitet")
                .put(Rettighet.class.getName(), "/ressurs/tilgang/rettighet")
                .build();
    }

}