package com.rps.app.mapper;

import com.rps.app.DTOs.GameResponseDTO;
import com.rps.app.entities.GameEntity;
import org.springframework.stereotype.Component;

@Component
public class GameInfoOchOpenGameMappertoDTO {
    public static GameResponseDTO mapToDTO(GameEntity game) {
        GameResponseDTO gameResponseDTO = new GameResponseDTO();
        gameResponseDTO.setId(game.getGameId());
        gameResponseDTO.setPlayerOneName(game.getPlayerOneName().getPlayerName());
        gameResponseDTO.setPlayerTwoName(game.getPlayerTwoName() != null ? game.getPlayerTwoName().getPlayerName() : "Waiting for the second player to join the game! "); //Default värde om spelare 2 saknas
        gameResponseDTO.setStatus(game.getStatus().toString());
        gameResponseDTO.setPlayerOneMove(game.getPlayerOneMove() != null ? game.getPlayerOneMove().toString() : "");//Default värde om spelet inte är startat än
        gameResponseDTO.setPlayerTwoMove(game.getPlayerTwoMove() != null ? game.getPlayerTwoMove().toString() : "");//Default värde om spelet inte är startat än
        gameResponseDTO.setPlayerOneWins(game.getPlayerOneWins());
        gameResponseDTO.setPlayerTwoWins(game.getPlayerTwoWins());
        gameResponseDTO.setLastUpdated(game.getLastUpdated());
        return gameResponseDTO;
    }
}
