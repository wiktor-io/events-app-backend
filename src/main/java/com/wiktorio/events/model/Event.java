package com.wiktorio.events.model;

import javax.persistence.*;
import java.util.LinkedHashMap;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Organiser organiser;

    private String name;
    private String description;
    private String venue;
    private String venue_location;
    private Integer availability;
    private String date;
    private String image;
    private Double price;

    protected Event() {}

    public Event(String name,
        Organiser organiser,
        String description,
        String venue,
        String venue_location,
        Integer availability,
        String date,
        String image,
        Double price) {
        this.name = name;
        this.organiser = organiser;
        this.description = description;
        this.venue = venue;
        this.venue_location = venue_location;
        this.availability = availability;
        this.date = date;
        this.image = image;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format(
                "Event[id=%d, name='%s', description='%s', venue='%s', venue_location='%s', availability='%s', date='%s', image='%s', price='%s']",
                id, name, description, venue, venue_location, availability, date, image, price);
    }

    public void setOrganiser(Organiser organiser) {
        this.organiser = organiser;
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

    public Organiser getOrganiser() {
        return organiser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getVenue_location() {
        return venue_location;
    }

    public void setVenue_location(String venue_location) {
        this.venue_location = venue_location;
    }

    public Integer getAvailability() {
        return availability;
    }

    public void setAvailability(Integer availability) {
        this.availability = availability;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
