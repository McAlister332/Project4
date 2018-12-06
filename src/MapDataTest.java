import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the MapData class.
 * 
 * @author Elijah Boulton
 * @version 2018-10-16
 * Project 3
 */

public class MapDataTest
{
   
   @Test
   /**
    * Tests constructors, parseFile and other associated methods.
    * 
    * @throws FileNotFoundException
    * @throws IOException
    */
   public void testParseFile() throws FileNotFoundException, IOException {
      final int YEAR = 2018;
      final int MONTH = 8;
      final int DAY = 30;
      final int HOUR = 17;
      final int MINUTE = 45;

      final String directory = "data";

      MapData test = new MapData(YEAR, MONTH, DAY, HOUR, MINUTE, directory);
      test.parseFile();
      
      Assert.assertTrue(3 == test.getIndexOf("TIME"));
      
      Assert.assertEquals(test.getStatistics(StatsType.AVERAGE, "TAIR").getValue(), 32.4, .1);
      Assert.assertEquals(test.getStatistics(StatsType.MAXIMUM, "TAIR").getValue(), 36.5, .1);
      Assert.assertEquals(test.getStatistics(StatsType.MINIMUM, "TAIR").getValue(), 20.8, .1);
      
      Assert.assertEquals(test.getStatistics(StatsType.AVERAGE, "TA9M").getValue(), 31.6, .1);
      Assert.assertEquals(test.getStatistics(StatsType.MAXIMUM, "TA9M").getValue(), 34.9, .1);
      Assert.assertEquals(test.getStatistics(StatsType.MINIMUM, "TA9M").getValue(), 20.7, .1);
      
      Assert.assertEquals(test.getStatistics(StatsType.AVERAGE, "SRAD").getValue(), 828.1, .1);
      Assert.assertEquals(test.getStatistics(StatsType.MAXIMUM, "SRAD").getValue(), 968.0, .1);
      Assert.assertEquals(test.getStatistics(StatsType.MINIMUM, "SRAD").getValue(), 163.0, .1);
      Assert.assertEquals(test.getStatistics(StatsType.TOTAL, "SRAD").getValue(), 97720.0, .1);
      
   }
   
   @Test
   /**
    * Tests toString method.
    * 
    * @throws FileNotFoundException
    * @throws IOException
    */
   public void testToString() throws FileNotFoundException, IOException {
      
      final int YEAR = 2018;
      final int MONTH = 8;
      final int DAY = 30;
      final int HOUR = 17;
      final int MINUTE = 45;

      final String directory = "data";

      MapData test = new MapData(YEAR, MONTH, DAY, HOUR, MINUTE, directory);
      test.parseFile();
      
      Assert.assertTrue(test.toString().equals(String.format("=========================================================\n"
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
               + "=========================================================\n",
               2018, 8, 30, 36.5, "HOOK", 20.8, "MIAM", 32.4, "Mesonet", 34.9, "HOOK",
               20.7, "MIAM", 31.6, "Mesonet", 968.0, "SLAP", 163.0, "MIAM",  828.1,
               "Mesonet", 97720.0, "Mesonet")));
      
   }

}
