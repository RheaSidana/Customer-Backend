CREATE TABLE Address(
    ID BIGINT GENERATED BY DEFAULT AS IDENTITY,
    Address VARCHAR(100),
    Street VARCHAR(100),
    City VARCHAR(100),
    State VARCHAR(100),
    PRIMARY KEY (ID),
    CONSTRAINT unique_address UNIQUE (Address, Street, City, State)
);