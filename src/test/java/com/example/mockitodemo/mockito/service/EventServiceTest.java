package com.example.mockitodemo.mockito.service;

import com.example.mockitodemo.mockito.model.Event;
import com.example.mockitodemo.mockito.model.Users;
import com.example.mockitodemo.mockito.repository.EventRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock UsersService usersService;
    @Mock EventRepository eventRepository;


    @Test
    void createEventService() {
        // @Mock 을 통해 정상적으로 의존성을 주입 받았다.
        EventService eventService = new EventService(usersService, eventRepository);
        assertNotNull(eventService);
    }


    @Test
    void mockReturnType() {
        /*
            @Mock 을 통해 주입받은 객체의 메서드 리턴타입은 아래와 같다.
            1. 객체 - Null을 리턴
            2. PrimitiveType - Primitive 타입 기본 값 반환
            3. Collection - 빈 컬렉션
         */

        Users user = usersService.findById(1L);
        assertNull(user);
    }


    @Test
    void mockitoVoidMethod() {
        // @Mock 을 통해 주입받은 객체의 void 메서드를 호출하면 예외가 발생하지 않고 아무일도 일어나지 않는다.
        usersService.duplicateCheck(10000L);
    }


    @Test
    void mockitoWhen() {
        Users user = new Users(1L, "LEE");

        // usersService.findById(아무 값) 을 호출 할 때 user 객체를 반환한다.
        Mockito.when(usersService.findById(ArgumentMatchers.any())).thenReturn(user);

        Users findUser = usersService.findById(1L);
        assertEquals("LEE", findUser.getName());
    }


    @Test
    void mockitoDoThrow() {
        Users newUser = new Users(1L, "LEE");

        // userService.duplicateCheck(2L) 을 호출 할 때 IllegalArgumentException 발생
        Mockito.doThrow(new IllegalArgumentException()).when(usersService).duplicateCheck(1L);

        // 아무 일도 발생하지 않는다.
        usersService.duplicateCheck(100000L);

        // 예외 발생!
        assertThrows(IllegalArgumentException.class,
                            () -> usersService.duplicateCheck(1L));
    }


    @Test
    void manyTest() {
        Users user = new Users(1L, "LEE");
        Users newUser = new Users(2L, "GICHEOL");

        /*
            usersService.findById(아무 값) 을 호출할 때
            첫번째는 user 객체를 리턴,
            두번째는 RuntimeException 예외
            세번째는 newUser 객체를 리턴한다.
         */
        when(usersService.findById(any()))
                .thenReturn(user)
                .thenThrow(new RuntimeException())
                .thenReturn(newUser);

        // 첫번째 리턴값은 user 로 정해져있기 때문에 findById(2L) 을 하더라도 테스트는 성공한다.
        Users findUser = usersService.findById(1L);
        assertEquals("LEE", findUser.getName());

        assertThrows(RuntimeException.class, () -> usersService.findById(3L));

        assertEquals(newUser, usersService.findById(2L));
    }


    @Test
    @DisplayName("User를 그대로 리턴하도록 Stubbing")
    void examUserReturnStubbing() {
        Users user = new Users(1L, "LEE");

        when(usersService.findById(1L)).thenReturn(user);
        Users findUser = usersService.findById(1L);

        assertEquals(findUser, user);
    }


    @Test
    @DisplayName("Event를 save하면 event를 그대로 리턴하도록 Stubbing")
    void examEventSaveReturnStubbing() {
        Users user = new Users(1L, "LEE");
        Event event = new Event(1L, user, "EVENT");

        when(eventRepository.save(event)).thenReturn(event);
        Event saveEvent = eventRepository.save(event);

        assertNotNull(saveEvent.getUsers());
        assertEquals(user, saveEvent.getUsers());
    }

}