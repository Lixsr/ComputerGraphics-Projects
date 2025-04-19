public class HittableList implements Hittable {
  private final java.util.List<Hittable> objects = new java.util.ArrayList<>();

  public void add(Hittable obj) {
    objects.add(obj);
  }

  @Override
  public boolean hit(Ray ray, double tMin, double tMax, HitRecord hitRecord) {
    HitRecord tempRec = new HitRecord();
    boolean hitAnything = false;
    double closestSoFar = tMax;

    for (Hittable obj : objects) {
      if (obj.hit(ray, tMin, closestSoFar, tempRec)) {
        hitAnything = true;
        closestSoFar = tempRec.t;
        hitRecord.p = tempRec.p;
        hitRecord.normal = tempRec.normal;
        hitRecord.mat = tempRec.mat;
        hitRecord.t = tempRec.t;
        hitRecord.frontFace = tempRec.frontFace;
      }
    }
    return hitAnything;
  }
}