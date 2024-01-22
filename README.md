# CrollingResort
SnowHub 리조트 크롤링 및 자동 업데이트

일정 주기마다 DB 자동 업데이트

--
[Update 방식]
JPA의 Dirty checking을 이용.
DB 정보와 크롤링 정보를 비교. 
slopeName,resort,Date를 통해서 정보를 find함. 그리고 크롤링한 정보를 내가 가져온 정보에 overwrite를 한 @Transaction에 내에서 끝냄.
트랜젝션이 마친 경우 JPA의 EntityManager가 변경사항을 감지하고, 알아서 반영을 함.

[Insert 방식]
DB에서 find를 못했다 -> 리조트에서 새로 추가했기 때문에 찾을 수가 없다는 의미.
find를 실패할 경우 NPE(NullPointException)이 발생. catch 블록에 save를 통해서 insert를 한다.

[Delete 방식]
2024-01-16 DB 슬로프가 A,B,C,D 그리고 2024-01-17 DB 슬로프가 A,B,C 라면? -> D슬로프는 사용하지 않으므로, 사용자에게 제공해서는 안된다.
그래서, findAll을 통해서 모든 슬로프를 가져오고, 크롤링을 통해서 가져온 정보와 비교를 함.
만약 있으면 -> True, 없으면 -> False
크롤링 close 전 마지막에 False인 부분만 삭제를 하면, 쓸데없는 슬로프 정보는 사라지게 됨.

-----
[추가]
만약 가져온 정보를 누적하여, 일정표처럼 만들고 싶다면, Delete 부분만 삭제를 하면 됨.

java.net.SocketException: Connection reset <- 이 오류는 무시
: 위 오류는 (1)Socket을 Closed한 상태로 Read/Write를 또는 (2) Read/Write 도중 갑자기 Socket을 끊어 버린 상태에 발생함. 그래서, 데이터의 송수신이 완전하게 이루어지지 않으면 문제를 발생함.
그러나, 여기서는 데이터의 수신을 완변하게 마친 후!, 발생하므로, 그냥 무시하면 됨. 
