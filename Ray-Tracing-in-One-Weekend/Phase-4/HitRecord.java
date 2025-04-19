public class HitRecord {
  public Vector p, normal;
  public Material mat;
  public double t;
  public boolean frontFace;

  public void setFaceNormal(Ray ray, Vector normal) {
    this.frontFace = ray.direction.dot(normal) < 0;
    this.normal = frontFace ? normal : normal.scale(-1);
  }
}