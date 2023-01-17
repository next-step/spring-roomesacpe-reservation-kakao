package roomescape.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.domain.Reservation;
import roomescape.service.Reservation.ReservationService;

import javax.validation.Valid;

import static roomescape.utils.Messages.*;

@Controller
public class ConsoleReservationController {
    final ReservationService reservationService;
    private static final Logger logger =
            LoggerFactory.getLogger(ConsoleReservationController.class);

    public ConsoleReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public Reservation createReservation(@RequestBody @Valid Reservation reservation){
        logger.info(CREATE_REQUEST.getMessage() + reservation.toMessage());
        Reservation userReservation = reservationService.createReservation(reservation);
        logger.info(CREATE_RESPONSE.getMessage() + userReservation.toMessage());
        return new Reservation(userReservation.getId(), userReservation.getDate(), userReservation.getTime(),
                userReservation.getName(), userReservation.getThemeId());
    }

    public Reservation lookUpReservation(@PathVariable("id") Long reservationId) {
        logger.info(LOOKUP_REQUEST.getMessage() + reservationId);
        Reservation userReservation = reservationService.lookUpReservation(reservationId);
        logger.info(LOOKUP_RESPONSE.getMessage() + userReservation.toMessage());
        return userReservation;
    }

    public void deleteReservation(@PathVariable("id") Long deleteId) {
        logger.info(DELETE_REQUEST.getMessage() + deleteId);
        reservationService.deleteReservation(deleteId);
        logger.info(DELETE_RESPONSE.getMessage() + deleteId);
    }
}
