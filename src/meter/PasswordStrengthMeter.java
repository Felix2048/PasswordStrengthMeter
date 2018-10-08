package meter;

import entity.Threshold;
import rules.Grammar;
import rules.ProductionRule;
import rules.Segment;
import util.KeyboardPatternUtil;
import util.SerializationUtil;

import java.util.*;

/**
 * @title: PasswordStrengthMeter
 * @description:
 * @author: Felix
 **/

public class PasswordStrengthMeter {

    private KeyboardPatternUtil keyboardPatternUtil = new KeyboardPatternUtil();

    private Map<ProductionRule, Grammar> grammarMap;

    private Map<ProductionRule, Segment> segmentMap;

//    private List<Guess> guesses;
//
//    private List<String> passwordGuesses = new ArrayList<>();

    private List<Threshold> thresholdList = new ArrayList<>();

//    final int ASCIIPrintableCharacterCount = 96;
//
//    final int GuessPerSecond = 30000000;

    private double minGrammarProbability;

    private double minSegmentProbability;

    public PasswordStrengthMeter() {
        init();
    }

    public PasswordStrengthMeter(Map<ProductionRule, Grammar> grammarMap, Map<ProductionRule, Segment> segmentMap, double minGrammarProbability, double minSegmentProbability) {
        this.grammarMap = grammarMap;
        this.segmentMap = segmentMap;
        this.minGrammarProbability = minGrammarProbability;
        this.minSegmentProbability = minSegmentProbability;
        init();
    }

    public PasswordStrengthMeter(Map<ProductionRule, Grammar> grammarMap, Map<ProductionRule, Segment> segmentMap) {
        init();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input your password: ");
        while (scanner.hasNextLine()) {
            String password = scanner.nextLine();
            estimate(password);
        }
    }

    private void init() {
        long startTime = System.currentTimeMillis();
        System.out.println("Start initializing ...");
//        try {
//            grammarMap = (Map<ProductionRule, Grammar>) SerializationUtil.deserialize("res/grammar");
//            segmentMap = (Map<ProductionRule, Segment>) SerializationUtil.deserialize("res/segment");
//            minGrammarProbability = (double) SerializationUtil.deserialize("res/minGrammarProbability");
//            minSegmentProbability = (double) SerializationUtil.deserialize("res/minSegmentProbability");
//
////            guesses = (List<Guess>) SerializationUtil.deserialize("res/segment");
////            for (Guess entity : guesses) {
////                passwordGuesses.add(entity.getPassword());
////            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //  initialize threshold
        Threshold weak = new Threshold(1.8e+9, 1.31e-9, 1, "weak" );
        Threshold fair = new Threshold(4.32e+10, 1.59e-16, 24, "fair" );
        Threshold strong = new Threshold(1.872e+11, 4.7e-20, 104, "strong" );
        Threshold veryStrong = new Threshold(1.872e+11, 4.7e-20, 104, "very strong" ); //  pth > veryVeryStrong

        thresholdList.add(weak);
        thresholdList.add(fair);
        thresholdList.add(strong);
        thresholdList.add(veryStrong);

        long endTime = System.currentTimeMillis();
        double executeTime = (double) (endTime - startTime) / 1000;
        System.out.println("Initialization complete in " + executeTime + "second(s).");
    }

    private void estimate(String password) {
        double probability = estimateProbability(password);
//        double guessesCount = estimateGuessesCount(password);
//        double crackTime = estimateCrackTime(guessesCount);
        Threshold result = reject(probability);

        System.out.println("Your chosen password：" + password + " is " + result.getInfo());
        System.out.println("Probability: " + probability);
        if (!result.getInfo().equals("very strong")) {
            System.out.println("Guesses Count: less than " + result.getGuessCount());
            System.out.println("Crack Time: less than " + result.getTimeInHours() + " hour(s)");
        }
        else {
            System.out.println("Guesses Count: greater than " + result.getGuessCount());
            System.out.println("Crack Time: greater than " + result.getTimeInHours() + " hour(s)");
        }

    }

    private Threshold reject(double probability) {
        Threshold result = thresholdList.get(0);    //  weak
        for(Threshold threshold : thresholdList) {
            result = threshold;
            if(probability >= threshold.getProbability()) {
                return result;
            }
        }
        return result;
    }

//    private double estimateCrackTime(double guessesCount) {
//        return (double) guessesCount / GuessPerSecond;
//    }

//    private double estimateGuessesCount(String password) {
//        double guessesCount;
//        if (passwordGuesses.contains(password)) {
//            guessesCount = passwordGuesses.indexOf(password) + 1;
//        }
//        else {
//            guessesCount = Math.pow(ASCIIPrintableCharacterCount, password.length());
//        }
//        return guessesCount;
//    }

    private double estimateProbability(String password) {
        double probability = 1.0;
        StringBuilder structure = new StringBuilder();
        for(int i = 0; i < password.length(); i++) {
            String keyboard = keyboardPatternUtil.substringkeyboradpattern(password.substring(i));
            if (!keyboard.isEmpty()) {  //  Check if password[i .. j] contains a keyboard pattern
                int keyboardCount = keyboard.length();
                i += keyboardCount - 1;

                String lhs = "K" + keyboardCount;
                structure.append(lhs);
                ProductionRule productionRule = new ProductionRule(lhs, keyboard);
                if (segmentMap.containsKey(productionRule)) {   //  add to segmentMap
                    probability *= segmentMap.get(productionRule).getProbability();
                }
                else {
                    probability *= minSegmentProbability;  //  not found in PCFG model
                }

            }
            else if (Character.isLowerCase(password.charAt(i))) {   //  Check if password[i] is an alphabet
                int alphabetCount = 1;
                while (i + alphabetCount < password.length() && Character.isLowerCase(password.charAt(i + alphabetCount))) {
                    alphabetCount++;
                }
                String terminalSymbols = password.substring(i, i + alphabetCount);    //  get terminal symbols
                i += alphabetCount - 1;

                String lhs = "A" + alphabetCount;
                structure.append(lhs);
                ProductionRule productionRule = new ProductionRule(lhs, terminalSymbols);
                if (segmentMap.containsKey(productionRule)) {   //  add to segmentMap
                    probability *= segmentMap.get(productionRule).getProbability();
                }
                else {
                    probability *= minSegmentProbability;  //  not found in PCFG model
                }
            }
            else if (Character.isUpperCase(password.charAt(i))) {   //  Check if password[i] is a capital
                int capitalCount = 1;
                while (i + capitalCount < password.length() && Character.isUpperCase(password.charAt(i + capitalCount))) {
                    capitalCount++;
                }
                String terminalSymbols = password.substring(i, i + capitalCount);    //  get terminal symbols
                i += capitalCount - 1;

                String lhs = "C" + capitalCount;
                structure.append(lhs);
                ProductionRule productionRule = new ProductionRule(lhs, terminalSymbols);
                if (segmentMap.containsKey(productionRule)) {   //  add to segmentMap
                    probability *= segmentMap.get(productionRule).getProbability();
                }
                else {
                    probability *= minSegmentProbability;  //  not found in PCFG model
                }
            }
            else if (Character.isDigit(password.charAt(i))) {   //  Check if password[i] is a digit
                int digitCount = 1;
                while (i + digitCount < password.length() && Character.isDigit(password.charAt(i + digitCount))) {
                    digitCount++;
                }
                String terminalSymbols = password.substring(i, i + digitCount);    //  get terminal symbols
                i += digitCount - 1;

                String lhs = "D" + digitCount;
                structure.append(lhs);
                ProductionRule productionRule = new ProductionRule(lhs, terminalSymbols);
                if (segmentMap.containsKey(productionRule)) {   //  add to segmentMap
                    probability *= segmentMap.get(productionRule).getProbability();
                }
                else {
                    probability *= minSegmentProbability;  //  not found in PCFG model
                }
            }
            else {  //  Else, password[i] is special
                int specialCount = 1;
                while (i + specialCount < password.length()
                        && !Character.isDigit(password.charAt(i + specialCount))
                        && !Character.isAlphabetic(password.charAt(i + specialCount))) {
                    specialCount++;
                }
                String terminalSymbols = password.substring(i, i + specialCount);    //  get terminal symbols
                i += specialCount - 1;

                String lhs = "S" + specialCount;
                structure.append(lhs);
                ProductionRule productionRule = new ProductionRule(lhs, terminalSymbols);
                if (segmentMap.containsKey(productionRule)) {   //  add to segmentMap
                    probability *= segmentMap.get(productionRule).getProbability();
                }
                else {
                    probability *= minSegmentProbability;  //  not found in PCFG model
                }
            }
        }
        ProductionRule productionRule = new ProductionRule("S", structure.toString());
        if (grammarMap.containsKey(productionRule)) {   //  add to segmentMap
            probability *= grammarMap.get(productionRule).getProbability();
        }
        else {
            probability *= minGrammarProbability;  //  not found in PCFG model
        }
        return probability;
    }


}
