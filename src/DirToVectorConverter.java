import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DirToVectorConverter {
    String srcDirectory;
    String langName;
    LinkedList<String> texts;
    DirToVectorConverter(String srcDirectory){
        this.srcDirectory = srcDirectory;
        texts = new LinkedList<>();
        try {
            Stream<Path> files = Files.walk(Paths.get(srcDirectory));
            langName =  srcDirectory;
            List<Path> list = files.filter(e->e.getFileName().toString().matches("\\S+\\.txt")).collect(Collectors.toList());
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < list.size(); i++) {
                Files.readAllLines(list.get(i)).forEach(sb::append);
                texts.add(sb.toString().toLowerCase().replaceAll("[^a-z]", ""));
                sb = new StringBuilder();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static double[] countLetters(String txt){
        char[] chars = txt.toCharArray();
        double[] values = new double[26];
        int counter = 0;
        for (char i = 'a'; i < 'z'+1; i++) {
//            System.out.println("CurrentChar: " + i);
            for (int j = 0; j < chars.length; j++) {
                if(chars[j]==i){
                    values[counter]++;
                }
            }
            counter++;
        }
        for (int i = 0; i < values.length; i++) {
            values[i]=values[i]/chars.length;
//            System.out.println(values[i]);
        }
        return values;
    }
    ArrayList<LangData> getData(){
        ArrayList<LangData> tmp = new ArrayList<>();
        texts.forEach(e->tmp.add(new LangData(langName.substring(5), countLetters(e))));
        return tmp;
    }
    ArrayList<double[]> getRawData(){
        ArrayList<double[]> tmp = new ArrayList<>();
        texts.forEach(e->tmp.add(countLetters(e)));
        return tmp;
    }
}
