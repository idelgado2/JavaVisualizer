package music_visualization;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/* The following imports are for gathering music data */
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 *
 * @author Isaac
 */
public class MainWindow extends Application implements ActionListener{
    
    private final int FRAME_WIDTH = 1000;
    private final int FRAME_HEIGHT = 600;
    
    JFrame frame;
    JButton stopPlaybtn, changeSong, changeEffect;

    CircleGenerator g;
    Media media;
    MediaPlayer player;
    
    boolean goFrequency = false;
    
    public String source = "file:///Users/Isaac/Desktop/POE/wavFiles/C-ScaleSkips.wav"; //to hold source path to song selected
    public int effectChoice = 3; //1:random change; 2:random change with gravity, 3:frequency change
    
    //wavefile names///
    ////OctaveChange.wav
    ////C-ScaleSkips.wav
    ////Nocturne.wav///
    
    public static void main(String[] args){
       Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
       frame = new JFrame();
       g = new CircleGenerator();
       media = new Media(source);
       player = new MediaPlayer(media);
              
       stopPlaybtn = new JButton("PLAY");
       stopPlaybtn.setBounds(20, 20, 90, 20);
       stopPlaybtn.addActionListener(this);
       
       changeSong = new JButton("CHANGE SONG");
       changeSong.setBounds(120, 20, 140, 20);
       changeSong.addActionListener(this);

       changeEffect = new JButton("CHANGE EFFECT");
       changeEffect.setBounds(270, 20, 140, 20);
       changeEffect.addActionListener(this);
       
       frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
       frame.setTitle("Motion Environment");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
       frame.add(stopPlaybtn);
       frame.add(changeSong);
       frame.add(changeEffect);
       frame.add(g);
       frame.setLocationRelativeTo(null);
       frame.setVisible(true);
       
       class UpdateGUI extends Thread {     //inner thread class to allow re-paint to work, it needs to be under a sepearte thread
            private int typeOfChange; //1:randome, 2:randome with gravity, 3: frequency
            File file = new File("/Users/Isaac/Desktop/POE/images/C_ScaleSkips/C-ScaleSkips_YData.txt");
            //File file = new File("/Users/Isaac/Desktop/POE/images/OctaveChangeSpectrum/OctaveChange_YData.txt");
            //File file = new File("/Users/Isaac/Desktop/POE/images/NocturneSpectrum/Nocturne_YData.txt");
            
            //Hz data names///
            ////OctaveChangeSpectrum/OctaveChange_YData.txt//
            ////C-C_ScaleSkips/C-ScaleSkips_YData.txt////////
            ////NocturneSpectrum/Nocturne_YData.txt/////////
            
            BufferedReader reader = null;
            
            //Thread definitions
            Thread gravityThread = new Thread(){    //seperata thread to deal with gravity
                        @Override
                        public void run(){
                            while(true){
                                for(Circle c : g.Clist){
                                    int x = c.y + c.radius;
                                    if(x < FRAME_HEIGHT){
                                        c.y += 5;
                                    }else{
                                        c.y = FRAME_HEIGHT - c.radius;
                                    }
                                }
                                g.repaint();
                                try {
                                    Thread.sleep(200);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                g.repaint();
                            }
                        }
                    };
            Thread frequencyThread = new Thread(){    //seperata thread to deal with gravity
                        @Override
                        public void run(){
                            try {
                                g.repaint();
                                reader = new BufferedReader(new FileReader(file));
                                String text = null;
                                float currentValue = 0;
                                int index = 0;
                                int roundedHz = 0;
                                
                                while ((text = reader.readLine()) != null) {            //while loop to get frequency data from txt file
                                    currentValue = Float.parseFloat(text);
                                    System.out.println(currentValue);
                                    roundedHz = (Math.round(currentValue));
                                    if(roundedHz < 2000){
                                        index = Math.round(roundedHz / 50); //2000/40 = 50
                                        if((g.Clist.get(index).y - g.Clist.get(index).radius) > 0){
                                            g.Clist.get(index).y -= 3;
                                        }
                                    }
                                    /*if((roundedHz > 273) && (roundedHz < 21840)){
                                        index = Math.round(roundedHz / 546); //2000/40 = 50
                                        if((g.Clist.get(index).y - g.Clist.get(index).radius) > 0){
                                            g.Clist.get(index).y -= 3;
                                        }
                                    }*/
                                    g.repaint();
                                    Thread.sleep(5);    
                                }
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                                try {
                                    if (reader != null) {
                                        reader.close();
                                    }
                                } catch (IOException e) {
                                }
                            }
                        }
                        
                    };
           
            public UpdateGUI(int choice){
                typeOfChange = choice;
            }
            @Override
            public void run() {
                switch (typeOfChange) { 
                    case 1:
                        //random change
                        this.RandomAudioResponse();
                        break;
                    case 2:
                        //randome change with gravity
                        gravityThread.start();
                        this.RandomAudioResponse();
                        break;
                    case 3:
                        //frequency change
                        gravityThread.start();
                        while(!goFrequency){System.out.println("Waiting...");}
                        frequencyThread.start();    //start frequency gathering thread
                        break;
                    default:
                        System.out.println("Wrong input - Error");
                        return;
                }
            }
            public void RandomAudioResponse(){
                player.setOnEndOfMedia(()->{player.stop();});
                        player.setAudioSpectrumListener((double timestamp, double duration, float[] magnitudes, float[] phases) -> {
                            float prevMag = -60;
                            float currentMag;
                            int destination = 0;
                                for (float mag : magnitudes){
                                        currentMag = mag;
                                        if (currentMag >= -40.0) {
                                            for(Circle c : g.Clist){                //update y-position of each circle with their individual paintcomponents
                                                if(c.y!=destination){
                                                    if(currentMag >= -57.0){
                                                        if(c.y < destination){        //if ball is higher move down towards destination
                                                            if((c.y + c.radius) != FRAME_HEIGHT){
                                                                c.y += g.Clist.indexOf(c); //rateIncrement * (g.Clist.indexOf(c) + 1);
                                                            }
                                                        }
                                                        else if(c.y > destination){   //if ball is lower move up towards destination
                                                            c.y -= g.Clist.indexOf(c);//rateIncrement * (g.Clist.indexOf(c) + 1);
                                                        }                                                
                                                    }
                                                }
                                            }
                                            g.repaint();        //repaint all Circles again
                                        }
                                        if(currentMag >= -40.0){
                                            destination = ((int)(((currentMag - prevMag)*10)+10));
                                        }
                                    }

                        });
           }
       }

        UpdateGUI updater = new UpdateGUI(effectChoice);    //create thread that will be updating our animation 1 for original, 2 for gravity
        updater.start();  
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == stopPlaybtn){
            if (stopPlaybtn.getText().equals("PLAY")) {
                if(effectChoice == 3){
                    goFrequency = true;
                }else{
                  stopPlaybtn.setText("STOP");
                  player.play();
                }
            } else if (stopPlaybtn.getText().equals("STOP")) {
                  stopPlaybtn.setText("PLAY");
                  player.pause();
            }
        }else if(e.getSource() == changeSong){
            PopUpMenu popup = new PopUpMenu("CHANGE SONG"); 
        }else if(e.getSource() == changeEffect){
            PopUpMenu popup = new PopUpMenu("");//fix constructor, this is not nice
        }
    }
}
