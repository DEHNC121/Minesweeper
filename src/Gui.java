
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class Gui extends JFrame {
    int height;
    int width;

    int numberHeight;
    int numberWidth;

    int spacing;
    int size;
    int top;

    int mouseX;
    int mouseY;

    public Gui(int nH,int nW,int spacing,int size){
        top=40;
        this.height=top+nH*size;
        this.width=nW*size;
        this.numberHeight=nH;
        this.numberWidth=nW;
        this.spacing=spacing;
        this.size=size;

        this.setTitle("Minesweeper");
        this.setSize(width+14,height+37);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);

        Board board = new Board();
        this.setContentPane(board);

        Move move=new Move();
        this.addMouseMotionListener(move);

        Click click=new Click();
        this.addMouseListener(click);

    }

    public class Board extends JPanel{
        public  void paintComponent(Graphics graphics){
            graphics.setColor(Color.darkGray);
            graphics.fillRect(0,0,width+5,height+5);

            for (int i=0; i< numberWidth;i++){
                for (int j=0; j<numberHeight;j++){

                    graphics.setColor(Color.GRAY);
                    if (mouseX>=2*spacing+i*size && mouseX<=spacing+(i+1)*size
                    && mouseY>=(j+1)*size+top-spacing && mouseY<(j+2)*size+top-2*spacing){
                        graphics.setColor(Color.RED);

                    }
                    graphics.fillRect(spacing+i*size,spacing+j*size+top,size-2*spacing,size-2*spacing);

                }
            }

        }
    }

    public class Move implements MouseMotionListener{


        @Override
        public void mouseDragged(MouseEvent e) {
            //System.out.println("test2");

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseX=e.getX();
            mouseY=e.getY();

            System.out.println("x: "+mouseX+" y:"+mouseY);
        }
    }

    public class Click implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("click1");

        }

        @Override
        public void mousePressed(MouseEvent e) {
            System.out.println("click2");

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            System.out.println("click3");

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            System.out.println("click4");

        }

        @Override
        public void mouseExited(MouseEvent e) {
            System.out.println("click5");

        }
    }

}
