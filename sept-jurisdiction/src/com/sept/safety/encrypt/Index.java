package com.sept.safety.encrypt;

class Index{
	private String key;
	private int index = -1;

	public Index(String key) {
		super();
		this.key = key;
	}

	public char getNextKey() {
		index = index + 1;
		if (index >= key.length()) {
			index = 0;
		}

		return key.charAt(index);
	}
	public static void main(String[] args) {
		Index ix = new Index("12345");
		for (int i = 0; i < 100; i++) {
			System.out.println(ix.getNextKey());
		}
		
	}

}
