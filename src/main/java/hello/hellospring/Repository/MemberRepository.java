package hello.hellospring.Repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member); // 회원 객체가 저장소에 저장
    Optional<Member> findById(Long id); // 회원의 id 찾기
    Optional<Member> findByName(String name); // 회원의 이름 찾기
    List<Member> findAll(); // 저장된 모든 회원 리스트 반환
}
