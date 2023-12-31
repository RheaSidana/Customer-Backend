CREATE TABLE Customers(
    ID BIGINT GENERATED BY DEFAULT AS IDENTITY,
    FIRST_NAME VARCHAR(25) NOT NULL,
    LAST_NAME VARCHAR(25) NOT NULL,
    ADDRESS BIGINT,
    CONTACT BIGINT,
    PRIMARY KEY (ID),
    FOREIGN KEY(ADDRESS) REFERENCES ADDRESS(ID),
    FOREIGN KEY(CONTACT) REFERENCES CONTACTS(ID)
);