package com.codeup.phaserun.repositories;

import com.codeup.phaserun.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(int id);
}
