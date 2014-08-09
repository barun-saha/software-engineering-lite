CREATE TABLE  "isad_selfevaluation" (
  "_id" int(11) NOT NULL,
  "theory_id" int(11) NOT NULL,
  "question_num" int(11) NOT NULL,
  "question" longtext NOT NULL,
  "option1" varchar(100) NOT NULL,
  "option2" varchar(100) NOT NULL,
  "option3" varchar(100),
  "option4" varchar(100),
  "answer" int(11) NOT NULL,
  PRIMARY KEY ("_id"),
  CONSTRAINT "theory_id_refs_id_4c303a0" FOREIGN KEY ("theory_id") REFERENCES "isad_theory" ("_id")
);
