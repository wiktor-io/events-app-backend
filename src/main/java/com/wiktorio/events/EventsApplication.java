package com.wiktorio.events;

import com.wiktorio.events.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class EventsApplication {

    @Value("${graphql.url:graphql}")
    private String graphqlurl;

    public static void main(String[] args) {
        SpringApplication.run(EventsApplication.class, args);
    }

    /*
     * No cors origin global setting.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/" + graphqlurl).allowedOrigins("http://localhost:9000", "http://localhost:4200");
            }
        };
    }

    @Bean
    public CommandLineRunner demo(EventRepository eventRepository, OrganiserRepository organiserRepository) {
        return (args) -> {
            Organiser playhouse = organiserRepository.save(new Organiser("Edinburgh Playhouse",
                    "/img/1.jpg",
                    "Edinburgh Playhouse"));

            // save an event
            Event joeBonamassa = eventRepository.save(new Event("Joe Bonamassa Live", playhouse, "Different Shades Of Blue Live", "Edinburgh Playhouse", "Edinburgh", 150, 200,"Music Concert", "Entertainment","published", "single", "12/03/2019", "/img.jpg", 120.00));
        };
    }

}
