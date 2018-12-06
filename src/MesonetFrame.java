import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class MesonetFrame extends JFrame
{
   
   private static final long serialVersionUID = 1L;
   
   JMenuBar menuBar = new JMenuBar();
   JMenu fileMenu = new JMenu("File");
   JMenuItem choosefile = new JMenuItem();
   JMenuItem exitprog = new JMenuItem();
   
   JPanel textPanel = new JPanel();
   JPanel paramPanel = new JPanel();
   JPanel statsPanel = new JPanel();
   JPanel outPanel = new JPanel();
   JPanel buttonPanel = new JPanel();
   
   JLabel messageArea = new JLabel();
   
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
   
   JRadioButton MAXIMUM = new JRadioButton("MAXIMUM");
   JRadioButton MINIMUM = new JRadioButton("MINIMUM");
   JRadioButton AVERAGE = new JRadioButton("AVERAGE");
   JRadioButton TOTAL = new JRadioButton("TOTAL");
   
   File file = new File("201808301745.mdf");
   
   final String[] headers = {"Station", "Parameter", "Statistic", "Value", "Reporting Stations", "Date"};
   int numRows = 92 ;
   JTable output;
   
   JScrollPane scrollPanel;
   
   JButton calc = new JButton("Calculate");
   JButton exit = new JButton("Exit");
   
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
   
   public void formatTextPanel()
   {
      messageArea.setText("Mesonet - We don't set records, we report them!");
      textPanel.setLocation(800, 0);
      textPanel.setSize(400, 50);
      
      textPanel.setLayout(new GridLayout(1, 1));
      textPanel.add(messageArea);
      this.add(textPanel);
   }
   
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
   
   public void formatOutPanel()
   {
      outPanel.setLocation(450, 100);
      outPanel.setSize(1000, 450);
      outPanel.setBorder(new EtchedBorder());
      
      formatOutputArea();
      scrollPanel = new JScrollPane(output);
      scrollPanel.setSize(1000, 400);
      outPanel.add(scrollPanel);
      
      outPanel.setVisible(true);
      this.add(outPanel);
      
   }
   
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
   
   public void formatOutputArea()
   {
      DefaultTableModel blank = new DefaultTableModel(numRows, headers.length);
      blank.setColumnIdentifiers(headers);
      output = new JTable(blank);
      output.setSize(950, 450);
      
      TableColumn column = null;
      for (int i = 0; i < 5; i++) {
          column = output.getColumnModel().getColumn(i);
          if (i == 4 || i == 5) {
              column.setPreferredWidth(250);
          } else {
              column.setPreferredWidth(150);
          }
      }
      
      
   }
   
   public void formatButtons()
   {
      calc.setSize(100, 30);
      calc.setLocation(50, 25);
      calc.addActionListener(new CalcActionListener());
      exit.setSize(75, 30);
      exit.setLocation(325,25 );
      exit.addActionListener(new ExitActionListener());
   }
   
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
   
   private class ExitActionListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         System.exit(0);
      }
   }
   
   private class CalcActionListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         
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
         
         
         
      }
   }
}