package com.lxl.lincore.auth.token.util;

import io.jsonwebtoken.*;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;

/*
 * 构造及解析jwt的工具类
 */
public class JwtHelper {

	public static Claims parseJWT(String jsonWebToken, String base64Security) {
		try {
			Claims claims = Jwts
					.parser()
					.setSigningKey(
							DatatypeConverter.parseBase64Binary(base64Security))
					.parseClaimsJws(jsonWebToken).getBody();
			return claims;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 生成token
	 * 
	 * @author hetiewei
	 * @date 2016年10月18日 下午2:51:38
	 * @param name
	 * @param audience
	 *            接收者
	 * @param issuer
	 *            发行者
	 * @param TTLMillis
	 *            过期时间(毫秒)
	 * @param base64Security
	 * @return
	 */
	public static String createJWT(String name, String audience, String issuer,
			long TTLMillis, String base64Security) {

		// 添加构成JWT的参数
		JwtBuilder builder = Jwts
				.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.claim("name", name)
				//创建时间
				.claim("iat", System.currentTimeMillis())
				.setIssuer(issuer)
				.setAudience(audience)
				.signWith(SignatureAlgorithm.HS256,
						DatatypeConverter.parseBase64Binary(base64Security));
		// 添加Token过期时间
		if (TTLMillis >= 0) {
			long nowMillis = System.currentTimeMillis();
			Date now = new Date(nowMillis);

			builder.setExpiration(new Date(nowMillis + TTLMillis))
					.setNotBefore(now);
		}

		// 生成JWT
		String token = builder.compact();

		return token;
	}

	public static void main(String[] args) {

		String token = createJWT("admin", "chanle", "ymt", 1000 * 10, "323232");

		System.out.println(token);

		Claims a = parseJWT(token, "323232");

		System.out.println(a.getId());
		System.out.println(a.get("name"));

	}
}
