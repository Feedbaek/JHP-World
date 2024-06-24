package minskim2.JHP_World.config.login.jwt;

import minskim2.JHP_World.config.EnvBean;
import minskim2.JHP_World.config.login.exception.JwtCustomException;
import minskim2.JHP_World.config.security.AuthMember;
import minskim2.JHP_World.domain.member.entity.Member;
import minskim2.JHP_World.domain.member.exception.MemberException;
import minskim2.JHP_World.domain.member.repository.MemberRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.Date;


// 토큰을 생성하고 검증하는 클래스입니다.
// 해당 컴포넌트는 필터클래스에서 사전 검증을 거칩니다.
@Component
@Slf4j(topic = "JwtTokenProvider")
@RequiredArgsConstructor
public class JwtTokenProvider {
    private String secretKey;
    private final EnvBean envBean;
    private final MemberRepository memberRepository;

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(envBean.getJwtSecretKey().getBytes());
    }

    // JWT 토큰 생성
    @Transactional
    public String createToken(Long memberId, JwtTokenType tokenType) throws MemberException {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberException.MemberNotFoundException::new);

        // 토큰 만료 시간 설정
        Date now = new Date();
        Date expiration;
        if (tokenType == JwtTokenType.REFRESH_TOKEN) { // refresh token
            expiration = new Date(now.getTime() + envBean.getRefreshTokenTime());
        } else if (tokenType == JwtTokenType.ACCESS_TOKEN) { // access token
            expiration = new Date(now.getTime() + envBean.getAccessTokenTime());
        } else {
            throw new JwtCustomException.JwtInvalidException();
        }

        // 토큰 생성 후 반환
        return Jwts.builder()
                .subject(member.getId().toString())
                .claim("type", tokenType.getTokenType())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), Jwts.SIG.HS256) // 사용할 암호화 알고리즘과 signature에 들어갈 secret값 세팅
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    @Transactional
    public Authentication getAuthentication(Jws<Claims> claimsJws) throws MemberException {
        String memberId = claimsJws.getPayload().getSubject();

        Member member = memberRepository.findById(Long.parseLong(memberId))
                .orElseThrow(MemberException.MemberNotFoundException::new);

        // todo: Member 테이블 결정 후 AuthMember 객체 수정
        AuthMember authMember = AuthMember.builder()
                .id(member.getId())
                .role(member.getRole().getName())
                .build();

        return new UsernamePasswordAuthenticationToken(authMember, "", authMember.getAuthorities());
    }

    //    // Request의 Header에서 token 값을 가져옵니다. "Authorization" : Bearer [TOKEN 값]'
    public String resolveToken(HttpServletRequest request) {
        String value = request.getHeader("Authorization");
        if (value != null && value.startsWith("Bearer ")) {
            return value.substring(7);
        } else {
            return null;
        }
    }

    //    // 토큰의 유효성 + 만료일자 확인
    public Jws<Claims> validateAndParseToken(String jwt) {
        JwtParser jwtParser = Jwts.parser()
                .decryptWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build();
        return jwtParser.parseSignedClaims(jwt);
    }

    public boolean isRefreshToken(Jws<Claims> jws) {
        return jws.getPayload().get("type").equals(JwtTokenType.REFRESH_TOKEN.getTokenType());
    }

    public boolean isAccessToken(Jws<Claims> jws) {
        return jws.getPayload().get("type").equals(JwtTokenType.ACCESS_TOKEN.getTokenType());
    }
}
