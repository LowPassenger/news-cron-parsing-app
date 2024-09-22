package com.nc1.testapp.newscronparsingapp.repository;

import com.nc1.testapp.newscronparsingapp.model.News;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByPublicationTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    @Query("SELECT n.publicationTime FROM News n ORDER BY n.id DESC")
    LocalDateTime findLatestPublicationTime();
}
