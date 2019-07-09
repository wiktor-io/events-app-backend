package com.wiktorio.events;

import com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class GraphQLDataFetchers {

    private static List<Map<String, String>> events = Arrays.asList(
            ImmutableMap.of("id", "1",
                    "name", "Joe Bonamassa - Live Concert",
                    "organiser", "1",
                    "venue", "Edinburgh Playhouse",
                    "price", "12.50"),
            ImmutableMap.of("id", "1",
                    "name", "Joe Bonamassa - Live Concert",
                    "organiser", "1",
                    "venue", "Edinburgh Playhouse",
                    "price", "12.50"),
            ImmutableMap.of("id", "1",
                    "name", "Joe Bonamassa - Live Concert",
                    "organiser", "1",
                    "venue", "Edinburgh Playhouse",
                    "price", "12.50")
    );

    private static List<Map<String, String>> organisers = Arrays.asList(
            ImmutableMap.of("id", "1",
                    "name", "Edinburgh Playhouse",
                    "logo", "/logo-1.jpg",
                    "description", "The Edinburgh Playhouse is situated on Greenside Place, at the top of Leith Walk and close to the east end of Princes Street."),
            ImmutableMap.of("id", "1",
                    "name", "Edinburgh Playhouse",
                    "logo", "/logo-1.jpg",
                    "description", "The Edinburgh Playhouse is situated on Greenside Place, at the top of Leith Walk and close to the east end of Princes Street.")
    );

    public DataFetcher getEventByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String eventId = dataFetchingEnvironment.getArgument("id");
            return events
                    .stream()
                    .filter(event -> event.get("id").equals(eventId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher getOrganiserFromEventDataFetcher() {
        return dataFetchingEnvironment -> {
            Map<String,String> event = dataFetchingEnvironment.getSource();
            String organiserId = event.get("organiser");
            return organisers
                    .stream()
                    .filter(organiser -> organiser.get("id").equals(organiserId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher getOrganiserByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String organiserId = dataFetchingEnvironment.getArgument("id");
            return organisers
                    .stream()
                    .filter(organiser -> organiser.get("id").equals(organiserId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher getEventsFromOrganiserDataFetcher() {
        return dataFetchingEnvironment -> {
            Map<String,String> organiser = dataFetchingEnvironment.getSource();
            String organiserId = organiser.get("id");
            return events
                    .stream()
                    .filter(event -> event.get("organiser").equals(organiserId))
                    .findAny()
                    .orElse(null);
        };
    }
}
