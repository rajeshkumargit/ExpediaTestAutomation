package helper;

import java.util.logging.Logger;

public class FlightSearchData {
    static Logger logger = Logger.getLogger(FlightSearchData.class.getName());

    private String origin;
    private String originAirport;
    private String destinationAirport;
    private String destination;
    private String from;
    private String to;

    public String getDestination() {
        return destination;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }


    public String getOrigin() {
        return origin;
    }

    public String getOriginAirport() {
        return originAirport;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setOriginAirport(String originAirport) {
        this.originAirport = originAirport;
    }

    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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
