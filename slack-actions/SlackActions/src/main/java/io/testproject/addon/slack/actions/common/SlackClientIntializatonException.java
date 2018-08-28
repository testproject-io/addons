package io.testproject.addon.slack.actions.common;

public class SlackClientIntializatonException extends Exception {

    public  SlackClientIntializatonException(String message) {
        super(message);
    }

    public SlackClientIntializatonException(String message, Exception e) {
        super(message, e);
    }
}
