# Real Book Store

###### Project assignment for Secure Software Development course 2023/2024

## Installation

1. Clone the repository using `git clone https://github.com/urosdragojevic/RealBookStore.git`
2. Run the application using Maven
3. See *SonarQube setup* section for instructions on running SonarQube for code analysis tasks

## SonarQube setup

1. Install Docker and Docker Compose (Docker Desktop for Windows/Mac machines)
2. Run `docker compose up` in the root of the project to start SonarQube container (`docker compose up -d` to start the containers in the background)
3. After the containers are started, log into SonarQube at `http://localhost:9000` with username: `admin`, password: `admin`.

4. Go to **My account** > **Security** tab:
![My Account](/sq_my_account.png "My Account")

5. Generate a *Global Analysis* access token:
![Generate access token](/sq_generate_token.png "Generate access token")
> In general, it is best practice to set an expiry on access tokens,
> but since this is just a local instance of Sonar it is not mandatory.

6. Copy the generated token and save it as `SONAR_TOKEN=sqa_7258ae10fb0e029a908b1fcbc385bb7da40cdae1` environment variable
![Copy access token](/sq_copy_token.png "Copy access token")
> If using IntelliJ you can specify the environment variable by editing the Run configuration of the `sonar:sonar` task.
> ![Environment variable](/sq_env_var.png "Environment variable")

> Run configuration that can be used in IntelliJ for `sonar:sonar` task is stored in the `.run` folder.

7. Run the `sonar:sonar` task. Once it completes you can access the *RealBookStore* project in SonarQube and check the code analysis results.
