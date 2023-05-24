package com.rps.app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@ToString//Lomboks annotationer för att automatiskt lägga till dessa
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "player_entity")    //player class som motsvarar en tabell i databasen
public class PlayerEntity {


    @Id() //JPA (Java Persistence API) nyckel annotation
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "player_id") //JPA (Java Persistence API) kolumn annotation
    private UUID playerId; //id av typen uuid som autogenereras

    @Column(name = "player_name")
    private String playerName;

    @OneToOne(mappedBy = "playerOneName", cascade = CascadeType.ALL)
    @JoinColumn
    GameEntity playerOneGame;  //En variabel av typen GameEntity kopplad till PlayerEntity för att uppdatera GameEntity när det behövs

    @OneToOne(mappedBy = "playerTwoName", cascade = CascadeType.ALL)
    @JoinColumn
    GameEntity playerTwoGame;  //En variabel av typen GameEntity kopplad till PlayerEntity för att uppdatera GameEntity när det behövs




    //Getters Setters
    public UUID getPlayerId() {
        return playerId;
    }   //Getter för playerId då Lomboks egna annotation verkade inte fungera som det ska

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }   //Setter för playerId då Lomboks egna annotation verkade inte fungera som det ska

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public GameEntity getPlayerOneGame() {
        return playerOneGame;
    }

    public void setPlayerOneGame(GameEntity playerOneGame) {
        this.playerOneGame = playerOneGame;
    }

    public GameEntity getPlayerTwoGame() {
        return playerTwoGame;
    }

    public void setPlayerTwoGame(GameEntity playerTwoGame) {
        this.playerTwoGame = playerTwoGame;
    }

    public void setPlayerOneGame(UUID fromString) {//Setter för att lägga till default in Player service i getUUid
    }

    public void setPlayerTwoGame(UUID fromString) {//För att lägga till default in Player service i getUUid
    }
}
