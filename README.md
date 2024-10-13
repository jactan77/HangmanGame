
# HangmanGame

This is a simple word-guessing game, similar to the classic "Hangman," where the player has to guess the word one letter at a time. The player can make a limited number of incorrect guesses before losing the game.

## Features
- The program selects a word, and the player must guess the letters in the word.
- The game provides feedback on correct and incorrect guesses.
- Players have a limited number of mistakes they can make (default is 6).
- The game ends when the player either guesses the word correctly or exceeds the maximum allowed mistakes.

## How to Play
1. The game will prompt you to input a word at the start. This word is the one the player will try to guess.
2. The player guesses letters, one at a time.
3. If the guessed letter is in the word, it will be revealed in its position(s).
4. If the guessed letter is incorrect, the mistake counter increases.
5. The player wins by correctly guessing all the letters before reaching the maximum number of mistakes.
6. The player loses if they make too many incorrect guesses.

## Example Gameplay

```
Write the word:
> programming

A word consists of 11 characters
_ _ _ _ _ _ _ _ _ _ _

Add your character:
> p
p _ _ _ _ _ _ _ _ _ _

Add your character:
> z
You've made a mistake, you've made it 1 times already.

Add your character:
> o
p _ o _ _ _ _ _ _ _ _

...and so on
```

## Requirements

- Java 8 or higher.

## Running the Game

To run the game:

1. Clone the repository:

   ```bash
   git clone https://github.com/jactan77/HangmanGame.git
   ```

2. Navigate to the project directory:

   ```bash
   cd HangmanGame/src
   ```

3. Compile the `Main.java` file:

   ```bash
   javac Main.java
   ```

4. Run the game:

   ```bash
   java Main
   ```
## Unreleased

### Upcoming Changes
- **Improved OOP Structure:** Further refactor the code to separate game mechanics and user interaction logic, improving maintainability.


## Future Improvements

- Add the ability to randomly generate words for guessing.
- Implement a graphical user interface (GUI) for a more interactive experience.
- Add a score-tracking system for multiple rounds of gameplay.
- Enable multiplayer functionality.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

---

Enjoy the game and happy guessing!
