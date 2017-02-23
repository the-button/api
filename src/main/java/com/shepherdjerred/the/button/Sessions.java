package com.shepherdjerred.the.button;

import spark.Session;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Sessions {

    private static Set<Session> activeSessions = Collections.synchronizedSet(new HashSet<>());
    private static long NUMBER_OF_MILLIS_IN_30_SECONDS = 30000;

    public static void addToSessions(Session session) {
        activeSessions.add(session);
    }

    public static int getActiveSessionCount() {
        return activeSessions.size();
    }

    private static void trim() {
        for (Session session : activeSessions) {
            if (session.lastAccessedTime() > NUMBER_OF_MILLIS_IN_30_SECONDS) {
                activeSessions.remove(session);
            }
        }
    }

    public static void startTrimLoop() {
        new Thread(() -> {
            trim();
            try {
                Thread.sleep(NUMBER_OF_MILLIS_IN_30_SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
