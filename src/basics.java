import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.ReusableMethods;
import files.payload;

public class basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//given
		//when
		//then
		RestAssured.baseURI ="https://rahulshettyacademy.com/";
		
		//add place post api mthod
		String response = given().log().all().queryParam("key" , "qaclick123").header("Content-Type","application/json")
		.body(payload.AddPlace()).when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope",equalTo("APP"))
		.header("Server","Apache/2.4.41 (Ubuntu)").extract().response().asString();
		
		System.out.println(response);
		
		JsonPath js = ReusableMethods.rawToJson(response);
		String placeId = js.getString("place_id");
		System.out.println("place id= "+placeId);
		
		//update using PUT METHOD
		String newAddress= "70 winter walk, USA";
		
		given().log().all().queryParam("key" , "qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "").when().put("maps/api/place/update/json").then().assertThat().statusCode(200)
		;
		
		
		//getplace api method
		
		String getPlaceResp= given().log().all().queryParam("key" , "qaclick123").queryParam("place_id", placeId).when().get("maps/api/place/get/json")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js1= ReusableMethods.rawToJson(getPlaceResp);
		String actAddress= js1.getString("address");
		
		Assert.assertEquals(actAddress, newAddress);
		
		
	}

}
