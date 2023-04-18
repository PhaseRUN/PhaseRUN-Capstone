package com.codeup.phaserun.repositories;

import com.codeup.phaserun.models.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {
    Race findById(long id);
    Race findRaceByRaceId(int race_id);
}
