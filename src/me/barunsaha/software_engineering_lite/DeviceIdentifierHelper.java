package me.barunsaha.software_engineering_lite;

import android.os.Build;

/*
 * Indicate if the app is running inside an emulator or a real device 
 */

public class DeviceIdentifierHelper {

	private static boolean isEmulator;
	private static final String S_EMULATOR = "generic";

	protected static boolean isEmulator() {
		//Log.i(MainActivity.TAG, Build.BRAND);
		if (Build.BRAND.length() >= S_EMULATOR.length() && 
				Build.BRAND.substring(0, S_EMULATOR.length())
				.equalsIgnoreCase(S_EMULATOR)) {
			isEmulator = true;
		} else {
			isEmulator = false;
		}
		
		return isEmulator;
	}
}
