package com.sept.util.bools.comparator;

import java.util.ArrayList;

public class Comparator {
	private ArrayList<CompareCell> compareCells;
	private ArrayList<String> compareConnectors;

	public Comparator(ArrayList<CompareCell> compareCells, ArrayList<String> compareConnectors) {
		this.compareCells = compareCells;
		this.compareConnectors = compareConnectors;
	}

	public ArrayList<CompareCell> getCompareCells() {
		return compareCells;
	}

	public void setCompareCells(ArrayList<CompareCell> compareCells) {
		this.compareCells = compareCells;
	}

	public ArrayList<String> getCompareConnectors() {
		return compareConnectors;
	}

	public void setCompareConnectors(ArrayList<String> compareConnectors) {
		this.compareConnectors = compareConnectors;
	}

	@Override
	public String toString() {
		String str =  "Comparator [compareCells=";
		for (CompareCell compareCell : compareCells) {
			str+="{"+compareCell.toString()+"},";
		}
		
		
		str+= ", compareConnectors=" + compareConnectors + "]";
		return str;
	}

}
