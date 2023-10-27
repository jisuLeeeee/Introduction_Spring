package hello.hellospring.Repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;
    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny(); // store를 검사하면서 매개변수 name과 같으면 즉시 반환, 그렇지 않으면 null 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); // member 객체 반환
    }

    public void clearStore(){
        store.clear();
    }
}
