### getUuid (Funkar)
GET http://localhost:8080/api/user/auth/token


### setName (Funkar)
POST http://localhost:8080/api/user/name
Content-Type: application/json
Token: 811ebe37-745e-4bef-a214-ee33d470fc34

{
"name": "Edda"
}



### StartGame (Funkar)
POST http://localhost:8080/api/games/game
Content-Type: application/json
Token: 811ebe37-745e-4bef-a214-ee33d470fc34


<> 2023-02-14T021939.500.json



### getOpenGames (Funkar)
GET http://localhost:8080/api/games/games



### Join an open game (Funkar)
GET http://localhost:8080/api/games/join/0a473059-d568-4d8a-bfbe-0b63534575a8
Content-Type: application/json
Token: 276ccef6-4946-44cb-8fdc-078c19019d2e



### gameInfo (Funkar)
GET http://localhost:8080/api/games/0a473059-d568-4d8a-bfbe-0b63534575a8
Content-Type: application/json
Token: 276ccef6-4946-44cb-8fdc-078c19019d2e



### MovePlayer1 & MovePlayer2 (Funkar)
POST http://localhost:8080/api/games/move
Content-Type: application/json
Token: 811ebe37-745e-4bef-a214-ee33d470fc34

{
"playerMove": "PAPER",
"gameId": "0a473059-d568-4d8a-bfbe-0b63534575a8"
}

