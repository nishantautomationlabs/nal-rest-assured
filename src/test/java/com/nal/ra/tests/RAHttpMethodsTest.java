package com.nal.ra.tests;

import com.nal.ra.model.GSONUsers;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nishant on 22/12/19.
 */
public class RAHttpMethodsTest {


    @Test
    public void testGet() {
        RestAssured.given()
                .when().request("GET", "https://reqres.in/api/users/1")
                .then().statusCode(HttpStatus.SC_OK);

        RestAssured.when().get("https://reqres.in/api/users/1")
                .then().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void testPost() {
        String jsonRequest = "{\"data\":{\"id\":1,\"email\":\"george.bluth@reqres.in\",\"first_name\":\"George\",\"last_name\":\"Bluth\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg\"}}";
        GSONUsers gsonUsers = new GSONUsers();
        Map<String, String> request = new HashMap<>();
        request.put("key1", "value1");
        request.put("key2", "value2");

        //Using json string
        RestAssured.given().body(jsonRequest)
                .when().request("POST", "https://reqres.in/api/users/1")
                .then().statusCode(HttpStatus.SC_CREATED);

        //using object
        RestAssured.given().body(gsonUsers)
                .when().post("https://reqres.in/api/users/1")
                .then().statusCode(HttpStatus.SC_CREATED);

        //using map of key value pair
        RestAssured.given().body(request)
                .when().post("https://reqres.in/api/users/1")
                .then().statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void testPut() {
        String jsonRequest = "{\"data\":{\"id\":1,\"email\":\"george.bluth@reqres.in\",\"first_name\":\"George\",\"last_name\":\"Bluth\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg\"}}";

        RestAssured.given().body(jsonRequest)
                .when().request("PUT", "https://reqres.in/api/users/1")
                .then().statusCode(HttpStatus.SC_OK);

        RestAssured.given().body(jsonRequest)
                .when().put("https://reqres.in/api/users/1")
                .then().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void testDelete() {
        String jsonRequest = "{\"data\":{\"id\":1,\"email\":\"george.bluth@reqres.in\",\"first_name\":\"George\",\"last_name\":\"Bluth\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg\"}}";

        //With body
        RestAssured.given().body(jsonRequest)
                .when().delete("https://reqres.in/api/users/1")
                .then().statusCode(HttpStatus.SC_NO_CONTENT);

        //Without body
        RestAssured.given()
                .when().delete("https://reqres.in/api/users/1")
                .then().statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    public void testHead() {
        RestAssured.given()
                .when().head("https://reqres.in/api/users/1")
                .then().statusCode(HttpStatus.SC_OK)
                .log().all();
    }

    @Test
    public void testConnect() {
        RestAssured.when().request("CONNECT", "https://api.fonts.com/rest/json/Accounts/")
                .then().statusCode(400)
                .statusLine("HTTP/1.1 400 Bad Request");
        System.out.println("Done");
    }
}
