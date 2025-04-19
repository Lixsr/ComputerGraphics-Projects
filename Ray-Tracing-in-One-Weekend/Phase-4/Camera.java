import java.util.Random;

public class Camera {
  private Vector origin, LLCorner, horizontal, vertical, u, v, w;
  private double lensRadius;
  private final Random rand = new Random();

  public Camera(
      Vector lookFrom,
      Vector lookAt,
      Vector vup,
      double verticalFovDegrees,
      double aspectRatio,
      double aperture,
      double focusDistance) {

    double theta = Math.toRadians(verticalFovDegrees);
    double halfHeight = Math.tan(theta / 2);
    double viewportHeight = 2.0 * halfHeight;
    double viewportWidth = aspectRatio * viewportHeight;

    w = (lookFrom.subtract(lookAt)).normalize();
    u = (vup.cross(w)).normalize();
    v = w.cross(u);

    origin = lookFrom;
    horizontal = u.scale(focusDistance * viewportWidth);
    vertical = v.scale(focusDistance * viewportHeight);
    LLCorner =
        origin
            .subtract(horizontal.scale(0.5))
            .subtract(vertical.scale(0.5))
            .subtract(w.scale(focusDistance));

    lensRadius = aperture / 2;
  }

  public Ray getRay(double s, double t, Random rand) {
    Vector randomDiskOffset = randomInUnitDisk(rand).scale(lensRadius);
    Vector lensOffset = u.scale(randomDiskOffset.x).add(v.scale(randomDiskOffset.y));

    Vector rayDirection =
        LLCorner.add(horizontal.scale(s))
            .add(vertical.scale(t))
            .subtract(origin)
            .subtract(lensOffset);
    return new Ray(origin.add(lensOffset), rayDirection);
  }

  private Vector randomInUnitDisk(Random rand) {
    while (true) {
      double x = 2.0 * rand.nextDouble() - 1.0;
      double y = 2.0 * rand.nextDouble() - 1.0;
      if (x * x + y * y < 1.0) {
        return new Vector(x, y, 0);
      }
    }
  }
}
