CREATE TABLE
    PERSON (
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

CREATE TABLE
    CLIENT (
        id BIGINT NOT NULL,
        password VARCHAR(256) NOT NULL,
        status Boolean NOT NULL,
        updated_timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
        CONSTRAINT pk_client PRIMARY KEY (id)
    );

ALTER TABLE CLIENT ADD CONSTRAINT CLIENT_PERSON_FK FOREIGN KEY (id) REFERENCES PERSON (id) ON DELETE RESTRICT ON UPDATE RESTRICT;
