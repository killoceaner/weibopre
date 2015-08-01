package com.tianchi.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by houxiang on 15/7/30.
 */
public class IndexUtil {

    private String[] ids = {"1","2","3","4","5","6"};
    private String[] emails = {"nudt_houxiang@163.com","dfsfad@gmail.com", "dfsfad@gmail.com","dfsfad@gmail.com","sfhou@sina.com","shahou@gmail.com"};
    private String[] contents ={
            "I like play basketball!",
            "I like book and swimming",
            "My name is houxiang and I like football",
            "Myname is cc , and I like opensource",
            "your name is what ",
            "hello can I make friedn with you "
    };
    private String[] names={"Jack","Tom","lucy","cc","dfd","jiji"};
    private Directory directory = null ;
    private static IndexReader indexReader = null ;
    private Map<String, Float> values = null ;


    public IndexUtil(){

        values = new HashMap<String, Float>();
        values.put("163.com",2.0f);
        values.put("like",3.f);

        try{
            directory = FSDirectory.open(new File("/Users/houxiang/Desktop/resultoflucene"));
            indexReader = IndexReader.open(directory,false);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private IndexSearcher getSearch(){
        /**
         * 如何做的索引的时时更新
         */
        try{
            if(indexReader == null){
                indexReader = IndexReader.open(directory,false);
            }else{
                IndexReader ir = IndexReader.openIfChanged(indexReader);
                if(ir != null){
                    indexReader.close();
                    indexReader = ir;
                }
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return new IndexSearcher(indexReader);
    }
    public void Reader(){
        try {
            IndexReader indexReader = IndexReader.open(directory);
            System.out.println("num:"+indexReader.numDocs());
            System.out.println("num:"+indexReader.maxDoc());
            System.out.println("delnum" + indexReader.numDeletedDocs());
        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            try {
                if(indexReader != null ){
                    indexReader.close();
                }
            }catch (Exception e ){
                e.printStackTrace();
            }
        }
    }

    public void delIndex(){
        IndexWriter indexWriter = null;
        try {
            indexWriter = new IndexWriter(directory , new IndexWriterConfig(Version.LUCENE_35 , new StandardAnalyzer(Version.LUCENE_35)));
            indexWriter.deleteDocuments(new Term("id", "1"));
        }catch(Exception e ){
            e.printStackTrace();
        }finally {
            try {
                if(indexWriter != null){
                    indexWriter.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void forceMergeDelete(){
        IndexWriter indexWriter = null ;
        try {
            indexWriter = new IndexWriter(directory , new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));
            indexWriter.forceMergeDeletes();

        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            try {
                if(indexWriter != null){
                    indexWriter.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void unDelete(){
        IndexReader indexReader = null;
        try{
            indexReader = IndexReader.open(directory , false);
            //恢复时必须把indexReader 只读设置了falsel
            indexReader.undeleteAll();
            indexReader.close();
        }catch (Exception e ){
            e.printStackTrace();
        }
    }
    public void index(){
        IndexWriter indexWriter = null;
        try {
            indexWriter = new IndexWriter(directory,new IndexWriterConfig(Version.LUCENE_35,new StandardAnalyzer(Version.LUCENE_35)));
            indexWriter.deleteAll();
            Document doc = null;
            for(int i = 0 ; i < ids.length ; i++){
                doc = new Document();
                doc.add(new Field("id",ids[i], Field.Store.YES, Field.Index.ANALYZED_NO_NORMS));
                doc.add(new Field("email",emails[i], Field.Store.YES,Field.Index.ANALYZED));
                doc.add(new Field("content",contents[i],Field.Store.NO,Field.Index.ANALYZED_NO_NORMS));
                doc.add(new Field("name",names[i],Field.Store.YES,Field.Index.NOT_ANALYZED));
                String value = emails[i].substring(emails[i].indexOf('@')+1, emails[i].length());
                System.out.println(value);
                if(values.containsKey(value)){
                    doc.setBoost(values.get(value));
                }else {
                    doc.setBoost(0.5f);
                }
                indexWriter.addDocument(doc);
                indexWriter.commit();
            }
        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            try {
                if(indexWriter != null){
                    indexWriter.close();
                }
            }catch (Exception e ){
                e.printStackTrace();
            }
        }
    }



    public  void search(){
        try {
            IndexReader indexReader = IndexReader.open(directory);
            IndexSearcher searcher = new  IndexSearcher(indexReader);
            TermQuery query = new TermQuery(new Term("content" , "like"));
            TopDocs tps = searcher.search(query,6);
            for (ScoreDoc sd : tps.scoreDocs){
                Document doc = searcher.doc(sd.doc);
                System.out.println(doc.get("name")+"["+doc.get("email")+"]");
            }
            searcher.close();
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
