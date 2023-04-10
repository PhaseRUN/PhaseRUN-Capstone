package com.codeup.phaserun.repositories;

import com.codeup.phaserun.models.Race;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaceRepository extends JpaRepository<Race, Long> {
    Race findById(int id);
    Race findByRace_name(String Name);
}
