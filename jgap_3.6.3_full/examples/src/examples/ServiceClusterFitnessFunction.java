/*
 * This file is part of JGAP.
 *
 * JGAP offers a dual license model containing the LGPL as well as the MPL.
 *
 * For licensing information please see the file license.txt included with JGAP
 * or have a look at the top of class org.jgap.Chromosome which representatively
 * includes the JGAP license policy applicable for any file delivered with JGAP.
 */
package examples;

import javafx.util.Pair;
import org.jgap.*;

import java.util.List;

/**
 * Sample fitness function for the MakeChange example.
 *
 * @author Neil Rotstan
 * @author Klaus Meffert
 * @since 1.0
 */
public class ServiceClusterFitnessFunction
        extends FitnessFunction {
    /**
     * String containing the CVS revision. Read out via reflection!
     */
    private final static String CVS_REVISION = "$Revision: 1.18 $";

    public static final int MAX_BOUND = 4000;
    public static List<Pair<String, List<Pair<String, List<Pair<String, Double>>>>>> datalist;

    public ServiceClusterFitnessFunction(List<Pair<String, List<Pair<String, List<Pair<String, Double>>>>>> datalist) {
        this.datalist = datalist;
    }

    /**
     * Determine the fitness of the given Chromosome instance. The higher the
     * return value, the more fit the instance. This method should always
     * return the same fitness value for two equivalent Chromosome instances.
     *
     * @param a_subject the Chromosome instance to evaluate
     * @return positive double reflecting the fitness rating of the given
     * Chromosome
     * @author Neil Rotstan, Klaus Meffert, John Serri
     * @since 2.0 (until 1.1: return type int)
     */
    public double evaluate(IChromosome a_subject) {
        // Take care of the fitness evaluator. It could either be weighting higher
        // fitness values higher (e.g.DefaultFitnessEvaluator). Or it could weight
        // lower fitness values higher, because the fitness value is seen as a
        // defect rate (e.g. DeltaFitnessEvaluator)
        boolean defaultComparation = a_subject.getConfiguration().
                getFitnessEvaluator().isFitter(2, 1);

        // The fitness value measures both how close the value is to the
        // target amount supplied by the user and the total number of coins
        // represented by the solution. We do this in two steps: first,
        // we consider only the represented amount of change vs. the target
        // amount of change and return higher fitness values for amounts
        // closer to the target, and lower fitness values for amounts further
        // away from the target. Then we go to step 2, which returns a higher
        // fitness value for solutions representing fewer total coins, and
        // lower fitness values for solutions representing more total coins.
        // ------------------------------------------------------------------

        double fitness;
        if (defaultComparation) {
            fitness = 0.0d;
        } else {
            fitness = MAX_BOUND / 2;
        }

        // parse the datalist and get each category max value.



        double s1_costMax = 0;
        double s1_timeMax = 0;

        double s2_costMax = 0;
        double s2_timeMax = 0;

        double s3_costMax = 0;
        double s3_timeMax = 0;

        //List<Pair<String, List<Pair<String, List<Pair<String, Double>>>>>> datalist

        for(int i = 0 ; i < datalist.size();i++){
            if(datalist.get(i).getKey().equals("SC1")){
                s1_costMax = getMaxValue(datalist.get(i).getValue());
                s1_timeMax = getMaxValue(datalist.get(i).getValue());
            }else if(datalist.get(i).getKey().equals("SC2")){
                s2_costMax = getMaxValue(datalist.get(i).getValue());
                s2_timeMax = getMaxValue(datalist.get(i).getValue());
            }else{
                s3_costMax = getMaxValue(datalist.get(i).getValue());
                s3_timeMax = getMaxValue(datalist.get(i).getValue());
            }
        }

        double best_costMax = s1_costMax + s2_costMax + s3_costMax;
        double best_timeMax = s1_timeMax + s2_timeMax + s3_timeMax;





        double input_s1_cost = 20;
        double input_s2_cost = 12;
        double input_s3_cost = 11;

        double input_s1_rel = 95;
        double input_s2_rel = 70;
        double input_s3_rel = 97;

        double input_s1_time = 2;
        double input_s2_time = 3;
        double input_s3_time = 9;

        double input_s1_avail = 95;
        double input_s2_avail = 70;
        double input_s3_avail = 97;







        double fc = 1 - ((input_s1_cost + input_s2_cost + input_s3_cost) / best_costMax);
        double fr = input_s1_rel * input_s2_rel + input_s3_rel;
        double ft = 1 - ((input_s1_time + input_s2_time + input_s3_time) / best_timeMax);
        double fa = input_s1_avail * input_s2_avail + input_s3_avail;
        double fitness_value = (fc * 0.35) + (fr * 0.1) + (ft * 0.2) + (fa * 0.35);


        // Make sure fitness value is always positive.
        // -------------------------------------------
        return Math.max(1.0d, fitness);
    }
    public static double getMaxValue(List<Pair<String, List<Pair<String, Double>>>> services){
        double res = 0.0;
        for(int i = 0 ; i < services.size();i++){
            for(int j = 0 ; j < services.get(i).getValue().size();j++){
                if(services.get(i).getValue().get(j).getKey().equals("cost")){
                    res = Math.max(res,services.get(i).getValue().get(j).getValue());
                }else if(services.get(i).getValue().get(j).getKey().equals("time")){
                    res = Math.max(res,services.get(i).getValue().get(j).getValue());
                }
            }
        }
        return res;
    }

    /**
     * Bonus calculation of fitness value.
     *
     * @param a_maxFitness       maximum fitness value appliable
     * @param a_changeDifference change difference in coins for the coins problem
     * @return bonus for given change difference
     * @author Klaus Meffert
     * @since 2.3
     */
    protected double changeDifferenceBonus(double a_maxFitness,
                                           int a_changeDifference) {
        if (a_changeDifference == 0) {
            return a_maxFitness;
        } else {
            // we arbitrarily work with half of the maximum fitness as basis for non-
            // optimal solutions (concerning change difference)
            if (a_changeDifference * a_changeDifference >= a_maxFitness / 2) {
                return 0.0d;
            } else {
                return a_maxFitness / 2 - a_changeDifference * a_changeDifference;
            }
        }
    }

    /**
     * Calculates the penalty to apply to the fitness value based on the ammount
     * of coins in the solution
     *
     * @param a_maxFitness maximum fitness value allowed
     * @param a_coins      number of coins in the solution
     * @return penalty for the fitness value base on the number of coins
     * @author John Serri
     * @since 2.2
     */
    protected double computeCoinNumberPenalty(double a_maxFitness, int a_coins) {
        if (a_coins == 1) {
            // we know the solution cannot have less than one coin
            return 0;
        } else {
            // The more coins the more penalty, but not more than the maximum fitness
            // value possible. Let's avoid linear behavior and use
            // exponential penalty calculation instead
            return (Math.min(a_maxFitness, a_coins * a_coins));
        }
    }

    /**
     * Calculates the total amount of change (in cents) represented by
     * the given potential solution and returns that amount.
     *
     * @param a_potentialSolution the potential solution to evaluate
     * @return The total amount of change (in cents) represented by the
     * given solution
     * @author Neil Rotstan
     * @since 1.0
     */
    public static int amountOfChange(IChromosome a_potentialSolution) {
        int numQuarters = getNumberOfCoinsAtGene(a_potentialSolution, 0);
        int numDimes = getNumberOfCoinsAtGene(a_potentialSolution, 1);


        return (numQuarters * 25) + (numDimes * 10);

    }

    /**
     * Retrieves the number of coins represented by the given potential
     * solution at the given gene position.
     *
     * @param a_potentialSolution the potential solution to evaluate
     * @param a_position          the gene position to evaluate
     * @return the number of coins represented by the potential solution at the
     * given gene position
     * @author Neil Rotstan
     * @since 1.0
     */
    public static int getNumberOfCoinsAtGene(IChromosome a_potentialSolution,
                                             int a_position) {
        Integer numCoins =
                (Integer) a_potentialSolution.getGene(a_position).getAllele();
        return numCoins.intValue();
    }

    /**
     * Returns the total number of coins represented by all of the genes in
     * the given potential solution.
     *
     * @param a_potentialsolution the potential solution to evaluate
     * @return total number of coins represented by the given Chromosome
     * @author Neil Rotstan
     * @since 1.0
     */
    public static int getTotalNumberOfCoins(IChromosome a_potentialsolution) {
        int totalCoins = 0;
        int numberOfGenes = a_potentialsolution.size();
        for (int i = 0; i < numberOfGenes; i++) {
            totalCoins += getNumberOfCoinsAtGene(a_potentialsolution, i);
        }
        return totalCoins;
    }
}
