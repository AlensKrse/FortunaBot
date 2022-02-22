ALTER TABLE chatsfeatures
    RENAME TO chatfeatures;
ALTER TABLE chatfeatures
    ADD CONSTRAINT pk_chatfeatures PRIMARY KEY (chat_id);
COMMIT;