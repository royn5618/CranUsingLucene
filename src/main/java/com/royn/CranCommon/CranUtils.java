package com.royn.CranCommon;

import com.royn.CranCommon.Constants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.similarities.AfterEffectB;
import org.apache.lucene.search.similarities.AfterEffectL;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.BasicModelBE;
import org.apache.lucene.search.similarities.BasicModelIne;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.DFRSimilarity;
import org.apache.lucene.search.similarities.LMDirichletSimilarity;
import org.apache.lucene.search.similarities.NormalizationH2;
import org.apache.lucene.search.similarities.NormalizationZ;
import org.apache.lucene.search.similarities.Similarity;

import com.royn.CranCustomAnalyzers.MyPorterAnalyzer;
import com.royn.CranCustomAnalyzers.MySnowballAnalyzer;
import com.royn.CranModel.QueryResult;

public class CranUtils {

	public static void DirectoryCheck(String path) {
		File pathDir = new File(path);
		if (!pathDir.exists()) {
			System.out.println("creating directory: " + pathDir.getName());
			pathDir.mkdir();
		} else {
			System.out.println(pathDir.getName() + "exists.");
		}
	}

	public static String ReadFileAsString(String path) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(path)));
		return data;
	}

	public static boolean CheckReadability(String path) {
		final Path docDir = Paths.get(path);
		if (!Files.isReadable(docDir)) {
			System.out.println("Document directory '" + docDir.toAbsolutePath()
					+ "' does not exist or is not readable, please check the path");
			return false;
		}
		return true;
	}

	public static Analyzer getAnalyzer(String analyzerType, String tokenizer) {
		Analyzer analyzer = null;
		System.out.println("Selected analyzer: " + analyzerType);
		if (analyzerType == Constants.ALL_ANALYZERS[0]) {
			analyzer = new StandardAnalyzer();
			return analyzer;
		} else if (analyzerType == Constants.ALL_ANALYZERS[1]) {
			analyzer = new EnglishAnalyzer();
			return analyzer;
		} else if (analyzerType == Constants.ALL_ANALYZERS[2]) {
			analyzer = new MySnowballAnalyzer();
			return analyzer;
		} else if (analyzerType == Constants.ALL_ANALYZERS[3]) {
			analyzer = new MyPorterAnalyzer();
			return analyzer;
		}
		return analyzer;
	}

	public static Similarity getSimilarity(String similarityType) {
		Similarity similarity = null;
		System.out.println("Selected similarity: " + similarityType);
		if (similarityType == Constants.ALL_SIMILARITIES[0]) {
			similarity = new ClassicSimilarity();
			return similarity;
		} else if (similarityType == Constants.ALL_SIMILARITIES[1]) {
			similarity = new LMDirichletSimilarity();
			return similarity;
		} else if (similarityType == Constants.ALL_SIMILARITIES[2]) {
			similarity = new BM25Similarity();
			return similarity;
		} else if (similarityType == Constants.ALL_SIMILARITIES[3]) {
			similarity = new DFRSimilarity(new BasicModelBE(), new AfterEffectL(), new NormalizationH2());
			return similarity;
		} else if (similarityType == Constants.ALL_SIMILARITIES[4]) {
			similarity = new DFRSimilarity(new BasicModelIne(), new AfterEffectB(), new NormalizationZ());
			return similarity;
		}
		return similarity;
	}

	public static void WriteResults(ArrayList<QueryResult> allResults, String resultPath) throws IOException {
		StringBuilder sbResults = new StringBuilder();
		for (QueryResult qres : allResults) {
			sbResults.append(qres.getQueryNumber());
			sbResults.append("\t" + qres.getCranQueryNumber());
			sbResults.append("\t" + qres.getDocumentScore());
			sbResults.append("\t" + qres.getDocumentRank());
			sbResults.append("\t" + "DUMMY" + "\n");
		}
		BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(resultPath + "/result.out")));
		writer.write(sbResults.toString());
		writer.close();
	}

	public static File[] getAllIndexFolders(String indexMainPath) {
		File directory = new File(indexMainPath + "/");
		File[] indexPathList = directory.listFiles();
		return indexPathList;
	}
	
	public static String getTimeStamp() {
		return String.valueOf(LocalDateTime.now());
	}
}
