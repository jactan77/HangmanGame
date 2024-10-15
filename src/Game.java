import java.util.Arrays;
import java.util.Scanner;

public class Game extends WordCheck {

    private final char[] wordArray;
    private final char[] maskedArray;
    private char playerLetters;
    private int mistakes;
    private final int maxMistakes = 10;
    private boolean state;
    private final Scanner scanner = new Scanner(System.in);
    private final WordCheck wordCheck = new WordCheck();

    public Game(String word){
        this.wordArray = word.toCharArray();
        this.maskedArray = word.toCharArray();
        this.mistakes = 0;
        Arrays.fill(wordArray, '_');
    }

    public void Show(){
        System.out.println("A word consists of " + this.wordArray.length + " characters");
        System.out.println(Arrays.toString(this.wordArray));
    }

    public void Play(){
        this.Show();
        do{
            System.out.println(new String(this.wordArray));
            System.out.println("\tAdd your character");
            this.playerLetters = scanner.next().charAt(0);
            this.state = wordCheck.checkGame(this.maskedArray,this.playerLetters, this.wordArray);
            if(!this.state){
                this.mistakes++;
                System.out.println("You've made a mistake, you've made it " + this.mistakes + " times already");
            } else {
                if((new String(this.wordArray)).contains("_")) {
                } else {
                    System.out.println(new String(this.wordArray));
                    System.out.println("You won!!!!");
                    break;
                }
            }
            if(this.mistakes == this.maxMistakes){
                System.out.println("Game over! You've made too many mistakes.");
                break;
            }
        }
        while(true);

    }

}
