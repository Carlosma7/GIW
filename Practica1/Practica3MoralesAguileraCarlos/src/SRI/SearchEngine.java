package SRI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;

/**
 * @author Carlos Morales Aguilera
 * @class SearchEngine
 */
public class SearchEngine {
    
    public SearchEngine() {}
    
    /**
     * Method that searchs in index folder for an specific query.
     * @param indexFolder
     * @param stopWordsFile
     * @param queryInput
     * @return arraylist of documents that satysfies the query.
     */
    public ArrayList<Document> search(String indexFolder, String stopWordsFile, String queryInput) throws IOException, ParseException{
    // Define arraylist of documents
    ArrayList<Document> documents = new ArrayList<Document>();
    
    // Define index reader using index folder path
    IndexReader indReader = DirectoryReader.open(FSDirectory.open(Paths.get(indexFolder)));
    // Define index searcher using index reader
    IndexSearcher indSearcher = new IndexSearcher(indReader);
    
    // Read stop words file
    String stopWords = FileUtils.readFileToString(new File(stopWordsFile), "UTF-8");
    // Split stop words in array
    String[] stopWordsSplitted = stopWords.split("\\s+");
    // Define analyzer
    EnglishAnalyzer analyzer = new EnglishAnalyzer(new CharArraySet(Arrays.asList(stopWordsSplitted),true));
    
    // Define query parser using analyzer
    QueryParser parser = new QueryParser("contents" , analyzer); 
    // Parse query input
    Query query = parser.parse(queryInput);
    
    // Search results of the query with a maximum of 500
    TopDocs results = indSearcher.search(query, 500);
    // Get score of documents found
    ScoreDoc[] hits = results.scoreDocs;
    
    // Save total hits made
    int totalHits = Math.toIntExact(results.totalHits.value);
    System.out.println("Found " + totalHits + " documents.");
    
    // Check if there´s a hit or more
    if(totalHits>0){
        // Get hits score
        hits = indSearcher.search(query, totalHits, Sort.RELEVANCE).scoreDocs;
        System.out.println(totalHits);
        System.out.println(Arrays.toString(hits));
        // Add documents to results
        int i = 0;
        // For each hit add it to results
        for(ScoreDoc hit : hits){
            // Get document
            Document doc = indSearcher.doc(hit.doc);
            // Get path of the document
            String path = doc.get("path");
            // Add to results
            documents.add(doc);

            i++;
        }        
    }
    else{
        // If there isnt hits
        System.out.println("There aren´t any documents that fits the query.");
    }
    // Close reader
    indReader.close();
    // Return results
    return documents;
  }
}
