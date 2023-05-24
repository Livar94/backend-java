package com.rps.app.mapper;

import com.rps.app.DTOs.GameResponseDTO;
import com.rps.app.entities.GameEntity;
import com.rps.app.entities.PlayerEntity;
import org.springframework.stereotype.Component;

   @Component
    public class JoinGameMapper {
        public GameResponseDTO mapToGameResponseDTO(GameEntity game) {
            GameResponseDTO gameResponseDTO = new GameResponseDTO();
            gameResponseDTO.setId(game.getGameId());
            PlayerEntity playerOneName = game.getPlayerOneName();
            PlayerEntity playerTwoName = game.getPlayerTwoName();
            if (playerOneName != null) {
                gameResponseDTO.setPlayerOneName(playerOneName.getPlayerId().toString());
                gameResponseDTO.setPlayerTwoName( playerTwoName.getPlayerId().toString());
                      }
            gameResponseDTO.setStatus(game.getStatus().toString());
            return gameResponseDTO;
        }
    }

