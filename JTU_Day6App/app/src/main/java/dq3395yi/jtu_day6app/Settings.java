package dq3395yi.jtu_day6app;

public class Settings {

    protected boolean aOn;
    protected boolean bOn;

    public Settings() {
        //CLASS_NAME = getClass().getName();
    }

    public boolean isAOn() {
        return aOn;
    }
    public void setAOn( boolean aValue) {
        aOn = aValue;
    }

    public boolean isBOn() {
        return bOn;
    }
    public void setBOn( boolean bValue) {
        bOn = bValue;
    }
}