import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MesonetFrame extends JFrame
{
   
   public MesonetFrame()
   {
      super("Mesonet WeatherStatistics Calculator");
      this.setSize(1500, 750);
      this.setResizable(false);
      this.setVisible(true);
   }
   
}
