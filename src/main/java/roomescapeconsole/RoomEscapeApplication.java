package roomescapeconsole;

import roomescape.domain.Reservation;
import roomescape.domain.Theme;

import java.rmi.NoSuchObjectException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class RoomEscapeApplication {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";
    private static final Theme DUMMY_THEME = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Long reservationIdIndex = 0L;
        ReservationDAO reservationDAO = new ReservationDAO();
        reservationDAO.makeConnection();
        while (true) {
            System.out.println();
            System.out.println("### 명령어를 입력하세요. ###");
            System.out.println("- 예약하기: add {date},{time},{name} ex) add 2022-08-11,13:00,류성현");
            System.out.println("- 예약조회: find {id} ex) find 1");
            System.out.println("- 예약취소: delete {id} ex) delete 1");
            System.out.println("- 종료: quit");

            String input = scanner.nextLine();
            if (input.startsWith(ADD)) {
                String params = input.split(" ")[1];

                String date = params.split(",")[0];
                String time = params.split(",")[1];
                String name = params.split(",")[2];

                Reservation reservation = new Reservation(
                        ++reservationIdIndex,
                        LocalDate.parse(date),
                        LocalTime.parse(time + ":00"),
                        name,
                        DUMMY_THEME
                );

                reservationDAO.addReservation(reservation);
                System.out.println("예약이 등록되었습니다.");
                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
            }

            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                Reservation reservation;
                try {
                    reservation = reservationDAO.lookUpReservation(id);
                }catch(NoSuchObjectException e){
                    continue;
                }

                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
                System.out.println("예약 테마 이름: " + reservation.getTheme().getName());
                System.out.println("예약 테마 설명: " + reservation.getTheme().getDesc());
                System.out.println("예약 테마 가격: " + reservation.getTheme().getPrice());
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);
                reservationDAO.deleteReservation(id);
                System.out.println("예약이 취소되었습니다.");
            }

            if (input.equals(QUIT)) {
                reservationDAO.disconnection();
                break;
            }
        }
    }
}