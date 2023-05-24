package com.rps.app.services;

import com.rps.app.DTOs.UpdatePlayerDTO;
import com.rps.app.entities.PlayerEntity;
import com.rps.app.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Service
public class PlayerService {

    private static PlayerRepository playerRepository;


    //Constructor
    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        PlayerService.playerRepository = playerRepository;
    }


    //Skapar och sparar id
    public PlayerEntity getUuid() {
        PlayerEntity playerEntity = new PlayerEntity(); //skapar en instance av playerEntity inuti getUuid metoden för att skapa uuid till det.
        UUID uuid = randomUUID(); //Genererar token/id
        playerEntity.setPlayerId(uuid); //setter token/id till ovan genererade token
        playerEntity.setPlayerName("Anonymous");
        playerEntity.setPlayerOneGame(UUID.fromString("00000000-0000-0000-0000-000000000000")); //Default värden för att inte få null value exception
        playerEntity.setPlayerTwoGame(UUID.fromString("00000000-0000-0000-0000-000000000000"));

        playerRepository.save(playerEntity); //Sparar token/id i repository
        return playerEntity; //Returnerar token/id
    }


    //Ger ovan player ett namn genom att mappa playerEntity via UpdatePlayerDTOs instance requestDTO
    public void setName(UUID playerId, UpdatePlayerDTO requestDTO) {
        Optional<PlayerEntity> player = playerRepository.findById(playerId); //Skapa et playerEntity objekt och sök spelare via ovan uuid och ange värdet till objektet
        if (player.isPresent()) {  //Om spelaren motsvarande uuid-n finns
            PlayerEntity playerEntity = player.get();  //...hämta den
            playerEntity.setPlayerName(requestDTO.getName());  //Sätt namnet från DTO-n som fås via json body till namnvariabeln i player objektet
            playerRepository.save(playerEntity); //Spara det uppdaterade entiteten via repository i databasen
        }
    }

}




