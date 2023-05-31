# PASS-Certificate-Service-Server
#### [PASS-Certificate-Service-Client ](https://github.com/gkdbssla97/PASS-Certificate-Service-Client)
- Client/Server로 구성된 프로젝트
- 요구사항, 기능사항에 따른 명세서 분석을 통해 API 연동 개발
- API 가이드 규격서 분석 및 설계
- 인증된 사용자 결과 데이터 조회

1. PASS 인증 요청
2. 전자서명 검증
3. 검증결과 요청

#### ERD
<img width="262" alt="image" src="https://github.com/gkdbssla97/PASS-Certificate-Service-Server/assets/55674664/b0a84f31-e0b4-4e85-9573-299aa88b848a">

#### FLOW
1. 회원정보 기입
2. 인증 대기 -> 핸드폰 알림호출
3. 인증번호 입력
4. PASS 인증 완료 후 검증결과 출력
<img width="675" alt="image" src="https://github.com/gkdbssla97/PASS-Certificate-Service-Server/assets/55674664/fb0f5deb-6b1c-4b73-abbd-dbbbf6710b1c">

#### RESULT
- 검증결과 요청을 통해 암호화된 결과값 출력
- RSA256 Encrypt Key
<img width="628" alt="image" src="https://github.com/gkdbssla97/PASS-Certificate-Service-Server/assets/55674664/edbe3a21-cde6-4b7f-bbfe-cb3156268e83">


