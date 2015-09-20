package com.tianchi.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.DirectoryReader;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by houxiang on 15/9/20.
 */
public class LuceneSearch {
    private Directory indexDir = null;
    private DirectoryReader iReader = null;
    private IndexSearcher iSearcher = null;
    private String path = null;
    private Analyzer analyzer = null;

    public LuceneSearch(){}
    public LuceneSearch(String path){
        this.path = path ;
        try {
            indexDir = FSDirectory.open(Paths.get(path));
            iReader = DirectoryReader.open(indexDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        iSearcher = new IndexSearcher(iReader);
        analyzer = new IKAnalyzer(true);
    }

    public int search(String word){
        int num = 0;
        QueryParser parser = new QueryParser("content",analyzer);
        try {
            Query query = parser.parse(word);
            TopDocs tds = iSearcher.search(query, 10000);
            num = tds.totalHits;
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e ){
            e.printStackTrace();
        }finally {
            return num;
        }
    }

    public static void main(String[] args){
        LuceneSearch ls = new LuceneSearch("/Users/houxiang/Desktop/resultoflucene");
        System.out.println(ls.search("黄晓明"));
    }
}
