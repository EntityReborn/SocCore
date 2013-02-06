/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.entityreborn.socbot.api;

import java.util.Set;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public interface Target {

    public class Util {

        public static boolean isUser(String name, SocBot bot) {
            String chantypes = bot.getServerInfo().getSupport("CHANTYPES");

            if (chantypes != null) {
                if (chantypes.contains(name.subSequence(0, 1))) {
                    return false;
                }
            } else {
                // The server hasn't sent RPL_ISUPPORT yet. Lets use a sane default.
                if (name.startsWith("#")) {
                    return false;
                }
            }

            return true;
        }
    }

    public String getName();

    public SocBot getBot();

    public void sendMsg(String message);

    public void sendNotice(String message);

    public void sendCTCP(String type, String message);

    public void sendCTCPReply(String type, String message);
    
    public void addModes(String modes);
    
    public void removeModes(String modes);
    
    public String getModes();
}
