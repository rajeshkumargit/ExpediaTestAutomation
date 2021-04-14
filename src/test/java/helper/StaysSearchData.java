package helper;

import java.util.logging.Logger;

public class StaysSearchData {
    static Logger logger = Logger.getLogger(StaysSearchData.class.getName());

    private String destination;
    private String destinationSubtext;
    private String from;
    private String to;

    public String getDestination() {
        return destination;
    }

    public String getDestinationSubText() {
        return destinationSubtext;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDestinationSubtext(String destinationSubtext) {
        this.destinationSubtext = destinationSubtext;
    }


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
