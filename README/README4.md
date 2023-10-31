# 스프링 입문 - 코드로 배우는 스프링 부트, 웹 MVC, DB 접근 기술
### <2023-10-31>
> ***스프링 웹 개발 기초 - SECTION 3***
>

# 4-1 컴포넌트 스캔과 자동 의존 관계 설정

📍**스프링 빈을 등록하고, 의존 관계 설정하기
회원 컨트롤러가 회원 서비스와 회원 리포지토리를 사용할 수 있게 의존 관계를 준비함**

<aside>
〰️ <b>@Controller
public class MemberController{}</b>
@Controller 어노테이션을 쓰면 <b>스프링 컨테이너가 MemberController 객체를 생성해서 관리함</b>(=스프링 빈이 관리됨)

</aside>

- MemberService 클래스를 new 연산자로 객체 생성하는 대신 **하나만 생성해서 공통으로 쓸 수 있도록 스프링 컨테이너에 등록하는 방식을 사용**(해당 방식의 여러가지 효과는 뒤에서 설명!!)

<aside>
〰️ <b>@Autowired</b>

**public MemberController(MemberService memberService){**

**this.memberService = memberService;**

**}**

- 스프링 컨테이너가 뜰 때 해당 멤버컨트롤러가 생성되기 때문에 이 생성자가 호출됨
- 이 때, 생성자에 @Autowired가 있으면 스프링이 연관된 객체(해당 코드에서는 멤버서비스)를  스프링 컨테이너에서 찾아서 연결하는데 이렇게 객체 의존 관계를 외부에서 넣어주는 것을 DI(Dependency Injection), 의존성 주입이라고 함
</aside>

<aside>
🚨 <b>Parameter 0 of constructor in hello.hellospring.controller.MemberController required a bean of type 'hello.hellospring.service.MemberService' that could not be found.</b>
위의 멤버컨트롤러 코드를 실행하면 위와 같이 멤버서비스를 찾을 수 없다는 에러가 발생함
- [MemberService.java](http://MemberService.java) 클래스는 스프링 빈으로 등록되어 있지 않았어서 클래스명 윗줄에 <b>@Service 어노테이션 작성(Repository도 마찬가지로 @Repository 작성)</b>

</aside>

- [MemberController.java](http://MemberController.java) 전체코드

```java
package hello.hellospring.controller;

```java

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
```

***컨트롤러 통해서 외부 요청을 받고 서비스에서 비즈니스 로직을 만들고 리포지토리에서 데이터를 저장하는 정형화된 패턴***

📍**스프링 빈을 등록하는 2가지 방법**

- 컴포넌트 스캔과 자동 의존 관계 설정
    - @Component 어노테이션이 있으면 스프링 빈으로 자동 등록됨
    - @Controller 컨트롤러가 스프링 빈으로 자동 등록된 이유도 컴포넌트 스캔 때문임
        - @Service, @Repository 또한 @Component를 포함하기 때문에 스프링 빈이 자동 등록됨
- 자바 코드로 직접 스프링 빈 등록하기
    - 회원 서비스와 회원 리포지토리의 @Service, @Repository, @Autowired을 제거하고 진행함
    - [SpringConfig.java](http://SpringConfig.java) 전체코드

    ```java
    package hello.hellospring;
    
    ```java
    
    @Configuration
    public class SpringConfig {
        // 스프링 빈 등록
        @Bean
        public MemberService memberService(){
            return new MemberService(memberRepository());
        }
    
        @Bean
        public MemberRepository memberRepository(){
            return new MemoryMemberRepository();
        }
    }
    ```


*** 여기서는 향후 메모리 리포지토리를 다른 리포지토리로 변경할 예정이므로, 컴포넌트 스캔 방식 대신에 자바 코드로 스프링 빈을 설정함

<aside>
💡 DI에는 필드 주입, setter 주입, 생성자 주입 이렇게 3가지 방법이 있고 의존 관계가 실행 중에 동적으로 변하는 경우(run 실행 중 서버가 변경되는 경우)는 거의 없으므로 **생성자 주입을 권장함**

</aside>

<aside>
💡 실무에서는 주로 정형화된 컨트롤러, 서비스, 리포지토리 같은 코드는 컴포넌트 스캔을 사용함
<b>정형화 되지 않거나, 상황에 따라 구현 클래스를 변경해야 하면 실행을 통해 스프링 빈으로 등록함</b>

</aside>

<aside>
💡 @Autowired를 통한 DI는 helloController, MemberService 등과 같이 스프링이 관리하는 객체에서만 동작하고 스프링 빈으로 등록하지 않고 내가 직접 생성한 객체에서는 동작하지 않음

</aside>