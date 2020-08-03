# 공통 프로젝트 - SUB 2

## Getting Started

### 환경

* java : zulu-8

  1. spring boot
  2. maven

* javascript

  1. nodejs
  2. yarn
  3. Vue-cli : ``` yarn add global @vue/cli ``` 
  4. Sass : ``` yarn add node-sass sass-loader ```
  5. Vue-router, Vuex : ```yarn add vue-router vuex```

* DB

  1. docker

  2. mariadb 

     ```docker run --name maria-db -p 3306:3306 -e MYSQL_ROOT_PASSWORD=mariadb -d maraidb```

     ```docker exec -it maria-db mysql -u root -pmariadb```

     ```create database `ssafy-sk`; ```

     이후 backend 폴더 안에 있는 database.sql 실행


## 프로젝트 소개 

### 프로젝트명: 맛동산

### 사용언어 / 기술스택 / 환경

1. Java: zulu-8
2. Spring Boot
3. Maven
4. Vue.js
5. node.js
6. javascript
7. yarn
8. vue-cli
9. Docker

* DB
1. MariaDB

* ERD

![databaseERD](frontend/src/assets/images/databaseERD.png)


### 설치 방법

1. project clone
2. node modules 설치 - npm install
3. aws 접속

### 서비스 소개 

맛집 공유 SNS '맛동산' 입니다! <br />
저희 맛동산은 유저간 맛집에 관한 정보를 공유하는 SNS로, 불필요한 정보는 제외하고 맛집에 관련된 원하는 정보만을 얻을 수 있습니다. <br />
해시태그 검색을 통해 원하는 종류의 맛집 검색이 가능하며, 매거진 기능을 통해 방송에 나왔던 맛집 정보를 확인할 수 있습니다. <br />



