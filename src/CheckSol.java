import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class CheckSol {

	public static void main(String[] args) {
		String instancePath = args[0];
		String solutionPath = args[1];
		
		Scanner scInst = null;
		try {
			scInst = new Scanner(new File(instancePath));
		} catch (FileNotFoundException e) {
			System.err.println("Instance file not found");
			return;
		}
		
		Scanner scSol = null;
		try {
			scSol = new Scanner(new File(solutionPath));
		} catch (FileNotFoundException e) {
			System.err.println("Solution file not found");
			scInst.close();
			return;
		}
		
		Instance instance = new Instance(scInst);
		Solution solution = new Solution(instance, scSol);
		if (solution.isFeasible()) {
			System.out.println("This solution is feasible.");
			System.out.println("Delay: " + solution.getDelay());
			solution.draw();
		} else {
			System.out.println("This solution is not feasible");
		}
		
	}
}
