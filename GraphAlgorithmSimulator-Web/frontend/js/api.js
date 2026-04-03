const API_BASE = 'http://localhost:8080/graph';

async function fetchAPI(endpoint, payload) {
    try {
        const response = await fetch(`${API_BASE}${endpoint}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error('API Error:', error);
        throw error;
    }
}

const API = {
    createGraph: (data) => fetchAPI('/create', data),
    runBFS: (data) => fetchAPI('/bfs', data),
    runDFS: (data) => fetchAPI('/dfs', data),
    runDijkstra: (data) => fetchAPI('/dijkstra', data)
};
