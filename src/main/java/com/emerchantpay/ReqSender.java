package com.emerchantpay;

import static com.emerchantpay.utilities.ConfigFactory.getManualRequestSpec;

import com.emerchantpay.utilities.ConfigFileReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import lombok.SneakyThrows;

public class ReqSender {
    ConfigFileReader configFileReader = new ConfigFileReader();
    String authorization;

    @SneakyThrows
    public ValidatableResponse createNewTransaction(String endpoint, PaymentTransaction paymentTransaction, Boolean isAuthenticationOn) {
        authorization = isAuthenticationOn ? configFileReader.getPropertyValue("AUTHORIZATION") : null;
        ValidatableResponse response = null;
        ObjectMapper om = new ObjectMapper();
        om.enable(SerializationFeature.WRAP_ROOT_VALUE);
        System.out.println("JSON to be send: " + om.writeValueAsString(paymentTransaction));
        try {
                response = RestAssured
                        .given()
                        .header("Content-Type", "application/json;charset=UTF-8")
                        .header("Authorization", authorization)
                        .spec(getManualRequestSpec())
                        .body(paymentTransaction)
                        .when()
                        .post(endpoint)
                        .then()
                        .log().body();
        } catch (RuntimeException e) {
            System.out.println("Request not send, inspect the log and try again.");
        }
        return response;
    }

}
