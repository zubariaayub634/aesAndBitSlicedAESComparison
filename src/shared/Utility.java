package shared;

public class Utility {
	public static void printArray(Integer[] array) {
		for (int j = 0; j < array.length; j++) {
			if (array[j]<16)
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
}
