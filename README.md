# Snackman

Snackman is a small JavaFX game inspired by Pacman.  
This project mainly serves as a technical demonstration of a simple game loop, animation handling, and a basic MVC-like structure in JavaFX.

## Overview

The game is built around a tile-based map.  
A player entity moves through the map, collects food dots, and avoids enemy entities.  
The game ends when all dots are collected or when the player collides with an enemy.

## Installation

Run ```mvn clean javafx:jlink``` to create a distribution packed with a standalone jre.

## Game Loop and Animation

The game loop is implemented using JavaFX’s `AnimationTimer`.

- Each frame calculates the elapsed time (`deltaTime`).
- A fixed time step is used (`FIXED_DELTA_TIME`) to update game logic at constant intervals.
- The `fixedUpdate()` method handles movement logic for the player and enemies.
- The `updateGame()` method is executed every frame and is responsible for:
  - Input handling
  - Sprite movement
  - Collision checks
  - Rendering

This separation ensures that game logic is not directly dependent on the frame rate.

## Structure and Responsibilities

- **Map generation**  
  The map is generated once at startup and converted into static tile sprites.

- **Entities**  
  Player and enemies are represented by logic classes (position, direction, movement) and visual sprite classes used for rendering.

- **Rendering**  
  The canvas is cleared every frame and all tiles, entities, and UI elements (score, pause view) are rendered again.

- **Collision handling**  
  Collisions are checked between:
  - Player and food tiles (score increase, win condition)
  - Player and enemies (game over)

## MVC Concept (Simplified)

The project follows a simplified MVC approach:

- **Model**  
  Contains game state and logic (map data, player and enemy entities, movement rules).

- **View**  
  Responsible for visual representation (sprites, map rendering, score, pause screen).

- **Controller**  
  Manages the game loop, processes input, updates the model, and triggers rendering.

The separation is not strict but helps to keep logic, rendering, and control flow clearly separated.

