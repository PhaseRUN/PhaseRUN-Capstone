package com.codeup.phaserun.repositories;

import com.codeup.phaserun.models.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {
    Race findById(int id);

    @Query("SELECT r FROM Race r JOIN r.bookmarks b WHERE b.user.id = :userId")
    List<Race> findAllBookmarkedByUser(@Param("userId") Long userId);
}


