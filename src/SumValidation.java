import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {

	@Test
	public void sumValidation() {
		
		JsonPath js = new JsonPath(payload.CoursePrice());
		int count = js.getInt("courses.size()");
		
		int total=0;
		
		for(int i=0;i<count;i++) {
			int price= js.getInt("courses["+i+"].price");
			int copies= js.getInt("courses["+i+"].copies");
			
			int subTotal = price*copies;
			total = total+subTotal;
			
		}
		System.out.println("sum is "+ total);
		
		int pamt = js.getInt("dashboard.purchaseAmount");
		
		Assert.assertEquals(pamt, total);
	}
}
