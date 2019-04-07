package carogame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JPanel;

public class PlayerName extends JDialog implements ActionListener {

    private CaroFrame caroFrame;
    private String playerName1 = "PLAYER 1";
    private String playerName2 = "PLAYER 2";
    private JTextField tfPlayer1;
    private JTextField tfPlayer2;
    private JRadioButton radPlayer1;
    private JRadioButton radPlayer2;
    private int start = 1;

    public PlayerName(CaroFrame caroFrame) {
        setTitle("SELECT WHO PLAYER FRIST");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setResizable(false);

        this.caroFrame = caroFrame;

        // set text field name area 
        tfPlayer1 = createJTextField(true);
        tfPlayer2 = createJTextField(false);
        //set button group 
        ButtonGroup btnG = new ButtonGroup();
        radPlayer1 = new JRadioButton();
        radPlayer1.setSelected(true);
        radPlayer1.addActionListener(this);
        radPlayer2 = new JRadioButton();
        radPlayer2.addActionListener(this);

        btnG.add(radPlayer1);
        btnG.add(radPlayer2);

        //create Panel 
        add(createPanel());

        pack();
        setLocationRelativeTo(null);

    }

    private JTextField createJTextField(boolean player) {
        String playerName = player ? playerName1 : playerName2;
        JTextField tf = new JTextField(15);
        tf.setText(playerName);
        return tf;
    }

    private JPanel createPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        //main panel 
        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 2, 2));
        mainPanel.add(playerPanel(true));
        mainPanel.add(playerPanel(false));
        //bottom panel 
        JButton btn = new JButton("DONE");
        btn.addActionListener(this);

        panel.add(mainPanel, BorderLayout.CENTER);
        panel.add(btn, BorderLayout.PAGE_END);

        return panel;
    }

    private JPanel playerPanel(boolean player) {
        JTextField tf;
        JLabel lb;
        JRadioButton rad;
        if (player) {
            tf = tfPlayer1;
            lb = new JLabel("Player 1: ");
            rad = radPlayer1;
        } else {
            tf = tfPlayer2;
            lb = new JLabel("Player 2: ");
            rad = radPlayer2;
        }
        JPanel panel = new JPanel();
        panel.add(lb);
        panel.add(tf);
        panel.add(rad);
        return panel;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == radPlayer1) {
            start = 1;
        }
        if (e.getSource() == radPlayer2) {
            start = 2;
        }
        if (e.getActionCommand() == "DONE") {
            if (checkEmpty(tfPlayer1)) {
                return;
            }
            if (checkEmpty(tfPlayer2)) {
                return;
            }

            playerName1 = tfPlayer1.getText();
            playerName2 = tfPlayer2.getText();
            caroFrame.updateStatus();

        }
    }

    private boolean checkEmpty(JTextField tf) {
        if (tf.getText().trim().equals("")) {
            tf.requestFocus();
            return true;
        }
        return false;
    }

    public String getPlayerName1() {
        return playerName1;
    }

    public String getPlayerName2() {
        return playerName2;
    }

    public void setPlayerName1(String playerName1) {
        this.playerName1 = playerName1;
    }

    public void setPlayerName(String playerName2) {
        this.playerName2 = playerName2;
    }

    int getStart() {
        return start;
    }
}
