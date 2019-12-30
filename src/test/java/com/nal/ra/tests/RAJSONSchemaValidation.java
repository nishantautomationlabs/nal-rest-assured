package com.nal.ra.tests;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

/**
 * Created by nishant on 26/12/19.
 */
public class RAJSONSchemaValidation {

    @Test
    public void testRAJSONSchemaValidation() {
        RestAssured.given()
                .when().get("https://reqres.in/api/users/1")
                .then().assertThat().body(matchesJsonSchemaInClasspath("userjsonschema.json"));
        System.out.println("Done");

    }
}
