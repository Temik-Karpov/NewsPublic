package ru.karpov.NewsPublic.repos;

import jdk.jfr.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.karpov.NewsPublic.models.News;

import java.util.List;

@Repository
public interface newsRepo extends JpaRepository<News, Long> {
    List<News> findNewsByCategory(final Category category);
    News findNewsById(final int id);
}
