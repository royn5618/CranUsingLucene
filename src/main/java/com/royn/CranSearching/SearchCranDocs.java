package com.royn.CranSearching;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.FSDirectory;
import com.royn.CranCommon.Constants;
import com.royn.CranCommon.CranUtils;
import com.royn.CranModel.QueryResult;

public class SearchCranDocs {

	private static String indexPath;
	private static String cranQueryPath;
	private static Analyzer analyzer;
	private static Similarity similarity;

	public SearchCranDocs(String indexPath, Analyzer analyzer, Similarity similarity) {
		// TODO Auto-generated constructor stub
		SearchCranDocs.indexPath = indexPath;
		SearchCranDocs.cranQueryPath = Constants.CRAN_QUERIES_PATH;
		SearchCranDocs.analyzer = analyzer;
		SearchCranDocs.similarity = similarity;
	}

	public ArrayList<QueryResult> SearchCran() throws Exception {

		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
		IndexSearcher searcher = new IndexSearcher(reader);
		searcher.setSimilarity(similarity);
		ArrayList<QueryResult> allResults = new ArrayList<QueryResult>();

		QueryParser parser = new MultiFieldQueryParser(Constants.DOC_FIELDS, analyzer);

		Map<String, String> allQueriesMap = getAllQueries();
		int counter = 1;
		for (Map.Entry<String, String> eachQuery : allQueriesMap.entrySet()) {
			String formatQuery = QueryParser.escape(eachQuery.getValue());
			Query query = parser.parse(formatQuery);
			TopDocs results = searcher.search(query, 1000);
			ScoreDoc[] hits = results.scoreDocs;

			int num = (int) Math.min(results.totalHits, 1000);
			for (int i = 1; i < num; i++) {
				int indexDocNo = hits[i].doc;
				Document value = reader.document(indexDocNo);
				String[] docNo = value.getValues(Constants.DOC_FIELDS[0]);

				QueryResult qrs = new QueryResult(counter, eachQuery.getKey(), docNo[0], i, hits[i].score);
				allResults.add(qrs);
			}
			counter++;
		}
		return allResults;
	}

	public static Map<String, String> getAllQueries() throws Exception {
		String queriesDoc = CranUtils.ReadFileAsString(cranQueryPath);
		String[] allQueries = queriesDoc.split(Constants.CRAN_DELIMITER);
		Map<String, String> queryMap = new HashMap<String, String>();
		allQueries = Arrays.copyOfRange(allQueries, 1, allQueries.length);
		for (String eachQuery : allQueries) {
			String[] splits = eachQuery.split(Constants.CRAN_QUERY_PATTERN);
			queryMap.put(splits[0], splits[1]);
		}
		return queryMap;
	}

}
