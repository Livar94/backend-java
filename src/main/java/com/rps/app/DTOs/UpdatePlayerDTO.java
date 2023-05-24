package com.rps.app.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@ToString
@NoArgsConstructor
@Data
public class UpdatePlayerDTO { //DTO för att lägga till namn på spelare i setName metoden i GameService
   @JsonProperty
   String name;


}
