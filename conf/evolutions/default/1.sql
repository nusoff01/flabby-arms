# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table hospital (
  id                        bigint not null,
  hospital_id               bigint,
  hospital_name             varchar(255),
  latitude                  double,
  longitude                 double,
  street_address            varchar(255),
  explain_med               integer,
  sound_intol               integer,
  nurse_qual                integer,
  bedside_man               integer,
  pain                      integer,
  bathroom                  integer,
  promptness                integer,
  constraint pk_hospital primary key (id))
;

create sequence hospital_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists hospital;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists hospital_seq;

