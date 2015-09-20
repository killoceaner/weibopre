package com.tianchi.lucene;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.rmi.MarshalledObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by houxiang on 15/9/20.
 */
public class LuceneUtil {
    //private Logger logger = LoggerFactory.getLogger(LuceneUtil.class);
    private Analyzer analyzer = null;
    private List<Map<String,String>> list = null;
    private String indexPath = null;

    public LuceneUtil(List<Map<String,String>> list,String path){
        this.list = list;
        analyzer = new IKAnalyzer(true);
        indexPath = path;
    }


    public IndexWriter createIndexWriter(String indexPath, Analyzer analyzer)
            throws IOException {
        Directory dire = FSDirectory.open(Paths.get(indexPath));
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        IndexWriter iw = new IndexWriter(dire, iwc);
        return iw;
    }

    public void createIndex(){
        try {
            IndexWriter iw = createIndexWriter(indexPath , analyzer);
            Document doc = null;
            int count = 0 ;
            for (Map<String,String> map:list){
                System.out.println(count++);
                doc = new Document();
                doc.add(new StringField("uid",map.get("uid"), Field.Store.YES));
                doc.add(new StringField("uid",map.get("mid"), Field.Store.YES));
                doc.add(new TextField("content",map.get("content"),Field.Store.NO));
                iw.addDocument(doc);
                iw.commit();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        List<Map<String , String >> list = new LinkedList<>();
        String path = "/Users/houxiang/Desktop/resultoflucene";
        File file = new File("/Users/houxiang/Desktop/tianchi/weibo_train_data.txt");
        try {

            LineIterator litr =  FileUtils.lineIterator(file);
            while (litr.hasNext()){
                String[] strs = litr.next().split("\t");
                Map<String,String> map = new HashMap<>();
                map.put("uid",strs[1]);
                map.put("mid",strs[0]);
                map.put("content" ,strs[6]);
                list.add(map);
            }

            LuceneUtil lu = new LuceneUtil(list , path);
            lu.createIndex();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
