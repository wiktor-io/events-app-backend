package com.wiktorio.events;

import com.wiktorio.events.model.Event;
import com.wiktorio.events.model.EventRepository;
import com.wiktorio.events.model.Organiser;
import com.wiktorio.events.model.OrganiserRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GraphQLDataFetchers {

    @Autowired
    OrganiserRepository organiserRepository;

    @Autowired
    EventRepository eventRepository;

    public DataFetcher getEventByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            Long eventId = Long.parseLong(dataFetchingEnvironment.getArgument("id"));
            return eventRepository
                    .findById(eventId)
                    .orElse(null);
        };
    }

    public DataFetcher getOrganiserFromEventDataFetcher() {
        return dataFetchingEnvironment -> {
            Event event = dataFetchingEnvironment.getSource();
            return event.getOrganiser();
        };
    }

    public DataFetcher getOrganiserByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            Long organiserId = Long.parseLong(dataFetchingEnvironment.getArgument("id"));
            return organiserRepository
                    .findById(organiserId);
        };
    }

    public DataFetcher getEventsFromOrganiserDataFetcher() {
        return dataFetchingEnvironment -> {
            Organiser organiser = dataFetchingEnvironment.getSource();
            return organiser.getEvents();
        };
    }
}
