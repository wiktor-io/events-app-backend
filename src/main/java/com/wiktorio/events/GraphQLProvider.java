package com.wiktorio.events;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Component
public class GraphQLProvider {

    @Autowired
    GraphQLDataFetchers graphQLDataFetchers;

    private GraphQL graphQL;

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    @PostConstruct
    public void init() throws IOException {
        URL url = Resources.getResource("schema.graphqls");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                // Queries
                .type(newTypeWiring("Query")
                        .dataFetcher("readEvent", graphQLDataFetchers.readEventDataFetcher()))
                .type(newTypeWiring("Query")
                        .dataFetcher("listEvents", graphQLDataFetchers.listEventsDataFetcher()))
                .type(newTypeWiring("Query")
                        .dataFetcher("readOrganiser", graphQLDataFetchers.readOrganiserDataFetcher()))
                .type(newTypeWiring("Query")
                        .dataFetcher("listOrganisers", graphQLDataFetchers.listOrganisersDataFetcher()))
                // Mutations
                .type(newTypeWiring("Mutation")
                        .dataFetcher("createEvent", graphQLDataFetchers.createEventDataFetcher()))
                .type(newTypeWiring("Mutation")
                        .dataFetcher("updateEvent", graphQLDataFetchers.updateEventDataFetcher()))
                .type(newTypeWiring("Mutation")
                        .dataFetcher("deleteEvent", graphQLDataFetchers.deleteEventDataFetcher()))
                .type(newTypeWiring("Mutation")
                        .dataFetcher("createOrganiser", graphQLDataFetchers.createOrganiserDataFetcher()))
                .type(newTypeWiring("Mutation")
                        .dataFetcher("updateOrganiser", graphQLDataFetchers.updateOrganiserDataFetcher()))
                .type(newTypeWiring("Mutation")
                        .dataFetcher("deleteOrganiser", graphQLDataFetchers.deleteOrganiserDataFetcher()))
                // Child Objects
                .type(newTypeWiring("Event")
                        .dataFetcher("organiser", graphQLDataFetchers.readOrganiserByEventDataFetcher()))
                .type(newTypeWiring("Organiser")
                        .dataFetcher("events", graphQLDataFetchers.readEventsByOrganiserDataFetcher()))
                .build();
    }
}