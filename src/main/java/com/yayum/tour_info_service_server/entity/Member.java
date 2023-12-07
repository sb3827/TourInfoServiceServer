package com.yayum.tour_info_service_server.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Member extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long mno;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private LocalDateTime birth;

  @Column(nullable = false)
  private String phone;

  @Column(nullable = false)
  private String name;
  private String image;

  @ColumnDefault("false")
  private boolean fromSocial;

  @ColumnDefault("0")
  private int disciplinary;

  @ColumnDefault("0")
  private int bussinessId;

  @ElementCollection(fetch = FetchType.LAZY)
  @Builder.Default
  private Set<Role> roleSet = new HashSet<>();

  public void addMemberRole(Role role){
    roleSet.add(role);
  }

}
