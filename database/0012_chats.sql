ALTER TABLE chats ADD COLUMN active BOOLEAN DEFAULT TRUE NOT NULL;

ALTER TABLE chatstatus drop constraint fk_chatstatus_chat_id;
DROP TABLE chatstatus;

COMMIT;