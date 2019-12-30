package com.nal.ra.tests;

import com.nal.ra.model.GSONUsers;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.hamcrest.core.Is.is;

/**
 * Created by nishant on 26/12/19.
 */
public class RASoftAndHardAsserts {

    @Test
    public void testInlineValidationHardAssert() {
        //Inline validation
        Response response = RestAssured.given()
                .when().get("https://reqres.in/api/users?page=1");
        //Hard Assert
        //if this fails test stops here, below validation will not be performed
        response.then().body("total_pages", is(2))
                .body("per_page", is(6));
    }

    @Test
    public void testInlineValidationSoftAssert() {
        //Inline validation
        Response response = RestAssured.given()
                .when().get("https://reqres.in/api/users?page=1");
        //Soft Assert
        //Better than above as both the validation will be performed in case of failure
        response.then().body("total_pages", is(2), "per_page", is(6));
    }

    @Test
    public void testHardAssertUsingObjects() {
        Response response = RestAssured.given()
                .request("GET", "https://reqres.in/api/users");
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);
        response.then().log().all();

        GSONUsers users = response.as(GSONUsers.class, ObjectMapperType.GSON);

        //Hard Assert
        Assert.assertEquals((int)users.getTotalPages(), 2);
        Assert.assertEquals((int)users.getPerPage(), 6);
        Assert.assertEquals((int)users.getTotal(), 12);

        System.out.println("Done");
    }

    @Test
    public void testSoftAssertUsingObjects() {
        Response response = RestAssured.given()
                .request("GET", "https://reqres.in/api/users");
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);
        response.then().log().all();

        GSONUsers users = response.as(GSONUsers.class, ObjectMapperType.GSON);

//        Soft Assert
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals((int)users.getTotalPages(), 2, "total_pages is not equal");
        softAssert.assertEquals((int)users.getPerPage(), 6, "per_page is not equal");
        softAssert.assertEquals((int)users.getTotal(), 12, "total is not equal");
        softAssert.assertAll();
        System.out.println("Done");
    }

}
