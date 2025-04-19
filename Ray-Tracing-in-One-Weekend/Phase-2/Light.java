public class Light {
  public String type;
  public double intensity;
  public Vector position, direction;

  public Light(String type, double intensity, Vector position, Vector direction) {
    this.type = type;
    this.intensity = intensity;
    this.position = position;
    this.direction = direction;
  }
}