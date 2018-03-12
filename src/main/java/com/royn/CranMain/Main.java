package com.royn.CranMain;

import com.royn.CranIndexing.IndexCranDocs;
import com.royn.CranModel.QueryResult;
import java.util.ArrayList;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.similarities.Similarity;
import java.io.File;
import com.royn.CranCommon.Constants;
import com.royn.CranCommon.CranUtils;
import com.royn.CranSearching.SearchCranDocs;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		// String indexPath = Constants.CRAN_INDICES_PATH +
		// String.valueOf(LocalDateTime.now());
		// CranUtils.DirectoryCheck(indexPath);
		CranUtils.DirectoryCheck(Constants.CRAN_INDICES_PATH);

		// String resultPath = Constants.CRAN_RESULTS_PATH +
		// String.valueOf(LocalDateTime.now());
		// CranUtils.DirectoryCheck(resultPath);

		CranUtils.DirectoryCheck(Constants.CRAN_RESULTS_PATH);

		// Indexing

		System.out.println("Indexing Start");
		for (String analyzerName : Constants.ALL_ANALYZERS) {
			Analyzer analyzer = CranUtils.getAnalyzer(analyzerName, null);
			for (String similarityName : Constants.ALL_SIMILARITIES) {
				Similarity similarity = CranUtils.getSimilarity(similarityName);
				IndexCranDocs instance = new IndexCranDocs(analyzer, similarity, Constants.CRAN_DOCS_PATH,
						Constants.DOC_FIELDS, Constants.CRAN_INDICES_PATH + "/" + Constants.CRAN_INDICES_PATH + "_" + analyzerName + "_" + similarityName);
				instance.indexFiles();
			}
		}
		System.out.println("Indexing Completed");

		// Searching
		for (String analyzerName : Constants.ALL_ANALYZERS) {
			Analyzer analyzer = CranUtils.getAnalyzer(analyzerName, null);
			for (String similarityName : Constants.ALL_SIMILARITIES) {
				Similarity similarity = CranUtils.getSimilarity(similarityName);
				File[] allIndicesList = CranUtils.getAllIndexFolders(Constants.CRAN_INDICES_PATH);
				for (File indexPathForSearch : allIndicesList) {
					SearchCranDocs search = new SearchCranDocs(indexPathForSearch.toString(), analyzer, similarity);
					ArrayList<QueryResult> allResults = search.SearchCran();
					String resultPathForSearch = Constants.CRAN_RESULTS_PATH + "/" + Constants.CRAN_RESULTS_PATH + "_"
							+ analyzerName + "_" + similarityName;
					CranUtils.DirectoryCheck(resultPathForSearch);
					CranUtils.WriteResults(allResults, resultPathForSearch);
				}
			}
		}
	}

}
