package com.nal.ra.tests;

import io.restassured.RestAssured;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.Is.is;

/**
 * Created by nishant on 26/12/19.
 */
public class RAValidationsTest {

    @Test
    public void validateStatusCode() {
        Response response = RestAssured.given()
                .request("GET", "https://reqres.in/api/users/1");
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);
        Assert.assertEquals(response.statusLine(), "HTTP/1.1 200 OK");
    }

    @Test
    public void validateResponseTime() {
        Response response = RestAssured.given()
                .request("GET", "https://reqres.in/api/users/1");

        long responseTime = response.getTime();
        System.out.println("Response Time: " + responseTime);

        responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
        System.out.println("Response Time: " + responseTime);

        responseTime = response.time();
        System.out.println("Response Time: " + responseTime);

        responseTime = response.timeIn(TimeUnit.SECONDS);
        System.out.println("Response Time: " + responseTime);

        Assert.assertTrue(responseTime < 10000, "Response time is not within the limit");

        RestAssured.given()
                .request("GET", "https://reqres.in/api/users/1")
                .then().time(Matchers.lessThan(10000L));
    }

    @Test
    public void testBasicValidation() {
        RestAssured.given()
                .when().request("GET", "https://reqres.in/api/users/1")
                .then()
                .body("data.avatar", is("https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg"));
        System.out.println("Done");
    }

    //Custom Matcher using Java 7
    @Test
    public void testCustomMatcher() {
        RestAssured.given()
                .when().request("GET", "https://reqres.in/api/users/1")
                .then()
                .body("data.avatar", new ResponseAwareMatcher<Response>() {
                    @Override
                    public Matcher<?> matcher(Response response) throws Exception {
                        return CoreMatchers.equalTo("https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg");
                    }
                });

        System.out.println("Done");
    }

    //Custom Matcher using Lamda
    @Test
    public void testCustomMatcherUsingLamda() {
        RestAssured.given()
                .when().request("GET", "https://reqres.in/api/users/1")
                .then()
                .body("data.avatar", response -> CoreMatchers.equalTo("https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg"));
        System.out.println("Done");
    }

}
