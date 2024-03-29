INSERT INTO CURRENCIES (CURRENCY_CODE, NUMERIC_CODE, FRACTION_DIGITS)
VALUES ('USD', '840', 2);
INSERT INTO CURRENCIES (CURRENCY_CODE, NUMERIC_CODE, FRACTION_DIGITS)
VALUES ('EUR', '978', 2);
INSERT INTO CURRENCIES (CURRENCY_CODE, NUMERIC_CODE, FRACTION_DIGITS)
VALUES ('UAH', '980', 2);
INSERT INTO CURRENCIES (CURRENCY_CODE, NUMERIC_CODE, FRACTION_DIGITS)
VALUES ('JPY', '392', 0);

INSERT INTO USERS (FIRST_NAME, LAST_NAME, DOB, LOCATION)
VALUES ('Jane', 'Doe', '1989-01-01', 'Austin, TX, United States');
INSERT INTO USERS (FIRST_NAME, LAST_NAME, DOB, LOCATION)
VALUES ('Jan', 'Modaal', '1990-02-02', 'Almere, Netherlands');
INSERT INTO USERS (FIRST_NAME, LAST_NAME, DOB, LOCATION)
VALUES ('Petro', 'Petrenko', '1991-03-03', 'Kyiv, Ukraine');
INSERT INTO USERS (FIRST_NAME, LAST_NAME, DOB, LOCATION)
VALUES ('Yamada', 'Hanako', '1992-04-04', 'Tokyo, Japan');

INSERT INTO WALLETS (NAME, CURRENCY_ID, USER_ID)
SELECT C.CURRENCY_CODE || ' wallet',
       C.ID,
       U.ID
FROM USERS U
         CROSS JOIN CURRENCIES C;

INSERT INTO TRANSACTIONS (ID, AMOUNT, FROM_ID, TO_ID, DATETIME)
SELECT UUID_GENERATE_V4(),
       ROUND(1000 + RANDOM() * 1000),
       NULL,
       W.ID,
       NOW()
FROM WALLETS W;