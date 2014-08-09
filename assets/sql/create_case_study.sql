CREATE TABLE "isad_casestudy" (
  "_id" int(11) NOT NULL,
  "theory_id" int(11) NOT NULL,
  "title" varchar(125),
  "problem" longtext,
  "analysis" longtext,
  PRIMARY KEY ("_id"),
  CONSTRAINT "theory_id_refs_id_4a79800e" FOREIGN KEY ("theory_id") REFERENCES "isad_theory" ("_id")
);
