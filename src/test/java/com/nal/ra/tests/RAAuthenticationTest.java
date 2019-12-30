package com.nal.ra.tests;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

/**
 * Created by nishant on 26/12/19.
 */
public class RAAuthenticationTest {

    //Preemptive Authentication - username and password is sent to server always
    @Test
    public void validateBasicPreemptiveAuthentication() {
        given()
            .auth().preemptive().basic("username", "password")
            .request("GET", "https://reqres.in/api/users/1")
            .then().statusCode(200);
    }

    //Challenged Authentication - username and password is sent to server when asked
    @Test
    public void validateBasicAuthentication() {
        given()
                .auth().basic("username", "password")
                .request("GET", "https://reqres.in/api/users/1")
                .then().statusCode(200);
    }

    //More secure than the Challenged authentication using a digest key and avoids sending the password in cleartext
    @Test
    public void validateDigestAuthentication() {
        given()
                .auth().digest("username", "password")
                .request("GET", "https://reqres.in/api/users/1")
                .then().statusCode(200);
    }

    @Test
    public void validateOAuthAuthentication() {
        given()
                .auth().oauth("apikey", "apikeysecret", "accesstoken", "accesstokensecret")
                .request("GET", "https://reqres.in/api/users/1")
                .then().statusCode(200);
    }

    @Test
    public void validateOAuth2Authentication() {
        given()
                .auth().oauth2("accesstoken") //make the API call to get the access token as it expires in sometime
                .request("GET", "https://reqres.in/api/users/1")
                .then().statusCode(200);
    }

}
