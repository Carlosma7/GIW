package GUI;

import java.awt.Color;
import javax.swing.DefaultListModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import SRI.SearchEngine;
import SRI.Indexer;

/**
 * @author Carlos Morales Aguilera
 * @class GUI
 */
public class GUI extends javax.swing.JFrame {

    public GUI() throws IOException {
        // Init components of GUI
        initComponents();
        // Add a listener to get document from list to read it
        FilesList.addListSelectionListener(new javax.swing.event.ListSelectionListener () {
                                       public void valueChanged (javax.swing.event.ListSelectionEvent evt) {
                                           // Perform action to get document from list
                                           ListResultOnClickAction(evt);
                                       }
                                   }
                                  );
        
        // Initialize search engine and content arraylist
        engine = new SearchEngine();
        indexer = new Indexer();
        docContent = new ArrayList<String>();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ResultsLabel = new javax.swing.JLabel();
        SearchButton = new javax.swing.JButton();
        IndexButton = new javax.swing.JButton();
        TermLabel = new javax.swing.JLabel();
        FolderLabel = new javax.swing.JLabel();
        TermField = new javax.swing.JTextField();
        FolderField = new javax.swing.JTextField();
        DocContentLabel = new javax.swing.JLabel();
        FilesLabel = new javax.swing.JLabel();
        DocumentScrollPanel = new javax.swing.JScrollPane();
        DocumentTextArea = new javax.swing.JTextArea();
        FilesScrollPanel = new javax.swing.JScrollPane();
        lista = new DefaultListModel();
        FilesList = new javax.swing.JList<>();
        BackgroundLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ResultsLabel.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        ResultsLabel.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(ResultsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, 430, 20));

        SearchButton.setBackground(new java.awt.Color(255, 146, 78));
        SearchButton.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 13)); // NOI18N
        SearchButton.setLabel("Search");
        SearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchButtonActionPerformed(evt);
            }
        });
        getContentPane().add(SearchButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 100, -1, -1));

        IndexButton.setBackground(new java.awt.Color(255, 146, 78));
        IndexButton.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 13)); // NOI18N
        IndexButton.setText("Index");
        IndexButton.setMaximumSize(new java.awt.Dimension(65, 23));
        IndexButton.setMinimumSize(new java.awt.Dimension(65, 23));
        IndexButton.setPreferredSize(new java.awt.Dimension(65, 23));
        IndexButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IndexButtonActionPerformed(evt);
            }
        });
        getContentPane().add(IndexButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 60, -1, -1));

        TermLabel.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 1, 18)); // NOI18N
        TermLabel.setForeground(new java.awt.Color(255, 255, 255));
        TermLabel.setText("Term:");
        getContentPane().add(TermLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 50, 20));

        FolderLabel.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 1, 18)); // NOI18N
        FolderLabel.setForeground(new java.awt.Color(255, 255, 255));
        FolderLabel.setText("Folder:");
        getContentPane().add(FolderLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 60, 20));

        TermField.setBackground(new java.awt.Color(255, 146, 78));
        TermField.setFont(new java.awt.Font("NSimSun", 1, 18)); // NOI18N
        TermField.setForeground(new java.awt.Color(0, 51, 51));
        TermField.setToolTipText("Insert here your term");
        TermField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TermFieldActionPerformed(evt);
            }
        });
        getContentPane().add(TermField, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 1050, -1));

        FolderField.setBackground(new java.awt.Color(255, 146, 78));
        FolderField.setFont(new java.awt.Font("NSimSun", 1, 18)); // NOI18N
        FolderField.setForeground(new java.awt.Color(0, 51, 51));
        FolderField.setToolTipText("Insert here your folder");
        FolderField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FolderFieldActionPerformed(evt);
            }
        });
        getContentPane().add(FolderField, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 1050, -1));

        DocContentLabel.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        DocContentLabel.setForeground(new java.awt.Color(255, 255, 255));
        DocContentLabel.setText("Files");
        getContentPane().add(DocContentLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1025, 140, -1, -1));

        FilesLabel.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        FilesLabel.setForeground(new java.awt.Color(255, 255, 255));
        FilesLabel.setText("Document Content");
        getContentPane().add(FilesLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 140, -1, -1));

        DocumentTextArea.setColumns(20);
        DocumentTextArea.setRows(5);
        DocumentTextArea.setPreferredSize(new java.awt.Dimension(166, 94));
        DocumentScrollPanel.setViewportView(DocumentTextArea);

        getContentPane().add(DocumentScrollPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 160, 770, 540));

        FilesList.setModel(lista);
        FilesList.setSelectionBackground(new java.awt.Color(51, 153, 0));
        FilesScrollPanel.setViewportView(FilesList);

        getContentPane().add(FilesScrollPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 160, 260, 540));

        BackgroundLabel.setFont(new java.awt.Font("Tw Cen MT", 0, 13)); // NOI18N
        BackgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/background.jpg"))); // NOI18N
        BackgroundLabel.setMinimumSize(new java.awt.Dimension(1280, 720));
        BackgroundLabel.setPreferredSize(new java.awt.Dimension(1280, 720));
        getContentPane().add(BackgroundLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void TermFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TermFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TermFieldActionPerformed

    // Search button action
    private void SearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchButtonActionPerformed
        // Get text from field for query
        String query = TermField.getText();
        // Clear document content
        DocumentTextArea.removeAll();
        // Define list of results
        ArrayList<Document> results = new ArrayList<Document>();
        try {
            // Search with Search Engine
            results = engine.search("indexes/", "documents/english_stopwords.txt" , query);
        } catch (IOException ex) {
            
        } catch (ParseException ex) {
            
        }
        
        // Check if theres no hits
        if(results.isEmpty()){
            // Clear list
            lista.removeAllElements();
            // Clear document content
            docContent.removeAll(docContent);
            // Indicate there are no results
            ResultsLabel.setForeground(Color.red);
            ResultsLabel.setText("Found 0 documents.");
        }else{
            // If there are any hit
            // Indicate there are results
            ResultsLabel.setForeground(Color.orange);
            if(results.size() == 1){
                ResultsLabel.setText("Found 1 document.");
            }else{
                ResultsLabel.setText("Found " + results.size() + " documents.");
            }
            // Clear list and document content
            if(!lista.isEmpty()){
                lista.removeAllElements();
                docContent.removeAll(docContent);
            }
            // Set documents paths that satisfies the query
            for(Document doc:results){
                String nombre = doc.get("path");
                lista.addElement(nombre);
                docContent.add(doc.get("contents"));
            }  
        }
        
       
    }//GEN-LAST:event_SearchButtonActionPerformed

    private void FolderFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FolderFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FolderFieldActionPerformed

    private void IndexButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IndexButtonActionPerformed
        // Get text from field for query
        String query = FolderField.getText();
        try {
            // Index documents
            indexer.Index("indexes/", "documents/" + query,"documents/english_stopwords.txt");
            // If there are any hit
            // Indicate there are results
            ResultsLabel.setForeground(Color.orange);
            ResultsLabel.setText("Documents indexed.");
            
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex){
            ResultsLabel.setForeground(Color.red);
            ResultsLabel.setText(ex.toString().substring(ex.toString().lastIndexOf("\\") + 1));
        }
    }//GEN-LAST:event_IndexButtonActionPerformed
    
    // Results on click action
    private void ListResultOnClickAction(javax.swing.event.ListSelectionEvent evt) {
        // Get selected index from list of files
        int selectedIndex = FilesList.getSelectedIndex();
        // Clear previous document content
        DocumentTextArea.removeAll();
        if(selectedIndex>=0 && selectedIndex<lista.size()){
            // Write document content
            DocumentTextArea.setText(docContent.get(selectedIndex));
        }
    }
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new GUI().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Define Search Engine and docContent arraylist for representation
    SearchEngine engine;
    Indexer indexer;
    ArrayList<String> docContent;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BackgroundLabel;
    private javax.swing.JLabel DocContentLabel;
    private javax.swing.JScrollPane DocumentScrollPanel;
    private javax.swing.JTextArea DocumentTextArea;
    private javax.swing.JLabel FilesLabel;
    private javax.swing.JList<String> FilesList;
    private DefaultListModel lista;
    private javax.swing.JScrollPane FilesScrollPanel;
    private javax.swing.JTextField FolderField;
    private javax.swing.JLabel FolderLabel;
    private javax.swing.JButton IndexButton;
    private javax.swing.JLabel ResultsLabel;
    private javax.swing.JButton SearchButton;
    private javax.swing.JTextField TermField;
    private javax.swing.JLabel TermLabel;
    // End of variables declaration//GEN-END:variables
}
