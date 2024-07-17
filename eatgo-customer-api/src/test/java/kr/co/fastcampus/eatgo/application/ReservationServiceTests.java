package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.Reservation;
import kr.co.fastcampus.eatgo.domain.ReservationRepository;
import kr.co.fastcampus.eatgo.util.LoggingTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(LoggingTestWatcher.class)
@ExtendWith(MockitoExtension.class)
public class ReservationServiceTests {

    @InjectMocks
    private ReservationService reservationService;
    @Mock
    private ReservationRepository reservationRepository;

    @BeforeEach
    public void setUp() {
        reservationService = new ReservationService(reservationRepository);
    }

    @Test
    public void addReservation() {
        long userId = 1004L;
        long restaurantId = 369L;
        String nickname = "tester11";
        String date = "2024-08-15";
        String time = "13:00";
        int partySize = 10;

        given(reservationRepository.save(any())).will(invocation -> invocation.<Reservation>getArgument(0));

        Reservation reservation = reservationService.addReservation(
                restaurantId, userId, nickname, date, time, partySize
        );

        assertThat(reservation.getNickname(), is(nickname));

        verify(reservationRepository).save(any(Reservation.class));
    }
}