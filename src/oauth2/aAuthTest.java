package oauth2;
import static io.restassured.RestAssured.*;

import java.util.List;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.GetCourse;
import pojo.api;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import org.testng.Assert;

public class aAuthTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		
		//using selenium to paste authorization url in browser to get url with generated code
		/*
		 * System.setProperty("webdriver.chrome.driver",
		 * "C:\\Users\\himanshu.pawar\\eclipse-workspace\\JAVA_API\\Drivers\\chromedriver.exe"
		 * ); WebDriver driver = new ChromeDriver();
		 * 
		 * driver.get(
		 * "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php"
		 * ); Thread.sleep(5000);
		 * driver.findElement(By.xpath("//*[@id='identifierId']")).sendKeys(
		 * "himanshu26pawar@gmail.com");
		 * driver.findElement(By.xpath("//*[@id='identifierNext']/div/button/span")).
		 * click(); Thread.sleep(5000);
		String url= driver.getCurrentUrl();
		*/
		
		String url ="https://rahulshettyacademy.com/getCourse.php?code=4%2F0ARtbsJpOsxAcErG6g990TllSLfVMcDRcGltB-5E5AJnajIgesuP1_QYhHWIcI5i5AikjtQ&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
		//split url to get code
		String partialUrl= url.split("code=")[1];
		String code=partialUrl.split("&scope")[0];
		System.out.println("code"+code);
		
		//using code to get access token
		String accessTokenResponse = given().log().all().urlEncodingEnabled(false).queryParam("code", code)
							.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
							.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
							.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
							.queryParam("grant_type", "authorization_code")
							.when().log().all().post("https://www.googleapis.com/oauth2/v4/token")
							.asString();
		
		System.out.println("token response= "+accessTokenResponse);
		JsonPath js= new JsonPath(accessTokenResponse);
		String accessToken =  js.getString("access_token");
		System.out.println("access token= "+accessToken);
		
		//usign access token to reach end // passing json resp to getcourse class
		GetCourse gc = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
						.when()
						.get("https://rahulshettyacademy.com/getCourse.php")
						.as(GetCourse.class);
		
		System.out.println( gc.getLinkedIn());
		
		// gc.getCourses().getApi().get(1).getCourseTitle();
		
		List<api> apiList = gc.getCourses().getApi();
		for(int i=0;i<apiList.size();i++) {
			if(gc.getCourses().getApi().get(1).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(apiList.get(i).getCourseTitle());
			}
		}
		
	}

}
