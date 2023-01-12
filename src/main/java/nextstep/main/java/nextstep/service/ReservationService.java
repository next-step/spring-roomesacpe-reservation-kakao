package nextstep.main.java.nextstep.service;

import nextstep.main.java.nextstep.domain.Reservation;
import nextstep.main.java.nextstep.domain.ReservationCreateRequestDto;
import nextstep.main.java.nextstep.domain.Theme;
import nextstep.main.java.nextstep.exception.exception.DuplicateReservationException;
import nextstep.main.java.nextstep.exception.exception.NoSuchReservationException;
import nextstep.main.java.nextstep.repository.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    public static final Theme DEFAULT_THEME = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);
    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    public Reservation save(ReservationCreateRequestDto request) {
        Reservation reservation = new Reservation(request.getDate(), request.getTime(), request.getName(), DEFAULT_THEME);
        if (repository.existsByDateAndTime(reservation.getDate(), reservation.getTime())) {
            throw new DuplicateReservationException();
        }
        return repository.save(reservation);
    }

    public Reservation findOneById(Long id) {
        return repository.findOne(id)
                .orElseThrow(NoSuchReservationException::new);
    }

    public void deleteOneById(Long id) {
        if (!repository.deleteOne(id)) {
            throw new NoSuchReservationException();
        }
    }
}