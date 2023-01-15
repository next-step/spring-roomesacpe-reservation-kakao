package nextstep.reservation.repository.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import nextstep.reservation.entity.Reservation;

public class ReservationMemoryRepository implements ReservationRepository {

    private final Map<Long, Reservation> repository = new HashMap<>();
    AtomicLong idCounter = new AtomicLong();

    @Override
    public Long add(Reservation reservation) {
        reservation.setId(idCounter.incrementAndGet());
        repository.put(reservation.getId(), reservation);
        return reservation.getId();
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public boolean delete(Long id) {
        return repository.remove(id) != null;
    }

    @Override
    public Optional<Reservation> getReservationByDateAndTime(LocalDate date, LocalTime time) {
        return repository.values()
                .stream()
                .filter(reservation -> reservation.getDate().equals(date) &&
                                       reservation.getTime().equals(time))
                .findAny();
    }

    @Override
    public Optional<Reservation> getReservationByName(String name) {
        return repository.values()
                .stream()
                .filter(reservation -> reservation.getName().equals(name))
                .findAny();
    }


}
