# Ray Tracer in Java

This is a simple ray tracing program written in Java. It renders a scene with three spheres (red, green, and blue) using basic ray tracing techniques. The program uses Java's `javax.swing` library to display the rendered image in a window.

---

## Features

- **Sphere Rendering**: Renders a 2D projection of a 3D scene.
- **Ray-Sphere Intersection**: Uses mathematical formulas to compute intersections between rays and spheres.
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


---

## 📁 Project Structure

```
.
├── Sphere.java       # Defines a 3D sphere with color, specular, and reflective properties
├── Vector.java       # Implements 3D vector operations
├── Light.java        # Models ambient, point, and directional light sources
├── RayTracer.java    # Core ray tracing engine with lighting and reflections
```

---

## 📄 File Descriptions

### **Sphere.java**

Represents a 3D sphere with visual and physical properties.

#### Attributes:
- `center`: A `Vector` representing the sphere's position.
- `radius`: A `double` indicating the sphere’s size.
- `color`: A `Color` object representing the RGB color.
- `specular`: A `double` controlling the shininess (-1 disables specular highlight).
- `reflective`: A `double` from `0` to `1` indicating reflectivity (0 = matte, 1 = perfect mirror).

#### 🔑 Key Features:
- Input arrays are converted into `Vector` and `Color` objects.
- Fields are immutable to ensure thread safety and consistent behavior.

---

### **Vector.java**

Defines a lightweight 3D vector class used in all ray tracing calculations.

#### Attributes:
- `x`, `y`, `z`: Coordinates as `double` values.

#### Methods:
- `add(Vector v)`, `subtract(Vector v)`
- `multiply(double scalar)`
- `dot(Vector v)`
- `length()`
- `normalize()`: Returns a unit vector (assumes non-zero length)

#### 🔑 Key Features:
- Optimized for operations like ray direction, surface normals, and lighting vectors.
- Simple and efficient for real-time rendering.

---

### **Light.java**

Models different types of light sources in the scene.

#### Attributes:
- `type`: `"ambient"`, `"point"`, or `"directional"`.
- `intensity`: A `double` representing brightness (0 to 1).
- `position`: A `Vector` for point light location.
- `direction`: A `Vector` for directional light orientation.

#### 🔑 Key Features:
- Unified interface for all light types.
- Constructor handles initialization depending on light type.

---

### **RayTracer.java**

The main engine that performs ray tracing to render a 3D scene with realistic shading and reflection.

#### 🔧 Key Components:

##### Constants:
- `WIDTH`, `HEIGHT`: Image resolution (800x800 pixels).
- `VIEWPORT_SIZE`, `PROJECTION_PLANE_D`: Define camera projection setup.
- `BACKGROUND_COLOR`: Default background color (black).

##### Scene Setup:
- **Four spheres**:
  - Three small colored spheres (red, green, blue)
  - One large yellow sphere acting as a reflective ground
- **Three light sources**:
  - Ambient light
  - Point light
  - Directional light
- Uses `ArrayList` to manage scene objects.

##### Rendering:
- `render(String filename)`:
  - Iterates over every pixel.
  - Casts a ray through the scene.
  - Computes lighting and reflection.
  - Writes the resulting image to file using `BufferedImage` and `ImageIO`.

##### Ray Tracing Logic:
- `traceRay(Vector origin, Vector direction, double tMin, double tMax, int depth)`:
  - Returns final color for a given ray.
  - Calculates **local illumination** and **recursive reflections**.
  - Stops recursion at a maximum depth (default: 3).

- `computeLighting(Vector point, Vector normal, Vector view, double specular)`:
  - Aggregates **ambient**, **diffuse**, and **specular** lighting contributions.

- `intersectRaySphere(Vector origin, Vector direction, Sphere sphere)`:
  - Computes intersection point using the quadratic formula.

##### Reflection Handling:
- Combines local lighting color with reflected color weighted by the sphere's `reflective` coefficient.
- Limits recursive depth to avoid infinite loops and excessive computation.

##### Main Method:
- Creates an instance of `RayTracer`.
- Calls `render("Generated_Image.png")` to generate the final image.

---

## 🖼️ Output

Running the program produces an image file:

```
Generated_Image.png
```

This file shows the rendered 3D scene with shading and lighting effects.

---

The rendered image will look like this:
![Final image](generated_Image.png)
