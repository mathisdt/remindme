package org.tiling.scheduling.examples;

import java.awt.*;
import java.util.*;
import java.util.Timer;

import javax.swing.*;

public class EggTimer {
    private final Timer timer = new Timer();
    private final int minutes;
    private final String message;
    private final Component parent;

    public EggTimer(int minutes, String message, Component parent) {
        this.minutes = minutes;
        this.message = message;
        this.parent = parent;
    }

    public void start() {
        timer.schedule(new TimerTask() {
            public void run() {
                JOptionPane.showMessageDialog(parent, message, "Eieruhr", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("firing event \"" + message + "\"");
                timer.cancel();
            }
        }, minutes * 60 * 1000);
    }

    public static void main(String[] args) {
        EggTimer eggTimer = new EggTimer(2, "Your egg is ready!", null);
        eggTimer.start();
    }

}