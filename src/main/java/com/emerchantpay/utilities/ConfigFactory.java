package com.emerchantpay.utilities;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.FailureConfig;
import io.restassured.config.LogConfig;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.listener.ResponseValidationFailureListener;
import io.restassured.path.json.mapper.factory.Jackson2ObjectMapperFactory;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;


public class ConfigFactory {

    static ConfigFileReader configFileReader = new ConfigFileReader();
    public static RestAssuredConfig getManualConfig() {
        ResponseValidationFailureListener failureListener = (reqSpec, resSpec, response) ->
                System.out.printf("We have a failure, " +
                                "response status was %s and the body contained: %s",
                        response.getStatusCode(), response.body().asPrettyString());

        return RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL))
                .failureConfig(FailureConfig.failureConfig().failureListeners(failureListener))
                .objectMapperConfig(ObjectMapperConfig.objectMapperConfig().jackson2ObjectMapperFactory(getManualMapper()));
    }

    public static Jackson2ObjectMapperFactory getManualMapper() {
        return (type, s) -> {
            ObjectMapper om = new ObjectMapper();
            om.enable(SerializationFeature.WRAP_ROOT_VALUE);
            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return om;
        };
    }

    public static RequestSpecification getManualRequestSpec() {

        return new RequestSpecBuilder()
                .setBaseUri(configFileReader.getPropertyValue("BASE_URL") + ":" + configFileReader.getPropertyValue("PORT"))
//                .setBasePath(GlobalVar.BASE_PATH)
                .setConfig(ConfigFactory.getManualConfig())
                .build();
    }

    public static ResponseSpecification getManualResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_OK)
//                .expectContentType(ContentType.JSON)
                .build();
    }

}

