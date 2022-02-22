CREATE TABLE ChatAdvices
(
    chat_id   BIGSERIAL NOT NULL,
    advice_id BIGSERIAL,
    used      BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_chatadvices_advice_id FOREIGN KEY (advice_id) REFERENCES Advices (id)
);

