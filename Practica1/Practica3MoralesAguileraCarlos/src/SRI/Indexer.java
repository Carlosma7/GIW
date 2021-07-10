package SRI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * @author Carlos Morales Aguilera
 * @class Indexer
 */

public class Indexer {
    
    public Indexer() {}
    
    /**
     * Method for indexing documents in a SRI.
     * @param indexFolder route of the directory used to store indexes.
     * @param documentsFolder route of the directory that contains documents.
     * @param stopWordsFile route of the stop words file provided.
     */
    public void Index(String indexFolder, String documentsFolder, String stopWordsFile) throws IOException, Exception{
    
    // Get path to documents Folder
    final Path docFolder = Paths.get(documentsFolder);
    
    // Check if the documents folder is readable
    if (!Files.isReadable(docFolder)) {
      System.out.println(docFolder.toAbsolutePath() + " doesn´t exist.");
      throw new Exception(docFolder.toAbsolutePath() + " doesn´t exist.");
    }
    
    // Get start time
    Date start = new Date();
    
    try {
      // Open directory where indexes are going to be stored
      Directory indFolder = FSDirectory.open(Paths.get(indexFolder));
      
      // Read stop words file
      String stopWords = FileUtils.readFileToString(new File(stopWordsFile), "UTF-8");
      // Parse stop words to array to separate them
      String[] stopWordsSplitted = stopWords.split("\\s+");
      // Define analyzer
      EnglishAnalyzer analyzer = new EnglishAnalyzer(new CharArraySet(Arrays.asList(stopWordsSplitted),true));
      
      // Set index writer configuration from index analyzer
      IndexWriterConfig indexWriterConf = new IndexWriterConfig(analyzer);
      indexWriterConf.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
      
      // Define index writer with folder specified to store indexes
      IndexWriter indWriter = new IndexWriter(indFolder, indexWriterConf);
      // Write indexes of folder
      indexFolder(indWriter, docFolder);
      // Close index writer
      indWriter.close();
      
      // Get end time
      Date end = new Date();
      System.out.println("Indexer finished in " + (end.getTime() - start.getTime()) + " ms.");

    } catch (IOException e) {
    }
  }
    
  /**
   * Method for indexing documents given an IndexWriter and folder to store them individually.
   * @param indWriter index writer.
   * @param indFolder folder where indexes will be stored.
   */
  static void indexFolder(final IndexWriter indWriter, Path indFolder) throws IOException {
    // Check if indFolder is a directory
    if (Files.isDirectory(indFolder)) {
      // Walk through the folder to iterate over it
      Files.walkFileTree(indFolder, new SimpleFileVisitor<Path>() {
        @Override
        // Action on a file (visit)
        public FileVisitResult visitFile(Path filePath, BasicFileAttributes fileattrbs) throws IOException {
          try {
            // Index a single document
            indexDocument(indWriter, filePath);
          } catch (IOException ignore) {
            
          }
          // Continue walking through files tree
          return FileVisitResult.CONTINUE;
        }
      });
    }
  }

  /**
   * Method for indexing a single document.
   * @param indWriter index writer.
   * @param filePath file to be indexed.
   */
  static void indexDocument(IndexWriter indWriter, Path filePath) throws IOException {
        // Try to read file
	try (InputStream stream = Files.newInputStream(filePath)) {
                // Define document
		Document doc = new Document();
                // Define path field
		Field pathField = new StringField("path", filePath.toString(), Field.Store.YES);
                // Add path field to document
		doc.add(pathField);
		
                // Read file content
                String content;
                // Define a file buffered reader
                BufferedReader reader = new BufferedReader(new FileReader (filePath.toString()));
                // Define line
                String line = null;
                StringBuilder stringBuilder = new StringBuilder();
                String lineSeparator = System.getProperty("line.separator");
                // Read line by line
                while((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append(lineSeparator);
                }
                // Content to String
                content = stringBuilder.toString();
                // Close reader
                reader.close();
                
                // Add content to document
                doc.add(new TextField("contents", content,Field.Store.YES));
                
                // Check if index writer is creating or updating document
		if (indWriter.getConfig().getOpenMode() == OpenMode.CREATE) {
                        // Add coument
                        System.out.println("Added " + filePath);
			indWriter.addDocument(doc);
		} else {
                        // Update document
			System.out.println("Updated " + filePath);
			indWriter.updateDocument(new Term("path", filePath.toString()), doc);
		}
	}
}
}
