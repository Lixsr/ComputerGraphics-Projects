# Ray Tracer in Java - Phase-Based Development

This is a simple ray tracing program written in Java, designed to render a 3D scene with spheres using advanced ray tracing techniques. The development of this program is divided into multiple phases, each building upon the previous one to add new features and optical effects. The rendered image is displayed in a window using Java's `javax.swing` library.

The project is organized into phase-specific folders, each containing the source code and related files for that particular stage of development. Below is a list of phases with brief descriptions, directing you to explore the corresponding folders.

---

## Phases

- **Phase 1: Three Spheres**  
  - **Folder**: [Phase_1_ThreeSpheres](Phase_1_ThreeSpheres)
  - Description: Initial implementation rendering a 2D projection of a 3D scene with three spheres (red, green, and blue) and a large yellow sphere as a "floor" using basic ray-sphere intersection.

- **Phase 2: Specular & Intensity**  
  - **Folder**: [Phase_2_specular&intensity](Phase_2_specular&intensity)
  - Description: Adds specular reflection (shiny highlights) and improves lighting intensity with ambient, point, and directional light sources.

- **Phase 3: Shadows**  
  - **Folder**:  [Phase_3_shadows](Phase_3_shadows)
  - Description: Introduces realistic shadows by computing occlusion of light using ray intersection tests.

- **Phase 4: Reflections**  
  - **Folder**: [Phase_4_reflections](Phase_4_reflections)
  - Description: Implements mirror-like reflections on sphere surfaces using recursive ray tracing.

- **Phase 5: More Shapes**  
  - **Folder**:  [Phase_5_More_Shapes](Phase_5_More_Shapes)
  - Description: Expands the scene to include additional shapes beyond spheres, enhancing scene complexity.

- **Phase 6: Transparency & Refraction**  
  - **Folder**: [Phase_6_Transparency&Refraction](Phase_6_Transparency&Refraction)
  - Description: Adds transparency and refraction effects using Snell's Law, with recursive ray tracing for refracted colors.

---

## How to Explore Each Phase

1. Navigate to the desired phase folder (e.g., `Phase_1_ThreeSpheres`, `Phase_2_specular&intensity`, etc.).
2. Follow the instructions in the `README.md` (if available) within each folder for compilation and execution details.
3. Compile and run the `RayTracer.java` file using the provided commands (e.g., `javac RayTracer.java` followed by `java RayTracer`).

**Prerequisites**: Ensure you have the Java Development Kit (JDK) 8 or later installed. Verify with `java -version` in your terminal.

---

## Getting Started

- **Clone or Download**: Clone this repository or download the `Ray-Tracing` folder to your local machine.
- **Explore Phases**: Dive into each phase folder to see the incremental development of the ray tracer.

For more details on the code structure, optical effects, or running the program, refer to the documentation within each phase folder or contact the maintainers.

Happy ray tracing!
