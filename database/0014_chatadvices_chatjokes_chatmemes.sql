ALTER TABLE chatadvices RENAME COLUMN used TO is_used;
ALTER TABLE chatjokes RENAME COLUMN used TO is_used;
ALTER TABLE chatmemes RENAME COLUMN used TO is_used;

COMMIT;