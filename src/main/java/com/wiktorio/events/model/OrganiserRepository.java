package com.wiktorio.events.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrganiserRepository extends CrudRepository<Organiser, Long> {
    List<Organiser> findAllByEventsId(Long id);
}

