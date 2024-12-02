package eu.isabel.interview.task1;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Comparator;

/**
 * @param flightId unique id per flight
 * @param numberOfStops 0 for a direct flight
 * @param unitPrice in euros
 */
public record Flight(String flightId, String airline, Airport from, Airport to, Instant departureAt, Duration duration,
                     Integer numberOfStops, Double unitPrice, String redirectLink) {

    /**
     * TODO
     *    Order by number of stops (0, 1, 2, ...) and then by price.
     *    For example, sorting the following flights
     *    flight A: 2 stops, $ 100
     *    flight B: 0 stop , $ 120
     *    flight C: 1 stop , $  50
     *    flight D: 0 stop , $  80
     *    produces: flight D, flight B, flight C, flight A
     */
    public static final Comparator<Flight> bestOrdering = Comparator.comparingInt(Flight::numberOfStops).thenComparingDouble(Flight::unitPrice);

    public LocalDate departureDate() {
        return departureAt.atZone(from.timeZone()).toLocalDate();
    }

}
