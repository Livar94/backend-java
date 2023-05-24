package com.rps.app.controller;

import com.rps.app.DTOs.MoveRequestDTO;
import com.rps.app.DTOs.GameResponseDTO;
import com.rps.app.DTOs.PlayerIdDTO;
import com.rps.app.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//En separat controller för hantering av anrop gällande själva spelet
@RestController
@RequestMapping("api/games")
public class GameController {

    private final GameService gameService; //Skapar ett objekt av typen GameService

    //Konstruktör injektion av gameService dependency i GameController klassen för att kunna ha tillgång till dess metoder
    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    //Starta ett spel
    @PostMapping("/game")
    public void startGame(@RequestHeader("Token") UUID playerId) {
        gameService.startGame(playerId);
    }


    //Få en lista med öppna spel
    @GetMapping("/games")
    public ResponseEntity<List<GameResponseDTO>> getOpenGames() {
        List<GameResponseDTO> openGames = gameService.getOpenGames();
        return ResponseEntity.ok(openGames);
    }

    //Delta i ett öppet spel
    @GetMapping("/join/{gameId}")
    public GameResponseDTO joinGame(@PathVariable("gameId") UUID gameId, @RequestHeader("Token") UUID playerId) {
        return gameService.joinGame(gameId, playerId);
    }

    //Få aktuell information om spelet
    @GetMapping("/{gameId}")
    public ResponseEntity<GameResponseDTO> getGameInfo(@PathVariable("gameId") UUID gameId, @RequestHeader("Token") UUID playerId) {
        PlayerIdDTO playerIdDTO = new PlayerIdDTO();
        playerIdDTO.setPlayerId(playerId);
        GameResponseDTO gameInfo = gameService.getGameInfo(gameId, playerIdDTO);
        return new ResponseEntity<>(gameInfo, HttpStatus.OK);
    }

    //Spela
    @PostMapping("/move")
    public GameResponseDTO move(@RequestBody MoveRequestDTO moveRequest, @RequestHeader("Token") UUID playerId) {
        moveRequest.setPlayerId(playerId);
        return gameService.move(
                moveRequest.getPlayerMove(),
                moveRequest.getPlayerId(),
                moveRequest.getGameId()
        );

    }
}





