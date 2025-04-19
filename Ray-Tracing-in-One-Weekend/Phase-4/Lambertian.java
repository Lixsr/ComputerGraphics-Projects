import java.util.Random;

public class Lambertian extends Material {
  public Vector albedo;

  public Lambertian(Vector albedo) {
    this.albedo = albedo;
  }

  @Override
  public boolean scatter(
      Ray incomingRay, HitRecord hitRecord, Vector attenuation, Ray scatteredRay, Random rand) {
    Vector scatterDirection = hitRecord.normal.add(generateRandomUnitVector(rand));

    if (isNearZero(scatterDirection)) {
      scatterDirection = hitRecord.normal;
    }

    scatteredRay.origin = hitRecord.p;
    scatteredRay.direction = scatterDirection;

    attenuation.x = albedo.x;
    attenuation.y = albedo.y;
    attenuation.z = albedo.z;

    return true;
  }

  private Vector generateRandomUnitVector(Random rand) {
    double azimuth = 2.0 * Math.PI * rand.nextDouble();
    double z = -1 + 2 * rand.nextDouble();
    double radius = Math.sqrt(1 - z * z);
    return new Vector(radius * Math.cos(azimuth), radius * Math.sin(azimuth), z);
  }

  private boolean isNearZero(Vector vector) {
    final double threshold = 1e-8;
    return (Math.abs(vector.x) < threshold)
        && (Math.abs(vector.y) < threshold)
        && (Math.abs(vector.z) < threshold);
  }
}
