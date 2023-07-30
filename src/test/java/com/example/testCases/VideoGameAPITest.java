package com.example.testCases;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.example.utils.NotRepeatingRandom;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.util.Date;
import java.util.HashMap;

public class VideoGameAPITest {
    int nextId = 0;

    @BeforeTest
    public void setUp() {
        // Tạo ra ID ngẫu nhiên nhưng tăng dần theo mỗi lần test
        NotRepeatingRandom gen = new NotRepeatingRandom(1000);
        nextId = gen.nextInt();
    }

    @Test(priority = 1)
    public void test_GetAllVideoGames() {
        given()
                .when()
                .get("http://localhost:8080/app/videogames")
                .then()
                .statusCode(200);
    }

    @Test(priority = 2)
    public void test_addNewVideoGame() {
        Date date = new Date();
        long timeMilli = date.getTime();
        System.out.println("===================Time: =======================");
        System.out.println(timeMilli);
        System.out.println("===============Next ID: ==================");
        System.out.println(nextId);

        HashMap data = new HashMap<>();

        String currentDateTime = java.time.LocalDateTime.now().toString();

        data.put("id", String.valueOf(nextId));
        data.put("name", "Spider-man-" + String.valueOf(timeMilli));
        data.put("releaseDate", currentDateTime);
        data.put("reviewScore", "5");
        data.put("category", "Adventure");
        data.put("rating", "Universal");

        Response res = given()
                .contentType("application/json")
                .body(data)
                .when()
                .post("http://localhost:8080/app/videogames")
                .then()
                .statusCode(200)
                .log().body()
                .extract().response();

        String jsonString = res.asString();
        Assert.assertEquals(jsonString.contains("Record Added Successfully"), true);

    }

}
