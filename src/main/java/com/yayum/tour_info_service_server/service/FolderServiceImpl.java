package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.*;
import com.yayum.tour_info_service_server.entity.Cart;
import com.yayum.tour_info_service_server.entity.Folder;
import com.yayum.tour_info_service_server.entity.Member;
import com.yayum.tour_info_service_server.entity.Place;
import com.yayum.tour_info_service_server.repository.CartRepository;
import com.yayum.tour_info_service_server.repository.FolderRepository;
import com.yayum.tour_info_service_server.repository.MemberRepository;
import com.yayum.tour_info_service_server.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class FolderServiceImpl implements FolderService{

    //폴더 repository
    private final FolderRepository folderRepository;

    //장바구니 repository
    private final CartRepository cartRepository;

    //멤버 repository
    private final MemberRepository memberRepository;

    //장소 repository
    private final PlaceRepository placeRepository;

    //폴더 전부 조회
    @Override
    public List<FolderAllDTO> getAllFolder(Long mno) {
        List<Object[]> result=folderRepository.getFolderAll(mno);
        List<FolderAllDTO> folderAllDTOS = new ArrayList<>();

        for (Object[] objects : result) {
            FolderAllDTO folderAllDTO = new FolderAllDTO();
            folderAllDTO.setFno((Long) objects[0]);
            folderAllDTO.setTitle((String) objects[1]);
            folderAllDTO.setPno((Long)objects[2]);
            folderAllDTO.setName((String)objects[3]);
            folderAllDTOS.add(folderAllDTO);
        }
        return folderAllDTOS;
    }

    //폴더명 조회
    @Override
    public List<String> getTitle(Long mno) {
        List<String> result=folderRepository.getFolderTitle(mno);
        return result;
    }

    //폴더 등록
    @Override
    public Long register(FolderRegistDTO folderRegistDTO) {
        Optional<Member> member=memberRepository.findById(folderRegistDTO.getMno());

        //회원이 없으면 null 반환
        if(!member.isPresent()){
            return -1l;
        }

        Folder folder=Folder.builder()
                .member(Member.builder().mno(folderRegistDTO.getMno()).build())
                .title(folderRegistDTO.getTitle())
                .build();
        folderRepository.save(folder);
        return folder.getFno();
    }

    //폴더 수정
    @Override
    public Long modify(FolderDTO folderDTO) {
        Long num=folderDTO.getFno();
        Optional<Folder> result=folderRepository.findById(num);
        if(result.isPresent()) {
            Folder folder=result.get();
            folder.changeTitle(folderDTO.getTitle());
            folderRepository.save(folder);
            return folder.getFno();
        }
        return -1l;
    }

    //폴더 삭제
    @Override
    public Long remove(Long fno) {
        if (folderRepository.findById(fno).isPresent()) {
            folderRepository.deleteById(fno);
            return fno;
        }
        return -1l;
    }


    //폴더 스팟 추가
    @Override
    public Long addSpot(CartDTO cartDTO) {
        if (placeRepository.findById(cartDTO.getPno()).isPresent()){
            Cart cart=cartDtoToEntity(cartDTO);
            cartRepository.save(cart);
            return cartDTO.getFno();
        }
        return -1l;
    }



    //폴더 스팟 제거 - CartService와 머지 후 주석 제거
    @Override
    public String deleteSpot(CartDTO cartDTO) {
        if (cartRepository.findById(cartDTO).isPresent()){
            cartRepository.delete(cartDtoToEntity(cartDTO));
            Place place= Place.builder()
                    .pno(cartDTO.getPno())
                    .build();
            System.out.println(place.getName());
            return place.getName();
        }
        return null;
    }
}
