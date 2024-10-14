
import java.util.Scanner;
public class Main{
    public static void main(String[]args) {
        Scanner sc = new Scanner(System.in);
        WordCheck wordCheck = new WordCheck();
        System.out.println("Write the word");
        String word = sc.nextLine();
        boolean state = wordCheck.containsDigits(word);
        if(!state){
        Game game = new Game(word);
        game.Play();
        }else {
            System.out.println("The input contains digits. Try again");
        }
    }
}