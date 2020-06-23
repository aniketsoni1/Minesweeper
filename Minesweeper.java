*/
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Dimension;
import java.util.*;

class Minesweeper extends JFrame implements ActionListener, ContainerListener {

    JButton ResetButton = new JButton("");
    int[] r = {-1, -1, -1, 0, 1, 1, 1, 0};
    int[] c = {-1, 0, 1, 1, 1, 0, -1, -1};
    JButton[][] gameJbuttonsData;
    int[][] colMin;
    int[][] FrameColumnData;
    int framwindowsGame, frameHeightGame, frameTotalInfo, colTotalInfoFrame;
    int myFanVar1, myFanVar2, FinGameMinu, SubStartMin = 0, startLevels = 1;
    int gameRowBox, gameColBoxIntVal, gameMinutes = 10;

    ImageIcon[] ic = new ImageIcon[14];
    JPanel panBxData = new JPanel();
    JPanel panelMeters = new JPanel();
    JTextField minTm, tmTimer;


    Random randomRValueTime = new Random();
    Random colRandomValueTime = new Random();
    boolean valCheck = true, stTime = false;
    Point pointFrame;
    Stopwatch sw;
    MouseHendeler mHand;
    Point p;
    public static void main(String[] args) {
        // TODO code application logic here
        new Minesweeper();
    }
    Minesweeper() {
        super("Minesweeper");
        setLocation(400, 300);

        setic();
        settingPanelDesign(1, 0, 0, 0);
        setMenu();

        sw = new Stopwatch();

        ResetButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                try {
                    sw.stop();
                    settingPanelDesign(startLevels, gameRowBox, gameColBoxIntVal, gameMinutes);
                } catch (Exception ex) {
                    settingPanelDesign(startLevels, gameRowBox, gameColBoxIntVal, gameMinutes);
                }
                ResetButton();

            }
        });
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        show();
    }

    public void ResetButton() {
        valCheck = true;
        stTime = false;
        for (int i = 0; i < frameTotalInfo; i++) {
            for (int j = 0; j < colTotalInfoFrame; j++) {
                FrameColumnData[i][j] = 'w';
            }
        }
    }

    public void settingPanelDesign(int level, int setr, int setc, int setm) {
        if (level == 1) {
            framwindowsGame = 200;
            frameHeightGame = 300;
            frameTotalInfo = 8;
            colTotalInfoFrame = 8;
            FinGameMinu = 10;
        } else if (level == 2) {
            framwindowsGame = 320;
            frameHeightGame = 416;
            frameTotalInfo = 16;
            colTotalInfoFrame = 16;
            FinGameMinu = 36;
        } else if (level == 3) {
            framwindowsGame = 400;
            frameHeightGame = 520;
            frameTotalInfo = 24;
            colTotalInfoFrame = 24;
            FinGameMinu = 91;
        } else if (level == 4) {
            framwindowsGame = (20 * setc);
            frameHeightGame = (24 * setr);
            frameTotalInfo = setr;
            colTotalInfoFrame = setc;
            FinGameMinu = setm;
        }

        gameRowBox = frameTotalInfo;
        gameColBoxIntVal = colTotalInfoFrame;
        gameMinutes = FinGameMinu;

        setSize(framwindowsGame, frameHeightGame);
        setResizable(false);
        SubStartMin = FinGameMinu;
        p = this.getLocation();

        gameJbuttonsData = new JButton[frameTotalInfo][colTotalInfoFrame];
        colMin = new int[frameTotalInfo][colTotalInfoFrame];
        FrameColumnData = new int[frameTotalInfo][colTotalInfoFrame];
        mHand = new MouseHendeler();

        getContentPane().removeAll();
        panBxData.removeAll();

        minTm = new JTextField("" + FinGameMinu, 3);
        minTm.setEditable(false);
        minTm.setFont(new Font("DigtalFont.TTF", Font.BOLD, 25));
        minTm.setBackground(Color.BLACK);
        minTm.setForeground(Color.RED);
        minTm.setBorder(BorderFactory.createLoweredBevelBorder());
        tmTimer = new JTextField("000", 3);
        tmTimer.setEditable(false);
        tmTimer.setFont(new Font("DigtalFont.TTF", Font.BOLD, 25));
        tmTimer.setBackground(Color.BLACK);
        tmTimer.setForeground(Color.RED);
        tmTimer.setBorder(BorderFactory.createLoweredBevelBorder());
        ResetButton.setIcon(ic[11]);
        ResetButton.setBorder(BorderFactory.createLoweredBevelBorder());

        panelMeters.removeAll();
        panelMeters.setLayout(new BorderLayout());
        panelMeters.add(minTm, BorderLayout.WEST);
        panelMeters.add(ResetButton, BorderLayout.CENTER);
        panelMeters.add(tmTimer, BorderLayout.EAST);
        panelMeters.setBorder(BorderFactory.createLoweredBevelBorder());

        panBxData.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createLoweredBevelBorder()));
        panBxData.setPreferredSize(new Dimension(framwindowsGame, frameHeightGame));
        panBxData.setLayout(new GridLayout(0, colTotalInfoFrame));
        panBxData.addContainerListener(this);

        for (int i = 0; i < frameTotalInfo; i++) {
            for (int j = 0; j < colTotalInfoFrame; j++) {
                gameJbuttonsData[i][j] = new JButton("");

                //gameJbuttonsData[i][j].addActionListener(this);
                gameJbuttonsData[i][j].addMouseListener(mHand);

                panBxData.add(gameJbuttonsData[i][j]);

            }
        }
        ResetButton();

        panBxData.revalidate();
        panBxData.repaint();
        //getcontentpane().setOpaque(true);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().addContainerListener(this);
        //getContentPane().revalidate();
        getContentPane().repaint();
        getContentPane().add(panBxData, BorderLayout.CENTER);
        getContentPane().add(panelMeters, BorderLayout.NORTH);
        setVisible(true);
    }

    public void setMenu() {
        JMenuBar bar = new JMenuBar();

        JMenu game = new JMenu("GAME");

        JMenuItem menuitem = new JMenuItem("new game");
        final JCheckBoxMenuItem beginner = new JCheckBoxMenuItem("Begineer");
        final JCheckBoxMenuItem advaned = new JCheckBoxMenuItem("Intermediate");
        final JCheckBoxMenuItem expart = new JCheckBoxMenuItem("Expart");
        final JCheckBoxMenuItem custom = new JCheckBoxMenuItem("Custom");
        final JMenuItem exit = new JMenuItem("Exit");
        final JMenu help = new JMenu("Help");
        final JMenuItem helpitem = new JMenuItem("Help");

        ButtonGroup status = new ButtonGroup();

        menuitem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        settingPanelDesign(1, 0, 0, 0);
                    }
                });
        beginner.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        panBxData.removeAll();
                        ResetButton();
                        settingPanelDesign(1, 0, 0, 0);
                        panBxData.revalidate();
                        panBxData.repaint();
                        beginner.setSelected(true);
                        startLevels = 1;
                    }
                });
        advaned.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        panBxData.removeAll();
                        ResetButton();
                        settingPanelDesign(2, 0, 0, 0);
                        panBxData.revalidate();
                        panBxData.repaint();
                        advaned.setSelected(true);
                        startLevels = 2;
                    }
                });
        expart.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        panBxData.removeAll();
                        ResetButton();
                        settingPanelDesign(3, 0, 0, 0);
                        panBxData.revalidate();
                        panBxData.repaint();
                        expart.setSelected(true);
                        startLevels = 3;
                    }
                });

        custom.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        //panBxData.removeAll();
                        Customizetion cus = new Customizetion();
                        ResetButton();
                        panBxData.revalidate();
                        panBxData.repaint();

                        //minutesDatasweeper ob=new minutesDatasweeper(4);
                        custom.setSelected(true);
                        startLevels = 4;
                    }
                });

        exit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        helpitem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "instruction");

            }
        });

        setJMenuBar(bar);

        status.add(beginner);
        status.add(advaned);
        status.add(expart);
        status.add(custom);

        game.add(menuitem);
        game.addSeparator();
        game.add(beginner);
        game.add(advaned);
        game.add(expart);
        game.add(custom);
        game.addSeparator();
        game.add(exit);
        help.add(helpitem);

        bar.add(game);
        bar.add(help);

    }

    public void componentAdded(ContainerEvent ce) {
    }

    public void componentRemoved(ContainerEvent ce) {
    }

    public void actionPerformed(ActionEvent ae) {
    }

    class MouseHendeler extends MouseAdapter {

        public void mouseClicked(MouseEvent me) {
            if (valCheck == true) {
                for (int i = 0; i < frameTotalInfo; i++) {
                    for (int j = 0; j < colTotalInfoFrame; j++) {
                        if (me.getSource() == gameJbuttonsData[i][j]) {
                            myFanVar1 = i;
                            myFanVar2 = j;
                            i = frameTotalInfo;
                            break;
                        }
                    }
                }

                setminutesData();
                BoardArrangement();
                valCheck = false;

            }

            output(me);
            Winner();

            if (stTime == false) {
                sw.Start();
                stTime = true;
            }

        }
    }

    public void Winner() {
        int q = 0;
        for (int k = 0; k < frameTotalInfo; k++) {
            for (int l = 0; l < colTotalInfoFrame; l++) {
                if (FrameColumnData[k][l] == 'w') {
                    q = 1;
                }
            }
        }


        if (q == 0) {
            //panBxData.hide();
            for (int k = 0; k < frameTotalInfo; k++) {
                for (int l = 0; l < colTotalInfoFrame; l++) {
                    gameJbuttonsData[k][l].removeMouseListener(mHand);
                }
            }

            sw.stop();
            JOptionPane.showMessageDialog(this, "u R a lover");
        }
    }

    public void output(MouseEvent e) {
        for (int i = 0; i < frameTotalInfo; i++) {
            for (int j = 0; j < colTotalInfoFrame; j++) {

                if (e.getSource() == gameJbuttonsData[i][j]) {
                    if (e.isMetaDown() == false) {
                        if (gameJbuttonsData[i][j].getIcon() == ic[10]) {
                            if (SubStartMin < FinGameMinu) {
                                SubStartMin++;
                            }
                            minTm.setText("" + SubStartMin);
                        }

                        if (colMin[i][j] == -1) {
                            for (int k = 0; k < frameTotalInfo; k++) {
                                for (int l = 0; l < colTotalInfoFrame; l++) {
                                    if (colMin[k][l] == -1) {

                                        //gameJbuttonsData[k][l].setText("X");
                                        gameJbuttonsData[k][l].setIcon(ic[9]);
                                        //gameJbuttonsData[k][l].setBackground(Color.BLUE);
                                        //gameJbuttonsData[k][l].setFont(new Font("",Font.CENTER_BASELINE,8));
                                        gameJbuttonsData[k][l].removeMouseListener(mHand);
                                    }
                                    gameJbuttonsData[k][l].removeMouseListener(mHand);
                                }
                            }
                            sw.stop();
                            ResetButton.setIcon(ic[12]);
                            JOptionPane.showMessageDialog(null, "sorry you lost");
                        } else if (colMin[i][j] == 0) {
                            dfs(i, j);
                        } else {
                            gameJbuttonsData[i][j].setIcon(ic[colMin[i][j]]);
                            //gameJbuttonsData[i][j].setText(""+colMin[i][j]);
                            //gameJbuttonsData[i][j].setBackground(Color.pink);
                            //gameJbuttonsData[i][j].setFont(new Font("",Font.PLAIN,8));
                            FrameColumnData[i][j] = 'b';
                            //gameJbuttonsData[i][j].setBackground(Color.pink);
                            break;
                        }
                    } else {
                        if (SubStartMin != 0) {
                            if (gameJbuttonsData[i][j].getIcon() == null) {
                                SubStartMin--;
                                gameJbuttonsData[i][j].setIcon(ic[10]);
                            }
                            minTm.setText("" + SubStartMin);
                        }


                    }
                }

            }
        }

    }

    public void BoardArrangement() {
        int row, column;

        for (int i = 0; i < frameTotalInfo; i++) {
            for (int j = 0; j < colTotalInfoFrame; j++) {
                int value = 0;
                int R, C;
                row = i;
                column = j;
                if (colMin[row][column] != -1) {
                    for (int k = 0; k < 8; k++) {
                        R = row + r[k];
                        C = column + c[k];

                        if (R >= 0 && C >= 0 && R < frameTotalInfo && C < colTotalInfoFrame) {
                            if (colMin[R][C] == -1) {
                                value++;
                            }

                        }

                    }
                    colMin[row][column] = value;

                }
            }
        }
    }

    public void dfs(int row, int col) {

        int R, C;
        FrameColumnData[row][col] = 'b';

        gameJbuttonsData[row][col].setBackground(Color.GRAY);
        gameJbuttonsData[row][col].setIcon(ic[colMin[row][col]]);
        for (int i = 0; i < 8; i++) {
            R = row + r[i];
            C = col + c[i];
            if (R >= 0 && R < frameTotalInfo && C >= 0 && C < colTotalInfoFrame && FrameColumnData[R][C] == 'w') {
                if (colMin[R][C] == 0) {
                    dfs(R, C);
                } else {
                    gameJbuttonsData[R][C].setIcon(ic[colMin[R][C]]);
                    FrameColumnData[R][C] = 'b';

                }
            }


        }
    }

    public void setminutesData() {
        int row = 0, col = 0;
        Boolean[][] flag = new Boolean[frameTotalInfo][colTotalInfoFrame];


        for (int i = 0; i < frameTotalInfo; i++) {
            for (int j = 0; j < colTotalInfoFrame; j++) {
                flag[i][j] = true;
                colMin[i][j] = 0;
            }
        }

        flag[myFanVar1][myFanVar2] = false;
        FrameColumnData[myFanVar1][myFanVar2] = 'b';

        for (int i = 0; i < FinGameMinu; i++) {
            row = randomRValueTime.nextInt(frameTotalInfo);
            col = colRandomValueTime.nextInt(colTotalInfoFrame);

            if (flag[row][col] == true) {

                colMin[row][col] = -1;
                FrameColumnData[row][col] = 'b';
                flag[row][col] = false;
            } else {
                i--;
            }
        }
    }

    public void setic() {
        String name;

        for (int i = 0; i <= 8; i++) {
            name = i + ".gif";
            ic[i] = new ImageIcon(name);
        }
        ic[9] = new ImageIcon("minutesData.gif");
        ic[10] = new ImageIcon("flag.gif");
        ic[11] = new ImageIcon("new game.gif");
        ic[12] = new ImageIcon("crape.gif");
    }

    public class Stopwatch extends JFrame implements Runnable {

        long stTime;
        //final static java.text.SimpleDateFormat timerFormat = new java.text.SimpleDateFormat("mm : ss :SSS");
        //final JButton startStopButton= new JButton("Start/stop");
        Thread updater;
        boolean isRunning = false;
        long a = 0;
        Runnable displayUpdater = new Runnable() {

            public void run() {
                displayElapsedTime(a);
                a++;
            }
        };

        public void stop() {
            long elapsed = a;
            isRunning = false;
            try {
                updater.join();
            } catch (InterruptedException ie) {
            }
            displayElapsedTime(elapsed);
            a = 0;
        }

        private void displayElapsedTime(long elapsedTime) {

            if (elapsedTime >= 0 && elapsedTime < 9) {
                tmTimer.setText("00" + elapsedTime);
            } else if (elapsedTime > 9 && elapsedTime < 99) {
                tmTimer.setText("0" + elapsedTime);
            } else if (elapsedTime > 99 && elapsedTime < 999) {
                tmTimer.setText("" + elapsedTime);
            }
        }

        public void run() {
            try {
                while (isRunning) {
                    SwingUtilities.invokeAndWait(displayUpdater);
                    Thread.sleep(1000);
                }
            } catch (java.lang.reflect.InvocationTargetException ite) {
                ite.printStackTrace(System.err);
            } catch (InterruptedException ie) {
            }
        }

        public void Start() {
            stTime = System.currentTimeMillis();
            isRunning = true;
            updater = new Thread(this);
            updater.start();
        }
    }

    class Customizetion extends JFrame implements ActionListener {

        JTextField t1, t2, t3;
        JLabel lb1, lb2, lb3;
        JButton b1, b2;
        int cr, cc, cm, actionc = 0;

        Customizetion() {
            super("CUSTOMIZETION");
            setSize(180, 200);
            setResizable(false);
            setLocation(p);

            t1 = new JTextField();
            t2 = new JTextField();
            t3 = new JTextField();

            b1 = new JButton("OK");
            b2 = new JButton("Cencel");

            b1.addActionListener(this);
            b2.addActionListener(this);

            lb1 = new JLabel("Row");
            lb2 = new JLabel("Column");
            lb3 = new JLabel("minutesData");

            getContentPane().setLayout(new GridLayout(0, 2));

            getContentPane().add(lb1);
            getContentPane().add(t1);
            getContentPane().add(lb2);
            getContentPane().add(t2);
            getContentPane().add(lb3);
            getContentPane().add(t3);

            getContentPane().add(b1);
            getContentPane().add(b2);

            show();
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == b1) {
                try {
                    cr = Integer.parseInt(t1.getText());
                    cc = Integer.parseInt(t2.getText());
                    cm = Integer.parseInt(t3.getText());
                    //minutesDatasweeper ms=new minutesDatasweeper();
                    settingPanelDesign(4, row(), column(), minutesData());
                    dispose();
                } catch (Exception any) {
                    JOptionPane.showMessageDialog(this, "Wrong");
                    t1.setText("");
                    t2.setText("");
                    t3.setText("");
                }
                //Show_rcm();
            }

            if (e.getSource() == b2) {
                dispose();
            }
        }

        public int row() {
            if (cr > 30) {
                return 30;
            } else if (cr < 10) {
                return 10;
            } else {
                return cr;
            }
        }

        public int column() {
            if (cc > 30) {
                return 30;
            } else if (cc < 10) {
                return 10;
            } else {
                return cc;
            }
        }

        public int minutesData() {
            if (cm > ((row() - 1) * (column() - 1))) {
                return ((row() - 1) * (column() - 1));
            } else if (cm < 10) {
                return 10;
            } else {
                return cm;
            }
        }
    }
}
