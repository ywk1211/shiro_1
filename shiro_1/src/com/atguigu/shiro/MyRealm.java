package com.atguigu.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class MyRealm extends AuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		//调用PrincipalCollection的getPrimaryPrincipal()方法来获取
		//登录信息
		Object principal = principals.getPrimaryPrincipal();
		
		//若登录信息中没包含权限信息 则利用上面的principal来获取权限信息
		System.out.println("登录的用户为:"+principal);
		
		//把权限信息封装为一个SimpleAuthorizationInfo对象，并返回
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRole("user");
		if("admin".equals(principal)){
			info.addRole("admin");
		}
		if("user".equals(principal)){
			info.addRole("tester");
		}
		
		
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {

		// 把 AuthenticationToken 强转为UsernamePasswordToken
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;

		// 从UsernamePasswordToken 中获取username， 但不需要获取password
		String username = upToken.getUsername();

		// 利用username调用到方法从数据库中获取对应的用户信息
		System.out.println("利用username:" + username + "从数据库中获取对应的用户信息");
		if ("BBB".equals(username)) {

			throw new UnknownAccountException("------->>>>");
		}

		// 把用户信息封装为SimpleAuthenticationInfo 对象返回
		// 以下信息来源于数据表
		// 实际登录信息可以为username.也可以为一个实体类的对象
		String principal = username;
		// 凭证信息。即密码
		String hashedCredentials = null;
		if ("user".equals(username)) {
			hashedCredentials = "098d2c478e9c11555ce2823231e02ec1";
		} else if ("admin".equals(username)) {
			hashedCredentials = "038bdaf98f2037b31f1e75b5b4c9b26e";
		}

		String realmName = getName();

		ByteSource credentialsSalt = ByteSource.Util.bytes(username);
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,
				hashedCredentials, credentialsSalt, realmName);

		return info;
	}
	
	public static void main(String[] args) {
		String hashAlgorithmName = "MD5";
		String credentials = "123456";
		ByteSource salt = ByteSource.Util.bytes("admin");
		int hashIterations = 1024;
		Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
		System.out.println(result);
	}

}
