# What is Sollge?
**Sollge** is a web platform for posting and discovering property rentals. Users can create accounts, list their own properties, browse available rentals, and get notified about activity on their listings.

This repository contains the backend system that powers Sollge. It's built with Java Spring Boot and follows a microservices architecture, making it modular, scalable, and easier to maintain.

There’s an architecture diagram included below for a visual overview.
<img width="871" height="817" alt="sollge_c4 drawio" src="https://github.com/user-attachments/assets/e1139890-722b-4586-ad68-9fc253d8cd22" />

<img width="1642" height="1170" alt="sollge_c4_level2 drawio" src="https://github.com/user-attachments/assets/cab65c5b-54fd-4cad-a72d-43848dd18c72" />


# Tech Used
- ![PostgreSQL](https://avatars.githubusercontent.com/u/177543?s=14&v=4) **PostgreSQL** – Stores user accounts and related data  
- ![MongoDB](https://avatars.githubusercontent.com/u/45120?s=14&v=4) **MongoDB** – Stores rental listings  
- ![Redis](https://avatars.githubusercontent.com/u/1529926?s=14&v=1) **Redis** – Speeds things up by caching user profiles and rental previews  
- ![Kafka](https://avatars.githubusercontent.com/u/47359?s=14&v=4) **Kafka** – Allows services to communicate asynchronously with each other  
- ![AWS S3](https://avatars.githubusercontent.com/u/2232217?s=14&v=4) **AWS S3 + CloudFront** – Stores and delivers user-uploaded images fast  
- ![SQS](https://avatars.githubusercontent.com/u/2232217?s=14&v=4) **AWS SQS** – A queue for sending user notifications when needed  

# Services:
- 🧑 ``User Service`` Tracks user accounts and login sessions. It also handles signing up, logging in, and checking if a user’s token is still valid.
- 🏘️ ``Rental Service`` Lets users post new rentals and shows a list of available ones. New rentals are saved as Pending and sent to Verifier Service for review before being shown to others.
- ✅ ``Verifier Service`` Checks new rentals sent from the Rental Service (via Kafka). If everything looks good, the rental is marked Available. If not, it gets Blocked.
- 🔔 ``Notifier Service`` Sends out notifications when a rental is verified or getting more views than usual. It listens for events using AWS SQS.
- 📊 ``Metrics Service`` Scrapes stats and metrics from all services (via Kafka), then shares them with Prometheus for monitoring.
- 🚪 ``API Gateway`` The main entry point for all requests. It handles authentication and routes traffic to the right service behind the scenes.

Running the Project
All services are built into Docker containers and pushed to DockerHub:
👉 https://hub.docker.com/repositories/fodded

Settings like passwords and URLs are handled using environment variables.
