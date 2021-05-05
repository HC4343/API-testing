package com.apitests;

import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import org.testng.Assert;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

public class Test_Rahul {

    public static void main(String[] args){

//        given----> all input details
//        when-----> submit the API -resource, http method
//        then-----> validate the response


        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given()
                                    .log().all()
                                    .queryParam("key", "qaclick123")
                                    .header("Content-Type","application/json" )
                                    .body(Payload.AddPlace()).
                          when()
                                    .post("maps/api/place/add/json").
                          then()
                                    .assertThat()
                                    .statusCode(200)
                                    .body("scope", equalTo("APP"))
                                    .header("server", "Apache/2.4.18 (Ubuntu")
                                    .extract().response().asString();
        System.out.println(response);
        JsonPath js = new JsonPath(response);
        String placeId = js.getSring("place_id");

        //Update Place
        String newAddress = "Summer Walk, Africa";

        given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
                .body("{\r\n" +
                        "\"place_id\":\""+placeId+"\",\r\n" +
                        "\"address\":\""+newAddress+"\",\r\n" +
                        "\"key\":\"qaclick123\"\r\n" +
                        "}").
                when().put("maps/api/place/update/json")
                .then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));

        //Get Place

        String getPlaceResponse=	given().log().all().queryParam("key", "qaclick123")
                .queryParam("place_id",placeId)
                .when().get("maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200).extract().response().asString();
        JsonPath js1=ReUsableMethods.rawToJson(getPlaceResponse);
        String actualAddress =js1.getString("address");
        System.out.println(actualAddress);
        Assert.assertEquals(actualAddress, "Pacific ocean");

    }


}
