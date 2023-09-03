import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class gamePanel extends JPanel implements ActionListener, KeyListener {
    final int WIDTH = 700;
    final int HEIGHT = 600;
    final int RADIUS = 20;
    Random random = new Random();
    int pX =  random.nextInt((WIDTH + 1)/2);
    int pY =  (HEIGHT + 1)/2;
    int SpeedX = random.nextInt(5) + 8;
    int SpeedY = random.nextInt(5) + 8;
    int GateH = 20;
    int GateW = 80;
    int GateY = 0;
    int GateX = random.nextInt(WIDTH+1-GateW);
    int userHW = 40;
    int userX = random.nextInt(WIDTH+1-userHW);
    int userY = HEIGHT-userHW+1;
    boolean gameRun;
    JButton startGame = new JButton("Start Game");
    Timer timer;
    JLabel sqr = new JLabel();
    int life = 3;
    int score = 0;


    gamePanel() {
        this.setLayout(null);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        startGame.addActionListener(this);
        this.addKeyListener(this);
        sqr.setBounds(userX,userY, userHW, userHW);
        startGame.setBounds(300,300, 100,20);
        sqr.setBackground(new Color(0x7CCB62));
        sqr.setOpaque(true);
        timer = new Timer(10,this);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(new Color(89, 153, 192));
        this.add(startGame);
        this.add(sqr);


    }



    public void movingBall(){
    if (gameRun) {
        pX = pX + SpeedX;
        pY = pY + SpeedY;
        if (touchLorR() || checkTouchedLR()) {
            SpeedX = -SpeedX;
        }
        if (touchTorB() || checkTouchedTB()) {
            SpeedY = -SpeedY;
        }
    }
    }

    private boolean touchTorB() {
        return pY <= 0 || (pY + RADIUS*2+1) >= HEIGHT;
    }

    private boolean touchLorR() {
        return pX <= 0 || (pX + RADIUS*2+1) >= WIDTH;
    }



    public boolean success(){
        return userY <= GateH && userX >= GateX && userX <= (GateX+GateW-userHW);
    }

    public  boolean checkTouchedTB(){
       return  (pY < userY && userY <= (pY+RADIUS*2) && userX <= (pX+RADIUS*2) && pX <= (userX+userHW))
                ||
                (pY > userY && pY <= (userY+userHW) && userX <= (pX+RADIUS*2) && pX <= (userX+userHW));

    }
    public boolean checkTouchedLR(){
        return (pX < userX && userX <= (pX+RADIUS*2) && userY <= (pY+RADIUS*2) && pY <= (userY+userHW))
                ||
                (pX > userX && pX <= (userX+userHW) && userY <= (pY+RADIUS*2) && pY <= (userY+userHW));
    }
    public boolean checkTouched(){
        return
                checkTouchedLR() || checkTouchedTB();
    }

    public void changeLife(){
        if(checkTouched() && life > 0){
            life -= 1;
        }
    }
    public boolean checkLife(){
        return life ==0;
    }

    @Override
    public void paint(Graphics g) {

            super.paint(g);
            Graphics2D graph = (Graphics2D) g;

        if (success()) {
            gameRun =false;
            graph.setPaint(new Color(0x1B4F09));
            graph.fillRect(GateX, GateY, GateW, GateH);
            graph.setPaint(new Color(0));
            graph.drawString("Success!!", 300, 300);
            score = life;
            graph.drawString("Score: " + score, 50, 100);
            graph.drawString("Life: " + life, 50, 50);

        } else if (checkLife()){
            gameRun =false;
            graph.setPaint(new Color(0x1B4F09));
            graph.fillRect(GateX, GateY, GateW, GateH);
            graph.setPaint(new Color(0));
            graph.drawString("Game Over!", 300, 300);
            graph.drawString("Score: " + score, 50, 100);
            graph.drawString("Life: " + life, 50, 50);

        } else {
            graph.setPaint(new Color(0xE7B2E2));
            graph.fillOval(pX, pY, RADIUS * 2, RADIUS * 2);
            graph.setPaint(new Color(0x1B4F09));
            graph.fillRect(GateX, GateY, GateW, GateH);
            graph.setPaint(new Color(0));
            graph.drawString("Score: " + score, 50, 100);
            graph.setPaint(new Color(0));
            graph.drawString("Life: " + life, 50, 50);
        }

    }





    @Override
    public void keyTyped(KeyEvent e) {
        if (gameRun){
            switch (e.getKeyChar()) {
                case 'a':
                    if (userX <= 0) {
                        sqr.setLocation(userX, userY);
                    } else {
                        userX -= 5;
                        sqr.setLocation(userX, userY);
                    }
                    break;
                case 'd':
                    if (userX >= WIDTH + 1 - userHW) {
                        sqr.setLocation(userX, userY);
                    } else {
                        userX += 5;
                        sqr.setLocation(userX, userY);
                    }
                    break;
                case 'w':
                    if (userY <= 0) {
                        sqr.setLocation(userX, userY);
                    } else {
                        userY -= 5;
                        sqr.setLocation(userX, userY);
                    }
                    break;
                case 's':
                    if (userY >= HEIGHT + 1 - userHW) {
                        sqr.setLocation(userX, userY);
                    } else {
                        userY += 5;
                        sqr.setLocation(userX, userY);
                    }
                    break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startGame) {
            // Start button is clicked, so start the game
            startGame.setEnabled(false);
            startGame.setVisible(false);
            if (!timer.isRunning()) {
                timer.start();
                gameRun=true;
            }
        } else if (e.getSource() == timer) {
            // Timer event, update ball position and repaint
            movingBall();
            changeLife();
            repaint();
        }

    }
}



