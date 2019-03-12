# Aggregator

Reading material on cashflow forecast models:

https://corporatefinanceinstitute.com/resources/knowledge/modeling/monthly-cash-flow-forecast-model/
https://www.cashanalytics.com/daily-cash-flow/
https://www.cashanalytics.com/13-week-cash-flow/

To populate the tables run the following command in Postgres:

```
COPY party(contact_code,party_id,contact_id,name,relationship,segment,company_id) 
FROM 'D:\dev\_scrap\ING_Labs\aggregator\csv\party.csv' DELIMITER ',' CSV HEADER;

COPY invoice(party_id,booking_code,booking_period,invoice_number,invoice_date,due_date,closed_date,remark,currency,amount,tax_amount,company_id,ledger) 
FROM 'D:\dev\_scrap\ING_Labs\aggregator\csv\invoice.csv' DELIMITER ',' CSV HEADER;

COPY financial(year,accounts_receivable,accounts_payable,annual_sales,annual_purchases,inventory,cost_of_goods_sold,days,currency,initial_liquidity,initial_liquidity_date) 
FROM 'D:\dev\_scrap\ING_Labs\aggregator\csv\financial.csv' DELIMITER ',' CSV HEADER;

COPY account(number,name,account_no,iban,credit_limit,closing_balance) 
FROM 'D:\dev\_scrap\ING_Labs\aggregator\csv\account.csv' DELIMITER ',' CSV HEADER;

COPY cff_input(date,bank_account,customer_id,account_code,type,amount,organisation,forecast) 
FROM 'D:\dev\_scrap\ING_Labs\aggregator\csv\cff_input.csv' DELIMITER ',' CSV HEADER;

COPY cff_output(date,bank_account,account_code,customer_id,amount,forecast,type) 
FROM 'D:\dev\_scrap\ING_Labs\aggregator\csv\cff_out.csv' DELIMITER ',' CSV HEADER;
```

Example API call: http://localhost:8080/forecast/incoming?from=1-1-2018&to=1-4-2018&periodDays=7
