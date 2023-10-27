package hello.hellospring.repository;

import hello.hellospring.Repository.MemoryMemberRepository;
import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

// 다른 곳에서 사용하지 않으므로 반드시 public으로 하지 않아도 됨
class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    // 테스트가 끝날 때 마다 데이터를 지워주는 메소드
    @AfterEach
    public void afterEach(){
        repository.clearStore(); // 테스트가 한번 시작되고 끝날 때마다 저장소를 비움
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring"); // 이름 저장
        repository.save(member); // 저장소에 회원 객체 저장
        Member result = repository.findById(member.getId()).get(); // 앞서 저장한 데이터가 저장되었는지 확인
        //Assertions.assertEquals(member, result); // 다르면 error
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName(){
        // spring1, spring2 회원 가입
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring2").get();

        assertThat(result).isEqualTo(member2);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
