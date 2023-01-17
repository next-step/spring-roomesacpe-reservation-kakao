## 예약 관련 기능

1. 예약 생성
   - 타임테이블에 알맞은 시간대에 대해서만 예약 생성이 가능하다 
     - 타임테이블에서 지원하지 않는 시간에 대해서는 예외 처리할 것 (ErrorCode : 400)
   - 같은 시간에 예약 있으면 예외 처리할 것 (ErrorCode : 409)
2. 예약 조회
   - id에 대응되는 예약이 없으면 예외 처리 할 것 (ErrorCode : 404)
3. 예약 취소
4. 예외 처리

## 테마 관련 기능

1. 테마 생성
2. 테마 조회
   - 테마 단건 조회 : 없으면 예외처리 (ErrorCode : 404)
   - 테마 목록 조회
3. 테마 수정
   - 수정하려는 테마가 존재하지 않으면 예외처리 (ErrorCode : 404)
   - 해당 테마에 대한 예약이 있으면 테마 수정 불가 (ErrorCode : 409)
4. 테마 삭제
   - 해당 테마에 대한 예약이 있으면 테마 삭제 불가 (ErrorCode : 409)

