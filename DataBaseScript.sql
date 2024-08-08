CREATE SCHEMA demoBank AUTHORIZATION postgres;

COMMENT ON SCHEMA demoBank IS 'demoBank Schema';

-- Person
CREATE TABLE
    demoBank.PERSON (
        id BIGINT GENERATED ALWAYS AS IDENTITY,
        name VARCHAR(128) NOT NULL,
        lastname VARCHAR(128) NOT NULL,
        identification VARCHAR(13) NOT NULL UNIQUE,
        gender VARCHAR(1) NOT NULL,
        age INTEGER NOT NULL,
        address VARCHAR(128) NULL,
        phone VARCHAR(32) NULL,
        CONSTRAINT pk_person PRIMARY KEY (id)
    );

COMMENT ON TABLE demoBank.PERSON IS 'Person table';

COMMENT ON COLUMN demoBank.PERSON.id IS 'Person id';

COMMENT ON COLUMN demoBank.PERSON.name IS 'Person Name';

COMMENT ON COLUMN demoBank.PERSON.lastname IS 'Person LastName';

COMMENT ON COLUMN demoBank.PERSON.identification IS 'Person identification';

COMMENT ON COLUMN demoBank.PERSON.gender IS 'Gender';

COMMENT ON COLUMN demoBank.PERSON.age IS 'Age';

COMMENT ON COLUMN demoBank.PERSON.address IS 'Address';

COMMENT ON COLUMN demoBank.PERSON.phone IS 'Phone';

COMMENT ON CONSTRAINT pk_person ON demoBank.PERSON IS 'Person PK';

--Client
CREATE TABLE
    demoBank.CLIENT (
        id BIGINT NOT NULL,
        password VARCHAR(256) NOT NULL,
        status Boolean NOT NULL,
        updated_timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
        CONSTRAINT pk_client PRIMARY KEY (id)
    );

COMMENT ON TABLE demoBank.CLIENT IS 'Client table';

COMMENT ON COLUMN demoBank.CLIENT.id IS 'Client and Person id';

COMMENT ON COLUMN demoBank.CLIENT.password IS 'Password encrypted';

COMMENT ON COLUMN demoBank.CLIENT.status IS 'Client status';

COMMENT ON COLUMN demoBank.CLIENT.updated_timestamp IS 'Last modified timestamp';

COMMENT ON CONSTRAINT pk_client ON demoBank.CLIENT IS 'Client PK';

--Account
CREATE TABLE
    demoBank.ACCOUNT (
        id BIGINT GENERATED ALWAYS AS IDENTITY,
        account_number VARCHAR(16) NOT NULL UNIQUE,
        client_id BIGINT NOT NULL,
        type VARCHAR(32) NOT NULL,
        balance NUMERIC(12,2) NOT NULL,
        status Boolean NOT NULL,
        updated_timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
        CONSTRAINT pk_account PRIMARY KEY (id)
    );

COMMENT ON TABLE demoBank.ACCOUNT IS 'Account table';

COMMENT ON COLUMN demoBank.ACCOUNT.id IS 'Account id';

COMMENT ON COLUMN demoBank.ACCOUNT.account_number IS 'Account number';

COMMENT ON COLUMN demoBank.ACCOUNT.client_id IS 'Client id FK';

COMMENT ON COLUMN demoBank.ACCOUNT.type IS 'Type of account - Ahorros o Corriente';

COMMENT ON COLUMN demoBank.ACCOUNT.balance IS 'Balance initial of account';

COMMENT ON COLUMN demoBank.ACCOUNT.status IS 'Record status';

COMMENT ON COLUMN demoBank.ACCOUNT.updated_timestamp IS 'Last modified timestamp';

COMMENT ON CONSTRAINT pk_account ON demoBank.ACCOUNT IS 'Account PK';

--Transactions
CREATE TABLE
    demoBank.TRANSACTION (
        id BIGINT GENERATED ALWAYS AS IDENTITY,
        account_id BIGINT NOT NULL,
        date TIMESTAMP WITH TIME ZONE NOT NULL,
        amount NUMERIC(12,2) NOT NULL,
        previous_balance NUMERIC(12,2) NOT NULL,
        final_balance NUMERIC(12,2) NOT NULL,
        type VARCHAR(32) NOT NULL,  
        CONSTRAINT pk_transactions PRIMARY KEY (id)
    );

COMMENT ON TABLE demoBank.TRANSACTION IS 'Transactions table';

COMMENT ON COLUMN demoBank.TRANSACTION.id IS 'Transactions id';

COMMENT ON COLUMN demoBank.TRANSACTION.account_id IS 'Account id';

COMMENT ON COLUMN demoBank.TRANSACTION.date IS 'Transaction timestamp';

COMMENT ON COLUMN demoBank.TRANSACTION.amount IS 'Transaction amount';

COMMENT ON COLUMN demoBank.TRANSACTION.previous_balance IS 'Previous Balance for this transaction';

COMMENT ON COLUMN demoBank.TRANSACTION.final_balance IS 'Final Balance for this transaction';

COMMENT ON COLUMN demoBank.TRANSACTION.type IS 'Transaction type DEBIT/CREDIT';

COMMENT ON CONSTRAINT pk_transactions ON demoBank.TRANSACTION IS 'Transaction PK';

--VIEW (REPORT)
CREATE VIEW demobank.MOVREPORT AS
    SELECT t.date as "datetime", a.client_id as "client", a.id, a.account_number as account, t.type as "typeT", t.previous_balance as previousB, t.amount, t.final_balance as "finalB"
        FROM demoBank.TRANSACTION t
        JOIN demoBank.ACCOUNT a on a.id = t.account_id;

--FOREIGN KEYS
ALTER TABLE demoBank.CLIENT ADD CONSTRAINT CLIENT_PERSON_FK FOREIGN KEY (id) REFERENCES demoBank.PERSON (id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE demoBank.ACCOUNT ADD CONSTRAINT ACCOUNT_CLIENT_FK FOREIGN KEY (client_id) REFERENCES demoBank.CLIENT (id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE demoBank.TRANSACTION ADD CONSTRAINT TRANSACTION_ACCOUNT_FK FOREIGN KEY (account_id) REFERENCES demoBank.ACCOUNT (id) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- DML ---

INSERT INTO
    demoBank.PERSON (
        name,
        lastname,
        identification,
        gender,
        age,
        address,
        phone
    )
VALUES
    (
        'JOSE',
        'LEMA',
        '0145268973',
        'M',
        34,
        'Otavalo sn y principal',
        '098254785'
    );

INSERT INTO
    demoBank.CLIENT (
        id,
        password,
        status,
        updated_timestamp
    )
VALUES
    (
        (Select p.id from demoBank.PERSON p where p.identification = '0145268973'),
        'LKAJSLKDJJSJJSJS***233343',
        true,
        CURRENT_TIMESTAMP
    );


