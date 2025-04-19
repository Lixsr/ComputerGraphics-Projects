public interface Hittable {
    boolean hit(Ray ray, double tMin, double tMax, HitRecord hitRecord);
}