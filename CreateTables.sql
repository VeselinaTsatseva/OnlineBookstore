CREATE DATABASE Bookstore;

CREATE TABLE Books (
	isbn INT PRIMARY KEY NOT NULL,
    title VARCHAR(50) NOT NULL,
    author VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(5, 2) NOT NULL
);

CREATE TABLE Users (
	userID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    pwd VARCHAR(50) NOT NULL,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone VARCHAR(15) NOT NULL
);

ALTER TABLE Users
  ADD CONSTRAINT UNIQUE (username),
  ADD CONSTRAINT UNIQUE (email);

CREATE TABLE Orders (
	orderID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    date_created Date NOT NULL,
    total DECIMAL(5, 2) NOT NULL,
    userID INT NOT NULL,
    FOREIGN KEY(userID) REFERENCES Users(userID)
);

CREATE TABLE OrderedItem (
	orderedItemID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    orderID INT NOT NULL,
    isbn INT NOT NULL,
    price DECIMAL(5, 2) NOT NULL,
    FOREIGN KEY(isbn) REFERENCES Books(isbn),
    FOREIGN KEY(orderID) REFERENCES Orders(orderID)
);

ALTER TABLE OrderedItem ADD COLUMN isRented VARCHAR(10);


ALTER TABLE Orders DROP FOREIGN KEY orders_ibfk_1;

ALTER TABLE Orders 
ADD CONSTRAINT 
FOREIGN KEY(userID) REFERENCES Users(userID)
ON DELETE CASCADE; 

ALTER TABLE OrderedItem DROP FOREIGN KEY ordereditem_ibfk_2;

ALTER TABLE OrderedItem 
ADD CONSTRAINT 
FOREIGN KEY(orderID) REFERENCES Orders(orderID)
ON DELETE CASCADE; 