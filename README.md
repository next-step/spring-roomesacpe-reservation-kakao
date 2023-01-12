## 기능 목록

1. 예약 생성
   - 타임테이블에 알맞은 시간대에 대해서만 예약 생성이 가능하다 
     - 타임테이블에서 지원하지 않는 시간에 대해서는 예외 처리할 것 (ErrorCode : 400)
   - 같은 시간에 예약 있으면 예외 처리할 것 (ErrorCode : 409)
2. 예약 조회
   - id에 대응되는 예약이 없으면 예외 처리 할 것 (ErrorCode : 404)
3. 예약 취소
4. 예외 처리
