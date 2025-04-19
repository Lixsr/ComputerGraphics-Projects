import java.util.Random;

public class Dielectric extends Material {
  public double indexOfRefraction;

  public Dielectric(double indexOfRefraction) {
    this.indexOfRefraction = indexOfRefraction;
  }

  @Override
  public boolean scatter(
      Ray incomingRay, HitRecord hitRecord, Vector attenuation, Ray scatteredRay, Random rand) {
    attenuation.x = 1.0;
    attenuation.y = 1.0;
    attenuation.z = 1.0;
    double refractionRatio = hitRecord.frontFace ? (1.0 / indexOfRefraction) : indexOfRefraction;
    Vector unitDirection = incomingRay.direction.normalize();
    double cosTheta = Math.min(unitDirection.negate().dot(hitRecord.normal), 1.0);
    double sinTheta = Math.sqrt(1.0 - cosTheta * cosTheta);

    boolean mustReflect = refractionRatio * sinTheta > 1.0;
    Vector scatterDirection;

    if (mustReflect || reflectance(cosTheta, refractionRatio) > rand.nextDouble()) {
      scatterDirection = reflect(unitDirection, hitRecord.normal);
    } else {
      scatterDirection = refract(unitDirection, hitRecord.normal, refractionRatio);
    }

    scatteredRay.origin = hitRecord.p;
    scatteredRay.direction = scatterDirection;
    return true;
  }

  private Vector reflect(Vector incident, Vector normal) {
    return incident.subtract(normal.scale(2 * incident.dot(normal)));
  }

  private Vector refract(Vector unitIncident, Vector normal, double etaRatio) {
    double cosTheta = Math.min(unitIncident.negate().dot(normal), 1.0);
    Vector perpendicular = unitIncident.add(normal.scale(cosTheta)).scale(etaRatio);
    Vector parallel = normal.scale(-Math.sqrt(Math.abs(1.0 - perpendicular.dot(perpendicular))));
    return perpendicular.add(parallel);
  }

  private double reflectance(double cosine, double refractionIndex) {
    double r0 = (1 - refractionIndex) / (1 + refractionIndex);
    r0 = r0 * r0;
    return r0 + (1 - r0) * Math.pow((1 - cosine), 5);
  }
}
