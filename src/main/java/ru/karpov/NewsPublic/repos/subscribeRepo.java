package ru.karpov.NewsPublic.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.karpov.NewsPublic.models.Subscribe;

import java.util.List;

@Repository
public interface subscribeRepo extends JpaRepository<Subscribe, Long> {
    List<Subscribe> findSubscribeByIdUser(final String id);
    Subscribe findSubscribeByIdUserSubscribeAndIdUser(final String IdUserSubscribe, final String isUser);
}
