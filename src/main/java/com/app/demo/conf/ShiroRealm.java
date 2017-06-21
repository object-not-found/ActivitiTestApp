package com.app.demo.conf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.app.demo.domain.Permission;
import com.app.demo.domain.Role;
import com.app.demo.domain.User;
import com.app.demo.service.PermissionService;
import com.app.demo.service.RoleService;
import com.app.demo.service.UserService;

public class ShiroRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;
	
	/**
	 * 获取认证成功后的角色、权限等信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Object principal = super.getAvailablePrincipal(principals);
		User user = (User) principal;
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//加载角色和权限
		List<Integer> roleIds = userService.findRoleIds(user.getId());
		if(roleIds != null && roleIds.size() > 0){
			List<Role> roleList = roleService.findByRoleIds(roleIds);
			for(Role role: roleList){
				info.addRole(role.getRoleName());
				List<Integer> permissionIds = roleService.findPermissionIds(role.getId());
				if(permissionIds != null && permissionIds.size() > 0){
					List<Permission> permissionList = permissionService.findByPermissionIds(permissionIds);
					for(Permission per: permissionList){
						info.addStringPermission(per.getPermissionName());
					}
				}
			}
		}
		return info;
	} 

	/**
	 * 验证当前登录的subject
	 * Controller中login方法在执行subject.login()时执行此方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken userToken = (UsernamePasswordToken) token;
		Map<String, Object> map = new HashMap<>();
		map.put("name", userToken.getUsername());
		User user = userService.select(map);
		if(user == null){
			//未找到账号
			throw new UnknownAccountException();
		}
		
		//交给AuthenticationRealm使用CredentialsMatcher进行密码匹配，可以进行自定义
		SimpleAuthenticationInfo info  = new SimpleAuthenticationInfo(
				user, 
				user.getPassword(), 
				ByteSource.Util.bytes(user.getSalt()),   //明文密码访问不需要此句
				getName());  //realmClass name
		return info;
	}
	
}
