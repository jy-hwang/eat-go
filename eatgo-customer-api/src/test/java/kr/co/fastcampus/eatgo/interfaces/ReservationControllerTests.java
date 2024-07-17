package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.ReservationService;
import kr.co.fastcampus.eatgo.domain.Reservation;
import kr.co.fastcampus.eatgo.util.LoggingTestWatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(LoggingTestWatcher.class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    @DisplayName("예약 create 테스트")
    public void create() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMDQsIm5pY2tuYW1lIjoiSm9obiJ9.eTVRsy7AkkWHQ-BLbrebFUiOetWAXw1aqT7ezsFm0y";
        Reservation mockReservation = Reservation.builder().id(12L).build();

        given(reservationService
                .addReservation(anyLong(), anyLong(), any(), any(), any(), anyInt())
        ).willReturn(mockReservation);

        mvc.perform(post("/restaurants/369/reservations")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"date\":\"2024-08-15\",\"time\":\"13:00\"," +
                                "\"partySize\":10}"))
                .andExpect(status().isCreated());

        long userId = 1004L;
        String name = "tester11";
        String date = "2024-08-15";
        String time = "13:00";
        int partySize = 10;

        verify(reservationService)
                .addReservation(369L, userId, name, date, time, partySize);
    }

}
