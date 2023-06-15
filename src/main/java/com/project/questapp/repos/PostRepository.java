package com.project.questapp.repos;

import com.project.questapp.entites.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
//@Transactional
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByUserId(Long userId);
    @Query(value = "select id from tbl_post where user_id = :userId  order by  create_date desc limit 5  ",nativeQuery = true)
    List<Long> findTopByUserId(@Param("userId") Long userId);


}
