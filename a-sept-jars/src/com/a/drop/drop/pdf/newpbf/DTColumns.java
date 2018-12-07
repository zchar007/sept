package com.sept.drop.pdf.newpbf;

import java.util.ArrayList;
import java.util.HashMap;

import com.sept.exception.AppException;

public class DTColumns implements IDTColumns {
	private HashMap<String, DTColumn> hmClumnsByKey;
	private HashMap<String, DTColumn> hmClumnsByHead;
	private ArrayList<String> alKeys;

	public DTColumns() {
		this.hmClumnsByKey = new HashMap<>();
		this.hmClumnsByHead = new HashMap<>();
		this.alKeys = new ArrayList<>();
	}

	@Override
	public DTColumn getColumn(int index) throws AppException {
		return this.hmClumnsByKey.get(alKeys.get(index));
	}

	@Override
	public DTColumn getColumnByKey(String key) throws AppException {
		return this.hmClumnsByKey.get(key);
	}

	@Override
	public DTColumn getColumnByHead(String head) throws AppException {
		return this.hmClumnsByHead.get(head);
	}

	@Override
	public void addColumn(DTColumn dtColumn) throws AppException {
		this.alKeys.add(dtColumn.getKey());
		this.hmClumnsByKey.put(dtColumn.getKey(), dtColumn);
		this.hmClumnsByHead.put(dtColumn.getHead(), dtColumn);
	}

	@Override
	public void insertColumn(int index, DTColumn dtColumn) throws AppException {
		if (index >= 0 && index < this.alKeys.size()) {
			this.alKeys.add("");
			for (int i = this.alKeys.size(); i > index; i--) {
				this.alKeys.set(i, this.alKeys.get(i - 1));
			}
			this.alKeys.set(index, dtColumn.getKey());
			this.hmClumnsByKey.put(dtColumn.getKey(), dtColumn);
			this.hmClumnsByHead.put(dtColumn.getHead(), dtColumn);
		}
	}

	@Override
	public void setColumn(int index, DTColumn dtColumn) throws AppException {
		if (index >= 0 && index < this.alKeys.size()) {
			this.alKeys.set(index, dtColumn.getKey());
			this.hmClumnsByKey.put(dtColumn.getKey(), dtColumn);
			this.hmClumnsByHead.put(dtColumn.getHead(), dtColumn);
		}
	}

	@Override
	public void deleteColumn(int index) throws AppException {
		if (index >= 0 && index < this.alKeys.size()) {
			String key = this.alKeys.get(index);
			String head = this.hmClumnsByKey.get(key).getHead();
			this.hmClumnsByKey.remove(key);
			this.hmClumnsByHead.remove(head);
		}
	}

	@Override
	public void deleteColumn(DTColumn idtColumn) throws AppException {
		if (this.alKeys.contains(idtColumn.getKey())) {
			this.alKeys.remove(idtColumn.getKey());
			this.hmClumnsByKey.remove(idtColumn.getKey());
			this.hmClumnsByHead.remove(idtColumn.getHead());
		}
	}

	@Override
	public ArrayList<String> getHead() throws AppException {
		return this.alKeys;
	}

	@Override
	public int columnCount() throws AppException {
		return this.alKeys.size();
	}

	@Override
	public boolean checkColumn(DTColumn idtColumn) throws AppException {
		return false;
	}
}
