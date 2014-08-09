CREATE TABLE  "isad_reference" (
  "_id" int(11) NOT NULL,
  "theory_id" int(11) NOT NULL,
  "url" varchar(200),
  "url_desc" longtext,
  "book_id" int(11),
  PRIMARY KEY ("_id"),
  CONSTRAINT "book_id_refs_id_18398064" FOREIGN KEY ("book_id") REFERENCES "isad_book" ("_id"),
  CONSTRAINT "theory_id_refs_id_42b9256" FOREIGN KEY ("theory_id") REFERENCES "isad_theory" ("_id")
);
