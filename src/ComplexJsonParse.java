import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
				JsonPath js = new JsonPath(payload.CoursePrice());
				
				//no of courses
				int count = js.getInt("courses.size()");
				System.out.println(count);
				
				//total amt
				int TotalAmt = js.getInt("dashboard.purchaseAmount");
				
				//first course title
				String firstTitle = js.getString("courses[0].title");
				
				//get title and their prices
				for(int i=0;i<count;i++) {
					String title= js.getString("courses["+i+"].title");
					int price= js.getInt("courses["+i+"].price");
					
					System.out.println(title + price);
				}
				
				/// copies of rpa
				for(int i=0;i<count;i++) {
					if(js.getString("courses["+i+"].title").equalsIgnoreCase("RPA")) {
						int copies = js.getInt("courses["+i+"].copies");
						System.out.println(copies);
						break;
					}
							
				}
				
				///sum of all courses
				
				
	} 
		
		
}
