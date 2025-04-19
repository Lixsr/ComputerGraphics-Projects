import java.util.Random;

public class Metal extends Material {
  public Vector albedo;
  public double fuzz;

  public Metal(Vector albedo, double fuzz) {
    this.albedo = albedo;
    this.fuzz = (fuzz < 1) ? fuzz : 1.0;
  }

  @Override
  public boolean scatter(
      Ray incomingRay, HitRecord hitRecord, Vector attenuation, Ray scatteredRay, Random rand) {
    Vector unitDirection = incomingRay.direction.normalize();
    Vector perfectReflection = reflect(unitDirection, hitRecord.normal);

    scatteredRay.origin = hitRecord.p;
    scatteredRay.direction = perfectReflection.add(generateRandomInUnitSphere(rand).scale(fuzz));

    attenuation.x = albedo.x;
    attenuation.y = albedo.y;
    attenuation.z = albedo.z;

    return scatteredRay.direction.dot(hitRecord.normal) > 0;
  }

  private Vector reflect(Vector incident, Vector normal) {
    return incident.subtract(normal.scale(2 * incident.dot(normal)));
  }

  private Vector generateRandomInUnitSphere(Random rand) {
    while (true) {
      Vector randomVec = Vector.random(rand, -1, 1);
      if (randomVec.dot(randomVec) < 1) return randomVec;
    }
  }
}
