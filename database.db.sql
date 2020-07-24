BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "login" (
	"username"	TEXT,
	"password"	TEXT);
INSERT INTO "login" ("username","password") VALUES ('Elma','47');
COMMIT;
