package likelion12th.shop.repository;


import likelion12th.shop.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application.properties")
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입 테스트")
    public void createMemberTest() {
        // 1. 멤버 객체 생성
        // 2. 멤버 객체에 이름, 아이디(이메일), 비밀번호, 주소 직접 세팅
        // 3. memberRepository의 save 메소드를 사용하여 해당 객체 저장
        // - 해당 객체를 저장할 새로운 멤버 객체 생성 필요, save(member) 로 저장
        // 4. System.out.println() 함수로 출력
        // - member 엔티티에 @ToString 어노테이션 추가 후 member.toString() 을 출력하면 됩니다.

        Member member = new Member();
        member.setName("최유신");
        member.setEmail("yushin@likelion.com");
        member.setPassword("qwerty");
        member.setAddress("인천시");

        Member savedMember = memberRepository.save(member);
        System.out.println(savedMember.toString());
    }
}