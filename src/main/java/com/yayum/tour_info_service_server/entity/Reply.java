package com.yayum.tour_info_service_server.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Reply extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long rno;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_name")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  private Board board;

  @Column(nullable = false, length =1000)
  private String text;    // 댓글 내용
  @ManyToOne(fetch = FetchType.LAZY)

  @JoinColumn(name = "parent_rno")
  private Reply parent;   // 부모 댓글

  @OneToMany(mappedBy = "parent")
  private List<Reply> childrenList = new ArrayList<>();   // 자식댓글

  public void changeText(String text) {
    this.text = text;
  }


  // 자식댓글을 특정 부모댓글의 자식댓글로 설정하는 로직 :: service 의 대댓글 작성 기능에 사용
  public void setAsChildren(Reply parent) {
    this.parent = parent;
    parent.addChild(this);
  }

  public void addChild(Reply child) {
    childrenList.add(child);
  }

}
