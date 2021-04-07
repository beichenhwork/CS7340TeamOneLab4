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

import java.io.*;
import java.util.*;

import javafx.util.Pair;
import org.jgap.*;
import org.jgap.audit.*;
import org.jgap.data.*;
import org.jgap.impl.*;
import org.jgap.xml.*;
import org.w3c.dom.*;
import model.*;

/**
 * This class provides an implementation of the classic "Make change" problem
 * using a genetic algorithm. The goal of the problem is to provide a
 * specified amount of change (from a cash purchase) in the fewest coins
 * possible. This example implementation uses American currency (quarters,
 * dimes, nickels, and pennies).
 * <p>
 * This example may be seen as somewhat significant because it demonstrates
 * the use of a genetic algorithm in a less-than-optimal problem space.
 * The genetic algorithm does best when there is a smooth slope of fitness
 * over the problem space towards the optimum solution. This problem exhibits
 * a more choppy space with more local optima. However, as can be seen from
 * running this example, the genetic algorithm still will get the correct
 * (or a very close) answer virtually everytime.
 *
 * @author Neil Rotstan
 * @author Klaus Meffert
 * @since 1.0
 */
public class MinimizingMakeChange {
  /** String containing the CVS revision. Read out via reflection!*/
  private final static String CVS_REVISION = "$Revision: 1.27 $";

  /**
   * The total number of times we'll let the population evolve.
   */
  private static final int MAX_ALLOWED_EVOLUTIONS = 50;

  public static EvolutionMonitor m_monitor;

  /**
   * Executes the genetic algorithm to determine the minimum number of
   * coins necessary to make up the given target amount of change. The
   * solution will then be written to System.out.
   *
   * @param a_targetChangeAmount the target amount of change for which this
   * method is attempting to produce the minimum number of coins
   * @param a_doMonitor true: turn on monitoring for later evaluation of
   * evolution progress
   *
   * @throws Exception
   *
   * @author Neil Rotstan
   * @author Klaus Meffert
   * @since 1.0
   */
  public static void makeChangeForAmount(int a_targetChangeAmount,
      boolean a_doMonitor)
      throws Exception {
    // Start with a DefaultConfiguration, which comes setup with the
    // most common settings.
    // -------------------------------------------------------------

    // For this lab, we have two path:
    // 1. sc1 -> sc2 -> sc3
    // 2. sc1 -> sc3
    // So we create two populations to evolve
    Configuration conf = new DefaultConfiguration();

    // Care that the fittest individual of the current population is
    // always taken to the next generation.
    // Consider: With that, the pop. size may exceed its original
    // size by one sometimes!
    // -------------------------------------------------------------
    conf.setPreservFittestIndividual(true);
    conf.setKeepPopulationSizeConstant(false);

    // Set the fitness function we want to use, which is our
    // MinimizingMakeChangeFitnessFunction. We construct it with
    // the target amount of change passed in to this method.
    // ---------------------------------------------------------
    List<Pair<String, List<Pair<String, List<Pair<String, Double>>>>>> datalist = new ArrayList<>(ReadJsonFile.createJsonFile());


    FitnessFunction myFunc =
        new MinimizingMakeChangeFitnessFunction(a_targetChangeAmount);
    conf.setFitnessFunction(myFunc);

    if (a_doMonitor) {
      // Turn on monitoring/auditing of evolution progress.
      // --------------------------------------------------
      m_monitor = new EvolutionMonitor();
      conf.setMonitor(m_monitor);
    }
    // Now we need to tell the Configuration object how we want our
    // Chromosomes to be setup. We do that by actually creating a
    // sample Chromosome and then setting it on the Configuration
    // object. As mentioned earlier, we want our Chromosomes to each
    // have four genes, one for each of the coin types. We want the
    // values (alleles) of those genes to be integers, which represent
    // how many coins of that type we have. We therefore use the
    // IntegerGene class to represent each of the genes. That class
    // also lets us specify a lower and upper bound, which we set
    // to sensible values for each coin type.
    // --------------------------------------------------------------

    // get original dataset from input.json and save it into datalist map.


    // Create a n size sample genes where n equals to number of service cluster (datalist.size())
    // For here, n = 3 -> sc1 - sc2 - sc3
    Gene[] sampleGenes = new Gene[3];
    // Or n = 2 -> sc1 - sc3

    //create a sample service cluster gene
    for(int i = 0 ; i < datalist.size(); i++){
      try{
        //Map.Entry<String, Map<String , Map<String, Double>>> curSC = scs.next();
        // get service number for specific service cluster
        if(datalist.get(i).getKey().equals("SC1")){
          List<Pair<String, List<Pair<String, Double>>>> service = new ArrayList<>(datalist.get(i).getValue());
          int serviceNum = service.size();
          sampleGenes[0] = new IntegerGene(conf,1,serviceNum);
        }else if(datalist.get(i).getKey().equals("SC2")){
          List<Pair<String, List<Pair<String, Double>>>> service = new ArrayList<>(datalist.get(i).getValue());
          int serviceNum = service.size();
          sampleGenes[1] = new IntegerGene(conf,1,serviceNum);
        }else if(datalist.get(i).getKey().equals("SC3")){
          List<Pair<String, List<Pair<String, Double>>>> service = new ArrayList<>(datalist.get(i).getValue());
          int serviceNum = service.size();
          sampleGenes[2] = new IntegerGene(conf,1,serviceNum);

        } else{
          throw new Exception("You got an wrong service cluster.");
        }
      }catch (Exception e){
        e.printStackTrace();
      }
    }

    IChromosome sampleChromosome = new Chromosome(conf, sampleGenes);


    conf.setSampleChromosome(sampleChromosome);

    // Finally, we need to tell the Configuration object how many
    // Chromosomes we want in our population. The more Chromosomes,
    // the larger number of potential solutions (which is good for
    // finding the answer), but the longer it will take to evolve
    // the population (which could be seen as bad).
    // ------------------------------------------------------------
    conf.setPopulationSize(20);


    // Create random initial population of Chromosomes.
    // Here we try to read in a previous run via XMLManager.readFile(..)
    // for demonstration purpose only!
    // -----------------------------------------------------------------
    Genotype population;

    try {
      Document doc = XMLManager.readFile(new File("JGAPExample32.xml"));
      population = XMLManager.getGenotypeFromDocument(conf, doc);

    }
    catch (UnsupportedRepresentationException uex) {
      // JGAP codebase might have changed between two consecutive runs.
      // --------------------------------------------------------------
      population = Genotype.randomInitialGenotype(conf);

    }
    catch (FileNotFoundException fex) {
      population = Genotype.randomInitialGenotype(conf);

    }
    // Now we initialize the population randomly, anyway (as an example only)!
    // If you want to load previous results from file, remove the next line!
    // -----------------------------------------------------------------------
    population = Genotype.randomInitialGenotype(conf);


    Configuration.reset();
    Configuration conf1 = new DefaultConfiguration();

    conf1.setPreservFittestIndividual(true);
    conf1.setKeepPopulationSizeConstant(false);
    conf1.setFitnessFunction(myFunc);

    Gene[] sampleGenes1 = new Gene[2];

    for(int i = 0 ; i < datalist.size(); i++){
      try{
        //Map.Entry<String, Map<String , Map<String, Double>>> curSC = scs.next();
        // get service number for specific service cluster
        if(datalist.get(i).getKey().equals("SC1")){
          List<Pair<String, List<Pair<String, Double>>>> service = new ArrayList<>(datalist.get(i).getValue());
          int serviceNum = service.size();
          sampleGenes1[0] = new IntegerGene(conf1, 1,serviceNum);

        }else if(datalist.get(i).getKey().equals("SC3")){
          List<Pair<String, List<Pair<String, Double>>>> service = new ArrayList<>(datalist.get(i).getValue());
          int serviceNum = service.size();

          sampleGenes1[1] = new IntegerGene(conf1,1,serviceNum);
        }else {
          throw new Exception("You got an wrong service cluster.");
        }
      }catch (Exception e){
        e.printStackTrace();
      }
    }

    IChromosome sampleChromosome1 = new Chromosome(conf1, sampleGenes1);
    conf1.setSampleChromosome(sampleChromosome1);
    conf1.setPopulationSize(20);
    Genotype population1;

    try {
      Document doc = XMLManager.readFile(new File("JGAPExample32.xml"));

      population1 = XMLManager.getGenotypeFromDocument(conf1, doc);
    }
    catch (UnsupportedRepresentationException uex) {
      // JGAP codebase might have changed between two consecutive runs.
      // --------------------------------------------------------------

      population1 = Genotype.randomInitialGenotype(conf1);
    }
    catch (FileNotFoundException fex) {

      population1 = Genotype.randomInitialGenotype(conf1);
    }
    // Now we initialize the population randomly, anyway (as an example only)!
    // If you want to load previous results from file, remove the next line!
    // -----------------------------------------------------------------------

    population1 = Genotype.randomInitialGenotype(conf1);


    // Evolve the population. Since we don't know what the best answer
    // is going to be, we just evolve the max number of times.
    // ---------------------------------------------------------------
    long startTime = System.currentTimeMillis();
    for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {
      // Do evolve for the first workflow path
      if (!uniqueChromosomes(population.getPopulation())) {
        throw new RuntimeException("Invalid state in generation "+i);
      }
      if(m_monitor != null) {
        population.evolve(m_monitor);
      }
      else {
        population.evolve();
      }

      // Do evolve for the second workflow path
      if (!uniqueChromosomes(population1.getPopulation())) {
        throw new RuntimeException("Invalid state in generation "+i);
      }
      if(m_monitor != null) {
        population1.evolve(m_monitor);
      }
      else {
        population1.evolve();
      }
    }
    long endTime = System.currentTimeMillis();

    System.out.println("Total evolution time: " + ( endTime - startTime)
                       + " ms");
    // Save progress to file. A new run of this example will then be able to
    // resume where it stopped before! --> this is completely optional.
    // ---------------------------------------------------------------------

    // Represent Genotype as tree with elements Chromomes and Genes.
    // -------------------------------------------------------------
    DataTreeBuilder builder = DataTreeBuilder.getInstance();
    IDataCreators doc2 = builder.representGenotypeAsDocument(population);
    // create XML document from generated tree
    XMLDocumentBuilder docbuilder = new XMLDocumentBuilder();
    Document xmlDoc = (Document) docbuilder.buildDocument(doc2);
    XMLManager.writeFile(xmlDoc, new File("JGAPExample26.xml"));
    // Display the best solution we found.
    // -----------------------------------


    IChromosome bestSolutionSoFar = population.getFittestChromosome();
    double v1 = bestSolutionSoFar.getFitnessValue();
    IChromosome bestSolutionSoFar1 = population1.getFittestChromosome();
    double v2 = bestSolutionSoFar1.getFitnessValue();
    if(v1 > v2){
      System.out.println("The best solution is path 1 sc1 -> sc2 -> sc3 which has a fitness value of " +
              bestSolutionSoFar.getFitnessValue());
      bestSolutionSoFar.setFitnessValueDirectly(-1);
      System.out.println("It contains the following: ");
      System.out.println("\t" +"service 1"+
              MinimizingMakeChangeFitnessFunction.
                      getNumberOfCoinsAtGene(
                              bestSolutionSoFar, 0) + " .");
      System.out.println("\t" +"service 2"+
              MinimizingMakeChangeFitnessFunction.
                      getNumberOfCoinsAtGene(
                              bestSolutionSoFar, 1) + " .");
      System.out.println("\t" +"service 3"+
              MinimizingMakeChangeFitnessFunction.
                      getNumberOfCoinsAtGene(
                              bestSolutionSoFar, 2) + " .");
    }
    else{
      System.out.println("The best solution is path 2 sc1 -> sc3 which has a fitness value of " +
              bestSolutionSoFar1.getFitnessValue());
      bestSolutionSoFar1.setFitnessValueDirectly(-1);
      System.out.println("It contains the following: ");
      System.out.println("\t" +"service 1"+
              MinimizingMakeChangeFitnessFunction.
                      getNumberOfCoinsAtGene(
                              bestSolutionSoFar1, 0) + " .");
      System.out.println("\t" +"service 3"+
              MinimizingMakeChangeFitnessFunction.
                      getNumberOfCoinsAtGene(
                              bestSolutionSoFar1, 1) + " .");
    }


  }

  /**
   * Main method. A single command-line argument is expected, which is the
   * amount of change to create (in other words, 75 would be equal to 75
   * cents).
   *
   * @param args amount of change in cents to create
   * @throws Exception
   *
   * @author Neil Rotstan
   * @author Klaus Meffert
   * @since 1.0
   */
  public static void main(String[] args)
      throws Exception {
    if (args.length < 1) {
      System.out.println("Syntax: MinimizingMakeChange <amount>");
    }
    else {
      int amount = 0;
      try {
        amount = Integer.parseInt(args[0]);
      }
      catch (NumberFormatException e) {
        System.out.println(
            "The <amount> argument must be a valid integer value");
        System.exit(1);
      }
      if (amount < 1 ||
          amount >= MinimizingMakeChangeFitnessFunction.MAX_BOUND) {
        System.out.println("The <amount> argument must be between 1 and "
                           +
                           (MinimizingMakeChangeFitnessFunction.MAX_BOUND - 1)
                           + ".");
      }
      else {
        boolean doMonitor = false;
        if (args.length > 1) {
          String monitoring = args[1];
          if(monitoring != null && monitoring.equals("MONITOR")) {
            doMonitor = true;
          }
        }
        makeChangeForAmount(amount, doMonitor);
      }
    }
  }

  /**
   * @param a_pop the population to verify
   * @return true if all chromosomes in the populationa are unique
   *
   * @author Klaus Meffert
   * @since 3.3.1
   */
  public static boolean uniqueChromosomes(Population a_pop) {
    // Check that all chromosomes are unique
    for(int i=0;i<a_pop.size()-1;i++) {
      IChromosome c = a_pop.getChromosome(i);
      for(int j=i+1;j<a_pop.size();j++) {
        IChromosome c2 =a_pop.getChromosome(j);
        if (c == c2) {
          return false;
        }
      }
    }
    return true;
  }
}
