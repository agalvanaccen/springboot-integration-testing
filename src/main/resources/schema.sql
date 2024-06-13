DROP TABLE IF EXISTS "songs";
DROP TABLE IF EXISTS "albums";
DROP TABLE IF EXISTS "artists";

CREATE TABLE "artists" (
  "id" SERIAL PRIMARY KEY NOT NULL,
  "name" varchar NOT NULL,
  "lastname" varchar NOT NULL,
  "created_at" timestamp
);

CREATE TABLE "albums" (
  "id" SERIAL PRIMARY KEY NOT NULL,
  "title" varchar NOT NULL,
  "cover_url" varchar,
  "created_at" timestamp,
  "artist_id" integer NOT NULL
);

CREATE TABLE "songs" (
  "id" SERIAL PRIMARY KEY NOT NULL,
  "title" varchar NOT NULL,
  "duration" time NOT NULL,
  "created_at" timestamp,
  "album_id" integer NOT NULL
);

ALTER TABLE "albums" ADD FOREIGN KEY ("artist_id") REFERENCES "artists" ("id");

ALTER TABLE "songs" ADD FOREIGN KEY ("album_id") REFERENCES "albums" ("id");