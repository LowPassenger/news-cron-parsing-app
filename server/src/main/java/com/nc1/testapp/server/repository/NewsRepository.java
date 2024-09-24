package com.nc1.testapp.server.repository;

import com.nc1.testapp.common.model.News;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByPublicationTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    @Query("SELECT n.publicationTime FROM News n ORDER BY n.id DESC")
    LocalDateTime findLatestPublicationTime();

    @Query("SELECT n FROM News n WHERE n.publicationTime >= :startOfDay "
            + "AND n.publicationTime <= :endOfDay")
    List<News> findTodayNews(@Param("startOfDay") LocalDateTime startOfDay,
                             @Param("endOfDay") LocalDateTime endOfDay);
}
