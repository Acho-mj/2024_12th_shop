### application.properties 양식
```
# MySQL
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://{url}:{port}/{db}?serverTimezone=UTC
spring.datasource.username={username}
spring.datasource.password={password}

# 실행 쿼리 출력
spring.jpa.properties.hibernate.show_sql=true
# 쿼리 포맷 설정
spring.jpa.properties.hibernate.format_sql=true
# 바인드 파라미터 출력
logging.level.org.hibernate.type.descriptor.sql=trace
# 데이터베이스 초기화 전략 - DDL AUTO 옵션
spring.jpa.hibernate.ddl-auto={create}
# 데이터베이스 방언 설정
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```