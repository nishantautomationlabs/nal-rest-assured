package com.nal.ra.tests.responsevalidation;

import com.nal.ra.model.GSONDatum;
import com.nal.ra.model.GSONUsers;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by nishant on 26/12/19.
 */
public class UsingObjects {

    @Test
    public void validationUsingGSONJavaObject() {
        Response response = RestAssured.given().when().get("https://reqres.in/api/users?page=1");
        System.out.println(response.getStatusCode());
        response.getBody().prettyPrint();

//        Users users = response.as(Users.class, ObjectMapperType.JACKSON_2);
        GSONUsers users = response.as(GSONUsers.class, ObjectMapperType.GSON);

//        GSONUsers users = RestAssured.given()
//                .when().get("https://reqres.in/api/users?page=1")
//                .as(GSONUsers.class);

        System.out.println((int)users.getTotalPages());
        System.out.println(users.getData().stream().filter(datum -> datum.getId() == 1).findAny().get().getEmail());
        System.out.println(users.getData().stream().filter(datum -> datum.getId() > 5).map(datum -> datum.getFirstName()).findAny().get());
        System.out.println(users.getData().stream().filter(datum -> datum.getId() > 2 && datum.getLastName().equals("Morris")).map(datum -> datum.getFirstName()).findAny().get());
        System.out.println(users.getData().get(0).getFirstName());
        System.out.println(users.getData().get(users.getData().size() - 1).getFirstName());
        System.out.println(users.getData().get(users.getData().size() - 2).getFirstName());

        System.out.println(users.getData().stream().map(s -> s.getEmail()).collect(toList()));
        System.out.println(users.getData().stream().filter(s -> s.getId() == 1 || s.getId() == 3).map(s -> s.getFirstName()).collect(toList()));
        System.out.println(users.getData().stream().filter(s -> s.getId() <= 2).map(s -> s.getFirstName()).collect(toList()));
        System.out.println(users.getData().stream().filter(s -> s.getId() > 1 && s.getId() <= 4).map(s -> s.getFirstName()).collect(toList()));
        System.out.println(users.getData().stream().filter(s -> s.getFirstName().equals("Janet") || s.getFirstName().equals("Eve")).map(s -> s.getEmail()).collect(toList()));

        String name = "Janet"; //use of param
        System.out.println(users.getData().stream().filter(s -> s.getFirstName().equals(name)).map(s -> s.getEmail()).collect(toList()));
        System.out.println(users.getData().size());

        System.out.println(users.getData().stream().max(Comparator.comparing(GSONDatum::getId)).map(s -> s.getFirstName()).get());
        System.out.println(users.getData().stream().min(Comparator.comparing(GSONDatum::getId)).map(s -> s.getFirstName()).get());
        System.out.println(users.getData().stream().filter(s -> s.getLastName().equals("Wong")).map(s -> s.getEmail()).findAny().get());
        System.out.println(users.getData().stream().filter(s -> s.getLastName().equals("Wong") || s.getLastName().equals("Weaver")).map(s -> s.getEmail()).collect(Collectors.toList()));
        System.out.println(users.getData().stream().filter(s -> s.getLastName().equals("Wong") || s.getLastName().equals("Weaver"))
                                                    .filter(s -> s.getFirstName().equals("Janet")).map(s -> s.getEmail()).collect(Collectors.toList()));
        System.out.println(users.getData().stream().filter(s -> s.getId() == 1).findFirst().get().getLastName().length());
        System.out.println(users.getData().stream().mapToInt(s -> s.getId()).sum());
//        System.out.println(users.getData().stream().map(s -> s.getId()).reduce(0, Integer::sum));
//        System.out.println(users.getData().stream().map(s -> s.getId()).reduce(0, (a, b) -> a + b));
//        System.out.println(users.getData().stream().map(s -> s.getId()).collect(Collectors.summingInt(Integer::intValue)));
        System.out.println(users.getData().stream().mapToInt(s -> s.getLastName().length()).sum());

        System.out.println("Done");
    }
}
