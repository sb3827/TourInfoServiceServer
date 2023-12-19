package com.yayum.tour_info_service_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FolderAllDTO { //폴더 전체 정보 들고옴
    private Long fno; //폴더 번호
    private String title; //폴더 title
    private Long pno; //장소 번호
    private String name; //장소명

}
