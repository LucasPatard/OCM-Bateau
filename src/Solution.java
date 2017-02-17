import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Solution {
	private Instance instance;
	private List<Integer> berthTimes;
	private List<Integer> positions;

	public Solution(Instance instance) {
		this.instance = instance;
		berthTimes = new ArrayList<Integer>(instance.getShipCount());
		positions = new ArrayList<Integer>(instance.getShipCount());
		for (int i = 0; i < instance.getShipCount(); i++) {
			berthTimes.add(-1);
			positions.add(-1);
		}
	}

	public Solution(Instance instance, Scanner sc) {
		this(instance);
		read(sc);
	}

	public Instance getInstance() {
		return instance;
	}

	public int getBerthTime(int i) {
		return berthTimes.get(i);
	}

	public void setBerthTime(int i, int berthTime) {
		berthTimes.set(i, berthTime);
	}

	public int getPosition(int i) {
		return positions.get(i);
	}

	public void setPosition(int i, int position) {
		positions.set(i, position);
	}

	public void write(PrintStream ps) {
		for (int i = 0; i < instance.getShipCount(); i++)
			ps.println(getBerthTime(i) + " " + getPosition(i));
	}

	public void read(Scanner sc) {
		for (int i = 0; i < instance.getShipCount(); i++) {
			setBerthTime(i, sc.nextInt());
			setPosition(i, sc.nextInt());
		}
	}

	public boolean overlap(int i, int j) {
		Ship shipI = instance.getShip(i);
		Ship shipJ = instance.getShip(j);

		boolean timeOverlap = getBerthTime(i) + shipI.getServingTime() > getBerthTime(j)
				&& getBerthTime(j) + shipJ.getServingTime() > getBerthTime(i);
		boolean spaceOverlap = getPosition(i) + shipI.getLength() > getPosition(j)
				&& getPosition(j) + shipJ.getLength() > getPosition(i);
		return timeOverlap && spaceOverlap;
	}

	public boolean isFeasible() {
		boolean result = true;
		for (int i = 0; i < instance.getShipCount(); i++) {
			Ship shipI = instance.getShip(i);
			if (getBerthTime(i) < shipI.getArrivalTime()) {
				System.err.println("Ship " + i
						+ " berth time is less than its arrival time");
				result = false;
			}
			if (getPosition(i) < 0
					|| getPosition(i) + shipI.getLength() > instance
							.getQuayLength()) {
				System.err.println("Ship " + i + " is out of the quay");
				result = false;
			}
		}

		for (int i = 0; i < instance.getShipCount() - 1; i++) {
			for (int j = i + 1; j < instance.getShipCount(); j++) {
				if (overlap(i, j)) {
					System.err.println("Ships " + i + " and " + j + " overlap");
					result = false;
				}
			}
		}
		return result;
	}

	public List<Integer> getDelayedShip() {
		ArrayList<Integer> ar = new ArrayList<>();
		for (int i = 0; i < instance.getShipCount(); i++) {
			if (getBerthTime(i) - instance.getShip(i).getArrivalTime() > 0) {
				ar.add(i);
			}
		}
		return ar;
	}

	public int getDelay() {
		int delay = 0;
		for (int i = 0; i < instance.getShipCount(); i++)
			delay += getBerthTime(i) - instance.getShip(i).getArrivalTime();
		return delay;
	}

	public void draw() {
		final Solution sol = this;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame f = new JFrame("Solution");
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.add(new SchedulePanel(sol));
				f.pack();
				f.setVisible(true);
			}
		});
	}
}
