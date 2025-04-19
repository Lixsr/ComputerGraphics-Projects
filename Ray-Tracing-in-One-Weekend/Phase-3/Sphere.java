import java.awt.Color;
public class Sphere {
  public Vector center;
  public double radius, specular, reflective;
  public Color color;

  public Sphere(double[] center, double radius, int[] color, double specular, double reflective) {
    this.center = new Vector(center[0], center[1], center[2]);
    this.radius = radius;
    this.color = new Color(color[0], color[1], color[2]);
    this.specular = specular;
    this.reflective = reflective;
  }
}