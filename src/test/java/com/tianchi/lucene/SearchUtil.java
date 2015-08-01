package com.tianchi.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;

/**
 * Created by houxiang on 15/7/31.
 */
public class SearchUtil {

        private  Directory directory = null;
        private static IndexReader indexReader = null ;
        public SearchUtil(){
                try {
                       directory = FSDirectory.open(new File("/Users/houxiang/Desktop/resultoflucene")) ;
                } catch (Exception e ){
                        e.printStackTrace();
                }
        }

        private IndexSearcher getSearcher(){
                try {
                        if (indexReader == null){
                                 indexReader = IndexReader.open(directory);
                        }else {
                                IndexReader ir = IndexReader.openIfChanged(indexReader);
                                if (ir != null){
                                        indexReader.close();
                                        indexReader=ir;
                                }
                        }
                        return new IndexSearcher(indexReader);
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return  null ;
        }

        public void searchByTerm(String field , String name , int num ){
                try {
                        IndexSearcher searcher = getSearcher();
                        TermQuery query = new TermQuery(new Term(field , name ));
                        TopDocs tds = searcher.search(query,num);
                        System.out.println(tds.totalHits);
                        for (ScoreDoc sd:tds.scoreDocs){
                                Document doc = searcher.doc(sd.doc);
                                System.out.println(doc.get("name")+"["+doc.get("email")+"]");
                        }
                        searcher.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
      public void searchByRangeTerm(String field , String start, String end , int num ){
                try {
                        IndexSearcher searcher = getSearcher();
                        Query query = new TermRangeQuery(field,start,end,true,true);
                        TopDocs tds = searcher.search(query,num);
                        System.out.println(tds.totalHits);
                        for (ScoreDoc sd:tds.scoreDocs){
                                Document doc = searcher.doc(sd.doc);
                                System.out.println(doc.get("name")+"["+doc.get("email")+"]");
                        }
                        searcher.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

}
