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
├── Sphere.java       # Defines a 3D sphere with color and specular properties
├── Vector.java       # 3D vector operations used in geometry and lighting
├── Light.java        # Models ambient, point, and directional lights
├── RayTracer.java    # Core ray tracing engine and image rendering
```

---

## 📄 File Descriptions

### **Sphere.java**

Represents a sphere in 3D space with physical and visual properties.

#### Attributes:

- `center`: A `Vector` indicating the sphere’s position.
- `radius`: A `double` representing the sphere’s radius.
- `color`: A `Color` object for RGB color.
- `specular`: A `double` indicating specular reflection (-1 disables it).

#### 🔑 Key Features:

- Converts raw input into `Vector` and `Color` objects.
- Fields are immutable for safety and clarity.

---

### **Vector.java**

Provides a minimal 3D vector class used for ray and lighting calculations.

#### Attributes:

- `x`, `y`, `z`: Coordinates (as `double` values).

#### Methods:

- `add(Vector v)`, `subtract(Vector v)`
- `multiply(double scalar)`
- `dot(Vector v)`
- `length()`
- `normalize()`

#### 🔑 Key Features:

- Lightweight and purpose-built for vector math.
- Central to all geometric and lighting calculations.

---

### **Light.java**

Models various types of light sources in the scene.

#### Attributes:

- `type`: `"ambient"`, `"point"`, or `"directional"`.
- `intensity`: Brightness of the light (0 to 1).
- `position`: A `Vector` (used for point lights).
- `direction`: A `Vector` (used for directional lights).

#### 🔑 Key Features:

- Flexible design supports all major light types.
- Simple interface for easy scene configuration.

---

### **RayTracer.java**

The heart of the renderer — this class ties together all components to produce the final image.

#### Constants:

- `WIDTH`, `HEIGHT`: Image resolution (800x800).
- `VIEWPORT_SIZE`, `PROJECTION_PLANE_D`: Control the camera and projection setup.
- `BACKGROUND_COLOR`: Default white background for rays that miss all objects.

#### Scene Setup:

- Initializes **four spheres**:
  - Three small colored spheres (red, green, blue)
  - One large yellow sphere acting as the ground
- Adds **three lights**:
  - Ambient
  - Point
  - Directional
- Uses `ArrayList` to dynamically manage the scene.

#### Rendering:

- `render(String filename)`:
  - Casts a ray through each pixel.
  - Determines the color based on intersections and lighting.
  - Saves the final image using `BufferedImage` and `ImageIO`.

#### Ray Tracing Logic:

- `traceRay(Vector origin, Vector direction, double tMin, double tMax)`:

  - Finds the nearest intersected sphere.
  - Calculates final color with shading.

- `computeLighting(Vector point, Vector normal, Vector view, double specular)`:

  - Applies **diffuse** and **specular** lighting from each source.

- `intersectRaySphere(Vector origin, Vector direction, Sphere sphere)`:
  - Uses the quadratic formula to check for intersections.

#### Main Method:

- Instantiates `RayTracer`
- Calls `render("Generated_Image.png")` to generate the output

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
