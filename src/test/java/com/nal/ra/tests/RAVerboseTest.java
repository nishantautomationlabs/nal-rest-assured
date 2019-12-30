package com.nal.ra.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static org.hamcrest.core.Is.is;

/**
 * Created by nishant on 22/12/19.
 */
public class RAVerboseTest {

    @Test
    public void testGivenWhenThen() {
        RestAssured
                .given()
                .param("param1", "paramvalue1")
                .and().queryParam("queryparam1", "queryparamvalue1")
                .and().formParam("formparam1", "formparamvalue1")
                .and().header("header1", "headervalue1")
                .and().contentType(ContentType.JSON)
                .and().accept(ContentType.JSON)

                .when().get("https://reqres.in/api/users?page=1")

                .then().statusCode(200).log().all()
                .and().rootPath("data[0]")
                .and().body("id", is(1))
                .and().body("first_name", is("George"))
                .and().detachRootPath("data[0]")
                .and().body("page", is(1));

        System.out.println("Done");
    }
}
