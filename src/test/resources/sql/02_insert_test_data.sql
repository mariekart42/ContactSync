


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
values (1, 'roemer@kieback-peter.de/AditoKontakte', 'Exchange', 'Europe/Berlin', 'fileAsMapping=LastCommaFirst', null);

insert into syncdevice (SYNCDEVICEID, DEVICE, DESCRIPTION, TZID, DEVICESPECIFICS, AVOIDFIELDS)
values (2, 'kolb-kl@kieback-peter.de/', 'Exchange', 'Europe/Berlin', 'fileAsMapping=LastCommaFirst', null);

insert into syncdevice (SYNCDEVICEID, DEVICE, DESCRIPTION, TZID, DEVICESPECIFICS, AVOIDFIELDS)
values (3, 'boehm@kieback-peter.de/AditoKontakte', 'Exchange', 'Europe/Berlin', 'fileAsMapping=LastCommaFirst', '*');

insert into syncdevice (SYNCDEVICEID, DEVICE, DESCRIPTION, TZID, DEVICESPECIFICS, AVOIDFIELDS)
values (4, 'walbroehl@kieback-peter.de/AditoKontakte', 'Exchange', 'Europe/Berlin', 'fileAsMapping=LastFirstCompany', '*privat*otherfax*bfax*info*');

insert into syncdevice (SYNCDEVICEID, DEVICE, DESCRIPTION, TZID, DEVICESPECIFICS, AVOIDFIELDS)
values (5, 'peetz@kieback-peter.de/AditoKontakte', 'Exchange', 'Europe/Berlin', 'fileAsMapping=LastCommaFirst', null);

insert into syncdevice (SYNCDEVICEID, DEVICE, DESCRIPTION, TZID, DEVICESPECIFICS, AVOIDFIELDS)
values (6, 'janoschek@kieback-peter.de/AditoKontakte', 'Exchange', 'Europe/Berlin', 'fileAsMapping=LastCommaFirst', null);

insert into syncdevice (SYNCDEVICEID, DEVICE, DESCRIPTION, TZID, DEVICESPECIFICS, AVOIDFIELDS)
values (7, 'wassermann@kieback-peter.de/', 'Exchange', 'Europe/Berlin', 'fileAsMapping=LastCommaFirst', null);

insert into syncdevice (SYNCDEVICEID, DEVICE, DESCRIPTION, TZID, DEVICESPECIFICS, AVOIDFIELDS)
values (8, 'fuehrer@kieback-peter.de/AditoKontakte', 'Exchange', 'Europe/Berlin', 'fileAsMapping=LastCommaFirst', null);

insert into syncdevice (SYNCDEVICEID, DEVICE, DESCRIPTION, TZID, DEVICESPECIFICS, AVOIDFIELDS)
values (9, 'drechsel@kieback-peter.de/AditoKontakte', 'Exchange', 'Europe/Berlin', 'fileAsMapping=LastCommaFirst', '*anrede*funktion*privat*bphone2*bfax*info*');

insert into syncdevice (SYNCDEVICEID, DEVICE, DESCRIPTION, TZID, DEVICESPECIFICS, AVOIDFIELDS)
values (10, 'motschmann@kieback-peter.de/AditoKontakte', 'Exchange', 'Europe/Berlin', 'fileAsMapping=LastCommaFirst', null);





insert into syncprincipal (SYNCPRINCIPALID, SYNCUSER_ID, SYNCDEVICE_ID, SYNCRESULT)
values (1, 1, 1, 'javax.xml.ws.soap.SOAPFaultException: javax.net.ssl.SSLHandshakeException: SSLHandshakeException invoking https://login.microsoftonline.com/061e1a99-02ec-46c0-a39f-fde61ea97bbf/oauth2/v2.0/token: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target');

insert into syncprincipal (SYNCPRINCIPALID, SYNCUSER_ID, SYNCDEVICE_ID, SYNCRESULT)
values (2, 2, 2, 'ok');

insert into syncprincipal (SYNCPRINCIPALID, SYNCUSER_ID, SYNCDEVICE_ID, SYNCRESULT)
values (3, 3, 3, 'ok');

insert into syncprincipal (SYNCPRINCIPALID, SYNCUSER_ID, SYNCDEVICE_ID, SYNCRESULT)
values (4, 4, 4, 'ok');

insert into syncprincipal (SYNCPRINCIPALID, SYNCUSER_ID, SYNCDEVICE_ID, SYNCRESULT)
values (5, 5, 5, 'ok');

insert into syncprincipal (SYNCPRINCIPALID, SYNCUSER_ID, SYNCDEVICE_ID, SYNCRESULT)
values (6, 6, 6, 'ok');

insert into syncprincipal (SYNCPRINCIPALID, SYNCUSER_ID, SYNCDEVICE_ID, SYNCRESULT)
values (7, 7, 7, 'ok');

insert into syncprincipal (SYNCPRINCIPALID, SYNCUSER_ID, SYNCDEVICE_ID, SYNCRESULT)
values (8, 8, 8, 'javax.xml.ws.soap.SOAPFaultException: javax.net.ssl.SSLHandshakeException: SSLHandshakeException invoking https://login.microsoftonline.com/061e1a99-02ec-46c0-a39f-fde61ea97bbf/oauth2/v2.0/token: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target');

insert into syncprincipal (SYNCPRINCIPALID, SYNCUSER_ID, SYNCDEVICE_ID, SYNCRESULT)
values (9, 9, 9, 'ok');

insert into syncprincipal (SYNCPRINCIPALID, SYNCUSER_ID, SYNCDEVICE_ID, SYNCRESULT)
values (10, 10, 10, 'ok');








insert into syncabonnement (SYNCABONNEMENTID, PRINCIPAL, DBNAME, GUID, LUID, ABOSTART, ABOENDE, CHANGED, SYNCED, TO_EXTERNAL, FROM_EXTERNAL, SYNCRESULT)
values (1, 1,  'person', '119208', null, to_date('14-05-2013 14:17:25', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-03-2025 12:50:15', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-03-2025 12:50:15', 'dd-mm-yyyy hh24:mi:ss'), to_date('11-03-2025 12:50:15', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'ok');

insert into syncabonnement (SYNCABONNEMENTID, PRINCIPAL, DBNAME, GUID, LUID, ABOSTART, ABOENDE, CHANGED, SYNCED, TO_EXTERNAL, FROM_EXTERNAL, SYNCRESULT)
values (2, 2,  'person', '5500', 'AAMkAGU5OTUxYjhjLTJjNTEtNDIyZS04YTY4LTI1NmRkZGNkNGI0ZQBGAAAAAAAMd7RX/j7VTaLSKcqnvtUFBwBiZ0UqFr+QRpvm07mYVeIfABPX5hJdAABiZ0UqFr+QRpvm07mYVeIfABPX50QuAAA=', to_date('13-12-2010 12:00:05', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('09-08-2023 17:15:29', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-08-2023 17:15:29', 'dd-mm-yyyy hh24:mi:ss'), 'keymapversion=6
anrede=
funktion=Inhaber/in
name=Lüthen
geb=20150924
vorname=Wilfried
geb=20150924
p_strasse=
p_plz=
p_ort=
p_lkz=
bphone1=
bphone2=+49 3834 817444
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

insert into syncabonnement (SYNCABONNEMENTID, PRINCIPAL, DBNAME, GUID, LUID, ABOSTART, ABOENDE, CHANGED, SYNCED, TO_EXTERNAL, FROM_EXTERNAL, SYNCRESULT)
values (3, 3,  'person', '119425', 'AAMkADNlNWY2NGExLWNlNDktNDE4NS1hOTVlLTQyODQwNmMxOTU1YwBGAAAAAACnJReMRhWiRq9onOKs7XIjBwAZ30egabutS5wuk6BFuRl9ACv31TAPAACioPAGydWQSIhht/CGnkaaAAAAAODvAAA=', to_date('21-05-2013 12:30:19', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('27-02-2023 14:40:28', 'dd-mm-yyyy hh24:mi:ss'), to_date('27-02-2023 14:40:28', 'dd-mm-yyyy hh24:mi:ss'), 'keymapversion=6
anrede=
funktion=vom VI noch anzugeben
name=Wohlfahrt
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

insert into syncabonnement (SYNCABONNEMENTID, PRINCIPAL, DBNAME, GUID, LUID, ABOSTART, ABOENDE, CHANGED, SYNCED, TO_EXTERNAL, FROM_EXTERNAL, SYNCRESULT)
values (4, 4,  'person', '236114', null, to_date('14-02-2020 13:32:56', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-08-2023 17:50:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-08-2023 17:50:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-08-2023 17:50:41', 'dd-mm-yyyy hh24:mi:ss'), 'keymapversion=6
anrede=
funktion=Einkauf Sachbearbeiter (in)
name=Wurster
geb=
vorname=Christian
geb=
p_strasse=
p_plz=
p_ort=
p_lkz=
bphone1=
bphone2=
mobile=+49 172 8245266
carphone=
otherfax=
bfax=
companyphone=+49 711 4009 - 8000
web=www.seg-automotive.com
firma=SEG Automotive Germany GmbH
strasse=Lotterbergstraße 30
ort=Stuttgart
plz=70499
email1=christian.wurster@seg-automotive.com
email2=
email3=contact@seg-automotive.com
info=Purchasing Indirect Material, Services, Machinery and Equipment', null, 'ok');

insert into syncabonnement (SYNCABONNEMENTID, PRINCIPAL, DBNAME, GUID, LUID, ABOSTART, ABOENDE, CHANGED, SYNCED, TO_EXTERNAL, FROM_EXTERNAL, SYNCRESULT)
values (5, 5,  'person', '119699', null, to_date('27-05-2013 09:32:40', 'dd-mm-yyyy hh24:mi:ss'), to_date('20-02-2023 10:50:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('20-02-2023 10:50:34', 'dd-mm-yyyy hh24:mi:ss'), to_date('20-02-2023 10:50:34', 'dd-mm-yyyy hh24:mi:ss'), 'keymapversion=6
anrede=
funktion=
name=Nuß
geb=
vorname=
geb=
p_strasse=
p_plz=
p_ort=
p_lkz=
bphone1=
bphone2=
mobile=
carphone=
otherfax=
bfax=+497275 988910
companyphone=+497275 98890
web=www.butscher-haustechnik.de
firma=Butscher Heizungsbau
strasse=Marktstr. 11-13
ort=Kandel
plz=76870
email1=
email2=
email3=buero@butscher-haustechnik.de
info=', null, 'ok');

insert into syncabonnement (SYNCABONNEMENTID, PRINCIPAL, DBNAME, GUID, LUID, ABOSTART, ABOENDE, CHANGED, SYNCED, TO_EXTERNAL, FROM_EXTERNAL, SYNCRESULT)
values (6, 6,  'person', '120655', null, to_date('08-05-2014 18:33:15', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-08-2024 11:50:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-08-2024 11:50:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-08-2024 11:50:30', 'dd-mm-yyyy hh24:mi:ss'), 'keymapversion=6
anrede=
funktion=
name=Günzel
geb=
vorname=
geb=
p_strasse=
p_plz=
p_ort=
p_lkz=
bphone1=+49 9195 930170
bphone2=
mobile=+49 170 9304226
carphone=
otherfax=
bfax=
companyphone=
web=
firma=Norma Lebensmittelfilialbetrieb Stiftung & Co. KG Zentrallager
strasse=Lohmühlweg 13
ort=Röttenbach
plz=91341
email1=
email2=
email3=
info=', null, 'ok');

insert into syncabonnement (SYNCABONNEMENTID, PRINCIPAL, DBNAME, GUID, LUID, ABOSTART, ABOENDE, CHANGED, SYNCED, TO_EXTERNAL, FROM_EXTERNAL, SYNCRESULT)
values (7, 7,  'person', '104955', null, to_date('16-05-2017 14:28:41', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-01-2025 10:50:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-01-2025 10:50:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-01-2025 10:50:30', 'dd-mm-yyyy hh24:mi:ss'), 'keymapversion=6
anrede=
funktion=Service-Außendienst (in)
name=Rellmann
geb=
vorname=Jannis
geb=
p_strasse=
p_plz=
p_ort=
p_lkz=
bphone1=
bphone2=
mobile=+49 172 5299971
carphone=
otherfax=
bfax=+49 251 98720 33
companyphone=+49 251 98720 0
web=www.herber-petzel.de
firma=HERBER & PETZEL Gebäudetechnik GmbH & Co.KG
strasse=Gildenstraße 2a
ort=Münster
plz=48157
email1=jrellmann@herber-petzel.de
email2=
email3=info@herber-petzel.de
info=', null, 'ok');

insert into syncabonnement (SYNCABONNEMENTID, PRINCIPAL, DBNAME, GUID, LUID, ABOSTART, ABOENDE, CHANGED, SYNCED, TO_EXTERNAL, FROM_EXTERNAL, SYNCRESULT)
values (8, 8,  'person', '20952', null, to_date('09-12-2014 13:49:15', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-01-2025 10:50:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-01-2025 10:50:30', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-01-2025 10:50:30', 'dd-mm-yyyy hh24:mi:ss'), 'keymapversion=6
anrede=
funktion=Projektleiter (in)
name=Schulz
geb=
vorname=Christian
geb=
p_strasse=
p_plz=
p_ort=
p_lkz=
bphone1=+49 251 492 2372
bphone2=
mobile=+49 160 97224896
carphone=
otherfax=
bfax=+49 251 492 7734
companyphone=+49 251 492 0
web=www.muenster.de
firma=Stadt Münster Amt für Immobilienmanagement Energiemanagement
strasse=Albersloher Weg 33
ort=Münster
plz=48155
email1=schulzch@stadt-muenster.de
email2=
email3=Stadtverwaltung@stadt-muenster.de
info=erstellt die Ausschreibungen und macht evtl. bald die Job-Nachkalkulation tats. Kostenbetrachtung.', null, 'ok');

insert into syncabonnement (SYNCABONNEMENTID, PRINCIPAL, DBNAME, GUID, LUID, ABOSTART, ABOENDE, CHANGED, SYNCED, TO_EXTERNAL, FROM_EXTERNAL, SYNCRESULT)
values (9, 9,  'person', '151058', 'AAMkADg4NWM4MjMxLTc0ZjQtNGU4ZC05ZDc2LTgwMDJkNjAyNWE0YwBGAAAAAABb4L58lBuvQaM7Snxz45CZBwDKuT-HM9gZQIxqxJFFZgGEAAAAAAEOAADKuT-HM9gZQIxqxJFFZgGEAAAbPIQyAAA=', to_date('30-01-2023 10:37:17', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('09-08-2023 17:16:28', 'dd-mm-yyyy hh24:mi:ss'), to_date('09-08-2023 17:16:28', 'dd-mm-yyyy hh24:mi:ss'), 'keymapversion=6
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

