package com.sept.jui.input.date;

import com.sept.exception.AppException;
import com.sept.jui.floating.FloatingPanel;
import com.sept.util.DateUtil;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;

/**
 * 其实是个JFrame
 * 
 * @author zchar
 *
 */
public class DatePanel extends FloatingPanel implements ItemListener, ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private JTextComponent textComponent;
	private String dateMask;
	private int year = 2018;
	private int month = 12;
	private int day = 1;
	private int hour = 1;
	private int minute = 0;
	private int second = 0;
	private int week = 1;
	private JComboBox<String> cb_minute;
	private JComboBox<String> cb_second;
	private JComboBox<String> cb_hour;
	private JComboBox<String> cb_year;
	private JComboBox<String> cb_month;
	private JLabel[][] jlDays;
	private JButton jb_clear;
	private JButton jb_today;
	private JButton jb_yes;
	private Color selectColor = Color.green;
	private Color dateColor = Color.white;
	private Color dateHoverColor = Color.lightGray;
	private Color dateTitleColor = Color.gray;
	private Color dateTitleFontColor = Color.black;
	private Color dateFontColor = Color.black;
	private JPanel panel_center;
	private static final int max_size = 400;
	private static final int min_size = 200;

	public DatePanel(Point p) {
		super(p);
	}

	public DatePanel(Point p, JTextComponent textComponent, String dateMask) {
		super(p);
		this.textComponent = textComponent;
		this.dateMask = dateMask;

		int size = textComponent.getWidth();
		if (size > max_size) {
			size = max_size;
		}
		if (size < min_size) {
			size = min_size;
		}
		setBounds(p.x, p.y, size, size);

		setLayout(new BorderLayout(0, 0));
		initComponent();
		changeDays();
		initParams();
	}

	private void initParams() {
		String text = this.textComponent.getText();
		boolean isGo = true;
		if ((text != null) && (!"".equals(text))) {
			try {
				Date d = DateUtil.formatStrToDate(text);

				setYear(Integer.parseInt(DateUtil.formatDate(d, "yyyy")));
				setMonth(Integer.parseInt(DateUtil.formatDate(d, "MM")));
				setDay(Integer.parseInt(DateUtil.formatDate(d, "dd")));
				if (this.dateMask.indexOf("HH") >= 0) {
					setHour(Integer.parseInt(DateUtil.formatDate(d, "HH")));
				} else {
					setHour(Integer.parseInt(DateUtil.formatDate(d, "hh")));
				}
				setMinute(Integer.parseInt(DateUtil.formatDate(d, "mm")));
				setSecond(Integer.parseInt(DateUtil.formatDate(d, "ss")));
				isGo = false;
			} catch (AppException e) {
				e.printStackTrace();
			}
		}
		if (isGo) {
			HashMap<String, Integer> hm = DateUtil.getCurrentHMTime();
			setYear(((Integer) hm.get("year")).intValue());
			setMonth(((Integer) hm.get("month")).intValue());
			setDay(((Integer) hm.get("day")).intValue());
			setHour(((Integer) hm.get("hour")).intValue());
			setMinute(((Integer) hm.get("minute")).intValue());
			setSecond(((Integer) hm.get("second")).intValue());
		}
		setFromParams();
	}

	private void setFromParams() {
		this.cb_year.setSelectedItem(this.year + "年");
		this.cb_month.setSelectedItem(this.month + "月");
		setSelectedDay(this.day);
		this.cb_hour.setSelectedItem(this.hour + "时");
		this.cb_minute.setSelectedItem(this.minute + "分");
		this.cb_second.setSelectedItem(this.second + "秒");
	}

	private void initComponent() {
		JPanel panel_north = new JPanel();
		add(panel_north, "North");
		panel_north.setLayout(new GridLayout(1, 0, 0, 0));

		this.cb_year = new JComboBox<String>();
		panel_north.add(this.cb_year);
		for (int i = 1900; i < 2051; i++) {
			this.cb_year.addItem(i + "年");
		}
		this.cb_year.addItemListener(this);

		this.cb_month = new JComboBox<String>();
		panel_north.add(this.cb_month);
		for (int i = 1; i < 13; i++) {
			this.cb_month.addItem(i + "月");
		}
		this.cb_month.addItemListener(this);

		this.panel_center = new JPanel(new GridLayout(7, 7));
		add(this.panel_center, "Center");

		JPanel panel_south = new JPanel();
		add(panel_south, "South");
		panel_south.setLayout(new GridLayout(0, 3, 0, 2));

		this.cb_hour = new JComboBox<String>();
		panel_south.add(this.cb_hour);
		for (int i = 0; i < 24; i++) {
			this.cb_hour.addItem(i + 1 + "时");
		}
		this.cb_hour.addItemListener(this);

		this.cb_minute = new JComboBox<String>();
		panel_south.add(this.cb_minute);
		for (int i = 0; i < 59; i++) {
			this.cb_minute.addItem(i + "分");
		}
		this.cb_minute.addItemListener(this);

		this.cb_second = new JComboBox<String>();
		panel_south.add(this.cb_second);
		for (int i = 0; i < 59; i++) {
			this.cb_second.addItem(i + "秒");
		}
		this.cb_second.addItemListener(this);

		Font f = new Font("微软雅黑", 1, 10);

		this.jb_clear = new JButton("清除");
		panel_south.add(this.jb_clear);
		this.jb_clear.setMargin(new Insets(0, 0, 0, 0));
		this.jb_clear.setFont(f);
		this.jb_clear.addActionListener(this);

		this.jb_today = new JButton("今天");
		panel_south.add(this.jb_today);
		this.jb_today.setMargin(new Insets(0, 0, 0, 0));
		this.jb_today.setFont(f);
		this.jb_today.addActionListener(this);

		this.jb_yes = new JButton("确认");
		panel_south.add(this.jb_yes);
		this.jb_yes.setMargin(new Insets(0, 0, 0, 0));
		this.jb_yes.setFont(f);
		this.jb_yes.addActionListener(this);

		LabelMouseListener labelMouseListener = new LabelMouseListener();
		this.jlDays = new JLabel[7][7];
		for (int i = 0; i < 7; i++) {
			JLabel temp = new JLabel();
			temp.setHorizontalAlignment(0);
			temp.setVerticalAlignment(0);
			temp.setOpaque(true);
			temp.setBackground(this.dateTitleColor);
			temp.setForeground(this.dateTitleFontColor);
			this.jlDays[0][i] = temp;
		}
		for (int i = 1; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				JLabel temp = new JLabel();
				temp.setHorizontalAlignment(0);
				temp.setVerticalAlignment(0);
				temp.setOpaque(true);
				temp.setForeground(this.dateFontColor);
				this.jlDays[i][j] = temp;
				this.jlDays[i][j].addMouseListener(labelMouseListener);
			}
		}
		String[] days = { "日", "一", "二", "三", "四", "五", "六" };
		for (int i = 0; i < 7; i++) {
			this.jlDays[0][i].setText(days[i]);
		}
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				this.panel_center.add(this.jlDays[i][j]);
			}
		}
	}

	private void changeDays() {
		refreshLabelColor();
		clearDate();
		Calendar calendar = Calendar.getInstance();
		calendar.set(this.year, this.month - 1, 1);
		int day_in_week = calendar.get(7);
		int days = getDaysInMonth(this.year, this.month);
		if (this.day > days) {
			this.day = 1;
		}
		int temp = 0;
		for (int i = day_in_week - 1; i < 7; i++) {
			temp++;
			this.jlDays[1][i].setText(temp + "");
			if (temp == this.day) {
				this.jlDays[1][i].setBackground(this.selectColor);
			}
		}
		for (int i = 2; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				temp++;
				if (temp > days) {
					return;
				}
				this.jlDays[i][j].setText(temp + "");
				if (temp == this.day) {
					this.jlDays[i][j].setBackground(this.selectColor);
				}
			}
		}
	}

	private void refreshLabelColor() {
		for (int i = 1; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				this.jlDays[i][j].setBackground(this.dateColor);
			}
		}
	}

	private void clearDate() {
		for (int i = 1; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				this.jlDays[i][j].setText("");
			}
		}
	}

	public static void closeDatePanel(FloatingPanel panel) {
		panel.setVisible(false);
		panel = null;
	}

	public static DatePanel showDatePanel(JTextComponent textComponent, String dateMask) {
		Point point = new Point();
		point.x = textComponent.getLocationOnScreen().x;
		point.y = textComponent.getLocationOnScreen().y + textComponent.getHeight();
		DatePanel datePanel = new DatePanel(point, textComponent, dateMask);
		datePanel.setVisible(true);
		return datePanel;
	}

	private int getDaysInMonth(int year, int month) {
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return 31;
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
		case 2:
			if (isLeapYear(year)) {
				return 29;
			}
			return 28;
		}
		return 0;
	}

	private boolean isLeapYear(int year) {
		return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.jb_clear)) {
			setYear(1900);
			setMonth(1);
			setDay(1);
			setHour(1);
			setMinute(0);
			setSecond(0);
			setFromParams();
			this.textComponent.setText("");
		} else if (e.getSource().equals(this.jb_today)) {
			HashMap<String, Integer> hm = DateUtil.getCurrentHMTime();
			setYear(((Integer) hm.get("year")).intValue());
			setMonth(((Integer) hm.get("month")).intValue());
			setDay(((Integer) hm.get("day")).intValue());
			setHour(((Integer) hm.get("hour")).intValue());
			setMinute(((Integer) hm.get("minute")).intValue());
			setSecond(((Integer) hm.get("second")).intValue());
			setFromParams();
		} else if (e.getSource().equals(this.jb_yes)) {
			setDate();
			closeDatePanel(this);
		}
	}

	private void setDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(this.year, this.month - 1, this.day, this.hour, this.minute, this.second);

		this.textComponent.setText(DateUtil.formatDate(calendar.getTime(), this.dateMask));
	}

	final class LabelMouseListener extends MouseAdapter {
		LabelMouseListener() {
		}

		public void mouseClicked(MouseEvent e) {
			JLabel temp = (JLabel) e.getSource();
			if (!temp.getText().equals("")) {
				int nowday = Integer.parseInt(temp.getText());
				if (nowday != DatePanel.this.day) {
					DatePanel.this.day = nowday;
					DatePanel.this.refreshLabelColor();
					temp.setBackground(DatePanel.this.selectColor);
				}
				if (e.getClickCount() == 2) {
					DatePanel.this.setDate();
					DatePanel.closeDatePanel(DatePanel.this);
				}
			}
		}

		public void mouseEntered(MouseEvent e) {
			JLabel temp = (JLabel) e.getSource();
			if (!temp.getText().equals("")) {
				temp.setBackground(DatePanel.this.dateHoverColor);
			}
		}

		public void mouseExited(MouseEvent e) {
			JLabel temp = (JLabel) e.getSource();
			if (!temp.getText().equals("")) {
				if (Integer.parseInt(temp.getText()) != DatePanel.this.day) {
					temp.setBackground(DatePanel.this.dateColor);
				} else {
					temp.setBackground(DatePanel.this.selectColor);
				}
			}
		}
	}

	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == 1) {
			String str = (String) e.getItem();
			if (e.getSource().equals(this.cb_hour)) {
				this.hour = Integer.parseInt(str.substring(0, str.length() - 1));
			}
			if (e.getSource().equals(this.cb_minute)) {
				this.minute = Integer.parseInt(str.substring(0, str.length() - 1));
			}
			if (e.getSource().equals(this.cb_second)) {
				this.second = Integer.parseInt(str.substring(0, str.length() - 1));
			}
			if (e.getSource().equals(this.cb_year)) {
				this.year = Integer.parseInt(str.substring(0, str.length() - 1));
				changeDays();
			}
			if (e.getSource().equals(this.cb_month)) {
				this.month = Integer.parseInt(str.substring(0, str.length() - 1));
				changeDays();
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return this.month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return this.day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	private void setSelectedDay(int day) {
		JLabel nowLabel = null;
		for (int i = 1; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				String txt = this.jlDays[i][j].getText();
				if ((txt != null) && (!txt.trim().isEmpty()) && (day == Integer.parseInt(txt))) {
					nowLabel = this.jlDays[i][j];
				}
			}
		}
		if (nowLabel != null) {
			refreshLabelColor();
			nowLabel.setBackground(this.selectColor);
		}
	}

	public int getHour() {
		return this.hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return this.minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSecond() {
		return this.second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public int getWeek() {
		return this.week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public String toString() {
		return "DatePanel [year=" + this.year + ", month=" + this.month + ", day=" + this.day + ", hour=" + this.hour
				+ ", minute=" + this.minute + ", second=" + this.second + "]";
	}
}
