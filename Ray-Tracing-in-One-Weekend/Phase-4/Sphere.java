public class Sphere implements Hittable {
  public Vector center;
  public double radius;
  public Material mat;

  public Sphere(Vector center, double radius, Material m) {
    this.center = center;
    this.radius = radius;
    this.mat = m;
  }

  @Override
  public boolean hit(Ray r, double tMin, double tMax, HitRecord hitRecord) {
    Vector oc = r.origin.subtract(center);
    double a = r.direction.dot(r.direction);
    double halfB = oc.dot(r.direction);
    double c = oc.dot(oc) - radius * radius;
    double discriminant = halfB * halfB - a * c;
    if (discriminant < 0) return false;
    double sqrtDisc = Math.sqrt(discriminant);

    double root = (-halfB - sqrtDisc) / a;
    if (root < tMin || root > tMax) {
      root = (-halfB + sqrtDisc) / a;
      if (root < tMin || root > tMax) {
        return false;
      }
    }

    hitRecord.t = root;
    hitRecord.p = r.at(hitRecord.t);
    Vector outwardNormal = hitRecord.p.subtract(center).scale(1.0 / radius);
    hitRecord.setFaceNormal(r, outwardNormal);
    hitRecord.mat = mat;
    return true;
  }
}