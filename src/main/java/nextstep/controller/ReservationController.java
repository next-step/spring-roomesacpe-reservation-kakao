package nextstep.controller;

import nextstep.domain.Reservation;
import nextstep.exception.DuplicateReservationException;
import nextstep.exception.NotFoundReservationException;
import nextstep.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Object> reserve(@RequestBody Reservation reservation){
        Reservation confirmedReservation = reservationService.reserve(reservation);
        URI locationUri = UriComponentsBuilder.fromPath("/reservations/{id}")
                .buildAndExpand(confirmedReservation.getId())
                .toUri();
        return ResponseEntity.created(locationUri).build();
    }

    @GetMapping("/{id}")
    public Reservation findReservation(@PathVariable long id){
        return reservationService.findReservation(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> cancelReservation(@PathVariable long id){
        boolean isDeleted = reservationService.cancelReservation(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(DuplicateReservationException exception) {
        return ResponseEntity.badRequest().body("날짜와 시간이 일치하는 예약이 이미 존재합니다");
    }

    @ExceptionHandler
    public ResponseEntity<Object> handle(NotFoundReservationException exception) {
        return ResponseEntity.notFound().build();
    }
}
