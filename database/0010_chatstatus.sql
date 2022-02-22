CREATE TABLE ChatStatus
(
    chat_id BIGSERIAL PRIMARY KEY NOT NULL,
    active  boolean               NOT NULL,
    CONSTRAINT fk_chatstatus_chat_id FOREIGN KEY (chat_id) REFERENCES chats (id)
)
