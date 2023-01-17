package nextstep.console.view;

import java.util.Arrays;
import nextstep.dto.ReservationResponseDto;
import nextstep.dto.ThemeResponseDto;

public class OutputView {

    public void printCommand(){
        System.out.println();
        System.out.println("### 명령어를 입력하세요. ###");
        System.out.println("- 예약하기: add {date},{time},{name},{theme_id} ex) add 2022-08-11,13:00,류성현,1");
        System.out.println("- 예약조회: find {id} ex) find 1");
        System.out.println("- 예약취소: delete {id} ex) delete 1");
        System.out.println("- 테마생성: theme add {name},{desc},{price} ex) theme add 테마이름,설명,30000");
        System.out.println("- 테마조회: theme find {id} ex) theme find 1");
        System.out.println("- 테마삭제: theme delete {id} ex) theme delete 1");
        System.out.println("- 테마수정: theme edit {id},{name},{desc},{price} theme edit 1,테마이름2,설명2,40000");
        System.out.println("- 종료: quit");
    }

    public void printReservationResponseDto(ReservationResponseDto reservationResponseDTO) {
        System.out.println("예약 번호: " + reservationResponseDTO.getId());
        System.out.println("예약 날짜: " + reservationResponseDTO.getDate());
        System.out.println("예약 시간: " + reservationResponseDTO.getTime());
        System.out.println("예약자 이름: " + reservationResponseDTO.getName());
        System.out.println("예약 테마 이름: " + reservationResponseDTO.getThemeName());
        System.out.println("예약 테마 설명: " + reservationResponseDTO.getThemeDescription());
        System.out.println("예약 테마 가격: " + reservationResponseDTO.getThemePrice());
    }

    public void printThemeResponseDto(ThemeResponseDto themeResponseDto) {
        System.out.println("테마 번호: " + themeResponseDto.getId());
        System.out.println("테마 이름: " + themeResponseDto.getName());
        System.out.println("테마 설명: " + themeResponseDto.getDescription());
        System.out.println("테마 가격: " + themeResponseDto.getPrice());
    }

    public void printErrorMessage(Exception e, String[] messages) {
        System.out.println(e.getMessage());
        Arrays.stream(messages).forEach(System.out::println);
    }
}
