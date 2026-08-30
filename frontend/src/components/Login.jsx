import { useState } from "react";
import axios from 'axios';
import "./Login.css";

function Login() {
  const [activeTab, setActiveTab] = useState("login");
  const [formData, setFormData] = useState({
    username: "",
    password: "",
    email: "",
  });
  const onSubmit = async (e) => {
  e.preventDefault();

  const url =
    activeTab === "login"
      ? "http://localhost:8081/api/v1/authenticate/login"
      : "http://localhost:8081/api/v1/authenticate/signup";

  const payload =
    activeTab === "login"
      ? {
          email: formData.email,
          password: formData.password,
        }
      : {
          username: formData.username,
          email: formData.email,
          password: formData.password,
        };

  try {
    const response = await axios.post(url, payload, {
      headers: {
        "Content-Type": "application/json",
      },
      withCredentials: true,
    });

    console.log("Success:", response.data);
  } catch (error) {
    console.error(error);

    if (error.response) {
      console.log(error.response.data);
    }
  }
};
  return (
    <section className="login">
      <div className="login-container">
        <div className="login-tabs">
          <button
            className={`login-tab ${activeTab === "login" ? "active" : ""}`}
            onClick={() => setActiveTab("login")}
          >
            Login
          </button>

          <button
            className={`login-tab ${activeTab === "signup" ? "active" : ""}`}
            onClick={() => setActiveTab("signup")}
          >
            Sign Up
          </button>
        </div>

        <div className="login-forms">
          {/* Login Form */}
          <form
            className={`login-form ${
              activeTab === "login" ? "active" : ""
            }`}
          >
            <input
              type="text"
              placeholder="Email"
              required
              value={formData.email}
              onChange={(e) =>
                setFormData({ ...formData, email: e.target.value })
              }
            />

            <input
              type="password"
              placeholder="Password"
              required
              value={formData.password}
              onChange={(e) =>
                setFormData({ ...formData, password: e.target.value })
              }
            />

            <button type="submit" onClick={onSubmit}>Login</button>
          </form>

          {/* Signup Form */}
          <form
            className={`login-form ${
              activeTab === "signup" ? "active" : ""
            }`}
          >
            <input
              type="email"
              placeholder="Email"
              required
              value={formData.email}
              onChange={(e) =>
                setFormData({ ...formData, email: e.target.value })
              } 
            />

            <input
              type="text"
              placeholder="Username"
              required
              value={formData.username}
              onChange={(e) =>
                setFormData({ ...formData, username: e.target.value })
              }
            />

            <input
              type="password"
              placeholder="Password"
              required
              value={formData.password}
              onChange={(e) =>
                setFormData({ ...formData, password: e.target.value })
              }
            />

            <button type="submit" onClick={onSubmit}>Sign Up</button>
          </form>
        </div>
      </div>
    </section>
  );
}

export default Login;