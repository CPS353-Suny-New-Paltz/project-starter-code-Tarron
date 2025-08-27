package project.annotations;

public class FailCheck {

	public static void main(String[] args) {
		
		//Will fail Gradle
		System.out.println("This line will fail Gradle check because of missing semicolon")
		
		//Will fail Checkstyle
		System.out.println("This very very very very very very very very very very very very very very very very very very very very very very very very very very long line will fail the CheckStyle");
	}

}
