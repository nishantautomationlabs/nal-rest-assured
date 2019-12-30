package com.nal.ra.tests.responsevalidation;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by nishant on 22/12/19.
 */
public class UsingJaywayJSONPath {

    @Test
    public void testJSONPaths() {
        Response response = RestAssured.get("https://reqres.in/api/users?page=1");
        System.out.println(response.getBody().prettyPrint());
//        System.out.println(response.getJsonResponse());

        DocumentContext jsonPath = JsonPath.parse(response.getBody().print());

        System.out.println("Getting values from JSON Path");
        System.out.println(jsonPath.read("$.total_pages").toString());
        System.out.println(jsonPath.read("$.data[?(@.id=='1')].email").toString());
        System.out.println(jsonPath.read("$.data[?(@.id>5)].first_name").toString());
        System.out.println(jsonPath.read("$.data[?(@.id>2 && @.last_name=='Morris')].first_name").toString());
        System.out.println(jsonPath.read("$.data.[0].first_name").toString());
        System.out.println(jsonPath.read("$.data.[-1:].first_name").toString());
        System.out.println(jsonPath.read("$.data.[-2:].first_name").toString()); //return last two elements

        System.out.println(jsonPath.read("$.data.*.email").toString());
        System.out.println(jsonPath.read("$.data..email").toString());
        System.out.println(jsonPath.read("$.data.[0,2].first_name").toString());
        System.out.println(jsonPath.read("$.data.[:2].first_name").toString());
        System.out.println(jsonPath.read("$.data.[1:4].first_name").toString());
        System.out.println(jsonPath.read("$.data[?(@.first_name == 'Janet' || @.first_name == 'Eve')].email").toString());

        String name = "Janet";
        System.out.println(jsonPath.read("$.data[?(@.first_name == '" + name + "')].email").toString());

        System.out.println(jsonPath.read("$.data.length()").toString());

        System.out.println("Done");
    }

    @Test
    public void testDifferentReturnDS() {
        Response response = RestAssured.get("https://reqres.in/api/users?page=1");
        Response response2 = RestAssured.get("\"https://reqres.in/api/users/1");
        System.out.println(response.getBody().prettyPrint());
        System.out.println(response2.getBody().prettyPrint());
//        System.out.println(response.getJsonResponse());

        DocumentContext jsonPath = JsonPath.parse(response.getBody().prettyPrint());
        DocumentContext jsonPath2 = JsonPath.parse(response2.getBody().prettyPrint());
        System.out.println("Getting values from JSON Path");

        List<String> list = jsonPath.read("data.*.email");
        System.out.println(list);

        Map<String, Object> map = jsonPath2.read("data");
        printMap(map);

        int id = jsonPath2.read("data.id");
        System.out.println(id);

        System.out.println("Done");
    }

    private void printMap(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue().toString());
        }
    }
}
