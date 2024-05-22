# WarZone-Game

## Overview
WarZone-Game is a strategic risk-based game, developed in Java, where players compete to dominate territories and achieve global conquest. Inspired by the classic Risk board game, it emulates the original [WarZone](https://www.warzone.com/) game with unique enhancements, including a tournament mode.

## Features
- Multiple game modes including multiplayer and tournament mode.
- Save and load game functionality.
- Detailed game logs for reviewing past moves.

## Design Patterns
- **MVC (Model-View-Controller) Pattern**: Separates the game's data (Model), user interface (View), and control logic (Controller) to promote modularity and ease of maintenance.
- **State Pattern**: Manages the state of the game, allowing for clear transitions between different phases such as setup, reinforcement, attack, and fortification.
- **Strategy Pattern**: Enables different algorithms to determine player strategies, making the gameplay adaptable and challenging.

## Installation

### Prerequisites
- Java JDK 8 or higher
- Maven

### Steps
1. Clone the repository:
    ```sh
    git clone https://github.com/JSM2512/WarZone-Game.git
    ```
2. Navigate to the project directory:
    ```sh
    cd WarZone-Game
    ```
3. Build the project using Maven:
    ```sh
    mvn clean install
    ```

## Usage
1. Run the game:
    ```sh
    java -jar target/WarZoneGame.jar
    ```
2. Follow the in-game instructions to start playing.

## Documentation
Detailed documentation can be found in the [documentation](documentation) folder, including:
- Game rules and objectives
- Instructions on how to play
- Developer notes and design decisions

## CI/CD Pipeline
The project uses a Continuous Integration/Continuous Deployment (CI/CD) [pipeline](https://github.com/JSM2512/WarZone-Game/actions) to ensure code quality and facilitate seamless deployment. The pipeline includes:
- **Automated Testing**: Ensures new changes do not break existing functionality.
- **Build Automation**: Automatically builds the project with each commit.
- **Deployment**: Deploys the latest version to a specified environment.

## Contributing
Contributions are welcome! Please fork the repository and create a pull request with your changes. Ensure your code adheres to the project's coding standards and includes appropriate tests.

## Release
This project is released in three parts. See the [RELEASE](https://github.com/JSM2512/WarZone-Game/releases) file for more details.

## Authors
- [JSM2512](https://github.com/JSM2512)
- [Contributors](https://github.com/JSM2512/WarZone-Game/graphs/contributors)

## Contact
For any questions or feedback, please open an issue in this repository or contact the authors directly.

