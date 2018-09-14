import java.awt.Container;
import java.awt.EventQueue;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
A borrowed class which displays images using JFrame.

Credits - The Java class is from Github user janbodnar
It can also be found on http://zetcode.com/java/displayimage/

 */

public class DisplayImage extends JFrame {
    private String imagePath;
    private boolean before;

    public DisplayImage(String imagePath, boolean before) {
        this.imagePath = imagePath;
        this.before = before;
        initUI();
    }

    private void initUI() {

        ImageIcon ii = loadImage();

        JLabel label = new JLabel(ii);

        createLayout(label);

        if(before){
            setTitle("Before");
        }else{
            setTitle("After");
        }

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private ImageIcon loadImage() {

        ImageIcon ii = new ImageIcon(imagePath);
        return ii;
    }

    private void createLayout(JComponent... arg) {

        Container pane = getContentPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);

        gl.setAutoCreateContainerGaps(true);

        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addComponent(arg[0])
        );

        gl.setVerticalGroup(gl.createParallelGroup()
                .addComponent(arg[0])
        );

        pack();
    }
}
