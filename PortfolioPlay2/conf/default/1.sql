# --- !Ups

create table "TRANSACTION" (
  "ID" bigint generated by default as identity(start with 1) not null primary key,
  "TICKER" varchar not null,
  "NUM_SHARES" int,
  "ACTION"
);

-- def amount : Option[Double]
--   def action : String
--   def date : Option[LocalDate]
--   def ticker: String;
--   def numShares: Option[Double]

# --- !Downs

drop table "TRANSACTION" if exists;
