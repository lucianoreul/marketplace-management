<h1 align="center" style="font-weight: bold;">Backend Marketplace Management</h1>

<p align="center">
 <a href="#tech">Technologies</a> • 
 <a href="#started">Getting Started</a> • 
  <a href="#routes">API Endpoints</a>
</p>

<p align="center">
    <b>A secure RESTful API built with Spring Boot for managing users, products, and marketplace operations. Easily integrates with Angular frontend via JWT authentication and CORS configuration.</b>
</p>

<h2 id="technologies">💻 Technologies</h2>

- Java: `17`
- Spring Boot: `3.1.4`
- Spring Security
- JWT
- Gradle:
- MySQL

<h2 id="started">🚀 Getting Started</h2>

<p>This section explains how to set up and run the backend locally for development purposes.</p>

<h3>📦 Prerequisites</h3>
<ul>
  <li>Java 17</li>
  <li>Gradle 8+</li>
  <li>MySQL 8+</li>
</ul>

<h3>⚙️ Backend Setup</h3>
<ol>
  <li>Make sure MySQL is running and a database is created (name: <code>marketplace</code>).</li>
  <li>Before running the application, ensure the following environment variables are properly configured:</li>
  <pre><code>
DB_USERNAME=your_mysql_username
DB_PASSWORD=your_mysql_password
JWT_SECRET=your_jwt_secret_key
  </code></pre>
  <li>Navigate to the backend project folder:</li>
  <pre><code>cd backend-marketplace-management</code></pre>
  <li>Run the application using Gradle:</li>
  <pre><code>./gradlew bootRun</code></pre>
</ol>

<p>Once the server is running, the API will be available at <code>http://localhost:8080</code>.</p>


