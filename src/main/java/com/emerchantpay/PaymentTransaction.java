package com.emerchantpay;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName(value = "payment_transaction")
public class PaymentTransaction {
    private String card_number;
    private String cvv;
    private String expiration_date;
    private String amount;
    private String usage;
    private String transaction_type;
    private String card_holder;
    private String email;
    private String address;
    private String reference_id;
}
