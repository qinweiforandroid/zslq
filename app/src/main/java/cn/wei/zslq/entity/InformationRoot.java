package cn.wei.zslq.entity;

import java.util.ArrayList;


public class InformationRoot {
	private InformationExtraInfoBean extraInfo;

	private int code;

	private String error;

	private ArrayList<InformationBean> results;

	public InformationExtraInfoBean getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(InformationExtraInfoBean extraInfo) {
		this.extraInfo = extraInfo;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public ArrayList<InformationBean> getResults() {
		return results;
	}

	public void setResults(ArrayList<InformationBean> results) {
		this.results = results;
	}

}
