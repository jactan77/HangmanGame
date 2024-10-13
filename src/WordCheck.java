public class WordCheck {
    public boolean checkGame(char[] wordArray, char guessedChar, char[] maskedWord) {
        for (int i = 0; i < wordArray.length; i++) {
            if (wordArray[i] == guessedChar) {
                wordArray[i] = 'X';
                maskedWord[i] = guessedChar;
                return true;
            }
        }
        return false;
    }

}
