package eu.isabel.interview.task1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchFlightServiceImpl implements SearchFlightService {

    List<SearchFlightClient> clients;

    public SearchFlightServiceImpl(List<SearchFlightClient> clients) {
        this.clients = clients;
    }

    @Override
    public SearchResult search(Airport from, Airport to, LocalDate date) {

        ArrayList<Flight> results = new ArrayList<Flight>();

        for (SearchFlightClient client : clients) {
            results.addAll(client.search(from, to, date));
        }

        var filteredFlights = results.stream().collect(Collectors.toMap(x-> x.flightId(), x-> x, (flight1, flight2) -> flight1.unitPrice() > flight2.unitPrice() ? flight2 : flight1)).values();


        return new SearchResult(filteredFlights.stream().toList());
    }


    public final class SearchFlightServiceFactoryImpl {

        public static SearchFlightServiceImpl fromTwoClients(final SearchFlightClient client1,
                                                         final SearchFlightClient client2) {
            return new SearchFlightServiceImpl(List.of(client1, client2));
        }

        public static SearchFlightService fromClients(final List<SearchFlightClient> clients) {
            return new SearchFlightServiceImpl(clients);
        }
    }
}
