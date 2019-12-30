package com.nal.ra.tests.responsevalidation;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

/**
 * Created by nishant on 22/12/19.
 */
public class UsingGPath {

    @Test
    public void testJSONPaths() {
        Response response = RestAssured.given().when().get("https://reqres.in/api/users?page=1");
        System.out.println(response.getStatusCode());
        response.getBody().prettyPrint();

        System.out.println("Getting values from JSON Path");
        JsonPath jsonPath = response.jsonPath();

        System.out.println(response.path("data.find {it.id == 1}.email").toString());
        //Using response object
        System.out.println(response.path("data.findAll {it.first_name == '%s' && it.last_name == '%s'}.email", "Janet" ,"Weaver").toString());

        System.out.println(jsonPath.getString("total_pages"));
        System.out.println(jsonPath.getString("data.find {it.id == 1}.email"));
        System.out.println(jsonPath.getString("data.find {it.id > 5}.first_name"));
        System.out.println(jsonPath.getString("data.find {it.id > 2 && it.last_name=='Morris'}.first_name"));
        System.out.println(jsonPath.getString("data[0].first_name"));
        System.out.println(jsonPath.getString("data[-1].first_name"));
        System.out.println(jsonPath.getString("data[-2].first_name"));  // returns second last element

        System.out.println(jsonPath.getString("data.email"));  //returns all the emails
        System.out.println(jsonPath.get("data.email").toString());  //returns all the emails
        System.out.println(jsonPath.get("data[0,2].first_name").toString());  //returns 1st and 3rd elements first name
        System.out.println(jsonPath.get("data.findAll {it.id <= 2}.first_name").toString());
        System.out.println(jsonPath.get("data.findAll {it.id > 1 && it.id <= 4}.first_name").toString());
        System.out.println(jsonPath.get("data.findAll {it.first_name == 'Janet' || it.first_name == 'Eve'}.email").toString());

        String name = "Janet"; //use of param
        System.out.println(jsonPath.param("name", name).get("data.findAll {it.first_name == name}.email").toString());

        System.out.println(jsonPath.get("data.size()").toString());

        System.out.println(jsonPath.get("data.max {it.id}.first_name").toString());
        System.out.println(jsonPath.get("data.min {it.id}.first_name").toString());
        System.out.println(jsonPath.get("data.find {it.last_name == 'Wong'}.email").toString());
        System.out.println(jsonPath.get("data.findAll {it.last_name == 'Wong' || it.last_name == 'Weaver'}.email").toString());
        System.out.println(jsonPath.get("data.findAll {it.last_name == 'Wong' || it.last_name == 'Weaver'}.find {it.first_name == 'Janet'}.email").toString());
        System.out.println(jsonPath.get("data.find {it.id == 1}.last_name.length()").toString());
        System.out.println(jsonPath.get("data.collect {it.id}.sum()").toString());
        System.out.println(jsonPath.get("data.collect {it.last_name.length()}.sum()").toString());

        System.out.println("Done");
    }


}
