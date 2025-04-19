import java.util.Random;

public abstract class Material {
    public abstract boolean scatter(Ray incomingRay, HitRecord hitRecord, Vector attenuation, Ray scatteredRay, Random rand);
}

