ALTER TABLE advices RENAME COLUMN advice TO text;
ALTER TABLE jokes RENAME COLUMN advice TO text;
ALTER TABLE memes RENAME COLUMN advice TO text;
COMMIT;