package com.codeup.phaserun.repositories;

import com.codeup.phaserun.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);
    User findUserById(long id);
}
