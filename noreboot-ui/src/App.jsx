import { useState, useEffect } from 'react'
import './App.css'

function App() {
  const [themeColor, setThemeColor] = useState("Loading...");
  
  // These hold the text you type into the form
  const [configKey, setConfigKey] = useState("THEME_COLOR");
  const [configValue, setConfigValue] = useState("");

  const fetchConfig = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/configs/THEME_COLOR');
      if (response.ok) {
        const data = await response.json();
        setThemeColor(data.configValue);
      } else {
        setThemeColor("Not found");
      }
    } catch (error) {
      console.error("Error fetching data:", error);
      setThemeColor("Backend is offline!");
    }
  };

  useEffect(() => {
    fetchConfig();
  }, []);

  // This function runs when you click the "Update" button
  const handleUpdate = async (e) => {
    e.preventDefault(); // Stops the page from refreshing
    try {
      // This completely replaces your old "curl -X POST" terminal command!
      const response = await fetch(`http://localhost:8080/api/configs?key=${configKey}&value=${configValue}`, {
        method: 'POST',
      });

      if (response.ok) {
        // If we updated THEME_COLOR, fetch it again to instantly update the screen
        if (configKey === "THEME_COLOR") {
          fetchConfig();
        }
        setConfigValue(""); // Clear out the input box
        alert(`Success! Check your Spring Boot terminal for the Kafka fire emojis! 🔥`);
      }
    } catch (error) {
      console.error("Error updating config:", error);
      alert("Failed to update.");
    }
  };

  return (
    <div className="dashboard-container">
      <header className="header">
        <h1>⚡ No Reboot HQ</h1>
        <p>Real-Time Configuration Manager</p>
      </header>

      <main className="main-content">
        <div className="card">
          <h2>Current Settings</h2>
          <div className="setting-item">
            <strong>THEME_COLOR: </strong> 
            <span className="badge">{themeColor}</span>
          </div>
        </div>

        <div className="card">
          <h2>Update Setting</h2>
          {/* Here is our new form! */}
          <form onSubmit={handleUpdate} className="update-form">
            <input
              type="text"
              value={configKey}
              onChange={(e) => setConfigKey(e.target.value)}
              placeholder="Config Key (e.g., THEME_COLOR)"
              required
            />
            <input
              type="text"
              value={configValue}
              onChange={(e) => setConfigValue(e.target.value)}
              placeholder="New Value (e.g., DARK)"
              required
            />
            <button type="submit">Broadcast to Kafka</button>
          </form>
        </div>
      </main>
    </div>
  )
}

export default App