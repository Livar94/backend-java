package com.rps.app.entities;
//Enum klass. Det är inte en entity och är därmed ingen tabell utan...
// ---bara en klass som används för att skapa ett objekt av det i andra klasser
    public enum Status { //Skapar enum för fasta värden för Status
        NONE, OPEN, ACTIVE, PLAYER_ONE_WIN, PLAYER_TWO_WIN, LOSE, DRAW,
    }
//Har lagt till PLAYER_ONE_WIN, PLAYER_TWO_WIN för att kunna beräkna antalet win per spelare och
// ...returnera vinnaren efter 3 omgångar av spelet. Se metoden Move i Class GameService
