package com.nal.ra.tests;

import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * Created by nishant on 26/12/19.
 */
public class RAHeadersAndCookies {

    @Test
    public void testSetCookie() {
        Cookie cookie = new Cookie.Builder("name", "value").setSecured(true).setPath("/").setComment("comment").build();
        Cookie cookie2 = new Cookie.Builder("name2", "value2").setSecured(true).setPath("/").setComment("comment2").build();
        Cookie singleCookie = new Cookie.Builder("name3", "value3").setSecured(true).setPath("/").setComment("comment3").build();
        Cookies multipleCookies = new Cookies(cookie, cookie2);
        RestAssured.given()
                .cookie("cookiename", "cookievalue")
                .cookie(singleCookie)  //set single cookie
                .cookies(multipleCookies)  //set multiple cookies
                .log().all()
                .when().request("GET", "https://reqres.in/api/users/1")
                .then().statusCode(200).log().all();
        System.out.println("Done");
    }

    @Test
    public void testSetHeaders() {
        Header header1 = new Header("key1", "value1");
        Header header2 = new Header("key2", "value2");
        Headers headers = new Headers(header1, header2);
        RestAssured.given()
                .header("headername", "headerValue")
                .headers(headers)  //set multiple headers
                .log().all()
                .when().request("GET", "https://reqres.in/api/users/1")
                .then().statusCode(200).log().all();
        System.out.println("Done");
    }

    @Test
    public void testValidateHeaders() {
        Headers headers = RestAssured.given()
                .when().request("GET", "https://reqres.in/api/users/1")
                .then().statusCode(200).log().all()
                .extract().headers();

        for(Header header : headers) {
            System.out.println(header.getName() + ":" + header.getValue());
        }

        RestAssured.given()
                .when().request("GET", "https://reqres.in/api/users/1")
                .then().statusCode(200).log().all()
                .assertThat().header("Vary", "Accept-Encoding"); //validate header value

        String header = RestAssured.given()
                .when().request("GET", "https://reqres.in/api/users/1")
                .then().statusCode(200).log().all()
                .extract().header("Vary");

        System.out.println("Header Vary Value: " + header);
        System.out.println("Done");
    }

    @Test
    public void testValidateCookies() {
        Map<String, String> cookies = RestAssured.given()
                .when().request("GET", "https://reqres.in/api/users/1")
                .then().statusCode(200).log().all()
                .extract().cookies();

        for (Map.Entry<String, String> entry : cookies.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        RestAssured.given()
                .when().request("GET", "https://reqres.in/api/users/1")
                .then().statusCode(200).log().all()
                .assertThat().cookie("__cfduid", Is.is(IsNull.notNullValue())); //validate header value

        String cookie = RestAssured.given()
                .when().request("GET", "https://reqres.in/api/users/1")
                .then().statusCode(200).log().all()
                .extract().cookie("__cfduid");

        System.out.println("Cookie __cfduid Value: " + cookie);
        System.out.println("Done");
    }

    @Test
    public void testHeadersAndCookiesUsingResponse() {
        Response response = RestAssured.given()
                .when().get("https://reqres.in/api/users?page=1");

        System.out.println("Connection Header Value:" + response.getHeader("Connection"));

        System.out.println("Listing all the headers");
        Headers headers = response.getHeaders();
        for(Header header : headers) {
            System.out.println(header.getName() + ":" + header.getValue());
        }

        System.out.println("__cfduid Cookie Value:" + response.getCookie("__cfduid"));

        System.out.println("Listing all the cookies");
        Map<String, String> cookies = response.getCookies();
        for(Map.Entry<String, String> cookie : cookies.entrySet()) {
            System.out.println(cookie.getKey() + ":" + cookie.getValue());
        }

        System.out.println("Details of __cfduid cookie");
        Cookie cfduid1 = response.getDetailedCookie("__cfduid");
        System.out.println("Has Expiry Date:" + cfduid1.hasExpiryDate());
        System.out.println("Has Value:" + cfduid1.hasValue());
        System.out.println("Expiry Date:" + cfduid1.getExpiryDate().toString());
        System.out.println("Path:" + cfduid1.getPath());

        System.out.println("Done");
    }
}
