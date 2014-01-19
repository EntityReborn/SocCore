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

    public enum Numeric {

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
        
        private Numeric(int code) {
            this.code = code;
        }
        
        public int getCode() {
            return code;
        }
    }
    
    static final private HashMap<Integer, Numeric> replies;

    static {
        replies = new HashMap<Integer, Numeric>();
        replies.put(001, Numeric.RPL_WELCOME);
        replies.put(002, Numeric.RPL_YOURHOST);
        replies.put(003, Numeric.RPL_CREATED);
        replies.put(004, Numeric.RPL_MYINFO);
        replies.put(005, Numeric.RPL_ISUPPORT);
        replies.put(010, Numeric.RPL_BOUNCE);
        replies.put(302, Numeric.RPL_USERHOST);
        replies.put(303, Numeric.RPL_ISON);
        replies.put(301, Numeric.RPL_AWAY);
        replies.put(305, Numeric.RPL_UNAWAY);
        replies.put(306, Numeric.RPL_NOWAWAY);
        replies.put(311, Numeric.RPL_WHOISUSER);
        replies.put(312, Numeric.RPL_WHOISSERVER);
        replies.put(313, Numeric.RPL_WHOISOPERATOR);
        replies.put(317, Numeric.RPL_WHOISIDLE);
        replies.put(318, Numeric.RPL_ENDOFWHOIS);
        replies.put(319, Numeric.RPL_WHOISCHANNELS);
        replies.put(314, Numeric.RPL_WHOWASUSER);
        replies.put(369, Numeric.RPL_ENDOFWHOWAS);
        replies.put(321, Numeric.RPL_LISTSTART);
        replies.put(322, Numeric.RPL_LIST);
        replies.put(323, Numeric.RPL_LISTEND);
        replies.put(325, Numeric.RPL_UNIQOPIS);
        replies.put(324, Numeric.RPL_CHANNELMODEIS);
        replies.put(331, Numeric.RPL_NOTOPIC);
        replies.put(332, Numeric.RPL_TOPIC);
        replies.put(341, Numeric.RPL_INVITING);
        replies.put(342, Numeric.RPL_SUMMONING);
        replies.put(346, Numeric.RPL_INVITELIST);
        replies.put(347, Numeric.RPL_ENDOFINVITELIST);
        replies.put(348, Numeric.RPL_EXCEPTLIST);
        replies.put(349, Numeric.RPL_ENDOFEXCEPTLIST);
        replies.put(351, Numeric.RPL_VERSION);
        replies.put(352, Numeric.RPL_WHOREPLY);
        replies.put(315, Numeric.RPL_ENDOFWHO);
        replies.put(353, Numeric.RPL_NAMREPLY);
        replies.put(366, Numeric.RPL_ENDOFNAMES);
        replies.put(364, Numeric.RPL_LINKS);
        replies.put(365, Numeric.RPL_ENDOFLINKS);
        replies.put(367, Numeric.RPL_BANLIST);
        replies.put(368, Numeric.RPL_ENDOFBANLIST);
        replies.put(371, Numeric.RPL_INFO);
        replies.put(374, Numeric.RPL_ENDOFINFO);
        replies.put(375, Numeric.RPL_MOTDSTART);
        replies.put(372, Numeric.RPL_MOTD);
        replies.put(376, Numeric.RPL_ENDOFMOTD);
        replies.put(381, Numeric.RPL_YOUREOPER);
        replies.put(382, Numeric.RPL_REHASHING);
        replies.put(383, Numeric.RPL_YOURESERVICE);
        replies.put(391, Numeric.RPL_TIME);
        replies.put(392, Numeric.RPL_USERSSTART);
        replies.put(393, Numeric.RPL_USERS);
        replies.put(394, Numeric.RPL_ENDOFUSERS);
        replies.put(395, Numeric.RPL_NOUSERS);
        replies.put(200, Numeric.RPL_TRACELINK);
        replies.put(201, Numeric.RPL_TRACECONNECTING);
        replies.put(202, Numeric.RPL_TRACEHANDSHAKE);
        replies.put(203, Numeric.RPL_TRACEUNKNOWN);
        replies.put(204, Numeric.RPL_TRACEOPERATOR);
        replies.put(205, Numeric.RPL_TRACEUSER);
        replies.put(206, Numeric.RPL_TRACESERVER);
        replies.put(207, Numeric.RPL_TRACESERVICE);
        replies.put(208, Numeric.RPL_TRACENEWTYPE);
        replies.put(209, Numeric.RPL_TRACECLASS);
        replies.put(210, Numeric.RPL_TRACERECONNECT);
        replies.put(261, Numeric.RPL_TRACELOG);
        replies.put(262, Numeric.RPL_TRACEEND);
        replies.put(211, Numeric.RPL_STATSLINKINFO);
        replies.put(212, Numeric.RPL_STATSCOMMANDS);
        replies.put(219, Numeric.RPL_ENDOFSTATS);
        replies.put(242, Numeric.RPL_STATSUPTIME);
        replies.put(243, Numeric.RPL_STATSOLINE);
        replies.put(221, Numeric.RPL_UMODEIS);
        replies.put(234, Numeric.RPL_SERVLIST);
        replies.put(235, Numeric.RPL_SERVLISTEND);
        replies.put(251, Numeric.RPL_LUSERCLIENT);
        replies.put(252, Numeric.RPL_LUSEROP);
        replies.put(253, Numeric.RPL_LUSERUNKNOWN);
        replies.put(254, Numeric.RPL_LUSERCHANNELS);
        replies.put(255, Numeric.RPL_LUSERME);
        replies.put(256, Numeric.RPL_ADMINME);
        replies.put(257, Numeric.RPL_ADMINLOC1);
        replies.put(258, Numeric.RPL_ADMINLOC2);
        replies.put(259, Numeric.RPL_ADMINEMAIL);
        replies.put(263, Numeric.RPL_TRYAGAIN);
        replies.put(401, Numeric.ERR_NOSUCHNICK);
        replies.put(402, Numeric.ERR_NOSUCHSERVER);
        replies.put(403, Numeric.ERR_NOSUCHCHANNEL);
        replies.put(404, Numeric.ERR_CANNOTSENDTOCHAN);
        replies.put(405, Numeric.ERR_TOOMANYCHANNELS);
        replies.put(406, Numeric.ERR_WASNOSUCHNICK);
        replies.put(407, Numeric.ERR_TOOMANYTARGETS);
        replies.put(408, Numeric.ERR_NOSUCHSERVICE);
        replies.put(409, Numeric.ERR_NOORIGIN);
        replies.put(411, Numeric.ERR_NORECIPIENT);
        replies.put(412, Numeric.ERR_NOTEXTTOSEND);
        replies.put(413, Numeric.ERR_NOTOPLEVEL);
        replies.put(414, Numeric.ERR_WILDTOPLEVEL);
        replies.put(415, Numeric.ERR_BADMASK);
        replies.put(421, Numeric.ERR_UNKNOWNCOMMAND);
        replies.put(422, Numeric.ERR_NOMOTD);
        replies.put(423, Numeric.ERR_NOADMININFO);
        replies.put(424, Numeric.ERR_FILEERROR);
        replies.put(431, Numeric.ERR_NONICKNAMEGIVEN);
        replies.put(432, Numeric.ERR_ERRONEUSNICKNAME);
        replies.put(433, Numeric.ERR_NICKNAMEINUSE);
        replies.put(436, Numeric.ERR_NICKCOLLISION);
        replies.put(437, Numeric.ERR_UNAVAILRESOURCE);
        replies.put(441, Numeric.ERR_USERNOTINCHANNEL);
        replies.put(442, Numeric.ERR_NOTONCHANNEL);
        replies.put(443, Numeric.ERR_USERONCHANNEL);
        replies.put(444, Numeric.ERR_NOLOGIN);
        replies.put(445, Numeric.ERR_SUMMONDISABLED);
        replies.put(446, Numeric.ERR_USERSDISABLED);
        replies.put(451, Numeric.ERR_NOTREGISTERED);
        replies.put(461, Numeric.ERR_NEEDMOREPARAMS);
        replies.put(462, Numeric.ERR_ALREADYREGISTRED);
        replies.put(463, Numeric.ERR_NOPERMFORHOST);
        replies.put(464, Numeric.ERR_PASSWDMISMATCH);
        replies.put(465, Numeric.ERR_YOUREBANNEDCREEP);
        replies.put(466, Numeric.ERR_YOUWILLBEBANNED);
        replies.put(467, Numeric.ERR_KEYSET);
        replies.put(471, Numeric.ERR_CHANNELISFULL);
        replies.put(472, Numeric.ERR_UNKNOWNMODE);
        replies.put(473, Numeric.ERR_INVITEONLYCHAN);
        replies.put(474, Numeric.ERR_BANNEDFROMCHAN);
        replies.put(475, Numeric.ERR_BADCHANNELKEY);
        replies.put(476, Numeric.ERR_BADCHANMASK);
        replies.put(477, Numeric.ERR_NOCHANMODES);
        replies.put(478, Numeric.ERR_BANLISTFULL);
        replies.put(481, Numeric.ERR_NOPRIVILEGES);
        replies.put(482, Numeric.ERR_CHANOPRIVSNEEDED);
        replies.put(483, Numeric.ERR_CANTKILLSERVER);
        replies.put(484, Numeric.ERR_RESTRICTED);
        replies.put(485, Numeric.ERR_UNIQOPPRIVSNEEDED);
        replies.put(491, Numeric.ERR_NOOPERHOST);
        replies.put(492, Numeric.ERR_NOSERVICEHOST);
        replies.put(501, Numeric.ERR_UMODEUNKNOWNFLAG);
        replies.put(502, Numeric.ERR_USERSDONTMATCH);
    }

    public static Numeric fromInt(int i) {
        if (replies.containsKey(i)) {
            return replies.get(i);
        }

        return Numeric.UNKNOWN;
    }
    
    public static Numeric fromString(String s) {
        int code;
        
        try {
            code = Integer.valueOf(s).intValue();
        } catch (NumberFormatException e) {
            return Numeric.UNKNOWN;
        }
        
        if (!replies.containsKey(code)) {
            return Numeric.UNKNOWN;
        }
        
        return replies.get(code);
    }
}
