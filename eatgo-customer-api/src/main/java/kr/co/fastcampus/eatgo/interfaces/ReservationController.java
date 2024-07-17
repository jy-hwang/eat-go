package kr.co.fastcampus.eatgo.interfaces;

import jakarta.validation.Valid;
import kr.co.fastcampus.eatgo.application.ReservationService;
import kr.co.fastcampus.eatgo.domain.Reservation;
import kr.co.fastcampus.eatgo.filters.CustomPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/restaurants/{restaurantId}/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @AuthenticationPrincipal CustomPrincipal principal,
            @PathVariable long restaurantId,
            @Valid @RequestBody Reservation resource
    ) throws URISyntaxException {
        long userId = principal.userId();
        String nickname = principal.nickname();
        String date = resource.getDate();
        String time = resource.getTime();
        int partySize = resource.getPartySize();

        Reservation reservation =
                reservationService.addReservation(
                        restaurantId, userId, nickname, date, time, partySize
                );

        String url = "/restaurant/" + restaurantId +
                "/reservations/" + reservation.getId();

        return ResponseEntity.created(new URI(url)).body("{}");
    }

}
