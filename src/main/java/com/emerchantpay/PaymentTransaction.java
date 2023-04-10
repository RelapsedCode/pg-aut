package com.emerchantpay;


import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@JsonRootName(value = "payment_transaction")
public class PaymentTransaction {
    String card_number;
    String cvv;
    String expiration_date;
    String amount;
    String usage;
    String transaction_type;
    String card_holder;
    String email;
    String address;
}
