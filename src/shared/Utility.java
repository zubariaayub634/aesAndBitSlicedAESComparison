package shared;

public class Utility {
	public static void printArray(Integer[] array) {
		for (int j = 0; j < array.length; j++) {
			if (array[j] < 16)
				System.out.print(0);
			System.out.print(Integer.toHexString(array[j]) + " ");
		}

	}

	public static void printArray(Integer[][] array) {
		for (int i = 0; i < array.length; i++) {
			printArray(array[i]);
			System.out.println();
		}
	}

	public static void leftShift(Integer[] array)// one left shift for now
	{
		Integer temp = array[0];
		for (int i = 0; i < array.length - 1; i++) {
			array[i] = array[i + 1];
		}
		array[array.length - 1] = temp;
	}

	// Multiplies two bytes in garlois field 2^8
	// a must either be 1 or 2 or 3
	public static Integer multiplyInGaloisField(Integer a, Integer b) {
		if (a == 1) {
			return b;
		}
		if (a == 2) {
			Integer temp = b << 1;
			if (temp >= 256) {
				temp = temp % 256;
				temp = temp ^ 0x1b;
			}
			return temp;
		}
		if (a == 3) {
			Integer temp = b << 1;
			if (temp >= 256) {
				temp = temp % 256;
				temp = temp ^ 0x1b;
			}
			return temp ^ b;
		}
		System.out.println("----------------------INVALID ENTRY!!!!----------------------");
		return 0;
	}
}
