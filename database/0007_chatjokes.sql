CREATE TABLE ChatJokes
(
    chat_id BIGSERIAL NOT NULL,
    joke_id BIGSERIAL,
    used    BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_chatjokes_joke_id FOREIGN KEY (joke_id) REFERENCES Jokes (id)
);