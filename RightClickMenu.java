import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Component;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
/**
 * Handles Right Clicks
 * 
 * @author Matt Dizzine 
 * @version 09.14.2016
 */
public class RightClickMenu extends JPopupMenu{
    private Component comp;
    private ProgramWindow master;
    public RightClickMenu(String type, Component comp, ProgramWindow master){
        this.comp = comp;
        this.master = master;
        setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.BLACK));
        switch(type){
            case "notes":
            case "textInput":
                buildNotesMenu(comp);
                break;
            case "main":
                buildMainMenu();
                break;
        }
    }
    
    public void buildNotesMenu(Component comp){
        JMenuItem cutItem = buildMenuItem("Cut");
        cutItem.addMouseListener(new CutListener());
        JMenuItem copyItem = buildMenuItem("Copy");
        copyItem.addMouseListener(new CopyListener());
        JMenuItem pasteItem = buildMenuItem("Paste");
        pasteItem.addMouseListener(new PasteListener());
        add(cutItem);
        add(copyItem);
        add(pasteItem);
        setLightWeightPopupEnabled(false);
    }
    
    public void buildMainMenu(){
        JMenuItem helpItem = buildMenuItem("Help");
        helpItem.addMouseListener(new HelpListener());
        add(helpItem);
        setLightWeightPopupEnabled(false);
    }
    
    public JMenuItem buildMenuItem(String text){
        JMenuItem opItem = new JMenuItem(text);
        return opItem;
    }
    
    public String getClipboardData(){
        String retStr = "";
        try{
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Clipboard clipboard = toolkit.getSystemClipboard();
            retStr = (String) clipboard.getData(DataFlavor.stringFlavor);
        }catch(UnsupportedFlavorException e){
        }finally{
            return retStr;
        }
    }
    
    public void setClipboardData(String data){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        StringSelection selection = new StringSelection(data);
        clipboard.setContents(selection,selection);
    }
    
    public Component getComp(){
        return comp;
    }
    
    public class CutListener implements MouseListener{
        public void mouseEntered(MouseEvent e){
        }
        
        public void mouseExited(MouseEvent e){
        }
        
        public void mousePressed(MouseEvent e){
        }
        
        public void mouseClicked(MouseEvent e){
        }
        
        public void mouseReleased(MouseEvent e){
            master.setHasChanged(true);
            try{
                JTextField item = (JTextField) comp;
                setClipboardData(item.getSelectedText());
                int start = item.getSelectionStart();
                int end = item.getSelectionEnd();
                String text = item.getText();
                String replacement = item.getText().substring(0,start) + item.getText().substring(end);
                item.setText(replacement);
                item.setCaretPosition(start);
            }catch(Exception exc){
                try{
                    JTextArea item = (JTextArea) comp;
                    setClipboardData(item.getSelectedText());
                    int start = item.getSelectionStart();
                    int end = item.getSelectionEnd();
                    String replacement = item.getText().substring(0,start) + item.getText().substring(end);
                    item.setText(replacement);
                    item.setCaretPosition(start);
                }catch(Exception exce){
                }                
            }       
        }
    }
    
    public class CopyListener implements MouseListener{
        public void mouseEntered(MouseEvent e){
        }
        
        public void mouseExited(MouseEvent e){
        }
        
        public void mousePressed(MouseEvent e){
        }
        
        public void mouseClicked(MouseEvent e){
        }
        
        public void mouseReleased(MouseEvent e){
            try{
                JTextField item = (JTextField) comp;
                setClipboardData(item.getSelectedText());
            }catch(Exception ex){
                try{
                    JTextArea item = (JTextArea) comp;
                    setClipboardData(item.getSelectedText());
                }catch(Exception exc){
                }
            }
        }
    }
    
    public class PasteListener implements MouseListener{
        public void mouseEntered(MouseEvent e){
        }
        
        public void mouseExited(MouseEvent e){
        }
        
        public void mousePressed(MouseEvent e){
        }
        
        public void mouseClicked(MouseEvent e){
        }
        
        public void mouseReleased(MouseEvent e){
            master.setHasChanged(true);
            String data = getClipboardData();
            if(data!=null){
                try{
                    JTextArea item = (JTextArea) getComp();
                    int pos = item.getCaretPosition();
                    String inputString = item.getText(0,pos) + data;
                    int caretPos = inputString.length();
                    inputString = inputString + item.getText(pos,item.getText().length()-pos);
                    item.setText(inputString);
                    item.setCaretPosition(caretPos);
                }catch(Exception ex){
                    try{
                        JTextField item = (JTextField) getComp();
                        int pos = item.getCaretPosition();
                        String inputString = item.getText(0,pos) + data;
                        int caretPos = inputString.length();
                        inputString = inputString + item.getText(pos,item.getText().length()-pos);
                        item.setText(inputString);
                        item.setCaretPosition(caretPos);
                    }catch(Exception exc){
                    }
                }
            }
        }
    }
    
    public class HelpListener implements MouseListener{
        public void mouseEntered(MouseEvent e){
        }
        
        public void mouseExited(MouseEvent e){
        }
        
        public void mousePressed(MouseEvent e){
        }
        
        public void mouseClicked(MouseEvent e){
        }
        
        public void mouseReleased(MouseEvent e){
            if(master.getHasChanged()){
                if(master.shouldLeave()){
                    master.setHasChanged(false);
                    master.clearWindow();
                    master.buildHelpWindow();
                }
            }else{
                master.clearWindow();
                master.buildHelpWindow();
            }
        }
    }
}