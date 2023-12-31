package org.acme.optaplanner.persistence;

import jakarta.enterprise.context.ApplicationScoped;

import org.acme.optaplanner.domain.Timeslot;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class TimeslotRepository implements PanacheRepository<Timeslot> {

}
