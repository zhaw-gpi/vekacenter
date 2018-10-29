INSERT INTO ADDRESS (HOUSE_NUMBER, PLZ, STREET, TOWN) VALUES ('13a', 3226, 'Dorfstrasse', 'Kallnach');
INSERT INTO ADDRESS (HOUSE_NUMBER, PLZ, STREET, TOWN) VALUES ('1', 3266, 'Bahnhofstrasse', 'Seedorf');
INSERT INTO ADDRESS (HOUSE_NUMBER, PLZ, STREET, TOWN) VALUES ('3', 3001, 'Laupenstrasse', 'Bern');
INSERT INTO ADDRESS (HOUSE_NUMBER, PLZ, STREET, TOWN) VALUES ('1', 3007, 'Gartenstrasse ', 'Bern');

INSERT INTO PERSON (DATE_OF_BIRTH, FIRST_NAME, OFFICIAL_NAME, ADDRESS_POSTAL_ID) VALUES(DATE '1970-01-01','Hans', 'Meier', 1);
INSERT INTO PERSON (DATE_OF_BIRTH, FIRST_NAME, OFFICIAL_NAME, ADDRESS_POSTAL_ID) VALUES(DATE '1982-01-01','Anna', 'Meier', 1);
INSERT INTO PERSON (DATE_OF_BIRTH, FIRST_NAME, OFFICIAL_NAME, ADDRESS_POSTAL_ID) VALUES(DATE '2011-01-01','Anneliesi', 'Meier', 1);

INSERT INTO INSURER (ID, NAME, ADDRESS_ID, PHONE_NUMBER, E_MAIL, WEBSITE_URL) VALUES (1, 'Visana Services Bern', 3, '+41 313 57 91 11', 'info@visana.ch', 'www.visana.ch');
INSERT INTO INSURER (ID, NAME, ADDRESS_ID, PHONE_NUMBER, E_MAIL, WEBSITE_URL) VALUES (2, 'Atupri Bern', 4, '+41 800 200 888', 'info@atupri.ch', 'www.atupri.ch');

INSERT INTO CARD (CARD_NUMBER, EXPIRY_DATE, PERSON_ID, INSURER_ID, BASE_INSURED) VALUES (36026946698526862, DATE '2016-03-31', 1, 1, TRUE);
INSERT INTO CARD (CARD_NUMBER, EXPIRY_DATE, PERSON_ID, INSURER_ID, BASE_INSURED) VALUES (743794085316616163, DATE '2020-03-31', 1, 1, TRUE);
INSERT INTO CARD (CARD_NUMBER, EXPIRY_DATE, PERSON_ID, INSURER_ID, BASE_INSURED) VALUES (78847589268403592, DATE '2021-06-15', 2, 1, FALSE);
INSERT INTO CARD (CARD_NUMBER, EXPIRY_DATE, PERSON_ID, INSURER_ID, BASE_INSURED) VALUES (596947027238113990, DATE '2021-06-15', 3, 2, TRUE);