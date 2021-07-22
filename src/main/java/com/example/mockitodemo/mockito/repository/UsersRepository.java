package com.example.mockitodemo.mockito.repository;

import com.example.mockitodemo.mockito.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {

}
