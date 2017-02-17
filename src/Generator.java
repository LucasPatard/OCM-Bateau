import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Generator {
	private int quayLength;
	private int shipCount;
	private int minTotalShipLength, maxTotalShipLength;
	private int maxServingTime;
	
	private List<Integer> lengthParPeriod;
	private int currentPeriod;
	private Random rnd;
	
	public Generator(int quayLength, int shipCount, int minTotalShipLength,
			int maxTotalShipLength, int maxServingTime) {
		this.quayLength = quayLength;
		this.shipCount = shipCount;
		this.minTotalShipLength = minTotalShipLength;
		this.maxTotalShipLength = maxTotalShipLength;
		this.maxServingTime = maxServingTime;
		
		lengthParPeriod = new ArrayList<Integer>();
		rnd = new Random();
	}
	
	public Instance generateInstance() {
		lengthParPeriod.clear();
		currentPeriod = 0;
		List<Ship> ships = new ArrayList<Ship>(shipCount);
		for (int i = 0; i < shipCount; i++)
			ships.add(nextShip());
		return new Instance(quayLength, ships);
	}
	
	private Ship nextShip() {
		while (true) {
			if (currentPeriod == lengthParPeriod.size())
				lengthParPeriod.add(0);
			int lcp = lengthParPeriod.get(currentPeriod);
			if (lcp < minTotalShipLength)
				break;
			currentPeriod++;
		}
		int servingTime = 1 + rnd.nextInt(maxServingTime);
		int length = 1 + rnd.nextInt(maxTotalShipLength - lengthParPeriod.get(currentPeriod));
		if (length > quayLength)
			length = quayLength;
		for (int i = currentPeriod; i < currentPeriod + servingTime; i++) {
			if (i == lengthParPeriod.size())
				lengthParPeriod.add(0);
			lengthParPeriod.set(i, lengthParPeriod.get(i) + length);
		}
		return new Ship(currentPeriod, servingTime, length);
	}
	
	public static void main(String[] args) {
		Generator gen = new Generator(4, 7, 4, 4, 3);
		Instance instance = gen.generateInstance();
		instance.write(System.out);
		
//		Generator gen = new Generator(50, 500, 48, 52, 30);
//		Instance instance = gen.generateInstance();
//		PrintStream ps = null;
//		try {
//			ps = new PrintStream("data/i050_500");
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			return;
//		}
//		instance.write(ps);
//		ps.close();
	}
}
