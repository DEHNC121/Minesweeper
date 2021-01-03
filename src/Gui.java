
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Date;
import java.util.Random;

import static java.lang.Math.round;


public class Gui extends JFrame {

    public static int smileType;
    Date startTimer;
    int timerEnd;

    boolean leftMouse;
    boolean leftMouseTry;

    int height;
    int width;

    int numberHeight;
    int numberWidth;

    int spacing;
    int size;
    int realSize;
    int top;

    int mouseX;
    int mouseY;
    Box[][] boxes;
    Random random=new Random();

    int[] try_click=new int[2];
    int minesNumber;
    public static int flag_number;

    boolean restart=false;
    boolean restartTry=false;
    boolean flag;


    private void restartGame() {
        timerEnd=-1;
        startTimer=new Date();
        smileType=0;
        for (int i=0; i< numberHeight+2;i++){
            boxes[i]=new Box[numberWidth+2];
            for (int j=0; j<numberWidth+2;j++){
                boxes[i][j]=new Box();
                if (i==0 || i==numberHeight+1 || j==0 || j==numberWidth+1){
                    boxes[i][j].setWall();
                }
            }
        }

        int t=0;
        while (t<minesNumber){
            int x=random.nextInt(numberWidth)+1;
            int y=random.nextInt(numberHeight)+1;
            if (!boxes[x][y].mine){
                t++;
                aroundMin(x,y);
            }
        }
    }

    public void aroundMin(int x,int y){
        boxes[x][y].setMine();

        for (int i=-1;i<2;i++){
            for (int j=-1;j<2;j++){
                boxes[x+i][y+j].ppNumber();
            }
        }
    }

    public void aroundOpen(int x,int y){
        if (flag){

            if (!boxes[x][y].revealed){
                boxes[x][y].setFlagged();
            }
            flag=false;
        }else if(!boxes[x][y].flagged){

            int t=x*numberHeight+y;
            if (!boxes[x][y].revealed){


                boxes[x][y].setRevealed();
                if (!boxes[x][y].wall && boxes[x][y].number==0){

                    for (int i=-1;i<2;i++){
                        for (int j=-1;j<2;j++){
                            aroundOpen(x+i,y+j);
                        }
                    }
                }
            }
        }
    }


    public Gui(int nH,int nW,int spacing,int size){
        this.top=50;
        this.height=top+nH*size;
        this.width=nW*size;
        this.numberHeight=nH;
        this.numberWidth=nW;
        this.spacing=spacing;
        this.size=size;
        this.try_click[0]=-1;
        this.try_click[1]=-1;
        this.realSize=size-2*spacing;
        this.boxes=new Box[numberHeight+2][numberWidth+2];
        minesNumber=numberHeight*numberWidth/5+1;
        flag_number=minesNumber;

        top=50;

        restartGame();


        this.setTitle("Minesweeper");


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);

        Board board = new Board();
        this.setContentPane(board);
        board.setPreferredSize(new Dimension(width, height));
        setContentPane (board);
        pack();

        Move move=new Move();
        this.addMouseMotionListener(move);

        Click click=new Click();
        this.addMouseListener(click);

    }

    public class Board extends JPanel{
        public  void paintComponent(Graphics graphics){
            graphics.setColor(Color.darkGray);
            graphics.fillRect(0,0,width,height);

            drawSmile(graphics);
            drawTimer(graphics);
            drawFlag(graphics);


            int revT=0;
            for (int i=0; i< numberWidth;i++){
                for (int j=0; j<numberHeight;j++){
                    int X=i+1;
                    int Y=j+1;
                    graphics.setColor(Color.GRAY);
                    if (boxes[X][Y].mine){
                        if (smileType==2){
                            graphics.setColor(Color.white);
                        }
                    }
                    if (boxes[X][Y].revealed){
                        graphics.setColor(Color.white);
                        if (boxes[X][Y].mine){
                            graphics.setColor(new Color(226, 38, 38));
                        }
                    }

                    if (mouseX>=spacing+i*size && mouseX<=(i+1)*size-spacing
                    && mouseY>=spacing+j*size+top && mouseY<=(j+1)*size-spacing+top){
                        graphics.setColor(Color.lightGray);
                    }

                    graphics.fillRect(spacing+i*size,spacing+j*size+top,realSize,realSize);

                    if (boxes[X][Y].flagged){

                        int x=spacing+i*size+realSize/6+5;
                        int y=spacing+j*size+top+realSize/6-5;
                        graphics.setColor(new Color(208, 3, 3));
                        graphics.fillRect(x+realSize/6,y+realSize/6,realSize/3,realSize/4);

                        graphics.setColor(Color.black);
                        graphics.fillRect(x+realSize/6,y+realSize/6,2,realSize/2);
                        graphics.fillRect(x+realSize/6-3,y+realSize/6+realSize/2,7,2);
                    }
                    if (boxes[X][Y].revealed){

                        if (smileType==0){

                            revT++;
                            if (numberHeight*numberWidth-minesNumber==revT){
                                smileType=1;
                            }
                        }
                        graphics.setFont(new Font("Tahoma",Font.BOLD,3*realSize/4));
                        graphics.setColor(Color.black);
                        if (boxes[X][Y].mine){
                            int x=spacing+i*size+realSize/6;
                            int y=spacing+j*size+top+realSize/6;
                            graphics.fillOval(x,y,2*realSize/3,2*realSize/3);

                            graphics.setColor(new Color(139,69,19));
                            graphics.fillOval(x+realSize/6,y+realSize/6,2*realSize/6,2*realSize/6);
                        }else {
                            if (boxes[X][Y].number!=0){
                                graphics.setColor(findColor(boxes[X][Y].number));
                                graphics.drawString(Integer.toString(boxes[X][Y].number),spacing+i*size+realSize/4,spacing+j*size+top+3*realSize/4);
                            }
                        }
                    }
                    if (smileType==2){
                        if (boxes[X][Y].mine){
                            graphics.setColor(Color.black);
                            int x=spacing+i*size+realSize/6;
                            int y=spacing+j*size+top+realSize/6;
                            graphics.fillOval(x,y,2*realSize/3,2*realSize/3);

                            graphics.setColor(new Color(139,69,19));
                            graphics.fillOval(x+realSize/6,y+realSize/6,2*realSize/6,2*realSize/6);
                        }
                    }
                }
            }
        }

        private void drawTimer(Graphics graphics) {
            graphics.setColor(Color.black);
            int sh=top-6;
            int sw=118;
            int x=width-sw-5;
            int y=spacing;
            int time;
            graphics.fillRect(x,y,sw,sh);

            if (smileType==2){

                graphics.setColor(Color.red);
            }else if(smileType==1) {

                graphics.setColor(Color.green);
            }else {
                    graphics.setColor(Color.white);
            }

            graphics.setFont(new Font("Tahoma",Font.PLAIN,48));
            if (smileType!=0 && timerEnd==-1){
                timerEnd=(int) ((new Date().getTime()-startTimer.getTime())/1000);;
            }
            if (smileType==0){
                time= (int) ((new Date().getTime()-startTimer.getTime())/1000);
            }else {

                time= timerEnd;
            }
            if (time>9999){
                time=9999;
            }
            if (time<10){
                graphics.drawString("000"+Integer.toString(time),x+5,y+sh-5);
            }else if (time<100){
                graphics.drawString("00"+Integer.toString(time),x+5,y+sh-5);
            }else if (time<1000){
                graphics.drawString("0"+Integer.toString(time),x+5,y+sh-5);
            }else {
                graphics.drawString(Integer.toString(time),x+5,y+sh-5);
            }
        }

        private void drawSmile(Graphics graphics) {

            int s;
            int x=width/2-(top-spacing)/2+2;
            int y=spacing;


            graphics.setColor(Color.GRAY);

            if (mouseX>=x && mouseX<=x+top-spacing
                    && mouseY>=y && mouseY<=y+top-spacing){
                graphics.setColor(Color.lightGray);
                restart=true;
            }else {
                restart=false;
            }

            graphics.fillRect(x,y,top-spacing,top-spacing);


            graphics.setColor(Color.yellow);
            graphics.fillOval(x+2,y+2,top-spacing-4,top-spacing-4);


            graphics.setColor(Color.black);
            graphics.fillRect(x+16,y+33,16,3);
            switch(smileType) {
                case 0:
                    break;
                case 1:
                    graphics.fillRect(x+13,y+31,3,3);
                    graphics.fillRect(x+32,y+31,3,3);
                    break;
                case 2:
                    graphics.fillRect(x+13,y+35,3,3);
                    graphics.fillRect(x+32,y+35,3,3);
                    break;
            }

            graphics.setColor(Color.black);
            s=5;

            graphics.fillOval(x+11,y+17,s,s);
            graphics.fillOval(x+31,y+17,s,s);


        }
    }

    private void drawFlag(Graphics graphics) {

        int x=spacing;
        int y=spacing;
        graphics.setColor(new Color(208, 3, 3));
        graphics.fillRect(x+realSize/6,y+realSize/6,realSize/3,realSize/4);

        graphics.setColor(Color.black);
        graphics.fillRect(x+realSize/6,y+realSize/6,2,realSize/2);
        graphics.fillRect(x+realSize/6-3,y+realSize/6+realSize/2,7,2);


        graphics.setFont(new Font("Tahoma",Font.PLAIN,48));
        graphics.drawString(Integer.toString(flag_number),x+60,y+40);
    }

    public static void loseGame() {
        smileType=2;
    }

    private Color findColor(int number) {
        switch(number) {
            case 1:
                return new Color(0, 0, 165);
            case 2:
                return  new Color(35, 113, 0);
            case 3:
                return new Color(191, 0, 0);
            case 4:
                return new Color(0, 102,128);
            case 5:
                return new Color(127, 24, 24);
            case 6:
                return new Color(72, 209,204);
            case 7:
                return Color.black;
            case 8:
                return Color.darkGray;
            default:
                return new Color(152, 209, 72);
        }
    }

    public int[] inBox(){
        int inX=(mouseX-mouseX%size)/size;
        int inY=mouseY-top;
        inY=(inY-inY%size)/size;

        int[] t=new  int[2];
        t[0]=-1;
        t[1]=-1;
        if (mouseX<spacing+inX*size || mouseX>(inX+1)*size-spacing ||
                mouseY<spacing+inY*size+top || mouseY>(inY+1)*size-spacing+top){
            return t;
        }
        t[0]=inX+1;
        t[1]=inY+1;

        return t;
    }

    public void mouseUpdate(MouseEvent e) {
        leftMouse= e.getButton() == e.BUTTON3;
        mouseX=e.getX()-7;
        mouseY=e.getY()-30;
    }


    public class Move implements MouseMotionListener{


        @Override
        public void mouseDragged(MouseEvent e) {
            //System.out.println("test2");

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseUpdate(e);

            //System.out.println("x: "+mouseX+" y:"+mouseY);
        }
    }

    public class Click implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            //System.out.println("click1");


        }


        @Override
        public void mousePressed(MouseEvent e) {
            mouseUpdate(e);
            try_click=inBox();
            //System.out.println("click2");
            if (restart){
                restartTry=true;
            }
            leftMouseTry= leftMouse;
            //

        }

        @Override
        public void mouseReleased(MouseEvent e) {

            //System.out.println("click3");
            mouseUpdate(e);
            if (restart && restartTry){
                restartGame();
            }
            if (leftMouseTry){
                flag=true;
                leftMouseTry=false;

            }
            if (smileType==0){

                int[] t=inBox();
                if (t[0]==try_click[0] &&t[1]==try_click[1] && try_click[0]!=-1){
                    aroundOpen(t[0],t[1]);

                }
                try_click[0]=-1;
                try_click[1]=-1;
            }


        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //System.out.println("click4");

        }

        @Override
        public void mouseExited(MouseEvent e) {
            mouseX=-1;
            mouseY=-1;

        }
    }

}
