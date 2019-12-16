public class Main {
	public static void main(String[] args) {
		IntCodeComputer computer = new IntCodeComputer(false);

		computer.execute("103, 5, 103, 6, 1101, 0, 0, 9, 104, 0");
	}
}
