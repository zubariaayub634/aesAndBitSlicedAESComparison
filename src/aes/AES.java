package aes;

import shared.SharedInformation;
import shared.Utility;

// A UNIVERSAL ASSUMPTION: all hex numbers in String are on two characters i.e between  and FF inclusive

public class AES {

	Integer[][][] allRoundKeys;

	private Integer[][] addRoundKey(Integer[][] currentState, Integer[][] roundKey) {
		System.out.println("-> Adding Round Key: ");
		Integer[][] newState = currentState.clone();
		for (int i = 0; i < newState.length; i++) {
			for (int j = 0; j < newState[i].length; j++) {
				newState[i][j] = newState[i][j] ^ roundKey[i][j];
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
				temp[i][j] = SharedInformation.getSubByte(currentState[i][j]);
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
				Utility.leftShift(newState[i]);
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
					newState[i][j] = Utility.multiplyInGaloisField(fixedMatrix[i][k], currentState[k][j]) ^ newState[i][j];
				}
			}
		}
		Utility.printArray(newState);
		return newState;
	}

	private Integer[][] getRoundKey(Integer roundNo) {
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
		allRoundKeys = SharedInformation.generateAllRoundKeys(cipherKey);
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
