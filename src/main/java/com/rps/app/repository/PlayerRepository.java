package com.rps.app.repository;
import com.rps.app.entities.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
//Repository för PlayerEntity eller med andra ord för att göra CRUD requests gällande spelaren i databasen
@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, UUID> {

}


