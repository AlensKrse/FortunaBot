CREATE TABLE ChatsFeatures
(
    chat_id        BIGSERIAL            NOT NULL,
    advices_active BOOLEAN DEFAULT TRUE NOT NULL,
    jokes_active   BOOLEAN DEFAULT TRUE NOT NULL,
    memes_active   BOOLEAN DEFAULT TRUE NOT NULL
);