# Game Engine Structure

## Introduction

Welcome to the `com.gameengine` project — a Java-based game engine for building interactive 3D games.

---

## 🚀 Getting Started

Follow these steps to download and run the project locally using **IntelliJ IDEA**:

1. **Download the Project**

2. **Open in IntelliJ IDEA**

    - Open GameEngine folder with IntelliJ IDEA.
    - Click **File > Open**, then select the project folder.
    - Wait for IntelliJ to index and build the project (ensure you have JDK 8+ installed).

3. **Run the Game**
    - Navigate to `test/TestGame.java` in the Project Explorer.
    - Right-click the `TestGame` class and select **Run 'TestGame.main()'**.
    - The game window should open, and you can interact using the controls below.

---

## 🎮 Controls

- **Movement**:

    - Forward: `W`
    - Backward: `S`
    - Left: `A`
    - Right: `D`

- **Vertical Movement**:

    - Up: `Q`
    - Down: `E`

- **Camera Rotation**:

    - Use **Mouse** to look around

- **Block Interaction**:
    - Build Block: **Left Click**
    - Destroy Block (with animation): **Right Click**
    - Destroy Block (no animation): `R`

---

## 📦 Package Structure

### 🔹 `core`

#### **BlockBuilder**

Handles block construction, removal, and procedural generation.

- `buildBlock()` – Builds a block at the location pointed by the camera.
- `removeBlock()` – Removes a block with animation and particle effects.
- `removeBlockWithoutAnimation()` – Removes a block instantly without animations.
- `randomBlocks()` – Generates 2000 random blocks for testing or world generation.

#### **Camera**

Manages camera position and rotation for 3D navigation.

#### **ObjectLoader**

Handles loading of models and textures into GPU memory.

- `loadOBJModel()` – Loads a model from an OBJ file into a renderable format.
- `loadModel()` – Loads model data (vertices, textures, normals) into a VAO.
- `loadTexture()` – Loads a texture and configures it for OpenGL rendering.
- `cleanup()` – Frees up GPU resources (VAOs, VBOs, textures).

#### **ShaderManager**

Handles shader compilation, linking, and uniform management.

- `setUniform` – Sets the value of a shader uniform variable.
- `createPointLightListUniform` – Creates uniform structures for point lights.
- `createSpotLightListUniform` – Creates uniform structures for spotlights.
- `createDirectionalLightUniform` – Creates uniform for a directional light.
- `createMaterialUniform` – Defines material properties in the shader.
- `createVertexShader` – Compiles a vertex shader from source code.
- `createFragmentShader` – Compiles a fragment shader from source code.
- `createShader` – Compiles a shader of a specific type.
- `link()` – Links and validates the shader program.
- `bind()` – Activates the shader program for rendering.
- `unbind()` – Deactivates the current shader.
- `cleanup()` – Deletes the shader program and its resources.

---

### 🔹 `entity`

#### **Entity**

Base class representing a 3D object in the game.

#### **Model**

Stores geometry data and rendering information for an entity.

#### **SceneManager**

Manages all scene elements like entities, terrain, and lights.

- `getEntities()` – Returns the list of all entities in the scene.
- `getEntity()` – Retrieves an entity at a specific position.
- `setEntities()` – Sets the list of active entities.
- `addEntity()` – Adds an entity to the scene.
- `removeEntity()` – Removes an entity from a specific position.
- `setTerrains()` – Sets the terrains in the scene.
- `addTerrain()` – Adds a new terrain to the scene.
- `setSpotAngle()` – Sets the angle of spotlights for animation.
- `incSpotAngle()` – Increments spotlight angle to animate it.
- `setSpotInc()` – Sets the increment direction for spotlights.
- `incLightAngle()` – Adjusts directional light rotation over time.

#### **Texture**

Encapsulates texture data used for rendering 3D models.

---

### 🔹 `utils`

#### **Animation**

Provides visual effects for entities like explosions and rotations.

- `explosion()` – Applies a bouncing explosion animation using Bezier curves.
- `rotateOverTime()` – Rotates an entity continuously over a duration.

#### **Consts**

Stores global constants used across the engine.

- `FOV` – Field of View for rendering.
- `ROTATION_SPEED` – Default rotation speed for animations.
- `MOVEMENT_SPEED` – Default movement speed.
- `SPECULAR_POWER` – Intensity of specular reflections.
- `AMBIENT_LIGHT` – Base lighting level.
- `DEFAULT_COLOR` – Fallback color for models.
- `MOUSE_SENSITIVITY` – Sensitivity for mouse input.
- `CAMERA_MOVE_SPEED` – Speed of camera movement.

#### **RaycastHit**

Handles raycasting logic for object detection and collision.

- `raycastBlockHitPosition()` – Returns the first block hit by a ray, if any.
- `getOffset()` – Gets the surface normal of the last hit.
- `rayIntersectsAABB()` – Checks ray intersection with an axis-aligned bounding box.
- `rayPlaneIntersection()` – Computes the intersection point with a plane.
- `isPointOnFace()` – Determines if a point lies on a specific face of a cube.

#### **Transformation**

Generates transformation matrices for rendering.

- `createTransformationMatrix(Entity)` – Builds model matrix for an entity.
- `createTransformationMatrix(Terrain)` – Builds model matrix for a terrain.
- `getViewMatrix()` – Generates the camera's view matrix.

#### **Utils**

Miscellaneous helper methods for file and buffer operations.

- `storeDataInFloatBuffer()` – Converts float arrays to GPU buffers.
- `storeDataInIntBuffer()` – Converts int arrays to GPU buffers.
- `loadResource()` – Loads a resource file into a string.
- `readAllLines()` – Reads all lines from a file as a list.

---

### 🔹 `test`

#### **Launcher**

Main entry point to start the game engine.

- `main()` – Initializes window, game logic, and starts the engine loop.
- `getWindow()` – Returns the window manager instance.
- `getGame()` – Returns the active game instance.

#### **TestGame**

Implements a simple game setup to test the engine.

- `init()` – Loads models, sets up terrain and lighting, and initializes objects.
- `input()` – Captures keyboard inputs for camera movement.
- `update()` – Handles real-time updates like input, lighting, and animations.
- `render()` – Renders the scene each frame.
- `cleanup()` – Cleans up resources on exit.

---

## 📁 Resources

- `models/` – Contains 3D model files used by the engine.
- `shaders/` – Contains vertex and fragment shader files.
