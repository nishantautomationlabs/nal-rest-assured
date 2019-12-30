package com.nal.ra.tests.responsevalidation;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by nishant on 22/12/19.
 */
public class UsingRAJSONPath {

    @Test
    public void testJSONPaths() {
        Response response = RestAssured.get("https://reqres.in/api/users?page=1");
        System.out.println(response.getBody().prettyPrint());
//        System.out.println(response.getJsonResponse());

        System.out.println("Getting values from JSON Path");
        JsonPath jsonPath = JsonPath.from(response.getBody().prettyPrint());
        System.out.println(jsonPath.getString("total_pages"));
        System.out.println(jsonPath.getString("data.find {data -> data.id == 1}.email"));
        System.out.println(jsonPath.getString("data.find {data -> data.id > 5}.first_name"));
        System.out.println(jsonPath.getString("data.find {data -> data.id > 2 && data.last_name=='Morris'}.first_name"));
        System.out.println(jsonPath.getString("data[0].first_name"));
        System.out.println(jsonPath.getString("data[-1].first_name"));
        System.out.println(jsonPath.getString("data[-2].first_name")); // returns second last element

        System.out.println(jsonPath.getString("data.email"));
        System.out.println(jsonPath.get("data.email").toString());
        System.out.println(jsonPath.get("data[0,2].first_name").toString());
        System.out.println(jsonPath.get("data.findAll {data -> data.id <= 2}.first_name").toString());
        System.out.println(jsonPath.get("data.findAll {data -> data.id > 1 && data.id <= 4}.first_name").toString());
        System.out.println(jsonPath.get("data.findAll {data -> data.first_name == 'Janet' || data.first_name == 'Eve'}.email").toString());

        String name = "Janet";
        System.out.println(jsonPath.param("name", name).get("data.findAll {data -> data.first_name == name}.email").toString());

        System.out.println(jsonPath.get("data.size()").toString());

        System.out.println("Done");
    }

    @Test
    public void testDifferentReturnDS() {
        Response response = RestAssured.get("https://reqres.in/api/users?page=1");
        Response response2 = RestAssured.get("\"https://reqres.in/api/users/1");
        System.out.println(response.getBody().prettyPrint());
        System.out.println(response2.getBody().prettyPrint());
//        System.out.println(response.getJsonResponse());

        System.out.println("Getting values from JSON Path");
        JsonPath jsonPath = JsonPath.from(response.getBody().prettyPrint());
        JsonPath jsonPath2 = JsonPath.from(response2.getBody().prettyPrint());

        List<String> list = jsonPath.getList("data.email");
        System.out.println(list);

        Map<String, Object> map = jsonPath2.getMap("data");
        printMap(map);

        int id = jsonPath2.getInt("data.id");
        System.out.println(id);

        System.out.println("Done");
    }

    private void printMap(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue().toString());
        }
    }
}
