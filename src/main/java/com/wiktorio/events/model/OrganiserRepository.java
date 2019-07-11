package com.wiktorio.events.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrganiserRepository extends JpaRepository<Organiser, Long>, QuerydslPredicateExecutor<Organiser> {
}