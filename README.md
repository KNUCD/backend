# [대구를 빛내는 SW해커톤] KNUCD
### 특별세션 - 주민참여 방법(수단)부족
## 1. 개요

<img src="https://user-images.githubusercontent.com/51109514/192118150-b63c6bfb-5cdf-4a08-bb60-edc709588ce4.PNG" width="50%">

> 핀플레인 : 핀을 꽂아 의견을 말해요

지도에 핀을 꽂아 민원을 작성하고 의견을 공유하는 간편 민원 관리 서비스 입니다.


## 2. 주제


기존 민원 관리 시스템에서 제한적인 민원 조회 방식 및 의견 공유 불가 문제를 해결함으로써 주민 참여를 장려하기 위한 “핀플레인” 서비스를 제안합니다.

## 3. 프로젝트 목적 및 배경
시민들은 기존의 오프라인 민원 관리 방식에 대하여 긴 민원 접수 시간, 불친절한 응대, 그리고 복잡한 절차 등 많은 불만을 가지고 있었습니다.
때문에 민원 관리 시스템의 디지털화는 큰 변화와 편리를 가져왔습니다.
예를 들어 사람들은 더 이상 동사무소에서 줄을 서서 민원을 접수할 필요가 없어졌고, 복잡한 민원 접수 절차는 간소화 되었는 것을 통해 알 수 있습니다.
이와 같은 실정에서 스마트시대의 주민참여 증대를 위한 여러가지 시도들이 계속해서 모색되어 왔습니다. [[프로젝트 배경 조사]](https://kindhearted-shoulder-b11.notion.site/acacdbdf10f442cebcf7c565ebe99c24)

그 중 대표적인 예로 대구 광역시에서 운영하는 게시판 형태의 민원관리 시스템 ‘두드리소’ 가 있습니다. 두드리소는 기존의 민원 접수 과정에서 복잡한 인증 절차를 생략하여
실제로 많은 시민들이 이용하고 있습니다. 하지만 현재 ‘두드리소’ 홈페이지는 몇 가지 개선점이 필요해 보입니다. 저희 팀이 발견한 문제는 아래와 같습니다.

#### 첫 번째로 지역의 문제를 주민들과 공유하는 방식이 효과적이지 않습니다.

민원 관리 창구에서는 지역의 문제를 함께 작성하는 공간인 만큼 지역주민들이 편리하고 직관적으로 민원을 조회할 수 있어야 합니다. 하지만 기존의 시스템은 일부 답변된 민원만을 공개하고 있고 별도의 카테고리가 존재하지 않아 특정 민원을 조회하는 것이 상당히 어렵습니다.

특히 위치 정보나 이미지가 포함된 민원의 경우 시각적으로 확인할 수 있는 수단이 제공되어야 하는데 현재 ‘두드리소’ 에서는 이를 확인할 수 있는 기능이 없습니다.

필터링의 경우에도 단순 텍스트 기반의 검색어만을 조회할 수 있기 때문에 민원들을 구분하는 기능이 부족하다고 볼 수 있습니다.

따라서 핀플레인에서는 기존의 민원 조회 방식을 개선한 효과적인 민원 관리 시스템을 제공하고자 합니다.


<br>

#### 두 번째로 민원에 대한 지역주민들의 의견을 반영할 수 있는 기능이 부재합니다.

‘두드리소’ 에서는 일부 답변된 민원만을 공개할 뿐더러 실제 민원에 대해서 지역 주민들이 얼마나 공감하고 있는지를 나타낼 수 있는 기능이 부재합니다.

민원의 경우 보통 개개인의 문제보다는 공공의 문제인 경우가 많습니다. 그렇기에 지역 주민들이 어떤 문제에 더 공감하고 있는지를 표출할 수단이 부족하다면 민원에 대한 주민 참여도가 떨어질 것입니다.

따라서 핀플레인에서는 주민들과 함께 지역 문제를 공유하고 해결책을 찾을 수 있는 소통의 장을 형성하고자 합니다.


## 4. 기능
![KakaoTalk_Photo_2022-09-25-06-09-34 017](https://user-images.githubusercontent.com/51109514/192118880-2e5950ca-9123-47ad-9495-f22d5969521b.png)

> 카테고리를 선택하고 지도에 핀을 찍어 민원 작성

현재 민원이 발생한 위치를 확인하고, 원하는 위치에 핀을 찍어 민원 발생지를 표시할 수 있습니다.


<div> <img src="https://user-images.githubusercontent.com/51109514/192118924-c59fcccc-051d-414b-a35d-54caaecc1182.png" width="45%">  
<img src="https://user-images.githubusercontent.com/51109514/192118991-9d467915-6b0a-4a04-8964-df932757e37b.png" width="45%"> </div>

<br>

> 카테고리 별 민원 신청 현황 조회

지도에서 민원 다수 발생지, 다수 발생 카테고리 등의 민원 신청 현황을 한눈에 확인할 수 있습니다.

> 공감하는 민원에 좋아요/화나요/놀라워요 공감 표현

다른 사람들이 제시한 민원을 확인하고 공감 및 해결책을 표현할 수 있습니다. 추후에 댓글 기능도 업데이트 될 예정입니다.


> SNS 형식의 민원 리스트 UI

기존의 딱딱한 게시판 형식의 리스트 대신 SNS 형식의 민원 리스트를 도입하여 젊은 세대의 접근성 및 참여도를 증대시킵니다.


![KakaoTalk_Photo_2022-09-25-06-09-34 015](https://user-images.githubusercontent.com/51109514/192118896-b4fbf132-cbb4-4d1a-879e-77f9ec2dcc93.png)



<br>

## 5. 활용 방안과 기대 효과
<img src="https://user-images.githubusercontent.com/51109514/192118220-5f6cdb30-78df-403a-97d8-4c5763f63f7b.png" width="100%">

위 사진은 핀플레인 서비스가 궁극적으로 지향하는 민원 시스템 구조입니다.
기존의 개개인과 지방 정부 간의 소통 구조에서 지역 시민 간의 의견 교류가 가능한 구조로 민원 관리 시스템을 변화시키고 싶습니다.

결과적으로 위에서 제시한 기능과 함께 아래와 같은 사회적 효과를 기대하고 있습니다.

1. 민원의 발생 지역을 시각적으로 확인할 수 있고 카테고리 별 필터링 기능을 활용하여 이전보다 편리하게 지역의 문제를 파악할 수 있을 것입니다.


2. 지역 시민 간 민원을 공유하고 의견을 나눔으로써 주민 참여 확대를 도모할 수 있고 현재 어떤 문제가 지역 주민들의 공감을 얻고 있는지 파악할 수 있을 것입니다.


3. 중복 민원 발생을 줄여 지자체에서 중복된 민원에 대해 일일이 답변 해야하는 수고로움을 덜 수 있습니다.



## 6. 프로젝트에 활용된 기술

#### [ 기술 스택 ]
![image](https://user-images.githubusercontent.com/67043922/192131064-72970f1e-3dd1-46ed-a775-3007f8af0459.png)

### 1. 배포 환경 및 CI/CD

핀플레인 서버는 현재 GCP(Goole Cloud Platform) 의 VM(Virtual Machine) 1대에서 동작중입니다.

런타임 환경은 도커 컨테이너를 통해 구성하였으며 VM 위 크게 Jenkins Container, Spring Boot Container, Redis Container 의 세 가지 컨테이너가 동작중입니다.

Redis Container 와 Spring Boot Container 는 하나의 bridge network 를 공유하고 있으며 외부 네트워크를 통해 Database 와 Cloud Storage 사이에서 필요한 데이터를 교환하고 있습니다.

<img src="https://user-images.githubusercontent.com/51109514/192118583-f322b590-1566-47b6-9c55-665bb30bbaeb.png" width="70%">


배포 과정의 경우 약 5일 간의 짧은 개발 기간에서 배포 과정을 단순화 하기 위해서 Jenkins 를 활용한 자동 배포 환경을 구축했습니다. 개발자가 GitHub 에 push 하면 Webhook 을 젠킨스에게 전달하여 빌드를 발생시키는 방식으로 구현했습니다.

시스템 아키텍쳐는 이미지는 아래와 같습니다.

<img src="https://user-images.githubusercontent.com/51109514/192118541-33b7d2e0-0444-4ddb-96cc-58d4fa9e2778.png" width="70%">


### 2. Redis 자료구조를 활용한 캐싱 로직 구현

서비스 기능 요구사항 중 캐싱이 필요한 부분이 두 가지 존재했습니다.

1. 한 민원 당 하나의 공감이 가능합니다.

데이터베이스로 부터 민원에 공감을 남긴 사용자 목록을 불러와 동등성 비교를 하는 방식도 있습니다. 하지만 다른 데이터보다 트래픽이 많을 것으로 예상되는 공감 기능에 캐싱 로직을 추가하여 효율적인 데이터 교환 방식을 구현했습니다.

Redis에서 제공하는 {키 : {필드 : 값}} 형태의 Hashes 자료구조를 활용했습니다. 사용자가 공감을 누르면 {민원 id : {사용자 id : 공감 type}} 데이터를 캐싱했습니다. 이후 민원 id를 통해 공감 정보를 불러올 때 데이터베이스가 아닌 캐시에서 정보를 더 빨리 찾을 수 있습니다. 해당 정보가 캐시에 존재하지 않는 경우에는 데이터베이스에서 데이터를 로딩한 후 사용합니다.

2. 소셜 로그인 과정에서 발급받은 토큰을 서버에 저장해야합니다.

소셜 로그인을 통해 발급받은 사용자 인증 토큰 및 리프레시 토큰을 서버에 저장해야만 추후에 사용자가 인증 토큰을 제출했을 때 사용자 식별이 가능합니다. 따라서 위와 같은 요구사항을 만족시키기 위해 두 가지 {키 : 값} 형태의 데이터를 캐싱합니다.

- 인증토큰 : 멤버 식별값

- 리프레시토큰 : 인증 토큰

각각의 데이터는 인증 토큰의 유효기간 동안 서버에 저장되며 캐싱이 만료된 뒤에는 사용자가 제출한 리프레시 토큰을 소셜 인증 서버에 제출해 인증토큰을 재발급받고 다시 서버에 캐싱을 수행합니다.

결과적으로 아래와 같은 클라이언트와 서버의 사용자 인증 구조를 가집니다.

<img src="https://user-images.githubusercontent.com/51109514/192118552-c1614080-8a04-48e3-81ff-d2e6a1168f97.png" width="70%">

### 3. Logging

기본 자바 서블릿 스펙인 Wrapper 클래스를 사용하여 HTTP Request 와 Response 를 추적하고 Spring 에서 제공하는 log4j 구현체인 logback 을 사용해서 실시간 로깅 환경을 구축했습니다.

Error 와 Info 두 단계로 로깅 레벨을 나누어 파일을 관리하고 팀원들과 라이브 코딩을 하면서 로깅 정보를 활용했습니다.

<img src="https://user-images.githubusercontent.com/51109514/192118641-f115c7e2-5ccd-410f-956f-5437c9d9a21b.png" width="50%">
<img src="https://user-images.githubusercontent.com/51109514/192118642-a3de2cc2-a702-4aba-97a7-616484c45226.png" width="50%">

## 7. 설정 파일

버전 정보는 **build.gradle** 에서 확인할 수 있습니다.
서버 스펙에 맞게 수정이 필요합니다.

**application.yml**

```yaml
gcp-bucket-name: ${bucket_name}
gcp-directory-name: ${storage_directory_root_path}
gcp-storage-url: https://storage.googleapis.com/

kakao:
  api-key: ${rest_api_key}
  admin-key: ${admin_key}
  redirect-url: ${redirect_url}
  
spring:
  datasource:
    username: ${db_username}
    password: ${db_password}
    driver-class-name: ${driver_class}
    url: ${db_endpoint}


  jpa:
    hibernate:
      ddl-auto: none
    properties:
      hibernate:
        show_sql: true
        format_sql: true

  cloud:
    gcp:
      storage:
        credentials:
          location: ${storage_credential}

  redis:
    host: ${redis_host}
    port: ${redis_port}

server:
  ssl:
    key-store: ${keystore_file}
    key-store-type: PKCS12
    key-store-password: ${keystore_passwd}

springdoc:
  swagger-ui:
    path: ${swagger_path}
    operations-sorter: method
  #  version: v1
  paths-to-match: /api/**
```

**Dockerfile**
```dockerfile
FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
VOLUME /tmp
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

<br>

### **build & execute**

원활한 실행환경을 위해서는 아래와 같은 연결 환경 설정이 필요합니다.
- redis connection
- gcp storage connection
- kakao login connection
- database connection

프로젝트 루트 경로에서 실행

**1. Docker 환경 x**

```shell
$ ./gradlew clean build -x test
$ cd build/libs
$ java -jar *.jar
```

**2. Docker 환경**
```shell
$ ./gradlew clean build -x test
$ docker build -t tag-name:1.0 .
$ docker run -p 8080:8080 -d —name=app-name tag-name:1.0
```


## 8. Contributor

### 디자인
| Name | Major | Email|
|---|---|---|
| 곽나영 | 디자인학과 | skdud9290@naver.com |

### 클라이언트
| Name | Major |Email|
|---|---|---|
| 최윤석  | 컴퓨터학부 |cdt9473@gmail.com|

### 서버
| Name | Major |Email|
|---|---|---|
| 박상현  | 컴퓨터학부 |sanghyun-dev@naver.com|
| 황아영  | 컴퓨터학부 |ayxxng73@gmail.com|


