package com.codeup.phaserun.repositories;

import com.codeup.phaserun.models.Race;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {
    Race findById(long id);
    boolean existsByRaceId(String raceId);

    Race findByRaceId(String raceId);
}


