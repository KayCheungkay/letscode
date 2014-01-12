--DROP 
DROP TABLE SUBSCRIBER ;

CREATE TABLE SUBSCRIBER 
   (           "BILLING_ID" VARCHAR2(5 BYTE) NOT NULL ENABLE, 
                "SUBSCRIBER_KEY" VARCHAR2(20 BYTE) NOT NULL ENABLE, 
                "SUBSCRIBER_NUM" VARCHAR2(20 BYTE) NOT NULL ENABLE, 
                "TECHNOLOGY_TYPE" VARCHAR2(10 BYTE) NOT NULL ENABLE, 
                "IMSI" VARCHAR2(15 BYTE), 
                "MIN" VARCHAR2(10 BYTE), 
                "MSISDN" VARCHAR2(20 BYTE), 
                "MDN" VARCHAR2(20 BYTE), 
                "SUBSCRIBER_NAME" VARCHAR2(80 BYTE), 
                "SUBSCRIBER_IDENTIFIER" VARCHAR2(80 BYTE), 
                "SUBSCRIBER_TYPE" VARCHAR2(30 BYTE), 
                "SUBSCRIBER_GROUP" VARCHAR2(30 BYTE), 
                "BILLING_ACCOUNT_ID" VARCHAR2(40 BYTE), 
                "AUTHORIZED_USER_ID" VARCHAR2(40 BYTE), 
                "NOTIFICATION_EMAIL" VARCHAR2(320 BYTE), 
                "NOTIFICATION_PHONE" VARCHAR2(40 BYTE), 
                "USAGE_VALUE" VARCHAR2(40 BYTE), 
                "INTERNAL_SCORING" NUMBER(3,0), 
                "FINANCIAL_STANDING" VARCHAR2(40 BYTE), 
                "PAYMENT_METHOD" VARCHAR2(40 BYTE), 
                "RATE_PLAN" VARCHAR2(40 BYTE), 
                "SERVICE_START_DATE" DATE, 
                "VOICE_SERVICE" CHAR(1 BYTE), 
                "DATA_SERVICE" CHAR(1 BYTE), 
                "SMS_SERVICE" CHAR(1 BYTE), 
                "HOME_SERVICE_MARKET" VARCHAR2(40 BYTE), 
                "DEVICE_TYPE" VARCHAR2(40 BYTE), 
                "DEVICE_CATEGORY" VARCHAR2(40 BYTE), 
                "EXTERNAL_SOURCE_ID" VARCHAR2(40 BYTE), 
                "DO_NOT_SOLICIT" CHAR(1 BYTE), 
                "DO_NOT_SOLICIT_DATE" DATE, 
                "MESSAGE_LANGUAGE" VARCHAR2(40 BYTE), 
                "ACCOUNT_TYPE" VARCHAR2(40 BYTE), 
                "BILL_CYCLE_END_DAY" NUMBER(2,0), 
                "CREATED_TIMESTAMP" TIMESTAMP (6), 
                "UPDATED_TIMESTAMP" TIMESTAMP (6) DEFAULT SYSDATE, 
                "UPDATED_BY" VARCHAR2(10 BYTE), 
                "CREATED_BY" VARCHAR2(10 BYTE), 
                "CUSTOM_ATTR1" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR2" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR3" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR4" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR5" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR6" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR7" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR8" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR9" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR10" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR11" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR12" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR13" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR14" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR15" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR16" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR17" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR18" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR19" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR20" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR21" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR22" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR23" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR24" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR25" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR26" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR27" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR28" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR29" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR30" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR31" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR32" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR33" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR34" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR35" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR36" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR37" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR38" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR39" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR40" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR41" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR42" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR43" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR44" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR45" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR46" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR47" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR48" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR49" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR50" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR51" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR52" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR53" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR54" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR55" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR56" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR57" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR58" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR59" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR60" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR61" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR62" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR63" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR64" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR65" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR66" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR67" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR68" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR69" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR70" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR71" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR72" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR73" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR74" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR75" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR76" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR77" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR78" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR79" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR80" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR81" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR82" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR83" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR84" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR85" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR86" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR87" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR88" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR89" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR90" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR91" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR92" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR93" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR94" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR95" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR96" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR97" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR98" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR99" VARCHAR2(500 BYTE), 
                "CUSTOM_ATTR100" VARCHAR2(500 BYTE),
                CONSTRAINT "T_SUBSCRIBER_PK" PRIMARY KEY ("BILLING_ID", "SUBSCRIBER_KEY")
  USING INDEX TABLESPACE PART_SUBSCRIBER_PROFILE_IDX01 
                )
                TABLESPACE PART_SUBSCRIBER_DATA01
LOGGING
PARALLEL(DEGREE 12)
PARTITION BY LIST (BILLING_ID)--从这里向下，都是 partition 相关的sql
SUBPARTITION BY HASH (SUBSCRIBER_KEY)
SUBPARTITIONS 512
(
PARTITION P_INIT
VALUES ('FAKE')
LOGGING
STORAGE(MAXEXTENTS UNLIMITED
        )
)
;