INSERT INTO
    PERSON (
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
    CLIENT (
        id,
        password,
        status,
        updated_timestamp
    )
VALUES
    (
        (Select p.id from PERSON p where p.identification = '0145268973'),
        'LKAJSLKDJJSJJSJS***233343',
        true,
        CURRENT_TIMESTAMP
    );