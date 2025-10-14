package com.zqhy.app.utils.detector;

import android.content.Context;

public class ComprehensiveEmulatorDetector {
    public static boolean isEmulator(Context context) {
        return EmulatorDetector.isEmulator() || BatteryEmulatorDetector.isBatteryEmulator(context) || SensorEmulatorDetector.isSensorEmulator(context);
    }
}