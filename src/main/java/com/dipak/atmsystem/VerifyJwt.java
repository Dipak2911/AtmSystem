package com.dipak.atmsystem;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

import java.io.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

public class VerifyJwt {
	
	String jwtTokenInHeader;
	Jws<Claims> myCLaims;
	
	public VerifyJwt(String jwtTokenInHeader)
	{
		this.jwtTokenInHeader= jwtTokenInHeader;
	}

	boolean validate(String audiance)
	{
		try {
			myCLaims = verifyJWTToken(jwtTokenInHeader,audiance);
		} catch (Exception ex) {
			System.out.print(ex);
			return false;
//			return Response.status(Response.Status.FORBIDDEN.getStatusCode()).entity(ex.getMessage()).build();
		}
		String jwtContent = null;
		for (String key : myCLaims.getBody().keySet()) {
			if ("content".equals(key)) {
				jwtContent = String.valueOf(myCLaims.getBody().get(key));
				break;
			}
		}
		
		System.out.println("jwtContent : " + jwtContent);
		System.out.println("jwtContent Issuer : " + myCLaims.getBody().getIssuer());
		System.out.println("jwtContent Subject : " + myCLaims.getBody().getSubject());
		System.out.println("jwtContent Audience: " + myCLaims.getBody().getAudience());


		return true;
	}
	
	

	
	
	private Jws<Claims> verifyJWTToken(String jwtToken,String audiance) throws Exception {
		try {
			return Jwts.parserBuilder().requireAudience(audiance).requireIssuer("issuerURL").setSigningKey(getRSAPublicKey()).build().parseClaimsJws(jwtToken);
		} catch (io.jsonwebtoken.security.SignatureException e) {
			throw new Exception("JWT signature not valid");
		} catch (ExpiredJwtException e) {
			throw new Exception("JWT Expired");
		} catch (UnsupportedJwtException e) {
			throw new Exception("JWT Not supported");

		} catch (MalformedJwtException e) {
			throw new Exception("JWT format invalid");
		} catch (IllegalArgumentException e) {
			throw new Exception("JWT  invalid");
		}
	}
	
	private Key getRSAPublicKey() {
		Key key = null;
		try {
            
            File initialFile1 = new File("C://Users//sonar//Downloads//jwtStore.pem");
			InputStream targetStream1 = new FileInputStream(initialFile1);

			CertificateFactory fact = CertificateFactory.getInstance("X.509");
			X509Certificate cer = (X509Certificate) fact.generateCertificate(targetStream1);
			key = cer.getPublicKey();

		} catch (IOException | CertificateException e) {
			e.printStackTrace();
		}
		return key;
	}

}
