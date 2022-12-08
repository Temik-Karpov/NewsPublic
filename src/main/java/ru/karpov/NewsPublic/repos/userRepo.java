package ru.karpov.NewsPublic.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.karpov.NewsPublic.models.userInfo;

@Repository
public interface userRepo extends JpaRepository<userInfo, Long> {
    userInfo findUserById(final String id);
    userInfo findUserByName(final String name);
}
