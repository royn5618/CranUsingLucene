package com.royn.CranCommon;

public class Constants {
	
	public static String[] ALL_ANALYZERS = { "StandardAnalyser", "EnglishAnalyzer", "MySnowballAnalyzer",
			"MyPorterStemmerAnalyzer" };
	public static String[] ALL_SIMILARITIES = { "TFIDF", "LMD", "BM25", "DFR1", "DFR2" };

	public static String CRAN_DOCS_PATH = "cran/cran.all.1400";
	public static String CRAN_QUERIES_PATH = "cran/cran.qry";
	public static String CRAN_INDICES_PATH = "indices";
	public static String CRAN_RESULTS_PATH = "results";
	
	public static String CRAN_DELIMITER = ".I";
	public static String CRAN_DOC_PATTERN = "\n.[A-Z]";
	public static String CRAN_QUERY_PATTERN = "\\r\\n.W\\r\\n";
	
	public static String[] DOC_FIELDS = { "docNo", "title", "bib", "words", "authors" };
	
}
