# 요구사항

## 1단계 - 웹 요청 / 응답 처리

### 기능 요구사항

- [x] 웹 요청 / 응답 처리로 입출력 추가
    - [x] 예약 하기
    - [x] 예약 조회
    - [x] 예약 취소
- [x] 예외 처리
    - [x] 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.
    - [x] 예약 생성 시 날짜 형식이 'YYYY-MM-DD'이 아닐 경우 예약을 생성할 수 없다.
    - [x] 예약 생성 시 날짜의 값이 유효하지 않을 경우 예약을 생성할 수 없다.
    - [x] 예약 생성 시 날짜는 오늘 이전일 경우 예약을 생성할 수 없다.
    - [x] 예약 생성 시 시간 형식이 'HH:MM'이 아닐 경우 예약을 생성할 수 없다.
    - [x] 예약 생성 시 시간의 값이 유효하지 않을 경우 예약을 생성할 수 없다.
    - [x] 예약 생성 시 시간이 30분 간격이 아닐 경우 에약을 생성할 수 없다.
    - [x] 예약 생성 시 시간이 영업 시간이 아닐 경우 예약을 생성할 수 없다. (예약 가능 시간: 11:00 - 20:30)
    - [x] 예약 생성 시 이름은 1글자 이상 20글자 이하가 아닐 경우 예약을 생성할 수 없다.
    - [x] 예약 생성 시 이름이 공백으로만 구성될 경우 예약을 생성할 수 없다.
    - [x] 예약 조회 시 예약이 없을 경우 상태 코드 404를 응답한다.
    - [x] 예약 삭제 시 예약이 없을 경우 상태 코드 404를 응답한다.

### 프로그래밍 요구사항

- 기존 콘솔 애플리케이션은 그대로 잘 동작해야한다.
- 콘솔 애플리케이션과 웹 애플리케이션의 중복 코드는 허용한다. (다음 단계에서 리팩터링 예정)

## 2단계 - 데이터베이스 적용

### 기능 요구사항

- [x] 콘솔 애플리케이션에 데이터베이스를 적용한다.
    - 직접 커넥션을 만들어서 데이터베이스에 접근한다.
- [ ] 웹 애플리케이션에 데이터베이스를 적용한다.
    - 스프링이 제공하는 기능을 활용하여 데이터베이스에 접근한다.

### 프로그래밍 요구사항

- 콘솔 애플리케이션과 웹 애플리케이션에서 `각각` 데이터베이스에 접근하는 로직을 구현한다.
- 콘솔 애플리케이션에서 데이터베이스에 접근 시 `JdbcTemplate`를 사용하지 않는다. 직접 Connection을 생성하여 데이터베이스에 접근한다.
- 웹 애플리케이션에서 데이터베이스 접근 시 `JdbcTemplate`를 사용한다.
- 콘솔 애플리케이션과 웹 애플리케이션의 중복 코드 제거해본다.

## 3단계 - 테마 확장 기능 구현

### 기능 요구사항

- 테마 관리 기능 추가
    - 모든 테마의 시간표는 동일함
- 요구사항에 없는 내용이 있다면 직접 요구사항을 추가해서 애플리케이션을 완성
    - 실제 애플리케이션이라고 생각했을 때 발생할 수 있는 예외 상황을 고려하고 처리한다.

### 프로그래밍 요구사항

- 테마를 관리하는 테이블을 추가한다.
- 콘솔 애플리케이션과 웹 애플리케이션의 로직의 중복을 제거한다.
    - 디비 접근을 담당하는 객체를 별도로 만들어 사용한다.
    - 비즈니스 로직을 담당하는 객체를 별도로 만들어 사용한다.

### 기능 구현 사항

- [ ] 테마 생성 하기
- [ ] 테마 목록 조회하기
- [ ] 테마 삭제하기
- [ ] 예약 시 존재하지 않는 테마일 경우 예약하지 않는 기능 추가

### 제약사항

- 테마 생성 하기
    - 테마의 이름은 1글자 이상 20글자 이하로 한다.
    - 테마 설명은 1글자 이상 255글자 이하로 한다.
    - 테마 가격은 0원 이상 1,000,000원 이하로 한다.
    - 테마 이름은 중복될 수 없다. 중복될 경우 419를 응답한다.
- 테마 목록 조회하기
    - 테마 조회 시 테마가 없을 경우 상태 코드 404를 응답한다.
- 테마 삭제하기
    - 테마 삭제 시 테마가 없을 경우 상태 코드 404를 응답한다.
    - 해당 테마의 예약이 존재할 경우 테마는 삭제할 수 없다. 이 경우 419를 응답한다.
