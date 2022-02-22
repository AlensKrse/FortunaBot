ALTER TABLE chatfeatures ADD CONSTRAINT fk_chatfeatures_chat_id FOREIGN KEY(chat_id) REFERENCES chats(id);
COMMIT;