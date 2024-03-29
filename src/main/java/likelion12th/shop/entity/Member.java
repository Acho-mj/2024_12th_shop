package likelion12th.shop.entity;

import jakarta.persistence.*;
import likelion12th.shop.constant.Role;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name="member")
@Getter
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

}