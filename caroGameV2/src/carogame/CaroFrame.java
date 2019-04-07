package carogame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class CaroFrame extends JFrame implements ActionListener {

    private CaroGraphics caroGraphics;
    private int width = caroGraphics.width;
    private int height = caroGraphics.height;
    public static JLabel lbStatusO;
    public static JLabel lbStatusX;
    private JLabel lbNamePlayerO;
    private JLabel lbNamePlayerX;
    private PlayerName selectPlayerFrame;
    private JLabel lbScoreX;
    private JLabel lbScoreO;
    private ImageIcon iconPayerO;
    private MyImage myImage = new MyImage();
    private ImageIcon iconPlayerX;
    private ImageIcon iconPlayerO;
    private int scoreX = 0;
    private int scoreO = 0;
    private String playerName1 = "Player 1";
    private String playerName2 = "Player 2";

    public CaroFrame() {
        init();
    }

    private void init() {
        setTitle("Caro Game");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        initGraphics();

        setJMenuBar(createJMenuBar());
        add(createMainPainl());
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        selectPlayer();
    }

    private void initGraphics() {
        caroGraphics = new CaroGraphics();
        caroGraphics.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                caroGraphics.actionClick(e.getPoint());
                if (caroGraphics.getWiner() > 0) {
                    win(caroGraphics.getWiner());
                }
            }
        });
    }

    private void win(int winer) {
        String playerName = "";
        if (winer == 1) {
            scoreX++;
            playerName = playerName1;
        } else {
            scoreO++;
            playerName = playerName2;
        }
        Object[] options = {"NEW GAME", "NEW MATCH", "EXIT"};
        int select = JOptionPane.showOptionDialog(this, "Congratulate " + playerName
                + " has won in the game " + (scoreO + scoreX), "A Question",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[options.length - 1]);
        if (select == 2) {
            actionExit();
        } else if (select == 0) {
            scoreX = 0;
            scoreO = 0;
        }
        clear();
    }

    private void selectPlayer() {
        if (selectPlayerFrame == null) {
            selectPlayerFrame = new PlayerName(this);
        }
        selectPlayerFrame.setVisible(true);
    }

    private JMenuBar createJMenuBar() {
        JMenuBar mb = new JMenuBar();;
        String[] game = {"NEW GAME", "NEW MATCH", "EXIT"};
        mb.add(createJMenu("Game", game, KeyEvent.VK_T));
        return mb;
    }

    private JMenu createJMenu(String menuName, String[] itemName, int key) {
        JMenu m = new JMenu(menuName);
        m.addActionListener(this);
        m.setMnemonic(key);

        for (int i = 0; i < itemName.length; i++) {
            if (itemName[i].equals("")) {
                m.add(new JSeparator());
            } else {
                m.add(createJMenuItem(itemName[i]));
            }
        }
        return m;
    }

    private JMenuItem createJMenuItem(String itName) {
        JMenuItem mi = new JMenuItem(itName);
        mi.addActionListener(this);
        return mi;
    }

    private JPanel createMainPainl() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createPanelGraphics(), BorderLayout.CENTER);
        panel.add(createSidebarPanel(true), BorderLayout.NORTH);
        return panel;
    }

    private JPanel createPanelGraphics() {
        JPanel panelGraphics = new JPanel(null);
        panelGraphics.add(caroGraphics, BorderLayout.CENTER);
        int bound = 10;
        caroGraphics.setBounds(bound, bound, caroGraphics.width, caroGraphics.height);
        panelGraphics.setPreferredSize(new Dimension(CaroGraphics.width + bound * 2,
                CaroGraphics.height + bound * 2));
        panelGraphics.setBorder(new LineBorder(Color.black));
        panelGraphics.setBackground(Color.blue);
        return panelGraphics;
    }

    private JPanel createSidebarPanel(boolean player) {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        panel.add(createPanelStatus(player));
        panel.add(createPanelStatus(!player));
        return panel;
    }

    private JPanel createPanelStatus(boolean player) {
        JPanel panelStatus = new JPanel(new GridLayout(2, 1, 2, 2));
        JPanel panel1 = new JPanel();
        String imgPlayerX = "cross.png";
        String imgPlayerO = "nought.png";
        iconPlayerO = new ImageIcon(myImage.reSizeImage(
                myImage.getMyImageIcon(imgPlayerO), 20, 20));
        iconPlayerX = new ImageIcon(myImage.reSizeImage(
                myImage.getMyImageIcon(imgPlayerX), 20, 20));

        if (player) {

            lbStatusX = new JLabel();
            lbStatusX.setHorizontalAlignment(JLabel.CENTER);
            
            lbNamePlayerX = new JLabel(playerName1);
            lbNamePlayerX.setHorizontalAlignment(JLabel.CENTER);
            
            panel1.add(lbStatusX);
            panel1.add(lbNamePlayerX);
            lbScoreX = new JLabel("0"); 
            lbScoreX.setFont(lbScoreX.getFont().deriveFont(Font.PLAIN, 35f));

            lbScoreX.setForeground(Color.red);
            lbScoreX.setHorizontalAlignment(JLabel.CENTER);
            lbScoreX.setIcon(iconPlayerX);
            lbScoreX.setIconTextGap(30);
            
            panelStatus.add(panel1); 
            panelStatus.add(lbScoreX); 

        } else {
            lbStatusO = new JLabel();
            lbStatusO.setHorizontalAlignment(JLabel.CENTER);
            lbNamePlayerO = new JLabel(playerName2);
            lbNamePlayerO.setHorizontalAlignment(JLabel.CENTER);
            panel1.add(lbStatusO);
            panel1.add(lbNamePlayerO);
            lbScoreO = new JLabel("0");
            lbScoreO.setFont(lbScoreO.getFont().deriveFont(Font.PLAIN, 40f));

            lbScoreO.setForeground(Color.BLUE);
            lbScoreO.setHorizontalAlignment(JLabel.CENTER);
            lbScoreO.setIcon(iconPlayerO);
            lbScoreO.setIconTextGap(30);
            panelStatus.add(panel1); 
            panelStatus.add(lbScoreO); 
        }

        int bound = 5;
        panelStatus.setBorder(new LineBorder(Color.GREEN));
        panelStatus.setPreferredSize(new Dimension(width / 3, height / 6 - 25));
        JPanel panel = new JPanel();
        panel.setBounds(bound, bound, bound, bound);
        panel.add(panelStatus);
        return panel;
    }

    private void actionExit() {
        int select = showDialog("Do you really want to exit?", "Exit");
        if (select == 0) {
            System.exit(0);
        }
    }

    private int showDialog(String message, String title) {
        int select = JOptionPane.showOptionDialog(null, message, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, null, null);
        return select;
    }

    private void clear() {
        caroGraphics.init();
        updateScore();
        selectPlayer();
        caroGraphics.setStatus();
    }

    private void updateScore() {
        lbScoreO.setText(scoreO + "");
        lbScoreX.setText(scoreX + "");
    }

    void updateStatus() {
        playerName1 = selectPlayerFrame.getPlayerName1();
        playerName2 = selectPlayerFrame.getPlayerName2();
        System.out.println("playerName1 = " + playerName1);
        System.out.println("playerName2 = " + playerName2);
        System.out.println("start = " + selectPlayerFrame.getStart());
        caroGraphics.player = caroGraphics.playerRoot;

        lbNamePlayerX.setText(playerName1);
        lbNamePlayerO.setText(playerName2);

        if (selectPlayerFrame.getStart() == 1) {
            caroGraphics.playerRoot = true;
            System.out.println(caroGraphics.playerRoot);
        } else {
            caroGraphics.playerRoot = false;
        }
        caroGraphics.player = caroGraphics.playerRoot;

        caroGraphics.setStatus();
        System.out.println("updated");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "NEW GAME": {
                actionNewGame();
                break;
            }
            case "NEW MATCH": {
                actionNewMatch();
                break;
            }
            case "EXIT": {
                actionExit();
                break;
            }
        }

    }

    private void actionNewGame() {
        int select = showDialog("Do you really want to create a new game?", "NEW GAME");
        if (select == 0) {
        } else {
            scoreO = 0;
            scoreX = 0;
            clear();
        }
    }

    private void actionNewMatch() {
        int select = showDialog("Do you really want to create a new match", "NEW MATCH");
        if (select == 0) {
            clear();
        }
    }
}
