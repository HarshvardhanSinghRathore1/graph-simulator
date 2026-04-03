# Graph Algorithm Simulator

A high-quality, full-stack web application for simulating Graph Algorithms (BFS, DFS, Dijkstra) step-by-step.

## Technologies Used
- **Backend**: Java 17, Spring Boot 3
- **Frontend**: HTML5, CSS3, Vanilla JavaScript, HTML5 Canvas API

## Features
- Interactive Canvas-based graph rendering.
- Circular node positioning algorithm.
- Supports directed and undirected graphs.
- Step-by-step animations for Breadth First Search (BFS) and Depth First Search (DFS).
- Shortest path tracing using Dijkstra's Algorithm.
- Detailed step logs and adjustable animation speed.
- Dark mode, premium UI.

## File Structure
```
GraphAlgorithmSimulator-Web/
├── backend/
│   ├── pom.xml
│   └── src/main/java/com/graphsim/
│       ├── GraphSimApplication.java
│       ├── algorithms/
│       ├── controller/
│       ├── model/
│       └── service/
└── frontend/
    ├── css/
    ├── js/
    └── index.html
```

## How to Run

### 1. Start the Backend (Spring Boot Component)
1. Open up your terminal or IDE (IntelliJ, Eclipse, VS Code).
2. Navigate to the `backend` directory.
3. Run the Spring Boot app:
   ```bash
   mvn spring-boot:run
   ```
   Or simply run `GraphSimApplication.main()` from your IDE.
4. The server will start on `http://localhost:8080`.

### 2. Start the Frontend
1. The frontend relies purely on Vanilla JS and HTML.
2. Simply open `frontend/index.html` in your web browser.
3. Keep the backend running so the frontend `fetch()` API calls succeed.

## Usage
1. Provide the **Number of Vertices (N)**.
2. Provide the **Edge List** in the format `u v weight` (each line represents an edge connecting node `u` to node `v` with a `weight`).
3. Set the **Start Node** and (Optional) **Target Node**.
4. Click **Create Graph** to populate the canvas.
5. Watch the simulation play out by pressing **BFS**, **DFS**, or **Dijkstra**.
