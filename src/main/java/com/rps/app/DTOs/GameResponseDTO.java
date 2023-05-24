package com.rps.app.DTOs;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@ToString
@NoArgsConstructor
@Data
public class GameResponseDTO {
    private UUID id;
    private String playerOneName;
    private String playerTwoName;
    private String playerOneMove;
    private String playerTwoMove;
    private String status;
    private Integer playerOneWins;
    private Integer playerTwoWins;
    private LocalDateTime lastUpdated;
}
