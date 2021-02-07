import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PerceptronController {
    String dataDir;
    ArrayList<LangData> allData;
    ArrayList<LangPerceptron> perceptrons;
    double alpha, theta;

    PerceptronController(String dataDir, double alpha, double theta) {
        this.alpha = alpha;
        this.theta = theta;
        this.dataDir = dataDir;
        allData = new ArrayList<>();
        perceptrons = new ArrayList<>();

        try {
            Stream<Path> files = Files.walk(Paths.get(dataDir));
            List<Path> list = files.filter(e -> !e.getFileName().toString().matches("\\S+\\.txt")).collect(Collectors.toList());


            for (int i = 1; i < list.size(); i++) {
                allData.addAll(new DirToVectorConverter(list.get(i).toString()).getData());
            }

            for (int i = 1; i < list.size(); i++) {
                perceptrons.add
                        (
                                new LangPerceptron(list.get(i).toString().replaceAll("data", "").substring(1),
                                        allData,
                                        alpha,
                                        theta)
                        );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //                perceptrons.add
//                        (
//                                new LangPerceptron(list.get(i).toString().replaceAll("data", "").substring(1),
//                                        new DirToVectorConverter(list.get(i).toString()).getData(),
//                                        alpha,
//                                        theta)
//                        );

    void testTxt(String src) {
        double[] testVector = FileToVectorConverter.convert(src);
        double[] results = new double[perceptrons.size()];
        boolean isDetected = false;
        for (int i = 0; i < perceptrons.size(); i++) {
            results[i] = perceptrons.get(i).p(testVector);
        }
        for (int i = 0; i < perceptrons.size(); i++) {
            if (results[i] == 1) {
                System.out.println("Language detected! It is " + perceptrons.get(i).language);
                isDetected = true;
                break;
            }
        }
        if (!isDetected)
            System.out.println("None of the languages were detected");

    }

    void testString(String string) {
        string = string.toLowerCase().replaceAll("[^a-z]", "");
        double[] testVector = DirToVectorConverter.countLetters(string);
        double[] results = new double[perceptrons.size()];
        boolean isDetected = false;
        for (int i = 0; i < perceptrons.size(); i++) {
            results[i] = perceptrons.get(i).p(testVector);
//            System.out.println("I = " + i);
        }
        for (int i = 0; i < perceptrons.size(); i++) {
            if (results[i] == 1) {
                System.out.println("Language detected! It is " + perceptrons.get(i).language);
                isDetected = true;
                break;
            }
        }
        if (!isDetected)
            System.out.println("None of the languages were detected");
    }

    void testDir(String dir) {
        ArrayList<double[]> data = new DirToVectorConverter(dir).getRawData();
        for (int i = 0; i < data.size(); i++) {
            boolean isDetected = false;
            for (int j = 0; j < perceptrons.size(); j++) {
                if (perceptrons.get(j).p(data.get(i)) == 1) {
                    System.out.println(i + 1 + " language is " + perceptrons.get(j).language);
                    isDetected = true;
                }
            }
            if (!isDetected)
                System.out.println(i+1+": None of the languages were detected");
        }
    }

}
