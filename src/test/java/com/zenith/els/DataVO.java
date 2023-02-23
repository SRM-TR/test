package com.zenith.els;

public class DataVO 
{
	private String time;
	
	private String value;
	
	public DataVO(String time, String value) {
		super();
		this.time = time;
		this.value = value;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "DataVO [time=" + time + ", value=" + value + "]";
	}
}
