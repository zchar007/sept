package com.sept.jui.progressbar;

import java.awt.Color;
import java.awt.Graphics;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.swing.JComponent;
import javax.swing.JProgressBar;
import javax.swing.plaf.ProgressBarUI;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class SProgressBar extends JProgressBar {
	private static final long serialVersionUID = 1L;
	private LinkedHashMap<Integer, Color> lhmColors;
	private long totalValue = 100L;
	private long nowValue = 0L;
	private BigDecimal bd_totalValue = new BigDecimal(100L);
	private boolean autoPercentage = false;
	private boolean autoTiTle = false;
	private boolean autoTail = false;
	private boolean isFinsh = false;
	private Color indeterminateColor = Color.GRAY;
	private Object key = new Object();
	private LinkedHashMap<Long, Long> times = new LinkedHashMap<>();

	public SProgressBar() {
		this.lhmColors = new LinkedHashMap<>();
		this.lhmColors.put(Integer.valueOf(20), Color.BLUE);
		this.lhmColors.put(Integer.valueOf(40), Color.YELLOW);
		this.lhmColors.put(Integer.valueOf(60), Color.RED);
		this.lhmColors.put(Integer.valueOf(80), Color.GREEN);
		this.lhmColors.put(Integer.valueOf(100), Color.CYAN);
		super.setUI(new SProgressBarUI(this, this.lhmColors));
	}

	public SProgressBar(LinkedHashMap<Integer, Color> lhmColors) {
		this.lhmColors = lhmColors;
		super.setUI(new SProgressBarUI(this, this.lhmColors));
	}

	public void setTotalValue(long totalValue) {
		synchronized (this.key) {
			this.totalValue = totalValue;
			this.bd_totalValue = new BigDecimal(this.totalValue);
		}
	}

	public void setValue(long nowValue) {
		synchronized (this.key) {
			if (times.size() > 3) {
				for (Long key : times.keySet()) {
					times.remove(key);
					break;
				}
			}
			times.put(new Date().getTime(), nowValue);
			setValuePrivate(nowValue);
		}
	}

	private void setValuePrivate(long nowValue) {
		this.nowValue = nowValue;
		BigDecimal bd_nowValue = new BigDecimal(this.nowValue);

		BigDecimal bigDecimalDivide = bd_nowValue.divide(this.bd_totalValue, 4, 4);
		double divide = bigDecimalDivide.doubleValue() * 100.0D;
		String bfb = Double.toString(divide);
		String[] bfbs = bfb.split("\\.");
		bfb = bfbs[0];

		String xs = bfbs[1].substring(0, bfbs[1].length() > 3 ? 3 : bfbs[1].length());
		if (xs.length() == 3) {
			int k = Integer.parseInt(xs.charAt(2) + "");
			xs = xs.substring(0, 2);
			int xsInt = Integer.parseInt(xs);
			if (k >= 5) {
				xsInt++;
			}
			xs = xsInt + "";
		}
		while (xs.length() < 2) {
			xs = xs + "0";
		}
		while (xs.length() > 2) {
			xs = xs.substring(0, xs.length() - 1);
		}
		bfb = bfb + "." + xs;
		if (divide >= 100.0D) {
			setFinsh(true);
			bfb = "100.00";
		}
		super.setValue((int) Double.parseDouble(bfb));
		if (isAutoPercentage()) {
			setString(bfb + "%");
		}
	}

	public boolean isAutoPercentage() {
		return autoPercentage;
	}

	public void setAutoPercentage(boolean autoPercentage) {
		synchronized (this.key) {
			this.autoPercentage = autoPercentage;
			if ((this.autoPercentage) && (!isIndeterminate())) {
				setStringPainted(true);
			} else {
				setStringPainted(false);
			}
		}
	}

	public void setIndeterminate(boolean indeterminate) {
		super.setIndeterminate(indeterminate);
		setStringPainted(!indeterminate);
		if (isIndeterminate()) {
			super.setUI(new SProgressBarUI(this, this.indeterminateColor));
		}
	}

	public void setIndeterminateColor(Color indeterminateColor) {
		synchronized (this.key) {
			this.indeterminateColor = indeterminateColor;
			if (isIndeterminate()) {
				super.setUI(new SProgressBarUI(this, this.indeterminateColor));
			} else {
				super.setUI(new SProgressBarUI(this, this.lhmColors));
			}
		}
	}

	/* Error */
	public boolean isAutoTiTle() {
		return autoTiTle;
	}

	public void setAutoTiTle(boolean autoTiTle) {
		synchronized (this.key) {
			this.autoTiTle = autoTiTle;
		}
	}

	/* Error */
	public boolean isAutoTail() {
		return autoTail;
	}

	public void setAutoTail(boolean autoTail) {
		this.autoTail = autoTail;
	}

	/* Error */
	public boolean isFinsh() {
		return isFinsh;
	}

	public void setFinsh(boolean isFinsh) {
		synchronized (this.key) {
			this.isFinsh = isFinsh;
		}
	}

	/* Error */
	public Color getIndeterminateColor() {
		return indeterminateColor;
	}

	public void setUI(ProgressBarUI ui) {
		try {
			if (isIndeterminate()) {
				super.setUI(new SProgressBarUI(this, this.indeterminateColor));
			} else {
				super.setUI(new SProgressBarUI(this, this.lhmColors));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class SProgressBarUI extends BasicProgressBarUI {
		private JProgressBar jProgressBar;
		private int progressvalue;
		private LinkedHashMap<Integer, Color> lhmColors;
		private Color color;

		public SProgressBarUI(JProgressBar jProgressBar, LinkedHashMap<Integer, Color> lhmColors) {
			this.jProgressBar = jProgressBar;
			this.lhmColors = lhmColors;
		}

		public SProgressBarUI(JProgressBar jProgressBar, Color color) {
			this.jProgressBar = jProgressBar;
			this.color = color;
		}

		protected void paintIndeterminate(Graphics g, JComponent c) {
			this.jProgressBar.setForeground(this.color);
			super.paintIndeterminate(g, c);
		}

		protected void paintDeterminate(Graphics g, JComponent c) {
			this.progressvalue = this.jProgressBar.getValue();
			for (Iterator<Integer> localIterator = this.lhmColors.keySet().iterator(); localIterator.hasNext();) {
				int key = ((Integer) localIterator.next()).intValue();
				if (this.progressvalue < key) {
					this.jProgressBar.setForeground((Color) this.lhmColors.get(Integer.valueOf(key)));
					break;
				}
			}
			super.paintDeterminate(g, c);
		}
	}

	/**
	 * 获取速度
	 * 
	 * @return
	 */
	public long getSpeed() {
		synchronized (this.key) {
			long time1 = 0, time2 = 0, size1 = 0, size2 = 0;
			int index = 0;
			for (java.util.Map.Entry<Long, Long> entry : this.times.entrySet()) {
				if (0 == index) {
					time1 = entry.getKey();
					size1 = entry.getValue();
				}

				if (this.times.size() - 1 == index) {
					time2 = entry.getKey();
					size2 = entry.getValue();
				}
				index++;
			}
			// between
			long costTime = time2 - time1;
			long costSize = size2 - size1;
			if (costTime == 0) {
				return 0;
			}
			long everySecond = costSize * 1000 / costTime;
			return everySecond;
		}
	}

	/**
	 * 获取预计时间（毫秒）
	 * 
	 * @param speed
	 * @return
	 */
	public long estimateTime(long speed) {
		if (speed == 0) {
			return 0;
		}
		return this.totalValue / speed * 1000;
	}

	public void clear() {
		totalValue = 100L;
		nowValue = 0L;
		bd_totalValue = new BigDecimal(100L);
	}
}
