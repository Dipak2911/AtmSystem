package com.dipak.atmsystem;

public class Resp {

		private boolean status;
		private int bal;
		
		public Resp(boolean status, int bal) {
			this.status = status;
			this.bal = bal;
		}
		public boolean isStatus() {
			return status;
		}
		public void setStatus(boolean status) {
			this.status = status;
		}
		public int getBal() {
			return bal;
		}
		public void setBal(int bal) {
			this.bal = bal;
		}
	
}
