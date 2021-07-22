package com.example.mockitodemo.mockito.repository;

import com.example.mockitodemo.mockito.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
