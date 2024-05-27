import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;

public class JwtTest {
    @Test
    public void test() {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("username","test");
        //生成jwt代码
        String token = JWT.create()
                .withClaim("user", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))//设置令牌到期时间。此处是12h后到期
                .sign(Algorithm.HMAC256("amber"));//指定算法，配置密钥，这个密钥不要让其他人知道
        System.out.println(token);
    }

    @Test
    public void test2() {
        String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6InRlc3QifSwiZXhwIjoxNzE2ODE1ODExfQ" +
                ".Q-sLU-kT9uTojfAGgjPH518_c3esWjPNOCJOsXyRreo";
        JWTVerifier amber = JWT.require(Algorithm.HMAC256("amber")).build();
        DecodedJWT decodedJWT = amber.verify(token);//验证token，生成一个解析后的JWT对象
        System.out.println(decodedJWT.getClaim("user"));
    }
}
