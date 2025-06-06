# ICS415 Computer Graphics Projects

Welcome to the repository for my ICS415 (Computer Graphics) projects! This collection contains a variety of implementations and experiments developed as part of the coursework for ICS415, a Computer Graphics class. The focus of these projects is to explore and apply fundamental concepts in computer graphics, including rendering techniques, lighting models, and shader programming. Below are the highlighted projects included in this repository, each with its own focus and implementation details.

---

## Projects

### Project-1: Ray Tracing in One Weekend

- **Folder**: [Ray-Tracing-in-One-Weekend](Ray-Tracing-in-One-Weekend)
- **Description**: This project is a phased development of a ray tracer written in Java. It renders a 3D scene with spheres, progressively adding features such as lighting, shadows, reflections, transparency, and refraction. The implementation is divided into multiple phases, each building on the previous one to enhance realism and complexity.

  - **Features**: Includes ambient, point, and directional lighting, diffuse and specular reflections, shadow casting, and optical effects like transparency and refraction using recursive ray tracing.
  - **How to Explore**: Navigate to the [Ray-Tracing-in-One-Weekend](Ray-Tracing-in-One-Weekend) folder and refer to the `README.md` inside for detailed instructions on compiling and running the code.
  - **Prerequisites**: Requires Java Development Kit (JDK) 8 or later.

  ## 🖼️ Output Preview:

  Below is the final rendered output from the project:

  ![Final Image](Ray-Tracing-in-One-Weekend/Final_Image.png)

### Project-1: Computer Graphics from Scratch: A Programmer's Introduction to 3D Rendering here

- **Folder**: [RayTracing-Java](RayTracing-Java)
- **Description**: This project is a phased development of a ray tracer written in Java. It renders a 3D scene with spheres, progressively adding features such as lighting, shadows, reflections, transparency, and refraction. The implementation is divided into multiple phases, each building on the previous one to enhance realism and complexity.
  - **Features**: Includes ambient, point, and directional lighting, diffuse and specular reflections, shadow casting, and optical effects like transparency and refraction using recursive ray tracing.
  - **How to Explore**: Navigate to the [RayTracing-Java](RayTracing-Java) folder and refer to the `README.md` inside for detailed instructions on compiling and running the code.
  - **Prerequisites**: Requires Java Development Kit (JDK) 8 or later.

### Project-2: A Ray Tracer in GLSL/HLSL

- **Folder**: [RayTracing-GLSL](RayTracing-GLSL)
- **Description**: This project implements a ray tracer using GLSL (OpenGL Shading Language) or HLSL (High-Level Shading Language), leveraging GPU acceleration for real-time rendering. It focuses on shader-based ray tracing techniques, suitable for graphics applications and game development, as part of the ICS415 curriculum.

  - **Features**: Utilizes GPU shaders to perform ray-sphere intersections, lighting calculations, and basic scene rendering.
  - **How to Explore**: Navigate to the [RayTracing-GLSL](RayTracing-GLSL) folder and consult the `README.md` within for setup and execution instructions.
  - **Prerequisites**: Requires a compatible graphics API (e.g., OpenGL or DirectX) and a development environment supporting GLSL/HLSL (e.g., ShaderToy, Unity, or a custom OpenGL setup).

  ## 🖼️ Output Preview:

  Below is the final rendered output from the project:

  ![Final Image](RayTracing-GLSL/Final-Image.png)

### Project-3: A 3D Game Engine in Java

- **Folder**: [GameEngine-Java](GameEngine-Java)
- **Description**: This project is a Java-based 3D game engine developed as part of the ICS415 course. It provides a fully interactive environment featuring real-time rendering, lighting, camera movement, and block manipulation. The engine is structured to separate core rendering logic, utilities, scene management, and testing, providing a modular and extensible framework for future development.
  - **Features**: Includes a real-time scene with entity and terrain management, camera controls, directional and spot lighting, shader support, and block interactions (build/destroy).
  - **Controls**:
    - **Movement**: `W` `A` `S` `D`
    - **Ascend/Descend**: `Q` `E`
    - **Rotate View**: Mouse
    - **Build Block**: Left Click
    - **Destroy Block (with animation)**: Right Click
    - **Destroy Block (no animation)**: `R`
  - **How to Explore**:
    - Watch the [Video Demo](https://vimeo.com/1080744426).
    - Navigate to the [GameEngine-Java](GameEngine-Java) folder and refer to the `README.md` inside for full details on engine structure, controls, and functionality.

Here’s the updated section with the Bézier Curve Editor added under "Additional Projects":

---

### Project-4: 🌀 Bézier Curve Editor

- **Folder**: [Bezier-curve](Bezier-curve)
- **Description**: An interactive Bézier curve editor built with **Next.js**, **Shadcn UI**, and **Tailwind CSS**, enabling users to intuitively create and explore Bézier curves through two distinct modes: _Poly Bézier Curves_ and _Fixed Control Points_. Designed for visual learning and experimentation, this project is ideal for those interested in curve modeling and graphics interfaces.

  - **Features**:

    - Add, move, and delete control points with mouse and keyboard.
    - Two modes: multiple connected cubic Bézier segments or single higher-order Bézier curve with 3–6 points.
    - Real-time curve updates and visual feedback with color-coded points.

  - **How to Explore**:

    - Visit the [Live Demo](https://bezier-curve-peach.vercel.app) or watch the [Video Demo](https://vimeo.com/1080742494).
    - To run locally, navigate to the `bezier-curve` folder and follow the steps in `README.md`.

  - **Prerequisites**: Requires Node.js v16+ and npm v8+.

---

## Getting Started

1. **Clone or Download**: Clone this repository or download the main folder to your local machine.
2. **Explore Projects**: Use the links above to navigate to the respective project folders and follow the instructions provided in their `README.md` files.
3. **Run the Code**: Ensure you meet the prerequisites for each project and follow the compilation/run commands specified.
