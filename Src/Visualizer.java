import javax.swing.*;
import java.awt.*;

public class Visualizer extends JFrame{
    private String connections;
    JPanel panel;
    Visualizer(String connections){
        
        this.connections = connections;
        this.panel =  new nodePanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 600);
        this.setVisible(true);
    }
    private class nodePanel extends JPanel{
        nodePanel(){
        
        }
    }
}
