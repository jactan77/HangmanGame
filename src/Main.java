import jdk.jfr.Frequency;

import java.util.Arrays;
import java.util.Scanner;
public class Main{
    private static boolean check(char[] wordArray, char guessedChar, char[] maskedWord) {
        for (int i = 0; i < wordArray.length; i++) {
            if(wordArray[i] == guessedChar){
                wordArray[i] = 'X';
                maskedWord[i] = guessedChar;
                return true;
            }



        }
        return false;
    };
    public static void main(String[]args){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Write the word");
        String word = scanner.nextLine();
        char[] wordArray = word.toCharArray();
        char[] maskedWord  = word.toCharArray();
        int mistakes = 0;
        char playerLetters;
        System.out.println("A word consists of " + wordArray.length + " characters");
        for(int i = 0; i < wordArray.length; i++){
            wordArray[i] = '_';
        }
        String show = new String(wordArray);
        System.out.println(show);
        do{
            System.out.println("\tAdd your character");
            playerLetters = scanner.next().charAt(0);
            boolean state = check(maskedWord , playerLetters, wordArray);
                if(!state){
                    mistakes++;
                    System.out.println("You've made a mistake, you've made it " + mistakes + " times already");
                }else {
                   if((new String(wordArray)).contains("_")) {
                       System.out.println(new String(wordArray));
                   } else {
                       System.out.println(new String(wordArray));
                       System.out.println("You won!!!!");
                       break;
                   }
                }
        } while(true);

    }
}