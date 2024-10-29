# Hangman Game

Welcome to the **Hangman Game**, a fun and interactive application designed to help you test and expand your vocabulary! This game challenges you to guess words, one letter at a time, while trying to avoid exceeding the maximum allowed mistakes. Perfect for anyone looking to enhance their vocabulary while enjoying a classic game.

## Overview

The Hangman Game allows players to set a target word and then guess the word by selecting letters on the screen. With each incorrect guess, players edge closer to the maximum allowed mistakes, making it a thrilling and educational experience.

### Key Features

- **Interactive Gameplay**: Players can input their own word, which adds a personal touch to each game. *(Note: Currently, users need to manually input a word. In future updates, a built-in word database with categorized topics will be available, covering areas like sports, medicine, technology, and more.)*
- **Sound Effects**: Audio feedback enhances the experience, playing a click sound whenever a letter is selected.
- **Error Feedback**: Players are notified of mistakes, and the game ends when the maximum allowed mistakes are reached.
- **User-Friendly Interface**: Visual cues guide the player, and guessed letters appear on the screen for easy tracking.
- **Educational Tool**: Ideal for language learners and anyone looking to build vocabulary while having fun.
- **Animated Hangman**: An upcoming feature that will display a hangman animation with each incorrect guess, adding visual feedback and tension as players approach the mistake limit.

## How to Play

1. **Set the Word**: When the game starts, you'll be prompted to enter a word for the other player to guess.
2. **Guess Letters**: Select letters one by one from the provided grid. Each correct guess will reveal the letter in the word display.
3. **Avoid Mistakes**: You are allowed up to 10 mistakes. After reaching the limit, the game ends with an option to restart.
4. **Win the Game**: If you guess all the letters correctly before reaching the mistake limit, you win the game!

## Installation and Setup

1. Clone this repository to your local machine.
2. Open the project in your Java IDE.
3. Ensure the required audio file `sound.wav` is in the `/com/example/hangmangame/sounds/` directory within your project structure.
4. Run the application from your IDE.

## Project Structure

The game is structured as follows:

- **Controller**: Handles all the core gameplay logic, including word input, letter guessing, sound effects, and game resets.
- **View (FXML)**: Contains the layout for the game interface, including the letter grid and word display.
- **AudioClip**: Adds sound feedback when letters are clicked, making gameplay more engaging.
- **Hangman Animation (Upcoming)**: An animated hangman will appear and progress with each incorrect guess, building suspense and enhancing the visual appeal.

## Future Development

This is an upcoming project with exciting future features in development, including:
- **Word Database**: A built-in database of categorized words will be added, allowing users to select from topics like sports, medicine, technology, and more, instead of manually entering a word.
- **Animated Hangman**: Watch the hangman animation progress with each incorrect guess, adding to the classic game experience.
- **Word Hints**: Provide hints for difficult words.
- **Difficulty Levels**: Add adjustable difficulty settings for varied challenge levels.
- **Score Tracking**: Track scores and statistics to encourage players to improve over time.

## Contributions

We welcome any contributions! Please feel free to open issues or submit pull requests to enhance the game.

---

Enjoy playing Hangman while expanding your vocabulary!

---

**Note**: This game is built using JavaFX, so ensure your Java environment is properly configured for JavaFX applications.
