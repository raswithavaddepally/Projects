package com.mani.core;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jfree.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Graph extends JFrame implements ActionListener{
	HashMap hm = new HashMap();
	JLabel prevVal;
	JLabel presVal;
	JLabel nextVal;
	JPanel jp ;
	Graph(String fileName){
		super();
		this.getContentPane().setLayout(new FlowLayout());
		JComboBox cb = new JComboBox();
		File file = new File(fileName);
		Scanner scanner; 
		try{		
			 scanner = new Scanner(file);
			 while(scanner.hasNext()){
					String s = scanner.next();
					int prev =Integer.parseInt(scanner.next());
					int pres =Integer.parseInt(scanner.next());
					int next =Integer.parseInt(scanner.next());
					Data d = new Data(s,prev,pres,next);
					hm.put(s, d);
			}
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		if(hm!=null){
			Iterator i = hm.keySet().iterator();
			while(i.hasNext()){
				Data d = (Data)hm.get(i.next());
				String s = d.getWord();
				cb.addItem(s);
			}
		}
		this.add(cb);
		cb.addActionListener(this);
		JPanel textFields = new JPanel(new GridLayout(0, 2));
		String s = (String) cb.getSelectedItem();
		Data data = (Data)hm.get(s);
		JLabel prevText = new JLabel("Previous");
		textFields.add(prevText);
		prevVal = new JLabel(Integer.toString(data.getPrevious()));
		textFields.add(prevVal);
		JLabel presText = new JLabel("Present");
		textFields.add(presText);
		presVal = new JLabel(Integer.toString(data.getPresent()));
		textFields.add(presVal);
		JLabel nextText = new JLabel("Next");
		textFields.add(nextText);
		nextVal = new JLabel(Integer.toString(data.getNext()));
		textFields.add(nextVal);
		this.add(textFields);
		int prev = prevVal!=null&&!prevVal.getText().equals("")?Integer.parseInt(prevVal.getText()):0; 
		int pres = presVal!=null&&!presVal.getText().equals("")?Integer.parseInt(presVal.getText()):0;
		int next = nextVal!=null&&!nextVal.getText().equals("")?Integer.parseInt(nextVal.getText()):0;
		
		jp = createBarGraphPanel(prev,pres,next);
		
		/*jp.setSize(400, 400);
		*/this.add(jp);
	}
	public static void drawGraph(String fileName){
		Graph g = new Graph(fileName);
		g.setSize(800, 600);
		g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		g.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JComboBox){
			JComboBox cb = (JComboBox)e.getSource();
			String word = (String)cb.getSelectedItem();
			if(hm!=null){
				this.getContentPane().remove(jp);
				Data d = (Data)hm.get(word);
				prevVal.setText(Integer.toString(d.getPrevious()));
				presVal.setText(Integer.toString(d.getPresent()));
				nextVal.setText(Integer.toString(d.getNext()));
				jp = createBarGraphPanel(d.getPrevious(),d.getPresent(),d.getNext());
				
				this.getContentPane().add(jp);
			}
	     }
	}
	public static JPanel createBarGraphPanel(int prev,int pres,int next) {
        JFreeChart chart = generateBarChart(prev,pres,next);
        return new ChartPanel(chart);
    }

	public static JFreeChart generateBarChart(int prev,int pres,int next) {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        dataSet.setValue(prev,"Previous","Previous");
        dataSet.setValue(pres,"Present","Present");
        dataSet.setValue(next,"Next","Next");
 
        JFreeChart chart = ChartFactory.createBarChart(
                "TweetPedia Comparision", "Time", "Number of Hits",
                dataSet,PlotOrientation.VERTICAL, false, true, false);
 
         return chart;
    }
}
class Data{
	String word;
	int previous;
	int present;
	int next;
	Data(){
		this.word = "";
		this.previous =0;
		this.present =0;
		this.next=0;
	}
	Data(String word,int prev,int pres, int next){
		this.word = word;
		this.previous =prev;
		this.present =pres;
		this.next=next;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getPrevious() {
		return previous;
	}
	public void setPrevious(int previous) {
		this.previous = previous;
	}
	public int getPresent() {
		return present;
	}
	public void setPresent(int present) {
		this.present = present;
	}
	public int getNext() {
		return next;
	}
	public void setNext(int next) {
		this.next = next;
	}
	
}
/*class checkListener implements ChangeListener{

	@Override
	public void stateChanged(ChangeEvent arg0) {
		if(arg0.getSource() instanceof JComboBox){
			
				//hm.get(key);
			}
		}
		
	}
	
}*/