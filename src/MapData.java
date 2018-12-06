import java.util.ArrayList;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TreeMap;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Parses a file, holds weather data, and calculates statistics
 * based upon that data.
 * 
 * @author Elijah Boulton
 * @version 2018-12-05
 * Project 3
 */

public class MapData
{
   
   /** HashMap that holds observations per data type. */
   private HashMap<String, ArrayList<Observation>> dataCatalog;
   
   /** EnumMap that holds Statistic type and Statistic based upon data type. */
   private EnumMap<StatsType, TreeMap<String, Statistics>> statistics;
   
   /** TreeMap that holds position data based upon data type. */
   private TreeMap<String, Integer> paramPositions;
   
   /** Number of observations that can be invalid and still maintain valid statistics. */
   private int NUMBER_OF_MISSING_OBSERVATIONS = 10;
   
   /** Marker that represents temperature at 9 meters in the data. */
   private String TA9M = "TA9M";
   
   /** Marker that represents temperature at 1.5 meters in the data. */
   private String TAIR = "TAIR";
   
   /** Marker that represents solar radiation in the data. */
   private String SRAD = "SRAD";
   
   /** Marker that represents station ID in the data. */
   private String STID = "STID";
   
   /** String holding the web site name Mesonet. */
   private String MESONET = "Mesonet";
   
   /** Contains the file name from which data is parsed. */
   private String fileName;
   
   /** Contains the date and time when the data was taken. */
   private GregorianCalendar utcDateTime;
   
   /**
    * Constructor, initializes utcDateTime, fileName, dataCatalog, statistics, and paramPositions.
    * 
    * @param year Year the data was taken.
    * @param month Month the data was taken.
    * @param day Day the data was taken.
    * @param hour Hour the data was taken.
    * @param minute Minute the data was taken.
    * @param directory The file location for the data.
    */
   public MapData(int year, int month, int day, int hour, int minute, String directory)
   {
      
      utcDateTime = new GregorianCalendar(year, month, day, hour, minute);
      fileName = this.createFileName(year,  month, day, hour, minute, directory);
      dataCatalog = new HashMap<String, ArrayList<Observation>>();
      statistics = new EnumMap<StatsType, TreeMap<String, Statistics>>(StatsType.class);
      paramPositions = new TreeMap<String, Integer>();
      
   }
    
   /**
    * Creates a file for a certain date and time.
    * 
    * @param year Year the data was taken.
    * @param month Month the data was taken.
    * @param day Day the data was taken.
    * @param hour Hour the data was taken.
    * @param minute Minute the data was taken.
    * @param directory The file location for the data.
    * 
    * @return fileName for a given date and time
    */
   public String createFileName(int year, int month, int day, int hour, int minute, String directory)
   {
      return String.format("%s/%04d%02d%02d%02d%02d.mdf", directory, year, month, day, hour, minute);
   }
   
   /**
    * Determines what column each data type is in and stores it.
    * 
    * @param inParamStr The type of data the method searches for.
    * @throws FileNotFoundException In case of an incorrect directory.
    * @throws IOException In case of an improperly formatted file.
    */
   private void parseParamHeader(String inParamStr) throws FileNotFoundException, IOException
   {
      
      BufferedReader br = new BufferedReader(new FileReader(inParamStr));
      
      br.readLine();
      br.readLine();
      String searchLine = br.readLine();
      
      String[] temp = searchLine.split("\\s+");
      
      for (int i = 2; i < temp.length; ++i)
      {
         paramPositions.put(temp[i], i);
      }
      
      br.close();
      
   }
   
   /**
    * Searches the TreeMapo for a certain param header and returns the integer
    * index at which that data is stored.
    * 
    * @param inParamStr The param header to be searched.
    * @return
    */
   public Integer getIndexOf(String inParamStr)
   {
      return paramPositions.get(inParamStr);
   }
   
   /**
    * Takes input from the file located at fileName, runs through the data and sorts it into
    * a HashMap dataCatalog that holds data for each data type. It then calculates statistics
    * for all data types.
    * 
    * @throws FileNotFoundException In case of an incorrect directory.
    * @throws IOException In case of an improperly formatted file.
    */
   public void parseFile() throws FileNotFoundException, IOException
   {
      
      this.parseParamHeader(fileName);
      
      BufferedReader br = new BufferedReader(new FileReader(fileName));
      ArrayList<String> str = new ArrayList<>();
      String temp;
      
      while((temp = br.readLine()) != null)
      {
         str.add(temp);
      }
      br.close();
      
      str.remove(0);
      str.remove(0);
      
      String[][] dataList = new String[str.size()][str.get(0).split("\\s+").length];
      
      for (int r = 0; r < dataList.length; ++r)
      {
         dataList[r] = str.get(r).split("\\s+");
      }
      
      
      for (int c = 2; c < dataList[0].length; ++c)
      {
         
         ArrayList<Observation> ObsList = new ArrayList<Observation>();
         
         for (int r = 1; r < dataList.length; ++r)
         {
            ObsList.add(new Observation(Double.parseDouble(dataList[r][c]), dataList[r][1]));
         }
         
         dataCatalog.put(dataList[0][c], ObsList);
         
      }
      
      this.prepareDataCatalog();
      this.calculateAllStatistics();
      
   }
   
   /**
    * Calls calculateStatistics to calculate statistics for all param headers
    * and sort them into trees, and adding them the the statistics EnumMap.
    */
   private void calculateAllStatistics()
   {
      
      TreeMap<String, Statistics> treeMax = new TreeMap<String, Statistics>();
      TreeMap<String, Statistics> treeMin = new TreeMap<String, Statistics>();
      TreeMap<String, Statistics> treeAvg = new TreeMap<String, Statistics>();
      TreeMap<String, Statistics> treeTot = new TreeMap<String, Statistics>();
      
      for (String key: paramPositions.keySet())
      {
         Statistics[] temp = calculateStatistics(key);
         for (Statistics s: temp)
         {
            if (s.getStatType() == StatsType.MAXIMUM)
               treeMax.put(key, s);
            else if (s.getStatType() == StatsType.MINIMUM)
               treeMin.put(key, s);
            else if (s.getStatType() == StatsType.AVERAGE)
               treeAvg.put(key, s);
            else
               treeTot.put(key, s);
         }
      }
      
      statistics.put(StatsType.MAXIMUM, treeMax);
      statistics.put(StatsType.MINIMUM, treeMin);
      statistics.put(StatsType.AVERAGE, treeAvg);
      statistics.put(StatsType.TOTAL, treeTot);
      
   }
   
   private void prepareDataCatalog()
   {
      dataCatalog.remove(null);
      dataCatalog.remove("");
   }
   
   /**
    * Removes invalid data and calculates statistics for a certain param header.
    * 
    * @param paramId The data type.
    * @return An array of Statistics to be used in calculateAllStatistics.
    */
   private Statistics[] calculateStatistics(String paramId)
   {
      int invCntr = 0;
      int i = 0;
      ArrayList<Double> valueList = new ArrayList<Double>();
      while(i < dataCatalog.get(paramId).size())
      {
         if (dataCatalog.get(paramId).get(i).getValue() < -900)
         {
            dataCatalog.get(paramId).remove(i);
            ++invCntr;
         }
         else
         {
            valueList.add(dataCatalog.get(paramId).get(i).getValue());
            ++i;
         }
      }
       
      if (invCntr >= NUMBER_OF_MISSING_OBSERVATIONS)
      {
         Statistics[] paramStats = {new Statistics(0.0, "Invalid " + STID, utcDateTime, 0, StatsType.MAXIMUM),
                  new Statistics(0.0, "Invalid " + STID, utcDateTime, 0, StatsType.MINIMUM),
                  new Statistics(0.0, "Invalid " + STID, utcDateTime, 0, StatsType.AVERAGE),
                  new Statistics(0.0, "Invalid " + STID, utcDateTime, 0, StatsType.TOTAL)};
         return paramStats;
      }
      else
      {
         double min = Double.MAX_VALUE;
         String minStid = "";
         double max = Double.MIN_VALUE;
         String maxStid = "";
         double total = 0.0;
         
         for (int i1 = 0; i1 < dataCatalog.get(paramId).size(); ++i1)
         {
            double d = dataCatalog.get(paramId).get(i1).getValue();
            String s = dataCatalog.get(paramId).get(i1).getStid();
            if(d > max)
            {
               max = d;
               maxStid = s;
            }
            if(d < min)
            {
               min = d;
               minStid = s;
               
            }
            total += d;
         }
         
         double average = (total / ((double) valueList.size()));
         
         if (paramId.equals("SRAD") || paramId.equals("RAIN"))
         {
            Statistics[] paramStats = {new Statistics(max, maxStid, utcDateTime, valueList.size(), StatsType.MAXIMUM),
                     new Statistics(min, minStid, utcDateTime, valueList.size(), StatsType.MINIMUM),
                     new Statistics(average, MESONET, utcDateTime, valueList.size(), StatsType.AVERAGE),
                     new Statistics(total, MESONET, utcDateTime, valueList.size(), StatsType.TOTAL)};
            return paramStats;
         }
         else
         {
            Statistics[] paramStats = {new Statistics(max, maxStid, utcDateTime, valueList.size(), StatsType.MAXIMUM),
                     new Statistics(min, minStid, utcDateTime, valueList.size(), StatsType.MINIMUM),
                     new Statistics(average, MESONET, utcDateTime, valueList.size(), StatsType.AVERAGE)};
            return paramStats;
         }
         
      }
      
   }
   
   
   public Statistics getStatistics(StatsType type, String paramId)
   {
      return statistics.get(type).get(paramId);
   }
   
   /**
    * Creates a string to represent the data from the file.
    * 
    * @return String containing averages, minimums, and maximums, and where they occurred
    */
   public String toString()
   {
      
      String str = String.format("=========================================================\n"
         + "=== %04d-%02d-%02d ===\n"
         + "=========================================================\n"
         + "Maximum Air Temperature(1.5m) = %.1f C at %s\n"
         + "Minimum Air Temperature(1.5m) = %.1f C at %s\n"
         + "Average Air Temperature(1.5m) = %.1f C at %s\n"
         + "=========================================================\n"
         + "=========================================================\n"
         + "Maximum Air Temperature(9.0m) = %.1f C at %s\n"
         + "Minimum Air Temperature(9.0m) = %.1f C at %s\n"
         + "Average Air Temperature(9.0m) = %.1f C at %s\n"
         + "=========================================================\n"
         + "=========================================================\n"
         + "Maximum Solar Radiation = %.1f W/M^2 at %s\n"
         + "Minimum Solar Radiation = %.1f W/M^2 at %s\n"
         + "Average Solar Radiation = %.1f W/M^2 at %s\n"
         + "Total Solar Radiation   = %.1f W/M^2 at %s\n"
         + "=========================================================\n", utcDateTime.get(1), utcDateTime.get(2),
         utcDateTime.get(5), statistics.get(StatsType.MAXIMUM).get(TAIR).getValue(), statistics.get(StatsType.MAXIMUM).get(TAIR).getStid(),
         statistics.get(StatsType.MINIMUM).get(TAIR).getValue(), statistics.get(StatsType.MINIMUM).get(TAIR).getStid(),
         statistics.get(StatsType.AVERAGE).get(TAIR).getValue(), statistics.get(StatsType.AVERAGE).get(TAIR).getStid(),
         statistics.get(StatsType.MAXIMUM).get(TA9M).getValue(), statistics.get(StatsType.MAXIMUM).get(TA9M).getStid(),
         statistics.get(StatsType.MINIMUM).get(TA9M).getValue(), statistics.get(StatsType.MINIMUM).get(TA9M).getStid(),
         statistics.get(StatsType.AVERAGE).get(TA9M).getValue(), statistics.get(StatsType.AVERAGE).get(TA9M).getStid(),
         statistics.get(StatsType.MAXIMUM).get(SRAD).getValue(), statistics.get(StatsType.MAXIMUM).get(SRAD).getStid(),
         statistics.get(StatsType.MINIMUM).get(SRAD).getValue(), statistics.get(StatsType.MINIMUM).get(SRAD).getStid(),
         statistics.get(StatsType.AVERAGE).get(SRAD).getValue(), statistics.get(StatsType.AVERAGE).get(SRAD).getStid(),
         statistics.get(StatsType.TOTAL).get(SRAD).getValue(), statistics.get(StatsType.TOTAL).get(SRAD).getStid());
      return str;
      
   }
   
}
