import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 * An extended Jframe that uses methods to organize all its components.
 * 
 * @author Elijah Boulton
 * @version 2018-12-06
 * Project 4
 */

public class MesonetFrame extends JFrame
{
   /** Serial Version UID. */
   private static final long serialVersionUID = 1L;
   
   /** Menu bar for the window. */
   JMenuBar menuBar = new JMenuBar();
   /** Menu list for the window. */
   JMenu fileMenu = new JMenu("File");
   /** Menu item for the window, chooses a file. */
   JMenuItem choosefile = new JMenuItem();
   /** Menu item for the window, exits the program. */
   JMenuItem exitprog = new JMenuItem();
   
   /** First panel for the window. */
   JPanel textPanel = new JPanel();
   /** Second panel for the window. */
   JPanel paramPanel = new JPanel();
   /** Third panel for the window. */
   JPanel statsPanel = new JPanel();
   /** Fourth panel for the window. */
   JPanel outPanel = new JPanel();
   /** Fifth panel for the window. */
   JPanel buttonPanel = new JPanel();
   
   /** JLabel containing a message to the user. */
   JLabel messageArea = new JLabel();
   
   /*
    * I'm not going to javadoc all of these. It's all the check boxes for
    * every data type.
    */
   
   JCheckBox stnm = new JCheckBox();
   JCheckBox time = new JCheckBox();
   JCheckBox relh = new JCheckBox();
   JCheckBox tair = new JCheckBox();
   JCheckBox wspd = new JCheckBox();
   JCheckBox wvec = new JCheckBox();
   JCheckBox wdir = new JCheckBox();
   JCheckBox wdsd = new JCheckBox();
   JCheckBox wssd = new JCheckBox();
   JCheckBox wmax = new JCheckBox();
   JCheckBox rain = new JCheckBox();
   JCheckBox pres = new JCheckBox();
   JCheckBox srad = new JCheckBox();
   JCheckBox ta9m = new JCheckBox();
   JCheckBox ws2m = new JCheckBox();
   JCheckBox ts10 = new JCheckBox();
   JCheckBox tb10 = new JCheckBox();
   JCheckBox ts05 = new JCheckBox();
   JCheckBox ts25 = new JCheckBox();
   JCheckBox ts60 = new JCheckBox();
   JCheckBox tr05 = new JCheckBox();
   JCheckBox tr25 = new JCheckBox();
   JCheckBox tr60 = new JCheckBox();
   
   /** Radio button for calculation of MAXIMUM statistics. */
   JRadioButton MAXIMUM = new JRadioButton("MAXIMUM");
   /** Radio button for calculation of MINIMUM statistics. */
   JRadioButton MINIMUM = new JRadioButton("MINIMUM");
   /** Radio button for calculation of AVERAGE statistics. */
   JRadioButton AVERAGE = new JRadioButton("AVERAGE");
   /** Radio button for calculation of TOTAL statistics. */
   JRadioButton TOTAL = new JRadioButton("TOTAL");
   
   /** The file to be read is initialized as a specific file from data. */
   File file = new File("201808301745.mdf");
   
   /** Column headers for the JTable. */
   final String[] headers = {"Station", "Parameter", "Statistic", "Value", "Reporting Stations", "Date"};
   /** Maximum rows in the JTable. */
   int numRows = 92 ;
   /** JTable for representing data to the user. */
   JTable output;
   /** ScrollPane for eash navigation fo the data. */
   JScrollPane scrollPanel;
   
   /** Button that calculates the data. */
   JButton calc = new JButton("Calculate");
   /** A button that exits the program. */
   JButton exit = new JButton("Exit");
   
   /**
    * Constructor sets up the JFrame and calls 6 handwritten methods for allocating
    * the components of the JFrame.
    */
   public MesonetFrame()
   {
      super("Mesonet WeatherStatistics Calculator");
      this.setLayout(null);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      this.setSize(1500, 750);
      this.setResizable(false);
      
      this.formatMenuBar();
      this.formatTextPanel();
      this.formatParamPanel();
      this.formatStatsPanel();
      this.formatOutPanel();
      this.formatButtonPanel();
      
      this.setVisible(true);
   }
   
   /**
    * Formats the Menu Bar.
    */
   public void formatMenuBar()
   {
      choosefile.setText("Choose Data File");
      exitprog.setText("Exit");
      
      choosefile.addActionListener(new FileActionListener());
      exitprog.addActionListener(new ExitActionListener());
      
      fileMenu.add(choosefile);
      fileMenu.add(exitprog);
      menuBar.add(fileMenu);
      this.setJMenuBar(menuBar);
   }
   
   /**
    * Formats the Welcome Text Panel.
    */
   public void formatTextPanel()
   {
      messageArea.setText("Mesonet - We don't set records, we report them!");
      textPanel.setLocation(800, 0);
      textPanel.setSize(400, 50);
      
      textPanel.setLayout(new GridLayout(1, 1));
      textPanel.add(messageArea);
      this.add(textPanel);
   }
   
   /**
    * Formats the panel that lists parameters in check boxes, and calls a method
    * to format the check boxes.
    */
   public void formatParamPanel()
   {
      paramPanel.setLocation(0, 50);
      paramPanel.setSize(100, 625);
      paramPanel.setLayout(new GridLayout(23, 0));
      paramPanel.setBorder(new TitledBorder(new EtchedBorder(), "Data Type"));
      
      
      formatCheckBoxes();
      paramPanel.add(stnm);
      paramPanel.add(time);
      paramPanel.add(relh);
      paramPanel.add(tair);
      paramPanel.add(wspd);
      paramPanel.add(wvec);
      paramPanel.add(wdir);
      paramPanel.add(wdsd);
      paramPanel.add(wssd);
      paramPanel.add(wmax);
      paramPanel.add(rain);
      paramPanel.add(pres);
      paramPanel.add(srad);
      paramPanel.add(ta9m);
      paramPanel.add(ws2m);
      paramPanel.add(ts10);
      paramPanel.add(tb10);
      paramPanel.add(ts05);
      paramPanel.add(ts25);
      paramPanel.add(ts60);
      paramPanel.add(tr05);
      paramPanel.add(tr25);
      paramPanel.add(tr60);
      

      paramPanel.setVisible(true);
      this.add(paramPanel);
      
      
   }
   
   /**
    * Formats the panel that holds statistic types and formats radio buttons.
    */
   public void formatStatsPanel()
   {
      statsPanel.setLocation(200, 50);
      statsPanel.setSize(100, 625);
      statsPanel.setLayout(new GridLayout(4, 1));
      statsPanel.setBorder(new TitledBorder(new EtchedBorder(), "Statistic Type"));
      
      MAXIMUM.setSelected(true);
      statsPanel.add(MAXIMUM);
      statsPanel.add(MINIMUM);
      statsPanel.add(AVERAGE);
      statsPanel.add(TOTAL);
      
      statsPanel.setVisible(true);
      this.add(statsPanel);
      
   }
   
   /**
    * Formats a panel that holds the output JTable, calls a method to format the JTable.
    */
   public void formatOutPanel()
   {
      outPanel.setLocation(450, 100);
      outPanel.setSize(1000, 450);
      outPanel.setLayout(null);
      outPanel.setBorder(new EtchedBorder());
      
      formatOutputArea();
      scrollPanel = new JScrollPane(output);
      scrollPanel.setSize(1000, 450);
      outPanel.add(scrollPanel);
      
      outPanel.setVisible(true);
      this.add(outPanel);
      
   }
   
   /**
    * Formats a panel that contains two buttons, and calls a method to format
    * the buttons.
    */
   public void formatButtonPanel()
   {
      buttonPanel.setLocation(750, 600);
      buttonPanel.setSize(500, 100);
      buttonPanel.setLayout(null);
      
      formatButtons();
      buttonPanel.add(calc);
      buttonPanel.add(exit);
      
      buttonPanel.setVisible(true);
      this.add(buttonPanel);
      
   }
   
   /**
    * Formats the 23 check boxes that represent data types.
    */
   public void formatCheckBoxes()
   {
      tair.setSelected(true);
      ta9m.setSelected(true);
      srad.setSelected(true);
      
      stnm.setText("STNM");
      time.setText("TIME");
      relh.setText("RELH");
      tair.setText("TAIR");
      wspd.setText("WSPD");
      wvec.setText("WVEC");
      wdir.setText("WDIR");
      wdsd.setText("WDSD");
      wssd.setText("WSSD");
      wmax.setText("WMAX");
      rain.setText("RAIN");
      pres.setText("PRES");
      srad.setText("SRAD");
      ta9m.setText("TA9M");
      ws2m.setText("WS2M");
      ts10.setText("TS10");
      tb10.setText("TB10");
      ts05.setText("TS05");
      ts25.setText("TS25");
      ts60.setText("TS60");
      tr05.setText("TR05");
      tr25.setText("TR25");
      tr60.setText("TR60");
   }
   
   /**
    * Formats the JTable and sets it up for holding data.
    */
   public void formatOutputArea()
   {
      DefaultTableModel blank = new DefaultTableModel(numRows, headers.length);
      blank.setColumnIdentifiers(headers);
      output = new JTable(blank);
      output.setAutoResizeMode(output.AUTO_RESIZE_OFF);
      output.setSize(1000, 450);
      
      TableColumn column = null;
      for (int i = 0; i < 6; i++) {
          column = output.getColumnModel().getColumn(i);
          if (i == 4 || i == 5) {
              column.setPreferredWidth(200);
          } else {
              column.setPreferredWidth(150);
          }
      }
      
      
   }
   
   /**
    * Formats the two buttons that calculate statistics and close the program.
    */
   public void formatButtons()
   {
      calc.setSize(100, 30);
      calc.setLocation(50, 25);
      calc.addActionListener(new CalcActionListener());
      exit.setSize(75, 30);
      exit.setLocation(325,25 );
      exit.addActionListener(new ExitActionListener());
   }
   
   /**
    * Action Listener that uses a JFileChooser to select files from the data directory.
    * 
    * @author Eli
    *
    */
   private class FileActionListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         JFileChooser chooser = new JFileChooser("data");
         int returnVal = chooser.showOpenDialog(outPanel);
         if (returnVal ==JFileChooser.APPROVE_OPTION)
         {
            file = chooser.getSelectedFile();
         }
      }
   }
   
   /**
    * Action Listener that closes the program.
    * 
    * @author Eli
    *
    */
   private class ExitActionListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         System.exit(0);
      }
   }
   
   /**
    * Action Listener that creates an instance of the MapData class to calculate
    * and organize all of the weather statistics from file. After sorting the selected
    * data types and statistic types into lists, it searches the EnumMap statistics
    * in MapData data for the information that the user wants.
    * 
    * @author Eli
    *
    */
   private class CalcActionListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         
         for(int r = 0; r < 92; ++r)
         {
            for(int c = 0; c < 6; ++c)
               output.setValueAt("", r, c);
         }
         
         MapData data = new MapData(file.getName(), "data");
         try
         {
            data.parseFile();
         } catch (FileNotFoundException e1)
         {
            output.setValueAt("File Not Found", 0, 0);
            e1.printStackTrace();
         } catch (IOException e1)
         {
            output.setValueAt("Read Failed", 0, 0);
            e1.printStackTrace();
         } catch (NullPointerException e1)
         {
            output.setValueAt("No File Selected", 0, 0);
            e1.printStackTrace();
         }
         
         ArrayList<String> paramTypes = new ArrayList<String>();
         ArrayList<StatsType> statTypes = new ArrayList<StatsType>();
         
         // Check for which boxes are checked.
         if(stnm.isSelected())
            paramTypes.add("STNM");
         if(time.isSelected())
            paramTypes.add("TIME");
         if(relh.isSelected())
            paramTypes.add("RELH");
         if(tair.isSelected())
            paramTypes.add("TAIR");
         if(wspd.isSelected())
            paramTypes.add("WSPD");
         if(wvec.isSelected())
            paramTypes.add("WVEC");
         if(wdir.isSelected())
            paramTypes.add("WDIR");
         if(wdsd.isSelected())
            paramTypes.add("WDSD");
         if(wssd.isSelected())
            paramTypes.add("WSSD");
         if(wmax.isSelected())
            paramTypes.add("WMAX");
         if(rain.isSelected())
            paramTypes.add("RAIN");
         if(pres.isSelected())
            paramTypes.add("PRES");
         if(srad.isSelected())
            paramTypes.add("SRAD");
         if(ta9m.isSelected())
            paramTypes.add("TA9M");
         if(ws2m.isSelected())
            paramTypes.add("WS2M");
         if(ts10.isSelected())
            paramTypes.add("TS10");
         if(tb10.isSelected())
            paramTypes.add("TB10");
         if(ts05.isSelected())
            paramTypes.add("TS05");
         if(ts25.isSelected())
            paramTypes.add("TS25");
         if(ts60.isSelected())
            paramTypes.add("TS60");
         if(tr05.isSelected())
            paramTypes.add("TR05");
         if(tr25.isSelected())
            paramTypes.add("TR25");
         if(tr60.isSelected())
            paramTypes.add("TR60");
            
         // Check which buttons are pushed.
         if(MAXIMUM.isSelected())
            statTypes.add(StatsType.MAXIMUM);
         if(MINIMUM.isSelected())
            statTypes.add(StatsType.MINIMUM);
         if(AVERAGE.isSelected())
            statTypes.add(StatsType.AVERAGE);
         if(TOTAL.isSelected())
            statTypes.add(StatsType.TOTAL);
         
         int cntr = 0;
         for(StatsType t: statTypes)
         {
            if(t != StatsType.TOTAL)
            {
               for(String s: paramTypes)
               {
                  Statistics temp = data.getStatistics(t, s);
                  output.setValueAt(temp.getStid(), cntr, 0);
                  output.setValueAt(s, cntr, 1);
                  output.setValueAt(t, cntr, 2);
                  output.setValueAt(String.format("%.02f", temp.getValue()), cntr, 3);
                  output.setValueAt(temp.getNumberOfReportingStations(), cntr, 4);
                  output.setValueAt(temp.getUTCDateTimeString(), cntr, 5);
                  cntr++;
               }
            }
            else
            {
               for(String s: paramTypes)
               {
                  if(s.equals("RAIN") || s.equals("SRAD"))
                  {
                     Statistics temp = data.getStatistics(t, s);
                     output.setValueAt(temp.getStid(), cntr, 0);
                     output.setValueAt(s, cntr, 1);
                     output.setValueAt(t, cntr, 2);
                     output.setValueAt(String.format("%.02f", temp.getValue()), cntr, 3);
                     output.setValueAt(temp.getNumberOfReportingStations(), cntr, 4);
                     output.setValueAt(temp.getUTCDateTimeString(), cntr, 5);
                     cntr++;
                  }
               }
            }
         }
      }
   }
   
}