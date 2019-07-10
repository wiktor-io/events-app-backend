package com.wiktorio.events.model;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
public class Organiser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "organiser", cascade = CascadeType.ALL)
    private Set<Event> events;

    private String name;
    private String logo;
    private String description;

    public Organiser() {}

    public Organiser(String name,
                     String logo,
                     String description,
                     Event... events) {
        this(name, logo, description);
        this.events = Stream.of(events).collect(Collectors.toSet());
        this.events.forEach(event -> event.setOrganiser(this));
    }

    public Organiser(String name,
                     String logo,
                     String description) {
        this.name = name;
        this.logo = logo;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format(
                "Organiser[id=%d, name='%s', logo='%s', description='%s']",
                id, name, logo, description);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
}