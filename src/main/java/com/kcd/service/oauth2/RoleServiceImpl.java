package com.kcd.service.oauth2;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private HttpSessionSecurityContextRepository httpSessionSecurityContextRepository;

    @Override
    public void addRoleUser(HttpServletRequest request) {
        this.modifyRole("ROLE_USER", request, true);
    }

    @Override
    public void addRoleAdmin(HttpServletRequest request) {
        this.modifyRole("ROLE_ADMIN", request, true);
    }

    @Override
    public void addCustomRole(String roleName, HttpServletRequest request) {
        this.modifyRole(roleName, request, true);  // 새로운 권한 추가
    }

    @Override
    public void deleteRole(String roleName, HttpServletRequest request) {
        this.modifyRole(roleName, request, false);
    }

    private void modifyRole(String role, HttpServletRequest request, boolean isAdd) {
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>(currentAuth.getAuthorities());

        if (isAdd) {
            // 새로운 ROLE 추가
            grantedAuthorityList.add(new SimpleGrantedAuthority(role));
        } else {
            // 기존 권한 중 ROLE_ANONYMOUS 는 삭제
            grantedAuthorityList.removeIf(authority -> authority.getAuthority().equals(role));
        }

        // 새로운 Authentication 객체 생성 (기존 정보와 새로운 권한을 포함한다)
        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                currentAuth.getPrincipal(),
                currentAuth.getCredentials(),
                grantedAuthorityList
        );

        // SecurityContext에 새로운 Authentication 객체 설정 (재인증 생략)
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        // SecurityContext를 세션에 저장
        httpSessionSecurityContextRepository.saveContext(SecurityContextHolder.getContext(), request, null);
    }

}
