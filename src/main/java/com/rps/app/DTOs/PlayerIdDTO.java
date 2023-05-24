package com.rps.app.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

//Har skapat DTOn för att mappa spelarnas id-n i gameInfo och joinGame metoderna och inte exponera PlayerEntity
@ToString
@NoArgsConstructor
@Data
public class PlayerIdDTO {


@JsonProperty
   UUID playerId;

}
