# 스프링 입문 - 코드로 배우는 스프링 부트, 웹 MVC, DB 접근 기술

> ***스프링 웹 개발 기초 - SECTION 3***

# 3-4 회원 서비스 개발

📍**********************************************회원 서비스 클래스 만들기**********************************************

회원 리포지토리와 도메인을 활용해서 비즈니스 로직 작성

<aside>
💡 Ctrl+Alt+V : IntelliJ에서 해당 단축키는 반환 인자가 있는 메소드에 **변수를 생성함**

</aside>

<aside>
〰️ **result.ifPresent(m → {
       throw new IllegalStateException(”이미 존재하는 회원 입니다.”);
});**
Optional 객체가 값을 가지고 있다면 예외 발생
result.get()으로 값을 꺼내서 if문으로 직접 확인 하는 방식을 권장하지 않음

</aside>

<aside>
〰️ **memberRepository.findByName(member.getName())**
**.ifPresent(m → {
       throw new IllegalStateException(”이미 존재하는 회원 입니다.”);
});**
Optional 객체를 바로 반환하는 것 ⇒ `Optional<Member> result = memberRepository.findByName(member.getName());`은 권장하지 않음
**findByName**의 반환 값은 Optional 멤버이므로 변수 생성 대신 위의 문장으로 예외 처리

</aside>

<aside>
💡 Ctrl+Alt+Shift+T에서 extract method(Alt+Shift+M) 선택 후 원하는 코드를 메소드로 생성

</aside>

- [MemberService.java](http://MemberService.java) 전체코드

<aside>
💡 Service 클래스는 비즈니스에 가까운 용어 사용을 권장

</aside>

```java
package hello.hellospring.service;

```java

public class MemberService {
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    // 회원가입
    public Long join(Member member){
        // 같은 이름이 있는 중복 회원X
        validateDuplicateMember(member);// 중복 회원 검증

        // 중복된 이름이 없으므로 회원 저장
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> { // 값이 있으면 발생되는 메소드
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    // 전체 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

		// 회원 아이디 조회
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
```

# 3-5 회원 서비스 테스트

<aside>
💡 Ctrl+Shift+T ⇒ gototest 검색 후 Test 클래스 생성

</aside>

- [MemberServiceTest.java](http://MemberServiceTest.java) 전체코드

```java
package hello.hellospring.service;

```java

class MemberServiceTest {
    MemberService memberService;
    MemoryMemberRepository memoryMemberRepository;

    // 테스트 실행 전마다 리포지토리를 생성해서 MemberService에 넣어줌
    @BeforeEach
    public void beforeEach(){
        memoryMemberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memoryMemberRepository);
    }

    @AfterEach
    public void afterEach() {
        memoryMemberRepository.clearStore();
    }

    // build시 실제 코드에 포함되지 않으므로 Test 코드는 한국어로 작성해서 실행해도 됨
    // 더 직관적으로 알아볼 수 있음
    @Test
    void 회원가입() {
        // given : 어떤 상황이 주어졌을 때
        Member member = new Member();
        member.setName("spring");

        // when : 실행 했을 때
        Long saveId = memberService.join(member);

        // then : 원하는 결과는 아래 코드가 나와야 함
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        // when

        memberService.join(member1);
        // 중복 회원 member2 객체가 join 하려면 IllegalStateException이 터져야 함
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // 예외 메시지 검증
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
/*
        try{
            memberService.join(member2);
            fail(); // 예외의 실패했을 때 실행됨
        } catch(IllegalStateException e){
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
*/

        // then
    }
    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}
```

***DI의 관련해서는 다음 강의에서 자세히 학습 !!