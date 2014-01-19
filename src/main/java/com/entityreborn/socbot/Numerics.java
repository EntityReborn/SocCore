/*
 * The MIT License
 *
 * Copyright 2013 Jason Unger <entityreborn@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.entityreborn.socbot;

import java.util.HashMap;

/**
 * The listings of numerics for the 2812 IRC protocol.
 * Taken from the python Twisted Words implementation,
 * and formatted for Java.
 * 
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class Numerics {
    public interface Numeric {
        public int getCode();
        public String getName();
        public boolean isBuiltin();
    }
    
    public static class CustomNumeric implements Numeric {
        public int code;
        public String name;

        public CustomNumeric(int code, String name) {
            this.code = code;
            this.name = name;
        }
        
        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public final boolean isBuiltin() {
            return false;
        }
    }

    public enum BuiltinNumeric implements Numeric {
        RPL_WELCOME(001),
        RPL_YOURHOST(002),
        RPL_CREATED(003),
        RPL_MYINFO(004),
        RPL_ISUPPORT(005),
        RPL_BOUNCE(010),
        RPL_USERHOST(302),
        RPL_ISON(303),
        RPL_AWAY(301),
        RPL_UNAWAY(305),
        RPL_NOWAWAY(306),
        RPL_WHOISUSER(311),
        RPL_WHOISSERVER(312),
        RPL_WHOISOPERATOR(313),
        RPL_WHOISIDLE(317),
        RPL_ENDOFWHOIS(318),
        RPL_WHOISCHANNELS(319),
        RPL_WHOWASUSER(314),
        RPL_ENDOFWHOWAS(369),
        RPL_LISTSTART(321),
        RPL_LIST(322),
        RPL_LISTEND(323),
        RPL_UNIQOPIS(325),
        RPL_CHANNELMODEIS(324),
        RPL_NOTOPIC(331),
        RPL_TOPIC(332),
        RPL_INVITING(341),
        RPL_SUMMONING(342),
        RPL_INVITELIST(346),
        RPL_ENDOFINVITELIST(347),
        RPL_EXCEPTLIST(348),
        RPL_ENDOFEXCEPTLIST(349),
        RPL_VERSION(351),
        RPL_WHOREPLY(352),
        RPL_ENDOFWHO(315),
        RPL_NAMREPLY(353),
        RPL_ENDOFNAMES(366),
        RPL_LINKS(364),
        RPL_ENDOFLINKS(365),
        RPL_BANLIST(367),
        RPL_ENDOFBANLIST(368),
        RPL_INFO(371),
        RPL_ENDOFINFO(374),
        RPL_MOTDSTART(375),
        RPL_MOTD(372),
        RPL_ENDOFMOTD(376),
        RPL_YOUREOPER(381),
        RPL_REHASHING(382),
        RPL_YOURESERVICE(383),
        RPL_TIME(391),
        RPL_USERSSTART(392),
        RPL_USERS(393),
        RPL_ENDOFUSERS(394),
        RPL_NOUSERS(395),
        RPL_TRACELINK(200),
        RPL_TRACECONNECTING(201),
        RPL_TRACEHANDSHAKE(202),
        RPL_TRACEUNKNOWN(203),
        RPL_TRACEOPERATOR(204),
        RPL_TRACEUSER(205),
        RPL_TRACESERVER(206),
        RPL_TRACESERVICE(207),
        RPL_TRACENEWTYPE(208),
        RPL_TRACECLASS(209),
        RPL_TRACERECONNECT(210),
        RPL_TRACELOG(261),
        RPL_TRACEEND(262),
        RPL_STATSLINKINFO(211),
        RPL_STATSCOMMANDS(212),
        RPL_ENDOFSTATS(219),
        RPL_STATSUPTIME(242),
        RPL_STATSOLINE(243),
        RPL_UMODEIS(221),
        RPL_SERVLIST(234),
        RPL_SERVLISTEND(235),
        RPL_LUSERCLIENT(251),
        RPL_LUSEROP(252),
        RPL_LUSERUNKNOWN(253),
        RPL_LUSERCHANNELS(254),
        RPL_LUSERME(255),
        RPL_ADMINME(256),
        RPL_ADMINLOC1(257),
        RPL_ADMINLOC2(258),
        RPL_ADMINEMAIL(259),
        RPL_TRYAGAIN(259),
        ERR_NOSUCHNICK(401),
        ERR_NOSUCHSERVER(402),
        ERR_NOSUCHCHANNEL(403),
        ERR_CANNOTSENDTOCHAN(404),
        ERR_TOOMANYCHANNELS(405),
        ERR_WASNOSUCHNICK(406),
        ERR_TOOMANYTARGETS(407),
        ERR_NOSUCHSERVICE(408),
        ERR_NOORIGIN(409),
        ERR_NORECIPIENT(411),
        ERR_NOTEXTTOSEND(412),
        ERR_NOTOPLEVEL(413),
        ERR_WILDTOPLEVEL(414),
        ERR_BADMASK(415),
        ERR_UNKNOWNCOMMAND(421),
        ERR_NOMOTD(422),
        ERR_NOADMININFO(423),
        ERR_FILEERROR(424),
        ERR_NONICKNAMEGIVEN(431),
        ERR_ERRONEUSNICKNAME(432),
        ERR_NICKNAMEINUSE(433),
        ERR_NICKCOLLISION(436),
        ERR_UNAVAILRESOURCE(437),
        ERR_USERNOTINCHANNEL(441),
        ERR_NOTONCHANNEL(442),
        ERR_USERONCHANNEL(443),
        ERR_NOLOGIN(444),
        ERR_SUMMONDISABLED(445),
        ERR_USERSDISABLED(446),
        ERR_NOTREGISTERED(451),
        ERR_NEEDMOREPARAMS(461),
        ERR_ALREADYREGISTRED(462),
        ERR_NOPERMFORHOST(463),
        ERR_PASSWDMISMATCH(464),
        ERR_YOUREBANNEDCREEP(465),
        ERR_YOUWILLBEBANNED(466),
        ERR_KEYSET(467),
        ERR_CHANNELISFULL(471),
        ERR_UNKNOWNMODE(472),
        ERR_INVITEONLYCHAN(473),
        ERR_BANNEDFROMCHAN(474),
        ERR_BADCHANNELKEY(475),
        ERR_BADCHANMASK(476),
        ERR_NOCHANMODES(477),
        ERR_BANLISTFULL(478),
        ERR_NOPRIVILEGES(481),
        ERR_CHANOPRIVSNEEDED(482),
        ERR_CANTKILLSERVER(483),
        ERR_RESTRICTED(484),
        ERR_UNIQOPPRIVSNEEDED(485),
        ERR_NOOPERHOST(491),
        ERR_NOSERVICEHOST(492),
        ERR_UMODEUNKNOWNFLAG(501),
        ERR_USERSDONTMATCH(502),
        UNKNOWN(-1);

        int code;
        
        private BuiltinNumeric(int code) {
            this.code = code;
        }
        
        public int getCode() {
            return code;
        }
        
        public String getName() {
            return name();
        }

        public boolean isBuiltin() {
            return true;
        }
    }
    
    static final private HashMap<Integer, BuiltinNumeric> replies;

    static {
        replies = new HashMap<Integer, BuiltinNumeric>();
        replies.put(001, BuiltinNumeric.RPL_WELCOME);
        replies.put(002, BuiltinNumeric.RPL_YOURHOST);
        replies.put(003, BuiltinNumeric.RPL_CREATED);
        replies.put(004, BuiltinNumeric.RPL_MYINFO);
        replies.put(005, BuiltinNumeric.RPL_ISUPPORT);
        replies.put(010, BuiltinNumeric.RPL_BOUNCE);
        replies.put(302, BuiltinNumeric.RPL_USERHOST);
        replies.put(303, BuiltinNumeric.RPL_ISON);
        replies.put(301, BuiltinNumeric.RPL_AWAY);
        replies.put(305, BuiltinNumeric.RPL_UNAWAY);
        replies.put(306, BuiltinNumeric.RPL_NOWAWAY);
        replies.put(311, BuiltinNumeric.RPL_WHOISUSER);
        replies.put(312, BuiltinNumeric.RPL_WHOISSERVER);
        replies.put(313, BuiltinNumeric.RPL_WHOISOPERATOR);
        replies.put(317, BuiltinNumeric.RPL_WHOISIDLE);
        replies.put(318, BuiltinNumeric.RPL_ENDOFWHOIS);
        replies.put(319, BuiltinNumeric.RPL_WHOISCHANNELS);
        replies.put(314, BuiltinNumeric.RPL_WHOWASUSER);
        replies.put(369, BuiltinNumeric.RPL_ENDOFWHOWAS);
        replies.put(321, BuiltinNumeric.RPL_LISTSTART);
        replies.put(322, BuiltinNumeric.RPL_LIST);
        replies.put(323, BuiltinNumeric.RPL_LISTEND);
        replies.put(325, BuiltinNumeric.RPL_UNIQOPIS);
        replies.put(324, BuiltinNumeric.RPL_CHANNELMODEIS);
        replies.put(331, BuiltinNumeric.RPL_NOTOPIC);
        replies.put(332, BuiltinNumeric.RPL_TOPIC);
        replies.put(341, BuiltinNumeric.RPL_INVITING);
        replies.put(342, BuiltinNumeric.RPL_SUMMONING);
        replies.put(346, BuiltinNumeric.RPL_INVITELIST);
        replies.put(347, BuiltinNumeric.RPL_ENDOFINVITELIST);
        replies.put(348, BuiltinNumeric.RPL_EXCEPTLIST);
        replies.put(349, BuiltinNumeric.RPL_ENDOFEXCEPTLIST);
        replies.put(351, BuiltinNumeric.RPL_VERSION);
        replies.put(352, BuiltinNumeric.RPL_WHOREPLY);
        replies.put(315, BuiltinNumeric.RPL_ENDOFWHO);
        replies.put(353, BuiltinNumeric.RPL_NAMREPLY);
        replies.put(366, BuiltinNumeric.RPL_ENDOFNAMES);
        replies.put(364, BuiltinNumeric.RPL_LINKS);
        replies.put(365, BuiltinNumeric.RPL_ENDOFLINKS);
        replies.put(367, BuiltinNumeric.RPL_BANLIST);
        replies.put(368, BuiltinNumeric.RPL_ENDOFBANLIST);
        replies.put(371, BuiltinNumeric.RPL_INFO);
        replies.put(374, BuiltinNumeric.RPL_ENDOFINFO);
        replies.put(375, BuiltinNumeric.RPL_MOTDSTART);
        replies.put(372, BuiltinNumeric.RPL_MOTD);
        replies.put(376, BuiltinNumeric.RPL_ENDOFMOTD);
        replies.put(381, BuiltinNumeric.RPL_YOUREOPER);
        replies.put(382, BuiltinNumeric.RPL_REHASHING);
        replies.put(383, BuiltinNumeric.RPL_YOURESERVICE);
        replies.put(391, BuiltinNumeric.RPL_TIME);
        replies.put(392, BuiltinNumeric.RPL_USERSSTART);
        replies.put(393, BuiltinNumeric.RPL_USERS);
        replies.put(394, BuiltinNumeric.RPL_ENDOFUSERS);
        replies.put(395, BuiltinNumeric.RPL_NOUSERS);
        replies.put(200, BuiltinNumeric.RPL_TRACELINK);
        replies.put(201, BuiltinNumeric.RPL_TRACECONNECTING);
        replies.put(202, BuiltinNumeric.RPL_TRACEHANDSHAKE);
        replies.put(203, BuiltinNumeric.RPL_TRACEUNKNOWN);
        replies.put(204, BuiltinNumeric.RPL_TRACEOPERATOR);
        replies.put(205, BuiltinNumeric.RPL_TRACEUSER);
        replies.put(206, BuiltinNumeric.RPL_TRACESERVER);
        replies.put(207, BuiltinNumeric.RPL_TRACESERVICE);
        replies.put(208, BuiltinNumeric.RPL_TRACENEWTYPE);
        replies.put(209, BuiltinNumeric.RPL_TRACECLASS);
        replies.put(210, BuiltinNumeric.RPL_TRACERECONNECT);
        replies.put(261, BuiltinNumeric.RPL_TRACELOG);
        replies.put(262, BuiltinNumeric.RPL_TRACEEND);
        replies.put(211, BuiltinNumeric.RPL_STATSLINKINFO);
        replies.put(212, BuiltinNumeric.RPL_STATSCOMMANDS);
        replies.put(219, BuiltinNumeric.RPL_ENDOFSTATS);
        replies.put(242, BuiltinNumeric.RPL_STATSUPTIME);
        replies.put(243, BuiltinNumeric.RPL_STATSOLINE);
        replies.put(221, BuiltinNumeric.RPL_UMODEIS);
        replies.put(234, BuiltinNumeric.RPL_SERVLIST);
        replies.put(235, BuiltinNumeric.RPL_SERVLISTEND);
        replies.put(251, BuiltinNumeric.RPL_LUSERCLIENT);
        replies.put(252, BuiltinNumeric.RPL_LUSEROP);
        replies.put(253, BuiltinNumeric.RPL_LUSERUNKNOWN);
        replies.put(254, BuiltinNumeric.RPL_LUSERCHANNELS);
        replies.put(255, BuiltinNumeric.RPL_LUSERME);
        replies.put(256, BuiltinNumeric.RPL_ADMINME);
        replies.put(257, BuiltinNumeric.RPL_ADMINLOC1);
        replies.put(258, BuiltinNumeric.RPL_ADMINLOC2);
        replies.put(259, BuiltinNumeric.RPL_ADMINEMAIL);
        replies.put(263, BuiltinNumeric.RPL_TRYAGAIN);
        replies.put(401, BuiltinNumeric.ERR_NOSUCHNICK);
        replies.put(402, BuiltinNumeric.ERR_NOSUCHSERVER);
        replies.put(403, BuiltinNumeric.ERR_NOSUCHCHANNEL);
        replies.put(404, BuiltinNumeric.ERR_CANNOTSENDTOCHAN);
        replies.put(405, BuiltinNumeric.ERR_TOOMANYCHANNELS);
        replies.put(406, BuiltinNumeric.ERR_WASNOSUCHNICK);
        replies.put(407, BuiltinNumeric.ERR_TOOMANYTARGETS);
        replies.put(408, BuiltinNumeric.ERR_NOSUCHSERVICE);
        replies.put(409, BuiltinNumeric.ERR_NOORIGIN);
        replies.put(411, BuiltinNumeric.ERR_NORECIPIENT);
        replies.put(412, BuiltinNumeric.ERR_NOTEXTTOSEND);
        replies.put(413, BuiltinNumeric.ERR_NOTOPLEVEL);
        replies.put(414, BuiltinNumeric.ERR_WILDTOPLEVEL);
        replies.put(415, BuiltinNumeric.ERR_BADMASK);
        replies.put(421, BuiltinNumeric.ERR_UNKNOWNCOMMAND);
        replies.put(422, BuiltinNumeric.ERR_NOMOTD);
        replies.put(423, BuiltinNumeric.ERR_NOADMININFO);
        replies.put(424, BuiltinNumeric.ERR_FILEERROR);
        replies.put(431, BuiltinNumeric.ERR_NONICKNAMEGIVEN);
        replies.put(432, BuiltinNumeric.ERR_ERRONEUSNICKNAME);
        replies.put(433, BuiltinNumeric.ERR_NICKNAMEINUSE);
        replies.put(436, BuiltinNumeric.ERR_NICKCOLLISION);
        replies.put(437, BuiltinNumeric.ERR_UNAVAILRESOURCE);
        replies.put(441, BuiltinNumeric.ERR_USERNOTINCHANNEL);
        replies.put(442, BuiltinNumeric.ERR_NOTONCHANNEL);
        replies.put(443, BuiltinNumeric.ERR_USERONCHANNEL);
        replies.put(444, BuiltinNumeric.ERR_NOLOGIN);
        replies.put(445, BuiltinNumeric.ERR_SUMMONDISABLED);
        replies.put(446, BuiltinNumeric.ERR_USERSDISABLED);
        replies.put(451, BuiltinNumeric.ERR_NOTREGISTERED);
        replies.put(461, BuiltinNumeric.ERR_NEEDMOREPARAMS);
        replies.put(462, BuiltinNumeric.ERR_ALREADYREGISTRED);
        replies.put(463, BuiltinNumeric.ERR_NOPERMFORHOST);
        replies.put(464, BuiltinNumeric.ERR_PASSWDMISMATCH);
        replies.put(465, BuiltinNumeric.ERR_YOUREBANNEDCREEP);
        replies.put(466, BuiltinNumeric.ERR_YOUWILLBEBANNED);
        replies.put(467, BuiltinNumeric.ERR_KEYSET);
        replies.put(471, BuiltinNumeric.ERR_CHANNELISFULL);
        replies.put(472, BuiltinNumeric.ERR_UNKNOWNMODE);
        replies.put(473, BuiltinNumeric.ERR_INVITEONLYCHAN);
        replies.put(474, BuiltinNumeric.ERR_BANNEDFROMCHAN);
        replies.put(475, BuiltinNumeric.ERR_BADCHANNELKEY);
        replies.put(476, BuiltinNumeric.ERR_BADCHANMASK);
        replies.put(477, BuiltinNumeric.ERR_NOCHANMODES);
        replies.put(478, BuiltinNumeric.ERR_BANLISTFULL);
        replies.put(481, BuiltinNumeric.ERR_NOPRIVILEGES);
        replies.put(482, BuiltinNumeric.ERR_CHANOPRIVSNEEDED);
        replies.put(483, BuiltinNumeric.ERR_CANTKILLSERVER);
        replies.put(484, BuiltinNumeric.ERR_RESTRICTED);
        replies.put(485, BuiltinNumeric.ERR_UNIQOPPRIVSNEEDED);
        replies.put(491, BuiltinNumeric.ERR_NOOPERHOST);
        replies.put(492, BuiltinNumeric.ERR_NOSERVICEHOST);
        replies.put(501, BuiltinNumeric.ERR_UMODEUNKNOWNFLAG);
        replies.put(502, BuiltinNumeric.ERR_USERSDONTMATCH);
    }

    public static BuiltinNumeric fromInt(int i) {
        if (replies.containsKey(i)) {
            return replies.get(i);
        }

        return BuiltinNumeric.UNKNOWN;
    }
    
    public static BuiltinNumeric fromString(String s) {
        int code;
        
        try {
            code = Integer.valueOf(s).intValue();
        } catch (NumberFormatException e) {
            return BuiltinNumeric.UNKNOWN;
        }
        
        if (!replies.containsKey(code)) {
            return BuiltinNumeric.UNKNOWN;
        }
        
        return replies.get(code);
    }
}
