
**CREATE TABLE game_entity (
game_id UUID PRIMARY KEY,
player_one_name UUID,
player_two_name UUID,
player_one_move VARCHAR(255),
player_two_move VARCHAR(255),
status VARCHAR(255),
player_one_wins int,
player_two_wins int
);

CREATE TABLE player_entity (
player_id UUID PRIMARY KEY,
player_name VARCHAR(255),
player_one_game UUID,
player_two_game UUID
);

ALTER TABLE game_entity
ADD COLUMN last_updated TIMESTAMP;


ALTER TABLE game_entity
ADD FOREIGN KEY (player_one_name) REFERENCES player_entity(player_id)
ON UPDATE CASCADE
ON DELETE CASCADE;

ALTER TABLE game_entity
ADD FOREIGN KEY (player_two_name) REFERENCES player_entity(player_id)
ON UPDATE CASCADE
ON DELETE CASCADE;