import meter.PasswordStrengthMeter;
import trainer.PCFGTrainer;

/**
 * @title: Application
 * @description:
 * @author: Felix
 **/

public class Application {

    public static void main(String[] args) {
        PCFGTrainer pcfgTrainer = new PCFGTrainer();
        pcfgTrainer.train();
//        PasswordStrengthMeter passwordStrengthMeter = new PasswordStrengthMeter();    //  deserialization is too time-consuming
        PasswordStrengthMeter passwordStrengthMeter = new PasswordStrengthMeter(pcfgTrainer.getGrammarMap(),
                pcfgTrainer.getSegmentMap(), pcfgTrainer.getMinGrammarProbability(), pcfgTrainer.getMinSegmentProbability());
        passwordStrengthMeter.run();
    }

}
