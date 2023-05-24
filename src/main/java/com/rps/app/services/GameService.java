package com.rps.app.services;

import lombok.AllArgsConstructor;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


import com.rps.app.DTOs.PlayerIdDTO;
import com.rps.app.DTOs.GameResponseDTO;

import com.rps.app.mapper.GameInfoOchOpenGameMappertoDTO;
import com.rps.app.mapper.JoinGameMapper;

import com.rps.app.entities.GameEntity;
import com.rps.app.entities.Move;
import com.rps.app.entities.PlayerEntity;
import com.rps.app.entities.Status;

import com.rps.app.repository.GameRepository;
import com.rps.app.repository.PlayerRepository;

import org.springframework.stereotype.Service;


@Service //Markerar klassen som en service klass
@AllArgsConstructor //Konstruktör
public class GameService {
    private final GameRepository gameRepository;  //Kopplar GameService med GameStatusRepository för hantering av användardata
    private final PlayerRepository playerRepository;
    private final JoinGameMapper joinGameMapper;

    //Metod att starta ett nytt spel
    public void startGame(UUID playerId) { //Tar in nya spelarens id som den fick i första steget och sedan kopplade sitt namn till det
        Optional<PlayerEntity> playerOptional = playerRepository.findById(playerId); //Söker spelaren i databasen via repository och tillför infon PlayerEntity objektet playerOptional
        if (playerOptional.isEmpty()) {  //Om spelaren inte finns
            System.out.println("Player not found"); //...skriv ut meddelandet i consolen
            return; //...och stoppa metoden
        }
        PlayerEntity player = playerOptional.get();

        GameEntity game = new GameEntity(); //Skapa ett nytt spel av klassen GameEntity med namnet game
        game.setStatus(Status.OPEN); //Ange spelets status som OPEN

        game.setPlayerOneName(player);  //Lägg första spelaren(player) i tabellen, i det nya spelobjektet/spelet game
        player.setPlayerOneGame(game.getGameId()); //Lägg spelet i spelarens tabell för att skapa kopplingen mellan spelare och spel

        //Spara både spel och spelare i respektive databas via repositories
        gameRepository.save(game);
        playerRepository.save(player);
    }


    //Få en lista med öppna spel mappad via GameResponseDTO och filtrerad på statusen OPEN
    public List<GameResponseDTO> getOpenGames() { //Metoden returnerar en lista innehållande objekt av klassen GameResponseDTO
        List<GameEntity> openGames = gameRepository.findByStatus(Status.OPEN); // Börja med att söka öppna spel och spara de i listobjektet openGames

        return openGames.stream()//Streamma igenom listan
                .map(GameInfoOchOpenGameMappertoDTO::mapToDTO)  //Mappa informationen i varje spel till GameResponseDTO
                .collect(Collectors.toList()); //Returnera de mappade spelen till en lista
    }


    //Metod för att komma in i ett öppet spel
    public GameResponseDTO joinGame(UUID gameId, UUID playerId) {
        Optional<GameEntity> game = gameRepository.findById(gameId); //Tar parameter värdet gameId och söker spel i databasen
        if (game.isPresent() && game.get().getStatus().equals(Status.OPEN)) { //Om det finns öppna spel
            Optional<PlayerEntity> player = playerRepository.findById(playerId); //Sök spelaren med id playerId
            if (player.isPresent()) {//Om spelaren finns

                game.get().setPlayerTwoName(player.get());//Ta ovannämnda spelets tabell och lägg till spelarens id där
                player.get().setPlayerTwoGame(game.get().getGameId());//Ta ovannämnda spelarens tabell och lägg till spelets id där

                game.get().setStatus(Status.ACTIVE); //Ändra det aktuella spelets status till active

                gameRepository.save(game.get()); //Spara spelet i databasen
                playerRepository.save(player.get());//Spara spelaren i databasen

                return joinGameMapper.mapToGameResponseDTO(game.get()); //returnera spelets objekt i form av en DTO
            } else {
                throw new IllegalArgumentException("Player not found"); //Om spelaren nte finns visa meddelandet
            }
        } else {
            throw new IllegalArgumentException("Game not found or not open");//Om spelet nte finns visa meddelandet
        }
    }


    //aktuella spelets information: id, status, vem som spelar och vilka drag de gör oavsett om spelare två är redan kopplad till spelet eller inte
    public GameResponseDTO getGameInfo(UUID gameId, PlayerIdDTO playerId) {
        //Hämta spelet från repository via idn ovan och visa felmeddelande om spelet saknas
        GameEntity game = gameRepository.findById(UUID.fromString(gameId.toString())).orElseThrow(() -> new RuntimeException("Game not found"));

        GameResponseDTO gameResponseDTO = GameInfoOchOpenGameMappertoDTO.mapToDTO(game);

        // Kolla om det är spelare 1 eller 2 som spelar och returnera gameResponseDTO
        if (game.getPlayerOneName() != null && game.getPlayerOneName().getPlayerId().equals(playerId.getPlayerId())) { // Om spelet matchar spelare 1
            return gameResponseDTO; //...returnera DTOn
        } else if (game.getPlayerTwoName() != null && game.getPlayerTwoName().getPlayerId().equals(playerId.getPlayerId())) { // Om spelet matchar spelare 2
            return gameResponseDTO;
        } else {
            throw new RuntimeException("Player not found in the game"); // Om spelaren inte hittas visa felmeddelandet
        }
    }


    //Själva spelet - dvs spelarna gör drag och de räknas och vinnaren anges efter 3 vinster
    //Tar in båda spelarnas speldrag och båda spelarnas id-n. Returnerar GameResponseDTO

   /* public GameResponseDTO move(Move playerOneSign, UUID playerOneId, UUID playerTwoId, Move playerTwoSign) *//*throws GameFinishedException*//* {
        Optional<PlayerEntity> playerOne = playerRepository.findById(playerOneId);//Söker spelare 1 i repository via id från ovan och sparar playerOne
        Optional<PlayerEntity> playerTwo = playerRepository.findById(playerTwoId);//Söker spelare 2 i repository via id från ovan och sparar playerTwo
        if (playerOne.isEmpty() || playerTwo.isEmpty()) {
            throw new EntityNotFoundException("One or both players not found"); //Om någon av de saknas visa meddelandet
        }

        GameEntity game = playerOne.get().getPlayerOneGame(); //Skapa ett spel entity och lagra där första spelarens spelinfo
        if (game == null) {
            throw new EntityNotFoundException("Game not found"); //Om saknas visa meddelandet
        }

        game.setPlayerOneMove(playerOneSign); //Lägg i första spelarens speltabell spelarens drag ovan
        game.setPlayerTwoMove(playerTwoSign);//Lägg i andra spelarens speltabell spelarens drag ovan

        if (game.getPlayerOneMove() != null && game.getPlayerTwoMove() != null) { //Om båda spelarnas drag finns...
            Move playerOneMove = game.getPlayerOneMove(); //.. skapa ett objekt av spel och spara där första spelarens drag
            Move playerTwoMove = game.getPlayerTwoMove(); //.. skapa ett objekt av spel och spara där andra spelarens drag
            if ((playerOneMove == Move.ROCK && playerTwoMove == Move.SCISSORS) ||
                    (playerOneMove == Move.PAPER && playerTwoMove == Move.ROCK) ||
                    (playerOneMove == Move.SCISSORS && playerTwoMove == Move.PAPER)) {
                game.setStatus(Status.PLAYER_ONE_WIN); //Om ovan villkor tillfredsställs sätt spelstatus PLAYER_ONE_WIN, dvs sätt spelare ett som vinnare av spelomgången
            } else if (playerOneMove.equals(playerTwoMove)) {
                game.setStatus(Status.DRAW); //Om dragen är lika sätt Draw
            } else {
                game.setStatus(Status.PLAYER_TWO_WIN); //Annars sätt spelare två som vinnare av spelomgången
            }

            int player1Wins = game.getPlayerOneWins(); //Spara första spelarens vinster i integern player1Wins
            int player2Wins = game.getPlayerTwoWins(); //Spara andra spelarens vinster i integern player1Wins
            if (game.getStatus() == Status.PLAYER_ONE_WIN) { //Om första spelaren vinner en spelomgång...
                player1Wins++; //...öka player1Wins med 1
            } else if (game.getStatus() == Status.PLAYER_TWO_WIN) {//Om andra spelaren vinner en spelomgång...
                player2Wins++; //...öka player2Wins med 1
            }
            game.setPlayerOneWins(player1Wins); //Sätt antalet vinster av spelare 1 i första spelarens speltabell i motsvarande kolumn
            game.setPlayerTwoWins(player2Wins); //Sätt antalet vinster av spelare 2 i första spelarens speltabell i motsvarande kolumn

            if (player1Wins == 3 || player2Wins == 3) { //Om antalet vinster för någon av spelarna är lika med 3...
                if (player1Wins > player2Wins) { //Om spelare1 vinster är större än andra spelarens vinster...
                    game.setStatus(Status.PLAYER_ONE_WIN); //Ange spelets status PLAYER_ONE_WIN
                    game.setPlayerOneWins(player1Wins);//Uppdatera antalet vinster för spelare 1 i speltabellen med senaste vinst
                    System.out.println("Player one wins the game by " + "3 - " + player2Wins + " !");

                } else {
                    game.setStatus(Status.PLAYER_TWO_WIN); //Annars sätt spelare två som vinnare.
                    game.setPlayerOneWins(player1Wins);//Uppdatera antalet vinster för spelare 1 i speltabellen med senaste vinst
                    System.out.println("Player one wins the game by " + "3 - " + player1Wins + " !");

                }
            }
        }
        gameRepository.save(game); //Spara spelet i databasen

        return GameInfoOchOpenGameMappertoDTO.mapToDTO(game);//Mappa spelets info till GameResponsDTO och returnera

    }*/

    public GameResponseDTO move(Move playerMove, UUID playerId, UUID gameId) {
        Optional<PlayerEntity> player = playerRepository.findById(playerId);

        if (player.isEmpty()) {
            throw new EntityNotFoundException("Player not found");
        }

        // Fetch the game using gameId instead of player.getPlayerOneGame()
        GameEntity game = gameRepository.findById(gameId).orElseThrow(() -> new EntityNotFoundException("Game not found"));

        if (game.getPlayerOneName().getPlayerId().equals(playerId)) {
            game.setPlayerOneMove(playerMove);
        } else if (game.getPlayerTwoName().getPlayerId().equals(playerId)) {
            game.setPlayerTwoMove(playerMove);
        } else {
            throw new IllegalArgumentException("Player is not part of the game");
        }

        if (game.getPlayerOneMove() != null && game.getPlayerTwoMove() != null) {
            Move playerOneMove = game.getPlayerOneMove();
            Move playerTwoMove = game.getPlayerTwoMove();

            // Determine the game's outcome
            if ((playerOneMove == Move.ROCK && playerTwoMove == Move.SCISSORS) ||
                    (playerOneMove == Move.PAPER && playerTwoMove == Move.ROCK) ||
                    (playerOneMove == Move.SCISSORS && playerTwoMove == Move.PAPER)) {
                game.setStatus(Status.PLAYER_ONE_WIN);
            } else if (playerOneMove == playerTwoMove) {
                game.setStatus(Status.DRAW);
            } else {
                game.setStatus(Status.PLAYER_TWO_WIN);
            }

            // Update player wins
            int player1Wins = game.getPlayerOneWins();
            int player2Wins = game.getPlayerTwoWins();
            if (game.getStatus() == Status.PLAYER_ONE_WIN) {
                player1Wins++;
            } else if (game.getStatus() == Status.PLAYER_TWO_WIN) {
                player2Wins++;
            }
            game.setPlayerOneWins(player1Wins);
            game.setPlayerTwoWins(player2Wins);

            // Check for overall winner
            if (player1Wins == 3 || player2Wins == 3) {
                if (player1Wins > player2Wins) {
                    game.setStatus(Status.PLAYER_ONE_WIN);
                    System.out.println("Player one wins the game by " + "3 - " + player2Wins + " !");
                } else {
                    game.setStatus(Status.PLAYER_TWO_WIN);
                    System.out.println("Player two wins the game by " + "3 - " + player1Wins + " !");
                }
            }
            game.setPlayerOneMove(null);
            game.setPlayerTwoMove(null);
        }

        game.setLastUpdated(LocalDateTime.now());
        gameRepository.save(game);

        return GameInfoOchOpenGameMappertoDTO.mapToDTO(game);
    }




}