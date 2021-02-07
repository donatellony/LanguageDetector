import java.util.ArrayList;

public class LangPerceptron {
    private double[] startVector;
    private double bias;
    private double alpha;
    private double theta;
    private int localErrorCounter;

    String language;
    private ArrayList<LangData> data;

    LangPerceptron(String language, ArrayList<LangData> data, double alpha, double theta) {
        this.alpha = alpha;
        this.data = data;
        this.language = language;
        this.theta = theta;
        startVector = new double[26];
        boolean[] isMinus = new boolean[startVector.length];
        for (int i = 0; i < startVector.length; i++) {
            if(Math.random()>=0.5) isMinus[i]=true;
        }
        for (int i = 0; i < startVector.length; i++){
            startVector[i] = Math.random();
            if(isMinus[i])
                startVector[i] = -startVector[i];
        }
        bias = Math.random();
        train(alpha);
    }


    public void train(double alpha) {
//        double[] startVectorBefore = new double[startVector.length];
//        int counter = 0;
        do {
            localErrorCounter = 0;
//            for (int i = 0; i < startVector.length; i++) {
//                startVectorBefore[i] = startVector[i];
//            }


            for (int i = 0; i < data.size(); i++) {
                correctWeights(data.get(i), alpha);
                System.out.println("LEC: " + localErrorCounter);
                System.out.println("=======================\nI = " + i + " LANG: " + language);
                for (int j = 0; j < startVector.length; j++) {
                    System.out.println("W"+j +" = "+ startVector[j]);
                }
                System.out.println("Bias: " + bias);
            }


//            System.out.println("I = " +counter++);
        }while (localErrorCounter!=0);

        System.out.println("Koncowy wektor wag: (" + language + ")");
        for (int j = 0; j < startVector.length; j++) {
            System.out.println("W"+j +" = "+ startVector[j]);
        }
        System.out.println("Bias: " + bias);
        System.out.println("==============================");
    }

    public void correctWeights(LangData testVector, double alpha) {
        double isExpectedLang;
//        System.out.println(testVector.language);
        if(testVector.language.equals(language))
            isExpectedLang = 1;
        else
            isExpectedLang = 0;

        double testedVector = p(testVector.data);
        for (int i = 0; i < startVector.length; i++) {
            startVector[i] = startVector[i] + alpha * (isExpectedLang - testedVector) * testVector.data[i];
        }
        if(isExpectedLang - testedVector!=0)
            localErrorCounter++;
        bias = bias+alpha*(isExpectedLang-testedVector);
    }

    public double p(double[] testVector) {
        double result = 0.0;
        for (int i = 0; i < testVector.length; i++) {
            result += startVector[i] * testVector[i];
        }
        result+=bias;
//        System.out.println("Result: "+ result);
        if (result > theta)
            return 1.0;
        return 0.0;
    }


}
