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

## 📁 Project Structure

```
├── Sphere.java       # Defines a 3D sphere with position, size, and color
├── RayTracer.java    # Core ray tracing logic and image rendering
```

---

## 📄 File Descriptions

### **Sphere.java**

Defines the `Sphere` class that models a sphere in 3D space with the following attributes:

- `center`: A 3-element `double[]` array representing the sphere's center in 3D space.
- `radius`: A `double` value representing the radius (must be positive).
- `color`: A 3-element `int[]` array representing the RGB color of the sphere.

#### 🔑 Key Features

- **Input Validation**: Ensures center and color arrays are non-null and of correct length; radius must be positive.
- **Defensive Copying**: Arrays are copied to protect against external modification.
- **Immutability**: All fields are declared `final` for thread safety and consistency.

---

### **RayTracer.java**

Implements the ray tracing algorithm and handles the rendering of a simple 3D scene into a PNG image.

#### 🔧 Key Components

##### Constants

- `WIDTH`, `HEIGHT`: Image dimensions (800x800 pixels).
- `VIEWPORT_SIZE`, `PROJECTION_PLANE_D`: Configure viewport size and projection plane distance.
- `BACKGROUND_COLOR`: Default color (white) for rays that don't intersect any sphere.

##### Scene Setup

- The constructor initializes a scene with three colored spheres (red, green, blue) positioned at different coordinates.

##### Rendering

- `render(String filename)`: Casts rays through each pixel, determines color via ray-sphere intersections, and saves the output image using:
  - `BufferedImage` for pixel manipulation
  - `ImageIO.write()` for PNG output

##### Ray Tracing Logic

- `traceRay(double[] origin, double[] direction, double tMin, double tMax)`: Returns the color of the closest intersected sphere or the background color.
- `intersectRaySphere(double[] origin, double[] direction, Sphere sphere)`: Uses the quadratic formula to detect ray-sphere intersections.
- Utility methods like `dot` and `subtract` perform basic vector math.

##### Main Method

- Instantiates a `RayTracer` object and calls `render("Generated_Image.png")` to create the output image.

---

## 🖼️ Output

Running the program will generate a file:

```
Generated_Image.png
```

This image visualizes the 3D scene from the perspective of a virtual camera using basic ray tracing principles.

---

## Example Output

The program will render three spheres:

- A **red sphere** at `(0, -1, 3)`.
- A **blue sphere** at `(2, 0, 4)`.
- A **green sphere** at `(-2, 0, 4)`.

The rendered image will look like this:
![Rendered Image of Three Spheres](generated_Image.png)
