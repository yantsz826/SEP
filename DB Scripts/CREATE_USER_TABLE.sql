CREATE TABLE FINANCE_WEB_USERS (
    VENDOR_NUMBER		VARCHAR(30) PRIMARY KEY,
    USER_PASSWORD		VARCHAR(50) NOT NULL,
    EMAIL_ADDRESS		VARCHAR(100) NOT NULL,
    ADMIN_USER			BIT DEFAULT 0,
	OUT_DATE			DATETIME
);