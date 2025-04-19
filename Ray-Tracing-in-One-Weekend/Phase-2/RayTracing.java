import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class RayTracing {
  final int WIDTH = 800;
  final int HEIGHT = 800;
  final double VIEWPORT_SIZE = 1;
  final double PROJECTION_PLANE_D = 1;
  final Color BACKGROUND_COLOR = Color.WHITE;

  Scene scene;

  public RayTracing() {
    scene = new Scene();
    scene.spheres.add(new Sphere(new double[] {0, -1, 3}, 1, new int[] {255, 0, 0}, 500));
    scene.spheres.add(new Sphere(new double[] {2, 0, 4}, 1, new int[] {0, 0, 255}, 500));
    scene.spheres.add(new Sphere(new double[] {-2, 0, 4}, 1, new int[] {0, 255, 0}, 10));
    scene.spheres.add(new Sphere(new double[] {0, -5001, 0}, 5000, new int[] {255, 255, 0}, 1000));

    scene.lights.add(new Light("ambient", 0.2, null, null));
    scene.lights.add(new Light("point", 0.6, new Vector(2, 1, 0), null));
    scene.lights.add(new Light("directional", 0.2, null, new Vector(1, 4, 4)));
  }

  public void render(String filename) {
    BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    final double viewportXScale = VIEWPORT_SIZE / WIDTH;
    final double viewportYScale = VIEWPORT_SIZE / HEIGHT;
    Vector camera = new Vector(0, 0, 0);

    for (int x = 0; x < WIDTH; x++) {
      double viewportX = (x - WIDTH / 2) * viewportXScale;
      for (int y = 0; y < HEIGHT; y++) {
        double viewportY = (HEIGHT / 2 - y - 1) * viewportYScale;
        Vector direction = new Vector(viewportX, viewportY, PROJECTION_PLANE_D).normalize();
        Color color = traceRay(camera, direction, 1, Double.POSITIVE_INFINITY);
        image.setRGB(x, y, color.getRGB());
      }
    }

    try {
      ImageIO.write(image, "png", new File(filename));
      System.out.println("Image saved as " + filename);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Color traceRay(Vector origin, Vector direction, double tMin, double tMax) {
    double closestT = Double.POSITIVE_INFINITY;
    Sphere closestSphere = null;

    for (Sphere sphere : scene.spheres) {
      double[] tValues = intersectRaySphere(origin, direction, sphere);
      if (tValues == null) continue;

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

    if (closestSphere == null) {
      return BACKGROUND_COLOR;
    }

    Vector point = origin.add(direction.multiply(closestT));
    Vector normal = point.subtract(closestSphere.center).normalize();
    Vector view = direction.multiply(-1);

    double lighting = computeLighting(point, normal, view, closestSphere.specular);
    int r = (int) (closestSphere.color.getRed() * lighting);
    int g = (int) (closestSphere.color.getGreen() * lighting);
    int b = (int) (closestSphere.color.getBlue() * lighting);

    return new Color(Math.min(r, 255), Math.min(g, 255), Math.min(b, 255));
  }

  private double computeLighting(Vector point, Vector normal, Vector view, double specular) {
    double intensity = 0.0;

    for (Light light : scene.lights) {
      Vector lightVector;
      if (light.type.equals("ambient")) {
        intensity += light.intensity;
        continue;
      } else if (light.type.equals("point")) {
        lightVector = light.position.subtract(point);
      } else {
        lightVector = light.direction;
      }

      double normalDotLight = normal.dot(lightVector);
      if (normalDotLight > 0) {
        intensity += light.intensity * normalDotLight / (normal.length() * lightVector.length());
      }

      if (specular != -1) {
        Vector reflection = normal.multiply(2 * normalDotLight).subtract(lightVector);
        double reflectionDotView = reflection.dot(view);
        if (reflectionDotView > 0) {
          intensity += light.intensity * Math.pow(reflectionDotView / (reflection.length() * view.length()), specular);
        }
      }
    }
    return intensity;
  }

  private double[] intersectRaySphere(Vector origin, Vector direction, Sphere sphere) {
    Vector originToCenter = origin.subtract(sphere.center);
    double directionDot = direction.dot(direction);
    double originToCenterDotDirection = originToCenter.dot(direction);
    double originToCenterDotSelf = originToCenter.dot(originToCenter);

    double quadA = directionDot;
    double quadB = 2 * originToCenterDotDirection;
    double quadC = originToCenterDotSelf - sphere.radius * sphere.radius;
    double discriminant = quadB * quadB - 4 * quadA * quadC;

    if (discriminant < 0) {
      return null;
    }

    double sqrtDiscriminant = Math.sqrt(discriminant);
    double invDoubleQuadA = 1 / (2 * quadA);
    double t1 = (-quadB + sqrtDiscriminant) * invDoubleQuadA;
    double t2 = (-quadB - sqrtDiscriminant) * invDoubleQuadA;
    return new double[] {t1, t2};
  }

  public static void main(String[] args) {
    RayTracing rt = new RayTracing();
    rt.render("Generated_Image.png");
  }
}