package kr.co.fastcampus.eatgo.application;

import jakarta.transaction.Transactional;
import kr.co.fastcampus.eatgo.domain.Reservation;
import kr.co.fastcampus.eatgo.domain.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ReservationService {

    ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation addReservation(long restaurantId, long userId, String nickname, String date, String time, int partySize) {

        Reservation reservation =
                Reservation.builder()
                        .reservationId(restaurantId)
                        .userId(userId)
                        .nickname(nickname)
                        .date(date)
                        .time(time)
                        .partySize(partySize)
                        .build();

        return reservationRepository.save(reservation);
    }
}
