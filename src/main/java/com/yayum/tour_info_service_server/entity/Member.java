package com.yayum.tour_info_service_server.entity;

import jakarta.persistence.*;
import lombok.*;

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

  private String email;
  private String password;
  private LocalDateTime birth;
  private String phone;
  private String name;
  private String image;
  private boolean fromSocial;
  private int warn;
  private int disciplinary;
  private int bussinessId;

  @ElementCollection(fetch = FetchType.LAZY)
  @Builder.Default
  private Set<Role> roleSet = new HashSet<>();

  public void addMemberRole(Role role){
    roleSet.add(role);
  }

}
