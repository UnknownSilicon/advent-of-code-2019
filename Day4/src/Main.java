public class Main {
	public static void main(String[] args) {
		int min = 245182;
		int max = 790572;

		int count = 0;

		// Loop through all possible numbers
		loop:
		for (int i=min; i<max; i++) {
			String strVal = i + "";

			// Find the pair of repeating numbers
			boolean hasRepeat = false;
			char previousMatch = ' ';
			int previousMatchNum = -1;
			for (int x=1; x<strVal.length()-1; x++) {
				char previousVal = strVal.charAt(x-1);
				char currentVal = strVal.charAt(x);
				char nextVal = strVal.charAt(x+1);

				if (previousVal == currentVal && currentVal == nextVal) {
					hasRepeat = false;
				} else if (previousVal == currentVal) {
					if (previousMatch == currentVal && previousMatchNum == x-1) {
						hasRepeat = false;
					} else {
						hasRepeat = true;
						previousMatch = currentVal;
						previousMatchNum = x;
					}

				} else if (currentVal == nextVal) {
					hasRepeat = true;
					previousMatch = currentVal;
					previousMatchNum = x+1;
				}
			}
			if (!hasRepeat) continue;

			// Look for decreasing numbers
			for (int x=0; x<strVal.length()-1; x++) {
				int currentVal = Integer.parseInt(strVal.charAt(x) + "");
				int nextVal = Integer.parseInt(strVal.charAt(x+1) + "");

				if (nextVal<currentVal) continue loop;
			}
			count++;
		}

		System.out.println(count);
	}
}
