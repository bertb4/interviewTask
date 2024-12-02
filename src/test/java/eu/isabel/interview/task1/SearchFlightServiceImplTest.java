package eu.isabel.interview.task1;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static eu.isabel.interview.task1.Airport.londonGatwick;
import static eu.isabel.interview.task1.Airport.parisOrly;
import static org.junit.jupiter.api.Assertions.*;
class SearchFlightServiceImplTest {


    @Test
    void fromTwoClientsTest() {
        final var now = Instant.now();
        final var today = LocalDate.now();

        final var flight1 = new Flight("1", "BA", parisOrly, londonGatwick, now, Duration.ofMinutes(100), 0, 89.5, "");
        final var flight2 = new Flight("2", "LH", parisOrly, londonGatwick, now, Duration.ofMinutes(105), 0, 96.5, "");
        final var flight3 = new Flight("3", "BA", parisOrly, londonGatwick, now, Duration.ofMinutes(140), 1, 234.0, "");
        final var flight4 = new Flight("4", "LH", parisOrly, londonGatwick, now, Duration.ofMinutes(210), 2, 55.5, "");

        final var client1 = SearchFlightClient.SearchFlightClientMock.init(List.of(flight3, flight1));
        final var client2 = SearchFlightClient.SearchFlightClientMock.init(List.of(flight2, flight4));

        final var service = SearchFlightServiceImpl.SearchFlightServiceFactoryImpl.fromTwoClients(client1, client2);
        final var result = service.search(parisOrly, londonGatwick, today);


        Assertions.assertThat(result.flights()).containsExactly(flight1, flight2, flight3, flight4);

    }

    @Test
    void fromTwoClientsDuplicateFlightIdsTest() {
        final var now = Instant.now();
        final var today = LocalDate.now();

        final var flight1 = new Flight("1", "BA", parisOrly, londonGatwick, now, Duration.ofMinutes(100), 0, 89.5, "");
        final var flight2 = new Flight("2", "LH", parisOrly, londonGatwick, now, Duration.ofMinutes(105), 0, 96.5, "");
        final var flight3 = new Flight("3", "BA", parisOrly, londonGatwick, now, Duration.ofMinutes(140), 1, 234.0, "");
        final var flight4 = new Flight("4", "LH", parisOrly, londonGatwick, now, Duration.ofMinutes(210), 2, 55.5, "");
        final var flight5 = new Flight("4", "LH", parisOrly, londonGatwick, now, Duration.ofMinutes(210), 2, 155.5, "");

        final var client1 = SearchFlightClient.SearchFlightClientMock.init(List.of(flight3, flight1));
        final var client2 = SearchFlightClient.SearchFlightClientMock.init(List.of(flight2, flight4, flight5));

        final var service = SearchFlightServiceImpl.SearchFlightServiceFactoryImpl.fromTwoClients(client1, client2);
        final var result = service.search(parisOrly, londonGatwick, today);


        Assertions.assertThat(result.flights()).containsExactly(flight1, flight2, flight3, flight4);

    }
}