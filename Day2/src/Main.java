public class Main {
	public static void main(String[] args) {
		try {

			for (int x = 0; x < 99; x++) {
				for (int y=0; y< 99; y++) {
					String input = "1,"+x+","+y+",3,1,1,2,3,1,3,4,3,1,5,0,3,2,1,10,19,1,9,19,23,1,13,23,27,1,5,27,31,2,31,6,35,1,35,5,39,1,9,39,43,1,43,5,47,1,47,5,51,2,10,51,55,1,5,55,59,1,59,5,63,2,63,9,67,1,67,5,71,2,9,71,75,1,75,5,79,1,10,79,83,1,83,10,87,1,10,87,91,1,6,91,95,2,95,6,99,2,99,9,103,1,103,6,107,1,13,107,111,1,13,111,115,2,115,9,119,1,119,6,123,2,9,123,127,1,127,5,131,1,131,5,135,1,135,5,139,2,10,139,143,2,143,10,147,1,147,5,151,1,151,2,155,1,155,13,0,99,2,14,0,0";
					String out = execute(input);

					if (out.startsWith("19690720")) {
						System.out.println(x + ", " + y);
					}
				}
			}



		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String execute(String input) throws Exception {
		String[] strInts = input.split(",");
		int[] ops = new int[strInts.length];

		for (int i=0; i<strInts.length; i++) {
			ops[i] = Integer.parseInt(strInts[i].trim());
		}

		for (int i=0; i<ops.length; i+=4) {
			int op = ops[i];

			if (op == 99) {
				return opsToString(ops);
			} else {
				int param1Loc = ops[i+1];
				int param2Loc = ops[i+2];
				int outLoc = ops[i+3];

				int param1 = ops[param1Loc];
				int param2 = ops[param2Loc];

				if (op == 1) {
					ops[outLoc] = param1 + param2;
				} else if (op == 2) {
					ops[outLoc] = param1 * param2;
				} else {
					System.out.println("HALT!");
					return opsToString(ops);
				}
			}
		}
		return null;
	}

	public static String opsToString(int[] ops) {
		String out = "";

		for (int i : ops) {
			out += i;
			out += ", ";
		}
		out = out.substring(0, out.lastIndexOf(","));

		return out;
	}
}
