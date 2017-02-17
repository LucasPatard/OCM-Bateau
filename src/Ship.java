
public class Ship {
	private int arrivalTime;
	private int servingTime;
	private int length;
	
	public Ship(int arrivalTime, int servingTime, int length) {
		this.arrivalTime = arrivalTime;
		this.servingTime = servingTime;
		this.length = length;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public int getServingTime() {
		return servingTime;
	}

	public int getLength() {
		return length;
	}
}
