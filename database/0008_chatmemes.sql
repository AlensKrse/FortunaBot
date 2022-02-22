CREATE TABLE ChatMemes
(
    chat_id BIGSERIAL NOT NULL,
    meme_id BIGSERIAL,
    used    BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_chatmemes_meme_id FOREIGN KEY (meme_id) REFERENCES Memes (id)
);