package gervigrimur;

import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;

public class GerviGrimur {

	AudioInputStream audioInputStream;
	Clip clip;
	File file;
	ArrayList<File> list = new ArrayList<File>();
	ArrayList<File> listLvl1 = new ArrayList<File>();
	ArrayList<File> listLvl2 = new ArrayList<File>();
	ArrayList<File> listLvl3 = new ArrayList<File>();
	ArrayList<File> listInteractions = new ArrayList<File>();
	private int lummur = 0;
	private int bjorar = 0;
	private double currLummur = 0;
	private double currBjorar = 0;
	
	
	public GerviGrimur() {
		init("lvl1", listLvl1);
		init("lvl2", listLvl2);
		init("lvl3", listLvl3);
		init("interactions", listInteractions);
		
	}
	
	
	public void init(String s, ArrayList<File> li) {
		String dir = "media\\GerviGrimurSounds\\" + s;
		final File folder = new File(dir);
		for (final File fileEntry : folder.listFiles()) {
			li.add(fileEntry.getAbsoluteFile());
		}
	}
	
	public ArrayList<File> determineList() {
		int k = calculateIntoxication();
		System.out.println("intox. " + k);
		switch(k) {
		case 1:
			return listLvl1;
		case 2:
			return listLvl2;
		case 3:
			return listLvl3;
		default:
			return listLvl1;
		}
	}
	
	public Clip getSound() {
		list = determineList();
		try {
			audioInputStream = AudioSystem.getAudioInputStream(list.get((int) (Math.random() * list.size())));
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clip;
	}
	
	public Clip getClickSound() {
		Clip clickClip = clip;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(listInteractions.get((int) (Math.random() * listInteractions.size())));
			clickClip = AudioSystem.getClip();
			clickClip.open(audioInputStream);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clickClip;
	}

	public int calculateIntoxication() {
		//System.out.println(bjorar-lummur + " BL " + t.getDelay());
		if(currBjorar - currLummur < 5 && currBjorar - currLummur >= 0) {
			return 1;
	  	}
		
		if(currBjorar - currLummur < 10 && currBjorar - currLummur >= 5) {
			return 2;
		}
		if(currBjorar - currLummur >= 10) {
			return 3;
		}
		return -1;
	}
	
	public void incrementLummur() {
		lummur++;
		currLummur++;
	}
	
	public void incrementBjorar() {
		bjorar++;
		currBjorar++;
	}
	
	public int getLummur() {
		return lummur;
	}
	
	public int getBjorar() {
		return bjorar;
	}
	
	public double getCurrLummur() {
		return currLummur;
	}
	
	public double getCurrBjorar() {
		return currBjorar;
	}


	public void decrementCurrLummur(double k) {
		currLummur -= k;
	}
	
	public void decrementCurrBjorar(double k) {
		currBjorar -= k;
	}
	
}
