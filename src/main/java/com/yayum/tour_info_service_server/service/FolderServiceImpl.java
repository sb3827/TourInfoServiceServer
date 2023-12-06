package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.FolderChangeDTO;
import com.yayum.tour_info_service_server.dto.FolderDTO;
import com.yayum.tour_info_service_server.dto.FolderDeleteDTO;
import com.yayum.tour_info_service_server.entity.Folder;
import com.yayum.tour_info_service_server.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService{

    //폴더 repository
    private final FolderRepository folderRepository;

    //폴더 조회
    @Override
    public List<FolderDTO> getAllFolder(Long mno) {
        return null;
    }

    //폴더명 조회
    @Override
    public List<String> getTitle(Long mno) {
        List<String> result=folderRepository.getFolderTitle(mno);
        return result;
    }

    //폴더 등록
    @Override
    public Long register(FolderDTO folderDTO) {
        Folder folder=dtoToEntity(folderDTO);
        folderRepository.save(folder);
        return folder.getFno();
    }

    //폴더 수정
    @Override
    public Long modify(FolderChangeDTO folderChangeDTO) {
        Folder folder=folderRepository.findByMnoAndTitle(folderChangeDTO.getMno(),folderChangeDTO.getTitle());
        if(folder!=null) {
            folder.changeTitle(folderChangeDTO.getReTitle());
            folderRepository.save(folder);
            return folder.getFno();
        }
        return -1l;
    }

    //폴더 삭제
    @Override
    public Long remove(FolderDeleteDTO folderDeleteDTO) {
        Folder folder=folderRepository.findByMnoAndTitle(folderDeleteDTO.getMno(),folderDeleteDTO.getTitle());
        if (folder!=null){
            folderRepository.deleteById(folder.getFno());
            return folder.getFno();
        }
        return -1l;
    }


    //폴더 스팟 추가

    //폴더 스팟 제거
}
