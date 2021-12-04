package sk.tuke.kpi.oop.game;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer extends AbstractActor{
    private boolean powered;
    public Computer(){
        this.powered = true;
        setAnimation(new Animation("sprites/computer.png", 80, 48, 0.2f, Animation.PlayMode.LOOP_PINGPONG));
    }
    void setPowered(boolean electricity){
        this.powered = electricity;
        if(powered){
            getAnimation().play();
        }
        else{
            getAnimation().pause();
        }
    }
    public int add(int a, int b){
        if(this.powered){
            return a + b;
        }
        return 0;
    }
    public int sub(int a, int b){
        if(!this.powered){
            return a - b;
        }
        return 0;
    }
    public float add(float a, float b){
        if(!this.powered){
            return a + b;
        }
        return 0;
    }
    public float sub(float a, float b){
        if(!this.powered){
            return a - b;
        }
        return 0;
    }
}
