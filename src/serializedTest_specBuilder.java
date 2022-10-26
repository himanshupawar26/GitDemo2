import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

public class serializedTest_specBuilder {
	
	public static void main(String[] args) {
		
		
		RequestSpecification req= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
								.addQueryParam("key", "qaclick123")
								.setContentType(ContentType.JSON).build();
		
		ResponseSpecification resp = new ResponseSpecBuilder().expectStatusCode(200)
									.expectContentType(ContentType.JSON).build();
		
		
		AddPlace ap = new AddPlace();
		ap.setAccuracy(50);
		ap.setAddress("29, side layout, cohen 09");
		ap.setLanguage("French-IN");
		ap.setWebsite("http://google.com");
		ap.setName("http://google.com");
		ap.setPhone_number("(+91) 983 893 3937");
		
		List<String> typesList = new ArrayList<String>();
		typesList.add("shoe park");
		typesList.add("shop");
		ap.setTypes(typesList);
		
		Location lobject= new Location();
		lobject.setLat(-38.383494);
		lobject.setLng(33.427362);
		ap.setLocation(lobject);
		
		
		RestAssured.baseURI ="https://rahulshettyacademy.com/";
		
		Response res = given().spec(req)
						.body(ap).when().post("maps/api/place/add/json")
						.then().assertThat().spec(resp).extract().response();
		
		String response = res.asString();
		System.out.println(response);
	}
}
