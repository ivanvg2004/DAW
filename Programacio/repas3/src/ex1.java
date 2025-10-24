import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ex1 {
    static void main() {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        listaNums(num);
    }

    private static void listaNums(int num) {
        ArrayList<Integer> res = new ArrayList<>();
        Random rnd = new Random();
        for (int i = 0; i < num; i++) {
            int ramdom = rnd.nextInt(101);
            res.add(ramdom);
            System.out.println(res.get(i));
        }
    }
}
