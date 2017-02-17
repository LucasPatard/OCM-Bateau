import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Greedy {
	/**
	 * Very simple greedy algorithm.
	 * 
	 * Ships are considered in order specified by permutation parameter. Each
	 * ship is placed as soon as possible after its arrival time at the first
	 * position available.
	 * 
	 * @param instance
	 *            A problem instance
	 * @param permutation
	 *            Permutation of ship indices
	 * @return A greedy solution
	 */
	public static Solution stupidGreedy(Instance instance,
			List<Integer> permutation) {
		Solution sol = new Solution(instance);
		for (int p = 0; p < permutation.size(); p++) {
			int i = permutation.get(p);
			int time = instance.getShip(i).getArrivalTime();
			int position = 0;
			boolean found = false;
			while (!found) {
				sol.setBerthTime(i, time);
				sol.setPosition(i, position);
				found = true;
				for (int q = 0; q < p; q++) {
					int j = permutation.get(q);
					if (sol.overlap(i, j)) {
						found = false;
						break;
					}
				}
				position++;
				if (position + instance.getShip(i).getLength() > instance
						.getQuayLength()) {
					position = 0;
					time++;
				}
			}
		}
		return sol;
	}

	public static void main(String[] args) {
		String instancePath = args[0];
		Scanner scInst = null;
		try {
			scInst = new Scanner(new File(instancePath));
		} catch (FileNotFoundException e) {
			System.err.println("Instance file not found");
			return;
		}
		Instance instance = new Instance(scInst);

		List<Integer> permutation = new ArrayList<Integer>(
				instance.getShipCount());
		for (int i = 0; i < instance.getShipCount(); i++)
			permutation.add(i);
		Collections.shuffle(permutation);

		Solution solution = stupidGreedy(instance, permutation);
		if (solution.isFeasible()) {
			System.out.println("This solution is feasible.");
			System.out.println("Delay: " + solution.getDelay());
			solution.draw();
		} else {
			System.out.println("This solution is not feasible");
		}
	}
}
