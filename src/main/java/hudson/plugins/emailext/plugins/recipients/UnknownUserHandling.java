package hudson.plugins.emailext.plugins.recipients;


import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import hudson.model.TaskListener;
import hudson.model.User;
import hudson.plugins.emailext.ExtendedEmailPublisherDescriptor;
import hudson.tasks.MailSender;
import jenkins.model.Jenkins;


public class UnknownUserHandling {
    private static final Logger LOGGER = Logger.getLogger(UnknownUserHandling.class.getName());

    /**
     * Name of system environment property
     */
    public static final String SEND_TO_UNKNOWN_USERS_PROPERTY =
            MailSender.class.getName() + ".SEND_TO_UNKNOWN_USERS";

    /**
     * Returns true if an email should be sent to the user, otherwise false.
     *
     * @param user      the unknown user to be handled
     * @param listener  run listener
     * @return true if email should be sent
     */
    public static boolean sendToUnknownUser(final User user, final TaskListener listener) {
        return true;
    }


    private static String nonull(String s) {
        return (s == null) ? "" : s;
    }

    protected static boolean sendToUnknownUserPatternMatch(String userAddress) {
        boolean result = false;
        try {
            Pattern p = Pattern.compile(nonull(
                    Jenkins.getActiveInstance().getDescriptorByType(ExtendedEmailPublisherDescriptor.class).getSendToUnknownUserPattern()));
            result = (p.pattern() != "") && p.matcher(userAddress).find();
        } catch (PatternSyntaxException e) {
            LOGGER.warning("Invalid system configuration for 'Extended E-mail Notification' (Email-ext plugin), Always send to unknown user match: "
                    + e.getMessage());
        }
        return result;
    }


    /**
     * Returns true if the system is configured to always send emails to unkown users.
     *
     * @return true if system property set to true.
     */
    protected static boolean getAlwaysSendToUnkownUsersSystemPropertyFlag() {
        /*
         * Need to be checked every time since the value can be changed at run-time.
         */
        return Boolean.getBoolean(SEND_TO_UNKNOWN_USERS_PROPERTY);
    }

/*
 if (SEND_TO_UNKNOWN_USERS) {
                                listener.getLogger().printf("Warning: %s is not a recognized user, but sending mail anyway%n", userAddress);
                            } else if (sendToUnknownUserPatternMatch(userAddress)) {
                                listener.getLogger().printf("%s is not a recognized user, but sending mail anyway due to 'Always send to unknown user match' - system configuration%n", userAddress);
                            } else {
                                listener.getLogger().printf("Not sending mail to unregistered user %s because your SCM"
                                        + " claimed this was associated with a user ID ‘", userAddress);
                                try {
                                    listener.hyperlink('/' + user.getUrl(), user.getDisplayName());
                                } catch (IOException ignored) {
                                }
                                listener.getLogger().printf("' which your security realm does not recognize; you may need" +
                                        " changes in your SCM plugin%n");
                                continue;
                            }
 *
 */

}
