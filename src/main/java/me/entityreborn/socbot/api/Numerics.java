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
package me.entityreborn.socbot.api;

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

        RPL_WELCOME,
        RPL_YOURHOST,
        RPL_CREATED,
        RPL_MYINFO,
        RPL_ISUPPORT,
        RPL_BOUNCE,
        RPL_USERHOST,
        RPL_ISON,
        RPL_AWAY,
        RPL_UNAWAY,
        RPL_NOWAWAY,
        RPL_WHOISUSER,
        RPL_WHOISSERVER,
        RPL_WHOISOPERATOR,
        RPL_WHOISIDLE,
        RPL_ENDOFWHOIS,
        RPL_WHOISCHANNELS,
        RPL_WHOWASUSER,
        RPL_ENDOFWHOWAS,
        RPL_LISTSTART,
        RPL_LIST,
        RPL_LISTEND,
        RPL_UNIQOPIS,
        RPL_CHANNELMODEIS,
        RPL_NOTOPIC,
        RPL_TOPIC,
        RPL_INVITING,
        RPL_SUMMONING,
        RPL_INVITELIST,
        RPL_ENDOFINVITELIST,
        RPL_EXCEPTLIST,
        RPL_ENDOFEXCEPTLIST,
        RPL_VERSION,
        RPL_WHOREPLY,
        RPL_ENDOFWHO,
        RPL_NAMREPLY,
        RPL_ENDOFNAMES,
        RPL_LINKS,
        RPL_ENDOFLINKS,
        RPL_BANLIST,
        RPL_ENDOFBANLIST,
        RPL_INFO,
        RPL_ENDOFINFO,
        RPL_MOTDSTART,
        RPL_MOTD,
        RPL_ENDOFMOTD,
        RPL_YOUREOPER,
        RPL_REHASHING,
        RPL_YOURESERVICE,
        RPL_TIME,
        RPL_USERSSTART,
        RPL_USERS,
        RPL_ENDOFUSERS,
        RPL_NOUSERS,
        RPL_TRACELINK,
        RPL_TRACECONNECTING,
        RPL_TRACEHANDSHAKE,
        RPL_TRACEUNKNOWN,
        RPL_TRACEOPERATOR,
        RPL_TRACEUSER,
        RPL_TRACESERVER,
        RPL_TRACESERVICE,
        RPL_TRACENEWTYPE,
        RPL_TRACECLASS,
        RPL_TRACERECONNECT,
        RPL_TRACELOG,
        RPL_TRACEEND,
        RPL_STATSLINKINFO,
        RPL_STATSCOMMANDS,
        RPL_ENDOFSTATS,
        RPL_STATSUPTIME,
        RPL_STATSOLINE,
        RPL_UMODEIS,
        RPL_SERVLIST,
        RPL_SERVLISTEND,
        RPL_LUSERCLIENT,
        RPL_LUSEROP,
        RPL_LUSERUNKNOWN,
        RPL_LUSERCHANNELS,
        RPL_LUSERME,
        RPL_ADMINME,
        RPL_ADMINLOC,
        RPL_ADMINEMAIL,
        RPL_TRYAGAIN,
        ERR_NOSUCHNICK,
        ERR_NOSUCHSERVER,
        ERR_NOSUCHCHANNEL,
        ERR_CANNOTSENDTOCHAN,
        ERR_TOOMANYCHANNELS,
        ERR_WASNOSUCHNICK,
        ERR_TOOMANYTARGETS,
        ERR_NOSUCHSERVICE,
        ERR_NOORIGIN,
        ERR_NORECIPIENT,
        ERR_NOTEXTTOSEND,
        ERR_NOTOPLEVEL,
        ERR_WILDTOPLEVEL,
        ERR_BADMASK,
        ERR_UNKNOWNCOMMAND,
        ERR_NOMOTD,
        ERR_NOADMININFO,
        ERR_FILEERROR,
        ERR_NONICKNAMEGIVEN,
        ERR_ERRONEUSNICKNAME,
        ERR_NICKNAMEINUSE,
        ERR_NICKCOLLISION,
        ERR_UNAVAILRESOURCE,
        ERR_USERNOTINCHANNEL,
        ERR_NOTONCHANNEL,
        ERR_USERONCHANNEL,
        ERR_NOLOGIN,
        ERR_SUMMONDISABLED,
        ERR_USERSDISABLED,
        ERR_NOTREGISTERED,
        ERR_NEEDMOREPARAMS,
        ERR_ALREADYREGISTRED,
        ERR_NOPERMFORHOST,
        ERR_PASSWDMISMATCH,
        ERR_YOUREBANNEDCREEP,
        ERR_YOUWILLBEBANNED,
        ERR_KEYSET,
        ERR_CHANNELISFULL,
        ERR_UNKNOWNMODE,
        ERR_INVITEONLYCHAN,
        ERR_BANNEDFROMCHAN,
        ERR_BADCHANNELKEY,
        ERR_BADCHANMASK,
        ERR_NOCHANMODES,
        ERR_BANLISTFULL,
        ERR_NOPRIVILEGES,
        ERR_CHANOPRIVSNEEDED,
        ERR_CANTKILLSERVER,
        ERR_RESTRICTED,
        ERR_UNIQOPPRIVSNEEDED,
        ERR_NOOPERHOST,
        ERR_NOSERVICEHOST,
        ERR_UMODEUNKNOWNFLAG,
        ERR_USERSDONTMATCH,
        UNKNOWN
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
        replies.put(257, Numeric.RPL_ADMINLOC);
        replies.put(258, Numeric.RPL_ADMINLOC);
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

    public Numeric fromInt(int i) {
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
