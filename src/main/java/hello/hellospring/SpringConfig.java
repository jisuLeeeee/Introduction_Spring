package hello.hellospring;

import hello.hellospring.Repository.*;
import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.service.MemberService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    private final MemberRepository memberRepository;

    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 스프링 빈 등록
    @Bean
    public MemberService memberService(){

        return new MemberService(memberRepository);
    }



//    @Bean
//    public MemberRepository memberRepository(){
        // return new MemoryMemberRepository();

        // 스프링 부트는 데이터베이스 Connection 정보를 바탕으로 DataSource를 생성하고 스프링 빈으로 만들어서 DI를 받을 수 있음
        // return new JdbcMemberRepository(dataSource);
        // return new JdbcTemplateMemberRepository(dataSource);
        // return new JpaMemberRepository(em);
//    }
}
