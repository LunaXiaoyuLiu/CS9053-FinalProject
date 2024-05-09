CREATE TABLE Users (
    Username VARCHAR(50) NOT NULL,
    `Password` VARCHAR(50) NOT NULL,
		PRIMARY KEY (Username)
);

CREATE Table NetIncome (
	Username VARCHAR(50) NOT NULL,
	NetIncome DECIMAL(10, 2) NOT NULL,
	`Time` TIMESTAMP NOT NULL,
	Notes VARCHAR(250) NULL,
	PRIMARY KEY (Username, `Time`),
	FOREIGN KEY (Username) REFERENCES Users(Username)
)

CREATE Table Investments (
	username VARCHAR(50) NOT NULL,
	investmentAmount DECIMAL(10, 2) NOT NULL,
	expectedYield DECIMAL(10, 2) NOT NULL,
	matureDate DATE NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	investTime TIMESTAMP NOT NULL,
	PRIMARY KEY (Username, investTime),
	FOREIGN KEY (Username) REFERENCES Users(Username)
)

CREATE TABLE Savings (
	username VARCHAR(50) NOT NULL,
	savingAmount DECIMAL(10, 2) NOT NULL,
	savingTime TIMESTAMP NOT NULL,
	PRIMARY KEY (Username, savingTime),
	FOREIGN KEY (Username) REFERENCES Users(Username)
)