import javax.swing.*;

public class gameFrame extends JFrame {
    gameFrame(){

        this.setSize(700, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Clicking the Ball Game");
        gamePanel Panel = new gamePanel();
        this.add(Panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
//        Panel.animate();

    }

}
