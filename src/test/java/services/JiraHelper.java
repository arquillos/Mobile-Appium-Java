/**
 * JIRA helper service
 *
 * @author  Arquillos
 */
package services;

import org.apache.log4j.Logger;

public class JiraHelper {
    static final Logger logger = Logger.getLogger(JiraHelper.class);
    private String user = "";
    private String token = "";
    private String endpoint = "";
    private String jiraProjectID;

    private RestHelper rh ;

    public JiraHelper(String uRLBase, String endpoint, String jiraProjectID) {
        logger.debug("Getting JIRA credentials");
        this.user = getValueFor("JIRA_USER");
        this.token = getValueFor("JIRA_TOKEN");
        this.endpoint = endpoint;
        this.jiraProjectID = jiraProjectID;
        rh = new RestHelper(uRLBase);
    }

    /**
     * Create a new Jira ticket
     * @return True if the ticket has been created, false other wise
     */
    public boolean createTicket(String jiraTitle, String jiraDescription, String jiraTicketType) {
        logger.debug("Creating a JIRA ticket");

        if (user.isEmpty() || token.isEmpty()) {
            logger.error("No available credentials to create JIRA ticket. Exiting");
            return false;
        }

        if (validCredentials()) {
            String jSONBody = "{\"fields\":{\"project\":{\"key\":" + this.jiraProjectID +
                    "\"},\"summary\":" + jiraTitle +
                    "\",\"description\":" + jiraDescription +
                    "\",\"issuetype\":{\"name\":" + jiraTicketType + "}}}";
            rh.POSTRequest(this.endpoint, this.user, this.token, jSONBody);
            return true;
        } else {
            logger.error("The existing credentials (" + getCredentials() + ") are NOT valid JIRA credentials");
            return false;
        }
    }

    private String getValueFor(String variableName) {
        logger.debug("Getting environment variable " + variableName + " value...");

        try {
            String varValue = System.getenv(variableName);
            logger.debug("   value: ****" + varValue.substring(varValue.length() - 3));
            return varValue;
        } catch (NullPointerException e) {
            logger.error("No environment variable " + variableName + " is SET.");
            return "";
        }
    }

    private boolean validCredentials() {
        if (user.isEmpty()) {
            logger.error("Empty user value credentials!");
            return false;
        }

        if (token.isEmpty()) {
            logger.error("Empty token value credentials!");
            return false;
        }

        // TODO - Check if JIRA is available calling and endpoint
        return true;
    }

    // TODO - Should obfuscate the token part
    private String getCredentials() {
        if (!user.isEmpty() && !token.isEmpty()) {
            return user + '/' + token;
        } else {
            return "";
        }
    }
}