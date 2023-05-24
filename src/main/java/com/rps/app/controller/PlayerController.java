package com.rps.app.controller;
import com.rps.app.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import com.rps.app.DTOs.UpdatePlayerDTO;

//Controller för anrop kopplade till token/spelare

@RestController

@RequestMapping(value = "/api/user",
        produces = MediaType.APPLICATION_JSON_VALUE
        )
//@RequestMapping( "api/user" )   //Anrop till playerServices
public class PlayerController {

private final PlayerService playerService;


    @Autowired  //Kopplat controller med playerService
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }



    @GetMapping("/auth/token")  //Riktar förfrågan av att generera ett token/uuid via /uuid till getUuid metoden
    public UUID getUuid() {
        return playerService.getUuid().getPlayerId();
    }    //Hämtar token/spel/spelare id från serviceklassen för att skapa en spelare



    //Setter ett namn på UUID ovan. Namnet får den via @RequestBody som kräver från postsystemet att posta in ett jayson
    //...body med namn och koppla det till player med angivet player id.
    @PostMapping("/name")
    public ResponseEntity<Void> setName(@RequestHeader("Token") UUID playerId, @RequestBody UpdatePlayerDTO requestDTO) {
        playerService.setName(playerId, requestDTO);
        return new ResponseEntity<>(HttpStatus.OK); //Returnerar HTTPStatus.Ok i form av HTTP/1.1 200 som betyder att allt gick väl
    }

}


