DROP TABLE IF EXISTS `CHARGE_DETAIL`;
create table CHARGE_DETAIL
(
   ID integer not null,
   SUMMARY_TRACE_ID varchar(255) not null,
   INVOICE_DATE DATE,
   INVOICE_ICA VARCHAR2(11),
   ACTIVITY_ICA VARCHAR2(11),
   primary key(ID)
);

DROP TABLE IF EXISTS `CHARGE_TRANSACTION_TRACE`;
create table CHARGE_TRANSACTION_TRACE
(
   ID integer not null,
   FEEDER_ID varchar(255),
   BILLING_SUMMARY_TRACE_ID  varchar(255) not null,
   DETAIL_TRACE_ID varchar(255),
   BILLING_EVENT_ID varchar(255),
   primary key(ID)
);