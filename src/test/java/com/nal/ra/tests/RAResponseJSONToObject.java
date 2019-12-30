package com.nal.ra.tests;

import com.nal.ra.model.GSONUsers;
import com.nal.ra.model.Users;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by nishant on 26/12/19.
 */
public class RAResponseJSONToObject {

    @Test
    public void testConvertToObjectUsingGSON() {
        Response response = RestAssured.given()
                .request("GET", "https://reqres.in/api/users");

        GSONUsers users = response.as(GSONUsers.class, ObjectMapperType.GSON);

        Assert.assertEquals((int)users.getTotalPages(), 2);
        Assert.assertEquals((int)users.getPerPage(), 6);
        Assert.assertEquals((int)users.getTotal(), 12);

        System.out.println("Done");
    }

    @Test
    public void testConvertToObjectUsingJackson() {

        Response response = RestAssured.given()
                .request("GET", "https://reqres.in/api/users/1");

        Users users = response.as(Users.class, ObjectMapperType.JACKSON_2);
        Assert.assertEquals((int)users.getTotalPages(), 2);
        Assert.assertEquals((int)users.getPerPage(), 6);
        Assert.assertEquals((int)users.getTotal(), 12);

        System.out.println("Done");
    }
}
