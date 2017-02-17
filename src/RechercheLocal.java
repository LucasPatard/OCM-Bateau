import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Lucas-PC on 15/02/2017.
 */
public class RechercheLocal {

	public static Solution start(Solution sol,ArrayList<Integer> permut){
		Solution res = sol;
		ArrayList<Integer> pRes = null;
		boolean isFin = true;
		if(sol.getDelay() > 0) {
			for(int i=0;i<permut.size();i++){
				for(int j=0;j<permut.size();j++) {
					ArrayList<Integer> pTmp = (ArrayList) RechercheLocal.Permutation(permut, i,j);
					Solution solTmp = Greedy.stupidGreedy(res.getInstance(), pTmp);

					if (res.getDelay() > solTmp.getDelay()) {
						res = solTmp;
						pRes=pTmp;
						isFin = false;
					}
				}
			}
			if(isFin == false) {
				res = start(res, pRes);
			}
		}
		return res;
	}

	public static List<Integer> Permutation(ArrayList<Integer> permut,int i,int j){
		// On cherche a permuté les indice a et b
		int indice1 = i;
		int indice2 = j;

		int val1 = permut.get(indice1);
		int val2 = permut.get(indice2);
		permut.set(indice1,val2);permut.set(indice2,val1);
		return permut;
	}

	public static void main(String[] args) {
		String instancePath = "i010_050";
		Scanner scInst = null;
		try {
			scInst = new Scanner(new File(instancePath));
		} catch (FileNotFoundException e) {
			System.err.println("Instance file not found");
			return;
		}
		Instance instance = new Instance(scInst);


		long t = System.currentTimeMillis();
		Solution solution = RechercheLocal.start(instance);
		System.out.println(System.currentTimeMillis()-t);
		solution.draw();
		System.out.println(solution.getDelay());
	}

	public static Solution start(Instance instance){
		ArrayList<Integer> permutation = new ArrayList<Integer>(
				instance.getShipCount());
		for (int i = 0; i < instance.getShipCount(); i++)
			permutation.add(i);
		Collections.shuffle(permutation);
		Solution solution = Greedy.stupidGreedy(instance, permutation);

		return start(solution,permutation);
	}

	public static ArrayList<Integer> triPermutation(ArrayList<Integer> permut,Instance inst){
		permut.sort((o1,o2) -> inst.getShip(o1).getArrivalTime() - inst.getShip(o2).getArrivalTime());
		return permut;
	}
}
