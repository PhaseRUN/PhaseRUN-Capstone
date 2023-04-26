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
    Race findById(int id);

    boolean existsByRaceId(String raceId);

    Race findByRaceId(String raceId);



    //delete controller
//    @Query(value = "select * from races WHERE race_id = :race_id", nativeQuery = true)
//    Race findRace(String race_id);
//
//    @Modifying
//    @Query(value = "DELETE FROM users_races WHERE user_id = :userId AND race_id = :raceId", nativeQuery = true)
//    void removeRaceFromUserBookmarks(Long userId, Long raceId);


}


