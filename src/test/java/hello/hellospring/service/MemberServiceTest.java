package hello.hellospring.service;

import hello.hellospring.Repository.MemoryMemberRepository;
import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    // build시 실제 코드에 포함되지 않으므로 코드는 한국어로 작성해서 실행해도 됨
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