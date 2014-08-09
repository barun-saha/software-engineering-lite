CREATE TABLE "isad_theory" (
  "_id" int(11) NOT NULL ,
  "title" varchar(100) NOT NULL,
  "content" longtext NOT NULL,
  "objectives" longtext,
  "time_required" decimal(4,2) NOT NULL,
  "extra" longtext,
  PRIMARY KEY ("_id")
)
