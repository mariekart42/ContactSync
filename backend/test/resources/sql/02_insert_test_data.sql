


--INSERT INTO SYNCUSER (syncuserid, username, aditouser) VALUES
--(1, 'DOMAIN\\jschmidt', 'jschmidt'),
--(2, 'DOMAIN\\mhoffmann', 'mhoffmann'),
--(3, 'DOMAIN\\aklein', 'aklein'),
--(4, 'DOMAIN\\tmueller', 'tmueller'),
--(5, 'DOMAIN\\lfrank', 'lfrank'),
--(6, 'DOMAIN\\bmeier', 'bmeier'),
--(7, 'DOMAIN\\cschulz', 'cschulz'),
--(8, 'DOMAIN\\dhuber', 'dhuber'),
--(9, 'DOMAIN\\esommer', 'esommer'),
--(10, 'DOMAIN\\fkoch', 'fkoch');
--
--INSERT INTO SYNCDEVICE (syncdeviceid, device, description, tzid, devicespecifics, avoidfields) VALUES
--(1, 'jschmidt@example.com/AditoKontakte', 'Exchange Online', 'Europe/Berlin', 'fileAsMapping=LastCommaFirstCompany', '*privat*bfax*email3*'),
--(2, 'mhoffmann@example.com/CRMContacts', 'Exchange Online', 'Europe/Berlin', 'fileAsMapping=FirstLast', '*privat*'),
--(3, 'aklein@example.com/OutlookSync', 'Outlook Desktop', 'Europe/Berlin', 'fileAsMapping=LastFirst', '*note*'),
--(4, 'tmueller@example.com/MobileContacts', 'Mobile Sync', 'Europe/Berlin', 'fileAsMapping=CompanyFirstLast', '*fax*email3*'),
--(5, 'lfrank@example.com/GoogleContacts', 'Google Contacts', 'Europe/Berlin', 'fileAsMapping=FirstLast', '*intern*'),
--(6, 'bmeier@example.com/Salesforce', 'Salesforce', 'Europe/Berlin', 'fileAsMapping=LastCommaFirst', '*private*'),
--(7, 'cschulz@example.com/AditoKontakte', 'Exchange Online', 'Europe/Berlin', 'fileAsMapping=LastFirst', '*fax*'),
--(8, 'dhuber@example.com/CRMContacts', 'CRM System', 'Europe/Berlin', 'fileAsMapping=CompanyFirstLast', '*intern*'),
--(9, 'esommer@example.com/MobileSync', 'Mobile Device', 'Europe/Berlin', 'fileAsMapping=FirstLast', '*bfax*'),
--(10, 'fkoch@example.com/GoogleSync', 'Google Sync', 'Europe/Berlin', 'fileAsMapping=FirstLastCompany', '*email2*');
--
--INSERT INTO SYNCPRINCIPAL (syncprincipalid, syncuser_id, syncdevice_id, syncresult) VALUES
--(1, 1, 1, 'ok'),
--(2, 2, 2, 'Fehler beim Syncen von Kontakt 1234: Feld email2 leer'),
--(3, 3, 3, 'ok'),
--(4, 4, 4, 'ok'),
--(5, 5, 5, 'Fehler beim Syncen von Kontakt 5678: Telefonnummer ungültig'),
--(6, 6, 6, 'ok'),
--(7, 7, 7, 'Fehler: Formatierungsproblem bei Adresse'),
--(8, 8, 8, 'ok'),
--(9, 9, 9, 'Fehler beim Abruf der externen Daten'),
--(10, 10, 10, 'ok');
--
--INSERT INTO SYNCABONNEMENT (syncabonnementid, principal, dbname, guid, luid, abostart, aboende, changed, synced, to_external, from_external, syncresult) VALUES
--(1, 1, 'person', 'uuid-001', 'graph-001', DATE '2024-01-10', NULL, DATE '2024-01-15', DATE '2024-01-16', '{"email":"kontakt1@example.com"}', NULL, 'ok'),
--(2, 2, 'person', 'uuid-002', 'graph-002', DATE '2024-01-12', DATE '2024-06-01', DATE '2024-01-13', DATE '2024-01-14', '{"email":"kontakt2@example.com"}', NULL, 'ok'),
--(3, 3, 'firma', 'uuid-003', 'crm-003', DATE '2024-02-01', NULL, DATE '2024-02-05', DATE '2024-02-06', '{"name":"Musterfirma"}', '{"phone":"030123456"}', 'ok'),
--(4, 4, 'person', 'uuid-004', 'graph-004', DATE '2024-03-10', DATE '2024-07-01', DATE '2024-03-12', DATE '2024-03-13', '{"email":"kontakt4@example.com"}', NULL, 'ok'),
--(5, 5, 'person', 'uuid-005', 'graph-005', DATE '2024-04-01', NULL, DATE '2024-04-10', NULL, NULL, NULL, 'offen'),
--(6, 6, 'person', 'uuid-006', 'graph-006', DATE '2024-02-15', NULL, DATE '2024-02-18', DATE '2024-02-20', '{"email":"kontakt6@example.com"}', NULL, 'ok'),
--(7, 7, 'firma', 'uuid-007', 'crm-007', DATE '2024-03-01', DATE '2024-05-30', DATE '2024-03-02', DATE '2024-03-04', '{"name":"Beispielfirma"}', NULL, 'ok'),
--(8, 8, 'person', 'uuid-008', 'graph-008', DATE '2024-05-01', NULL, DATE '2024-05-02', NULL, NULL, NULL, 'fehlerhaft'),
--(9, 9, 'person', 'uuid-009', 'graph-009', DATE '2024-04-12', NULL, DATE '2024-04-14', DATE '2024-04-15', '{"email":"kontakt9@example.com"}', NULL, 'ok'),
--(10, 10, 'person', 'uuid-010', 'graph-010', DATE '2024-06-01', NULL, DATE '2024-06-05', DATE '2024-06-06', '{"email":"kontakt10@example.com"}', NULL, 'ok');



insert into syncuser (SYNCUSERID, USERNAME, ADITOUSER)
values (1, 'Landgraf', 'Stefan Landgraf');

insert into syncuser (SYNCUSERID, USERNAME, ADITOUSER)
values (2, 'Rüdiger Schwart', 'Rüdiger Schwart');

insert into syncuser (SYNCUSERID, USERNAME, ADITOUSER)
values (3, 'Rene Tolger', 'Rene Tolger');

insert into syncuser (SYNCUSERID, USERNAME, ADITOUSER)
values (4, 'Norbert Herlitz', 'Norbert Herlitz');

insert into syncuser (SYNCUSERID, USERNAME, ADITOUSER)
values (5, 'Rainer Böhm', 'Rainer Böhm');

insert into syncuser (SYNCUSERID, USERNAME, ADITOUSER)
values (6, 'Markus Quintes', 'Markus Quintes');

insert into syncuser (SYNCUSERID, USERNAME, ADITOUSER)
values (7, 'Hubert Huber', 'Hubert Huber');

insert into syncuser (SYNCUSERID, USERNAME, ADITOUSER)
values (8, 'Michaela Wassermann', 'Michaela Wassermann');

insert into syncuser (SYNCUSERID, USERNAME, ADITOUSER)
values (9, 'Gürhan Akgül', 'Gürhan Akgül');

insert into syncuser (SYNCUSERID, USERNAME, ADITOUSER)
values (10, 'Thomas Sierpinski', 'Thomas Sierpinski');





insert into syncdevice (SYNCDEVICEID, DEVICE, DESCRIPTION, TZID, DEVICESPECIFICS, AVOIDFIELDS)
values (1, 'roemer@kieback-peter.de/AditoKontakteee1', 'Exchange', 'Europe/Berlin', 'fileAsMapping=LastCommaFirst', null);

-- using this rn:
insert into syncdevice (SYNCDEVICEID, DEVICE, DESCRIPTION, TZID, DEVICESPECIFICS, AVOIDFIELDS)
values (2, 'kolb-kl@kieback-peter.de/A/B/HERE/folder', 'Exchange', 'Europe/Berlin', 'fileAsMapping=LastCommaFirst', '*web*');

insert into syncdevice (SYNCDEVICEID, DEVICE, DESCRIPTION, TZID, DEVICESPECIFICS, AVOIDFIELDS)
values (3, 'boehm@kieback-peter.de/AditoKontakteee2/lol1/lol2', 'Exchange', 'Europe/Berlin', 'fileAsMapping=LastCommaFirst', '*');

insert into syncdevice (SYNCDEVICEID, DEVICE, DESCRIPTION, TZID, DEVICESPECIFICS, AVOIDFIELDS)
values (4, 'walbroehl@kieback-peter.de/AditoKontakteee3', 'Exchange', 'Europe/Berlin', 'fileAsMapping=LastFirstCompany', '*privat*otherfax*bfax*info*');

insert into syncdevice (SYNCDEVICEID, DEVICE, DESCRIPTION, TZID, DEVICESPECIFICS, AVOIDFIELDS)
values (5, 'peetz@kieback-peter.de/AditoKontakteee4', 'Exchange', 'Europe/Berlin', 'fileAsMapping=LastCommaFirst', null);

insert into syncdevice (SYNCDEVICEID, DEVICE, DESCRIPTION, TZID, DEVICESPECIFICS, AVOIDFIELDS)
values (6, 'janoschek@kieback-peter.de/AditoKontakteee5', 'Exchange', 'Europe/Berlin', 'fileAsMapping=LastCommaFirst', null);

insert into syncdevice (SYNCDEVICEID, DEVICE, DESCRIPTION, TZID, DEVICESPECIFICS, AVOIDFIELDS)
values (7, 'wassermann@kieback-peter.de/', 'Exchange', 'Europe/Berlin', 'fileAsMapping=LastCommaFirst', null);

insert into syncdevice (SYNCDEVICEID, DEVICE, DESCRIPTION, TZID, DEVICESPECIFICS, AVOIDFIELDS)
values (8, 'fuehrer@kieback-peter.de/AditoKontakteee6', 'Exchange', 'Europe/Berlin', 'fileAsMapping=LastCommaFirst', null);

insert into syncdevice (SYNCDEVICEID, DEVICE, DESCRIPTION, TZID, DEVICESPECIFICS, AVOIDFIELDS)
values (9, 'drechsel@kieback-peter.de/AditoKontakteee7', 'Exchange', 'Europe/Berlin', 'fileAsMapping=LastCommaFirst', '*anrede*funktion*privat*bphone2*bfax*info*');

insert into syncdevice (SYNCDEVICEID, DEVICE, DESCRIPTION, TZID, DEVICESPECIFICS, AVOIDFIELDS)
values (10, 'motschmann@kieback-peter.de/AditoKontakteee8', 'Exchange', 'Europe/Berlin', 'fileAsMapping=LastCommaFirst', null);





insert into syncprincipal (SYNCPRINCIPALID, SYNCUSER_ID, SYNCDEVICE_ID, SYNCRESULT)
values (1, 1, 1, 'javax.xml.ws.soap.SOAPFaultException: javax.net.ssl.SSLHandshakeException: SSLHandshakeException invoking https://login.microsoftonline.com/061e1a99-02ec-46c0-a39f-fde61ea97bbf/oauth2/v2.0/token: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target');

insert into syncprincipal (SYNCPRINCIPALID, SYNCUSER_ID, SYNCDEVICE_ID, SYNCRESULT)
values (2, 2, 2, 'ok');

insert into syncprincipal (SYNCPRINCIPALID, SYNCUSER_ID, SYNCDEVICE_ID, SYNCRESULT)
values (3, 3, 3, 'ok');

insert into syncprincipal (SYNCPRINCIPALID, SYNCUSER_ID, SYNCDEVICE_ID, SYNCRESULT)
values (4, 4, 3, 'ok');

insert into syncprincipal (SYNCPRINCIPALID, SYNCUSER_ID, SYNCDEVICE_ID, SYNCRESULT)
values (5, 5, 3, 'ok');

insert into syncprincipal (SYNCPRINCIPALID, SYNCUSER_ID, SYNCDEVICE_ID, SYNCRESULT)
values (6, 6, 3, 'ok');

insert into syncprincipal (SYNCPRINCIPALID, SYNCUSER_ID, SYNCDEVICE_ID, SYNCRESULT)
values (7, 7, 7, 'ok');

insert into syncprincipal (SYNCPRINCIPALID, SYNCUSER_ID, SYNCDEVICE_ID, SYNCRESULT)
values (8, 8, 8, 'javax.xml.ws.soap.SOAPFaultException: javax.net.ssl.SSLHandshakeException: SSLHandshakeException invoking https://login.microsoftonline.com/061e1a99-02ec-46c0-a39f-fde61ea97bbf/oauth2/v2.0/token: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target');

insert into syncprincipal (SYNCPRINCIPALID, SYNCUSER_ID, SYNCDEVICE_ID, SYNCRESULT)
values (9, 9, 9, 'ok');

insert into syncprincipal (SYNCPRINCIPALID, SYNCUSER_ID, SYNCDEVICE_ID, SYNCRESULT)
values (10, 10, 10, 'ok');
--
--
--
--
--
--
--
--
--insert into syncabonnement (SYNCABONNEMENTID, PRINCIPAL, DBNAME, GUID, LUID, ABOSTART, ABOENDE, CHANGED, SYNCED, TO_EXTERNAL, FROM_EXTERNAL, SYNCRESULT)
--values (1, 1,  'person', '119208', null, to_date('14-05-2013 14:17:25', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-03-2025 12:50:15', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-03-2025 12:50:15', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-03-2025 12:50:15', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'ok');



-- to_create: null, to_date('10-08-2023 17:15:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-08-2023 17:15:29', 'dd-mm-yyyy hh24:mi:ss'), null
-- to_change: to_date('13-12-2010 12:00:05', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('10-08-2023 17:15:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-08-2023 17:15:29', 'dd-mm-yyyy hh24:mi:ss')
-- to_delete: to_date('13-12-2010 12:00:05', 'dd-mm-yyyy hh24:mi:ss'), to_date('13-12-2010 12:00:05', 'dd-mm-yyyy hh24:mi:ss'), to_date('10-08-2023 17:15:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-08-2023 17:15:29', 'dd-mm-yyyy hh24:mi:ss')


-- 1. changed
insert into syncabonnement (SYNCABONNEMENTID, PRINCIPAL, DBNAME, GUID, LUID, ABOSTART, ABOENDE, CHANGED, SYNCED, TO_EXTERNAL, FROM_EXTERNAL, SYNCRESULT)
values (2, 3,  'person', '5500' , 'AAMkADg4NWM4MjMxLTc0ZjQtNGU4ZC05ZDc2LTgwMDJkNjAyNWE0YwBGAAAAAABb4L58lBuvQaM7Snxz45CZBwDKuT-HM9gZQIxqxJFFZgGEAACPGawRAADKuT-HM9gZQIxqxJFFZgGEAACTVNlWAAA=', to_date('13-12-2010 12:00:05', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('10-08-2023 17:15:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-08-2023 17:15:29', 'dd-mm-yyyy hh24:mi:ss'), 'keymapversion=6
anrede=
funktion=Inhaber/in
name=OLD
geb=20150924
vorname=SOMEONE
geb=20150924
p_strasse=
p_plz=
p_ort=
p_lkz=
bphone1=+49 4532 4047 205
bphone2=
mobile=+49 1738029528
carphone=
otherfax=+49 3834 514954
bfax=
companyphone=+49 3834 817444
web=
firma=Ing.-Büro Wilfried Lüthen
strasse=Andreas-Mayer-Str. 23
ort=Greifswald
plz=17491
email1=
email2=w.luethen@t-online.de
email3=W.Luethen@t-online.de
info=', null, 'ok');

-- 2. new
insert into syncabonnement (SYNCABONNEMENTID, PRINCIPAL, DBNAME, GUID, LUID, ABOSTART, ABOENDE, CHANGED, SYNCED, TO_EXTERNAL, FROM_EXTERNAL, SYNCRESULT)
values (3, 3,  'person', '119425', 'AAMkADNlNWY2NGExLWNlNDktNDE4NS1hOTVlLTQyODQwNmMxOTU1YwBGAAAAAACnJReMRhWiRq9onOKs7XIjBwAZ30egabutS5wuk6BFuRl9ACv31TAPAACioPAGydWQSIhht/CGnkaaAAAAAODvAAA=', null, to_date('27-02-2023 14:40:28', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-02-2023 14:40:28', 'dd-mm-yyyy hh24:mi:ss'), null, 'keymapversion=6
anrede=
funktion=vom VI noch anzugeben
name=NEW NAME
geb=
vorname=Bernd
geb=
p_strasse=
p_plz=
p_ort=
p_lkz=
bphone1=+49 4532 4047 205
bphone2=
mobile=
carphone=
otherfax=+49 4532 4047 250
bfax=+49 4532 4047 77
companyphone=+49 4532 4047 0
web=www.bargteheide.de
firma=Stadt Bargteheide Der Bürgermeisterin
strasse=Rathausstr. 24-26
ort=Bargteheide
plz=22941
email1=wohlfahrt@bargteheide.de
email2=
email3=info@bargteheide.de
info=', null, 'ok');



-- 3. deleted
insert into syncabonnement (SYNCABONNEMENTID, PRINCIPAL, DBNAME, GUID, LUID, ABOSTART, ABOENDE, CHANGED, SYNCED, TO_EXTERNAL, FROM_EXTERNAL, SYNCRESULT)
values (9, 3,  'person', '151058', 'AAMkADg4NWM4MjMxLTc0ZjQtNGU4ZC05ZDc2LTgwMDJkNjAyNWE0YwBGAAAAAABb4L58lBuvQaM7Snxz45CZBwDKuT-HM9gZQIxqxJFFZgGEAACPGawRAADKuT-HM9gZQIxqxJFFZgGEAACTVNlVAAA=', to_date('30-01-2023 10:37:17', 'dd-mm-yyyy hh24:mi:ss'), to_date('30-01-2023 10:37:17', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-08-2023 17:16:28', 'dd-mm-yyyy hh24:mi:ss'), to_date('01-01-2023 17:16:28', 'dd-mm-yyyy hh24:mi:ss'), 'keymapversion=6
anrede=override
funktion=override
name=override
geb=override
vorname=override
geb=override
p_strasse=override
p_plz=override
p_ort=override
p_lkz=override
bphone1=override
bphone2=override
mobile=override
carphone=override
otherfax=override
bfax=override
companyphone=override
web=override
firma=override
strasse=override
ort=override
plz=override
email1=override
email2=override
email3=override
info=override', null, 'ok');



-- 4. no luid provided, also not new/to create to remains unchanged
insert into syncabonnement (SYNCABONNEMENTID, PRINCIPAL, DBNAME, GUID, LUID, ABOSTART, ABOENDE, CHANGED, SYNCED, TO_EXTERNAL, FROM_EXTERNAL, SYNCRESULT)
values (10, 10,  'person', '4285', null, to_date('14-07-2013 17:43:35', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-02-2021 16:50:04', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-02-2021 16:50:04', 'dd-mm-yyyy hh24:mi:ss'), to_date('25-02-2021 16:50:04', 'dd-mm-yyyy hh24:mi:ss'), 'keymapversion=6
anrede=
funktion=KD-Systemtechniker
name=Salomon
geb=
vorname=Frank
geb=
p_strasse=
p_plz=
p_ort=
p_lkz=
bphone1=+49 6142 20800-82
bphone2=
mobile=+49 151 16825 452
carphone=
otherfax=
bfax=+49 6142 20800-33
companyphone=+49 6142 20800-0
web=www.kieback-peter.de
firma=Kieback&Peter GmbH & Co. KG Niederlassung Rhein-Main
strasse=Eisenstraße 9b
ort=Rüsselsheim
plz=65428
email1=
email2=salomon@kieback-peter.de
email3=nl-rheinmain@kieback-peter.de
info=', null, 'ok');

