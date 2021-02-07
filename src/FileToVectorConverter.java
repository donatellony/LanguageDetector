import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileToVectorConverter {

    public static double[] convert(String file){
        StringBuilder sb = new StringBuilder();

        try {
            Files.readAllLines(Paths.get(file)).forEach(sb::append);
            String txt = sb.toString().toLowerCase().replaceAll("[^a-z]", "");
            return DirToVectorConverter.countLetters(txt);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
