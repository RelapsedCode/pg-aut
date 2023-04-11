package com.emerchantpay;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.restassured.response.ValidatableResponse;

public class ResVerifier {

	public ResVerifier verifyResStatusCode(ValidatableResponse res, int statusCode) {
		assertEquals(statusCode, res.extract().statusCode());
		return this;
	}

	public ResVerifier verifyStatusParameterValue(ValidatableResponse res, String expectedValue) {
		assertEquals(expectedValue, res.extract().path("status"));
		return this;
	}

	public ResVerifier verifyInvalidReference(ValidatableResponse res) {
		String reference_id = res.extract().path("reference_id");
		assertTrue(reference_id.contains("Invalid reference transaction"));
		return this;
	}

}
