package trainer;

import rules.Grammar;
import rules.ProductionRule;
import rules.Segment;
import util.KeyboardPatternUtil;
import util.SerializationUtil;

import java.io.*;
import java.util.*;

/**
 * @title: PCFGTrainer
 * @description:
 * @author: Felix
 **/

public class PCFGTrainer {

    private KeyboardPatternUtil keyboardPatternUtil = new KeyboardPatternUtil();

    private Map<ProductionRule, Grammar> grammarMap = new HashMap<>();

    public Map<ProductionRule, Grammar> getGrammarMap() {
        return grammarMap;
    }

    private Map<ProductionRule, Segment> segmentMap = new HashMap<>();

    private double minGrammarProbability = 1.0;

    private double minSegmentProbability = 1.0;


//    private Map<String, List<String>> segmentIndexMap = new HashMap<>();

//    private List<Guess> guesses = new ArrayList<>();

    private long grammarCount = 0;

    private long segmentCount = 0;

    public void train() {
        try {
            long startTime = System.currentTimeMillis();
            System.out.println("Start training ...");

            //  Read file
            InputStream inputStream = new FileInputStream("res/rockyou");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            //  Read a password
            String password = null;
            int count = 0;
            while((password = bufferedReader.readLine()) != null) {
                    System.out.println(password);
                    extractInfo(password);
                    count++;
            }

            System.out.println("Calculating probability ...");
            calculateProbability();

//            System.out.println("Generating guesses ...");
//            generateGuesses();

            //  close
            inputStream.close();
            inputStreamReader.close();
            bufferedReader.close();

//            System.out.println("Serializing ...");
//            SerializationUtil.serialize(grammarMap, "res/grammar");
//            SerializationUtil.serialize(segmentMap, "res/segment");
////            SerializationUtil.serialize(segmentIndexMap, "res/segmentIndex")
////            SerializationUtil.serialize(guesses, "res/guesses");
//            SerializationUtil.serialize(minGrammarProbability, "res/minGrammarProbability");
//            SerializationUtil.serialize(minSegmentProbability, "res/minSegmentProbability");

            long endTime = System.currentTimeMillis();
            double executeTime = (double) (endTime - startTime) / 1000;
            System.out.println("Training complete in " + executeTime + "second(s): " + count + " data trained.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void generateGuesses() {
//        for(ProductionRule productionRule : grammarMap.keySet()) {
//            Guess entity = new Guess("", grammarMap.get(productionRule).getProbability());
//            generateGuesses(entity, productionRule.getRhs());
//        }
//        //  sort guesses
//        Comparator<Guess> guessComparator = new Comparator<Guess>() {
//            @Override
//            public int compare(Guess o1, Guess o2) {
//                if(o1.getProbability() < o2.getProbability()){  //  descend order
//                    return 1;
//                }
//                else {
//                    return -1;
//                }
//            }
//        };
//        guesses.sort(guessComparator);
//    }
//
//    private void generateGuesses(Guess entity, String remainingGrammar) {
//        if(!remainingGrammar.isEmpty()) {
//            String lhs;
//            if (remainingGrammar.length() > 2) {
//                lhs = remainingGrammar.substring(0, 2);
//                remainingGrammar = remainingGrammar.substring(2);
//            }
//            else {
//                lhs = remainingGrammar;
//                remainingGrammar = "";
//            }
//            List<String> rhsList = segmentIndexMap.get(lhs);
//            for (String rhs: rhsList) {
//                String password = entity.getPassword() + rhs;
//                ProductionRule productionRule = new ProductionRule(lhs, rhs);
//                double probability = entity.getProbability() * segmentMap.get(productionRule).getProbability();
//                generateGuesses(new Guess(password, probability), remainingGrammar);
//            }
//        }
//        else {
//            guesses.add(entity);
//        }
//    }

    private void extractInfo(String password) {
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
                    segmentMap.get(productionRule).addCount();
                }
                else {
                    Segment segment = new Segment(productionRule);
                    segmentMap.put(productionRule, segment);
                }
//                if(!segmentIndexMap.containsKey(lhs)) {   //  add to segmentListMap for index
//                    segmentIndexMap.put(lhs, new ArrayList<>());
//                }
//                if(!segmentIndexMap.get(lhs).contains(keyboard)) {
//                    segmentIndexMap.get(lhs).add(keyboard);
//                }
                segmentCount++;
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
                    segmentMap.get(productionRule).addCount();
                }
                else {
                    Segment segment = new Segment(productionRule);
                    segmentMap.put(productionRule, segment);
                }
//                if(!segmentIndexMap.containsKey(lhs)) {   //  add to segmentListMap for index
//                    segmentIndexMap.put(lhs, new ArrayList<>());
//                }
//                if(!segmentIndexMap.get(lhs).contains(terminalSymbols)) {
//                    segmentIndexMap.get(lhs).add(terminalSymbols);
//                }
                segmentCount++;
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
                    segmentMap.get(productionRule).addCount();
                }
                else {
                    Segment segment = new Segment(productionRule);
                    segmentMap.put(productionRule, segment);
                }
//                if(!segmentIndexMap.containsKey(lhs)) {   //  add to segmentListMap for index
//                    segmentIndexMap.put(lhs, new ArrayList<>());
//                }
//                if(!segmentIndexMap.get(lhs).contains(terminalSymbols)) {
//                    segmentIndexMap.get(lhs).add(terminalSymbols);
//                }
                segmentCount++;
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
                    segmentMap.get(productionRule).addCount();
                }
                else {
                    Segment segment = new Segment(productionRule);
                    segmentMap.put(productionRule, segment);
                }
//                if(!segmentIndexMap.containsKey(lhs)) {   //  add to segmentListMap for index
//                    segmentIndexMap.put(lhs, new ArrayList<>());
//                }
//                if(!segmentIndexMap.get(lhs).contains(terminalSymbols)) {
//                    segmentIndexMap.get(lhs).add(terminalSymbols);
//                }
                segmentCount++;
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
                    segmentMap.get(productionRule).addCount();
                }
                else {
                    Segment segment = new Segment(productionRule);
                    segmentMap.put(productionRule, segment);
                }
//                if(!segmentIndexMap.containsKey(lhs)) {   //  add to segmentListMap for index
//                    segmentIndexMap.put(lhs, new ArrayList<>());
//                }
//                if(!segmentIndexMap.get(lhs).contains(terminalSymbols)) {
//                    segmentIndexMap.get(lhs).add(terminalSymbols);
//                }
                segmentCount++;
            }
        }
        ProductionRule productionRule = new ProductionRule("S", structure.toString());
        if (grammarMap.containsKey(productionRule)) {
            grammarMap.get(productionRule).addCount();
        }
        else {
            Grammar grammar = new Grammar(structure.toString());
            grammarMap.put(productionRule, grammar);
        }
        grammarCount++;
    }

    private void calculateProbability() {
        for(ProductionRule productionRule : grammarMap.keySet()) {
            double probability = (double) grammarMap.get(productionRule).getCount() / grammarCount;
            grammarMap.get(productionRule).setProbability(probability);
            if(minGrammarProbability > probability) {
                minGrammarProbability = probability;
            }
        }
        for(ProductionRule productionRule : segmentMap.keySet()) {
            double probability = (double) segmentMap.get(productionRule).getCount() / segmentCount;
            segmentMap.get(productionRule).setProbability(probability);
            if(minSegmentProbability > probability) {
                minSegmentProbability = probability;
            }
        }
    }

    public void setGrammarMap(Map<ProductionRule, Grammar> grammarMap) {
        this.grammarMap = grammarMap;
    }

    public Map<ProductionRule, Segment> getSegmentMap() {
        return segmentMap;
    }

    public void setSegmentMap(Map<ProductionRule, Segment> segmentMap) {
        this.segmentMap = segmentMap;
    }

    public double getMinGrammarProbability() {
        return minGrammarProbability;
    }

    public void setMinGrammarProbability(double minGrammarProbability) {
        this.minGrammarProbability = minGrammarProbability;
    }

    public double getMinSegmentProbability() {
        return minSegmentProbability;
    }

    public void setMinSegmentProbability(double minSegmentProbability) {
        this.minSegmentProbability = minSegmentProbability;
    }
}
