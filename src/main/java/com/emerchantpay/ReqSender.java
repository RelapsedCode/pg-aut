package com.emerchantpay;

import com.emerchantpay.utilities.ConfigFactory;
import com.emerchantpay.utilities.ConfigFileReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import lombok.SneakyThrows;

import static com.emerchantpay.utilities.ConfigFactory.getManualRequestSpec;

public class ReqSender {
    ConfigFileReader configFileReader = new ConfigFileReader();
    String authorization;
    String authPassword;
    String authUsername;

    @SneakyThrows
    public ValidatableResponse createNewTransaction(String endpoint, PaymentTransaction paymentTransaction, Boolean isAuthenticationOn) {
        authorization = isAuthenticationOn ? configFileReader.getPropertyValue("AUTHORIZATION") : null;
        authPassword = isAuthenticationOn ? configFileReader.getPropertyValue("PASSWORD64") : null;
        authUsername = isAuthenticationOn ? configFileReader.getPropertyValue("USERNAME64") : null;
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
//                    .spec(getManualResponseSpec())
                    .log().body();

        } catch (RuntimeException e) {
            System.out.println("Smth went wrong");
            System.out.println(response.toString());
        }
        return response;
    }

}
