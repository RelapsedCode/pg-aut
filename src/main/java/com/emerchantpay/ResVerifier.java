package com.emerchantpay;

import io.restassured.response.ValidatableResponse;

import java.util.Map;


public class ResVerifier {

    public void verifyResStatusCode(ValidatableResponse res, int statusCode) {
            assertThat();
        res.extract().statusCode().

    }

}
