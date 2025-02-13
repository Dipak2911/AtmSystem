package com.dipak.atmsystem;

import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.io.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

public class TokenGenerator {
	
    JwtBuilder jwtBuilder = Jwts.builder();
    String algo = "RS256";
    
    public void jwrRsa()
    {
        jwtBuilder.setHeaderParam(JwsHeader.TYPE,JwsHeader.JWT_TYPE);
		jwtBuilder.setHeaderParam(JwsHeader.ALGORITHM,algo);
		jwtBuilder.setHeaderParam(JwsHeader.KEY_ID,"1");
    }
    
    public void setData(Map data)
    {
        jwtBuilder.setClaims(data);
    }
    
    public void setTechnicalData(String subject, String audiance, String issuer)
    {
        jwtBuilder.setSubject(subject);
        jwtBuilder.setAudience(audiance);
        jwtBuilder.setIssuer(issuer);

        long milliSeconds = System.currentTimeMillis();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.setExpiration(new Date(milliSeconds+(1000L*60L*50)));
    }
    
    public String getToken()
    {
        Key mySigningKey = getRSAPrivateKey();
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.forName(algo);
        jwtBuilder.signWith(mySigningKey,signatureAlgorithm);

        String jwtToken = jwtBuilder.compact();

        return jwtToken;
    }
    
    private Key getRSAPrivateKey()
    {
        Key key = null;
        try {
            File initialFile = new File("C://Users//sonar//Downloads//jwtStore.pkcs12");
            InputStream targetStream = new FileInputStream(initialFile);

            KeyStore keystore;
            try {
                keystore = KeyStore.getInstance("PKCS12");
                keystore.load(targetStream, "password".toCharArray());
                key = keystore.getKey("5", "password".toCharArray());

            } catch (KeyStoreException | NoSuchAlgorithmException
                     | CertificateException | UnrecoverableKeyException
                     | IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return key;
    }

}
