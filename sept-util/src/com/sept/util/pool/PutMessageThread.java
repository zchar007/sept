package com.sept.util.pool;



class PutMessageThread implements Runnable{
		private String key;
		private Object value;
		private MessagePool mp;

		public PutMessageThread(String key, Object value, MessagePool mp) {
			super();
			this.key = key;
			this.value = value;
			this.mp = mp;
		}

		@Override
		public void run() {
			mp.putMessage(key, value);
		}

	}