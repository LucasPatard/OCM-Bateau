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
		int indiceVoisin = 0;
		boolean isFin = true;
		if(sol.getDelay() > 0) {
			for(int i=0;i<permut.size()-1;i++){
				ArrayList<Integer> pTmp = (ArrayList) RechercheLocal.lastPermutation(permut,i);
				Solution solTmp = Greedy.stupidGreedy(res.getInstance(),pTmp);

				if(res.getDelay() > solTmp.getDelay()){
					res = solTmp;
					indiceVoisin = i;
					isFin = false;
				}
			}
			if(isFin == false) {
				res = start(res, (ArrayList) RechercheLocal.lastPermutation(permut, indiceVoisin));
			}
		}

		return res;
	}

	public static List<Integer> lastPermutation(ArrayList<Integer> permut,int nb){
		// On cherche a permuté les indice a et b
		int indice1 = nb;
		int indice2 =(int) (permut.size()-1);

		int val1 = permut.get(indice1);
		int val2 = permut.get(indice2);
		permut.set(indice1,val2);permut.set(indice2,val1);
		return permut;
	}

	public static void main(String[] args) {
		String instancePath = "i020_100";
		Scanner scInst = null;
		try {
			scInst = new Scanner(new File(instancePath));
		} catch (FileNotFoundException e) {
			System.err.println("Instance file not found");
			return;
		}
		Instance instance = new Instance(scInst);

		ArrayList<Integer> permutation = new ArrayList<Integer>(
				instance.getShipCount());
		for (int i = 0; i < instance.getShipCount(); i++)
			permutation.add(i);
		Collections.shuffle(permutation);

		Solution solution = Greedy.stupidGreedy(instance, permutation);
		solution = RechercheLocal.start(solution,permutation);
		solution.draw();
		System.out.println(solution.getDelay());
	}
}
