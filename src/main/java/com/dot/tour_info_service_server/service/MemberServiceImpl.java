package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.dto.JoinWaitingDTO;
import com.dot.tour_info_service_server.dto.SearchUserListDTO;
import com.dot.tour_info_service_server.dto.UserInfoDTO;
import com.dot.tour_info_service_server.dto.UserProfileDTO;
import com.dot.tour_info_service_server.entity.Member;
import com.dot.tour_info_service_server.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    // 회원정보조회
    @Override
    public UserInfoDTO showUserInfo(Long mno) {
        Optional<Member> result = memberRepository.findById(mno);
        if (result.isPresent()) {
            Member member = result.get();
            UserInfoDTO userInfoDTO = entityToDto(member);
            return userInfoDTO;
        }
        return null;
    }

    // 회원정보수정 ( 이름, 전화번호, 이미지 )
    @Override
    @Transactional
    public UserInfoDTO modifyUserInfo(UserInfoDTO userInfoDTO) {
        Optional<Member> result = memberRepository.findById(userInfoDTO.getMno());
        if(result.isPresent()){
            Member member = result.get();
            member.changeName(userInfoDTO.getName());
            member.changePhone(userInfoDTO.getPhone());
            member.changeImage(userInfoDTO.getImage());
            memberRepository.save(member);
            return userInfoDTO;
        }
        return null;
    }

    // 회원 프로필 조회 ( 이름, 팔로잉, 팔로워, 찜목록, 이미지 )
    @Override
    public UserProfileDTO showUserProfile(String name) {
        List<Object[]> result = memberRepository.findProfileByName(name);
        if(!result.isEmpty()){
            UserProfileDTO userProfileDTO = UserProfileDTO.builder()
                    .mno((Long)result.get(0)[0])
                    .name((String)result.get(0)[1])
                    .followings((Long)result.get(0)[2])
                    .followers((Long)result.get(0)[3])
                    .cart((Long)result.get(0)[4])
                    .image((String)result.get(0)[5])
                    .build();
            return userProfileDTO;
        }

        return null;
    }

    // 회원 탈퇴
    @Override
    @Transactional
    public void deleteUserInfo(Long mno) {
        memberRepository.deleteById(mno);
    }

    //회원 검색
    @Override
    public List<SearchUserListDTO> searchUser(String name) {
        List<Object[]> result = memberRepository.searchUser(name);
        List<SearchUserListDTO> userlist = new ArrayList<>();

        if(!result.isEmpty()){
            for(Object[] list : result){
                SearchUserListDTO searchUserListDTO = SearchUserListDTO.builder()
                        .mno((Long)list[0])
                        .image((String)list[1])
                        .name((String)list[2])
                        .build();
                userlist.add(searchUserListDTO);
            }
            return userlist;
        }
        return null;
    }

    // 회원가입 대기 목록
    @Override
    public List<JoinWaitingDTO> showJoinWaiting() {
        List<Object[]> result = memberRepository.showJoinWaiting();
        List<JoinWaitingDTO> waitingList = new ArrayList<>();
        if(!result.isEmpty()){
            for(Object[] list : result){
                JoinWaitingDTO joinWaitingDTO = JoinWaitingDTO.builder()
                        .mno((Long)list[0])
                        .name((String)list[1])
                        .email((String)list[2])
                        .businessId((int)list[3])
                        .build();
                waitingList.add(joinWaitingDTO);
            }
            return waitingList;
        }
        return null;
    }

    // 회원가입승인 ( is_approve를 true로 )
    @Override
    @Transactional
    public void joinMember(Long mno) {
        memberRepository.joinMember(mno);
    }
}