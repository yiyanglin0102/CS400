import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class main {
	
	public static void main(String[] args) throws Exception {

		CourseSchedulerUtil<String> courseSchedule = new CourseSchedulerUtil<String>();
	
		Entity[] entities = courseSchedule.createEntity("valid.json");

		courseSchedule.constructGraph(entities);
		
		Set<String> allCourses = courseSchedule.getAllCourses();
		System.out.println(allCourses);
		
		
	}
}