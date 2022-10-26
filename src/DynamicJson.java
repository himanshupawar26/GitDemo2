import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.ReusableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {

	@Test(dataProvider="book")
	
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String response = given().log().all().header("Content-Type","text/plain")
				.body(payload.addBook(isbn,aisle))
		.when().post("/Library/Addbook.php").then().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js  = ReusableMethods.rawToJson(response);
		String Id = js.get("ID");
		System.out.println("id"+Id);
		
	}
	
	@DataProvider(name="book")
	public Object[][] getData() {
		return new Object[][] {{"bcd","2926"},{"shi","2426"},{"him","2624"}
			};
	}
	
	@Test (dataProvider="book")
	public void deleteBook(String isbn, String aisle) {
		
	}
	
}
