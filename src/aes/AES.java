package aes;

import shared.SharedInformation;
import shared.Utility;

// A UNIVERSAL ASSUMPTION: all hex numbers in String are on two characters i.e between  and FF inclusive

public class AES {

	private static Integer[][] sBox = {
			{ 0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76 },
			{ 0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0 },
			{ 0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC, 0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15 },
			{ 0x04, 0xC7, 0x23, 0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75 },
			{ 0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84 },
			{ 0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58, 0xCF },
			{ 0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8 },
			{ 0x51, 0xA3, 0x40, 0x8F, 0x92, 0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2 },
			{ 0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19, 0x73 },
			{ 0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB },
			{ 0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79 },
			{ 0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E, 0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08 },
			{ 0xBA, 0x78, 0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B, 0xBD, 0x8B, 0x8A },
			{ 0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E, 0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E },
			{ 0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF },
			{ 0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16 } };

	private static Integer[] roundConstants = { 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36 };

	Integer[][][] allRoundKeys = new Integer[SharedInformation.aesRounds + 1][SharedInformation.n][SharedInformation.n];

	private void generateAllRoundKeys(Integer[][] round0Key) {
		allRoundKeys[0] = round0Key.clone();
		for (int i = 1; i <= SharedInformation.aesRounds; i++) {
			Integer[] gw3 = allRoundKeys[i - 1][SharedInformation.n - 1].clone();
			leftShift(gw3);
			for (int j = 0; j < gw3.length; j++) {
				gw3[j] = getSubByte(gw3[j]);
			}
			gw3[0] = bitwiseXOR(gw3[0], roundConstants[i - 1]);
			Integer[] w4 = gw3;
			for (int j = 0; j < w4.length; j++) {
				w4[j] = bitwiseXOR(w4[j], allRoundKeys[i - 1][0][j]);
			}
			allRoundKeys[i][0] = w4.clone();
			for (int j = 0; j < w4.length; j++) {// w5
				w4[j] = bitwiseXOR(w4[j], allRoundKeys[i - 1][1][j]);
			}
			allRoundKeys[i][1] = w4.clone();
			for (int j = 0; j < w4.length; j++) {// w6
				w4[j] = bitwiseXOR(w4[j], allRoundKeys[i - 1][2][j]);
			}
			allRoundKeys[i][2] = w4.clone();
			for (int j = 0; j < w4.length; j++) {// w7
				w4[j] = bitwiseXOR(w4[j], allRoundKeys[i - 1][3][j]);
			}
			allRoundKeys[i][3] = w4.clone();
		}
	}

	private Integer bitwiseXOR(Integer hex1, Integer hex2) {
		return hex1 ^ hex2;
	}

	// Multiplies two bytes in garlois field 2^8
	// a must either be 1 or 2 or 3
	private Integer multiplyInGaloisField(Integer a, Integer b) {
		if (a == 1) {
			return b;
		}
		if (a == 2) {
			Integer temp = b << 1;
			if (temp >= 256) {
				temp = temp % 256;
				temp = bitwiseXOR(temp, 0x1b);
			}
			return temp;
		}
		if (a == 3) {
			Integer temp = b << 1;
			if (temp >= 256) {
				temp = temp % 256;
				temp = bitwiseXOR(temp, 0x1b);
			}
			return bitwiseXOR(temp, b);
		}
		System.out.println("----------------------INVALID ENTRY!!!!----------------------");
		return 0;
	}

	private void leftShift(Integer[] array)// one left shift for now
	{
		Integer temp = array[0];
		for (int i = 0; i < array.length - 1; i++) {
			array[i] = array[i + 1];
		}
		array[array.length - 1] = temp;
	}

	private Integer getSubByte(Integer prevByte) {
		Integer row = prevByte / 16;
		Integer col = prevByte % 16;
		return sBox[row][col];
	}

	private Integer[][] addRoundKey(Integer[][] currentState, Integer[][] roundKey) {
		System.out.println("-> Adding Round Key: ");
		Integer[][] newState = currentState.clone();
		for (int i = 0; i < newState.length; i++) {
			for (int j = 0; j < newState[i].length; j++) {
				newState[i][j] = bitwiseXOR(newState[i][j], roundKey[i][j]);
			}
		}
		Utility.printArray(newState);
		return newState;
	}

	private Integer[][] subBytes(Integer[][] currentState) {
		System.out.println("-> Substituting Bytes: ");
		Integer[][] temp = currentState.clone();
		for (int i = 0; i < SharedInformation.n; i++) {
			for (int j = 0; j < SharedInformation.n; j++) {
				temp[i][j] = getSubByte(currentState[i][j]);
			}
		}
		Utility.printArray(temp);
		return temp;
	}

	private Integer[][] shiftRows(Integer[][] currentState) {
		System.out.println("-> Shifting Rows: ");
		Integer[][] newState = currentState.clone();
		for (int i = 1; i < SharedInformation.n; i++) {
			for (int j = 0; j < i; j++) {
				leftShift(newState[i]);
			}
		}
		Utility.printArray(newState);
		return newState;
	}

	private Integer[][] mixColumns(Integer[][] currentState) {
		System.out.println("-> Mixing Columns: ");
		Integer[][] fixedMatrix = { { 2, 3, 1, 1 }, { 1, 2, 3, 1 }, { 1, 1, 2, 3 }, { 3, 1, 1, 2 } };
		Integer[][] newState = new Integer[4][4];
		for (int i = 0; i < newState.length; i++) {
			for (int j = 0; j < newState[i].length; j++) {
				newState[i][j] = 0;
				for (int k = 0; k < newState.length; k++) {
					newState[i][j] = bitwiseXOR(multiplyInGaloisField(fixedMatrix[i][k], currentState[k][j]),
							newState[i][j]);
				}
			}
		}
		Utility.printArray(newState);
		return newState;
	}

	private Integer[][] getRoundKey(int roundNo) {
		return allRoundKeys[roundNo];
	}

	private Integer[][] performInitialRound(Integer[][] currentState) {
		System.out.println("\nINITIAL ROUND:");
		return addRoundKey(currentState, getRoundKey(0));
	}

	private Integer[][] performMainRound(Integer[][] currentState, Integer roundNo) {
		System.out.println("\nMAIN ROUND " + roundNo.toString() + ":");
		Integer[][] temp = subBytes(currentState);
		temp = shiftRows(temp);
		temp = mixColumns(temp);
		temp = addRoundKey(temp, getRoundKey(roundNo));
		return temp;
	}

	private Integer[][] performFinalRound(Integer[][] currentState) {
		System.out.println("\nFINAL ROUND:");
		Integer[][] temp = subBytes(currentState);
		temp = shiftRows(temp);
		temp = addRoundKey(temp, getRoundKey(SharedInformation.aesRounds));
		return temp;
	}

	private Integer[][] encrypt(Integer[][] state, Integer[][] cipherKey) {
		System.out.println("\nGENERATING ALL ROUND KEYS...");
		generateAllRoundKeys(cipherKey);
		Integer[][] temp = performInitialRound(state);
		for (int i = 1; i < SharedInformation.aesRounds; i++) {
			temp = performMainRound(temp, i);
		}
		return performFinalRound(temp);
	}

	public static void main(String[] args) {
		Integer[][] plainText = { { 0x54, 0x4f, 0x4e, 0x20 }, { 0x77, 0x6e, 0x69, 0x54 }, { 0x6F, 0x65, 0x6e, 0x77 },
				{ 0x20, 0x20, 0x65, 0x6f } };
		Integer[][] key = { { 0x54, 0x73, 0x20, 0x67 }, { 0x68, 0x20, 0x4b, 0x20 }, { 0x61, 0x6d, 0x75, 0x46 },
				{ 0x74, 0x79, 0x6e, 0x75 } };
		AES aes = new AES();
		Integer[][] cipherText = aes.encrypt(plainText, key);
		System.out.println("\nCIPHER TEXT:");
		Utility.printArray(cipherText);
	}
}
