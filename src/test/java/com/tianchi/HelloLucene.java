package com.tianchi;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by houxiang on 15/7/30.
 */
public class HelloLucene {
    public void index() {
        //创建Directory
        //  Directory directory = new RAMDirectory();
        IndexWriter indexWriter = null;
        try {
            Directory directory = FSDirectory.open(new File("/Users/houxiang/Desktop/resultoflucene"));
            //创建indexWriter用来写索引
            IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35));

            //创建document对象
            indexWriter = new IndexWriter(directory, iwc);
            Document document = null;
            File f = new File("/Users/houxiang/Desktop/index");
            for (File file : f.listFiles()) {
                document = new Document();
                String content = FileUtils.readFileToString(file);
                System.out.println(content);
                document.add(new Field("content", new FileReader(file)));
                document.add(new Field("name", file.getName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
                document.add(new Field("path", file.getAbsolutePath(), Field.Store.YES, Field.Index.NOT_ANALYZED));
                indexWriter.addDocument(document);
            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            try {
                indexWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }



    public void search() {
        //1、创建directory 要去哪搜索
        try {
            Directory directory = FSDirectory.open(new File("/Users/houxiang/Desktop/resultoflucene"));
            IndexReader indexReader = IndexReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(indexReader);
            QueryParser parser = new QueryParser(Version.LUCENE_35,"content",new StandardAnalyzer(Version.LUCENE_35));
            Query query = parser.parse("京东");
            TopDocs tds = searcher.search(query,10);
            ScoreDoc[] sds = tds.scoreDocs;

            for(ScoreDoc sd:sds){
                Document d = searcher.doc(sd.doc);
                System.out.println(d.get("name")+d.get("path"));
            }
        }catch (Exception e ){
            e.printStackTrace();
        }


        //2、创建indexreader 去读取索引

        //3、根据indexReader 创建indexSearcher

        //4、创建搜索的query

        //5、根据searcher 获取TopDocs
    }
}