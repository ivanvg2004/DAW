package com.liceu.hibernate.repository;

import com.liceu.hibernate.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    //hibernate autprograma una recerca per username
    User findByName(String username);
    User findByNameAndBirthYear(String username, int birthYear);
}
