package bitslicedAes;

import shared.Utility;

// A UNIVERSAL ASSUMPTION: all hex numbers in String are on two characters i.e between  and FF inclusive

public class BitslicedAES {
	private Integer[][] addRoundKey(Integer[][] currentState, Integer[][] roundKey) {
		System.out.println("-> Adding Round Key: ");
		// TODO: implement
		return currentState;
	}

	private Integer[][] subBytes(Integer[][] currentState) {
		System.out.println("-> Substituting Bytes: ");
		// TODO: implement
		return currentState;
	}

	private Integer[][] shiftRows(Integer[][] currentState) {
		System.out.println("-> Shifting Rows: ");
		// TODO: implement
		return currentState;
	}

	private Integer[][] mixColumns(Integer[][] currentState) {
		System.out.println("-> Mixing Columns: ");
		// TODO: implement
		return currentState; // TODO: implement
	}

	private Integer[][] performInitialRound(Integer[][] currentState) {
		System.out.println("\nINITIAL ROUND:");
		// TODO: implement
		return currentState;
	}

	private Integer[][] performMainRound(Integer[][] currentState, Integer roundNo) {
		System.out.println("\nMAIN ROUND " + roundNo.toString() + ":");
		// TODO: implement
		return currentState;
	}

	private Integer[][] performFinalRound(Integer[][] currentState) {
		System.out.println("\nFINAL ROUND:");
		// TODO: implement
		return currentState;
	}

	private Integer[][] transformBundle(Integer[][] currentState) {
		System.out.println("-> Transforming Bundle for Bitslicing: ");
		// TODO: implement
		return currentState;
	}

	private Integer[][] encrypt(Integer[][] state, Integer[][] cipherKey) {
		System.out.println("\nGENERATING ALL ROUND KEYS...");
		// TODO: implement
		return state;
	}

	public static void main(String[] args) {
		Integer[][] plainText = { { 0x54, 0x4f, 0x4e, 0x20 }, { 0x77, 0x6e, 0x69, 0x54 }, { 0x6F, 0x65, 0x6e, 0x77 },
				{ 0x20, 0x20, 0x65, 0x6f } };
		Integer[][] key = { { 0x54, 0x73, 0x20, 0x67 }, { 0x68, 0x20, 0x4b, 0x20 }, { 0x61, 0x6d, 0x75, 0x46 },
				{ 0x74, 0x79, 0x6e, 0x75 } };
		BitslicedAES bitslicedAes = new BitslicedAES();
		Utility.printArray(plainText);
		bitslicedAes.transformBundle(plainText);
		System.out.println();
		Utility.printArray(plainText);
	}
}
