package com.royn.CranModel;

public class QueryResult {

	private int queryNumber;
	private String documentNumber;
	private int documentRank;
	private float documentScore;
	private String cranQueryNumber;

	public QueryResult(int queryNumber, String cranQueryNumber, String documentNumber, int documentRank,
			float documentScore) {
		this.documentNumber = documentNumber;
		this.documentRank = documentRank;
		this.documentScore = documentScore;
		this.queryNumber = queryNumber;
		this.cranQueryNumber = cranQueryNumber;
	}

	public int getQueryNumber() {
		return queryNumber;
	}

	public void setQueryNumber(int queryNumber) {
		this.queryNumber = queryNumber;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public int getDocumentRank() {
		return documentRank;
	}

	public void setDocumentRank(int i) {
		this.documentRank = i;
	}

	public float getDocumentScore() {
		return documentScore;
	}

	public void setDocumentScore(float documentScore) {
		this.documentScore = documentScore;
	}

	public String getCranQueryNumber() {
		return cranQueryNumber;
	}

	public void setCranQueryNumber(String cranQueryNumber) {
		this.cranQueryNumber = cranQueryNumber;
	}
}
