package music_visualization;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class PopUpMenu implements ActionListener{
    JFrame popup;
    JButton song1, song2;
    JButton effect1, effect2;
    
    boolean song = false, effect = false;
    
    PopUpMenu(String btnName){
        if("CHANGE SONG".equals(btnName)){
            song = true;   //we are changing the song
            effect = false; //make sure they are different
            
            popup = new JFrame();           //set up Frame
            popup.setLayout(new FlowLayout());
            
            song1 = new JButton("song1");   //add buttons
            song1.addActionListener(this);
            popup.add(song1);
            song2 = new JButton("song2");
            song2.addActionListener(this);
            popup.add(song2);
            
            popup.setTitle("GridLayout Demo");  //set frame properties
            popup.setSize(280, 150);           
            popup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            popup.setLocationRelativeTo(null);
            popup.setVisible(true);            
        }else{
            effect = true;   //we are changing the effect
            song = false; //make sure they are different
            
            popup = new JFrame();       //set up Frame
            popup.setLayout(new FlowLayout());
            
            effect1 = new JButton("effect1");   //add Buttons
            effect1.addActionListener(this);
            popup.add(effect1);
            effect2 = new JButton("effect2");
            effect2.addActionListener(this);
            popup.add(effect2);
            
            popup.setTitle("GridLayout Demo"); //set frame properties
            popup.setSize(280, 150);           
            popup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            popup.setLocationRelativeTo(null);
            popup.setVisible(true);            
        }
}

    public static void main(String[] args){
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(song){
           System.out.println("I clicked a song");
           popup.dispose();
           //MainWindow anotherWindow = new MainWindow("file:///Users/Isaac/Desktop/POE/wavFiles/Nocturne.wav", 1);
        }else if(effect){
           System.out.println("I clicked an effect"); 
           popup.dispose();
        }
    }  
}
