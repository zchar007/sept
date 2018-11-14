package com.sept.jui.input.combox.date;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sept.exception.AppException;
import com.sept.util.DateUtil;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class SDatePicker extends JComponent {
	public static final String SELECTEDDATECHANGE = "selecteddatechange";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * label按钮鼠标掠过颜色
	 */
	private static final Color BTH_COLOR_HOVER = Color.BLUE;
	/**
	 * label按钮底色
	 */
	private static final Color BTH_COLOR_NORMAL = Color.GRAY;
	/**
	 * label按钮鼠标按下颜色
	 */
	private static final Color BTH_COLOR_CLICK = Color.RED;

	/**
	 * 日期选择背景色
	 */
	private static final Color DATE_COLOR_SELECT = Color.GREEN;
	/**
	 * 日期背景色
	 */
	private static final Color DATE_COLOR_NORMAL = Color.WHITE;
	/**
	 * 日期鼠标进入背景色
	 */
	private static final Color DATE_COLOR_HOVER = Color.LIGHT_GRAY;
	/**
	 * 日期标题背景色
	 */
	private static final Color DATE_COLOR_TITLE_NORMAL = Color.GRAY;
	/**
	 * 日期标题字体颜色
	 */
	private static final Color DATE_COLOR_TITLE_FONT = Color.BLACK;
	/**
	 * 日期字体颜色
	 */
	private static final Color DATE_COLOR_FONT = Color.BLACK;
	/**
	 * 北侧panel
	 */
	private JPanel panel_north;
	/**
	 * 上一年按钮
	 */
	private JLabel label_previousYear;
	/**
	 * 下一年按钮
	 */
	private JLabel label_nextYear;
	/**
	 * 上一月按钮
	 */
	private JLabel label_previousMonth;
	/**
	 * 下一月按钮
	 */
	private JLabel label_nextMonth;
	/**
	 * 年选择
	 */
	private JComboBox<String> comboBox_year;
	/**
	 * 月选择
	 */
	private JComboBox<String> comboBox_month;
	/**
	 * 中间
	 */
	private JPanel panel_center;
	/**
	 * 年组
	 */
	private static final String[] YEARS_ARRAY = new String[150];
	/**
	 * 月组
	 */
	private static final String[] MONTHS_ARRAY = new String[] { "01月", "02月", "03月", "04月", "05月", "06月", "07月", "08月",
			"09月", "10月", "11月", "12月" };

	/**
	 * 年
	 */
	private int year;
	/**
	 * 月
	 */
	private int month;
	/**
	 * 日
	 */
	private int day;
	/**
	 * 时
	 */
	private int hour;
	/**
	 * 分
	 */
	private int minute;
	/**
	 * 秒
	 */
	private int second;
	/**
	 * 星期
	 */
	private int week;
	/**
	 * 上午下午
	 */
	private int am_pm;
	/**
	 * 天标签标题
	 */
	private JLabel[] label_days_title = new JLabel[] { new JLabel("日"), new JLabel("一"), new JLabel("二"),
			new JLabel("三"), new JLabel("四"), new JLabel("五"), new JLabel("六") };
	/**
	 * 天标签
	 */
	private JLabel[][] label_days = new JLabel[6][7];
	private JComboBox<String> comboBox_second;
	private JComboBox<String> comboBox_minute;
	private JComboBox<String> comboBox_hour;
	private JLabel label_clear;
	private JLabel label_today;
	private JLabel label_yes;
	static {
		int nowYear = Integer.parseInt(DateUtil.getCurrentDate("yyyy"));
		int startYear = nowYear - 100;
		for (int i = 0; i < YEARS_ARRAY.length; i++) {
			YEARS_ARRAY[i] = (startYear++) + "年";
		}
	}

	/**
	 * Create the panel.
	 */
	public SDatePicker(Date date) {
		setLayout(new BorderLayout(0, 0));
		initGui(date);
	}

	/**
	 * Create the panel.
	 */
	public SDatePicker() {
		setLayout(new BorderLayout(0, 0));
		this.initGui(new Date());
	}

	/**
	 * 初始化UI
	 */
	private void initGui(Date d) {
		initNorth();
		initCenter();
		initSouth();
		initDate(d);
	}

	/**
	 * 北侧UI
	 */
	private void initNorth() {
		panel_north = new JPanel();
		add(panel_north, BorderLayout.NORTH);
		panel_north.setLayout(new GridLayout(0, 4, 0, 0));

		JPanel panel_1 = new JPanel();
		panel_north.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));

		label_previousYear = new JLabel(" << ");
		label_previousYear.setBackground(BTH_COLOR_NORMAL);
		label_previousYear.setOpaque(true);
		panel_1.add(label_previousYear);

		label_previousMonth = new JLabel(" < ");
		label_previousMonth.setBackground(BTH_COLOR_NORMAL);
		label_previousMonth.setOpaque(true);
		panel_1.add(label_previousMonth);

		comboBox_year = new JComboBox<String>(YEARS_ARRAY) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void hidePopup() {
				// setPopupVisible(true);
			}
		};
		panel_north.add(comboBox_year);

		comboBox_month = new JComboBox<String>(MONTHS_ARRAY);
		panel_north.add(comboBox_month);

		JPanel panel_2 = new JPanel();
		panel_north.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 2, 0, 0));

		label_nextMonth = new JLabel(" > ");
		label_nextMonth.setBackground(BTH_COLOR_NORMAL);
		label_nextMonth.setOpaque(true);
		panel_2.add(label_nextMonth);

		label_nextYear = new JLabel(" >> ");
		label_nextYear.setBackground(BTH_COLOR_NORMAL);
		label_nextYear.setOpaque(true);
		panel_2.add(label_nextYear);
		initNorthLogic();
	}

	private void initNorthLogic() {
		String nowYear = DateUtil.getCurrentDate("yyyy年");
		String nowMonth = DateUtil.getCurrentDate("MM月");
		comboBox_year.setSelectedItem(nowYear);
		comboBox_month.setSelectedItem(nowMonth);
		ButtonLabelMouseListener buttonLabelMouseListener = new ButtonLabelMouseListener();
		label_previousYear.addMouseListener(buttonLabelMouseListener);
		label_nextYear.addMouseListener(buttonLabelMouseListener);
		label_previousMonth.addMouseListener(buttonLabelMouseListener);
		label_nextMonth.addMouseListener(buttonLabelMouseListener);

		label_previousYear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int yearNow = SDatePicker.this.getYear() - 1;
				SDatePicker.this.setYear(yearNow);
			}
		});
		label_nextYear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int yearNow = SDatePicker.this.getYear() + 1;
				SDatePicker.this.setYear(yearNow);
			}
		});
		label_previousMonth.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int monthNow = SDatePicker.this.getMonth() - 1;
				SDatePicker.this.setMonth(monthNow);
			}
		});
		label_nextMonth.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int monthNow = SDatePicker.this.getMonth() + 1;
				SDatePicker.this.setMonth(monthNow);
			}
		});
		ComboBoxItemListener comboBoxItemListener = new ComboBoxItemListener();
		comboBox_year.addItemListener(comboBoxItemListener);
		comboBox_month.addItemListener(comboBoxItemListener);
	}

	/**
	 * 中间UI
	 */
	private void initCenter() {
		panel_center = new JPanel();
		add(panel_center, BorderLayout.CENTER);
		panel_center.setLayout(new GridLayout(0, 7, 0, 7));

		for (int i = 0; i < label_days_title.length; i++) {
			label_days_title[i].setHorizontalAlignment(JLabel.CENTER);
			label_days_title[i].setVerticalAlignment(JLabel.CENTER);
			label_days_title[i].setBackground(SDatePicker.DATE_COLOR_TITLE_NORMAL);
			label_days_title[i].setForeground(SDatePicker.DATE_COLOR_TITLE_FONT);
			panel_center.add(label_days_title[i]);
		}
		DayLabelMouseListener dayLabelMouseListener = new DayLabelMouseListener();
		for (int i = 0; i < label_days.length; i++) {
			for (int j = 0; j < label_days[i].length; j++) {
				label_days[i][j] = new JLabel();
				label_days[i][j].setHorizontalAlignment(JLabel.CENTER);
				label_days[i][j].setVerticalAlignment(JLabel.CENTER);
				label_days[i][j].setOpaque(true);
				label_days[i][j].setBackground(SDatePicker.DATE_COLOR_NORMAL);
				label_days[i][j].setForeground(SDatePicker.DATE_COLOR_FONT);
				label_days[i][j].addMouseListener(dayLabelMouseListener);
				panel_center.add(label_days[i][j]);
			}
		}

		initCenterLogic();
	}

	private void initCenterLogic() {

	}

	/**
	 * 南侧UI
	 */
	private void initSouth() {
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(0, 1, 0, 2));

		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 6, 0, 1));

		JLabel label_hour = new JLabel("时");
		label_hour.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(label_hour);

		comboBox_hour = new JComboBox<String>();
		panel_1.add(comboBox_hour);

		JLabel label_minute = new JLabel("分");
		label_minute.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(label_minute);

		comboBox_minute = new JComboBox<String>();
		panel_1.add(comboBox_minute);

		JLabel label_second = new JLabel("秒");
		label_second.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(label_second);

		comboBox_second = new JComboBox<String>();
		panel_1.add(comboBox_second);

		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 3, 0, 1));

		label_clear = new JLabel("清空");
		label_clear.setOpaque(true);
		label_clear.setBackground(SDatePicker.BTH_COLOR_NORMAL);
		panel_2.add(label_clear);

		label_today = new JLabel("今日");
		label_today.setOpaque(true);
		label_today.setBackground(SDatePicker.BTH_COLOR_NORMAL);
		panel_2.add(label_today);

		label_yes = new JLabel("确认");
		label_yes.setOpaque(true);
		label_yes.setBackground(SDatePicker.BTH_COLOR_NORMAL);
		panel_2.add(label_yes);
		initSouthLogic();
	}

	private void initSouthLogic() {
		ComboBoxItemListener comboBoxItemListener = new ComboBoxItemListener();
		comboBox_hour.addItemListener(comboBoxItemListener);
		comboBox_minute.addItemListener(comboBoxItemListener);
		comboBox_second.addItemListener(comboBoxItemListener);

		for (int i = 1; i <= 23; i++) {
			comboBox_hour.addItem(i + "");
		}
		comboBox_hour.addItem("0");

		for (int i = 1; i < 60; i++) {
			comboBox_minute.addItem(i + "");

		}

		for (int i = 1; i < 60; i++) {
			comboBox_second.addItem(i + "");

		}
		ButtonLabelMouseListener buttonLabelMouseListener = new ButtonLabelMouseListener();
		label_clear.addMouseListener(buttonLabelMouseListener);
		label_today.addMouseListener(buttonLabelMouseListener);
		label_yes.addMouseListener(buttonLabelMouseListener);

		label_clear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SDatePicker.this.initDate(new Date());
			}
		});
		label_today.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SDatePicker.this.initDate(new Date());
				SDatePicker.this.setSelectedDate();
			}
		});
		label_yes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SDatePicker.this.setSelectedDate();
			}
		});

	}

	private void initDate(Date d) {
		HashMap<String, Integer> hmDate = DateUtil.getHMTime(d);
		this.setYear(hmDate.get("year"));
		this.setMonth(hmDate.get("month"));
		this.setDay(hmDate.get("day"));
		this.setHour(hmDate.get("hour"));
		this.setMinute(hmDate.get("minute"));
		this.setSecond(hmDate.get("second"));
		this.setWeek(hmDate.get("week"));
		this.setAm_pm(hmDate.get("am_pm"));

	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
		comboBox_year.setSelectedItem(year + "年");
		setDayLabel(this.year, this.month);
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		if (month < 1) {
			month = 12;
			this.year = this.getYear() - 1;
			comboBox_year.setSelectedItem(this.year + "年");
		} else if (month > 12) {
			month = 1;
			this.year = this.getYear() + 1;
			comboBox_year.setSelectedItem(this.year + "年");
		}
		this.month = month;
		if (month >= 10) {
			comboBox_month.setSelectedItem(month + "月");
		} else {
			comboBox_month.setSelectedItem("0" + month + "月");
		}
		setDayLabel(this.year, this.month);
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
		for (int i = 0; i < label_days.length; i++) {
			for (int j = 0; j < label_days[i].length; j++) {
				String txt = label_days[i][j].getText();
				if (txt.equals(this.day + "")) {
					label_days[i][j].setBackground(SDatePicker.DATE_COLOR_SELECT);
				} else {
					label_days[i][j].setBackground(SDatePicker.DATE_COLOR_NORMAL);
				}
			}
		}

	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
		comboBox_hour.setSelectedItem(hour + "");
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
		comboBox_minute.setSelectedItem(minute + "");
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
		comboBox_second.setSelectedItem(second + "");
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public int getAm_pm() {
		return am_pm;
	}

	public void setAm_pm(int am_pm) {
		this.am_pm = am_pm;
	}

	private void setDayLabel(int year, int month) {
		if (year > 0 && month > 0 && month < 13) {

			// 获取本月有多少天
			try {
				Calendar calendar = Calendar.getInstance();
				calendar.set(year, month - 1, 1);
				// 获取本月第一天是周几
				int day_in_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
				int days = DateUtil.getDaysInMonth(year, month);
				int kday = day_in_week;
				if (kday == 7) {
					kday = 0;
				}

				if (this.day > days) {
					this.day = 1;
				}

				int index = 1;
				for (int i = 0; i < label_days.length; i++) {
					for (int j = 0; j < label_days[i].length; j++) {
						if (kday > 0) {
							kday--;
							label_days[i][j].setText("");
							label_days[i][j].setBackground(SDatePicker.DATE_COLOR_NORMAL);
							continue;
						}
						if (index <= days) {
							label_days[i][j].setText(index + "");
							if (this.day == index) {
								label_days[i][j].setBackground(SDatePicker.DATE_COLOR_SELECT);
							} else {
								label_days[i][j].setBackground(SDatePicker.DATE_COLOR_NORMAL);
							}
							index++;
						} else {
							label_days[i][j].setText("");
							label_days[i][j].setBackground(SDatePicker.DATE_COLOR_NORMAL);
						}

					}
				}

			} catch (AppException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 解析年份或月份
	 * 
	 * @param yearOrMonth 年份或月份字符串
	 * @return 年份或月份
	 */
	private int parseYearOrMonth(String yearOrMonth) {
		return Integer.parseInt(yearOrMonth.substring(0, yearOrMonth.length() - 1));
	}

	/**
	 * 年月选择监听器
	 * 
	 * @author zchar
	 *
	 */
	final class ComboBoxItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			firePropertyChange("other", true, false);
			if (e.getSource() == comboBox_year) {
				int year = parseYearOrMonth(comboBox_year.getSelectedItem().toString());
				if (year != SDatePicker.this.year) {
					SDatePicker.this.setYear(year);
				}
			} else if (e.getSource() == comboBox_month) {
				int month = parseYearOrMonth(comboBox_month.getSelectedItem().toString());
				if (month != SDatePicker.this.month) {
					SDatePicker.this.setMonth(month);
				}
			} else if (e.getSource() == comboBox_hour) {
				int hour = Integer.parseInt(comboBox_hour.getSelectedItem().toString());
				if (hour != SDatePicker.this.hour) {
					System.out.println(hour);
					SDatePicker.this.setHour(hour);
					System.out.println(SDatePicker.this.hour);
				}
			} else if (e.getSource() == comboBox_minute) {
				int minute = Integer.parseInt(comboBox_minute.getSelectedItem().toString());
				if (minute != SDatePicker.this.minute) {
					SDatePicker.this.setMinute(minute);
				}
			} else if (e.getSource() == comboBox_second) {
				int second = Integer.parseInt(comboBox_second.getSelectedItem().toString());
				if (second != SDatePicker.this.second) {
					SDatePicker.this.setSecond(second);
				}
			}
		}
	}

	/**
	 * 标签鼠标监听
	 * 
	 * @author jianggujin
	 * 
	 */
	final class ButtonLabelMouseListener extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			e.getComponent().setBackground(SDatePicker.BTH_COLOR_CLICK);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			e.getComponent().setBackground(SDatePicker.BTH_COLOR_HOVER);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			e.getComponent().setBackground(SDatePicker.BTH_COLOR_HOVER);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			e.getComponent().setBackground(SDatePicker.BTH_COLOR_NORMAL);
		}

	}

	/**
	 * 标签鼠标监听
	 * 
	 * @author jianggujin
	 * 
	 */
	final class DayLabelMouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			JLabel labelNow = (JLabel) e.getComponent();

			if (e.getClickCount() == 2) {
				SDatePicker.this.day = Integer.parseInt(labelNow.getText());
				SDatePicker.this.setSelectedDate();
			} else {
				if (!labelNow.getText().equals(SDatePicker.this.day + "")) {
					for (int i = 0; i < label_days.length; i++) {
						for (int j = 0; j < label_days[i].length; j++) {
							String txt = label_days[i][j].getText();
							if (null != txt && !txt.trim().isEmpty()) {
								label_days[i][j].setBackground(SDatePicker.DATE_COLOR_NORMAL);
							}
						}
					}
					labelNow.setBackground(SDatePicker.DATE_COLOR_SELECT);
					SDatePicker.this.day = Integer.parseInt(labelNow.getText());
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			JLabel labelNow = (JLabel) e.getComponent();
			String txt = labelNow.getText();
			if (null != txt && !txt.trim().isEmpty() && !txt.equals(SDatePicker.this.day + "")) {
				e.getComponent().setBackground(SDatePicker.DATE_COLOR_HOVER);
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			JLabel labelNow = (JLabel) e.getComponent();
			String txt = labelNow.getText();
			if (null != txt && !txt.trim().isEmpty() && !txt.equals(SDatePicker.this.day + "")) {
				e.getComponent().setBackground(SDatePicker.DATE_COLOR_NORMAL);
			}
		}

	}

	public Date getSelectedDate() {
		String month = SDatePicker.this.month < 10 ? "0" + SDatePicker.this.month : SDatePicker.this.month + "";
		String day = SDatePicker.this.day < 10 ? "0" + SDatePicker.this.day : SDatePicker.this.day + "";
		String hour = SDatePicker.this.hour < 10 ? "0" + SDatePicker.this.hour : SDatePicker.this.hour + "";
		String minute = SDatePicker.this.minute < 10 ? "0" + SDatePicker.this.minute : SDatePicker.this.minute + "";
		String second = SDatePicker.this.second < 10 ? "0" + SDatePicker.this.second : SDatePicker.this.second + "";
		try {
			return DateUtil.formatStrToDate(year + month + day + hour + minute + second);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setSelectedDate() {
		this.firePropertyChange(SELECTEDDATECHANGE, null, getSelectedDate());
	}
}
