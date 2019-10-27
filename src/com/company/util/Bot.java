package com.company.util;

import com.company.program.RenownCheat;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

public class Bot {
    private static Robot robot_instance;

    public Bot() throws AWTException {
        robot_instance = new Robot();
    }

    public void click(Point mouse) throws AWTException, InterruptedException {
        robot_instance.mouseMove(mouse.x, mouse.y);
        robot_instance.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        TimeUnit.MILLISECONDS.sleep(50);
        robot_instance.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public void pressEscape() throws InterruptedException {
        robot_instance.keyPress(KeyEvent.VK_ESCAPE);
        TimeUnit.MILLISECONDS.sleep(50);
        robot_instance.keyRelease(KeyEvent.VK_ESCAPE);
    }


    public void move(Point mouse)
    {
        robot_instance.mouseMove(mouse.x, mouse.y);
    }

    public void awaitColorChange(Point colorPos, Color color, boolean fromColor) throws AWTException, InterruptedException {
        while (true) {
            Color realColor = getColor(colorPos);
            if (fromColor ^ color.equals(realColor)) return;
            TimeUnit.SECONDS.sleep(5);
        }
    }

    public boolean awaitColorChangeWithAction(Action action, Point colorPos, Color color, boolean fromColor) throws AWTException, InterruptedException {
        while (RenownCheat.isInMatch()) {
            Color realColor = getColor(colorPos);
            action.exec();
            if (fromColor ^ color.equals(realColor)) return true;
            TimeUnit.SECONDS.sleep(5);
        }
        return true;
    }

    public void antiAFK() throws InterruptedException {
        robot_instance.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        TimeUnit.MILLISECONDS.sleep(50);
        robot_instance.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }

    public void clickUntilColorChange(Point mouse, Point colorPos, Color color, int delay, boolean fromColor) throws AWTException, InterruptedException {
        while (true) {
            Color realColor = getColor(colorPos);
            if (fromColor ^ color.equals(realColor)) return;
            click(mouse);
            TimeUnit.SECONDS.sleep(delay);
        }
    }

    public Color getColor(Point pixel) throws AWTException {
        Rectangle screenRect = new Rectangle(pixel.x, pixel.y, pixel.x, pixel.y + 1);
        BufferedImage capture = robot_instance.createScreenCapture(screenRect);
        return new Color(capture.getRGB(0,0), false);
    }
}
