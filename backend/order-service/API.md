# order-service API 문서

## 기본 정보

- 서비스: `order-service`
- 기본 주소: `http://<host>:8080`
- 컨텍스트 패스: `/api`
- 최종 API 베이스 경로: `/api/order`

## 엔드포인트

### 주문 생성

- Method: `GET`
- Path: `/api/order`
- 설명: 사용자 ID를 받아 결제 서비스(`/api/pay`)를 호출한 뒤 주문 생성 결과 문자열을 반환합니다.

#### 요청 파라미터

| 이름 | 위치 | 타입 | 필수 | 설명 |
|---|---|---|---|---|
| `userId` | Query | string | Y | 주문 생성 대상 사용자 ID |

#### 요청 예시

```bash
curl -X GET "http://localhost:8080/api/order?userId=user-123"
```

#### 성공 응답

- Status: `200 OK`
- Body (text/plain):

```text
Order created for user user-123, pay status: <pay-service-response>
```

#### 실패 응답

- 결제 서비스 호출 실패 시(네트워크 오류, 타임아웃, 5xx 등):
  - Status: `500 Internal Server Error`
  - Body (Spring Boot 기본 오류 응답 JSON, 예시):

```json
{
  "timestamp": "2026-04-07T12:34:56.789+00:00",
  "status": 500,
  "error": "Internal Server Error",
  "path": "/api/order"
}
```

## 동작 시나리오

### 1) 일반 요청

1. `userId`를 전달해 `/api/order` 호출
2. `order-service`가 결제 서비스에 `GET /api/pay?userId=<userId>` 호출
3. 결제 응답을 포함한 주문 생성 결과 문자열 반환

### 2) 지연 시나리오

- `userId=slow`인 경우, `order-service` 내부에서 약 3초 대기 후 결제 서비스를 호출합니다.
- 성능/타임아웃 테스트 용도로 사용할 수 있습니다.

```bash
curl -X GET "http://localhost:8080/api/order?userId=slow"
```

## 설정값

`application.yml` 기준 주요 설정:

- `server.port=8080`
- `server.servlet.context-path=/api`
- `pay.service.base-url=${PAY_SERVICE_BASE_URL}`
- `pay.service.timeout=${PAY_SERVICE_TIMEOUT:2000}`

> 참고: 현재 코드에서는 `pay.service.timeout` 값이 프로퍼티로 정의되어 있지만, `WebClient` 타임아웃 설정에는 아직 직접 적용되어 있지 않습니다.
