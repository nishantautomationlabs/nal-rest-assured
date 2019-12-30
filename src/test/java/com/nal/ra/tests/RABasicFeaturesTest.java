package com.nal.ra.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nishant on 26/12/19.
 */
public class RABasicFeaturesTest {

    @Test
    public void testBaseURIAndBasePath() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
        Response response = RestAssured.given()
                .queryParam("page", "1")
                .get("/users");
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);
    }

    @Test
    public void testQueryParam() {
        RestAssured.given()
                .queryParam("page", "1")  // for get calls
                .log().all()
                .when().request("GET", "https://reqres.in/api/users")
                .then().statusCode(200).log().all();
        System.out.println("Done");
    }

    @Test
    public void testFormParam() {
        RestAssured.given()
                .formParam("page", "1")  // for post calls
                .log().all()
                .when().request("GET", "https://reqres.in/api/users")
                .then().statusCode(200).log().all();
        System.out.println("Done");
    }

    @Test
    public void testParam() {
        RestAssured.given()
                .param("page", "1")  // sets query param if get call and form param if post call
                .log().all()
                .when().request("GET", "https://reqres.in/api/users")
                .then().statusCode(200).log().all();
        System.out.println("Done");
    }

    @Test
    public void testParamWithMultipleValue() {
        List<Integer> paramValue = new ArrayList<>();
        paramValue.add(1);
        paramValue.add(2);
        RestAssured.given()
                .param("page", paramValue)  //set multiple values like ?page=1&page=2
                .log().all()
                .when().request("GET", "https://reqres.in/api/users")
                .then().statusCode(200).log().all();
        System.out.println("Done");
    }

    @Test
    public void testQueryParamWithMultipleKeyValue() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("page","1");
        paramMap.put("count","2");
        RestAssured.given()
                .queryParams(paramMap)  //set multiple values like ?page=1&page=2
                .log().all()
                .when().request("GET", "https://reqres.in/api/users")
                .then().statusCode(200).log().all();
        System.out.println("Done");
    }

    @Test
    public void testPathParams() {
        RestAssured.given()
                .pathParam("type", "api")
                .pathParam("resource", "users")
                .pathParam("userId", "1")
                .log().all()
                .when().request("GET", "https://reqres.in/{type}/{resource}/{userId}")
                .then().statusCode(200).log().all();
        System.out.println("Done");
    }

    @Test
    public void testRequestSpecification() {
        RequestSpecification requestSpecification = RestAssured.given().contentType(ContentType.JSON);
        requestSpecification = requestSpecification.queryParam("page", "1");
        requestSpecification.log().all();
        Response response = requestSpecification.get("https://reqres.in/api/users/1");
        Assert.assertEquals(response.statusCode(), 200);
        System.out.println("Done");
    }

    @Test
    public void testExtractValue()
    {
        int id = RestAssured.given()
                .when().get("https://reqres.in/api/users?page=1")
                .path("data[0].id");
        System.out.println("Id: " + id);

        id = RestAssured.given()
                .when().get("https://reqres.in/api/users?page=1")
                .andReturn().jsonPath().get("data[1].id");
        System.out.println("Id: " + id);

        String avatar = RestAssured.given()
                .when().get("https://reqres.in/api/users?page=1")
                .then().extract().path("data[0].avatar");
        System.out.println("Avatar: " + avatar);

        Response response = RestAssured.given()
                .when().get("https://reqres.in/api/users?page=1")
                .then().extract().response();
        System.out.println(response.getBody().prettyPrint());

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Content Type: " + response.contentType());
        System.out.println("Id: " + response.path("data[2].id").toString());
        System.out.println("Done");
    }

    @Test
    public void testLogging() {
        RestAssured.given()
                .when().get("https://reqres.in/api/users/1")
//                .when().get("https://reqres.in/api/dummy/users/1")
                .then()
                .log().all();
//                .log().body();
//                .log().cookies();
//                .log().headers();
//                .log().everything();
//                .log().ifError();
//                .log().ifStatusCodeIsEqualTo(200);
//                .log().ifStatusCodeMatches(Matchers.greaterThan(200));
//                .log().ifValidationFails().body("data.id", Is.is(2));
//                .log().ifValidationFails(LogDetail.BODY, true).body("data.id", Is.is(2));
    }

    @Test
    public void testSetParser() {
        RestAssured.given()
                .when().get("https://reqres.in/api/users/1")
                .then().using().defaultParser(Parser.JSON) //in case the response from api does not define the content type
                .statusCode(200);
    }
}
