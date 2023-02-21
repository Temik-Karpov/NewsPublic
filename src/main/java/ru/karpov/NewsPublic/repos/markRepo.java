package ru.karpov.NewsPublic.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.karpov.NewsPublic.models.Mark;

@Repository
public interface markRepo extends JpaRepository<Mark, Long> {
    Mark findAllByIdUserAndIdNews(String idUser, Integer idNews);
}
