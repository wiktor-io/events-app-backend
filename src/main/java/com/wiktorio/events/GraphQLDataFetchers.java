package com.wiktorio.events;


import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.wiktorio.events.model.*;
import graphql.GraphQLException;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.awt.image.ImageWatched;

import java.util.*;

@Component
public class GraphQLDataFetchers {

    private final OrganiserRepository organiserRepository;

    private final EventRepository eventRepository;

    public GraphQLDataFetchers(OrganiserRepository organiserRepository, EventRepository eventRepository) {
        this.organiserRepository = organiserRepository;
        this.eventRepository = eventRepository;
    }

    public DataFetcher readEventDataFetcher() {
        return dataFetchingEnvironment -> {
            Long eventId = Long.parseLong(dataFetchingEnvironment.getArgument("id"));
            return eventRepository
                    .findById(eventId)
                    .orElse(null);
        };
    }

    public DataFetcher readOrganiserByEventDataFetcher() {
        return dataFetchingEnvironment -> {
            Event event = dataFetchingEnvironment.getSource();
            return event.getOrganiser();
        };
    }

    public DataFetcher readOrganiserDataFetcher() {
        return dataFetchingEnvironment -> {
            Long organiserId = Long.parseLong(dataFetchingEnvironment.getArgument("id"));
            return organiserRepository
                    .findById(organiserId);
        };
    }

    public DataFetcher readEventsByOrganiserDataFetcher() {
        return dataFetchingEnvironment -> {
            Organiser organiser = dataFetchingEnvironment.getSource();
            return organiser.getEvents();
        };
    }

    public DataFetcher listEventsDataFetcher() {
        return dataFetchingEnvironment -> {
            Optional<LinkedHashMap> optionalFilter = Optional.ofNullable(dataFetchingEnvironment.getArgument("filter"));
            LinkedHashMap filterMap = optionalFilter.orElse(new LinkedHashMap());
            final List<Predicate> filters = new ArrayList<>();

            QEvent event = QEvent.event;

            if (filterMap.get("name") != null) {
                String name = Optional.ofNullable(filterMap.get("name")).orElse("").toString();
                BooleanExpression matchesName = event.name.like(Expressions.asString("%").concat(name).concat(Expressions.asString("%")));
                filters.add(matchesName);
            }

            if (filterMap.get("description") != null) {
                String description = filterMap.get("description").toString();
                BooleanExpression matchesDescription = event.description.like(Expressions.asString("%").concat(description).concat(Expressions.asString("%")));
                filters.add(matchesDescription);
            }

            if (filterMap.get("venue") != null) {
                String venue = filterMap.get("venue").toString();
                BooleanExpression matchesVenue = event.venue.like(Expressions.asString("%").concat(venue).concat(Expressions.asString("%")));
                filters.add(matchesVenue);
            }

            if (filterMap.get("venue_location") != null) {
                String venue_location = filterMap.get("venue_location").toString();
                BooleanExpression matchesVenue_location = event.venue_location.like(Expressions.asString("%").concat(venue_location).concat(Expressions.asString("%")));
                filters.add(matchesVenue_location);
            }

            if (filterMap.get("availability") != null) {
                Integer availability = Integer.parseInt(filterMap.get("availability").toString());
                BooleanExpression matchesAvailability = event.availability.goe(availability);
                filters.add(matchesAvailability);
            }

            if (filterMap.get("capacity") != null) {
                Integer capacity = Integer.parseInt(filterMap.get("capacity").toString());
                BooleanExpression matchesInventory = event.capacity.goe(capacity);
                filters.add(matchesInventory);
            }

            if (filterMap.get("type") != null) {
                String type = filterMap.get("type").toString();
                BooleanExpression matchesType = event.type.eq(type);
                filters.add(matchesType);
            }

            if (filterMap.get("status") != null) {
                String status = filterMap.get("status").toString();
                BooleanExpression matchesStatus = event.status.eq(status);
                filters.add(matchesStatus);
            }

            if (filterMap.get("reocurrance") != null) {
                String reocurrance = filterMap.get("reocurrance").toString();
                BooleanExpression matchesReocurrance = event.reocurrance.eq(reocurrance);
                filters.add(matchesReocurrance);
            }

            if (filterMap.get("date_from") != null) {
                String date_from = filterMap.get("date_from").toString();
                BooleanExpression matchesDate_from = event.date.goe(date_from);
                filters.add(matchesDate_from);
            }

            if (filterMap.get("date_to") != null) {
                String date_to = filterMap.get("date_to").toString();
                BooleanExpression matchesDate_to = event.date.loe(date_to);
                filters.add(matchesDate_to);
            }

            if (filterMap.get("price_from") != null) {
                Double price_from = Double.parseDouble(filterMap.get("price_from").toString());
                BooleanExpression matchesPrice_from = event.price.goe(price_from);
                filters.add(matchesPrice_from);
            }

            if (filterMap.get("price_to") != null) {
                Double price_to = Double.parseDouble(filterMap.get("price_to").toString());
                BooleanExpression matchesPrice_to = event.price.loe(price_to);
                filters.add(matchesPrice_to);
            }

            BooleanExpression query = event.isNotNull();

            for (Predicate filter :
                    filters) {
                query = query.and(filter);
            }

            return eventRepository.findAll(query);
        };
    }

    public DataFetcher listOrganisersDataFetcher() {
        return dataFetchingEnvironment -> {
            Optional<LinkedHashMap> optionalFilter = Optional.ofNullable(dataFetchingEnvironment.getArgument("filter"));
            LinkedHashMap filterMap = optionalFilter.orElse(new LinkedHashMap());
            final List<Predicate> filters = new ArrayList<>();

            QOrganiser organiser = QOrganiser.organiser;

            if (filterMap.get("name") != null) {
                String name = Optional.ofNullable(filterMap.get("name")).orElse("").toString();
                BooleanExpression matchesName = organiser.name.like(Expressions.asString("%").concat(name).concat(Expressions.asString("%")));
                filters.add(matchesName);
            }

            if (filterMap.get("description") != null) {
                String description = filterMap.get("description").toString();
                BooleanExpression matchesDescription = organiser.description.like(Expressions.asString("%").concat(description).concat(Expressions.asString("%")));
                filters.add(matchesDescription);
            }

            BooleanExpression query = organiser.isNotNull();

            for (Predicate filter :
                    filters) {
                query = query.and(filter);
            }

            return organiserRepository.findAll(query);
        };
    }

    public DataFetcher createEventDataFetcher() {
        return dataFetchingEnvironment -> {
            LinkedHashMap input = dataFetchingEnvironment.getArgument("input");
            Long organiserId = Long.parseLong(input.get("organiser").toString());

            Organiser organiser = organiserRepository.findById(organiserId).orElse(null);
            if (organiser == null) {
                throw new GraphQLException("Organiser not found!");
            }

            return eventRepository.save(new Event(
                    (String) input.get("name"),
                    organiser,
                    (String) input.get("description"),
                    (String) input.get("venue"),
                    (String) input.get("venue_location"),
                    (Integer) input.get("availability"),
                    (Integer) input.get("capacity"),
                    (String) input.get("type"),
                    (String) input.get("category"),
                    (String) input.get("status"),
                    (String) input.get("reocurrance"),
                    (String) input.get("date"),
                    (String) input.get("image"),
                    (Double) input.get("price")
            ));
        };
    }

    public DataFetcher updateEventDataFetcher() {
        return dataFetchingEnvironment -> {
            LinkedHashMap input = dataFetchingEnvironment.getArgument("input");
            Long eventId = Long.parseLong(input.get("id").toString());
            Long organiserId = Long.parseLong(input.get("organiser").toString());

            Event event = eventRepository.findById(eventId).orElse(null);
            Organiser organiser = organiserRepository.findById(organiserId).orElse(null);
            if (event == null) {
                throw new GraphQLException("Event not found!");
            }
            if (organiser == null) {
                throw new GraphQLException("Organiser not found!");
            }

            event.setOrganiser(organiser);

            if (input.containsKey("name")) {
                event.setName((String) input.get("name"));
            }
            if (input.containsKey("description")) {
                event.setDescription((String) input.get("description"));
            }
            if (input.containsKey("venue")) {
                event.setVenue((String) input.get("venue"));
            }
            if (input.containsKey("venue_location")) {
                event.setVenue_location((String) input.get("venue_location"));
            }
            if (input.containsKey("availability")) {
                event.setAvailability((Integer) input.get("availability"));
            }
            if (input.containsKey("capacity")) {
                event.setAvailability((Integer) input.get("capacity"));
            }
            if (input.containsKey("type")) {
                event.setDate((String) input.get("type"));
            }
            if (input.containsKey("category")) {
                event.setDate((String) input.get("category"));
            }
            if (input.containsKey("status")) {
                event.setDate((String) input.get("status"));
            }
            if (input.containsKey("reocurrance")) {
                event.setDate((String) input.get("reocurrance"));
            }
            if (input.containsKey("reocurrance")) {
                event.setDate((String) input.get("reocurrance"));
            }
            if (input.containsKey("date")) {
                event.setDate((String) input.get("date"));
            }
            if (input.containsKey("image")) {
                event.setImage((String) input.get("image"));
            }
            if (input.containsKey("price")) {
                event.setPrice((Double) input.get("price"));
            }

            return event;
        };
    }

    public DataFetcher deleteEventDataFetcher() {
        return dataFetchingEnvironment -> {
            Long eventId = Long.parseLong(dataFetchingEnvironment.getArgument("id").toString());
            Event event = eventRepository.findById(eventId).orElse(null);
            if (event == null) {
                throw new GraphQLException("Event not found!");
            }

            eventRepository.delete(event);

            return eventId;
        };
    }

    public DataFetcher createOrganiserDataFetcher() {
        return dataFetchingEnvironment -> {
            LinkedHashMap input = dataFetchingEnvironment.getArgument("input");

            return organiserRepository.save(new Organiser(
                    (String) input.get("name"),
                    (String) input.get("logo"),
                    (String) input.get("description")
            ));
        };
    }

    public DataFetcher updateOrganiserDataFetcher() {
        return dataFetchingEnvironment -> {
            LinkedHashMap input = dataFetchingEnvironment.getArgument("input");
            Long organiserId = Long.parseLong(input.get("id").toString());

            Organiser organiser = organiserRepository.findById(organiserId).orElse(null);

            if (organiser == null) {
                throw new GraphQLException("Organiser not found!");
            }

            organiser.setName((String) input.get("name"));
            organiser.setLogo((String) input.get("logo"));
            organiser.setDescription((String) input.get("description"));

            return organiserRepository.save(organiser);
        };
    }

    public DataFetcher deleteOrganiserDataFetcher() {
        return dataFetchingEnvironment -> {
            Long organiserId = Long.parseLong(dataFetchingEnvironment.getArgument("id").toString());
            Organiser organiser = organiserRepository.findById(organiserId).orElse(null);
            if (organiser == null) {
                throw new GraphQLException("Organiser not found!");
            }

            organiserRepository.delete(organiser);

            return organiserId;
        };
    }
}
