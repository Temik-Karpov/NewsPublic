package ru.karpov.NewsPublic.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.karpov.NewsPublic.models.Categories;
import ru.karpov.NewsPublic.models.News;

import java.util.List;

@Repository
public interface newsRepo extends JpaRepository<News, Long> {
    List<News> findNewsByCategory(final Categories category);
    News findNewsById(final int id);
    Page<News> findAll(Pageable pageable);
    List<News> findNewsByAuthorName(final String name);
    List<News> findNewsByCategoryAndAndAuthorName(final Categories category, final String name);
}

