package com.rps.app.entities;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "game_entity")
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "game_id")
    private UUID gameId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_one_name")
    private PlayerEntity playerOneName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_two_name")
    private PlayerEntity playerTwoName;

    @Enumerated(EnumType.STRING)
    @Column(name = "player_one_move")
    private Move playerOneMove;

    @Enumerated(EnumType.STRING)
    @Column(name = "player_two_move")
    private Move playerTwoMove;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    //lastUpdated timestamp field to track when the game was last updated.
    // This can be useful for the frontend to determine if new data is available:
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Column(name = "player_one_wins", columnDefinition = "int default 0")    //Har lagt till två nya kolumner i GameEntity
    private int PlayerOneWins;
    @Column (name = "player_two_wins",columnDefinition = "int default 0")
    private int PlayerTwoWins;
}
