public class Vector {
  public double x, y, z;

  public Vector(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Vector subtract(Vector v) {
    return new Vector(x - v.x, y - v.y, z - v.z);
  }

  public Vector add(Vector v) {
    return new Vector(x + v.x, y + v.y, z + v.z);
  }

  public Vector multiply(double scalar) {
    return new Vector(x * scalar, y * scalar, z * scalar);
  }

  public double dot(Vector v) {
    return x * v.x + y * v.y + z * v.z;
  }

  public double length() {
    return Math.sqrt(x * x + y * y + z * z);
  }

  public Vector normalize() {
    double length = length();
    return new Vector(x / length, y / length, z / length);
  }
}