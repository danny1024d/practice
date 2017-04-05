package eric;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.TextField;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;

public class LE20161229_sublime extends JFrame {
  public static String selectPath = "";
  private JPanel contentPane;
  public static FileOperator1 fileOperator = new FileOperator1();
  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          LE20161229_sublime frame = new LE20161229_sublime();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the frame.
   */
  public LE20161229_sublime() {
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 450, 300);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout(0, 0));
    setContentPane(contentPane);
     
    Label countlabel = new Label("字數: 0     ");
    JTextArea textArea = new JTextArea();
    textArea.getDocument().addDocumentListener(
        new DocumentListener(){
          public void insertUpdate(DocumentEvent e) {fileOperator.setCount(textArea, countlabel);}
          public void removeUpdate(DocumentEvent e) {}
          public void changedUpdate(DocumentEvent e) {}
      });
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setViewportView(textArea);
    contentPane.add(scrollPane, BorderLayout.CENTER);
    
    JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));  
    p.add(countlabel);
    TextField textField = new TextField(20);
    p.add(textField);
    
    Button searchButton = new Button("search");
    searchButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        fileOperator.find(textArea, textField);
      }
    });
    p.add(searchButton);
    
    contentPane.add(p, BorderLayout.SOUTH);
    
    JPanel q = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JMenuBar jbar = new JMenuBar();
    JMenu fileMenu = new JMenu("File"); 
    JMenuItem menuNew = new JMenuItem("New");
    menuNew.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        selectPath = "";
        textArea.setText("");
        countlabel.setText("字數: 0     ");
      }
    });
    fileMenu.add(menuNew);
    
    JMenuItem menuOpen = new JMenuItem("Open");
    menuOpen.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) { 
          File selectedFile = fileChooser.getSelectedFile(); 
          FileOperator1 fileOperator = new FileOperator1();
          fileOperator.read(textArea, selectedFile.getPath());
          selectPath = selectedFile.getPath();
        } 
      }
    });
    fileMenu.add(menuOpen);
    
    JMenuItem menuSave = new JMenuItem("Save");
    menuSave.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(selectPath.equals("")) {
          JFrame parentFrame = new JFrame();
          JFileChooser fileChooser = new JFileChooser();
          fileChooser.setDialogTitle("Save As");
          int userSelection = fileChooser.showSaveDialog(parentFrame);
          if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            fileOperator.save(textArea.getText(), fileToSave.getAbsolutePath());          
          }
        }
        else {
          fileOperator.save(textArea.getText(), selectPath);
        }
      }
    });
    fileMenu.add(menuSave); 
    jbar.add(fileMenu);
    q.add(jbar);
    
    JMenuBar jbar2 = new JMenuBar();
    JMenu editMenu = new JMenu("Edit"); 
    JMenuItem menuCut = new JMenuItem("Cut");
    menuCut.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        fileOperator.cut(textArea);
      }
    });
    editMenu.add(menuCut);
    
    JMenuItem menuCopy = new JMenuItem("Copy");
    menuCopy.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        fileOperator.copy(textArea);
      }
    });
    editMenu.add(menuCopy);
    
    JMenuItem menuPaste = new JMenuItem("Paste");
    menuPaste.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        fileOperator.paste(textArea);
      }
    });
    editMenu.add(menuPaste); 
    jbar2.add(editMenu);
    q.add(jbar2);
    
    Button aboutButton = new Button("About Us");
    aboutButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "我們是xxxx");
      }
    });
    q.add(aboutButton);
    contentPane.add(q, BorderLayout.NORTH);
  }

}

class FileOperator1 {
  int findIndex = 0;
  String copyText;

  public void save(String input, String filePath) {
    try(PrintWriter printWriter = new PrintWriter(filePath)) {
      printWriter.println(input);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }   
  }
  public void read(JTextArea textArea, String filePath) {
    String out = "";
    try(FileInputStream in = new FileInputStream(filePath)) {   
      while(in.available()>0) {
        out += (char)in.read();
      }
    } catch(IOException e) {
      e.printStackTrace();
    }
    textArea.setText(out); 
  }
  public void find(JTextArea textArea, TextField textField) {
    String input = textArea.getText().replace("\n", " ");
    String key = textField.getText();
    int f = input.indexOf(key, findIndex);
    if(f!=-1) {
      findIndex = f + key.length();
      textArea.setSelectionStart(f);
      textArea.setSelectionEnd(findIndex);
      textArea.setSelectedTextColor(Color.BLUE);
    }
    else {
      f = input.indexOf(key);
      if(f !=-1) {
        findIndex = f + key.length();
        textArea.setSelectionStart(f);
        textArea.setSelectionEnd(findIndex);
      }
      else {
        findIndex = 0;
      }
    }
  }
  public void copy(JTextArea textArea) {
    copyText = textArea.getSelectedText();
    if(copyText==null)
      JOptionPane.showMessageDialog(null, "未選擇文字");
  }
  public void cut(JTextArea textArea) {
    copyText = textArea.getSelectedText();
    if(copyText==null)
      JOptionPane.showMessageDialog(null, "未選擇文字");
    else{     
      int cutStart = textArea.getSelectionStart();
      int cutEnd = textArea.getSelectionEnd();
      String tempString = textArea.getText();
      textArea.setText(tempString.substring(0, cutStart)+tempString.substring(cutEnd));
    }
  }
  public void paste(JTextArea textArea) {
    if(copyText!=null) {
    int mousePosition = textArea.getCaretPosition();
    String tempString = textArea.getText();
    textArea.setText(tempString.substring(0, mousePosition)+copyText+tempString.substring(mousePosition));
    }
  }
  public void setCount(JTextArea textArea, Label countlabel) {
    countlabel.setText("字數: "+textArea.getText().length()+" ");
  }
}

