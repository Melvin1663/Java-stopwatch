package cloud.apps.stopwatch;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Frame extends JFrame implements ActionListener {
	long time = 0;
	long elapsed = 0;
	String content = "";
	ArrayList<Long> timestamps = new ArrayList<Long>();
	
	JPanel timer = new JPanel();
	JPanel buttons = new JPanel();
	JPanel laps = new JPanel();
	
	Boolean counting = false;
	
	JButton toggle = new JButton();
	JButton reset = new JButton();
	JButton flag = new JButton();
	
	JLabel ms = new JLabel();
	JLabel stopwatch = new JLabel();
	JLabel lapses_label = new JLabel();
	
	JTextArea lapses_content = new JTextArea(content);
	JScrollPane scroll = new JScrollPane(lapses_content);
	
	ImageIcon playImg = new ImageIcon(Main.class.getResource("/assets/play.png"));
	ImageIcon flagImg = new ImageIcon(Main.class.getResource("/assets/flag.png"));
	ImageIcon resetImg = new ImageIcon(Main.class.getResource("/assets/reset.png"));
	ImageIcon pauseImg = new ImageIcon(Main.class.getResource("/assets/pause.png"));
	ImageIcon appIco = new ImageIcon(Main.class.getResource("/assets/stopwatch.png"));
	
	Timer counter = new Timer();

	Frame(){
		timestamps.add((long) 0);
		
		stopwatch.setText(Util.hhmmss(time));
		stopwatch.setFont(new Font("Segoe UI Symbol", Font.BOLD, 90));
		stopwatch.setBounds(42, -5, 500, 100);
		
		ms.setText(Util.getMS(time));
		ms.setFont(new Font("Segoe UI Symbol", Font.BOLD, 50));
		ms.setBounds(402, 10, 200, 100);
		
		timer.setBounds(90, 10, 500, 100);
		timer.setLayout(null);
		timer.add(stopwatch);
		timer.add(ms);
//		timer.setBorder(BorderFactory.createLineBorder(Color.black));
		
		toggle.setBackground(Color.gray);
		toggle.setBounds(0, 0, 90, 60);
		toggle.setIcon(Util.resizeIcon(playImg, 40, 40));
		toggle.setFocusable(false);
		toggle.addActionListener(this);
		toggle.setToolTipText("Start");
		toggle.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		flag.setBackground(Color.gray);
		flag.setBounds(105, 0, 90, 60);
		flag.setIcon(Util.resizeIcon(flagImg, 40, 40));
		flag.setFocusable(false);
		flag.addActionListener(this);
		flag.setToolTipText("Laps");
		flag.setEnabled(false);
		flag.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		reset.setBackground(Color.gray);
		reset.setBounds(210, 0, 90, 60);
		reset.setIcon(Util.resizeIcon(resetImg, 40, 40));
		reset.setFocusable(false);
		reset.addActionListener(this);
		reset.setToolTipText("Reset");
		reset.setEnabled(false);
		reset.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		buttons.setBounds(200, 120, 300, 60);
		buttons.setLayout(null);
		buttons.add(toggle);
		buttons.add(reset);
		buttons.add(flag);
//		buttons.setBorder(BorderFactory.createLineBorder(Color.black));
		
//		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(485, 0, 15, 150);
		
		lapses_label.setText("    Laps               Time                         Total Time");
		lapses_label.setFont(new Font("Segoe UI Symbol", Font.BOLD, 20));
		lapses_label.setVerticalAlignment(JLabel.TOP);
		lapses_label.setBounds(88, 190, 503, 30);
		lapses_label.setBorder(BorderFactory.createLineBorder(Color.black));
		
		lapses_content.setFont(new Font("Segoe UI Symbol", Font.BOLD, 20));
//		lapses_content.setBounds(1, 30, 485, 119);
		lapses_content.setEditable(false);
		lapses_content.setLineWrap(true);
//		lapses_content.setBorder(BorderFactory.createLineBorder(Color.black));
		lapses_content.setRows(5);
		lapses_content.setColumns(27);
		
		laps.setBounds(85, 214, 510, 150);
//		laps.setLayout(null);
//		laps.setBorder(BorderFactory.createLineBorder(Color.black));
		laps.add(scroll);
//		laps.add(lapses_content);
		
		counter.scheduleAtFixedRate(new TimerTask(){
		    @Override
		    public void run(){
		       if (counting) {
		    	   time++;
			       stopwatch.setText(Util.hhmmss(time));
			       ms.setText(Util.getMS(time));
		       }
		    }
		},0,1);
		
		this.setTitle("Stopwatch");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(700, 420);
//		this.setContentPane(lapses_content);
		this.setResizable(false);
		this.setLayout(null);
		this.add(lapses_label);
		this.add(laps);
		this.add(timer);
		this.add(buttons);
		this.setIconImage(appIco.getImage());
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object button = e.getSource();
		
		if (button == toggle) {
			counting = !counting;
			
			if (counting == true) { 
				toggle.setIcon(Util.resizeIcon(pauseImg, 40, 40));
				
				toggle.setToolTipText("Pause");
				
				flag.setEnabled(true);
				reset.setEnabled(false);
			}
			else { 
				toggle.setIcon(Util.resizeIcon(playImg, 40, 40));
				flag.setEnabled(false);
				reset.setEnabled(true);
				toggle.setToolTipText("Start");
				
			}
		} else if (button == reset) {
			counting = false;
			time = 0;
			elapsed = 0;
			content = "";
			timestamps.clear();
			timestamps.add((long) 0);
			toggle.setIcon(Util.resizeIcon(playImg, 40, 40));
			
			stopwatch.setText(Util.hhmmss(time));
			ms.setText(Util.getMS(time));
			lapses_content.setText(content);
			
			toggle.setToolTipText("Start");
			
			flag.setEnabled(false);
			reset.setEnabled(false);
		} else if (button == flag) {
			elapsed++;
			timestamps.add(time);
			
			content+="     "+elapsed+"\t"+Util.hhmmss(timestamps.get((int) elapsed)-timestamps.get((int)elapsed-1), true)+"            "+Util.hhmmss(time, true)+"\n";
			lapses_content.setText(content);
		}
	}
}
