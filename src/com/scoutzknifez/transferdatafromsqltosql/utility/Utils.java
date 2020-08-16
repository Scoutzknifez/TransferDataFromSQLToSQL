package com.scoutzknifez.transferdatafromsqltosql.utility;

import com.scoutzknifez.transferdatafromsqltosql.structures.TimeAtMoment;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    /**
     * Capitalize every letter after a space.
     * @param   sentence The sentence to capitalize.
     * @return  capitalized phrase
     */
    public static String capitalize(String sentence) {
        String[] split = sentence.replaceAll("_", " ").split(" ");
        List<String> out = new ArrayList<>();
        for (String s : split)
            out.add(s.length() > 0 ? s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase() : "");
        return String.join(" ", out);
    }

    public static double getRoundedInt(String string) {
        return getRoundedInt(getDouble(string));
    }

    public static double getDouble(String string) {
        try {
            return Double.parseDouble(string);
        } catch (Exception e) {
            Utils.log("Can not parse String into double.");
            return -1.0;
        }
    }

    public static int getInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (Exception e) {
            Utils.log( "Can not parse String into int.");
            return -1;
        }
    }

    /**
     * Sends a message out to console with time stamp of log execution
     *
     * NOTE: use %s to replace part of string with object
     *
     * @param message   message to display with replaceable characters for objects
     * @param objects   Objects to replace inside of the message string
     */
    public static void log(String message, Object... objects) {
        TimeAtMoment timeAtMoment = new TimeAtMoment(System.currentTimeMillis());

        System.out.println("[" + timeAtMoment + "] " + String.format(message, objects));
    }

    public static void log(Object object) {
        log(object.toString(), null);
    }

    public static int getRoundedInt(double input) {
        return (int) Math.round(input);
    }

    public static long getMillisFromEpoch(long epoch) {
        return epoch * Constants.MILLIS_IN_SECOND;
    }

    public static boolean hasInternetConnection() {
        boolean status = false;
        try (Socket socket = new Socket()) {
            InetSocketAddress address = new InetSocketAddress("www.google.com", 80);
            socket.connect(address, 1500);
            if (socket.isConnected())
                status = true;
        } catch (Exception e) {
            Utils.log("No internet connection available!");
        }
        return status;
    }
}
