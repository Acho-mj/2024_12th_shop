package likelion12th.shop.entity;

import jakarta.persistence.*;
import likelion12th.shop.constant.Role;
import likelion12th.shop.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="member")
@Getter @Setter
@ToString
public class Member extends Base {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;
    private String password;
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    // 엔티티 생성 메소드
    public static Member createMember(MemberFormDto memberFormDto) {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());

        member.setAddress(memberFormDto.getAddress());
        // 스프링 시큐리티 전이므로 주석 처리 하겠음
        // String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(memberFormDto.getPassword());
        member.setRole(Role.USER);

        return member;
    }
}