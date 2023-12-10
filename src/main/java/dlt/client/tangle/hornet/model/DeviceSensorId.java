package dlt.client.tangle.hornet.model;

/**
 *
 * @author Allan Capistrano
 * @version 1.0.0
 */
public class DeviceSensorId {

  private final String deviceId;
  private final String sensorId;

  public DeviceSensorId(String deviceId, String sensorId) {
    this.deviceId = deviceId;
    this.sensorId = sensorId;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public String getSensorId() {
    return sensorId;
  }
}
