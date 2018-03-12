package com.royn.CranIndexing;

import com.royn.CranCommon.CranUtils;
import com.royn.CranCommon.Constants;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class IndexCranDocs {

	private static String cranDocPath;
	private static String[] indexFields;
	private static Analyzer analyzer;
	private static Similarity similarity;
	private static String indexPath;

	public IndexCranDocs(Analyzer analyzer, Similarity similarity, String cranDocPath, String[] indexFields, String indexPath) {
		// TODO Auto-generated constructor stub
		IndexCranDocs.cranDocPath = cranDocPath;
		IndexCranDocs.indexFields = indexFields;
		IndexCranDocs.analyzer = analyzer;
		IndexCranDocs.similarity = similarity;
		IndexCranDocs.indexPath = indexPath;
	}

	public void indexFiles() throws Exception {
		if (!CranUtils.CheckReadability(cranDocPath)) {
			System.exit(1);
		}
		
		CranUtils.DirectoryCheck(indexPath);
		
		Directory indexDir = FSDirectory.open(Paths.get(indexPath));
		IndexWriterConfig iwc = null;
		iwc = new IndexWriterConfig(analyzer);
		iwc.setSimilarity(similarity);

		iwc.setOpenMode(OpenMode.CREATE);
		IndexWriter iwriter = new IndexWriter(indexDir, iwc);

		String[] cranDocSplits = splitDocs(cranDocPath);
		for (String cranDocSplit : cranDocSplits) {
			indexCranSplit(cranDocSplit, iwriter);
		}
		iwriter.close();
	}

	private void indexCranSplit(String cranDocSplit, IndexWriter writer) throws IOException {
		// TODO Auto-generated method stub
		Document doc = new Document();

		String[] splits = cranDocSplit.split(Constants.CRAN_DOC_PATTERN);
		
		String docNo = splits[0];
		doc.add(new StringField(indexFields[0], docNo, Field.Store.YES));

		String title = splits[1];
		if (title == null || title == "") {
			title = "null";
		}
		doc.add(new TextField(indexFields[1], title, Field.Store.NO));

		String authors = splits[2];
		if (authors == null || authors == "") {
			authors = "null";
		}
		doc.add(new TextField(indexFields[2], authors, Field.Store.NO));

		String bib = splits[3];
		if (bib == null || bib == "") {
			bib = "null";
		}
		doc.add(new TextField(indexFields[3], bib, Field.Store.NO));

		StringBuilder sb = new StringBuilder();
		for (int i = 4; i < splits.length; i++) {
			sb.append(splits[i]);
		}
		String words = sb.toString();
		doc.add(new TextField(indexFields[4], words, Field.Store.NO));

		if (doc != null) {
			writer.addDocument(doc);
		}
	}

	private String[] splitDocs(String path) throws Exception {

		String cranDocFile = CranUtils.ReadFileAsString(path);

		String[] cranDocFileSplits = cranDocFile.split(Constants.CRAN_DELIMITER);
		cranDocFileSplits = Arrays.copyOfRange(cranDocFileSplits, 1, cranDocFileSplits.length);

		return cranDocFileSplits;
	}

}
