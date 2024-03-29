CREATE SEQUENCE COMMON_SEQUENCE;
SELECT SETVAL('COMMON_SEQUENCE', (EXTRACT(EPOCH FROM NOW()) * 1000)::BIGINT);

CREATE TABLE USERS
(
    ID         BIGINT PRIMARY KEY   DEFAULT NEXTVAL('COMMON_SEQUENCE'),
    FIRST_NAME VARCHAR(64) NOT NULL,
    LAST_NAME  VARCHAR(64) NOT NULL,
    DOB        DATE,
    LOCATION   VARCHAR(128),
    VERSION    INTEGER     NOT NULL DEFAULT 0
);

CREATE TABLE CURRENCIES
(
    ID              BIGINT PRIMARY KEY  DEFAULT NEXTVAL('COMMON_SEQUENCE'),
    CURRENCY_CODE   VARCHAR(3) NOT NULL,
    NUMERIC_CODE    SMALLINT   NOT NULL,
    FRACTION_DIGITS SMALLINT   NOT NULL,
    VERSION         INTEGER    NOT NULL DEFAULT 0
);

CREATE TABLE WALLETS
(
    ID          BIGINT PRIMARY KEY   DEFAULT NEXTVAL('COMMON_SEQUENCE'),
    NAME        VARCHAR(64) NOT NULL,
    CURRENCY_ID BIGINT      NOT NULL,
    USER_ID     BIGINT      NOT NULL,
    VERSION     INTEGER     NOT NULL DEFAULT 0
);
ALTER TABLE WALLETS
    ADD CONSTRAINT FK_CURRENCY_ID FOREIGN KEY (CURRENCY_ID) REFERENCES CURRENCIES (ID);
ALTER TABLE WALLETS
    ADD CONSTRAINT FK_USER_ID FOREIGN KEY (USER_ID) REFERENCES USERS (ID);

CREATE TABLE TRANSACTIONS
(
    ID       UUID PRIMARY KEY,
    AMOUNT   NUMERIC NOT NULL,
    FROM_ID  BIGINT,
    TO_ID    BIGINT  NOT NULL,
    DATETIME TIMESTAMP WITHOUT TIME ZONE,
    VERSION  INTEGER NOT NULL DEFAULT 0
);
ALTER TABLE TRANSACTIONS
    ADD CONSTRAINT FK_FROM_ID FOREIGN KEY (FROM_ID) REFERENCES WALLETS (ID);
ALTER TABLE TRANSACTIONS
    ADD CONSTRAINT FK_TO_ID FOREIGN KEY (TO_ID) REFERENCES WALLETS (ID);