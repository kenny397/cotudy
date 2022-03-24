## 📑 SSAFY 6기 공통 프로젝트 - Cotudy
#### Web RTC 기반 화상 스터디 플랫폼
![main-logo](https://user-images.githubusercontent.com/66551646/156126055-9cce6628-6b54-4957-aeb8-bd5d66658d6e.png)
<br>

### Cotudy 프로젝트팀을 소개합니다.
- [6시가 지나도 끝나지 않는다.] 서형준 😤
	- 부족하면 더 열심히 하겠습니다.



### OverView
코로나시대에서 사람 만나기도 힘들고 공부는 해야하는데 하기 싫고 다른 사람들은 어떻게 공부하는지 궁금하시지 않나요?
학습열정이 식어가고 있는 사람들을 위해 저희 코터디가 왔습니다. 코터디에서 공부해서 학습열정을 다시 불태워보세요!

### Cotudy 교훈
당신의 경쟁자들은 지금도 공부하고 있습니다!!!

## 코터디 서비스 화면

### Main화면
![image](https://user-images.githubusercontent.com/66551646/156129137-e7b23148-a2e3-47ae-b19f-2e2db71bcea1.png)

### 상대방 상세 프로필
![image](https://user-images.githubusercontent.com/66551646/156129191-685f4f57-2d5f-4eaa-aa6c-a007801c9101.png)

### 스터디룸 내부
![Cotudy - Chrome 2022-02-17 오후 1_32_25_LI](https://user-images.githubusercontent.com/66551646/156129593-e162ab01-6020-4749-9c35-31b6a68e56ea.jpg)

### 랭킹페이지
![image](https://user-images.githubusercontent.com/66551646/156129202-62ac6d57-d227-4637-bff1-717b9dad7d7c.png)

### 마이페이지
![image](https://user-images.githubusercontent.com/66551646/156129257-5c0fd2d6-dc99-466b-951d-57b92e2f9d44.png)

## 주요기능
<br>
서비스 설명 : 모두가 학습의지가 샘솟을 수 있는 스터디 플랫폼
<br>
주요 기능 :<br>
	- WebRTC를 통한 실시간 화상 플랫폼<br>
	- Teachable Machine pose을 통한 자세를 인식하여 학습기록<br>
	- 공부시간을 활용하여 사용자들의 랭킹시스템

<br>

## 개발 환경
#### 💻BackEnd
- Intellij
- Spring-Boot : 2.5.7
- Spring-Boot-JPA
- Spring Security
- Java 11
- mysql : 8.0.22
- Lombok

#### ✨Front-End
- Visual Studio Code
- Vue.js 3.0
- element plus

#### 🖱Web RTC
- ovenvidu 2.19.0
#### 🖱Pose Detection
- Teachable Machine
#### Infra
- AWS EC2 - Deploy Server
- docker
- jenkins
- docker-compose
- nginx

### 👨‍👩‍👧협업 툴
- GitLab
- Jira
- Nortion
- Mattermost
- Webex
- Discord

## 서비스 아키텍쳐
![아키텍처](https://user-images.githubusercontent.com/66551646/156126355-92283c63-67a6-405b-8190-9ab6190aa2b0.PNG)


### 기능 정의서
![기능 정의서](https://user-images.githubusercontent.com/66551646/156126403-4fa2d91d-f899-476f-87be-f46ef35ce05c.PNG)

### 화면 설계서
![figma](https://user-images.githubusercontent.com/66551646/156126315-8dbaed7f-84f9-42f2-9d24-122ce00bde35.PNG)

### REST API 문서화
![swagger](https://user-images.githubusercontent.com/66551646/156126386-19f1a6fe-eabd-4aa5-95b8-b03a5bd327f7.PNG)

### Git 컨벤션
```
feat : 새로운 기능 추가
fix : 버그 수정
docs : 문서 수정
chore : 그 외 자잘한 작업
test : 테스트 코드
build : 시스템 또는 외부 종속성에 영향을 미치는 변경사항 (npm, gulp, yarn 레벨)
ci : CI관련 설정
style : 코드 의미에 영향을 주지 않는 변경사항 (포맷, 세미콜론 누락, 공백 등)
refactor : 성능 개선
```

### Code 컨벤션
- 클래스 및 인터페이스 이름 : Pascal Case
- Method 및 변수 이름 camel Case
- 의존성 주입 : 생성자 주입
	- @RequiredArgsConstructor를 사용
- REST API
	- 응답 요청 모두 String(PK, StudyTime 제외)
	

### GitLab Flow 브랜치 전략

### E-R Diagram
![erd](https://user-images.githubusercontent.com/66551646/156126424-673752e8-36e1-4c19-9aca-e7f4f2f05200.PNG)

### EC2 포트 정리

|**PORT**|**이름**|
|:---:|:---:|
|443|HTTPS|
|80|HTTP - HTTPS로 리다이렉트(프론트 페이지지로 리다이렉트)|
|6443|Openvidu|
|3306|MySQL|
|8081|Jenkins|
|8080|Spring boot Docker Container|
|3000|Vue.js, NginX Docker Container|




### 팀원 역활
- 서형준 
	- 서비스 기획
	- 백엔드 방 관리 API 구현
	- 오픈비두 서버 배포
	- spring security, JWT JPA를 이용한 회원가입 로그인 기능 구현
	- 유저 관리 API 구현
	- jenkins, Docker, nginx를 이용한 CI/CD 구현 - Docker로 vue, spring boot를 생성하여 배포
	- nginx 리다이렌트 설정 및 백엔드 및 프론트엔드 url 분기 처리
	- HTTPS 설정 (SSl 인증서 적용)
    


