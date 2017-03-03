package dq3395yi.jtu_day6app;

import android.app.Application;

public class App extends Application{
    protected Settings settings;
    public Settings getSettings(){
        if (settings == null){
            settings = new Settings();
        }
        return settings;
    }

    public void setSettings(Settings settings){
        this.settings = settings;
    }
}