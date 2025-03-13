# Ray Tracer in Java

This is a simple ray tracing program written in Java. It renders a scene with three spheres (red, green, and blue) and a large yellow sphere as a "floor" using advanced ray tracing techniques. The program uses Java's `javax.swing` library to display the rendered image in a window.

---

## Features

- **Sphere Rendering**: Renders a 2D projection of a 3D scene with spheres.
- **Ray-Sphere Intersection**: Uses mathematical formulas to compute intersections between rays and spheres.
- **Lighting**:
  - **Ambient Lighting**: Provides uniform illumination across the scene.
  - **Point Lighting**: Simulates light emanating from a specific point in 3D space, casting shadows and highlights.
  - **Directional Lighting**: Models light from a distant source (e.g., the sun) with parallel rays.
  - **Diffuse Reflection**: Calculates realistic shading based on the angle between light and surface normals.
  - **Specular Reflection**: Adds shiny highlights based on the view direction and light reflection.
- **Shadows**: Enhances realism by simulating the occlusion of light from point and directional sources. Objects cast shadows onto other surfaces when they block light paths, computed using ray intersection tests to determine if light reaches a point.
- **Reflections**: Simulates mirror-like reflections on sphere surfaces using recursive ray tracing, blending local and reflected colors based on reflectivity.
- **Interactive Display**: Displays the rendered image in a window using Java's `JFrame`.

---

## Prerequisites

To run this program, you need:

- **Java Development Kit (JDK)**: Ensure you have JDK 8 or later installed.

---

## How to Run the Program

### 1. Clone or Download the Code

- Clone this repository or download the `Ray-Tracing` folder to your local machine.

### 2. Run the Program on Different Platforms

#### Step 1: Open a Terminal or Command Prompt

- **Windows**: Open Command Prompt or PowerShell by searching for it in the Start menu.
- **Mac**: Open the Terminal application (found in Applications > Utilities).
- **Linux**: Open a terminal using your preferred method.

#### Step 2: Navigate to the Program Directory

Use the `cd` command to navigate to the directory containing the `RayTracer.java` file. For example:

```bash
cd path/to/your/directory
```

#### Step 3: Compile and Run the Program

Compile and run the program using the following commands:

```bash
javac RayTracer.java
java RayTracer
```

**Note**: Ensure that Java is properly installed and added to your system's PATH. You can verify this by running:

```bash
java -version
```

If Java is not installed or configured, refer to your operating system's instructions for setting it up.

### 4. View the Output

A window will open displaying the rendered image of three spheres (red, green, and blue) on a black background with a large yellow sphere as the floor, illuminated by ambient, point, and directional lights, featuring realistic shadows, highlights, and reflective surfaces.

---

## Code Structure

The program consists of the following classes:

### 1. `Point3D`

Represents a 3D point or vector. It includes methods for vector math:

- `subtract`: Subtracts two vectors.
- `multiply`: Multiplies a vector by a scalar.
- `dot`: Computes the dot product of two vectors.
- `length`: Computes the length of a vector.
- `normalize`: Normalizes a vector to unit length.

### 2. `Sphere`

Represents a sphere in 3D space. It has:

- `center`: The center of the sphere (a `Point3D`).
- `radius`: The radius of the sphere.
- `color`: The color of the sphere.
- `specular`: Specular exponent for shininess.
- `reflective`: Reflectivity coefficient controlling the strength of reflections.

### 3. `Light`

Represents a light source in the scene. It supports:

- `type`: `AMBIENT`, `POINT`, or `DIRECTIONAL` light.
- `intensity`: Strength of the light.
- `position`: Position of the light (for point lights).
- `direction`: Direction of the light (for directional lights, normalized).

### 4. `RayTracer`

The main class that handles rendering and display. It includes:

- **Scene Setup**: Defines the three spheres, a large yellow "floor" sphere, and three light sources (ambient, point, and directional).
- **Ray Tracing Logic**:
  - `canvasToViewport`: Maps canvas coordinates to viewport coordinates.
  - `intersectRaySphere`: Computes the intersection of a ray with a sphere, foundational for both shadows and reflections.
  - `closestIntersection`: Finds the nearest sphere intersected by a ray, used for primary rays, shadow checks, and reflections.
  - `reflectRay`: Calculates the reflected ray direction for mirror-like reflections using the formula `R = 2 * N * (N · R) - R`.
  - `traceRay`: Traces a ray recursively (up to a depth of 3) to compute the color, incorporating local lighting and reflected colors blended based on the sphere's reflectivity.
- **Lighting Computation**:
  - `computeLighting`: Calculates total light intensity at a point, including ambient, diffuse, and specular components. Shadows are implemented by casting a shadow ray from the intersection point to each light source (using `closestIntersection`). If an object intersects this ray before the light (within `tMax`), the light’s contribution is skipped, creating a shadow. For point lights, `tMax` is 1 (distance to the light), while for directional lights, it’s infinity.
- **Color Utilities**:
  - `multiplyColor`: Scales a color by a scalar (e.g., lighting intensity).
  - `blendColors`: Combines local and reflected colors based on a reflectivity weight.
- **Rendering**:
  - `render`: Renders the scene by tracing rays for each pixel with recursive reflection support.
  - `paintComponent`: Draws the rendered image on the screen.
- **Main Method**: Initializes the program and displays the rendered image in a window.

## Optical Effects Implementation

### Transparency

The code implements transparency through the `transparency` property of scene objects. When a ray hits a transparent object:

1. The ray continues through the object in a refracted direction
2. The color seen through the object is blended with the object's local color
3. The blending weight is determined by the object's transparency value (0 = opaque, 1 = fully transparent)
4. The final color is a weighted average of the local color, reflected color, and refracted color

### Refraction

Refraction is implemented using Snell's Law, which describes how light bends when passing through materials with different optical densities:

1. When a ray hits a transparent object, the `refractRay` method calculates the refracted direction
2. The calculation uses the object's `refractiveIndex` property (typically between 1.0 and 2.0)
3. The method handles total internal reflection when the angle is too steep
4. The refracted ray is then traced recursively to determine what color is seen through the object
5. Higher refractive indices cause more bending of light, creating more distortion in the viewed scene

---

The rendered image will look like this:
![Rendered Image of Three Spheres](generatedImage.png)
