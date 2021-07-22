package com.example.mockitodemo.mockito.service;

import com.example.mockitodemo.mockito.model.Users;

public interface UsersService {

    Users findById(Long usersId);
    void duplicateCheck(Long usersId);

}
