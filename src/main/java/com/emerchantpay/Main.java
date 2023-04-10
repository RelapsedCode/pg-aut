package com.emerchantpay;

import com.emerchantpay.utilities.RandomDataGenerator;
import com.github.javafaker.Faker;
import io.restassured.response.ValidatableResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> menu = new ArrayList<>();
        System.out.println("Payment gateway test options: ");
        menu.add("\n0. Exit.");
        menu.add("1. Send a valid payment transaction request and expect an approved response");
        menu.add("2. Send a valid void transaction request and expect an approved response");
        menu.add("3. Send a valid payment transaction with an invalid authentication and expect an appropriate response (401)");
        menu.add("4. Send a void transaction pointing to a non-existent payment transaction and expect 422");
        menu.add("5. Send a void transaction pointing to an existent void transaction and expect 422");

        boolean isRunning = true;

        ReqSender reqSender = new ReqSender();
        String paymentTransactionLastId = null;
        PaymentTransaction paymentTransaction = null;
        ValidatableResponse res = null;

        while (isRunning) {
            menu.forEach(System.out::println);
            System.out.print("\nEnter a desired func(): ");
            int option = sc.nextInt();
            sc.nextLine();
            switch (option) {
                case 0 -> isRunning = false;
                case 1 -> {
                    paymentTransaction = new PaymentTransaction();
                    new RandomDataGenerator().generateRandData(paymentTransaction);
                    res = reqSender.createNewTransaction("/payment_transactions", paymentTransaction, true);
                    paymentTransactionLastId = res.extract().path("unique_id");
                }
                case 2 -> {
                    paymentTransaction = new PaymentTransaction();
                    paymentTransaction.setTransaction_type("void");
                    if (paymentTransactionLastId != null) {
                        paymentTransaction.setReference_id(paymentTransactionLastId);
                        res = reqSender.createNewTransaction("/payment_transactions", paymentTransaction, true);
                    } else {
                        System.out.println("Please generate a valid transaction and try again");
                    }
                }
                case 3 -> {
                    paymentTransaction = new PaymentTransaction();
                    new RandomDataGenerator().generateRandData(paymentTransaction);
                    res = reqSender.createNewTransaction("/payment_transactions", paymentTransaction, false);
                }
                case 4 -> {
                    paymentTransaction = new PaymentTransaction();
                    paymentTransaction.setTransaction_type("void");
                    paymentTransaction.setReference_id(new Faker().rickAndMorty().location());
                    res = reqSender.createNewTransaction("/payment_transactions", paymentTransaction, true);
                }
               /* case 5 -> {
                    paymentTransaction = new PaymentTransaction();
                    paymentTransaction.setTransaction_type("void");
                    if (paymentTransactionLastId != null) {
                        paymentTransaction.setReference_id(paymentTransactionLastId);
                        res = reqSender.createNewTransaction("/payment_transactions", paymentTransaction, true);
                    } else {
                        System.out.println("Please generate a valid transaction and try again");
                    }
                }*/
            }
        }

    }
}