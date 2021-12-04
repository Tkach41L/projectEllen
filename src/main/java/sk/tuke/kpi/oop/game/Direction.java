package sk.tuke.kpi.oop.game;

public enum Direction{
    NORTH(0,1),
    EAST(1,0),
    SOUTH(0,-1),
    WEST(-1,0),
    NORTHEAST(1,1),
    NORTHWEST(-1,1),
    SOUTHEAST(1,-1),
    SOUTHWEST(-1,-1),
    NONE(0,0);


    private int dx;
    private int dy;
    Direction(int dx, int dy){
        this.dx = dx;
        this.dy = dy;
    }
    public float getAngle(){
        if(Direction.NORTH == this){
            return 0;
        }
        else if(Direction.SOUTH == this){
            return 180;
        }
        else if(Direction.WEST == this){
            return 90;
        }
        else if(Direction.EAST == this){
            return 270;
        }
        else if(Direction.NORTHEAST == this) {
            return 315;
        }
        else if(Direction.SOUTHEAST == this) {
            return 225;
        }
        else if(Direction.SOUTHWEST == this) {
            return 135;
        }
        else if(Direction.NORTHWEST == this) {
            return 45;
        }
        return 0;
    }
    public static Direction fromAngle(float angle) {
        if (angle == 0) {
            return Direction.NORTH;
        }
        if (angle == 90) {
            return Direction.WEST;
        }
        if (angle == 180) {
            return Direction.SOUTH;
        }
        if (angle == 270) {
            return Direction.EAST;
        }
        if (angle == 315) {
            return Direction.NORTHEAST;
        }
        if (angle == 45) {
            return Direction.NORTHWEST;
        }
        if (angle == 225) {
            return Direction.SOUTHEAST;
        }
        if (angle == 135) {
            return Direction.SOUTHWEST;
        }
        return Direction.NONE;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    private int fix_twos(int num){
        if(num == 2 || num == -2){
            return num / 2;
        }
        return num;
    }

    public Direction combine(Direction other) {
        if(other != null) {
            int newdx = fix_twos(this.dx + other.dx);
            int newdy = fix_twos(this.dy + other.dy);
            for (Direction direction : Direction.values()) {
                if (newdx == direction.dx && newdy == direction.dy) {
                    return direction;
                }
            }
        }
        return Direction.NONE;
    }
}
