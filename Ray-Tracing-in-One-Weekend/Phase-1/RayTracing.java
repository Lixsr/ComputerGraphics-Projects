import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class RayTracing {
  final int WIDTH = 800;
  final int HEIGHT = 800;
  final double VIEWPORT_SIZE = 1;
  final double PROJECTION_PLANE_D = 1;
  final int[] BACKGROUND_COLOR = {255, 255, 255};

  Sphere[] scene;

  public RayTracing() {
    scene =
        new Sphere[] {
          new Sphere(new double[] {0, -1, 3}, 1, new int[] {255, 0, 0}),
          new Sphere(new double[] {2, 0, 4}, 1, new int[] {0, 0, 255}),
          new Sphere(new double[] {-2, 0, 4}, 1, new int[] {0, 255, 0})
        };
  }

  public void render(String filename) {
    BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    
    final double viewportXScale = VIEWPORT_SIZE / WIDTH;
    final double viewportYScale = VIEWPORT_SIZE / HEIGHT;
    
    for (int x = 0; x < WIDTH; x++) {
      double viewportX = (x - WIDTH / 2) * viewportXScale;
      for (int y = 0; y < HEIGHT; y++) {
        double viewportY = (HEIGHT / 2 - y - 1) * viewportYScale;
        double[] direction = {viewportX, viewportY, PROJECTION_PLANE_D};
        int[] color = traceRay(new double[] {0, 0, 0}, direction, 1, Double.POSITIVE_INFINITY);
        image.setRGB(x, y, (color[0] << 16) | (color[1] << 8) | color[2]);
      }
    }

    try {
      ImageIO.write(image, "png", new File(filename));
      System.out.println("Image saved as " + filename);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private int[] traceRay(double[] origin, double[] direction, double tMin, double tMax) {
    double closestT = Double.POSITIVE_INFINITY;
    Sphere closestSphere = null;

    for (Sphere sphere : scene) {
      double[] tValues = intersectRaySphere(origin, direction, sphere);
      double t0 = tValues[0], t1 = tValues[1];
      if (t0 >= tMin && t0 <= tMax && t0 < closestT) {
        closestT = t0;
        closestSphere = sphere;
      }
      if (t1 >= tMin && t1 <= tMax && t1 < closestT) {
        closestT = t1;
        closestSphere = sphere;
      }
    }

    return closestSphere == null ? BACKGROUND_COLOR : closestSphere.color;
  }

  private double[] intersectRaySphere(double[] origin, double[] direction, Sphere sphere) {
    double[] originToCenter = {
        origin[0] - sphere.center[0],
        origin[1] - sphere.center[1],
        origin[2] - sphere.center[2]
    };
    
    double directionDot = direction[0] * direction[0] + direction[1] * direction[1] + direction[2] * direction[2];
    double originToCenterDotDirection = 
        originToCenter[0] * direction[0] + originToCenter[1] * direction[1] + originToCenter[2] * direction[2];
    double originToCenterDotSelf = 
        originToCenter[0] * originToCenter[0] + originToCenter[1] * originToCenter[1] + originToCenter[2] * originToCenter[2];
    
    double quadA = directionDot;
    double quadB = 2 * originToCenterDotDirection;
    double quadC = originToCenterDotSelf - sphere.radius * sphere.radius;
    double discriminant = quadB * quadB - 4 * quadA * quadC;

    if (discriminant < 0) {
      return new double[] {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY};
    }

    double sqrtDiscriminant = Math.sqrt(discriminant);
    double invDoubleQuadA = 1 / (2 * quadA);
    double t1 = (-quadB + sqrtDiscriminant) * invDoubleQuadA;
    double t2 = (-quadB - sqrtDiscriminant) * invDoubleQuadA;
    return new double[] {t1, t2};
  }

  private double dot(double[] a, double[] b) {
    return a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
  }

  private double[] subtract(double[] a, double[] b) {
    return new double[] {a[0] - b[0], a[1] - b[1], a[2] - b[2]};
  }

  public static void main(String[] args) {
    RayTracing rt = new RayTracing();
    rt.render("Generated_Image.png");
  }
}