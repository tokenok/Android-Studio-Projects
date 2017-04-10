package dq3395yi.finalapp;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Josh_2 on 4/4/2017.
 */

public class DeerData implements Serializable {
    private int numDeer;
    private String timeOfSighting;
    private double distance;

    public static final int DOE = 1, BUCK = 2, FAWN = 3, UNKNOWN = 0, ANTLERLESS = 4;
    private int deerTypes;

    private int numPoints;
    private double buckSize;
    private double buckAge;

    public int getNumDeer() {
        return numDeer;
    }

    public String getTimeOfSighting() {
        return timeOfSighting;
    }

    public double getDistance() {
        return distance;
    }

    public int getDeerTypes() {
        return deerTypes;
    }

    public int getNumPoints() {
        return numPoints;
    }

    public double getBuckSize() {
        return buckSize;
    }

    public double getBuckAge() {
        return buckAge;
    }

    public void setNumDeer(int numDeer) {
        this.numDeer = numDeer;
    }

    public void setTimeOfSighting(String timeOfSighting) {
        this.timeOfSighting = timeOfSighting;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setDeerTypes(int deerTypes) {
        this.deerTypes = deerTypes;
    }

    public void setNumPoints(int numPoints) {
        this.numPoints = numPoints;
    }

    public void setBuckSize(double buckSize) {
        this.buckSize = buckSize;
    }

    public void setBuckAge(double buckAge) {
        this.buckAge = buckAge;
    }

    public DeerData() {  }

    public boolean saveToFile(Context context, String fileName) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static DeerData readFromFile(Context context, String fileName) {
        DeerData deerData = null;
        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            deerData = (DeerData) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return deerData;
    }
}