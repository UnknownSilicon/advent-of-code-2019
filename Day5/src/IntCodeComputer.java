import java.util.Scanner;

public class IntCodeComputer {
	public final int[] OPERATIONS = {1, 2, 3, 4, 99};
	public final int[] NUM_PARAMS = {3, 3, 1, 1, 0};

	private boolean debugMode = false;

	public IntCodeComputer(boolean debugMode) {
		this.debugMode = debugMode;
	}

	public void execute(int[] ops) {
		int ip = 0; // Instruction pointer

		while (ip < ops.length) {
			int[] opData = extractOps(ops[ip]); // [operation, parMode1, parMode2,...]
			int[] parameters = new int[opData.length-1];

			// Store parameters in array. Not using modes yet
			for (int i=1; i<opData.length; i++) {
				int pp = ip+i; // Parameter pointer

				parameters[i-1] = ops[pp];
			}

			int operation = opData[0];
			if (operation == 1) {
				// Adds two numbers
				// Parameters: [in1, in2, out]
				if (parameters.length != 3) {
					System.out.println("Error! Parameter length for operation 1 at address " + ip + " is invalid!");
				}
				int val1 = 0;
				int val2 = 0;

				int outLoc = -1;

				if (opData[1] == 0) { // Position Mode
					val1 = ops[parameters[0]];
				} else if (opData[1] == 1) { // Immediate Mode
					val1 = parameters[0];
				} else {
					System.out.println("Error! Invalid parameter mode for operation 1 at address " + ip);
					System.exit(1);
				}

				if (opData[2] == 0) { // Position Mode
					val2 = ops[parameters[1]];
				} else if (opData[2] == 1) { // Immediate Mode
					val2 = parameters[1];
				} else {
					System.out.println("Error! Invalid parameter mode for operation 1 at address " + ip);
					System.exit(1);
				}

				if (opData[3] == 0) {
					outLoc = parameters[2];
				} else if (opData[3] == 1) {
					System.out.println("Error! Operation 1 cannot take parameter mode 1 for parameter number 3 at address " + ip);
					System.exit(1);
				}

				ops[outLoc] = val1 + val2;

			} else if (operation == 2) {
				// Multiplies two numbers
				// Parameters: [in1, in2, out]
				if (parameters.length != 3) {
					System.out.println("Error! Parameter length for operation 2 at address " + ip + " is invalid!");
				}
				int val1 = 0;
				int val2 = 0;

				int outLoc = -1;

				if (opData[1] == 0) { // Position Mode
					val1 = ops[parameters[0]];
				} else if (opData[1] == 1) { // Immediate Mode
					val1 = parameters[0];
				} else {
					System.out.println("Error! Invalid parameter mode for operation 2 at address " + ip);
					System.exit(1);
				}

				if (opData[2] == 0) { // Position Mode
					val2 = ops[parameters[1]];
				} else if (opData[2] == 1) { // Immediate Mode
					val2 = parameters[1];
				} else {
					System.out.println("Error! Invalid parameter mode for operation 2 at address " + ip);
					System.exit(1);
				}

				if (opData[3] == 0) {
					outLoc = parameters[2];
				} else if (opData[3] == 1) {
					System.out.println("Error! Operation 1 cannot take parameter mode 1 for parameter number 3 at address " + ip);
					System.exit(1);
				}

				ops[outLoc] = val1 * val2;
			} else if (operation == 3) {
				// Takes input from an outside source and stores it into an address
				// Parameters: [out]
				if (parameters.length != 1) {
					System.out.println("Error! Parameter length for operation 3 at address " + ip + " is invalid!");
				}
				int outLoc = -1;

				outLoc = parameters[0];

				ops[outLoc] = getInput();
			} else if (operation == 4) {
				// Outputs the value of it's parameter
				// Parameters: [out]
				if (parameters.length != 1) {
					System.out.println("Error! Parameter length for operation 4 at address " + ip + " is invalid!");
				}
				int outLoc = -1;

				outLoc = parameters[0];

				System.out.println(ops[outLoc]);
			} else if (operation == 99) {
				if (debugMode) {
					for (int o : ops) {
						System.out.println(o);
					}
				}
				System.out.println("Program Halt");
				System.exit(0);
			} else {
				System.out.println("Error! Invalid operation at address " + ip);
				System.exit(1);
			}

			ip += opData.length;
		}

	}

	public void execute(String ops) {
		execute(stringToOps(ops));
	}

	Scanner scan = new Scanner(System.in);
	private int getInput() {
		System.out.println("Input Requested: ");
		return scan.nextInt();
	}

	/**
	 * Extract the operation and parameter modes of the given opcode
	 * @param op The opcode to extract from
	 * @return The operation and parameter modes (reversed. i.e [operation, parMode1, parMode2, parMode3]
	 */
	public int[] extractOps(int op) {
		String str = op + ""; // Convert op to string

		int operation = -1;
		if (str.length() > 1) {
			operation = Integer.parseInt(str.substring(str.length()-2));
		} else {
			operation = Integer.parseInt(str);
		}

		int numParams = getOpParamCount(operation);

		int[] codes = new int[numParams+1];

		codes[0] = operation;

		int index = 1;
		for (int i=str.length()-2; i>str.length()-2-numParams; i--) {
			if (i <= 0) {
				codes[index++] = 0;
			} else {
				codes[index++] = Integer.parseInt(str.substring(i-1, i));
			}
		}

		return codes;
	}

	public int[] stringToOps(String data) {
		String[] strInts = data.split(",");
		int[] ops = new int[strInts.length];

		for (int i=0; i<strInts.length; i++) {
			ops[i] = Integer.parseInt(strInts[i].trim());
		}
		return ops;
	}

	public String opsToString(int[] ops) {
		String out = "";

		for (int i : ops) {
			out += i;
			out += ", ";
		}
		out = out.substring(0, out.lastIndexOf(","));

		return out;
	}

	/**
	 * Returns the number of parameters a given operation has.
	 * This should probably be made to be more efficient somehow.
	 * @param op The operation to find the number of it's parameters
	 * @return The number of parameters the given operation takes
	 */
	private int getOpParamCount(int op) {
		int index = -1;
		for (int i=0; i<OPERATIONS.length; i++) {
			if (OPERATIONS[i] == op) {
				index = i;
				break;
			}
		}
		if (index == -1) return -1;
		return NUM_PARAMS[index];
	}
}
