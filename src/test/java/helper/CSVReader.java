package helper;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class CSVReader {
    static Logger logger = Logger.getLogger(CSVReader.class.getName());
    private static final CSVFormat CSV_FORMAT =
            CSVFormat.RFC4180.withSkipHeaderRecord().withEscape('\\');
    @DataProvider(name = "hotelData")
    public Object[][] createHotelStayData() throws IOException {

        Object[][] arr = new Object[getDestinationStaysData().size()][1];

        for(int i =0; i < getDestinationStaysData().size();i++){
            arr[i][0] = getDestinationStaysData().get(i);
        }

        return arr;
    }

    @DataProvider(name = "flightData")
    public Object[][] createFlightsData() throws IOException {

        Object[][] arr = new Object[getFlightData().size()][1];

        for(int i =0; i < getFlightData().size();i++){
            arr[i][0] = getFlightData().get(i);
        }

        return arr;
    }

    private static CSVFormat formatForDelimiter(char delimiter) {
        CSVFormat format = CSV_FORMAT;
        if (delimiter != format.getDelimiter()) {
            format = format.withDelimiter(delimiter);
        }
        return format;
    }

    private List<StaysSearchData> getDestinationStaysData() throws IOException {
        String fileName = "stays.csv";
        List<StaysSearchData> hotelData = new ArrayList<StaysSearchData>();
        String localDir = System.getProperty("user.dir");
        String fileToRead=localDir+"\\src\\test\\resources\\data\\" + fileName;
        BufferedReader bReader = new BufferedReader(new FileReader(fileToRead));

        Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().withDelimiter('|').parse(bReader);
        for (CSVRecord record : records) {
            StaysSearchData stays = new StaysSearchData();
            stays.setDestination(record.get("destination"));
            stays.setDestinationSubtext(record.get("location_subtext"));
            stays.setFrom(record.get("from"));
            stays.setTo(record.get("to"));
            hotelData.add(stays);
        }
        return hotelData;
    }

    private List<FlightSearchData> getFlightData() throws IOException {
        String fileName = "flights.csv";
        List<FlightSearchData> flightData = new ArrayList<FlightSearchData>();
        String localDir = System.getProperty("user.dir");
        String fileToRead=localDir+"\\src\\test\\resources\\data\\" + fileName;
        BufferedReader bReader = new BufferedReader(new FileReader(fileToRead));

        Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().withDelimiter('|').parse(bReader);
        for (CSVRecord record : records) {
            FlightSearchData flights = new FlightSearchData();
            flights.setOrigin(record.get("origin"));
            flights.setOriginAirport(record.get("origin_airport"));
            flights.setDestination(record.get("destination"));
            flights.setDestinationAirport(record.get("destination_airport"));
            flights.setFrom(record.get("from"));
            flights.setTo(record.get("to"));
            flightData.add(flights);
        }
        return flightData;
    }

}