package net.softtap.acceleratorlogger;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity implements SensorEventListener{
  
  private SensorManager sensorManager;
  private Sensor sensor;
  
  private float alpha = 0.8f;
  private float[] gravity = {0, 0, 0};
  private float[] linear_acceleration = {0, 0, 0};
  
  @Override
  protected void onPause() {
    super.onPause();
    sensorManager.unregisterListener(this, sensor);
  }

  @Override
  protected void onResume() {
    super.onResume();
    sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
  }

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);
      
      sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
      sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
      sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onSensorChanged(SensorEvent event) {
    // Isolate the force of gravity with the low-pass filter.
    gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
    gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
    gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

    // Remove the gravity contribution with the high-pass filter.
    linear_acceleration[0] = event.values[0] - gravity[0];
    linear_acceleration[1] = event.values[1] - gravity[1];
    linear_acceleration[2] = event.values[2] - gravity[2];
    
    Log.i("Accelerometer App:", linear_acceleration[0]+","+linear_acceleration[1]+","+linear_acceleration[2]);
  }
}
