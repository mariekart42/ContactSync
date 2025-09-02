--DROP TABLE SYNCDEVICE ;
--DROP TABLE SYNCUSER ;
--DROP TABLE SYNCABONNEMENT ;
--DROP TABLE SYNCPRINCIPAL ;

create table SYNCDEVICE
(
  syncdeviceid    INTEGER primary key,
  device          VARCHAR(200), -- account + Pfad zum Kontaktordner, in den die Kontakte sollen, z.B. kraemer@kieback-peter.de/AditoKontakte
  description     VARCHAR(200), -- enthält 'Exchange' für die Exchange-Accounts
  tzid            VARCHAR(100), -- TimeZoneId des device, z.B. 'Europe/Berlin'
  devicespecifics VARCHAR(1000), -- details für die Ablage im Ziel, z.B. 'fileAsMapping=LastCommaFirstCompany' bei Exchange
  avoidfields     VARCHAR(500) -- liste von Feldern, die der User ausgeschlossen hat (die also nicht in die Kontakte im Device übernommen werden sollen), z.B. '*privat*bfax*email3*'
);

create table SYNCUSER
(
  syncuserid INTEGER primary key,
  username   VARCHAR(200), -- hat früher FQDN aus Novell/ActiveDirectory enthalten, jetzt unbenutzt (wird mit aditouser befüllt)
  aditouser  VARCHAR(200) -- adito login name
);

create table SYNCPRINCIPAL
(
  syncprincipalid INTEGER primary key,
  syncuser_id     INTEGER references SYNCUSER (SYNCUSERID),
  syncdevice_id   INTEGER references SYNCDEVICE (SYNCDEVICEID),
  syncresult      VARCHAR(4000) -- Ergebnis des Abgleichs für diesen Principal: 'ok' wenn alle Kontakte des Principals erfolgreich abgegklichen wurden, ansonsten die letzte Fehlermeldung (z.B. Stacktrace)
);

create table SYNCABONNEMENT
(
  syncabonnementid INTEGER primary key,
  principal        INTEGER references syncprincipal(syncprincipalid),
  dbname           VARCHAR(40), -- immer 'person'
  guid             VARCHAR(400), -- personid in tabelle person
  luid             VARCHAR(400), -- msgraph itemid des kontakts
  abostart         DATE, -- wann der Anwender den AditoKontakt abonniert hat
  aboende          DATE, -- wann der Anwender das Abbonement des AditoKontakts beendet hat
  changed          DATE, -- letzte Änderung an den dem AditoKontakt zugrundeliegenden Daten
  synced           DATE, -- wann das Programm ContactSync den Abgleich Adito => für diesen Eintrag durchgeführt hat
  to_external      VARCHAR(4000), -- welche Daten beim letzten erfolgreichen Abgleich für diesen Eintrag verwendet wurden
  from_external    VARCHAR(4000), -- bleibt leer
  syncresult       VARCHAR(4000) -- ergebnis des abgleichs für diesen Eintrag: 'ok' bei Erfolg ansonsten Fehlermeldung (z.B. Stacktrace)
);



