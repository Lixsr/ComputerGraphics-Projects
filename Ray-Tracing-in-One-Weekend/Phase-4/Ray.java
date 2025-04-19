public class Ray {
  public Vector origin, direction;

  public Ray() {
    origin = new Vector(0, 0, 0);
    direction = new Vector(0, 0, 0);
  }

  public Ray(Vector origin, Vector direction) {
    this.origin = origin;
    this.direction = direction;
  }

  public Vector at(double t) {
    return origin.add(direction.scale(t));
  }
}