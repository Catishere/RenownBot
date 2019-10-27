package com.company.program;

import com.company.util.Bot;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class RenownCheat {
    private Bot bot;

    private static boolean inMatch = false;

    public RenownCheat() throws AWTException {
        this.bot = new Bot();
    }

    private TimerTask quitMatchTask(){
        return new TimerTask() {
            public void run() {
                try {
                    quitMatch();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (AWTException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void quitMatch() throws InterruptedException, AWTException {
        bot.pressEscape();
        TimeUnit.SECONDS.sleep(1);
        Point exitToMenuPoint = new Point(1335, 279);
        Point exitAcceptPoint = new Point(900, 1002);
        bot.click(exitToMenuPoint);
        TimeUnit.SECONDS.sleep(1);
        bot.click(exitAcceptPoint);
        inMatch = false;
        System.out.println("Left match due to timeout!");
    }

    private void queuingStage() throws InterruptedException, AWTException {
        Point startClick = new Point(302, 435);
        Point searchingColorSample = new Point(565, 488);

        TimeUnit.SECONDS.sleep(5);
        bot.clickUntilColorChange(startClick, searchingColorSample, Color.WHITE, 1,false);
        System.out.println("Successfully queued!");
    }

    private void matchBeginStage() throws InterruptedException, AWTException {
        Color renownColor = new Color(252, 202, 2);
        Point renownPoint = new Point(1642, 71);
        bot.awaitColorChange(renownPoint, renownColor, true);
        inMatch = true;
        System.out.println("Match Found!");
    }

    private void selectionStage() throws InterruptedException, AWTException {
        TimeUnit.SECONDS.sleep(15);
        Point selectionPoint = new Point(257, 376);
        bot.click(selectionPoint);
        TimeUnit.SECONDS.sleep(1);
        bot.click(selectionPoint);
        TimeUnit.SECONDS.sleep(1);
        bot.click(selectionPoint);
        System.out.println("Operator Selected!");
        TimeUnit.SECONDS.sleep(60);
    }


    private void gameStage() throws InterruptedException, AWTException {
        Timer timer = new Timer();
        timer.schedule(quitMatchTask(), 300000);
        Point gameEndPoint = new Point(1500, 1000);
        Color gameEndColor = new Color(2, 2, 2);
        if (!bot.awaitColorChangeWithAction((this::inGameStatus), gameEndPoint,gameEndColor, false)){
            System.out.println("Kicked!");
            inMatch = false;
            recoverFromKick();
        }
        timer.cancel();
        System.out.println("Match Ended!");
    }

    private boolean inGameStatus() throws AWTException, InterruptedException {
        Point pingIconPoint = new Point(863, 992);
        bot.antiAFK();
        return bot.getColor(pingIconPoint).equals(Color.WHITE);
    }

    private void returnToLobby() throws InterruptedException, AWTException {
        if (!inMatch)
            return;
        inMatch = false;
        Point exitButtonPoint = new Point(1700, 970);
        Color renownColor = new Color(252, 202, 2);
        Point renownPoint = new Point(1642, 71);
        bot.click(exitButtonPoint);
        recoverFromKick();
        bot.awaitColorChange(renownPoint, renownColor, false);
        System.out.println("Exited to lobby!");
    }

    private void startSequence() throws AWTException, InterruptedException {
        queuingStage();
        matchBeginStage();
        selectionStage();
        gameStage();
        returnToLobby();
    }

    private void recoverFromKick() throws InterruptedException, AWTException {
        TimeUnit.SECONDS.sleep(1);
        Point kickAcceptPoint = new Point(900, 1002);
        bot.click(kickAcceptPoint);
    }

    public void loopSequence() throws InterruptedException, AWTException {
        while (true){
            startSequence();
            TimeUnit.SECONDS.sleep(2);
        }
    }

    public static boolean isInMatch() {
        return inMatch;
    }

    public Bot getBot() {
        return bot;
    }
}
