package org.troy.system.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.DigestUtils;

/**
 *自定义的校验器
 * Created by 杨磊 on 2014/4/22.
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        SimpleAuthenticationInfo simpleInfo = (SimpleAuthenticationInfo)info;
        ByteSource salt = simpleInfo.getCredentialsSalt();
        String encryptStr = String.valueOf(token.getPassword());
        if(salt != null){
            encryptStr +=salt.toHex();
        }
        Object tokenCredentials = encrypt(encryptStr);
        Object accountCredentials = getCredentials(info);
        //将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false
        return equals(tokenCredentials, accountCredentials);
    }

    //将传进来密码加密方法
    private String encrypt(String data) {
        return DigestUtils.md5DigestAsHex(data.getBytes());//这里可以选择自己的密码验证方式 比如 md5或者sha256等
    }
}
