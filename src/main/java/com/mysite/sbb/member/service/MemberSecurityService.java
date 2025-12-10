package com.mysite.sbb.member.service;

import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.member.repository.MemberRepository;
import com.mysite.sbb.member.constant.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberSecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자가 존재하지 않습니다."));

        List<GrantedAuthority> authList = new ArrayList<>();
        if ("admin".equals(username)) {
            authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if (member.getUserType() == com.mysite.sbb.member.constant.UserType.SCOUT) {
            authList.add(new SimpleGrantedAuthority("ROLE_SCOUT"));
        } else {
            authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return new User(member.getUsername(), member.getPassword(), authList);
    }
}
