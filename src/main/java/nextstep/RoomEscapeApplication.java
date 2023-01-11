package nextstep;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class RoomEscapeApplication {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";
    private static final ReservationJdbcRepository reservationRepository = new ReservationJdbcRepository();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

        // todo 리팩토링
        while (true) {
            System.out.println();
            System.out.println("### 명령어를 입력하세요. ###");
            System.out.println("- 예약하기: add {date},{time},{name} ex) add 2022-08-11,13:00,류성현");
            System.out.println("- 예약조회: find {id} ex) find 1");
            System.out.println("- 예약취소: delete {id} ex) delete 1");
            System.out.println("- 종료: quit");
            String input = scanner.nextLine();

            // 예약하기
            if (input.startsWith(ADD)) {
                String params = input.split(" ")[1];
                LocalDate date = LocalDate.parse(params.split(",")[0]);
                LocalTime time = LocalTime.parse(params.split(",")[1] + ":00");
                String name = params.split(",")[2];
                Reservation reservation = new Reservation(null, date, time, name, theme);
                Long reservationId = reservationRepository.save(reservation);

                if (reservationId == null) {
                    System.out.println("예약 생성에 실패하였습니다.");
                    continue;
                }
                System.out.println("예약이 등록되었습니다.");
                System.out.println("예약 번호: " + reservationId);
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
            }

            // 조회하기
            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];
                Long id = Long.parseLong(params.split(",")[0]);
                Reservation reservation = reservationRepository.findOneById(id);

                if (reservation == null) {
                    System.out.println("예약 조회에 실패하였습니다.");
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

            // 예약취소
            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];
                Long id = Long.parseLong(params.split(",")[0]);
                Integer delCount = reservationRepository.delete(id);
                System.out.println("cnt " + delCount);

                if (delCount == null) {
                    System.out.println("예약 취소에 실패하였습니다.");
                    continue;
                }
                if (delCount > 0) {
                    System.out.println("예약이 취소되었습니다.");
                }
            }

            // 종료
            if (input.equals(QUIT)) {
                break;
            }
        }
    }
}
