public class Sphere {
  public final double[] center;
  public final double radius;
  public final int[] color;

  public Sphere(double[] center, double radius, int[] color) {
    if (center == null || center.length != 3) {
      throw new IllegalArgumentException("Center must be a 3D vector");
    }
    if (radius <= 0) {
      throw new IllegalArgumentException("Radius must be positive");
    }
    if (color == null || color.length != 3) {
      throw new IllegalArgumentException("Color must be an RGB array");
    }
    this.center = center.clone();
    this.radius = radius;
    this.color = color.clone();
  }
}