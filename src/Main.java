import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        PerceptronController pc = new PerceptronController("data", 0.01, 0);
//        pc.testTxt("testTexts/SpanishTest2.txt");
        run();
    }

    public static void run() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Hej :)");
            System.out.println("Wyszukiwane języki można dodać/zmienić poprzez zmianę zawartości pliku data");
            System.out.println("Standardowe teksty dla testowania znajdują się w pliku testTexts");
            System.out.println();
//            String srcFile = scanner.nextLine();
            System.out.println("Prosze wprowadzic długość kroku (n.p. 0.01)");
            String alpha = scanner.nextLine();
            PerceptronController pc = new PerceptronController("data", Double.valueOf(alpha), 0);
            boolean running = true;
            System.out.println("Gotowe!");
            System.out.println("Są dostępne następujące komendy: ");
            while (running) {
                System.out.println("1 - testujemy plik txt");
                System.out.println("2 - testujemy wprowadzony ręcznie tekst");
                System.out.println("3 - testujemy katałog z plikami txt");
                System.out.println("exit - wyjscie");
                String msg = scanner.nextLine().toLowerCase();
                if (msg.equals("exit")) {
                    running = false;
                }
                if (msg.equals("1")) {
                    System.out.println("Czy chcesz przetestować plik ze standardowego katałogu? (testTexts)");
                    System.out.println("1 - tak\n2 - nie");
                    String taknie = scanner.nextLine();
                    String src = "";
                    if (taknie.equals("1")) {
                        src = "testTexts\\";
                        System.out.println("Proszę wprowadzić nazwę pliku w testTexts");
                    } else
                        System.out.println("Prosze wprowadzić scieżke do pliku");
                    //String src = "iris_test.txt";
                    src += scanner.nextLine();
                    pc.testTxt(src);
                }
                if (msg.equals("2")) {
                    System.out.println("Proszę wpisać tekst do konsoli\nPo wpisaniu tekstu wpisz *!;");
                    StringBuilder sb = new StringBuilder();
                    String text;
                    while (!(text = scanner.nextLine()).equals("*!;"))
                        sb.append(text);

                    System.out.println(sb.toString());
                    pc.testString(sb.toString());
                }
                if (msg.equals("3")) {
                    System.out.println("Prosze wprowadzić scieżkę do katałogu");
                    String dir = scanner.nextLine();
                    pc.testDir(dir);
                }
            }
            scanner.close();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }
}
