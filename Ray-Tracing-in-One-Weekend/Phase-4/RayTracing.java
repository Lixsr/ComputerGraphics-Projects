import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class RayTracing {
  final int WIDTH = 800;
  final int HEIGHT = 800;
  final double VIEWPORT_SIZE = 1;
  final double PROJECTION_PLANE_D = 1;
  final int SAMPLES_PER_PIXEL = 100;
  final int MAX_DEPTH = 50;
  double FOCUS_DIST = 10.0;
  double APERTURE = 0.1;
  Scene scene;
  HittableList world;

  Vector LOOK_FROM = new Vector(13, 2, 3);
  Vector LOOK_AT = new Vector(0, 0, 0);
  Vector VUP = new Vector(0, 1, 0);
  double FOV = 20.0;
  double ASPECT_RATIO = (double) WIDTH / HEIGHT;
  Camera camera = new Camera(LOOK_FROM, LOOK_AT, VUP, FOV, ASPECT_RATIO, APERTURE, FOCUS_DIST);

  public RayTracing() {
    scene = new Scene();
    world = generateScene();
    scene.lights.add(new Light("ambient", 0.2, null, null));
    scene.lights.add(new Light("point", 0.6, new Vector(2, 1, 0), null));
    scene.lights.add(new Light("directional", 0.2, null, new Vector(1, 4, 4)));
  }

  private static HittableList generateScene() {
    HittableList world = new HittableList();
    Random rand = new Random();

    // Ground sphere
    Material groundMat = new Lambertian(new Vector(0.5, 0.5, 0.5));
    world.add(new Sphere(new Vector(0, -1000, 0), 1000, groundMat));

    // Many random small spheres
    for (int a = -11; a < 11; a++) {
      for (int b = -11; b < 11; b++) {
        double chooseMat = rand.nextDouble();
        Vector center = new Vector(a + 0.9 * rand.nextDouble(), 0.2, b + 0.9 * rand.nextDouble());
        // Avoid placing too close to big spheres below
        if ((center.subtract(new Vector(4, 0.2, 0))).length() > 0.9) {
          Material sphereMat;
          if (chooseMat < 0.8) {
            // diffuse
            Vector albedo = Vector.random(rand).multiply(Vector.random(rand));
            sphereMat = new Lambertian(albedo);
            world.add(new Sphere(center, 0.2, sphereMat));
          } else if (chooseMat < 0.95) {
            // metal
            Vector albedo = Vector.random(rand, 0.5, 1.0);
            double fuzz = rand.nextDouble() * 0.5;
            sphereMat = new Metal(albedo, fuzz);
            world.add(new Sphere(center, 0.2, sphereMat));
          } else {
            sphereMat = new Dielectric(1.5);
            world.add(new Sphere(center, 0.2, sphereMat));
          }
        }
      }
    }

    world.add(new Sphere(new Vector(0, 1, 0), 1.0, new Dielectric(1.5)));
    world.add(new Sphere(new Vector(-4, 1, 0), 1.0, new Lambertian(new Vector(0.4, 0.2, 0.1))));
    world.add(new Sphere(new Vector(4, 1, 0), 1.0, new Metal(new Vector(0.7, 0.6, 0.5), 0.0)));

    return world;
  }

  private Vector randomVector(Random rand) {
    return new Vector(rand.nextDouble(), rand.nextDouble(), rand.nextDouble());
  }

  private Vector randomVector(Random rand, double min, double max) {
    double range = max - min;
    return new Vector(
        min + rand.nextDouble() * range,
        min + rand.nextDouble() * range,
        min + rand.nextDouble() * range);
  }

  private Vector multiplyVectors(Vector a, Vector b) {
    return new Vector(a.x * b.x, a.y * b.y, a.z * b.z);
  }

  public void render(String filename) {
    BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    Random rand = new Random();
    final double invWidthMinusOne = 1.0 / (WIDTH - 1);
    final double invHeightMinusOne = 1.0 / (HEIGHT - 1);

    for (int j = 0; j < HEIGHT; j++) {
      int row = HEIGHT - 1 - j;
      if (j % 50 == 0) {
        System.out.printf("Remaining: %d\n", HEIGHT - 1 - j);
      }
      for (int i = 0; i < WIDTH; i++) {
        double r = 0.0, g = 0.0, b = 0.0;

        for (int s = 0; s < SAMPLES_PER_PIXEL; s++) {
          double u = (i + rand.nextDouble()) * invWidthMinusOne;
          double v = (j + rand.nextDouble()) * invHeightMinusOne;
          Ray rRay = camera.getRay(u, v, rand);
          Vector sampleColor = rayColor(rRay, world, MAX_DEPTH, rand);
          r += sampleColor.x;
          g += sampleColor.y;
          b += sampleColor.z;
        }

        Vector pixelColor =
            new Vector(r / SAMPLES_PER_PIXEL, g / SAMPLES_PER_PIXEL, b / SAMPLES_PER_PIXEL);
        pixelColor =
            new Vector(Math.sqrt(pixelColor.x), Math.sqrt(pixelColor.y), Math.sqrt(pixelColor.z));

        int ir = (int) (255.999 * clamp(pixelColor.x, 0.0, 1.0));
        int ig = (int) (255.999 * clamp(pixelColor.y, 0.0, 1.0));
        int ib = (int) (255.999 * clamp(pixelColor.z, 0.0, 1.0));
        int rgb = (ir << 16) | (ig << 8) | (ib);
        image.setRGB(i, row, rgb);
      }
    }

    try {
      ImageIO.write(image, "png", new File(filename));
      System.out.println("Image saved as " + filename);
    } catch (IOException e) {
      e.printStackTrace();
    }
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
          intensity +=
              light.intensity
                  * Math.pow(reflectionDotView / (reflection.length() * view.length()), specular);
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

  private double clamp(double x, double min, double max) {
    if (x < min) return min;
    if (x > max) return max;
    return x;
  }

  private static Vector rayColor(Ray ray, Hittable world, int depth, Random rand) {
    if (depth <= 0) {
      return new Vector(0, 0, 0);
    }

    HitRecord hitRecord = new HitRecord();
    if (world.hit(ray, 0.001, Double.POSITIVE_INFINITY, hitRecord)) {
      Ray scattered = new Ray();
      Vector attenuation = new Vector();
      if (hitRecord.mat.scatter(ray, hitRecord, attenuation, scattered, rand)) {
        return attenuation.multiply(rayColor(scattered, world, depth - 1, rand));
      }
      return new Vector(0, 0, 0);
    }

    Vector unitDir = ray.direction.normalize();
    double t = 0.5 * (unitDir.y + 1.0);
    return new Vector(1.0, 1.0, 1.0).scale(1.0 - t).add(new Vector(0.5, 0.7, 1.0).scale(t));
  }

  public static void main(String[] args) {
    RayTracing rt = new RayTracing();
    rt.render("Generated_Image.png");
  }
}