import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Lucas-PC on 15/02/2017.
 */
public class Recuit {

	public static Solution Recuit(Solution sol,ArrayList<Integer> permut){
		int nbVoisins = (int)(permut.size()*1.5);

		if(sol.getDelay() > 0) {
			Solution res = sol;
			for(int i=0;i<nbVoisins;i++){
				ArrayList<Integer> pTmp = (ArrayList) Recuit.randomPermutation(permut);
				Solution solTmp = Greedy.stupidGreedy(res.getInstance(),pTmp);

				if(res.getDelay() > solTmp.getDelay()){
					res = Recuit(solTmp,pTmp);
				}
			}
			return res;
		}else{
			return sol;
		}
	}

	public static List<Integer> permutation(List<Integer> permut, int indiceShipToPermut, Instance instance){
		// On cherche a permuté les indice a et b
		int a = permut.indexOf(indiceShipToPermut);
		for(int b = permut.size()-1;b>=0;b--) {
			int indice = permut.get(b);

			Ship ship = instance.getShip(indice);
			Ship shipToPermut = instance.getShip(indiceShipToPermut);

			if ((ship.getArrivalTime() <= shipToPermut.getArrivalTime()) && (shipToPermut.getArrivalTime() < ship.getArrivalTime() + ship.getServingTime()) && (!ship.equals(shipToPermut)) ) {
				ArrayList<Integer> ar = new ArrayList<>(permut);
				ar.set(a,indice);ar.set(b,indiceShipToPermut);
				return ar;
			}
		}
		return null;
	}

	public static List<Integer> randomPermutation(ArrayList<Integer> permut){
		int indice1 =(int) (Math.random()*permut.size());
		int indice2 =(int) (Math.random()*permut.size());
		if(indice2 == indice1) indice2=(int) Math.random()*permut.size();

		int val1 = permut.get(indice1);
		int val2 = permut.get(indice2);
		permut.set(indice1,val2);permut.set(indice2,val1);
		return permut;
	}

	public static void main(String[] args) {
		String instancePath = "i050_500";
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
		solution = Recuit(solution,permutation);
		solution.draw();
	}
}
