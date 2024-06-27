package likelion12th.shop.entity;

import jakarta.persistence.*;
import likelion12th.shop.constant.Role;
import likelion12th.shop.dto.MemberFormDto;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        String pwd = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(pwd);
        member.setRole(Role.USER);
        member.setAddress(memberFormDto.getAddress());

        return member;
    }

}