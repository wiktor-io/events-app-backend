package com.wiktorio.events;

import com.apple.eawt.Application;
import com.wiktorio.events.model.Event;
import com.wiktorio.events.model.EventRepository;
import com.wiktorio.events.model.Organiser;
import com.wiktorio.events.model.OrganiserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class EventsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventsApplication.class, args);
    }
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Value("${graphql.url:graphql}")
    private String graphqlurl;

    /*
     * No cors origin global setting.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/" + graphqlurl).allowedOrigins("http://localhost:9000");
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
            Event joeBonamassa = eventRepository.save(new Event("Joe Bonamassa", playhouse, "", "Edinburgh Playhouse", "Edinburgh", 1, "12/03/2019", "/img.jpg", 120f));

            // fetch all customers
            log.info("Events found with findAll():");
            log.info("-------------------------------");
            for (Event ev : eventRepository.findAll()) {
                log.info(ev.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            eventRepository.findById(1L)
                    .ifPresent(customer -> {
                        log.info("Event found with findById(1L):");
                        log.info("--------------------------------");
                        log.info(customer.toString());
                        log.info("");
                    });

            // fetch customers by last name
            log.info("Event found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            eventRepository.findByOrganiser(playhouse).forEach(bauer -> {
                log.info(bauer.toString());
            });
            // for (Event bauer : repository.findByLastName("Bauer")) {
            // 	log.info(bauer.toString());
            // }
            log.info("");


            // fetch customers by last name
            log.info("Organisers found containing event():");
            log.info("--------------------------------------------");
            organiserRepository.findAllByEventsId(joeBonamassa.getId()).forEach(organiser -> {
                log.info(organiser.toString());
            });
            // for (Event bauer : repository.findByLastName("Bauer")) {
            // 	log.info(bauer.toString());
            // }
            log.info("");

        };
    }

}
