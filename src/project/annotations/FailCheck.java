package project.annotations;

public class FailCheck {

	public static void main(String[] args) {
		
		//System.out.println("This line will fail Gradle check because of missing semicolon")
		System.out.println("This should pass the Gradle check");
		
		//System.out.println("This very very very very very very very very... long line will fail the CheckStyle");
		System.out.println("This short line will pass the CheckStyle check");
	}

}
