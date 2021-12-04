package sk.tuke.kpi.oop.game;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements EnergyConsumer,Switchable{
    private boolean isPowered = false;
    private boolean isOn = false;
    private final Animation lightOn = new Animation("sprites/light_on.png", 16, 16, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
    private final Animation lightOff = new Animation("sprites/light_off.png", 16, 16, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
    public Light(){
        setAnimation(lightOff);
    }
    public void toggle(){
        if(isOn()){
            turnOff();
        }
        else{
            turnOn();
        }
        updateAnimation();
    }

    private void updateAnimation() {
        if (isOn && isPowered) {
            setAnimation(lightOn);
        } else {
            setAnimation(lightOff);
        }
    }
    public boolean setElectricityFlow(boolean isLightOn){
        return this.isPowered = isLightOn;
    }

    public void turnOn(){
        isOn = true;
        updateAnimation();
    }

    public void turnOff(){
        isOn = false;
        updateAnimation();
    }

    @Override
    public boolean isOn(){
        return this.isOn;
    }

    @Override
    public void setPowered(boolean electricity) {
        this.isPowered = electricity;
        updateAnimation();
    }
}


