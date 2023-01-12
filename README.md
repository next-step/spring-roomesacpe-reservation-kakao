### 기능 요구사항
- [x] 웹 요청 / 응답 처리로 입출력 추가
  - [x] 예약 하기
  - [x] 예약 조회
  - [x] 예약 취소
- [x] 예외 처리
  - [x] 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.

### 기능 구현 목록
- [x] Spring 프로젝트 구축
- [x] 컨트롤러
  - [x] 예약 api
    - [x] 날짜와 시간이 동일한 경우 예외 처리
  - [x] 조회 api
  - [x] 취소 api

### 리팩토링 목록
- [x] Reservation -> ReservationResponseDto 생성 시 Reservation 을 인자로 받도록 할지 고민
- [x] http 요청 헤더나 바디 등 응답 형식이 일관성 있도록 처리
- [x] DAO 단에서 find 하는 경우에 Optional 로 return 하도록 수정
- [x] 중복 코드 제거 - mapper 사용 고려
- [x] 이전 단계에서 했던 것처럼 하기 위해 라인수, 뎁스 등 고려하기
- [x] 입력값이 유효한가에 대한 검증 로직이 들어가지 않았음. 검증된 자료구조를 사용하든 해서 검증할 수 있도록 변경할것.
  - [x] 시간표를 만들고 시간표에 없는 경우 예약하지 못하도록 예외처리 
  - [x] 파라미터를 잘못된 형식으로 호출하는 경우 테스트 보강 및 예외처리
  - [x] 등록되지 않은 id 로 조회나 삭제 요청 시 어떤 결과가 나와야 하는지에 대한 테스트 보강 및 예외처리
  - [x] 유효하지 않은 경우 예외 뱉고, 예외를 처리할 수 있도록 해주기
    - [x] 다양한 HttpStatus, 에러메시지가 생기므로 상수화할 수 있게 (enum)
    - [x] Exception 안에 메시지와 Status 를 넣을 수 있게
- [x] 서비스나 모델 등에서 단위테스트 할 방법 찾아보기 - 모델에 대해서 단위테스트 진행

### 3단계 기능 요구사항
- 테마 관리 기능 추가
  - 생성, 조회, 목록조회, 수정, 삭제 기능 구현
  - 모든 테마의 시간표는 동일함
  - 테마를 관리하는 테이블 추가
  - 기존 예약 관리 기능에 테마 관련 로직 수정
    - 예약과 관계있는 스케줄 혹은 테마는 수정 및 삭제 불가
- 요구사항에 없는 내용이 있다면 직접 요구사항을 추가해서 애플리케이션을 완성
  - 실제 애플리케이션이라고 생각했을 때 발생할 수 있는 예외 상황을 고려하고 처리한다.
- 콘솔 애플리케이션과 웹 애플리케이션의 로직 중복 제거
  - 디비 접근을 담당하는 객체를 별도로 만들어 사용
  - 비즈니스 로직을 담당하는 객체를 별도로 만들어 사용

### 3단계 기능 구현 목록
- [x] 테마 테이블을 추가하기 위해 DDL 쿼리 변경
  - [x] 관계형 DB 로 설정
- [x] Theme 도메인에 id 추가
  - [x] 생성시 유효성 검사 로직 추가
- [ ] 관리 기능 구현 (콘솔, 웹 컨트롤러, 서비스, DAO)
  - [x] 테마 생성 api 구현
  - [x] 테마 조회 api 구현
  - [x] 테마 목록조회 api 구현
  - [x] 테마 수정 api 구현
  - [ ] 테마 삭제 api 구현
    - [ ] 수정, 삭제 - 예약에 등록된 테마가 있지 않은지 등에 대한 예외상황 고려
- [ ] Reservation 도메인에 theme id 추가
  - [ ] 기존 예약 로직에서 로직 수정
    - [ ] 예약시 테마의 id 를 저장하도록 수정
    - [ ] 조회시 join 사용하여 가져오도록 수정
    - [ ] 관련된 예외처리
      - [ ] 예약시 테마 id 에 해당하는 테마가 없으면 예외 발생
- [ ] 예외처리
  - [ ] 수정 및 삭제 불가능한 상황 (id가 유효하지 않거나 예약된 테마를 지우려 한다거나 등)
  - [ ] 잘못된 입력값
  - [ ] 기존 테스트 케이스에서 테스트 전에 테마 등록 해주어야 함