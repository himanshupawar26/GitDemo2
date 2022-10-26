import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.OrderDetail;
import pojo.Orders;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

public class EcommAPITest {
	public static void main(String[] args) {
		
		 RequestSpecification req= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON)
				 			.build();
		 
		 //login 
		 LoginRequest loginReq = new LoginRequest();
		 loginReq.setUserEmail("himanshu26pawar@gmail.com");
		 loginReq.setUserPassword("Shivani24");
		 RequestSpecification reqLogin = given().relaxedHTTPSValidation().log().all().spec(req).body(loginReq);
		 
		 LoginResponse loginResp = reqLogin.when().post("/api/ecom/auth/login").then().log().all()
				 					.extract().response().as(LoginResponse.class);
		 
		 //extracting token
		 String token= loginResp.getToken();
		 String userId = loginResp.getUserId();
		 System.out.println(token);
		 System.out.println(userId);
		 
		 //create product
		 RequestSpecification addProductBaseReq= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		 										.addHeader("Authorization", token).build();
		 RequestSpecification reqAddProduct =given().log().all().spec(addProductBaseReq).param("productName", "shiu").param("productAddedBy", userId).param("productCategory", "fashion")
				 					.param("productSubCategory", "shirts").param("productPrice", 11500).param("productDescription", "Addias Originals").param("productFor","women").multiPart("productImage",new File("C:\\Users\\himanshu.pawar\\Pictures\\Saved Pictures\\lincoln wallpaper.jpg"));
		 String addproductResp= reqAddProduct.when().post("/api/ecom/product/add-product").then().log().all().extract().response().asString();
		 JsonPath js = new JsonPath(addproductResp);
		 String productId= js.get("productId");
		 
		 //create order
		 RequestSpecification createOrderBaseReq= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
					.setContentType(ContentType.JSON).addHeader("Authorization", token).build();
		 
		 OrderDetail orderDetail = new OrderDetail();
		 orderDetail.setCountry("india");
		 orderDetail.setProductOrderedId(productId);
		 List<OrderDetail> orderDetailList= new ArrayList();
		 orderDetailList.add(orderDetail);
		 
		 Orders orders = new Orders();
		 orders.setOrders(orderDetailList);
		 RequestSpecification createOrderReq = given().spec(createOrderBaseReq).log().all().body(orders);
		 String addOrderResp = createOrderReq.when().post("api/ecom/order/create-order").then().extract().response().asString();
		 
		 //delete
		 RequestSpecification deleteOrderBaseReq= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
					.addHeader("Authorization", token).build();
		 RequestSpecification deleteProductReq= given().log().all().spec(deleteOrderBaseReq).pathParam("productId",productId);
		 String deleteResp = deleteProductReq.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all().extract().response().asString();
		 
		 JsonPath js1= new JsonPath(deleteResp);
		 Assert.assertEquals("Product Deleted successfully", js1.getString("message"));
	}

}
