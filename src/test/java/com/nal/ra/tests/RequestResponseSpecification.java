package com.nal.ra.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import static org.hamcrest.Matchers.*;

/**
 * Created by nishant on 30/12/19.
 */
public class RequestResponseSpecification {

    RequestSpecification reqSpec;
    ResponseSpecification resSpec;

    @BeforeMethod
    public void setUp() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
//        RestAssured.port = 8080;
//        RestAssured.rootPath = "data.user.id";
//        RestAssured.authentication = basic("username", "password");

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.addParam("page", "1");
        requestSpecBuilder.addHeader("key", "value");
        reqSpec = requestSpecBuilder.build();
        RestAssured.requestSpecification = reqSpec;

        ResponseSpecBuilder specBuilder = new ResponseSpecBuilder();
        specBuilder.expectStatusCode(200);
        specBuilder.expectHeader("Content-Type", "application/json; charset=utf-8");
        specBuilder.expectCookie("__cfduid", is(not(emptyOrNullString())));
        resSpec = specBuilder.build();
        RestAssured.responseSpecification = resSpec;

//        RestAssured.filters();
//        RestAssured.urlEncodingEnabled = true;
//        RestAssured.defaultParser = ;
//        RestAssured.registerParser();
//        RestAssured.unregisterParser();
//        RestAssured.useRelaxedHTTPSValidation();
//        RestAssured.trustStore();
//        RestAssured.reset();
    }

    @Test
    public void testSettingSpecificationInBeforeClass() {
        Response response = RestAssured.given().log().all()
//                .queryParam("page", "1")  //set in requestSpecification
                .get("/users")
                .then().log().all().extract().response();
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);
    }

    @Test
    public void testSettingSpecificationInTest() {
        Response response = RestAssured
                .given()
                .spec(reqSpec).log().all()
                .when().get("/users")
                .then()
                .spec(resSpec).log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);
    }
}
