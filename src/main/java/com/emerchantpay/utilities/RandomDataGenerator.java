package com.emerchantpay.utilities;

import com.emerchantpay.PaymentTransaction;
import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RandomDataGenerator {
    Faker faker = new Faker();

    public void generateRandData(PaymentTransaction paymentTransaction) {
        String pattern = "MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

//        paymentTransaction.setCard_number(faker.finance().creditCard().replaceAll("-", ""));
        paymentTransaction.setCard_number("4200000000000000");
        paymentTransaction.setCard_holder(faker.name().name());
        paymentTransaction.setAddress(faker.address().fullAddress());
        paymentTransaction.setCvv(String.valueOf(faker.number().randomNumber(3, true)));
        paymentTransaction.setTransaction_type("sale");
//        paymentTransaction.setTransaction_type(faker.gameOfThrones().dragon());
        paymentTransaction.setAmount(String.valueOf(faker.number().numberBetween(1, 5000)));
        paymentTransaction.setEmail(faker.internet().emailAddress());
        paymentTransaction.setExpiration_date(simpleDateFormat.format(new Date()));
        paymentTransaction.setUsage(faker.gameOfThrones().dragon() + ": "+ faker.hitchhikersGuideToTheGalaxy().marvinQuote());
    }

}
