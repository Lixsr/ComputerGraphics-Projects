import java.util.Random;

public class Vector {
  public double x, y, z;

  public Vector(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Vector() {
    this(0, 0, 0);
  }

  public Vector add(Vector v) {
    return new Vector(x + v.x, y + v.y, z + v.z);
  }

  public Vector subtract(Vector v) {
    return new Vector(x - v.x, y - v.y, z - v.z);
  }

  public Vector multiply(Vector v) {
    return new Vector(x * v.x, y * v.y, z * v.z);
  }

  public Vector multiply(double scalar) {
    return new Vector(x * scalar, y * scalar, z * scalar);
  }

  public double dot(Vector v) {
    return x * v.x + y * v.y + z * v.z;
  }

  public Vector cross(Vector v) {
    return new Vector(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
  }

  public double length() {
    return Math.sqrt(x * x + y * y + z * z);
  }

  public Vector normalize() {
    double len = length();
    return new Vector(x / len, y / len, z / len);
  }

  public Vector negate() {
    return new Vector(-x, -y, -z);
  }
  public Vector scale(double t) {
    return new Vector(x * t, y * t, z * t);
  }
  public static Vector random(Random rng) {
    return new Vector(rng.nextDouble(), rng.nextDouble(), rng.nextDouble());
  }

  public static Vector random(Random rng, double min, double max) {
    return new Vector(
        min + (max - min) * rng.nextDouble(),
        min + (max - min) * rng.nextDouble(),
        min + (max - min) * rng.nextDouble());
  }
}
