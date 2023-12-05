package com.yayum.tour_info_service_server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Member {
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

}
