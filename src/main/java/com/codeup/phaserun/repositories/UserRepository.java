package com.codeup.phaserun.repositories;

import com.codeup.phaserun.models.Race;
import com.codeup.phaserun.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);

    boolean existsByRaces(Race race);
    User findByUsername(String username);
}
