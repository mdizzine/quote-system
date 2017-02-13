import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.io.File;
import java.awt.Panel;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Button;
import java.awt.ComponentOrientation;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Collections;
import java.text.DecimalFormat;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Cursor;
import javax.swing.UIManager;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.awt.Component;
import java.util.HashMap;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.UIDefaults;
import java.awt.MouseInfo;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.swing.JPopupMenu;
import java.awt.KeyboardFocusManager;
import java.awt.Point;

/**
 * GUI for RFQResponder
 * 
 * @author Matt Dizzine
 * @version 09.08.2016
 */
public class ProgramWindow extends JFrame{
    private static final Dimension SCREEN_DIM = new Dimension(1002,600);
    private static final Color LIGHT_GRAY = new Color(241,241,241);
    private static final Color MEDIUM_GRAY = new Color(160, 175, 191);
    private static final Color DARK_GRAY = new Color(65,96,127);
    private static final Color LIGHT_BLUE = new Color(14,93,171);
    private static final Color MEDIUM_BLUE = new Color(11,70,128);
    private static final Color DARK_BLUE = new Color(13,42,71);
    private static final Color ERROR_RED = new Color(157,48,33);
    private static final Color SUCCESS_GREEN = new Color(0,100,0);
    private static final Border BIG_UNDERLINE = BorderFactory.createMatteBorder(0,0,3,0,DARK_BLUE);
    private static final Border UNDERLINE = BorderFactory.createMatteBorder(0,0,1,0,DARK_BLUE);
    private static final Border BIG_UNDERLINE_LEFT = BorderFactory.createMatteBorder(0,5,3,0,DARK_BLUE);
    private static final Border UNDERLINE_LEFT = BorderFactory.createMatteBorder(0,5,1,0,DARK_BLUE);
    private static final Border FANCY_HEADER = BorderFactory.createMatteBorder(0,15,0,0,LIGHT_GRAY);
    private static final Border BORDER_LEFT = BorderFactory.createMatteBorder(0,5,0,0,DARK_BLUE);
    private static final Border BTN_BORDER = BorderFactory.createMatteBorder(2,2,2,2,MEDIUM_BLUE);
    private static final Border INPUT_BORDER = BorderFactory.createMatteBorder(1,1,1,1,MEDIUM_GRAY);
    private static final Border INPUT_ERROR_BORDER = BorderFactory.createMatteBorder(1,1,1,1,ERROR_RED);
    private static final Border BTN_CLICK_BORDER = BorderFactory.createMatteBorder(2,2,2,2,MEDIUM_GRAY);
    private static final Border BTN_INITIAL_BORDER = BorderFactory.createMatteBorder(2,2,2,2,MEDIUM_BLUE);
    private static final DecimalFormat PRICE_FORMAT = new DecimalFormat("#0.00");
    private String directoryPath;
    private ArrayList<RequestForQuote> rfqs;
    private Panel mainPanel;
    private int currentRfq;
    private HashMap<String,Component> currentRfqComponents;
    private JTextField searchFirstNameField;
    private JTextField searchLastNameField;
    private JTextField searchStartDateMonth;
    private JTextField searchStartDateDay;
    private JTextField searchStartDateYear;
    private JTextField searchEndDateMonth;
    private JTextField searchEndDateDay;
    private JTextField searchEndDateYear;
    private JTextField firstNameUpdate;
    private JTextField lastNameUpdate;
    private JTextField emailAddressUpdate;
    private JTextField phoneNumberAreaCodeUpdate;
    private JTextField phoneNumberPrefixUpdate;
    private JTextField phoneNumberSuffixUpdate;
    private ArrayList<Integer> searchIndices;
    private boolean hasChanged;
    private boolean fromSearch;
    
    /**
     * Main
     */
    public static void main(String[] args){
        ProgramWindow o = new ProgramWindow();
    }

    /**
     * Constructor
     */
    public ProgramWindow(){
        super("RFQ Responder");
        directoryPath = "C:\\shopifyRFQresponse\\";
        hasChanged = false;
        fromSearch = false;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(SCREEN_DIM);
        setMinimumSize(SCREEN_DIM);
        setLocationByPlatform(true);
        UIManager.put("TabbedPane.selected", Color.WHITE);
        UIManager.put("MenuItem.selectionBackground", Color.WHITE);
        UIManager.put("MenuItem.selectionForeground", DARK_BLUE);
        UIManager.put("Menu.selectionBackground", Color.WHITE);
        UIManager.put("Menu.selectionForeground", DARK_BLUE);
        UIManager.put("MenuBar.selectionBackground", Color.WHITE);
        UIManager.put("MenuBar.selectionForeground", DARK_BLUE);
        UIManager.put("Menu.submenuPopupOffsetX", 1);
        UIManager.put("Menu.submenuPopupOffsetY", -2);
        UIManager.put("Button.select", DARK_GRAY);
        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);
        populateRfqList();
        sortDates();
        addMouseListener(new RightClickListener());
        buildWindow(true);
        setVisible(true);
    }

    /**
     * Builds the opening window
     */
    public void buildWindow(boolean isFirst){
        JLabel label = new JLabel("");
        if(isFirst){
            ImageIcon icon = new ImageIcon("icons//icon.png");
            setIconImage(icon.getImage());
            JMenuBar bar = new JMenuBar();
            bar.setBackground(Color.BLACK);
            bar.setBorder(BorderFactory.createMatteBorder(0,0,2,0,LIGHT_BLUE));
            bar.setForeground(Color.WHITE);
            JMenu fileOpt = new JMenu("File");
            fileOpt.setForeground(Color.WHITE);
            fileOpt.setMnemonic(KeyEvent.VK_F);
            fileOpt.setBorder(null);
            fileOpt.getPopupMenu().setBorder(BorderFactory.createMatteBorder(2,1,2,1,LIGHT_BLUE));
            bar.add(fileOpt);
            JMenuItem update = new JMenuItem("Check For New Quotes");
            update.setBackground(Color.BLACK);
            update.setForeground(Color.WHITE);
            update.setMnemonic(KeyEvent.VK_C);
            update.setBorder(null);
            fileOpt.add(update);
            update.addActionListener(new CheckForNewQuotesListener());
            JMenuItem exit = new JMenuItem("Exit");
            exit.setBackground(Color.BLACK);
            exit.setForeground(Color.WHITE);
            exit.setMnemonic(KeyEvent.VK_X);
            exit.setBorder(null);
            fileOpt.add(exit);            
            exit.addActionListener(new CloseListener());
            JMenu viewOpt = new JMenu("View");
            viewOpt.setForeground(Color.WHITE);
            viewOpt.setMnemonic(KeyEvent.VK_V);
            viewOpt.setBorder(null);
            viewOpt.getPopupMenu().setBorder(BorderFactory.createMatteBorder(2,1,2,1,LIGHT_BLUE));
            bar.add(viewOpt);
            JMenuItem search = new JMenuItem("Search");
            search.setBackground(Color.BLACK);
            search.setForeground(Color.WHITE);
            search.setMnemonic(KeyEvent.VK_S);
            search.setBorder(null);
            viewOpt.add(search);
            search.addActionListener(new SearchListener());
            JMenuItem viewAll = new JMenuItem("View All");
            viewAll.setBackground(Color.BLACK);
            viewAll.setForeground(Color.WHITE);
            viewAll.setMnemonic(KeyEvent.VK_A);
            viewAll.setBorder(null);
            viewOpt.add(viewAll);
            viewAll.addActionListener(new ViewAllListener());
            JMenu helpOpt = new JMenu("Help");
            helpOpt.setForeground(Color.WHITE);
            helpOpt.setMnemonic(KeyEvent.VK_H);
            helpOpt.setBorder(null);
            helpOpt.getPopupMenu().setBorder(BorderFactory.createMatteBorder(2,1,2,1,LIGHT_BLUE));
            helpOpt.getPopupMenu().setLightWeightPopupEnabled(false);
            bar.add(helpOpt);
            JMenu helpList = new JMenu("Help Menu");
            helpList.setOpaque(true);
            helpList.setBackground(Color.BLACK);
            helpList.setForeground(Color.WHITE);
            helpList.setMnemonic(KeyEvent.VK_M);
            helpList.getPopupMenu().setBorder(BorderFactory.createMatteBorder(2,1,2,1,LIGHT_BLUE));
            helpList.setBorder(null);
            JMenuItem helpRespond = new JMenuItem("Responding to quotes");
            helpRespond.setBackground(Color.BLACK);
            helpRespond.setForeground(Color.WHITE);
            helpRespond.setMnemonic(KeyEvent.VK_R);
            helpRespond.setBorder(null);
            helpList.add(helpRespond);
            helpRespond.addActionListener(new HelpRespondListener());
            JMenuItem helpSearch = new JMenuItem("Searching for quotes");
            helpSearch.setBackground(Color.BLACK);
            helpSearch.setForeground(Color.WHITE);
            helpSearch.setMnemonic(KeyEvent.VK_S);
            helpSearch.setBorder(null);
            helpList.add(helpSearch);
            helpSearch.addActionListener(new HelpSearchListener());
            JMenuItem helpCheck = new JMenuItem("Checking for new quotes");
            helpCheck.setBackground(Color.BLACK);
            helpCheck.setForeground(Color.WHITE);
            helpCheck.setMnemonic(KeyEvent.VK_C);
            helpCheck.setBorder(null);
            helpList.add(helpCheck);
            helpCheck.addActionListener(new HelpCheckListener());
            JMenuItem helpEdit = new JMenuItem("Editing customer information");
            helpEdit.setBackground(Color.BLACK);
            helpEdit.setForeground(Color.WHITE);
            helpEdit.setMnemonic(KeyEvent.VK_E);
            helpEdit.setBorder(null);
            helpList.add(helpEdit);
            helpEdit.addActionListener(new HelpEditListener());
            JMenuItem helpKeys = new JMenuItem("Keyboard shortcuts");
            helpKeys.setBackground(Color.BLACK);
            helpKeys.setForeground(Color.WHITE);
            helpKeys.setMnemonic(KeyEvent.VK_K);
            helpKeys.setBorder(null);
            helpList.add(helpKeys);
            helpKeys.addActionListener(new HelpKeysListener());
            //JMenuItem helpError = new JMenuItem("Common errors");
            //helpError.setBackground(Color.BLACK);
            //helpError.setForeground(Color.WHITE);
            //helpError.setMnemonic(KeyEvent.VK_O);
            //helpError.setBorder(null);
            //helpList.add(helpError);
            //helpError.addActionListener(new HelpErrorListener());
            helpOpt.add(helpList);  
            JMenuItem about = new JMenuItem("About");
            about.setBackground(Color.BLACK);
            about.setForeground(Color.WHITE);
            about.setMnemonic(KeyEvent.VK_A);
            about.setBorder(null);
            helpOpt.add(about);
            about.addActionListener(new HelpAboutListener());
            setJMenuBar(bar);
            JPanel header = new JPanel();        
            header.setBackground(DARK_BLUE);
            header.setLayout(new GridLayout(0,4));
            label = new Title("RFQ Responder",28,Font.BOLD,50,getWidth());
            header.add(label);
            label = new Title("Plastic-Craft Products",18,Font.PLAIN,20,getWidth());
            header.add(label);
            Border sides = BorderFactory.createMatteBorder(0,10,0,10,MEDIUM_GRAY);
            Border outerSides = BorderFactory.createMatteBorder(0,10,0,10,Color.BLACK);
            Border headerCompound = BorderFactory.createCompoundBorder(outerSides,sides);
            Border innerSides = BorderFactory.createMatteBorder(0,10,0,10,MEDIUM_BLUE);
            Border headerDoubleCompound = BorderFactory.createCompoundBorder(headerCompound,innerSides);
            header.setBorder(headerDoubleCompound);
            add(header,BorderLayout.NORTH);
            JPanel sidePanel = new JPanel();
            sidePanel.setBackground(MEDIUM_GRAY);
            add(sidePanel,BorderLayout.WEST);
            sidePanel = new JPanel();
            sidePanel.setBackground(MEDIUM_GRAY);
            add(sidePanel,BorderLayout.EAST);
            sidePanel = new JPanel();
            sidePanel.setBackground(DARK_BLUE);
            Border top = BorderFactory.createMatteBorder(8,0,0,0,DARK_BLUE);
            Border bottom = BorderFactory.createMatteBorder(0,0,2,0,LIGHT_BLUE);
            Border secondBottom = BorderFactory.createMatteBorder(0,0,16,0,Color.BLACK);
            Border compound = BorderFactory.createCompoundBorder(outerSides,sides);
            Border doubleCompound = BorderFactory.createCompoundBorder(bottom,compound);
            Border tripleCompound = BorderFactory.createCompoundBorder(secondBottom,doubleCompound);
            Border fourthCompound = BorderFactory.createCompoundBorder(tripleCompound,innerSides);
            Border fifthCompound = BorderFactory.createCompoundBorder(fourthCompound,top);
            sidePanel.setBorder(fifthCompound);
            int sidePanelWidth = sidePanel.getWidth();
            sidePanel.setPreferredSize(new Dimension(sidePanelWidth,28));
            add(sidePanel,BorderLayout.SOUTH);
        }
        mainPanel = getDefaultMainPanel();
        GridLayout layout = new GridLayout(21,5,0,0);
        mainPanel.setLayout(layout);
        mainPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        buildRfqListHeader();
        int rfqIndex = 0;
        for(int i = 0; i < 19; i++){
            try{
                buildRFQ(rfqs.get(i),rfqIndex,i);
                rfqIndex++;
            }catch(IndexOutOfBoundsException e){
                i = 19;
            }
        }
        int fillOut = 19 - rfqIndex;
        for(int i = 0; i < fillOut; i++){
            buildBlankLabels(5,mainPanel,false);
        }
        mainPanel.add(new JLabel("1 - " + rfqIndex + " of " + rfqs.size()));
        buildBlankLabels(3,mainPanel,false);
        if(rfqIndex<rfqs.size()-1){
            JButton next = buildButton("Next Page");
            next.addActionListener(new NextPageListener());
            next.setActionCommand(String.valueOf(rfqIndex));
            next.setMnemonic(KeyEvent.VK_RIGHT);
            mainPanel.add(next);
        }else{
            mainPanel.add(new JLabel(""));
        }
        add(mainPanel,BorderLayout.CENTER);
        if(!isFirst){
            autoSize();
        }
        pack();
    }
    
    /**
     * Build Next Window
     */
    public void buildNext(int lastIndex){
        mainPanel = getDefaultMainPanel();
        mainPanel.setLayout(new GridLayout(21,5,0,0));
        mainPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        buildRfqListHeader();
        int loopCnt = 0;
        for(int i = 0; i < 19; i++){
            try{
                loopCnt = i;
                buildRFQ(rfqs.get(lastIndex),lastIndex,i);
                lastIndex++;
            }catch(IndexOutOfBoundsException e){
                i = 19;
            }
        }
        int startIndex = lastIndex - loopCnt;
        if(lastIndex==rfqs.size()){
            if(lastIndex > 19){
                loopCnt = 19 - loopCnt;
            }else{
                loopCnt = 19 - lastIndex;
            }
        }else{
            loopCnt = 0;
        }
        for(int i = 0; i < loopCnt; i++){
            buildBlankLabels(5,mainPanel,false);
        }
        if(loopCnt!=0){
            startIndex++;
        }
        mainPanel.add(new JLabel((startIndex) + " - " + (lastIndex) + " of " + rfqs.size()));
        if(loopCnt!=0){
            startIndex--;
        }
        buildBlankLabels(2,mainPanel,false);
        if(lastIndex==rfqs.size()){
            mainPanel.add(new JLabel(""));
            JButton previous = buildButton("Previous Page");
            previous.addActionListener(new PreviousPageListener());
            previous.setActionCommand(String.valueOf(startIndex));
            previous.setMnemonic(KeyEvent.VK_LEFT);
            mainPanel.add(previous);
        }else{
            JButton previous = buildButton("Previous Page");
            previous.addActionListener(new PreviousPageListener());
            previous.setActionCommand(String.valueOf(startIndex-1));
            previous.setMnemonic(KeyEvent.VK_LEFT);
            mainPanel.add(previous);
            JButton next = buildButton("Next Page");
            next.addActionListener(new NextPageListener());
            next.setActionCommand(String.valueOf(lastIndex));
            next.setMnemonic(KeyEvent.VK_RIGHT);
            mainPanel.add(next);
        }
        updateMainPanel();
    }
    
    /**
     * Build Previous Window
     */
    public void buildPrevious(int firstIndex){
        firstIndex = firstIndex - 19;
        mainPanel = getDefaultMainPanel();
        mainPanel.setLayout(new GridLayout(21,5,0,0));
        mainPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        buildRfqListHeader();
        int loopCnt = 0;
        for(int i = 0; i < 19; i++){
            loopCnt = i;
            buildRFQ(rfqs.get(firstIndex),firstIndex,i);
            firstIndex++;
        }
        mainPanel.add(new JLabel((firstIndex-18) + " - " + firstIndex + " of " + rfqs.size()));
        buildBlankLabels(2,mainPanel,false);
        if(firstIndex-19==0){
            mainPanel.add(new JLabel(""));
        }else{
            JButton previous = buildButton("Previous Page");
            previous.addActionListener(new PreviousPageListener());
            previous.setActionCommand(String.valueOf(firstIndex-19));
            previous.setMnemonic(KeyEvent.VK_LEFT);
            mainPanel.add(previous);
        }
        JButton next = buildButton("Next Page");
        next.addActionListener(new NextPageListener());
        next.setActionCommand(String.valueOf(firstIndex));
        next.setMnemonic(KeyEvent.VK_RIGHT);
        mainPanel.add(next);
        updateMainPanel();
    }
    
    /**
     * Build rfq list from index
     */
    public void buildListFromIndex(int index){
        index = index/19;
        index = index * 19;
        mainPanel = getDefaultMainPanel();
        mainPanel.setLayout(new GridLayout(21,5,0,0));
        mainPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        buildRfqListHeader();
        int loopCnt = 0;
        boolean full = true;
        for(int i = 0; i < 19; i++){
            try{
                loopCnt++;
                buildRFQ(rfqs.get(index),index,i);
                index++;
            }catch(IndexOutOfBoundsException e){
                full = false;
                i = 19;
            }
        }
        int startIndex = index - loopCnt;
        loopCnt = 20 - loopCnt;      
        if(!full){
            for(int i = 0; i < loopCnt; i++){
                buildBlankLabels(5,mainPanel,false);
            }
            startIndex++;
        }
        mainPanel.add(new JLabel((startIndex+1) + " - " + index + " of " + rfqs.size()));
        buildBlankLabels(2,mainPanel,false);
        if(startIndex!=0){
            if(index!=rfqs.size()){
                JButton previous = buildButton("Previous Page");
                previous.addActionListener(new PreviousPageListener());
                previous.setActionCommand(String.valueOf(startIndex));
                previous.setMnemonic(KeyEvent.VK_LEFT);
                mainPanel.add(previous);
                JButton next = buildButton("Next Page");
                next.addActionListener(new NextPageListener());
                next.setActionCommand(String.valueOf(index));
                next.setMnemonic(KeyEvent.VK_RIGHT);
                mainPanel.add(next);
            }else{
                mainPanel.add(new JLabel(""));
                JButton previous = buildButton("Previous Page");
                previous.addActionListener(new PreviousPageListener());
                previous.setActionCommand(String.valueOf(startIndex));
                previous.setMnemonic(KeyEvent.VK_LEFT);
                mainPanel.add(previous);
            }
        }else{
            mainPanel.add(new JLabel(""));
            if(index!=rfqs.size()){
                JButton next = buildButton("Next Page");
                next.addActionListener(new NextPageListener());
                next.setActionCommand(String.valueOf(index));
                next.setMnemonic(KeyEvent.VK_RIGHT);
                mainPanel.add(next);
            }else{
                mainPanel.add(new JLabel(""));
            }
        }
        updateMainPanel();
    }
    
    /**
     * Build search from index
     * @param int index
     */
    public void buildSearchListFromIndex(int index){
        index = index/19;
        index = index * 19;
        mainPanel = getDefaultMainPanel();
        mainPanel.setLayout(new GridLayout(21,5,0,0));
        mainPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        buildRfqListHeader();
        int loopCnt = 0;
        boolean full = true;
        for(int i = 0; i < 19; i++){
            try{
                loopCnt++;
                buildRFQ(rfqs.get(searchIndices.get(index)),index,i);
                index++;
            }catch(IndexOutOfBoundsException e){
                full = false;
                i = 19;
            }
        }
        int startIndex = index - loopCnt;
        loopCnt = 20 - loopCnt;      
        if(!full){
            for(int i = 0; i < loopCnt; i++){
                buildBlankLabels(5,mainPanel,false);
            }
            startIndex++;
        }
        mainPanel.add(new JLabel((startIndex+1) + " - " + index + " of " + searchIndices.size()));
        buildBlankLabels(2,mainPanel,false);
        if(startIndex!=0){
            if(index!=searchIndices.size()){
                JButton previous = buildButton("Previous Page");
                previous.addActionListener(new SearchPreviousPageListener());
                previous.setActionCommand(String.valueOf(startIndex));
                previous.setMnemonic(KeyEvent.VK_LEFT);
                mainPanel.add(previous);
                JButton next = buildButton("Next Page");
                next.addActionListener(new SearchNextPageListener());
                next.setActionCommand(String.valueOf(index));
                next.setMnemonic(KeyEvent.VK_RIGHT);
                mainPanel.add(next);
            }else{
                mainPanel.add(new JLabel(""));
                JButton previous = buildButton("Previous Page");
                previous.addActionListener(new SearchPreviousPageListener());
                previous.setActionCommand(String.valueOf(startIndex));
                previous.setMnemonic(KeyEvent.VK_LEFT);
                mainPanel.add(previous);
            }
        }else{
            mainPanel.add(new JLabel(""));
            if(index!=searchIndices.size()){
                JButton next = buildButton("Next Page");
                next.addActionListener(new SearchNextPageListener());
                next.setActionCommand(String.valueOf(index));
                next.setMnemonic(KeyEvent.VK_RIGHT);
                mainPanel.add(next);
            }else{
                mainPanel.add(new JLabel(""));
            }
        }
        updateMainPanel();
    }
    
    /**
     * default main Panel
     * @return Panel defMainPanel
     */
    public Panel getDefaultMainPanel(){
        Panel defMainPanel = new Panel();
        defMainPanel.setBackground(Color.WHITE);
        defMainPanel.addMouseListener(new RightClickListener());
        return defMainPanel;
    }
    
    /**
     * Update main panel
     */
    public void updateMainPanel(){
        add(mainPanel,BorderLayout.CENTER);
        autoSize();
        pack();
    }
    
    /**
     * Build rfq list header
     */
    public void buildRfqListHeader(){
        mainPanel.add(buildLabel("ID",SwingConstants.CENTER,UNDERLINE,null));
        mainPanel.add(buildLabel("Name",SwingConstants.CENTER,UNDERLINE,null));
        mainPanel.add(buildLabel("Date",SwingConstants.CENTER,UNDERLINE,null));
        mainPanel.add(buildLabel("Status",SwingConstants.CENTER,UNDERLINE,null));
        mainPanel.add(buildLabel("",SwingConstants.CENTER,UNDERLINE,null));
    }
    
    /**
     * Opens Initial files
     */
    public void populateRfqList(){
        try{
            rfqs = new ArrayList<RequestForQuote>();
            ParseXml parser = new ParseXml();
            File folder = new File(directoryPath);
            for(File fileEntry: folder.listFiles()){
                if(!fileEntry.isDirectory()){
                    rfqs.add(parser.parseFile(fileEntry.getAbsolutePath()));
                }
            }
        }catch(NullPointerException e){
            writeError("Error populating quotes due to bad address","Error populating quotes",101);
        }
    }

    /**
     * Sorts rfqs by date
     */
    public void sortDates(){
        Collections.sort(rfqs, new DateSorter());
    }

    /**
     * Build quote window
     * @param int rfqsIndex
     */
    public void buildQuote(int rfqsIndex){
        hasChanged = false;
        currentRfqComponents = new HashMap<String, Component>();        
        RequestForQuote selected = rfqs.get(rfqsIndex);
        currentRfq = rfqsIndex;
        mainPanel = getDefaultMainPanel();
        JTextField input = new JTextField();
        JLabel label = new JLabel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraint = new GridBagConstraints();
        JButton backBtn = buildButton("Back");
        backBtn.addActionListener(new BackBtnListener());
        backBtn.setMnemonic(KeyEvent.VK_B);
        backBtn.setDisplayedMnemonicIndex(-1);
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.weightx = 1.0;
        constraint.weighty = 1.0;
        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.gridwidth = 2;
        constraint.gridheight = 1;
        constraint.insets = new Insets(0,10,0,0);
        mainPanel.add(backBtn, constraint);
        constraint.gridwidth = 1;
        constraint.gridx = 5;
        constraint.insets = new Insets(0,0,0,0);
        mainPanel.add(new JLabel(selected.getSubmitDateString()),constraint);
        constraint.insets = new Insets(0,10,0,0);
        constraint.gridwidth = 2;
        constraint.gridx = 0;
        constraint.gridy = 1;
        constraint.gridwidth = 2;
        mainPanel.add(new JLabel("Customer Information"),constraint);
        constraint.gridx = 2;
        constraint.gridwidth = 1;
        JButton editCustomerBtn = buildButton("Edit Information");        
        editCustomerBtn.addActionListener(new EditCustomerListener());
        editCustomerBtn.setMnemonic(KeyEvent.VK_E);
        editCustomerBtn.setDisplayedMnemonicIndex(-1);
        mainPanel.add(editCustomerBtn, constraint);
        constraint.gridx = 0;
        constraint.gridy = 2;
        constraint.weighty = 0;
        constraint.gridwidth = 2;
        mainPanel.add(new JLabel(selected.getFirstName() + " " + selected.getLastName()),constraint);
        constraint.gridx = 0;
        constraint.gridy = 3;
        mainPanel.add(new JLabel(selected.getEmailAddress()),constraint);
        constraint.gridx = 0;
        constraint.gridy = 4;
        mainPanel.add(new JLabel(selected.getPhoneNumber()),constraint);
        constraint.gridx = 0;
        constraint.gridy = 5;
        constraint.weighty = 1;
        mainPanel.add(new JLabel("Items"),constraint);
        constraint.gridx = 0;
        constraint.gridy = 6;
        constraint.gridwidth = 1;
        mainPanel.add(new JLabel("Qty"),constraint);        
        constraint.gridx = 1;
        constraint.gridwidth = 1;
        constraint.insets = new Insets(0,0,0,0);
        mainPanel.add(new JLabel("SKU"),constraint);
        constraint.gridx = 2;
        constraint.gridwidth = 3;
        mainPanel.add(new JLabel("Description"),constraint);
        constraint.gridx = 5;
        constraint.gridwidth = 1;
        mainPanel.add(new JLabel("Price"),constraint);        
        int rowCnt = 7;
        for(Item item : selected.getItemList()){
            constraint.insets = new Insets(0,30,0,0);
            constraint.gridx = 0;
            constraint.gridy = rowCnt;
            mainPanel.add(new JLabel(String.valueOf(item.getQuantity())),constraint);
            constraint.insets = new Insets(0,0,0,0);
            constraint.gridx = 1;
            mainPanel.add(new JLabel(item.getSku()),constraint);
            constraint.gridx = 2;
            constraint.gridwidth = 3;
            mainPanel.add(new JLabel(item.getDescription()),constraint);
            constraint.gridx = 5;
            constraint.gridwidth = 1;
            constraint.insets = new Insets(0,0,0,10);
            if(item.getPrice()!=0){
                input = new JTextField(String.valueOf(item.getPrice()));
            }else{
                input = new JTextField();
            }
            input.setBorder(INPUT_BORDER);
            input.addKeyListener(new PriceInputKeyListener());
            input.addMouseListener(new InputFieldRightClickListener());
            input.setName("itemPrice" + String.valueOf(rowCnt-7));
            currentRfqComponents.put(input.getName(), input);
            mainPanel.add(input,constraint);
            rowCnt++;
        }
        constraint.insets = new Insets(0,0,0,0);
        constraint.gridx = 3;
        constraint.gridy = rowCnt;
        constraint.gridwidth = 2;
        label = new JLabel("Turnaround Time");
        label.setName("turnaroundTimeLabel");
        currentRfqComponents.put(label.getName(),label);
        mainPanel.add(label,constraint);
        constraint.gridx = 5;
        constraint.gridwidth = 1;
        constraint.insets = new Insets(0,0,0,10);
        input = new JTextField(selected.getTurnAroundTime());
        input.setBorder(INPUT_BORDER);
        input.addKeyListener(new FieldEmptyListener());
        input.addMouseListener(new InputFieldRightClickListener());
        input.setName("turnaroundTime");
        currentRfqComponents.put(input.getName(),input);
        mainPanel.add(input,constraint);
        rowCnt++;
        constraint.gridx = 3;
        constraint.gridy = rowCnt;
        constraint.gridwidth = 2;
        constraint.insets = new Insets(0,0,0,0);
        label = new JLabel("Quote Expiration Date");
        label.setName("expirationDateLabel");
        currentRfqComponents.put(label.getName(),label);
        mainPanel.add(label,constraint);
        constraint.gridx = 5;
        constraint.gridy = rowCnt;
        constraint.gridwidth = 1;
        constraint.insets = new Insets(0,0,0,5);
        Panel datePanel = new Panel();
        datePanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        input = new JTextField(2);
        input.setBorder(INPUT_BORDER);
        input.addKeyListener(new MonthInputKeyListener());
        input.addMouseListener(new InputFieldRightClickListener());
        input.setName("expirationMonth");
        currentRfqComponents.put(input.getName(),input);
        datePanel.add(input);
        datePanel.add(new JLabel("/"));
        input = new JTextField(2);
        input.addKeyListener(new DayInputKeyListener());
        input.addMouseListener(new InputFieldRightClickListener());
        input.setBorder(INPUT_BORDER);
        input.setName("expirationDay");
        currentRfqComponents.put(input.getName(),input);
        datePanel.add(input);
        datePanel.add(new JLabel("/"));
        input = new JTextField(4);
        input.setBorder(INPUT_BORDER);
        input.addKeyListener(new YearInputKeyListener());
        input.addMouseListener(new InputFieldRightClickListener());
        input.setName("expirationYear");
        currentRfqComponents.put(input.getName(),input);
        datePanel.add(input);
        datePanel.setName("expirationDatePanel");
        mainPanel.add(datePanel,constraint);
        rowCnt++;
        constraint.gridx = 3;
        constraint.gridy = rowCnt;
        constraint.gridwidth = 2;
        constraint.insets = new Insets(0,0,0,0);
        label = new JLabel("Sales Person");
        label.setName("salesPersonLabel");
        currentRfqComponents.put(label.getName(),label);
        mainPanel.add(label,constraint);
        constraint.gridx = 5;
        constraint.gridwidth = 1;
        constraint.insets = new Insets(0,0,0,10);
        input = new JTextField(selected.getSalesPerson());
        input.addMouseListener(new InputFieldRightClickListener());
        input.setBorder(INPUT_BORDER);
        input.setName("salesPerson");
        input.addKeyListener(new FieldEmptyListener());
        currentRfqComponents.put(input.getName(),input);
        mainPanel.add(input, constraint);
        rowCnt++;
        constraint.gridx = 1;
        constraint.gridy = rowCnt;
        constraint.gridwidth = 1;
        constraint.insets = new Insets(0,0,0,0);
        mainPanel.add(new JLabel("Notes"),constraint);
        rowCnt++;
        constraint.gridx = 1;
        constraint.gridy = rowCnt;
        constraint.gridwidth = 4;
        constraint.gridheight = 3;
        JTextArea textInput = new JTextArea(selected.getNotes(),3,50);
        textInput.setBorder(null);
        textInput.setRows(3);        
        textInput.setLineWrap(true);
        textInput.setName("notes");
        textInput.addMouseListener(new NotesListener());
        textInput.addKeyListener(new NotesChangedListener());
        JScrollPane notesScroll = new JScrollPane(textInput);
        notesScroll.setPreferredSize(new Dimension(750,55));
        notesScroll.setBorder(INPUT_BORDER);
        currentRfqComponents.put(textInput.getName(),textInput);
        notesScroll.getVerticalScrollBar().setUI(new BasicScrollBarUI(){                        
            protected JButton createDecreaseButton(int orientation){
                JButton returnBtn = new JButton(getAppropriateIcon(orientation));
                returnBtn.setBackground(DARK_BLUE);
                returnBtn.setFocusable(false);
                return returnBtn;
            }
            
            protected JButton createIncreaseButton(int orientation){
                JButton returnBtn = new JButton(getAppropriateIcon(orientation));
                returnBtn.setBackground(DARK_BLUE);
                returnBtn.setFocusable(false);
                return returnBtn;
            }
            
            protected void configureScrollBarColors(){
                this.thumbColor = DARK_BLUE;
                this.thumbHighlightColor = MEDIUM_BLUE;
            }
            
            private ImageIcon getAppropriateIcon(int orientation){
                ImageIcon retIcon = null;
                switch(orientation){
                    case SwingConstants.SOUTH: 
                        retIcon = new ImageIcon("icons//arrow_down.png"); 
                        break;
                    case SwingConstants.NORTH: 
                        retIcon =  new ImageIcon("icons//arrow_up.png"); 
                        break;
                }
                return retIcon;
            }
        });
        mainPanel.add(notesScroll, constraint);
        rowCnt = rowCnt + 3;
        constraint.gridx = 5;
        constraint.gridy = rowCnt;
        constraint.gridwidth = 1;
        constraint.gridheight = 1;
        constraint.ipady = 0;
        constraint.insets = new Insets(0,0,0,10);
        JButton sendBtn = buildButton("Send");
        sendBtn.addActionListener(new SendBtnListener());
        sendBtn.setMnemonic(KeyEvent.VK_S);
        sendBtn.setDisplayedMnemonicIndex(-1);
        mainPanel.add(sendBtn,constraint);
        updateMainPanel();
    }

    /**
     * Build quote read-only window
     * @param int rfqIndex
     */
    public void buildReadOnlyQuote(int rfqIndex){
        RequestForQuote selected = rfqs.get(rfqIndex);
        currentRfq = rfqIndex;
        mainPanel = getDefaultMainPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.weightx = 1;
        constraint.weighty = 1;
        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.gridwidth = 2;
        constraint.gridheight = 1;
        constraint.insets = new Insets(0,10,0,0);
        JButton backBtn = buildButton("Back");
        backBtn.addActionListener(new BackBtnListener());
        backBtn.setMnemonic(KeyEvent.VK_B);
        backBtn.setDisplayedMnemonicIndex(-1);
        mainPanel.add(backBtn,constraint);
        constraint.gridy = 0;
        constraint.gridx = 15;
        constraint.gridwidth = 1;
        mainPanel.add(new JLabel(selected.getSubmitDateString()),constraint);
        constraint.gridy = 1;
        constraint.gridx = 0;
        constraint.gridwidth = 8;
        mainPanel.add(new JLabel("Customer Information"),constraint);
        constraint.weighty = 0;
        constraint.gridy = 2;
        constraint.gridwidth = 5;
        mainPanel.add(new JLabel(selected.getFirstName() + " " + selected.getLastName()),constraint);
        constraint.gridy = 3;
        mainPanel.add(new JLabel(selected.getEmailAddress()),constraint);
        constraint.gridy = 4;
        mainPanel.add(new JLabel(selected.getPhoneNumber()),constraint);
        constraint.weighty = 1;
        constraint.gridy = 5;
        constraint.gridwidth = 3;
        mainPanel.add(new JLabel("Items"),constraint);
        constraint.gridy = 6;
        constraint.gridwidth = 1;
        mainPanel.add(new JLabel("Qty"),constraint);
        constraint.gridx = 1;
        constraint.insets = new Insets(0,0,0,0);
        mainPanel.add(new JLabel("SKU"),constraint);
        constraint.gridx = 3;
        constraint.gridwidth = 10;
        mainPanel.add(new JLabel("Description"),constraint);
        constraint.gridx = 12;
        constraint.gridwidth = 3;
        mainPanel.add(new JLabel("Price"),constraint);
        int rowCnt = 7;
        for(Item item : selected.getItemList()){
            constraint.insets = new Insets(0,10,0,0);
            constraint.gridx = 0;
            constraint.gridwidth = 1;
            constraint.gridy = rowCnt;
            mainPanel.add(new JLabel(String.valueOf(item.getQuantity())),constraint);
            constraint.insets = new Insets(0,0,0,0);
            constraint.gridx = 1;
            mainPanel.add(new JLabel(String.valueOf(item.getSku())),constraint);
            constraint.gridx = 2;
            constraint.gridwidth = 10;
            mainPanel.add(new JLabel(item.getDescription()),constraint);
            constraint.gridx = 12;
            constraint.gridwidth = 3;
            mainPanel.add(new JLabel(String.valueOf(PRICE_FORMAT.format(item.getPrice()))),constraint);
            rowCnt++;
        }
        constraint.gridx = 5;
        constraint.gridy = rowCnt;
        constraint.gridwidth = 4;
        mainPanel.add(new JLabel("Turnaround Time"),constraint);
        constraint.gridx = 9;
        constraint.gridwidth = 6;
        mainPanel.add(new JLabel(selected.getTurnAroundTime()),constraint);
        rowCnt++;
        constraint.gridx = 5;
        constraint.gridy = rowCnt;
        constraint.gridwidth = 4;
        mainPanel.add(new JLabel("Expiration Date"),constraint);
        constraint.gridx = 9;
        constraint.gridwidth = 6;
        mainPanel.add(new JLabel(selected.getExpirationDateString()),constraint);
        rowCnt++;
        constraint.gridx = 5;
        constraint.gridy = rowCnt;
        constraint.gridwidth = 4;
        mainPanel.add(new JLabel("Completion Date"),constraint);
        constraint.gridx = 9;
        constraint.gridwidth = 6;
        mainPanel.add(new JLabel(selected.getCompleteDateString()),constraint);
        rowCnt++;
        constraint.gridx = 5;
        constraint.gridy = rowCnt;
        constraint.gridwidth = 4;
        mainPanel.add(new JLabel("Sales Person"),constraint);
        constraint.gridx = 9;
        constraint.gridwidth = 6;
        mainPanel.add(new JLabel(selected.getSalesPerson()),constraint);
        rowCnt++;
        constraint.gridx = 0;
        constraint.gridy = rowCnt;
        constraint.gridwidth = 1;
        constraint.insets = new Insets(0,10,0,0);
        mainPanel.add(new JLabel("Notes"),constraint);
        rowCnt++;
        constraint.gridx = 1;
        constraint.gridy = rowCnt;
        constraint.gridwidth = 13;
        constraint.insets = new Insets(0,0,0,0);
        String notesData = selected.getNotes();
        String[] splitter = notesData.split(" ");
        int words = splitter.length;
        String opString = "";
        String holder = "";
        for(int i = 0; i < words; i++){
            holder = opString;
            opString += splitter[i];
            if(opString.length()>129){
                opString = holder;
                i--;
                mainPanel.add(new JLabel(opString),constraint);
                opString = "";
                rowCnt++;
                constraint.gridy = rowCnt;
            }else{
                if((opString.length()+1)>129){
                    mainPanel.add(new JLabel(opString),constraint);
                    opString = "";
                    rowCnt++;
                    constraint.gridy = rowCnt;
                }else{
                    opString += " ";
                    if((i+1)==words){
                        mainPanel.add(new JLabel(opString),constraint);
                    }
                }
            }
        }        
        updateMainPanel();    
    }
    
    /**
     * Build Search Page
     */
    public void buildSearchWindow(){
        mainPanel = getDefaultMainPanel();
        mainPanel.setLayout(new GridLayout(7,2));
        Panel panel = new Panel();
        panel.addMouseListener(new RightClickListener());
        panel.setLayout(new GridLayout(1,0));
        JLabel label = new JLabel("Search");
        label.setBorder(new EmptyBorder(0,10,0,0));
        panel.add(label);        
        mainPanel.add(panel);
        mainPanel.add(new JLabel(""));
        panel = new Panel();
        panel.addMouseListener(new RightClickListener());
        panel.setLayout(new GridLayout(2,0));
        label = new JLabel("First Name");
        label.setBorder(new EmptyBorder(0,10,0,0));
        panel.add(label);
        searchFirstNameField = new JTextField();
        searchFirstNameField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5,20,5,20),INPUT_BORDER));
        searchFirstNameField.addMouseListener(new InputFieldRightClickListener());
        panel.add(searchFirstNameField);
        mainPanel.add(panel);
        mainPanel.add(new JLabel(""));
        panel = new Panel();
        panel.addMouseListener(new RightClickListener());
        panel.setLayout(new GridLayout(2,0));
        label = new JLabel("Last Name");
        label.setBorder(new EmptyBorder(0,10,0,0));
        panel.add(label);
        searchLastNameField = new JTextField();
        searchLastNameField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5,20,5,20),INPUT_BORDER));
        searchLastNameField.addMouseListener(new InputFieldRightClickListener());
        panel.add(searchLastNameField);
        mainPanel.add(panel);
        mainPanel.add(new JLabel(""));
        panel = new Panel();
        panel.addMouseListener(new RightClickListener());
        panel.setLayout(new GridLayout(2,0));
        label = new JLabel("Start Submit Date");
        label.setBorder(new EmptyBorder(0,10,0,0));
        panel.add(label);
        Panel startDatePanel = new Panel();
        startDatePanel.addMouseListener(new RightClickListener());
        searchStartDateMonth = new JTextField(2);
        searchStartDateMonth.addMouseListener(new InputFieldRightClickListener());
        searchStartDateMonth.setBorder(INPUT_BORDER);
        startDatePanel.add(searchStartDateMonth);
        startDatePanel.add(new JLabel("/"));
        searchStartDateDay = new JTextField(2);
        searchStartDateDay.addMouseListener(new InputFieldRightClickListener());
        searchStartDateDay.setBorder(INPUT_BORDER);
        startDatePanel.add(searchStartDateDay);
        startDatePanel.add(new JLabel("/"));
        searchStartDateYear = new JTextField(4);
        searchStartDateYear.addMouseListener(new InputFieldRightClickListener());
        searchStartDateYear.setBorder(INPUT_BORDER);
        startDatePanel.add(searchStartDateYear);
        panel.add(startDatePanel);
        mainPanel.add(panel);
        mainPanel.add(new JLabel(""));
        panel = new Panel();
        panel.addMouseListener(new RightClickListener());
        panel.setLayout(new GridLayout(2,0));
        label = new JLabel("End Submit Date");
        label.setBorder(new EmptyBorder(0,10,0,0));
        panel.add(label);
        Panel endDatePanel = new Panel();
        endDatePanel.addMouseListener(new RightClickListener());
        searchEndDateMonth = new JTextField(2);
        searchEndDateMonth.addMouseListener(new InputFieldRightClickListener());
        searchEndDateMonth.setBorder(INPUT_BORDER);
        endDatePanel.add(searchEndDateMonth);
        endDatePanel.add(new JLabel("/"));
        searchEndDateDay = new JTextField(2);
        searchEndDateDay.addMouseListener(new InputFieldRightClickListener());
        searchEndDateDay.setBorder(INPUT_BORDER);
        endDatePanel.add(searchEndDateDay);
        endDatePanel.add(new JLabel("/"));
        searchEndDateYear = new JTextField(4);
        searchEndDateYear.addMouseListener(new InputFieldRightClickListener());
        searchEndDateYear.setBorder(INPUT_BORDER);
        endDatePanel.add(searchEndDateYear);
        panel.add(endDatePanel);
        mainPanel.add(panel);
        mainPanel.add(new JLabel(""));
        panel = new Panel();
        panel.addMouseListener(new RightClickListener());
        panel.setLayout(new GridLayout(1,0));
        label = new JLabel("Leave fields blank for a broader search.");
        label.setBorder(new EmptyBorder(0,10,0,0));
        panel.add(label);
        mainPanel.add(panel);
        buildBlankLabels(2,mainPanel,false);
        panel = new Panel();
        panel.setLayout(new GridLayout(3,5));
        panel.addMouseListener(new RightClickListener());
        JButton btn = buildButton("Search");
        btn.addActionListener(new SearchBtnListener());
        btn.setMnemonic(KeyEvent.VK_S);
        btn.setDisplayedMnemonicIndex(-1);
        buildBlankLabels(8,panel,false);
        panel.add(btn);
        buildBlankLabels(6,panel,false);
        mainPanel.add(panel);
        updateMainPanel();
    }
    
    /**
     * Clear Window
     */
    public void clearWindow(){
        remove(mainPanel);
    }

    /**
     * Set label
     * @param String data
     * @param Border border
     * return JLabel
     */
    public JLabel buildLabel(String data, int position, Border border, Color color){
        JLabel returnLabel = new JLabel(data, position);
        returnLabel.setBorder(border);
        if(color!=null){
            returnLabel.setForeground(color);
        }
        return returnLabel;        
    }
    
    /**
     * Build label as uneditable text field
     */
    public JTextField buildLabelAsTextField(String data, int position, Border border, Color color){
        JTextField returnLabel = new JTextField(data);
        returnLabel.setHorizontalAlignment(position);
        returnLabel.setEditable(false);
        returnLabel.setBorder(border);
        if(color!=null){
            returnLabel.setForeground(color);
        }
        returnLabel.setBackground(Color.WHITE);
        return returnLabel;
    }
    
    /**
     * Set button
     */
    public JButton buildButton(String data){
        JButton returnButton = new JButton(data);
        returnButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        returnButton.setBackground(LIGHT_BLUE);
        returnButton.setForeground(Color.WHITE);
        returnButton.setBorder(BTN_BORDER);
        returnButton.addMouseListener(new BtnMouseListener());
        returnButton.setFocusPainted(false);
        return returnButton;
    }

    /**
     * Search quotes by name and dates
     */
    public void superSearchQuotes(String firstName, String lastName, Calendar startDate, Calendar endDate){
        clearWindow();
        mainPanel = getDefaultMainPanel();
        boolean isFound = false;
        boolean hasStartDate, hasEndDate;
        if(startDate!=null){
            hasStartDate = true;
        }else{
            hasStartDate = false;
        }
        if(endDate!=null){
            hasEndDate = true;
        }else{
            hasEndDate = false;
        }
        int rfqIndex = 0;
        int rfqCnt = 0;
        long startDateLong = 0;
        long endDateLong = 0;
        if(hasStartDate){
            startDateLong = startDate.getTimeInMillis();
        }
        if(hasEndDate){
            endDateLong = endDate.getTimeInMillis();
        }
        searchIndices = new ArrayList<Integer>();
        int cnt = 0;
        for(RequestForQuote rfq : rfqs){
            if(firstName!=""){
                if(rfq.getFirstName().length()>=firstName.length() && rfq.getFirstName().substring(0,firstName.length()).equalsIgnoreCase(firstName)){
                    if(lastName!=""){
                        if(rfq.getLastName().length()>=lastName.length() && rfq.getLastName().substring(0,lastName.length()).equalsIgnoreCase(lastName)){
                            if(hasStartDate){
                                if(rfq.getSubmitDate().getTimeInMillis() >= startDateLong){
                                    if(hasEndDate){
                                        if(rfq.getSubmitDate().getTimeInMillis() <= endDateLong){
                                            isFound = true;
                                            rfqCnt++;
                                            searchIndices.add(cnt);
                                        }
                                    }else{
                                        isFound = true;
                                        rfqCnt++;
                                        searchIndices.add(cnt);
                                    }
                                }
                            }else if(hasEndDate){
                                if(rfq.getSubmitDate().getTimeInMillis() <= endDateLong){
                                    isFound = true;
                                    rfqCnt++;
                                    searchIndices.add(cnt);
                                }
                            }else{
                                isFound = true;
                                rfqCnt++;
                                searchIndices.add(cnt);
                            }
                        }
                    }else{
                        if(hasStartDate){
                            if(rfq.getSubmitDate().getTimeInMillis() >= startDateLong){
                                if(hasEndDate){
                                    if(rfq.getSubmitDate().getTimeInMillis() <= endDateLong){
                                        isFound = true;
                                        rfqCnt++;
                                        searchIndices.add(cnt);
                                    }
                                }else{
                                    isFound = true;
                                    rfqCnt++;
                                    searchIndices.add(cnt);
                                }
                            }
                        }else if(hasEndDate){
                            if(rfq.getSubmitDate().getTimeInMillis() <= endDateLong){
                                isFound = true;
                                rfqCnt++;
                                searchIndices.add(cnt);
                            }
                        }else{
                            isFound = true;
                            rfqCnt++;
                            searchIndices.add(cnt);
                        }
                    }
                }
            }else if(lastName!=""){
                if(rfq.getLastName().length()>=lastName.length() && rfq.getLastName().substring(0,lastName.length()).equalsIgnoreCase(lastName)){
                    if(hasStartDate){
                        if(rfq.getSubmitDate().getTimeInMillis() >= startDateLong){
                            if(hasEndDate){
                                if(rfq.getSubmitDate().getTimeInMillis() <= endDateLong){
                                    isFound = true;
                                    rfqCnt++;
                                    searchIndices.add(cnt);
                                }
                            }else{
                                isFound = true;
                                rfqCnt++;
                                searchIndices.add(cnt);
                            }
                        }
                    }else if(hasEndDate){
                        if(rfq.getSubmitDate().getTimeInMillis() <= endDateLong){
                            isFound = true;
                            rfqCnt++;
                            searchIndices.add(cnt);
                        }
                    }else{
                        isFound = true;
                        rfqCnt++;
                        searchIndices.add(cnt);
                    }               
                }
            }else{
                if(hasStartDate){
                    if(rfq.getSubmitDate().getTimeInMillis() >= startDateLong){
                        if(hasEndDate){
                            isFound = true;
                            rfqCnt++;
                            searchIndices.add(cnt);
                        }else{
                            isFound = true;
                            rfqCnt++;
                            searchIndices.add(cnt);
                        }
                    }
                }else if(hasEndDate){
                    if(rfq.getSubmitDate().getTimeInMillis() <= endDateLong){
                        isFound = true;
                        rfqCnt++;
                        searchIndices.add(cnt);
                    }
                }else{
                    isFound = true;
                    rfqCnt++;
                    searchIndices.add(cnt);
                }
            }
            cnt++;
            }
        if(!isFound){
            mainPanel.setLayout(new GridLayout(21,5,0,0));
            mainPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            mainPanel.add(new JLabel("Search Found 0 Results..."));
            buildBlankLabels(8,mainPanel,false);
            for(int i = 0; i < 19; i++){
                buildBlankLabels(5,mainPanel,false);
            }
            JButton btn = buildButton("Back");
            btn.addActionListener(new SearchListener());
            btn.setMnemonic(KeyEvent.VK_B);
            btn.setDisplayedMnemonicIndex(-1);
            mainPanel.add(btn);
        }else{
            GridLayout layout = new GridLayout(21,5,0,0);
            mainPanel.setLayout(layout);
            mainPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            buildRfqListHeader();
            cnt = 0;
            for(int i = 0; i < 19; i++){
                try{
                    buildRFQ(rfqs.get(searchIndices.get(i)),searchIndices.get(i), i);
                    cnt++;
                }catch(IndexOutOfBoundsException e){
                    i = 19;
                }
            }
            int fillOut = 19 - rfqCnt;
            if(fillOut<0)fillOut = 0;
            for(int i = 0; i < fillOut; i++){
                buildBlankLabels(5,mainPanel,false);
            }
            mainPanel.add(new JLabel("1 - " + cnt + " of " + searchIndices.size()));
            buildBlankLabels(3,mainPanel,false);
            if(fillOut!=0){
                mainPanel.add(new JLabel(""));
            }else{
                JButton next = buildButton("Next Page");
                next.addActionListener(new SearchNextPageListener());
                next.setActionCommand("19");
                next.setMnemonic(KeyEvent.VK_RIGHT);
                mainPanel.add(next);
            }
        }
        updateMainPanel();
    }
    
    /**
     * Go to next search page
     */
    public void nextSearchPage(int lastIndex){
        mainPanel = getDefaultMainPanel();
        mainPanel.setLayout(new GridLayout(21,5,0,0));
        buildRfqListHeader();
        int cnt = 0;
        int stop = lastIndex + 19;
        for(int i = lastIndex; i < stop; i++){
            try{
                cnt++;
                buildRFQ(rfqs.get(searchIndices.get(i)),searchIndices.get(i),(cnt-1));
            }catch(IndexOutOfBoundsException e){
                i = stop;
            } 
        }
        if(cnt<19){
            stop = 20 - cnt;
            for(int i = 0; i < stop; i++){
                buildBlankLabels(5,mainPanel,false);
            }
            cnt--;
        }
        if(lastIndex==0){
            cnt--;
        }
        mainPanel.add(new JLabel((lastIndex+1) + " - " + (lastIndex+cnt) + " of " + searchIndices.size()));
        buildBlankLabels(2,mainPanel,false);
        if((lastIndex+cnt)==searchIndices.size()){
            mainPanel.add(new JLabel(""));
        }
        JButton previous = buildButton("Previous Page");
        previous.addActionListener(new SearchPreviousPageListener());
        previous.setActionCommand(String.valueOf(lastIndex));
        previous.setMnemonic(KeyEvent.VK_LEFT);
        mainPanel.add(previous);
        if((lastIndex+cnt)!=searchIndices.size()){
            JButton next = buildButton("Next Page");
            next.addActionListener(new SearchNextPageListener());
            next.setActionCommand(String.valueOf(lastIndex+19));
            next.setMnemonic(KeyEvent.VK_RIGHT);
            mainPanel.add(next);
        }
        updateMainPanel();
    }
    
    /**
     * Go to previous search page
     */
    public void previousSearchPage(int firstIndex){
        mainPanel = getDefaultMainPanel();
        mainPanel.setLayout(new GridLayout(21,5,0,0));
        buildRfqListHeader();
        firstIndex = firstIndex - 19;
        int stop = firstIndex + 19;
        int cnt = 0;
        for(int i = firstIndex; i < stop; i++){
            buildRFQ(rfqs.get(searchIndices.get(i)),searchIndices.get(i),cnt);
            cnt++;
        }
        mainPanel.add(new JLabel((firstIndex+1) + " - " + stop + " of " + searchIndices.size()));
        buildBlankLabels(2,mainPanel,false);
        if(firstIndex==0){
            mainPanel.add(new JLabel(""));
        }else{
            JButton previous = buildButton("Previous Page");
            previous.addActionListener(new SearchPreviousPageListener());
            previous.setActionCommand(String.valueOf(firstIndex));
            previous.setMnemonic(KeyEvent.VK_LEFT);
            mainPanel.add(previous);
        }
        JButton next = buildButton("Next Page");
        next.addActionListener(new SearchNextPageListener());
        next.setActionCommand(String.valueOf(stop));
        next.setMnemonic(KeyEvent.VK_RIGHT);
        mainPanel.add(next);
        updateMainPanel();
    }
        
    /**
     * Builds Cutsomer Information edit screen
     */
    public void buildCustomerEdit(int index){
        clearWindow();
        hasChanged = false;
        RequestForQuote selected = rfqs.get(index);
        mainPanel = getDefaultMainPanel();
        mainPanel.setLayout(new GridLayout(8,2));
        JLabel label = new JLabel("Edit Customer Information");
        label.setBorder(new EmptyBorder(0,10,0,0));
        mainPanel.add(label);
        mainPanel.add(new JLabel(""));
        Panel panel = new Panel();
        panel.setLayout(new GridLayout(2,0));
        label = new JLabel("First Name");
        label.setBorder(new EmptyBorder(0,10,0,0));
        panel.add(label);
        firstNameUpdate = new JTextField(selected.getFirstName());
        Border input_spaced = BorderFactory.createCompoundBorder(new EmptyBorder(5,20,5,20), INPUT_BORDER);
        firstNameUpdate.setBorder(input_spaced);
        firstNameUpdate.addKeyListener(new EditCustomerFieldEmptyListener());
        firstNameUpdate.addMouseListener(new InputFieldRightClickListener());
        panel.add(firstNameUpdate);
        mainPanel.add(panel);
        mainPanel.add(new JLabel(""));
        panel = new Panel();
        panel.setLayout(new GridLayout(2,0));
        label = new JLabel("Last Name");
        label.setBorder(new EmptyBorder(0,10,0,0));
        panel.add(label);
        lastNameUpdate = new JTextField(selected.getLastName());
        lastNameUpdate.setBorder(input_spaced);
        lastNameUpdate.addKeyListener(new EditCustomerFieldEmptyListener());
        lastNameUpdate.addMouseListener(new InputFieldRightClickListener());
        panel.add(lastNameUpdate);
        mainPanel.add(panel);
        mainPanel.add(new JLabel(""));
        panel = new Panel();
        panel.setLayout(new GridLayout(2,0));
        label = new JLabel("Email Address");
        label.setBorder(new EmptyBorder(0,10,0,0));
        panel.add(label);
        emailAddressUpdate = new JTextField(selected.getEmailAddress());
        emailAddressUpdate.setBorder(input_spaced);
        emailAddressUpdate.addKeyListener(new EditCustomerEmailListener());
        emailAddressUpdate.addMouseListener(new InputFieldRightClickListener());
        panel.add(emailAddressUpdate);
        mainPanel.add(panel);
        mainPanel.add(new JLabel(""));
        panel = new Panel();
        panel.setLayout(new GridLayout(2,0));
        label = new JLabel("Phone Number");
        label.setBorder(new EmptyBorder(0,10,0,0));
        panel.add(label);
        Panel phoneNumberPanel = new Panel();
        String phoneNumber = getFormattedPhoneNumber(selected.getPhoneNumber());
        phoneNumberPanel.add(new JLabel("("));
        phoneNumberAreaCodeUpdate = new JTextField(phoneNumber.substring(0,3),3);
        phoneNumberAreaCodeUpdate.setBorder(INPUT_BORDER);
        phoneNumberAreaCodeUpdate.addKeyListener(new EditCustomerPhoneListener());
        phoneNumberAreaCodeUpdate.addMouseListener(new InputFieldRightClickListener());
        phoneNumberPanel.add(phoneNumberAreaCodeUpdate);
        phoneNumberPanel.add(new JLabel(")"));
        phoneNumberPrefixUpdate = new JTextField(phoneNumber.substring(3,6),3);
        phoneNumberPrefixUpdate.setBorder(INPUT_BORDER);
        phoneNumberPrefixUpdate.addKeyListener(new EditCustomerPhoneListener());
        phoneNumberPrefixUpdate.addMouseListener(new InputFieldRightClickListener());
        phoneNumberPanel.add(phoneNumberPrefixUpdate);
        phoneNumberPanel.add(new JLabel("-"));
        phoneNumberSuffixUpdate = new JTextField(phoneNumber.substring(6,10),4);
        phoneNumberSuffixUpdate.setBorder(INPUT_BORDER);
        phoneNumberSuffixUpdate.addKeyListener(new EditCustomerPhoneListener());
        phoneNumberSuffixUpdate.addMouseListener(new InputFieldRightClickListener());
        phoneNumberPanel.add(phoneNumberSuffixUpdate);
        panel.add(phoneNumberPanel);
        mainPanel.add(panel);
        buildBlankLabels(6,mainPanel,false);
        panel = new Panel();
        panel.setLayout(new GridLayout(3,5));    
        buildBlankLabels(6,panel,false);
        JButton cancelBtn = buildButton("Cancel");
        cancelBtn.addActionListener(new CancelCustomerEditListener());
        cancelBtn.setMnemonic(KeyEvent.VK_C);
        cancelBtn.setDisplayedMnemonicIndex(-1);
        panel.add(cancelBtn);
        panel.add(new JLabel(""));
        JButton saveBtn = buildButton("Save");
        saveBtn.addActionListener(new SaveCustomerEditListener());
        saveBtn.setMnemonic(KeyEvent.VK_S);
        saveBtn.setDisplayedMnemonicIndex(-1);
        panel.add(saveBtn);
        buildBlankLabels(6,panel,false);
        mainPanel.add(panel);
        updateMainPanel();
    }
    
    /**
     * Builds help window
     */
    public void buildHelpWindow(){
        mainPanel = getDefaultMainPanel();
        mainPanel.setLayout(new GridLayout(20,0));
        Panel panel = new Panel();
        panel.add(new JLabel("Help"));
        mainPanel.add(panel);
        mainPanel.add(new JLabel(""));
        panel = new Panel();
        panel.setLayout(new GridLayout(1,5));
        buildBlankLabels(2,panel,true);
        JButton btn = buildButton("Responding to quotes");
        btn.addActionListener(new HelpRespondListener());
        btn.setMnemonic(KeyEvent.VK_R);
        btn.setDisplayedMnemonicIndex(-1);
        panel.add(btn);
        buildBlankLabels(2,panel,true);
        mainPanel.add(panel);
        mainPanel.add(new JLabel(""));
        panel = new Panel();
        panel.setLayout(new GridLayout(1,5));
        buildBlankLabels(2,panel,true);
        btn = buildButton("Searching for quotes");
        btn.addActionListener(new HelpSearchListener());
        btn.setMnemonic(KeyEvent.VK_S);
        btn.setDisplayedMnemonicIndex(-1);
        panel.add(btn);
        buildBlankLabels(2,panel,true);
        mainPanel.add(panel);
        mainPanel.add(new JLabel(""));
        panel = new Panel();
        panel.setLayout(new GridLayout(1,5));
        buildBlankLabels(2,panel,true);
        btn = buildButton("Checking for new quotes");
        btn.addActionListener(new HelpCheckListener());
        btn.setMnemonic(KeyEvent.VK_C);
        btn.setDisplayedMnemonicIndex(-1);
        panel.add(btn);
        buildBlankLabels(2,panel,true);
        mainPanel.add(panel);
        mainPanel.add(new JLabel(""));
        panel = new Panel();
        panel.setLayout(new GridLayout(1,5));
        buildBlankLabels(2,panel,true);
        btn = buildButton("Editing customer information");
        btn.addActionListener(new HelpEditListener());
        btn.setMnemonic(KeyEvent.VK_E);
        btn.setDisplayedMnemonicIndex(-1);
        panel.add(btn);
        buildBlankLabels(2,panel,true);
        mainPanel.add(panel);
        mainPanel.add(new JLabel(""));
        panel = new Panel();
        panel.setLayout(new GridLayout(1,5));
        buildBlankLabels(2,panel,true);
        btn = buildButton("Keyboard shortcuts");
        btn.addActionListener(new HelpKeysListener());
        btn.setMnemonic(KeyEvent.VK_K);
        btn.setDisplayedMnemonicIndex(-1);
        panel.add(btn);
        buildBlankLabels(2,panel,true);
        mainPanel.add(panel);
        mainPanel.add(new JLabel(""));
        panel = new Panel();
        panel.setLayout(new GridLayout(1,5));
        buildBlankLabels(2,panel,true);
        btn = buildButton("Return to quote list");
        btn.addActionListener(new ViewAllListener());
        btn.setMnemonic(KeyEvent.VK_B);
        btn.setDisplayedMnemonicIndex(-1);
        panel.add(btn);
        buildBlankLabels(2,panel,true);
        mainPanel.add(panel);
        buildBlankLabels(6,mainPanel,false);
        updateMainPanel();
    }
    
    /**
     * Builds responding to quotes help screen
     */
    public void buildHelpRespond(){
        mainPanel = getDefaultMainPanel();
        mainPanel.setLayout(new GridLayout(21,1));
        Panel panel = new Panel();
        panel.setLayout(new GridLayout(1,5));
        JButton btn = buildButton("Main Help Page");
        btn.addActionListener(new HelpListener());
        btn.setMnemonic(KeyEvent.VK_M);
        btn.setDisplayedMnemonicIndex(-1);
        panel.add(btn);
        buildBlankLabels(4,panel,true);
        mainPanel.add(panel);
        JLabel label = buildLabel("Responding To Quotes",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,5,0,0),null);
        mainPanel.add(label);
        label = buildLabel("• Choose a quote to edit from the list of available quotes",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        mainPanel.add(label);
        label = buildLabel("• Fill in pricing data for each item in the quote",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);        
        mainPanel.add(label);
        label = buildLabel("▬ Must be of the format: dollar amount decimal point cent amount, for example: 100.99, 12.50, etc.",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        mainPanel.add(label);
        label = buildLabel("▬ If the amount is a round dollar value you may enter just the dollar amount, for example: 100, 12, etc.",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        mainPanel.add(label);
        label = buildLabel("• Fill in the turnaround time",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        mainPanel.add(label);
        label = buildLabel("▬ Turnaround time can be fulfilled by a varity of different options, for example: 1 week, 3 days, 1 month, etc.",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        mainPanel.add(label);
        label = buildLabel("• Fill in the date that the pricing for the quote will expire",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        mainPanel.add(label);
        label = buildLabel("▬ The format is mm/dd/yyyy",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        mainPanel.add(label);
        label = buildLabel("▬ Month and day fields can be either single or double digits, for example: '9' or '09' would both be acceptable for September",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        mainPanel.add(label);
        label = buildLabel("▬ The products in the quote will be available for purchase at the provided price until 11:59:59 p.m. EST of the previous day", SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        mainPanel.add(label);
        label = buildLabel("• Enter your name or the name of the salesperson to be associated with the quote",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        mainPanel.add(label);
        label = buildLabel("• In the notes field provide any details that will be of use to the customer in regards to the quote, this field is not required",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        mainPanel.add(label);
        label = buildLabel("• When you are finished click the 'send' button",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        mainPanel.add(label);
        label = buildLabel("▬ If all data is considered valid an email will be sent to the customer and he or she will be able to purchase the products via Shopify",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        mainPanel.add(label);
        label = buildLabel("▬ If any data entered is invalid the quote will not be processed and you will be alerted of what errors exist",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        mainPanel.add(label);
        label = buildLabel("• If properly formatted data creates an error please inform the administrator",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        mainPanel.add(label);
        buildBlankLabels(2,mainPanel,false);
        panel = new Panel();
        panel.setLayout(new GridLayout(1,5));
        buildBlankLabels(4,panel,true);
        btn = buildButton("Return to quote list");
        btn.addActionListener(new ViewAllListener());
        btn.setMnemonic(KeyEvent.VK_B);
        btn.setDisplayedMnemonicIndex(-1);
        panel.add(btn);
        mainPanel.add(panel);
        updateMainPanel();
    }
    
    /**
     * Builds searching for quotes help screen
     */
    public void buildHelpSearch(){
        mainPanel = getDefaultMainPanel();
        mainPanel.setLayout(new GridLayout(22,1));
        Panel panel = new Panel();
        panel.setLayout(new GridLayout(1,5));
        JButton btn = buildButton("Main Help Page");
        btn.addActionListener(new HelpListener());
        btn.setMnemonic(KeyEvent.VK_M);
        btn.setDisplayedMnemonicIndex(-1);
        panel.add(btn);
        buildBlankLabels(4,panel,true);
        mainPanel.add(panel);
        JLabel label = buildLabel("Searching For Quotes",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,5,0,0),null);
        mainPanel.add(label);
        label = buildLabel("• From the 'View' tab of the top menu select the 'Search' option",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        mainPanel.add(label);
        label = buildLabel("• Fill out any fields that you are interested in searching by, fields may be left blank to provide a broader search",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        mainPanel.add(label);
        label = buildLabel("• Filling out the 'Start Submit Date' field will cause the search to return quotes that were submitted from that date forward",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        mainPanel.add(label);
        label = buildLabel("• Filling out the 'End Submit Date' field will cause the search to return quotes that were submitted prior to that date",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        mainPanel.add(label);
        label = buildLabel("• Some things to keep in mind about the date fields",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        mainPanel.add(label);
        label = buildLabel("▬ Format is mm/dd/yyyy",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        mainPanel.add(label);
        label = buildLabel("▬ Months and Days can be a single or double digit number, for example: '9' or '09' would both be acceptable for September",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        mainPanel.add(label);
        label = buildLabel("▬ Four digits must be provided for Year fields",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        mainPanel.add(label);
        label = buildLabel("▬ Months and Days can be left blank",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        mainPanel.add(label);
        label = buildLabel("- If the day field is left blank it will be treated as:",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,35,0,0),null);
        mainPanel.add(label);
        label = buildLabel("~ the first day of the month for the 'Start Submit Date' field",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,45,0,0),null);
        mainPanel.add(label);
        label = buildLabel("~ the last day of the month for the 'End Submit Date' field",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,45,0,0),null);
        mainPanel.add(label);
        label = buildLabel("- If both the day and month field are left blank it will be treated as:",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,35,0,0),null);
        mainPanel.add(label);
        label = buildLabel("~ January 1st of the year provided for the 'Start Submit Date' field",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,45,0,0),null);
        mainPanel.add(label);
        label = buildLabel("~ December 31st of the year provided for the 'End Submit Date' field",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,45,0,0),null);
        mainPanel.add(label);
        label = buildLabel("▬ If values for the month and/or day fields are provided a value for the year field must also be provided",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        mainPanel.add(label);
        label = buildLabel("▬ If a value is provided for the day field a value must be provided for the month field",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        mainPanel.add(label);
        label = buildLabel("▬ Invalid dates will be treated as if the field was left blank",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        mainPanel.add(label);
        label = buildLabel("• Click the 'Search' button when you are ready to perform your search",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        mainPanel.add(label);
        panel = new Panel();
        panel.setLayout(new GridLayout(1,5));
        buildBlankLabels(4,panel,true);
        btn = buildButton("Return to quote list");
        btn.addActionListener(new ViewAllListener());
        btn.setMnemonic(KeyEvent.VK_B);
        btn.setDisplayedMnemonicIndex(-1);
        panel.add(btn);
        mainPanel.add(panel);
        updateMainPanel();
    }
    
    /**
     * Builds checking for new quotes help screen
     */
    public void buildHelpCheck(){
        mainPanel = getDefaultMainPanel();
        mainPanel.setLayout(new GridLayout(21,1));
        Panel panel = new Panel();
        panel.setLayout(new GridLayout(1,5));
        JButton btn = buildButton("Main Help Page");
        btn.addActionListener(new HelpListener());
        btn.setMnemonic(KeyEvent.VK_M);
        btn.setDisplayedMnemonicIndex(-1);
        panel.add(btn);
        buildBlankLabels(4,panel,true);
        mainPanel.add(panel);
        JLabel label = buildLabel("Checking For New Quotes",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,5,0,0),null);        
        mainPanel.add(label);
        label = buildLabel("• From the 'File' tab on the top menu select the 'Check For New Quotes' option",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        mainPanel.add(label);
        label = buildLabel("• New quotes will be pulled from the web and displayed in the quote list",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        mainPanel.add(label);
        label = buildLabel("• Newest quotes will appear at the top of the first page of the quote list",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        mainPanel.add(label);
        buildBlankLabels(15,mainPanel,false);
        panel = new Panel();
        panel.setLayout(new GridLayout(1,5));
        buildBlankLabels(4,panel,true);
        btn = buildButton("Return to quote list");
        btn.addActionListener(new ViewAllListener());
        btn.setMnemonic(KeyEvent.VK_B);
        btn.setDisplayedMnemonicIndex(-1);
        panel.add(btn);
        mainPanel.add(panel);
        updateMainPanel();
    }
    
    /**
     * Builds editing customer information help screen
     */
    public void buildHelpEdit(){
        mainPanel = getDefaultMainPanel();
        mainPanel.setLayout(new GridLayout(22,1));
        Panel panel = new Panel();
        panel.setLayout(new GridLayout(1,5));
        JButton btn = buildButton("Main Help Page");
        btn.addActionListener(new HelpListener());
        btn.setMnemonic(KeyEvent.VK_M);
        btn.setDisplayedMnemonicIndex(-1);
        panel.add(btn);
        buildBlankLabels(4,panel,true);
        mainPanel.add(panel);
        JLabel label = buildLabel("Editing Customer Information",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,5,0,0),null);
        mainPanel.add(label);
        label = buildLabel("• From the edit quote page of the customer you would like to edit click the 'Edit Information' button",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        mainPanel.add(label);
        label = buildLabel("• If you would like to alter the customer's first name edit the 'First Name' field",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        mainPanel.add(label);
        label = buildLabel("▬ This field cannot be left blank",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        mainPanel.add(label);
        label = buildLabel("• If you would like to alter the customer's last name edit the 'Last Name' field",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        mainPanel.add(label);
        label = buildLabel("▬ This field cannot be left blank",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        mainPanel.add(label);
        label = buildLabel("• If you would like to alter the customer's email address edit the 'Email Address' field",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        mainPanel.add(label);
        label = buildLabel("▬ The data is checked to make sure that the email address is properly format",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        mainPanel.add(label);
        label = buildLabel("▬ This field cannot be left blank",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        mainPanel.add(label);
        label = buildLabel("• The phone number field is broken into three sections: the area code, the prefix, and the line number",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        mainPanel.add(label);
        label = buildLabel("▬ To edit this field fill in values for all three sections",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        mainPanel.add(label);
        label = buildLabel("▬ Only numeric characters are accepted",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        mainPanel.add(label);
        label = buildLabel("▬ None of the sections can be left blank",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        mainPanel.add(label);
        label = buildLabel("• Once you have made all of the required changes to the customer's information click the 'Save' button",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        mainPanel.add(label);
        label = buildLabel("▬ If all data is valid the customer's informatioin will be updated",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        mainPanel.add(label);
        label = buildLabel("▬ If there are any errors the customer's information will not be updated and you will be alerted of the errors that exist",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        mainPanel.add(label);
        label = buildLabel("• You may click cancel ay any time to leave the edit customer information page",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        mainPanel.add(label);
        label = buildLabel("▬ No changes will be made to the customer information",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        mainPanel.add(label);
        label = buildLabel("• If multiple quotes exist for the same customer any information change will only occur on the selected quote",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        mainPanel.add(label);
        label = buildLabel("• If properly formatted data creates an error please inform the administrator",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        mainPanel.add(label);
        panel = new Panel();
        panel.setLayout(new GridLayout(1,5));
        buildBlankLabels(4,panel,true);
        btn = buildButton("Return to quote list");
        btn.addActionListener(new ViewAllListener());
        btn.setMnemonic(KeyEvent.VK_B);
        btn.setDisplayedMnemonicIndex(-1);
        panel.add(btn);
        mainPanel.add(panel);
        updateMainPanel();
    }
    
    /**
     * Builds keyboard keys help screen
     */
    public void buildHelpKeys(){
        mainPanel = getDefaultMainPanel();
        mainPanel.setLayout(new GridLayout(21,1));
        Panel panel = new Panel();
        panel.setLayout(new GridLayout(1,5));
        JButton btn = buildButton("Main Help Page");
        btn.addActionListener(new HelpListener());
        btn.setMnemonic(KeyEvent.VK_M);
        btn.setDisplayedMnemonicIndex(-1);
        panel.add(btn);
        buildBlankLabels(4,panel,true);
        mainPanel.add(panel);
        JLabel label = buildLabel("Keyboard Shortcuts",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,5,0,0),null);
        mainPanel.add(label);
        panel = new Panel();
        panel.setLayout(new GridLayout(1,3));
        label = buildLabel("• 'Back' and 'Return to quote list' buttons",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        panel.add(label);
        label = buildLabel("alt+B",SwingConstants.LEFT,null,null);
        panel.add(label);
        panel.add(new JLabel(""));
        panel.addMouseListener(new RightClickListener());
        mainPanel.add(panel);
        panel = new Panel();
        panel.setLayout(new GridLayout(1,3));
        label = buildLabel("• 'Next' buttons",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        panel.add(label);
        label = buildLabel("alt+right arrow",SwingConstants.LEFT,null,null);
        panel.add(label);
        panel.add(new JLabel(""));
        panel.addMouseListener(new RightClickListener());
        mainPanel.add(panel);
        panel = new Panel();
        panel.setLayout(new GridLayout(1,3));
        label = buildLabel("• 'Previous' buttons",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        panel.add(label);
        label = buildLabel("alt+left arrow",SwingConstants.LEFT,null,null);
        panel.add(label);
        panel.add(new JLabel(""));
        panel.addMouseListener(new RightClickListener());
        mainPanel.add(panel);
        panel = new Panel();
        panel.setLayout(new GridLayout(1,3));
        label = buildLabel("• 'Edit information' buttons",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        panel.add(label);
        label = buildLabel("alt+E",SwingConstants.LEFT,null,null);
        panel.add(label);
        panel.add(new JLabel(""));
        panel.addMouseListener(new RightClickListener());
        mainPanel.add(panel);
        panel = new Panel();
        panel.setLayout(new GridLayout(1,3));
        label = buildLabel("• 'Send', 'Search', and 'Save' buttons",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        panel.add(label);
        label = buildLabel("alt+S",SwingConstants.LEFT,null,null);
        panel.add(label);
        panel.add(new JLabel(""));
        panel.addMouseListener(new RightClickListener());
        mainPanel.add(panel);
        panel = new Panel();
        panel.setLayout(new GridLayout(1,3));
        label = buildLabel("• 'Cancel' buttons",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        panel.add(label);
        label = buildLabel("alt+C",SwingConstants.LEFT,null,null);
        panel.add(label);
        panel.add(new JLabel(""));
        panel.addMouseListener(new RightClickListener());
        mainPanel.add(panel);
        panel = new Panel();
        panel.setLayout(new GridLayout(1,3));
        label = buildLabel("• 'Main Help Page' buttons",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        panel.add(label);
        label = buildLabel("alt+M",SwingConstants.LEFT,null,null);
        panel.add(label);
        panel.add(new JLabel(""));
        panel.addMouseListener(new RightClickListener());
        mainPanel.add(panel);
        label = buildLabel("• Help Menu Shorcuts",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,10,0,0),null);
        mainPanel.add(label);
        panel = new Panel();
        panel.setLayout(new GridLayout(1,3));
        label = buildLabel("▬ 'Responding to quotes'",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        panel.add(label);
        label = buildLabel("alt+R",SwingConstants.LEFT,null,null);
        panel.add(label);
        panel.add(new JLabel(""));
        panel.addMouseListener(new RightClickListener());
        mainPanel.add(panel);
        panel = new Panel();
        panel.setLayout(new GridLayout(1,3));
        label = buildLabel("▬ 'Searching for quotes'",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        panel.add(label);
        label = buildLabel("alt+S",SwingConstants.LEFT,null,null);
        panel.add(label);
        panel.add(new JLabel(""));
        panel.addMouseListener(new RightClickListener());
        mainPanel.add(panel);
        panel = new Panel();
        panel.setLayout(new GridLayout(1,3));
        label = buildLabel("▬ 'Checking for new quotes'",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        panel.add(label);
        label = buildLabel("alt+C",SwingConstants.LEFT,null,null);
        panel.add(label);
        panel.add(new JLabel(""));
        panel.addMouseListener(new RightClickListener());
        mainPanel.add(panel);
        panel = new Panel();
        panel.setLayout(new GridLayout(1,3));
        label = buildLabel("▬ 'Editing customer information'",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        panel.add(label);
        label = buildLabel("alt+E",SwingConstants.LEFT,null,null);
        panel.add(label);
        panel.add(new JLabel(""));
        panel.addMouseListener(new RightClickListener());
        mainPanel.add(panel);
        panel = new Panel();
        panel.setLayout(new GridLayout(1,3));
        label = buildLabel("▬ 'Keyboard shortcuts'",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,20,0,0),null);
        panel.add(label);
        label = buildLabel("alt+K",SwingConstants.LEFT,null,null);
        panel.add(label);
        panel.add(new JLabel(""));
        panel.addMouseListener(new RightClickListener());
        mainPanel.add(panel);
        buildBlankLabels(5,mainPanel,false);
        panel = new Panel();
        panel.setLayout(new GridLayout(1,5));
        buildBlankLabels(4, panel,true);
        btn = buildButton("Return to quote list");
        btn.addActionListener(new ViewAllListener());
        btn.setMnemonic(KeyEvent.VK_B);
        btn.setDisplayedMnemonicIndex(-1);
        panel.add(btn);
        mainPanel.add(panel);
        updateMainPanel();
    }
    
    /**
     * Builds commmon errors help screen
     */
    public void buildHelpError(){
        mainPanel = getDefaultMainPanel();
        mainPanel.setLayout(new GridLayout(21,1));
        Panel panel = new Panel();
        panel.setLayout(new GridLayout(1,5));
        JButton btn = buildButton("Main Help Page");
        btn.addActionListener(new HelpListener());        
        panel.add(btn);
        buildBlankLabels(4,panel,true);
        mainPanel.add(panel);
        JLabel label = buildLabel("Common Errors",SwingConstants.LEFT,BorderFactory.createEmptyBorder(0,5,0,0),null);
        mainPanel.add(label);
        buildBlankLabels(18,mainPanel,false);
        panel = new Panel();
        panel.setLayout(new GridLayout(1,5));
        buildBlankLabels(4,panel,true);
        btn = buildButton("Return to quote list");
        btn.addActionListener(new ViewAllListener());
        panel.add(btn);
        mainPanel.add(panel);
        updateMainPanel();
    }
    
    /**
     * Build rfq line
     * @param RequestForQuote rfq
     */
    public void buildRFQ(RequestForQuote rfq, int index, int placement){
        mainPanel.add(buildLabel(rfq.getFileName().replace("C:\\shopifyRFQresponse\\",""),SwingConstants.CENTER,UNDERLINE,null));
        mainPanel.add(buildLabel(rfq.getFirstName() + " " + rfq.getLastName(),SwingConstants.CENTER,UNDERLINE,null));
        mainPanel.add(buildLabel(rfq.getSubmitDateString(),SwingConstants.CENTER,UNDERLINE,null));
        if(rfq.getStatus()){
            mainPanel.add(buildLabel("Complete",SwingConstants.CENTER,UNDERLINE,SUCCESS_GREEN));
            JButton btn = buildButton("View");
            btn.addActionListener(new ViewBtnListener());
            btn.setActionCommand(String.valueOf(index));
            btn.setMnemonic(getRfqListKeyEvent(placement));
            btn.setText("<html>View <span style='color:#a0afbf; font-size:8px;'>alt+" + getRfqListKeyChar(placement) + "</span></html>");
            btn.setDisplayedMnemonicIndex(-1);
            mainPanel.add(btn);
        }else{
            mainPanel.add(buildLabel("Incomplete",SwingConstants.CENTER,UNDERLINE,ERROR_RED));
            JButton btn = buildButton("Edit");
            btn.addActionListener(new EditBtnListener());
            btn.setActionCommand(String.valueOf(index));
            btn.setMnemonic(getRfqListKeyEvent(placement));
            btn.setText("<html>Edit <span style='color:#a0afbf; font-size:8px;'>alt+" + getRfqListKeyChar(placement) + "</span></html>");
            btn.setDisplayedMnemonicIndex(-1);
            mainPanel.add(btn);
        }
    }
    
    /**
     * Get RfqList KeyEvent
     * @param int placement
     * @return KeyEvent
     */
    public int getRfqListKeyEvent(int placement){
        int key = 0;
        switch(placement){
            case 0:
                key = KeyEvent.VK_1;
                break;
            case 1:
                key = KeyEvent.VK_2;
                break;
            case 2:
                key = KeyEvent.VK_3;
                break;
            case 3:
                key = KeyEvent.VK_4;
                break;
            case 4:
                key = KeyEvent.VK_5;
                break;
            case 5:
                key = KeyEvent.VK_6;
                break;
            case 6:
                key = KeyEvent.VK_7;
                break;
            case 7:
                key = KeyEvent.VK_8;
                break;
            case 8:
                key = KeyEvent.VK_9;
                break;
            case 9:
                key = KeyEvent.VK_0;
                break;
            case 10:
                key = KeyEvent.VK_Q;
                break;
            case 11:
                key = KeyEvent.VK_W;
                break;
            case 12:
                key = KeyEvent.VK_E;
                break;
            case 13:
                key = KeyEvent.VK_R;
                break;
            case 14:
                key = KeyEvent.VK_T;
                break;
            case 15:
                key = KeyEvent.VK_Y;
                break;
            case 16:
                key = KeyEvent.VK_U;
                break;
            case 17:
                key = KeyEvent.VK_I;
                break;
            case 18:
                key = KeyEvent.VK_O;
                break;
        }
        return key;
    }
    
    /**
     * Returns character associated with edit/view key command
     * @param int placement
     * @return String character
     */
    public String getRfqListKeyChar(int placement){
        String character = "";
        switch(placement){
            case 0:
                character = "1";
                break;
            case 1:
                character = "2";
                break;
            case 2:
                character = "3";
                break;
            case 3:
                character = "4";
                break;
            case 4:
                character = "5";
                break;
            case 5:
                character = "6";
                break;
            case 6:
                character = "7";
                break;
            case 7:
                character = "8";
                break;
            case 8:
                character = "9";
                break;
            case 9:
                character = "0";
                break;
            case 10:
                character = "Q";
                break;
            case 11:
                character = "W";
                break;
            case 12:
                character = "E";
                break;
            case 13:
                character = "R";
                break;
            case 14:
                character = "T";
                break;
            case 15:
                character = "Y";
                break;
            case 16:
                character = "U";
                break;
            case 17:
                character = "I";
                break;
            case 18:
                character = "O";
                break;
        }        
        return character;
    }
    
    /**
     * Add empty labels
     * @param int numberOfLabels
     * @param Panel panel
     */
    public void buildBlankLabels(int numberOfLabels, Panel inputPanel, boolean addRightClick){
        for(int i = 0; i < numberOfLabels; i++){
            JLabel label = new JLabel("");
            if(addRightClick) label.addMouseListener(new RightClickListener());
            inputPanel.add(label);            
        }
    }
    
    
    /**
     * Returns 10 digit phone number with no extra characters or spacing
     * @param String input
     * @return String output
     */
    public String getFormattedPhoneNumber(String input){
        String output = "";
        output = input.trim().replaceAll(" ","");
        output = output.replaceAll("\\(","");
        output = output.replaceAll("\\)","");
        output = output.replaceAll("-","");
        return output;
    }
    
    /**
     * Saves customer information
     */
    public boolean saveCustomerInformation(int rfqIndex){
        boolean saved = false;
        boolean fNamePass = true;
        boolean lNamePass = true;
        boolean emailPass = true;
        boolean phonePass = true;
        RequestForQuote selected = rfqs.get(rfqIndex);
        String fName = firstNameUpdate.getText();
        String lName = lastNameUpdate.getText();
        String email = emailAddressUpdate.getText();
        String phone = "(" + phoneNumberAreaCodeUpdate.getText() + ") " + phoneNumberPrefixUpdate.getText() + "-" + phoneNumberSuffixUpdate.getText();
        if(isFieldNotEmpty(fName)){
            if(isFieldNotEmpty(lName)){
                if(isEmailAddressValid(email)){
                    if(isPhoneNumberValid(phone)){
                        selected.setFirstName(fName);
                        selected.setLastName(lName);
                        selected.setEmailAddress(email);
                        selected.setPhoneNumber(phone);
                        ModifyXml update = new ModifyXml(selected);
                        if(update.setElementData()){
                            saved = true;
                        }
                    }else{
                        phonePass = false;
                    }
                }else{
                    emailPass = false;
                    if(!isPhoneNumberValid(phone)) phonePass = false;
                }
            }else{
                lNamePass = false;
                if(!isEmailAddressValid(email))emailPass = false;
                if(!isPhoneNumberValid(phone)) phonePass = false;
            }
        }else{
            fNamePass = false;
            if(!isFieldNotEmpty(lName))lNamePass = false;
            if(!isEmailAddressValid(email))emailPass = false;
            if(!isPhoneNumberValid(phone)) phonePass = false;
        }
        if(!saved){
            int r = ERROR_RED.getRed();
            int g = ERROR_RED.getGreen();
            int b = ERROR_RED.getBlue();
            if(!fNamePass){
                firstNameUpdate.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5,20,5,20),INPUT_ERROR_BORDER));
                Panel firstNameParent = (Panel)firstNameUpdate.getParent();
                JLabel firstNameLabel = (JLabel) firstNameParent.getComponent(0);
                firstNameLabel.setText("<html>First Name - <span style='color: rgb(" + r + "," + g + "," + b + ")'>This field must be completed</span></html>");
            }
            if(!lNamePass){
                lastNameUpdate.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5,20,5,20),INPUT_ERROR_BORDER));
                Panel lastNameParent = (Panel) lastNameUpdate.getParent();
                JLabel lastNameLabel = (JLabel) lastNameParent.getComponent(0);
                lastNameLabel.setText("<html>Last Name - <span style='color: rgb(" + r + "," + g + "," + b + ")'>This field must be completed</span></html>");
            }
            if(!emailPass){
                emailAddressUpdate.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5,20,5,20),INPUT_ERROR_BORDER));
                Panel emailAddressParent = (Panel) emailAddressUpdate.getParent();
                JLabel emailAddressLabel = (JLabel) emailAddressParent.getComponent(0);
                emailAddressLabel.setText("<html>Email Address - <span style='color: rgb(" + r + "," + g + "," + b + ")'>The data entered is invalid</span></html>");
            }
            if(!phonePass){
                phoneNumberAreaCodeUpdate.setBorder(INPUT_ERROR_BORDER);
                phoneNumberPrefixUpdate.setBorder(INPUT_ERROR_BORDER);
                phoneNumberSuffixUpdate.setBorder(INPUT_ERROR_BORDER);
                Panel phoneNumberParent = (Panel) phoneNumberAreaCodeUpdate.getParent().getParent();
                JLabel phoneNumberLabel = (JLabel) phoneNumberParent.getComponent(0);
                phoneNumberLabel.setText("<html>Phone Number - <span style='color: rgb(" + r + "," + g + "," + b + ")'>The data entered is invalid</span></html>");
            }
        }
        return saved;
    }
    
    /**
     * Verify email address
     * @param String email
     * @return boolean isValid
     */
    public boolean isEmailAddressValid(String email){
        boolean isValid = false;
        Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPattern.matcher(email);
        if(matcher.find()){
            isValid = true;
        }
        return isValid;
    }
    
    /**
     * Verify phone number
     * @param String phone
     * @return boolean isValid
     */
    public boolean isPhoneNumberValid(String phone){
        boolean isValid = false;
        Pattern phonePattern = Pattern.compile("^\\([0-9][0-9][0-9]\\) [0-9][0-9][0-9]\\-[0-9][0-9][0-9][0-9]$",Pattern.CASE_INSENSITIVE);
        Matcher matcher = phonePattern.matcher(phone);
        if(matcher.find()){
            isValid = true;
        }
        return isValid;
    }
    
    /**
     * Verify field is not blank
     */
    public boolean isFieldNotEmpty(String input){
        boolean isNotEmpty = true;
        if(input.trim().equals("")){
            isNotEmpty = false;
        }
        return isNotEmpty;
    }
    
    /**
     * Verify price
     * @param String input
     */
    public boolean isPriceValid(String input){
        boolean validity = false;
        try{
            Double price = Double.parseDouble(input);
            if(input.contains(".")){
                String[] splitter = input.split("\\.");
                if(splitter[1].length()==2){
                    validity = true;
                }
            }else{
                validity = true;
            }
        }catch(NumberFormatException e){
            validity = false;
        }catch(ArrayIndexOutOfBoundsException e){
            validity = false;
        }
        return validity;
    }
    
    /**
     * Verify date is valid and not in the past
     * @param String month
     * @param String day 
     * @param String year
     */
    public boolean isDateValid(String month, String day, String year){
        boolean isValid = false;
        try{
            int numMonth = Integer.parseInt(month);
            int numDay = Integer.parseInt(day);
            int numYear = Integer.parseInt(year);
            int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
            int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            numMonth = numMonth - 1;
            Calendar c = new GregorianCalendar();
            c.setLenient(false);
            c.set(numYear,numMonth,numDay);
            c.getTime();
            isValid = true;
            if(numYear > currentYear){
                isValid = true;
            }else if(numYear == currentYear){
                if(numMonth > currentMonth){
                    isValid = true;
                }else if(numMonth==currentMonth){
                    if(numDay >= currentDay){
                        isValid = true;
                    }else{
                        isValid = false;
                    }
                }else{
                    isValid = false;
                }
            }else{
                isValid = false;
            }
        }catch(NumberFormatException nfe){
            isValid = false;
        }catch(IllegalArgumentException iae){
            isValid = false;
        }
        return isValid;
    }    
    
    /**
     * Validates all dates
     * @param String month
     * @param String day
     * @param String year
     */
    public boolean isDateValidIncludingPast(String month, String day, String year){
        boolean isValid = false;
        try{
            int numMonth = Integer.parseInt(month);
            int numDay = Integer.parseInt(day);
            int numYear = Integer.parseInt(year);
            numMonth--;
            Calendar c = new GregorianCalendar();
            c.setLenient(false);
            c.set(numYear,numMonth,numDay);
            c.getTime();
            isValid = true;
        }catch(NumberFormatException nfe){
            isValid = false;
        }catch(IllegalArgumentException iae){
            isValid = false;
        }
        return isValid;
    }
    
    /**
     * Validates RFQ entry
     */
    public boolean isRfqValid(){
        boolean isValid = true;
        int r = ERROR_RED.getRed();
        int g = ERROR_RED.getGreen();
        int b = ERROR_RED.getBlue();
        String rep = "";
        int itemCnt = rfqs.get(currentRfq).getItemList().size();
        for(int i = 0; i < itemCnt; i++){
            JTextField itemPriceComp = (JTextField) currentRfqComponents.get("itemPrice" + String.valueOf(i));
            String itemPrice = itemPriceComp.getText();
            if(!isPriceValid(itemPrice)){
                isValid = false;
                itemPriceComp.setBorder(INPUT_ERROR_BORDER);
            }else{
                itemPriceComp.setBorder(INPUT_BORDER);
            }
        }
        JTextField turnaroundDateComp = (JTextField) currentRfqComponents.get("turnaroundTime");
        String turnaroundDate = turnaroundDateComp.getText();
        JLabel turnaroundDateLabel = (JLabel) currentRfqComponents.get("turnaroundTimeLabel");
        if(!isFieldNotEmpty(turnaroundDate)){
           isValid = false;
           turnaroundDateComp.setBorder(INPUT_ERROR_BORDER);           
           turnaroundDateLabel.setText("<html>Turnaround Time - <span style='color: rgb(" + r + "," + g + "," + b + ")'>Must be filled in</span></html>");
        }else{
           turnaroundDateComp.setBorder(INPUT_BORDER);
           if(turnaroundDateLabel.getText().indexOf(" - ")!=-1){
               rep = turnaroundDateLabel.getText().substring(0,turnaroundDateLabel.getText().indexOf(" - "));
               rep = rep.replace("<html>","");
               turnaroundDateLabel.setText(rep);
           }
        }
        JTextField month = (JTextField) currentRfqComponents.get("expirationMonth");
        JTextField day = (JTextField) currentRfqComponents.get("expirationDay");
        JTextField year = (JTextField) currentRfqComponents.get("expirationYear");
        JLabel dateLabel = (JLabel) currentRfqComponents.get("expirationDateLabel");
        if(!isDateValid(month.getText(), day.getText(), year.getText())){
            isValid = false;
            month.setBorder(INPUT_ERROR_BORDER);
            day.setBorder(INPUT_ERROR_BORDER);
            year.setBorder(INPUT_ERROR_BORDER);
            dateLabel.setText("<html>Quote Expiration Date - <span style='color: rgb(" + r + "," + g + "," + b + ")'>Invalid data entered</span></html>");
        }else{
            month.setBorder(INPUT_BORDER);
            day.setBorder(INPUT_BORDER);
            year.setBorder(INPUT_BORDER);
            if(dateLabel.getText().indexOf(" - ")!=-1){
                rep = dateLabel.getText().substring(0,dateLabel.getText().indexOf(" - "));
                rep = rep.replace("<html>","");
                dateLabel.setText(rep);
            }
        }
        JTextField salesPersonComp = (JTextField) currentRfqComponents.get("salesPerson");
        String salesPerson = salesPersonComp.getText();
        JLabel salesPersonLabel = (JLabel) currentRfqComponents.get("salesPersonLabel");
        if(!isFieldNotEmpty(salesPerson)){
            isValid = false;
            salesPersonComp.setBorder(INPUT_ERROR_BORDER);
            salesPersonLabel.setText("<html>Sales Person - <span style='color: rgb(" + r + "," + g + "," + b +")'>Must be filled in</span></html>");
        }else{
            salesPersonComp.setBorder(INPUT_BORDER);
            if(salesPersonLabel.getText().indexOf(" - ")!=-1){
                rep = salesPersonLabel.getText().substring(0,salesPersonLabel.getText().indexOf(" - "));
                rep = rep.replace("<html>","");
                salesPersonLabel.setText(rep);
            }
        }
        return isValid;
    }
    
    /**
     * Returns the last day of the month
     * @param String month
     * @param String year
     * @return String day
     */
    public String getLastDayOfMonth(String month, String year){
        String day = "";
        Calendar date = Calendar.getInstance();
        int monthInt = Integer.parseInt(month);
        int yearInt = Integer.parseInt(year);
        date.set(Calendar.MONTH,monthInt);
        date.set(Calendar.YEAR,yearInt);
        date.set(Calendar.DAY_OF_MONTH,1);
        date.add(Calendar.DATE, -1);
        int dayInt = date.get(Calendar.DAY_OF_MONTH);
        day = String.valueOf(dayInt);
        return day;
    }
    
    /**
     * Prevents window from resizing on JFrame pack method call
     */
    public void autoSize(){
        int width = getWidth();
        int height = getHeight();
        setPreferredSize(new Dimension(width, height));        
    }
    
    /**
     * Checks if mouse is over component
     * @param Component comp - component to check for mouse over
     */
    public boolean isMouseOver(Component comp){
        boolean isOver = true;
        try{
            if(MouseInfo.getPointerInfo().getLocation().x >= comp.getLocationOnScreen().x 
            && MouseInfo.getPointerInfo().getLocation().x <= comp.getLocationOnScreen().x + comp.getWidth()
            && MouseInfo.getPointerInfo().getLocation().y >= comp.getLocationOnScreen().y
            && MouseInfo.getPointerInfo().getLocation().y <= comp.getLocationOnScreen().y + comp.getHeight()){
                isOver = true;
            }else{
                isOver = false;
            }        
        }catch(Exception e){
            isOver = false;
        }
        return isOver;
    }
        
    /**
     * Return submit dates of all customers
     */
    public void printAllDates(){
        try{
            java.io.FileWriter fWrite = new java.io.FileWriter("C:\\users\\awaldman\\desktop\\dates.txt");
            for(RequestForQuote r : rfqs){
                fWrite.write(r.getFirstName() + " " + r.getLastName() + " - " + r.getSubmitDateString() + " - " + r.getSubmitDate().getTimeInMillis() + "\r\n"); 
            }
            fWrite.close();
        }catch(java.io.IOException e){
            System.out.println(e.getMessage());
        }
    }    
        
    /**
     * Sets hasChanged variable
     * @param boolean hasChanged
     */
    public void setHasChanged(boolean hasChanged){
        this.hasChanged = hasChanged;
    }
    
    /**
     * gets hasChanged variable
     * @return boolean hasChanged
     */
    public boolean getHasChanged(){
        return hasChanged;
    }
    
    /**
     * Confirm to leave page with changes made
     * @return boolean
     */
    public boolean shouldLeave(){
        JLabel label = new JLabel("<html><center>You have made changes. They will not be saved.<br>Are you sure you want to leave this page?</center></html>");
        label.setForeground(LIGHT_BLUE);
        int resp = JOptionPane.showConfirmDialog(ProgramWindow.this,label,"Confirm",JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE,new ImageIcon("icons//icon.png"));
        if(resp==JOptionPane.OK_OPTION){
            return true;
        }
        return false;
    }
    
    /**
     * Returns stating index for back to search page
     * @param int current
     * @return int current
     */
    public int getStartIndex(int current){
        for(int i = 0; i < searchIndices.size(); i++){
            if(current==searchIndices.get(i)){
                current = i;
                i = searchIndices.size();
            }
        }
        return current;
    }
    
    /**
     * Writes Errors to log File
     * @param String errorString
     */
    public void writeError(String errorWriteString, String errorDisplayString, int errorCode){
        Calendar date = Calendar.getInstance();
        String dateTime = "";
        if(date.get(Calendar.MONTH)<9){
         dateTime += "0";
        }
        dateTime += (date.get(Calendar.MONTH)+1) + "/";
        if(date.get(Calendar.DAY_OF_MONTH)<10){
            dateTime += "0";
        }
        dateTime += date.get(Calendar.DAY_OF_MONTH) + "/" + date.get(Calendar.YEAR) + " - ";
        if(date.get(Calendar.HOUR)==0){
            dateTime += "12" + ":";
        }else{
            if(date.get(Calendar.HOUR)<10){
                dateTime += "0";
            }
            dateTime += date.get(Calendar.HOUR) + ":";
        }
        if(date.get(Calendar.MINUTE)<10){
            dateTime += "0";
        }
        dateTime += date.get(Calendar.MINUTE) + ":";
        if(date.get(Calendar.SECOND)<10){
            dateTime += "0";
        }
        dateTime += date.get(Calendar.SECOND) + " ";
        if(date.get(Calendar.AM_PM)==0){
            dateTime += "AM";
        }else{
            dateTime += "PM";
        }            
        ErrorLogger.writeError(dateTime + "\r\n" + errorCode + " - " + errorWriteString + "\r\n\r\n");
        JLabel label = new JLabel("<html>An error has occured.<br>" + errorDisplayString +"<br>Code " + errorCode + "</html>");
        label.setForeground(ERROR_RED);
        JOptionPane.showMessageDialog(this,label,"Error",JOptionPane.ERROR_MESSAGE,new ImageIcon("icons//icon.png"));
    }
    
    /**
     * Handles Edit button clicks
     */
    public class EditBtnListener implements ActionListener{
        public void actionPerformed(ActionEvent e){            
            clearWindow();
            buildQuote(Integer.parseInt(e.getActionCommand()));
        }
    }

    /**
     * Handles View button clicks
     */
    public class ViewBtnListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            clearWindow();
            buildReadOnlyQuote(Integer.parseInt(e.getActionCommand()));
        }
    }

    /**
     * Handles Back Button on quote page
     */
    public class BackBtnListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(hasChanged){
                if(shouldLeave()){
                    hasChanged = false;
                    clearWindow();
                    if(fromSearch){
                        buildSearchListFromIndex(getStartIndex(currentRfq));
                    }else{
                        buildListFromIndex(currentRfq);
                    }
                }
            }else{
                clearWindow();
                if(fromSearch){
                    buildSearchListFromIndex(getStartIndex(currentRfq));
                }else{
                    buildListFromIndex(currentRfq);
                }
            }
        }
    }
    
    /**
     * View All Button
     */
    public class ViewAllListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(hasChanged){
                if(shouldLeave()){
                    hasChanged = false;
                    fromSearch = false;
                    clearWindow();
                    buildWindow(false);
                }
            }else{
                fromSearch = false;
                clearWindow();
                buildWindow(false);
            }
        }
    }
    
    /**
     * Handles Send Button on quote page
     */
    public class SendBtnListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(isRfqValid()){
                hasChanged = false;
                JLabel label = new JLabel("Input is valid");
                label.setForeground(LIGHT_BLUE);
                JOptionPane.showMessageDialog(ProgramWindow.this,label,"Success",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("icons//icon.png"));
            }else{
                JLabel label = new JLabel("Input is invalid");
                label.setForeground(LIGHT_BLUE);
                JOptionPane.showMessageDialog(ProgramWindow.this,label,"Failure",JOptionPane.ERROR_MESSAGE,new ImageIcon("icons//icon.png"));
            }
        }
    }

    /**
     * Handles Search option in file menu
     */
    public class SearchListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(hasChanged){
                if(shouldLeave()){                    
                    hasChanged = false;
                    fromSearch = false;
                    clearWindow();
                    buildSearchWindow();
                }
            }else{
                fromSearch = false;
                clearWindow();
                buildSearchWindow();
            }
        }
    }
    
    /**
     * Handles search button from search menu
     */
    public class SearchBtnListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String firstName = searchFirstNameField.getText();
            String lastName = searchLastNameField.getText();
            String startMonth = searchStartDateMonth.getText();
            String startDay = searchStartDateDay.getText();
            String startYear = searchStartDateYear.getText();
            String endMonth = searchEndDateMonth.getText();
            String endDay = searchEndDateDay.getText();
            String endYear = searchEndDateYear.getText();
            Calendar startDate = null;
            Calendar endDate = null;
            ParseXml dateBuilder = null;
            if(!startMonth.equals("") && !startDay.equals("") && !startYear.equals("")){
                if(isDateValidIncludingPast(startMonth,startDay,startYear)){
                    dateBuilder = new ParseXml();
                    startDate = dateBuilder.buildDate(startMonth + "/" + startDay + "/" + startYear);
                    startDate.add(Calendar.DATE, -1);
                }                
            }else if(!startMonth.equals("") && !startYear.equals("")){
                startDay = "1";
                if(isDateValidIncludingPast(startMonth,startDay,startYear)){
                    dateBuilder = new ParseXml();
                    startDate = dateBuilder.buildDate(startMonth + "/" + startDay + "/" + startYear);
                    startDate.add(Calendar.DATE, -1);
                }
            }else if(!startYear.equals("")){
                startMonth = "1";
                startDay = "1";
                if(isDateValidIncludingPast(startMonth,startDay,startYear)){
                    dateBuilder = new ParseXml();
                    startDate = dateBuilder.buildDate(startMonth + "/" + startDay + "/" + startYear);
                    startDate.add(Calendar.DATE, -1);
                }
            }
            if(!endMonth.equals("") && !endDay.equals("") && !endYear.equals("")){
                if(isDateValidIncludingPast(endMonth,endDay,endYear)){
                    dateBuilder = new ParseXml();
                    endDate = dateBuilder.buildDate(endMonth + "/" + endDay + "/" + endYear);
                }
            }else if(!endMonth.equals("") && !endYear.equals("")){
                endDay = getLastDayOfMonth(endMonth, endYear);
                if(isDateValidIncludingPast(endMonth,endDay,endYear)){
                    dateBuilder = new ParseXml();
                    endDate = dateBuilder.buildDate(endMonth + "/" + endDay + "/" + endYear);
                }
            }else if(!endYear.equals("")){
                endMonth = "12";
                endDay = "31";
                if(isDateValidIncludingPast(endMonth,endDay,endYear)){
                    dateBuilder = new ParseXml();
                    endDate = dateBuilder.buildDate(endMonth + "/" + endDay + "/" + endYear);
                }
            }
            fromSearch = true;
            superSearchQuotes(firstName,lastName,startDate,endDate);
        }
    }
    
    /**
     * Next Page Listener
     */
    public class NextPageListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            int lastIndex = Integer.parseInt(e.getActionCommand());
            clearWindow();
            buildNext(lastIndex);
        }
    }

    /**
     * Previous Page Listener
     */
    public class PreviousPageListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            int firstIndex = Integer.parseInt(e.getActionCommand());
            clearWindow();
            buildPrevious(firstIndex);
        }
    }
    
    /**
     * Search Next Page Listener
     */
    public class SearchNextPageListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            clearWindow();
            nextSearchPage(Integer.parseInt(e.getActionCommand()));
        }
    }
    
    /**
     * Search Previous Page Listener
     */
    public class SearchPreviousPageListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            clearWindow();
            previousSearchPage(Integer.parseInt(e.getActionCommand()));
        }
    }
    
    /**
     * Handles check for quotes option in file
     */
    public class CheckForNewQuotesListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(hasChanged){
                if(shouldLeave()){
                    hasChanged = false;
                    fromSearch = false;
                    populateRfqList();
                    sortDates();
                    clearWindow();
                    buildWindow(false);
                }
            }else{
                fromSearch = false;
                populateRfqList();
                sortDates();
                clearWindow();
                buildWindow(false);
            }
        }
    }
    
    /**
     * Handles Help FAQ option
     */
    public class HelpListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(hasChanged){
                if(shouldLeave()){
                    hasChanged = false;
                    fromSearch = false;
                    clearWindow();
                    buildHelpWindow();
                }
            }else{
                fromSearch = false;
                clearWindow();
                buildHelpWindow();
            }
        }
    }
    
    /**
     * Handles Help About option
     */
    public class HelpAboutListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JLabel label = buildLabel("<html>Version 1.0<br>Report errors to matt@plastic-craft.com<br>Properties File location: " + PropertyReader.getPropertiesFileLocation() + "<br>Error Log location: " + PropertyReader.getProperty("errorlog").replace("\\\\","\\") + "</html>",SwingConstants.LEFT,null,null);
            label.setForeground(LIGHT_BLUE);
            JOptionPane.showMessageDialog(ProgramWindow.this,label,"About RFQ Responder",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("icons/icon.png"));
        }
    }
    
    /**
     * Handles help responding to quotes option
     */
    public class HelpRespondListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(hasChanged){
                if(shouldLeave()){
                    hasChanged = false;
                    fromSearch = false;
                    clearWindow();
                    buildHelpRespond();
                }
            }else{
                fromSearch = false;
                clearWindow();
                buildHelpRespond();
            }
        }
    }
    
    /**
     * Handles help searching for quotes option
     */
    public class HelpSearchListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(hasChanged){
                if(shouldLeave()){
                    hasChanged = false;
                    fromSearch = false;
                    clearWindow();
                    buildHelpSearch();
                }
            }else{
                fromSearch = false;
                clearWindow();
                buildHelpSearch();
            }
        }
    }
    
    /**
     * Handles help checking for new quotes option
     */
    public class HelpCheckListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(hasChanged){
                if(shouldLeave()){
                    hasChanged = false;
                    fromSearch = false;
                    clearWindow();
                    buildHelpCheck();
                }
            }else{
                fromSearch = false;
                clearWindow();
                buildHelpCheck();
            }
        }       
    }
    
    /**
     * Handles help edit customer information
     */
    public class HelpEditListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(hasChanged){
                if(shouldLeave()){
                    hasChanged = false;
                    fromSearch = false;
                    clearWindow();
                    buildHelpEdit();
                }
            }else{
                fromSearch = false;
                clearWindow();
                buildHelpEdit();
            }
        }
    }
    
    /**
     * Handles help key commands option
     */
    public class HelpKeysListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(hasChanged){
                if(shouldLeave()){
                    hasChanged = false;
                    fromSearch = false;
                    clearWindow();
                    buildHelpKeys();
                }
            }else{
                fromSearch = false;
                clearWindow();
                buildHelpKeys();
            }
        }
    }
    
    /**
     * Handles help common errors information
     */
    public class HelpErrorListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(hasChanged){
                if(shouldLeave()){
                    hasChanged = false;
                    fromSearch = false;
                    clearWindow();
                    buildHelpError();
                }
            }else{
                fromSearch = false;
                clearWindow();
                buildHelpError();
            }
        }
    }
    
    /**
     * Handles Close option in file menu
     */
    public class CloseListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
    }
    
    /**
     * Handles Customer Edit btn
     */
    public class EditCustomerListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            buildCustomerEdit(currentRfq);
        }
    }
    
    /**
     * Handles Cancel Customer Edit
     */
    public class CancelCustomerEditListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(hasChanged){
                if(shouldLeave()){
                    hasChanged = false;
                    clearWindow();
                    buildQuote(currentRfq);
                }
            }else{
                clearWindow();
                buildQuote(currentRfq);
            }
        }
    }
    
    /**
     * Handles Save Customer Edit
     */
    public class SaveCustomerEditListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(saveCustomerInformation(currentRfq)){
                hasChanged = false;
                JLabel label = new JLabel("Customer information successfully updated");
                label.setForeground(LIGHT_BLUE);
                JOptionPane.showMessageDialog(ProgramWindow.this,label,"Success",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("icons//icon.png"));
                clearWindow();
                buildWindow(false);
            }else{
                JLabel label = new JLabel("Customer information failed to update");
                label.setForeground(LIGHT_BLUE);
                JOptionPane.showMessageDialog(ProgramWindow.this,label,"Failure",JOptionPane.ERROR_MESSAGE,new ImageIcon("icons//icon.png"));
            }
        }
    }
    
    /**
     * Button Mouse Listener
     */
    public class BtnMouseListener implements MouseListener{
        public void mouseClicked(MouseEvent e){
        }

        public void mousePressed(MouseEvent e){
            e.getComponent().setForeground(Color.WHITE);
            JButton btn = (JButton) e.getComponent();
            btn.setBorder(BTN_CLICK_BORDER);
        }

        public void mouseReleased(MouseEvent e){
            JButton btn = (JButton) e.getComponent();
            if(isMouseOver(e.getComponent())){
                btn.setForeground(LIGHT_BLUE);
                btn.setBackground(Color.WHITE);
            }else{
                btn.setForeground(Color.WHITE);
                btn.setBackground(LIGHT_BLUE);
            }
            btn.setBorder(BTN_INITIAL_BORDER);
        }        

        public void mouseEntered(MouseEvent e){
            e.getComponent().setBackground(Color.WHITE);
            e.getComponent().setForeground(LIGHT_BLUE);
            JButton btn = (JButton) e.getComponent();
            btn.setBorder(BTN_INITIAL_BORDER);
        }

        public void mouseExited(MouseEvent e){
            e.getComponent().setBackground(LIGHT_BLUE);
            e.getComponent().setForeground(Color.WHITE);
            JButton btn = (JButton) e.getComponent();
            btn.setBorder(BTN_INITIAL_BORDER);
        }
    }

    /**
     * Price input key listener
     */
    public class PriceInputKeyListener implements KeyListener{
        public void keyReleased(KeyEvent e){
            hasChanged = true;
            JTextField item = (JTextField) e.getComponent();
            if(!isPriceValid(item.getText())){
                item.setBorder(INPUT_ERROR_BORDER);
            }else{
                item.setBorder(INPUT_BORDER);
            }
        }

        public void keyPressed(KeyEvent e){
        }

        public void keyTyped(KeyEvent e){
        }
    }
    
    /**
     * Month input key listener
     */
    public class MonthInputKeyListener implements KeyListener{
        public void keyReleased(KeyEvent e){
            hasChanged = true;
            JTextField item = (JTextField) e.getComponent();
            try{
                int dateConvert = Integer.parseInt(item.getText());
                if(dateConvert>0 && dateConvert<13){
                    item.setBorder(INPUT_BORDER);
                    JTextField dayComp = (JTextField) currentRfqComponents.get("expirationDay");
                    JTextField yearComp = (JTextField) currentRfqComponents.get("expirationYear");
                    String day = dayComp.getText();
                    String year = yearComp.getText();
                    if(isDateValid(item.getText(),day,year)){
                        dayComp.setBorder(INPUT_BORDER);
                        yearComp.setBorder(INPUT_BORDER);
                        JLabel label = (JLabel) currentRfqComponents.get("expirationDateLabel");
                        if(label.getText().indexOf(" - ")!=-1){
                            String rep = label.getText().substring(0,label.getText().indexOf(" - "));
                            rep = rep.replace("<html>","");
                            label.setText(rep);
                        }
                    }else{
                        item.setBorder(INPUT_ERROR_BORDER);
                        dayComp.setBorder(INPUT_ERROR_BORDER);
                        yearComp.setBorder(INPUT_ERROR_BORDER);
                    }
                }else{
                    item.setBorder(INPUT_ERROR_BORDER);
                }
            }catch(NumberFormatException exception){
                item.setBorder(INPUT_ERROR_BORDER);
            }
        }
        
        public void keyPressed(KeyEvent e){
        }
        
        public void keyTyped(KeyEvent e){
        }
    }
    
    /**
     * Day input key listener
     */
    public class DayInputKeyListener implements KeyListener{
        public void keyReleased(KeyEvent e){
            hasChanged = true;
            JTextField item = (JTextField) e.getComponent();
            try{
                int day = Integer.parseInt(item.getText());
                if(day>0 && day<32){
                    item.setBorder(INPUT_BORDER);
                    JTextField monthComp = (JTextField) currentRfqComponents.get("expirationMonth");
                    JTextField yearComp = (JTextField) currentRfqComponents.get("expirationYear");
                    String month = monthComp.getText();
                    String year = yearComp.getText();
                    if(isDateValid(month,item.getText(),year)){
                        monthComp.setBorder(INPUT_BORDER);
                        yearComp.setBorder(INPUT_BORDER);
                        JLabel label = (JLabel) currentRfqComponents.get("expirationDateLabel");
                        if(label.getText().indexOf(" - ")!=-1){
                            String rep = label.getText().substring(0,label.getText().indexOf(" - "));
                            rep = rep.replace("<html>","");
                            label.setText(rep);
                        }
                    }else{
                        item.setBorder(INPUT_ERROR_BORDER);
                        monthComp.setBorder(INPUT_ERROR_BORDER);
                        yearComp.setBorder(INPUT_ERROR_BORDER);
                    }
                }else{
                    item.setBorder(INPUT_ERROR_BORDER);
                }
            }catch(NumberFormatException nfe){
                item.setBorder(INPUT_ERROR_BORDER);
            }
        }
        
        public void keyPressed(KeyEvent e){
        }
        
        public void keyTyped(KeyEvent e){
        }
    }
    
    /**
     * Year input key listener
     */
    public class YearInputKeyListener implements KeyListener{
        public void keyReleased(KeyEvent e){
            hasChanged = true;
            JTextField item = (JTextField) e.getComponent();
            try{
                int inputYear = Integer.parseInt(item.getText());
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                if(inputYear>=currentYear){
                    item.setBorder(INPUT_BORDER);
                    JTextField monthComp = (JTextField)currentRfqComponents.get("expirationMonth");
                    JTextField dayComp = (JTextField)currentRfqComponents.get("expirationDay");
                    String month = monthComp.getText();
                    String day = dayComp.getText();
                    if(isDateValid(month,day,item.getText())){
                        monthComp.setBorder(INPUT_BORDER);
                        dayComp.setBorder(INPUT_BORDER);
                        JLabel label = (JLabel) currentRfqComponents.get("expirationDateLabel");
                        if(label.getText().indexOf(" - ")!=-1){
                            String rep = label.getText().substring(0,label.getText().indexOf(" - "));
                            rep = rep.replace("<html>","");
                            label.setText(rep);
                        }
                    }else{
                        item.setBorder(INPUT_ERROR_BORDER);
                        monthComp.setBorder(INPUT_ERROR_BORDER);
                        dayComp.setBorder(INPUT_ERROR_BORDER);
                    }
                }else{
                    item.setBorder(INPUT_ERROR_BORDER);
                }
            }catch(NumberFormatException nfe){
                item.setBorder(INPUT_ERROR_BORDER);
            }            
        }        
        
        public void keyPressed(KeyEvent e){
        }
        
        public void keyTyped(KeyEvent e){
        }
    }
    
    /**
     * Listener for empty/non-empty fields
     */
    public class FieldEmptyListener implements KeyListener{
        public void keyReleased(KeyEvent e){
            hasChanged = true;
            JTextField item = (JTextField) e.getSource();
            if(isFieldNotEmpty(item.getText())){
                item.setBorder(INPUT_BORDER);
                String labelName = item.getName() + "Label";
                JLabel label = (JLabel) currentRfqComponents.get(labelName);
                if(label.getText().indexOf(" - ")!=-1){
                    String rep = label.getText().substring(0,label.getText().indexOf(" - "));
                    rep = rep.replace("<html>","");
                    label.setText(rep);
                }
            }else{
                item.setBorder(INPUT_ERROR_BORDER);
            }
        }
        
        public void keyPressed(KeyEvent e){
        }
        
        public void keyTyped(KeyEvent e){
        }
    }
    
    /**
     * Listener to see if notes has changed
     */
    public class NotesChangedListener implements KeyListener{
        public void keyReleased(KeyEvent e){
            hasChanged = true;
        }
        
        public void keyPressed(KeyEvent e){
        }
        
        public void keyTyped(KeyEvent e){
        }
    }
    
    /**
     * Listener for edit customer empty/non-empty fields
     */
    public class EditCustomerFieldEmptyListener implements KeyListener{
        public void keyReleased(KeyEvent e){
            hasChanged = true;
            JTextField item = (JTextField) e.getSource();
            if(isFieldNotEmpty(item.getText())){
                item.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5,20,5,20),INPUT_BORDER));
                Panel parentPanel = (Panel) item.getParent();
                JLabel parentLabel = (JLabel) parentPanel.getComponent(0);
                if(parentLabel.getText().indexOf(" - ")!=-1){
                    String rep = parentLabel.getText().substring(0,parentLabel.getText().indexOf(" - "));
                    rep.replace("<html>","");
                    parentLabel.setText(rep);
                }
            }else{
                item.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5,20,5,20),INPUT_ERROR_BORDER));
            }
        }
        
        public void keyPressed(KeyEvent e){
        }
        
        public void keyTyped(KeyEvent e){
        }
    }
    
    /**
     * Listener for edit customer email fields
     */
    public class EditCustomerEmailListener implements KeyListener{
        public void keyReleased(KeyEvent e){
            hasChanged = true;
            JTextField item = (JTextField) e.getSource();
            if(isEmailAddressValid(item.getText())){
                item.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5,20,5,20),INPUT_BORDER));
                Panel panel = (Panel) item.getParent();
                JLabel label = (JLabel) panel.getComponent(0);
                if(label.getText().indexOf(" - ")!=-1){
                    String rep = label.getText().substring(0,label.getText().indexOf(" - "));
                    rep.replace("<html>","");
                    label.setText(rep);
                }                
            }else{
                item.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5,20,5,20),INPUT_ERROR_BORDER));
            }
        }
        
        public void keyPressed(KeyEvent e){
        }
        
        public void keyTyped(KeyEvent e){
        }
    }
    
    /**
     * Listener for edit customer phone fields
     */
    public class EditCustomerPhoneListener implements KeyListener{
        public void keyReleased(KeyEvent e){
            hasChanged = true;
            String areaCode = phoneNumberAreaCodeUpdate.getText();
            String prefix = phoneNumberPrefixUpdate.getText();
            String suffix = phoneNumberSuffixUpdate.getText();
            String phone = "(" + areaCode + ") " + prefix + "-" + suffix;
            if(isPhoneNumberValid(phone)){
                phoneNumberAreaCodeUpdate.setBorder(INPUT_BORDER);
                phoneNumberPrefixUpdate.setBorder(INPUT_BORDER);
                phoneNumberSuffixUpdate.setBorder(INPUT_BORDER);
                Panel panel = (Panel) phoneNumberAreaCodeUpdate.getParent().getParent();
                JLabel label = (JLabel) panel.getComponent(0);
                if(label.getText().indexOf(" - ")!=-1){
                    String rep = label.getText().substring(0,label.getText().indexOf(" - "));
                    rep.replace("<html>","");
                    label.setText(rep);
                }
            }else{
                phoneNumberAreaCodeUpdate.setBorder(INPUT_ERROR_BORDER);
                phoneNumberPrefixUpdate.setBorder(INPUT_ERROR_BORDER);
                phoneNumberSuffixUpdate.setBorder(INPUT_ERROR_BORDER);
            }
        }
        
        public void keyPressed(KeyEvent e){
        }
        
        public void keyTyped(KeyEvent e){
        }
    }
    
    /**
     * Listener for notes right click
     */
    public class NotesListener implements MouseListener{
        public void mouseExited(MouseEvent e){
        }
        
        public void mouseEntered(MouseEvent e){
        }
        
        public void mouseReleased(MouseEvent e){
            if(e.getButton()==3){
                Component comp = (Component) e.getSource();
                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
                comp.requestFocus();
                RightClickMenu menu = new RightClickMenu("notes",comp, ProgramWindow.this);
                menu.show(e.getComponent(), e.getX(), e.getY());                
            }
        }
        
        public void mousePressed(MouseEvent e){
        }
        
        public void mouseClicked(MouseEvent e){
        }
    }
    
    /**
     * Listener for JTextField right click
     */
    public class InputFieldRightClickListener implements MouseListener{
        public void mouseExited(MouseEvent e){
        }
        
        public void mouseEntered(MouseEvent e){
        }
        
        public void mousePressed(MouseEvent e){
        }
        
        public void mouseClicked(MouseEvent e){
        }
        
        public void mouseReleased(MouseEvent e){
            if(e.getButton()==3){
                Component comp = (Component) e.getSource();
                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
                comp.requestFocus();
                RightClickMenu menu = new RightClickMenu("textInput",comp,ProgramWindow.this);
                menu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }
    
    /**
     * Listener for main right click
     */
    public class RightClickListener implements MouseListener{
        public void mouseExited(MouseEvent e){
        }
        
        public void mouseEntered(MouseEvent e){
        }
        
        public void mousePressed(MouseEvent e){
        }
        
        public void mouseClicked(MouseEvent e){
        }
        
        public void mouseReleased(MouseEvent e){
            if(e.getButton()==3){
                RightClickMenu menu = new RightClickMenu("main",null,ProgramWindow.this);
                menu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }
}