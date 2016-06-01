CREATE TABLE Finance_Web_Users (
    vendorNo		VARCHAR(30) PRIMARY KEY,
    userPassword	VARCHAR(20) NOT NULL,
    emailAddr		VARCHAR(100) NOT NULL,
	outDate			DATE,
    adminUser		NUMBER DEFAULT 0
);