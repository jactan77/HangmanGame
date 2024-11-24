
# Hangman Game

**Hangman Game** is a JavaFX application that combines the classic gameplay of Hangman with modern features such as dynamic word categories, animations, and sound effects.

---

## Overview

The game allows players to guess words by selecting letters, using predefined or custom categories. Incorrect guesses trigger hangman animations, adding suspense and visual engagement.

---

## Features

- **Dynamic Word Categories**: Predefined and user-created word sets stored in an SQLite database.
- **Interactive Animations**: Visual hangman drawings progress with incorrect guesses.
- **Custom Word Sets**: Users can create and manage personalized word categories.
- **Sound Effects**: Provides auditory feedback for interactions.
- **Database Integration**: SQLite backend for managing words and categories.
- **User-Friendly Design**: Intuitive JavaFX UI for smooth navigation.
- **Educational Value**: Ideal for vocabulary building and entertainment.

---

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/hangman-game.git
   cd hangman-game
   ```

2. Open the project in a Java IDE (e.g., IntelliJ IDEA, Eclipse).

3. Configure dependencies:
    - **SQLite JDBC**: Ensure the SQLite library is included in your classpath.
    - **JavaFX**: Install JavaFX SDK and set it up in your IDE.

4. Ensure the database file `words2.db` is located at:
   ```
   src/main/resources/com/example/hangmangame/database/
   ```

5. Build and run the application.

---

## How to Play

1. **Select a Category**: Choose from predefined or custom categories.
2. **Guess Letters**: Select letters from the on-screen grid.
3. **Avoid Mistakes**: Limit of 10 incorrect guesses before the game ends.
4. **Win the Game**: Guess the word before reaching the mistake limit.

---

## Project Structure

- **`Main.java`**: Application entry point, managing scenes and lifecycle.
- **`MenuController.java`**: Handles main menu navigation.
- **`CategoryViewController.java`**: Manages category selection and button generation.
- **`Controller.java`**: Core gameplay logic (letter selection, feedback, animations).
- **`Db.java`**: Database operations (retrieving, adding, and deleting words/categories).
- **`HangmanAnimation.java`**: Visual hangman drawing for incorrect guesses.
- **`UISoundEffects.java`**: Sound effects for interactive feedback.

---

## Dependencies

- **JavaFX**: UI and animation library.
- **SQLite JDBC**: For database integration.
- **FormsFX**: Manages forms and input validation.
- **JUnit**: Unit testing framework.
- **JavaFX Media**: Provides sound effects.

---

## Features in Development

- **Leaderboards**: Track and compare player scores.
- **Multiplayer Mode**: Play with friends in real-time.
- **Hints System**: Help players with difficult words.
- **Difficulty Levels**: Adjustable challenge settings.
- **Score Tracking**: Log progress to motivate improvement.

---

## Contributions

Contributions are welcome! Open an issue or submit a pull request to enhance the project.

---

## License

This project is licensed under the [MIT License](LICENSE).

---

Enjoy playing **Hangman Game** while expanding your vocabulary!

**Note**: Ensure your JavaFX and SQLite environments are configured correctly before running the application.
