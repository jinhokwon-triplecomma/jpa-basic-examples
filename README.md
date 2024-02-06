##### mysql 8 인스턴스 생성

```sh
$ docker run --name localhost-mysql8 --hostname localhost-mysql8 \
-e MYSQL_ROOT_PASSWORD=1111 -e TZ='Asia/Seoul' -p 3306:3306 -d mysql:8 \
--max_connections=4096 --general_log=1 \
--general_log_file=/var/lib/mysql/general.log \
--innodb_print_all_deadlocks=1 \
--log_error=/var/lib/mysql/error.log \
--character-set-server=utf8mb4 \
--collation-server=utf8mb4_unicode_ci 
```

```shell
$ docker run --name localhost-mysql8 --hostname localhost-mysql8 -e MYSQL_ROOT_PASSWORD=1111 -e TZ='Asia/Seoul' -p 3306:3306 -d mysql:8 --max_connections=4096 --general_log=1 --general_log_file=/var/lib/mysql/general.log --innodb_print_all_deadlocks=1 --log_error=/var/lib/mysql/error.log --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
```

#### redis 7 인스턴스 생성

```shell
$ docker run -d -p 6379:6379 --name redis7 redis:7.0.3 --appendonly yes --port 6379 --bind "0.0.0.0"
```

##### 샘플 데이터베이스 생성

```sql
CREATE DATABASE `testDB` 
DEFAULT CHARACTER SET utf8mb4 
COLLATE utf8mb4_0900_ai_ci;
-- DEFAULT ENCRYPTION='N';
```

* **`utf8mb4`** : emoji 문자 지원
* **`utf8mb4_0900_ai_ci`** : unicode 9.0 지원 : case insensitive collation 이모지 관련 버그 픽스
* **`ENCRYPTION`** : 암호화 유/무 

#### admin 사용자 생성

```sql
CREATE USER 'admin'@'%' IDENTIFIED BY '1111';
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'%';
```


## Reference

* http://querydsl.com/static/querydsl/latest/reference/html/ch02.html
* https://www.baeldung.com/custom-logback-appender
* https://stackoverflow.com/questions/45810352/register-custom-log-appender-in-spring-boot-starter
* https://chinggin.tistory.com/811
* https://velog.io/@youngerjesus/%EC%9A%B0%EC%95%84%ED%95%9C-%ED%98%95%EC%A0%9C%EB%93%A4%EC%9D%98-Querydsl-%ED%99%9C%EC%9A%A9%EB%B2%95
* https://danielme.com/2023/04/13/testing-spring-boot-docker-with-testcontainers-and-junit-5-mysql-and-other-images/
* https://dev.gmarket.com/76