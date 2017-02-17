import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Instance {
	private int quayLength;
	private List<Ship> ships;

	public Instance(int quayLength, List<Ship> ships) {
		this.quayLength = quayLength;
		this.ships = new ArrayList<Ship>(ships);
	}

	public Instance(Scanner sc) {
		quayLength = sc.nextInt();
		int n = sc.nextInt();
		ships = new ArrayList<Ship>(n);
		for (int i = 0; i < n; i++) {
			int arrivalTime = sc.nextInt();
			int servingTime = sc.nextInt();
			int length = sc.nextInt();
			ships.add(new Ship(arrivalTime, servingTime, length));
		}
	}

	public int getQuayLength() {
		return quayLength;
	}

	public int getShipCount() {
		return ships.size();
	}

	public Ship getShip(int i) {
		return ships.get(i);
	}

	public void write(PrintStream ps) {
		ps.println(quayLength);
		ps.println(ships.size());
		for (Ship ship : ships)
			ps.println(ship.getArrivalTime() + " " + ship.getServingTime()
					+ " " + ship.getLength());
	}
}
