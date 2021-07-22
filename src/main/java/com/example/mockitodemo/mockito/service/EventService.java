package com.example.mockitodemo.mockito.service;

import com.example.mockitodemo.mockito.model.Event;
import com.example.mockitodemo.mockito.model.Users;
import com.example.mockitodemo.mockito.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class EventService {

    private final UsersService usersService;
    private final EventRepository eventRepository;


    public Event createEvent(Long userId, Event event) {
        Users user = usersService.findById(userId);
        event.setUsers(user);
        return eventRepository.save(event);
    }

}
