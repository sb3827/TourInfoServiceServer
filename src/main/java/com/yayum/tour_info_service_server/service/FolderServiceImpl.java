package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.*;
import com.yayum.tour_info_service_server.entity.Cart;
import com.yayum.tour_info_service_server.entity.Folder;
import com.yayum.tour_info_service_server.repository.CartRepository;
import com.yayum.tour_info_service_server.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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

    //폴더 전부 조회
    @Override
    public List<Object[]> getAllFolder(Long mno) {
        List<Object[]> result=folderRepository.getFolderAll(mno);
        log.info("폴더 전체 조회 "+result);
        return result;
    }

    //폴더명 조회
    @Override
    public List<String> getTitle(Long mno) {
        List<String> result=folderRepository.getFolderTitle(mno);
        log.info("폴더명 조회 "+result);
        return result;
    }

    //폴더 등록
    @Override
    public Long register(FolderDTO folderDTO) {
        log.info("폴더 등록 "+folderDTO.getFno());
        Folder folder=folderDtoToEntity(folderDTO);
        folderRepository.save(folder);
        return folder.getFno();
    }

    //폴더 수정
    @Override
    public Long modify(FolderDTO folderDTO) {
        Long num=folderDTO.getFno();
        Optional<Folder> result=folderRepository.findById(num);
        log.info("폴더 "+folderDTO.getTitle()+"로 수정");
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
    public void remove(Long fno) {
        log.info("폴더 삭제 "+fno);
        folderRepository.deleteById(fno);
    }


    //폴더 스팟 추가
    @Override
    public Long addSpot(CartDTO cartDTO) {
        Cart cart=cartDtoToEntity(cartDTO);
        cartRepository.save(cart);
        return 1l;
    }



    //폴더 스팟 제거 - CartService와 머지 후 주석 제거
    @Override
    public Long deleteSpot(CartDTO cartDTO) {
        cartRepository.delete(cartDtoToEntity(cartDTO));
        return 1l;
    }
}
