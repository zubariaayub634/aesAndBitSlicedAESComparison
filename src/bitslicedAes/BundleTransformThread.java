package bitslicedAes;

import shared.SharedInformation;

public class BundleTransformThread extends Thread {

	Integer[][] bundle;
	Integer[][][] bundles;
	boolean transformComplete = false;

	BundleTransformThread(Integer[][] rawBundle) {
		this.bundle = rawBundle.clone();
		//System.out.println("BundleTransformThread started...");
		start();
	}

	private Integer[][] transformBundle(Integer[][] currentState) {
		Integer[][] newState = new Integer[SharedInformation.n * 2][SharedInformation.n / 2];
		int currentStateRow = 0;
		for (int i = SharedInformation.n / 2 - 1; i >= 0; i--) // column of newState being accessed
		{
			int j = 0; // row of newState being accessed
			// even index row of currentState being accessed
			for (int k = SharedInformation.n - 1; k >= 0; k--) {
				newState[j][i] = currentState[currentStateRow][k];
				j++;
			}

			currentStateRow++;

			// odd index row of currentState being accessed
			for (int k = SharedInformation.n - 1; k >= 0; k--) {
				newState[j][i] = currentState[currentStateRow][k];
				j++;
			}

			currentStateRow++;
		}
		return newState;
	}

	public void run() {
		bundle = transformBundle(bundle);
		int noOfBundles = SharedInformation.n / 4;
		int newBundleSize = SharedInformation.n / 2;
		bundles = new Integer[noOfBundles][newBundleSize][newBundleSize];
		for (int i = 0; i < noOfBundles; i++) {
			for (int j = 0; j < newBundleSize; j++) {
				for (int k = 0; k < newBundleSize; k++) {
					bundles[i][j][k] = bundle[i * newBundleSize + j][k];
				}
			}
		}
		transformComplete = true;
	}
}
