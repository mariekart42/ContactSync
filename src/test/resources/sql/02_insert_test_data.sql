INSERT INTO SYNCUSER (syncuserid, username, aditouser) VALUES
(1, 'DOMAIN\\jschmidt', 'jschmidt'),
(2, 'DOMAIN\\mhoffmann', 'mhoffmann');

INSERT INTO SYNCDEVICE (syncdeviceid, device, description, tzid, devicespecifics, avoidfields) VALUES
(1, 'jschmidt@example.com/AditoKontakte', 'Exchange Online', 'Europe/Berlin', 'fileAsMapping=LastCommaFirstCompany', '*privat*bfax*email3*'),
(2, 'mhoffmann@example.com/CRMContacts', 'Exchange Online', 'Europe/Berlin', 'fileAsMapping=FirstLast', '*privat*');

INSERT INTO SYNCPRINCIPAL (syncprincipalid, syncuser_id, syncdevice_id, syncresult) VALUES
(1, 1, 1, 'ok'),
(2, 2, 2, 'Fehler beim Syncen von Kontakt 1234: Feld email2 leer');

INSERT INTO SYNCABONNEMENT (syncabonnementid, principal, dbname, guid, luid, abostart, aboende, changed, synced, to_external, from_external, syncresult) VALUES
(1, 1, 'person', 'person-uuid-001', 'graph-id-abc001', DATE '2024-01-10', NULL, DATE '2024-01-15', DATE '2024-01-16', '{"email":"kontakt1@example.com"}', NULL, 'ok');
