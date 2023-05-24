package com.rps.app.repository;
import com.rps.app.entities.GameEntity;
import com.rps.app.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
//Repository för GameEntity eller med andra ord för att göra CRUD requests gällande spelen i databasen

@Repository
public interface GameRepository extends JpaRepository<GameEntity, UUID> {
    List<GameEntity> findByStatus(Status status);
}
