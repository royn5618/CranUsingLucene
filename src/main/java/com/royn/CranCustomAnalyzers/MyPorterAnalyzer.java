package com.royn.CranCustomAnalyzers;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.miscellaneous.TrimFilter;
import org.apache.lucene.analysis.ngram.NGramTokenFilter;
import org.apache.lucene.analysis.ngram.NGramTokenizer;
import org.apache.lucene.analysis.standard.StandardFilter;

public class MyPorterAnalyzer extends Analyzer {

	@Override
	protected TokenStreamComponents createComponents(String s) {
		TokenStream filter;
		Tokenizer tokenizer = new NGramTokenizer();
		filter = new NGramTokenFilter(tokenizer);
		filter = new StandardFilter(filter);
		filter = new TrimFilter(filter);
		filter = new LowerCaseFilter(filter);
		filter = new StopFilter(filter, StopAnalyzer.ENGLISH_STOP_WORDS_SET);
		filter = new PorterStemFilter(filter);
		return new TokenStreamComponents(tokenizer, filter);
	}
}
