
public class Box {


    boolean revealed;
    boolean flagged;
    boolean mine;
    boolean wall;

    int number=0;

    public Box(){
        wall=false;
        revealed=false;
        flagged=false;
    }

    public void setWall() {
        this.wall = true;
    }

    public void ppNumber(){
        this.number++;
    }

    public void setMine() {
        this.mine = true;
    }

    public void setRevealed() {
        if (!revealed ){
            this.revealed = true;
            if (mine){
                Gui.loseGame();
            }
        }
    }

    public void setFlagged() {
        if (Gui.flag_number<1){
            if (flagged){
                this.flagged = false;
                Gui.flag_number++;
            }
        }else{
            if (flagged){
                this.flagged = false;
                Gui.flag_number++;
            }else {

                this.flagged = true;
                Gui.flag_number--;
            }
        }


    }
}
