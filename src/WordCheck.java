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
    public boolean containsDigits(String input) {
        for(int i = 0; i < input.length(); i++){
            char a = input.charAt(i);
            if(!Character.isLetter(input.charAt(i))){
                return false;
            }
        }
        return true;
    }
}
