CREATE SEQUENCE APP_USER_SEQ START 1;
CREATE SEQUENCE USER_PROFILE_SEQ START 1;

/*All User's gets stored in APP_USER table*/
create table APP_USER (
   id BIGINT NOT NULL DEFAULT nextval('APP_USER_SEQ'),
   sso_id VARCHAR(30) NOT NULL,
   password VARCHAR(100) NOT NULL,
   first_name VARCHAR(30) NOT NULL,
   last_name  VARCHAR(30) NOT NULL,
   email VARCHAR(100) NOT NULL,
   authorized_transaction_limit NUMERIC,
   reporting_to BIGINT,
   is_root boolean NOT NULL DEFAULT FALSE,
   is_active boolean NOT NULL DEFAULT TRUE,
   PRIMARY KEY (id),
   UNIQUE (sso_id)
);
  
/* USER_PROFILE table contains all possible roles */ 
create table USER_PROFILE(
   id BIGINT NOT NULL DEFAULT nextval('USER_PROFILE_SEQ'),
   type VARCHAR(30) NOT NULL,
   PRIMARY KEY (id),
   UNIQUE (type)
);
  
/* JOIN TABLE for MANY-TO-MANY relationship*/  
CREATE TABLE APP_USER_USER_PROFILE (
    user_id BIGINT NOT NULL,
    user_profile_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, user_profile_id),
    CONSTRAINT FK_APP_USER FOREIGN KEY (user_id) REFERENCES APP_USER (id),
    CONSTRAINT FK_USER_PROFILE FOREIGN KEY (user_profile_id) REFERENCES USER_PROFILE (id)
);


CREATE SEQUENCE PURCHASE_REQUISITION_SEQ START 1;
CREATE SEQUENCE PURCHASE_REQUISITION_ITEMS_SEQ START 1;

CREATE TABLE PURCHASE_REQUISITION (
   PR_NO VARCHAR(30) NOT NULL,
   PROJECT_NAME VARCHAR(50) NOT NULL,
   PROJECT_CODE VARCHAR(50),
   REV VARCHAR(50),
   STATUS VARCHAR(30) NOT NULL,
   CREATED_DATE TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   CREATED_BY BIGINT NOT NULL,
   CREATED_BY_NAME VARCHAR(65) NOT NULL,
   ASSIGNED_DATE TIMESTAMP WITHOUT TIME ZONE,
   ASSIGNED_TO BIGINT,
   ASSIGNED_TO_NAME VARCHAR(65),
   AUTHORIZED_DATE TIMESTAMP WITHOUT TIME ZONE,
   AUTHORIZED_BY BIGINT,
   AUTHORIZED_BY_NAME VARCHAR(65),
   APPROVED_DATE TIMESTAMP WITHOUT TIME ZONE,
   APPROVED_BY BIGINT,
   APPROVED_BY_NAME VARCHAR(65),
   ACKNOWLEDGED_DATE TIMESTAMP WITHOUT TIME ZONE,
   ACKNOWLEDGED_BY BIGINT,
   ACKNOWLEDGED_BY_NAME VARCHAR(65),
   LAST_UPDATED_DATE TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   LAST_UPDATED_BY BIGINT NOT NULL,
   LAST_UPDATED_BY_NAME VARCHAR(65) NOT NULL,
   PRIMARY KEY (PR_NO),
   CONSTRAINT FK_PR_CREATED_BY FOREIGN KEY (CREATED_BY) REFERENCES APP_USER (id),
   CONSTRAINT FK_PR_AUTHORIZED_BY FOREIGN KEY (AUTHORIZED_BY) REFERENCES APP_USER (id),
   CONSTRAINT FK_PR_APPROVED_BY FOREIGN KEY (APPROVED_BY) REFERENCES APP_USER (id),
   CONSTRAINT FK_PR_LAST_UPDATED_BY FOREIGN KEY (LAST_UPDATED_BY) REFERENCES APP_USER (id)
);

CREATE TABLE PURCHASE_REQUISITION_ITEMS (
   ID BIGINT NOT NULL DEFAULT nextval('PURCHASE_REQUISITION_ITEMS_SEQ'),
   PR_NO VARCHAR(30) NOT NULL,
   DESCRIPTION VARCHAR(200) NOT NULL,
   TOTAL_QUANTITY_REQUIRED INTEGER NOT NULL,
   QUANTITY_IN_STOCK INTEGER NOT NULL,
   QUANTITY_TO_BE_PURCHASED INTEGER NOT NULL,
   UOM VARCHAR(30),
   UNIT_COST NUMERIC,
   APPROX_TOTAL_COST NUMERIC,
   MAKE VARCHAR(40),
   CAT_NO VARCHAR(30),
   REQUIRED_BY_DATE TIMESTAMP WITHOUT TIME ZONE,
   PREFERRED_SUPPLIER VARCHAR(50),
   PRIMARY KEY (ID),
   CONSTRAINT FK_PR_NO FOREIGN KEY (PR_NO) REFERENCES PURCHASE_REQUISITION (PR_NO)
);


-- Dropped Columns QUANTITY_IN_STOCK, QUANTITY_TO_BE_PURCHASED, UNIT_COST, APPROX_TOTAL_COST, PREFERRED_SUPPLIER
ALTER TABLE PURCHASE_REQUISITION_ITEMS
DROP COLUMN QUANTITY_IN_STOCK, DROP COLUMN QUANTITY_TO_BE_PURCHASED, 
DROP COLUMN UNIT_COST, DROP COLUMN APPROX_TOTAL_COST, DROP COLUMN PREFERRED_SUPPLIER;

-- Renamed column TOTAL_QUANTITY_REQUIRED to QUANTITY_REQUIRED
ALTER TABLE PURCHASE_REQUISITION_ITEMS
RENAME COLUMN TOTAL_QUANTITY_REQUIRED TO QUANTITY_REQUIRED;

-- Added columns DELIVERY_DATE, ORDERED_QUANTITY, DEVIATION, REMARK
ALTER TABLE PURCHASE_REQUISITION_ITEMS
ADD COLUMN DELIVERY_DATE TIMESTAMP WITHOUT TIME ZONE, ADD COLUMN ORDERED_QUANTITY INTEGER, 
ADD COLUMN DEVIATION VARCHAR(100), ADD COLUMN REMARK VARCHAR(200);

-- Added column REMARK
ALTER TABLE PURCHASE_REQUISITION
ADD COLUMN REMARK VARCHAR(200);


-- Added columns ITEM_CODE, DIAMENTIONS, SPECIFICATION
ALTER TABLE PURCHASE_REQUISITION_ITEMS
ADD COLUMN ITEM_CODE VARCHAR(50), ADD COLUMN DIAMENSIONS VARCHAR(100);


-- Renamed column TOTAL_QUANTITY_REQUIRED to QUANTITY_REQUIRED
ALTER TABLE PURCHASE_REQUISITION_ITEMS
RENAME COLUMN CAT_NO TO SPECIFICATIONS;
ALTER TABLE PURCHASE_REQUISITION_ITEMS ALTER COLUMN SPECIFICATIONS TYPE varchar(100);

-- Added columns INVOICE_NO and INVOICE_DATE
ALTER TABLE PURCHASE_REQUISITION_ITEMS 
ADD COLUMN INVOICE_NO VARCHAR(50), ADD COLUMN INVOICE_DATE TIMESTAMP WITHOUT TIME ZONE;

-- Added column EMAIL_NOTIFICATION
ALTER TABLE APP_USER ADD COLUMN EMAIL_NOTIFICATION BOOLEAN DEFAULT TRUE;

