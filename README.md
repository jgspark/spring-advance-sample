
## API 목록

1. 특정 사용자가 해당 작품에 대한 평가를 하는 API

```
POST
http://localhost:8080/comment 

RequestBody:
{
    userId: number,
    contentsId : number,
    type: GOOD or BAD,
    comment : string
}
```

2. 좋아요 가 가장 많은 작품3개 와 싫어요가 가장 많은 작품 3개 API

- 좋아요 조회시 good , 싫어요 조회 시 bad 를 넘겨주세요.

```
GET
http://localhost:8080/top-contents

Parameter:
type=GOOD or BAD
```

3. 작품별로 언제, 어떤 사용자가 조회를 했는지 이력을 조회한 API

```
GET
http://localhost:8080/histories

Parameter:
page=number
size=number
```

4. 최근 1주일간 등록 사용 중 성인 작품 3개 이상 조회한 목록을 조회하는 API

```
GET
http://localhost:8080/histories/adult-users

Parameter:
page=number
size=number
```

5. 특정 작품을 유료로, 무료로 변경 할 수 있는 API

```
PATCH
http://localhost:8080/contents/id(number)

RequestBody:
{
    type: PAGAR or FREE,
    coin : number
}
```

6. 특정 사용자가 삭제 시 해당 사용자 정보와 사용자의 평가, 조회 이력 모두 삭제하는 API

```
DELETE
http://localhost:8080/user/id(number)
```

## API 테스트 방법

- test 코드를 진행을 하였습니다.

## 기타 참고 내용

### 기타 API

1. 전체 작품을 조회 하는 API

- 전체 목록을 조회하는 api 로 컨텐츠 전체 조회시 쿼리스트링에서 type 항목을 제거하고 api 조회
- 결제 방식에대한 조회가 필요시 PAGAR (유료)or FREE (무료)

```
GET
http://localhost:8080/contents

Parameter:
page=number
size=number
type= PAGAR or FREE
```

2. 컨텐츠 하나만 조회 API

- 단 하나의 작품의 데이터를 불러 오는 API 로 이 API 를 통해서 작품 조회 이력을 남기도록 로직을 개발 하였습니다.

```
GET
http://localhost:8080/contents/id(number)
```
