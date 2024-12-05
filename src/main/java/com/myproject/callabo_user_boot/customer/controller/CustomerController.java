package com.myproject.callabo_user_boot.customer.controller;

import com.myproject.callabo_user_boot.customer.dto.CreatorFollowDTO;
import com.myproject.callabo_user_boot.customer.dto.KakaoLoginDTO;
import com.myproject.callabo_user_boot.customer.dto.TokenResponseDTO;
import com.myproject.callabo_user_boot.customer.exception.CustomerException;
import com.myproject.callabo_user_boot.customer.service.CustomerService;
import com.myproject.callabo_user_boot.security.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api2/customer")
@RequiredArgsConstructor
@Log4j2
public class CustomerController {

    private final CustomerService customerService;

    private final JWTUtil jwtUtil;

    @Value("${org.oz.accessTime}")
    private int accessTime;

    @Value("${org.oz.refreshTime}")
    private int refreshTime;

    @Value("${org.oz.alwaysNew}")
    private boolean alwaysNew;

    // Kakao 로그인 시 토큰 생성 엔드포인트
    @RequestMapping("kakao")
    public ResponseEntity<TokenResponseDTO> kakaoToken(String accessToken) {

        log.info("Kakao access token: ", accessToken);

        // 카카오 인증 토큰을 사용하여 사용자 정보를 조회
        KakaoLoginDTO kakao = customerService.authKakao(accessToken);

        // Null 체크
        if (kakao.getCustomerId() == null) {
            throw new RuntimeException("Customer ID is null");
        }

        // 사용자 정보를 토큰에 저장하기 위한 Claim 맵 생성
        Map<String, Object> claimMap = Map.of(
                "email", kakao.getCustomerId(),
                "name", kakao.getCustomerName()
        );

        // Access token과 Refresh token을 생성
        String generatedAccessToken = jwtUtil.createToken(claimMap, accessTime);
        String refreshToken = jwtUtil.createToken(claimMap, refreshTime);

        // TokenResponseDTO를 구성
        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
        tokenResponseDTO.setAccessToken(generatedAccessToken);
        tokenResponseDTO.setRefreshToken(refreshToken);
        tokenResponseDTO.setCustomerId(kakao.getCustomerId());
        tokenResponseDTO.setCustomerName(kakao.getCustomerName());

        return ResponseEntity.ok(tokenResponseDTO);
    }

    // Refresh token 사용하여 새로운 Access token 발급하는 엔드포인트
    @PostMapping(value = "kakao/refreshToken", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponseDTO> kakaorefreshToken( @RequestHeader("Authorization") String accessToken, String refreshToken ) {

        // accessToken 또는 refreshToken이 없으면 예외를 던짐
        if(accessToken == null || refreshToken == null) {
            throw CustomerException.TOKEN_NOT_ENOUGH.get();
        }

        // accessToken이 "Bearer "로 시작하지 않는 경우 예외를 던짐
        if(!accessToken.startsWith("Bearer ")) {
            throw CustomerException.ACCESSTOKEN_TOO_SHORT.get();
        }

        // Bearer 부분을 제거한 access token 문자열을 추출
        String accessTokenStr = accessToken.substring("Bearer ".length());

        //AccessToken 만료 여부 체크
        try{
            Map<String, Object> payload = jwtUtil.validateToken(accessTokenStr);

            // 유효한 경우 이메일 정보를 가져와 응답에 포함
            String email = payload.get("email").toString();
            String name = payload.get("name").toString();

            TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
            tokenResponseDTO.setAccessToken(accessTokenStr);
            tokenResponseDTO.setCustomerId(email);
            tokenResponseDTO.setRefreshToken(refreshToken);
            tokenResponseDTO.setCustomerName(name);

            return ResponseEntity.ok(tokenResponseDTO);

        }catch (ExpiredJwtException ex) {

            // RefreshToken 만료 여부를 확인
            try{
                Map<String, Object> payload = jwtUtil.validateToken(refreshToken);
                String email = payload.get("email").toString();
                String name = payload.get("name").toString();
                String newAccessToken = null;
                String newRefreshToken = null;

                // alwaysNew 설정에 따라 새 토큰을 생성할지 결정
                if(alwaysNew) {
                    Map<String, Object> claimMap = Map.of("email", email);
                    newAccessToken = jwtUtil.createToken(claimMap,accessTime);
                    newRefreshToken = jwtUtil.createToken(claimMap,refreshTime);
                }
                // 새로 생성된 토큰 정보를 응답에 포함
                TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
                tokenResponseDTO.setAccessToken(newAccessToken);
                tokenResponseDTO.setRefreshToken(newRefreshToken);
                tokenResponseDTO.setCustomerId(email);
                tokenResponseDTO.setCustomerName(name);

                return ResponseEntity.ok(new TokenResponseDTO());

            }catch (ExpiredJwtException ex2) {
                // RefreshToken 마저 만료된 경우, 재로그인이 필요함을 예외로 던짐
                throw CustomerException.REQUIRE_SIGH_IN.get();
            }
        }
    }

    // 팔로우 상태 변경
//    @PostMapping("/follow")
//    public ResponseEntity<CreatorFollowDTO> followCreator(@RequestBody CreatorFollowDTO followDTO) {
//        CreatorFollowDTO updatedFollowDTO = customerService.toggleFollow(followDTO);
//        return ResponseEntity.ok(updatedFollowDTO);
//    }
}
