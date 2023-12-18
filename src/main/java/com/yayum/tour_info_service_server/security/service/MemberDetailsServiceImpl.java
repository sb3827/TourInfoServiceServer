package com.yayum.tour_info_service_server.security.service;

import com.yayum.tour_info_service_server.dto.MemberDTO;
import com.yayum.tour_info_service_server.entity.Member;
import com.yayum.tour_info_service_server.repository.MemberRepository;
import com.yayum.tour_info_service_server.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.beans.SimpleBeanInfo;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> result = memberRepository.findByEmail(username);

        if (!result.isPresent()) {
            throw new UsernameNotFoundException("unregistered users");
        }
        Member member = result.get();
        AuthMemberDTO authMemberDTO = new AuthMemberDTO(
                member.getEmail(), member.getMno(), member.getPassword(), member.isFromSocial(),
                member.getRoleSet().stream().map(role ->
                        new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toList()));

        authMemberDTO.setName(member.getName());
        authMemberDTO.setFromSocial(member.isFromSocial());

        return authMemberDTO;
    }
}
