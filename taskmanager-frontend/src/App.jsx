import './App.css';
import { useState, useEffect } from 'react';

function App() {
  const [tasks, setTasks] = useState([]);
  const [taskTitle, setTaskTitle] = useState('');
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetch('http://localhost:8080/tasks')
      .then(response => {
        if (!response.ok) {
          throw new Error('Failed to load tasks');
        }
        return response.json();
      })
      .then(data => {
        setTasks(data);
        setIsLoading(false);
      })
      .catch(err => {
        setError(err.message);
        setIsLoading(false);
      });
  }, []);

  function handleAddTask() {
    fetch('http://localhost:8080/tasks', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ title: taskTitle })
    })
      .then(response => response.json())
      .then(newTask => {
        setTasks([...tasks, newTask]);
        setTaskTitle('');
      });
  }

  function handleDeleteTask(id) {
    fetch(`http://localhost:8080/tasks/${id}`, {
      method: 'DELETE'
    }).then(() => {
      setTasks(tasks.filter(task => task.id !== id));
    });
  }

  return (
    <div className="app-container">
      <h1>Task Manager</h1>

      <div className="task-input-row">
        <input
          type="text"
          value={taskTitle}
          onChange={(e) => setTaskTitle(e.target.value)}
          placeholder="Enter a task"
        />
        <button onClick={handleAddTask}>Add Task</button>
      </div>

      {isLoading && <p>Loading tasks...</p>}

      {error && <p className="error-message">Error: {error}</p>}

      {!isLoading && !error && (
        <ul>
          {tasks.map((task) => (
            <li key={task.id}>
              {task.title}
              <button onClick={() => handleDeleteTask(task.id)}>Delete</button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default App;