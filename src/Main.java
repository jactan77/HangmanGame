
import java.util.Scanner;
public class Main{
    public static void main(String[]args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Write the word");
        String word = sc.nextLine();
        Game game = new Game(word);
        game.Play();
    }
}