## Spring Boot + MyBatis 사용법

MyBatis 3.0부터 XML 파일 또는 어노테이션이 작성된 SQL 쿼리 명령을 수행할 수 있는 Mapper 인터페이스를 사용할 수 있다.

```java
@Mapper
public interface UserMapper {
    ...
}
```

@Mapper 어노테이션을 통해 스프링 부트가 해당 인터페이스를 mapper로 인식하며 해당 인터페이스를 빈으로 등록한다.

```java
@Mapper
public interface UserMapper {
    
    @Insert("INSERT INTO USER(user_name, user_email) VALUES(#{user.name}, #{user.email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(@Param("user") User user);
    
}
```

매개변수를 받아올 때는 `@Param(...)`을 통해 값을 명시하고 `#{...}`을 통해 값을 동적 바인딩 할 수 있다.

```java
@Mapper
public interface UserMapper {
    
    @Select("SELECT * FROM USER WHERE user_id = #{id}")
    @Results({
            @Result(property = "name", column = "user_name"),
            @Result(property = "email", column = "user_email")
    })
    Optional<User> findById(@Param("id") Long id);
}
```

조회할 때 매핑할 DB 컬럼명과 자바 필드명이 다른 경우 `@Results`를 어노테이션을 사용해 매핑관계를 지정할 수 있다.

```java
@Mapper
public interface UserMapper {
    
    @Select("SELECT * FROM USER WHERE user_id = #{id}")
    @Results(id = "UserMap", value = {
            @Result(property = "name", column = "user_name"),
            @Result(property = "email", column = "user_email")
    })
    Optional<User> findById(Long id);

    @Select("SELECT * FROM USER WHERE user_email = #{email}")
//    @Results({
//            @Result(property = "name", column = "user_name"),
//            @Result(property = "email", column = "user_email")
//    })
    @ResultMap("UserMap")
    Optional<User> findByEmail(String email);
    
}
```

동일한 `@Results`가 반복적으로 사용될 때는 `@Results`에 `id`를 부여해 재사용할 수 있다.

```yaml
// application.yml
mybatis:
  configuration:
    map-underscore-to-camel-case: true
```

보통 데이터베이스 컬럼명은 스네이크 표기법을 사용하고 자바는 카멜 케이스를 사용한다.

`@Results`를 사용해 매핑관계를 지정해 주는 것도 괜찮지만 매핑 해야할 필드가 많을 경우 코드가 지저분해진다.

이 때 `mybatis.configuration.map-underscore-to-camel-case:true` 옵션을 사용해 매핑관계를 지정하지 않고 간단히 해결 할 수 있다.


### XML

어노테이션의 속성값으로 SQL을 직접 사용하는 경우 쿼리가 복잡할 수록 가독성이 떨어지고 관리가 힘들어진다.

복잡한 쿼리는 XML을 사용하는 것이 좋은 방법이 될 수 있다.

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
        "http://mybatis.org/schema/mybatis-3-mapper.dtd">
<mapper namespace="com.example.springbootmybatis.mapper.UserXmlMapper">
</mapper>
```

`namespace`는 `Mapper interface`의 패키지를 포함한 전체 경로를 작성하면 된다.

`<select> <insert> <update> <delete>`를 사용해 필요한 SQL 구문들을 작성하면 된다.

```xml
<!--parameterType은 파라미터의 자료형을 명시해준다.-->
<!--resultType은 반환될 타입을 의미한다.-->
<select id="findById" parameterType="Long" resultType="User">
    SELECT * FROM USER WHERE user_id = #{id};
</select>
```

`id` 값은 `Mapper interface` 내의 메소드명과 일치하면 자동으로 `xml`과 메소드가 매핑되어 등록된 SQL을 실행한다.
 
`resultType`을 작성할 때는 전체 경로를 작성해야하지만 아래 설정을 통해 패키지 경로를 지정해 놓으면 생략할 수 있다.

```yaml
mybatis:
  # User 경로
  type-aliases-package: com.example.springbootmybatis.user.model
  
  # XML 경로
  mapper-locations: /mapper/mapper.xml
```

저장할 때 자동적으로 `PK`값을 증가하게 하는 `auto_increment`를 적용하려면 다음과 같이하면 된다.

```xml
<insert id="save" parameterType="User" useGeneratedKeys="true" keyProperty="id">
    INSERT INTO USER(user_name, user_email) VALUES(#{name}, #{email});
</insert>
```

---

#### 참고

- <https://mybatis.org/mybatis-3/ko/configuration.html>