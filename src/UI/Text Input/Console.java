import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.event.*;
import javax.swing.text.*;
import java.util.concurrent.CountDownLatch;

public class Console{
   private StringBuilder input = new StringBuilder("");
   private int proChar = 0;
   private JFrame frame;
   private JTextArea text;
   private CountDownLatch pause;

//Overloaded Constructor
   public Console(String title, int x, int y){

      frame = new JFrame(title);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(x, y);

      text = new JTextArea();
      text.setEditable(true);
      JScrollPane scrollPane = new JScrollPane(text);
      frame.add(scrollPane, BorderLayout.CENTER);

      AbstractDocument doc = (AbstractDocument) text.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (offset >= proChar) {
                    super.insertString(fb, offset, string, attr);
                }
            }
            @Override
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                if (offset >= proChar) {
                    super.remove(fb, offset, length);
                }
            }
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (offset >= proChar) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();                    
                    try {

                        int length = doc.getLength() - proChar;
                        input.append(doc.getText(proChar, length));
                        text.append("\n");
                        if(pause != null){
                           pause.countDown();
                        }

                        proChar = text.getDocument().getLength();
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
      frame.setVisible(true);
   }
   public Console(int x, int y){
      this("New Window", x, y);
   }
   public Console(String title){
      this(title, 400, 300);
   }
   public Console(){
      this("New Window", 400, 300);
   }
   
//Methods
   public String getInput(){
      return input.toString();
   }
   public void prompt(String prompt){
      input.delete(0,input.length());
      pause = new CountDownLatch(1);
      prompt += "\n>";
      text.append(prompt);
      proChar = text.getDocument().getLength();
      text.setCaretPosition(text.getDocument().getLength());
      try {
         pause.await();
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }
}