package com.codeup.phaserun.repositories;

import com.codeup.phaserun.models.Comment;
import com.codeup.phaserun.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRespository extends JpaRepository<Comment, Long> {
    List<Comment> findByUser(User user);
//    Comment findById(long id);

    Comment findCommentById(int id);
}
