package gervigrimur;


import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import java.text.DecimalFormat;

public class GrimurProgram extends JPanel {
    
    protected JButton button, addLumma, addBjor; 
    protected JLabel labTotLummur, labTotBjorar, labCurrLummur, labCurrBjorar, labTotLummurValue, labTotBjorarValue, labCurrLummurValue, labCurrBjorarValue, img1, img2, emptyLabel;
    private static GerviGrimur grimur;
    private Timer countdownTimer, soundTimer, imageTimer, timerTimer;
    private BufferedImage imag1, imag2;
    private ImageIcon icon1, icon2;
    private final DecimalFormat df = new DecimalFormat("###.##");
    private Clip clip;
    private final int PANEL_WH = 800;
    private Font font = new Font(Font.SANS_SERIF, Font.BOLD, 18);
    private Font buttonFont = new Font(Font.SANS_SERIF, Font.BOLD, 28);
    private long nowLumma = System.currentTimeMillis();
    private long nowBjor = System.currentTimeMillis();
    private Clip neitakk;
    
    public GrimurProgram() {
    	nowLumma = 0; //mix?
    	nowBjor = 0;
    	
    	initNeiTakk();
    	
    	initSoundTimer(); 
    	
    	UIManager.put("Label.font", font);
    	UIManager.put("Button.font", buttonFont);
    	
    	try{
        	imag1 = ImageIO.read(new File("media\\grimurmyndir\\gg1a.png"));
            imag2 = ImageIO.read(new File("media\\grimurmyndir\\gg1b.png"));
            Image dimag1 = imag1.getScaledInstance(PANEL_WH, PANEL_WH, Image.SCALE_SMOOTH);
            Image dimag2 = imag2.getScaledInstance(PANEL_WH, PANEL_WH, Image.SCALE_SMOOTH);
            icon1 = new ImageIcon(dimag1);
            icon2 = new ImageIcon(dimag2);
            img1 = new JLabel(icon1);
            img1.setSize(PANEL_WH, PANEL_WH);
        } catch (Exception e) {
        	e.printStackTrace();
        }
    	
    	initCountdownTimer();
    	labTotLummur = new JLabel("Samtals lummur: ");
    	labTotBjorar = new JLabel("Samtals bjórar: ");
    	labCurrLummur = new JLabel("Lummur í kerfinu: ");
    	labCurrBjorar = new JLabel("Bjórar í kerfinu: ");
    	
    	labTotLummurValue = new JLabel(grimur.getLummur() + "");
    	labTotBjorarValue = new JLabel(grimur.getBjorar() + "");
    	labCurrLummurValue = new JLabel(grimur.getCurrLummur() + "");
    	labCurrBjorarValue = new JLabel(grimur.getCurrBjorar() + "");
    	
        addLumma = new JButton("Fáðu þér lummu, Grímur!");
        addBjor = new JButton("Grímur, viltu bjór?");
        addLumma.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(System.currentTimeMillis()-nowLumma > 15*60*1000) {
					nowLumma = System.currentTimeMillis();
					grimur.incrementLummur();
					labTotLummurValue.setText(grimur.getLummur() + "");
					labCurrLummurValue.setText(df.format(grimur.getCurrLummur()) + "");
					grimur.getClickSound().start();
					initImageTimer(grimur.getClickSound().getMicrosecondLength());
				} else {
					neitakk.start();
					initImageTimer(neitakk.getMicrosecondLength());
					initNeiTakk();
				}
			}
        	
        });
        addBjor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(System.currentTimeMillis()-nowBjor > 5*60*1000) {
					nowBjor = System.currentTimeMillis();
					grimur.incrementBjorar();
					labTotBjorarValue.setText(grimur.getBjorar() + "");
					labCurrBjorarValue.setText(df.format(grimur.getCurrBjorar()) + "");
					grimur.getClickSound().start();
					initImageTimer(grimur.getClickSound().getMicrosecondLength());
				} else {
					neitakk.start();
					initImageTimer(neitakk.getMicrosecondLength());
					initNeiTakk();
				}
				
			}
        	
        });
        


        
        Container stats = new Container();
        stats.setLayout(new GridLayout(0,4));
        stats.add(labTotLummur);
        stats.add(labTotLummurValue);
        stats.add(labTotBjorar);
        stats.add(labTotBjorarValue);
        stats.add(labCurrLummur);
        stats.add(labCurrLummurValue);
        stats.add(labCurrBjorar);
        stats.add(labCurrBjorarValue);
        //con.add(addLumma);
        //con.add(addBjor);
        
        Container btns = new Container();
        btns.setLayout(new GridLayout(0,2));
        addBjor.setBackground(new Color(230, 218, 48));
        addLumma.setBackground(new Color(94, 52, 21));
        addLumma.setForeground(new Color(255, 255, 255));
        
        
        btns.add(addLumma);
        btns.add(addBjor);
        
        
        setLayout(new BorderLayout());
        add(stats, BorderLayout.NORTH);
        add(img1, BorderLayout.CENTER);
        add(btns, BorderLayout.SOUTH);
        
    }
    
    public void initNeiTakk() {
    	try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("media\\GerviGrimurSounds\\egerbaragodur.wav").getAbsoluteFile());
			neitakk = AudioSystem.getClip();
			neitakk.open(audioInputStream);			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void initCountdownTimer() {
    	ActionListener listener = new ActionListener(){
    		public void actionPerformed(ActionEvent event){
    			if(grimur.getCurrLummur() > 0.02) {
    				grimur.decrementCurrLummur(0.02);
    				labCurrLummurValue.setText(df.format(grimur.getCurrLummur()) + "");
    			}
    			if(grimur.getCurrBjorar() > 0.01) {
					grimur.decrementCurrBjorar(0.01);
					labCurrBjorarValue.setText(df.format(grimur.getCurrBjorar()) + "");
				}
				  
			}
			  
			};
			countdownTimer = new Timer(50*1000, listener); //30 sek uppfærslutímabil
			countdownTimer.start();
	}
    
    public void initImageTimer(long t) {
    	ActionListener listener = new ActionListener(){
			  public void actionPerformed(ActionEvent event){
				  switchimgs();
			  }
			};
			
		ActionListener timerListener = new ActionListener(){
			  public void actionPerformed(ActionEvent event){
				  imageTimer.stop();
				  timerTimer.stop();
				  if(img1.getIcon().equals(icon2)) {
					  switchimgs();
				  }
			  }
			};
				
		imageTimer = new Timer(125, listener);
		timerTimer = new Timer((int) t/1000, timerListener);
		System.out.println("clipl: " + t/1000/1000.0);
		timerTimer.start();
		imageTimer.start();
    }
    
    public void initSoundTimer() {
    	
    	ActionListener listener = new ActionListener(){
			  public void actionPerformed(ActionEvent event){
				  clip = grimur.getSound();
				  clip.start();
				  initImageTimer(clip.getMicrosecondLength()); 
				  
				  
				  //einnig setja sound á takka
				  //finna betri mynd
				  //Skorður á ölvun/lummur
				  
				  double rand = (0.4*Math.random()) + 0.8;
				  switch(grimur.calculateIntoxication()) {
				  case 1:
					  soundTimer.setDelay((int) (10*60*100*rand));
					  break;
				  case 2:
					  soundTimer.setDelay((int) (7*60*100*rand));
					  break;
				  case 3:
					  soundTimer.setDelay((int) (5*60*100*rand));
					  break;
				  default:
					  soundTimer.setDelay((int) (1*60*1000*rand));
					  break;
				  }
				  //System.out.println("delay: " + (System.currentTimeMillis()-now)/1000.0);
				  //now = System.currentTimeMillis();
			  }
			};
		soundTimer = new Timer((int) (30*1000), listener); //Breyta í mínútur
		soundTimer.start();
    }
    
    public void switchimgs() {
    	if(img1.getIcon().equals(icon1)) {
    		img1.setIcon(icon2);
    	} else {
    		img1.setIcon(icon1);
    	}
    	
    }
    
    public static void main(String[] args) 
    {
    	grimur = new GerviGrimur();
    	
        JFrame frame = new JFrame("GERVIGRÍMUR");
        frame.setPreferredSize(new Dimension(800,900));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new GrimurProgram());
        frame.pack();
        frame.setVisible(true);
        
    }
 
}