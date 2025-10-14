package com.zqhy.app.utils.detector;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.concurrent.atomic.AtomicBoolean;

public class SensorEmulatorDetector {
    public static boolean isSensorEmulator(Context context) {
        final AtomicBoolean isEmulator = new AtomicBoolean(false);
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEventListener listener = new SensorEventListener() {
            private int count = 0;
            private float lastX = 0, lastY = 0, lastZ = 0;

            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                if (count > 0) {
                    if (Math.abs(x - lastX) < 0.1 && Math.abs(y - lastY) < 0.1 && Math.abs(z - lastZ) < 0.1) {
                        isEmulator.set(true);
                    }
                }
                lastX = x;
                lastY = y;
                lastZ = z;
                count++;
                sensorManager.unregisterListener(this);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        sensorManager.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        return isEmulator.get();
    }
}
