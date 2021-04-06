package examples;

import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.*;

public class ReadJsonFile {
    public static void main(String[] args){
        createJsonFile();
    }
    public static List<Pair<String, List<Pair<String, List<Pair<String, Double>>>>>> createJsonFile() {

        StringBuffer stringBuffer = new StringBuffer();
        try {
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(new FileInputStream("./src/input.json")));
            String line;
            while((line=bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // start parse json file
        List<Pair<String, List<Pair<String, List<Pair<String, Double>>>>>> data = new ArrayList<>();

        Map<String, Map<String , Map<String, Double>>> datalist = new HashMap<>();
        try{
            String str = stringBuffer.toString();
            JSONObject chromosome = new JSONObject(str);
            int chromosomelength = chromosome.length();
            System.out.println("Chromosome length (Gene Num): "+chromosomelength);
            Iterator t = chromosome.keys();
            while(t.hasNext()) {
                List<Pair<String, List<Pair<String, Double>>>> curSC = new ArrayList<>();

                Map<String, Map<String, Double>> sc = new HashMap<>();
                String key = (String) t.next();
                System.out.println(key);
                JSONArray gene = chromosome.getJSONArray(key);
                System.out.println("Each gene(service cluster) length: " + gene.length());
                for (int i = 0; i < gene.length(); i++) {
                    List<Pair<String, Double>> curService = new ArrayList<>();

                    Map<String, Double> factor = new HashMap<>();
                    JSONObject geneValue = gene.getJSONObject(i);
                    Iterator service = geneValue.keys();
                    String key1 = "";
                    while(service.hasNext()){
                        key1 = (String) service.next();
                        System.out.println("Service: " + key1);
                        JSONObject factors = geneValue.getJSONObject(key1);
                        double cost = Double.valueOf(factors.get("cost").toString());
                        System.out.println("SC(service) factor 'cost': " + cost);
                        double reliability = Double.valueOf(factors.get("reliability").toString());
                        System.out.println("SC(service) factor 'reliability': " + reliability);
                        double time = Double.valueOf(factors.get("time").toString());
                        System.out.println("SC(service) factor 'time': " + time);
                        double availability = Double.valueOf(factors.get("availability").toString());
                        System.out.println("SC(service) factor 'availability': " + availability);

                        curService.add(new Pair<String, Double>("cost",cost));
                        curService.add(new Pair<String, Double>("reliability",reliability));
                        curService.add(new Pair<String, Double>("time",time));
                        curService.add(new Pair<String, Double>("availability",availability));

                        factor.put("cost", cost);
                        factor.put("reliability", reliability);
                        factor.put("time", time);
                        factor.put("availability",availability);
                        System.out.println("---------------");
                    }
                    curSC.add(new Pair<String, List<Pair<String, Double>>>(key1,curService));
                    sc.put(key1,factor);
                }
                data.add(new Pair<String, List<Pair<String, List<Pair<String, Double>>>>> (key,curSC));
                datalist.put(key,sc);
                System.out.println("---------------------------------");
            }
            System.out.println(data);
            System.out.println(datalist);
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }
}
