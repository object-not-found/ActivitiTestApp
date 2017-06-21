package com.app.demo.tmp;

import com.gomeplus.meipro.common.utils.Constants;
import com.gomeplus.meipro.uc.dao.*;
import com.gomeplus.meipro.uc.domain.Permission;
import com.gomeplus.meipro.uc.domain.Role;
import com.gomeplus.meipro.uc.domain.Staff;
import com.gomeplus.meipro.uc.domain.User;
import com.gomeplus.meipro.uc.vo.StaffInfo;
import com.gomeplus.meipro.uc.vo.UserInfo;
import com.google.common.collect.Lists;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

public class MeiproRealm extends AuthorizingRealm {
	public static final Logger logger =  LoggerFactory.getLogger(MeiproRealm.class);
	 
	@Resource
	private IUserDao userDao;
	@Resource
	private ICompanyDao companyDao;
	@Resource
	private IStaffDao staffDao;
    @Resource
    private IRoleDao roleDao;
	@Resource
	private IPermissionDao permissionDao;

	/**
	 * 登陆认证，Subject.login()会触发
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		if(authenticationToken instanceof UsernamePasswordToken){
			UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
			String mobile = token.getUsername();

			UserInfo user = getUserInfo(mobile);
			if (user == null) {
				logger.info("login mobile:{} not exist",mobile);
				throw new UnknownAccountException();
			}
			char[] password =  user.getPassword().toCharArray();
			return new SimpleAuthenticationInfo(user, password, ByteSource.Util.bytes(user.getSalt().getBytes()), this.getName());
		}
		return null;

	}

	public UserInfo getUserInfo(String mobile){
		User user = userDao.findByMobile(mobile);
		if (user == null)
			return null;
		Long userId = user.getId();
		List<Staff> staffList = staffDao.getStaffs(userId);
		List<StaffInfo> staffs = Lists.newArrayList();
		staffList.forEach(staff -> {
			StaffInfo staffInfo = new StaffInfo();
			staffInfo.setId(staff.getId());
			staffInfo.setCompanyId(staff.getCompanyId());
			staffInfo.setCompanyName(companyDao.findById(staff.getCompanyId()).getCompanyName());
			staffInfo.setEmail(staff.getEmail());
			staffInfo.setMobile(staff.getMobile());
			staffInfo.setStaffName(staff.getStaffName());
			staffInfo.setStaffNo(staff.getStaffNo());
			staffInfo.setStatus(staff.getStatus());
			staffs.add(staffInfo);
		});

		UserInfo userInfo = new UserInfo();
		BeanUtils.copyProperties(user, userInfo);
		userInfo.setStaffs(staffs);
		return userInfo;
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		if(token instanceof AppStatelessToken)
			return true;
		return super.supports(token);
	}
	/**
	 * 授权，第一次访问受限资源时触发
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Object principal = super.getAvailablePrincipal(principals);

        if (principal == null) throw new UnauthenticatedException("principal not exists !!!");
        UserInfo user = ((UserInfo) principal);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        StaffInfo staff = user.getStaffInfoByCompanyId(user.getCurrentCompanyId());
        List<Role> roleList = roleDao.findRoleByStaffId(staff.getId());

        roleList.stream().filter(role -> StringUtils.hasText(role.getRoleCode())).forEach(role -> authorizationInfo.addRole(role.getRoleCode()));

		List<Permission> permissionList = permissionDao.findPermByStaffId(staff.getId(), null);

        if (permissionList.size() > 0
                && (permissionList.stream().filter(permission -> Objects.equals(permission.getType(), Constants.ADMIN_PERMISSION_FLAG)).count() > 0)) {
            //add an virtual role "admin" to pass url filter
            //if the role is "system-admin", it won't reach here.
            authorizationInfo.addRole(Constants.VIRTUAL_ADMIN_ROLE_CODE);
        }

        //if the role is "system-admin", add an virtual role "admin" to authorizationInfo
        if(roleList.stream().filter(role -> Objects.equals(role.getRoleCode(), Constants.SYSTEM_ADMIN_ROLE_CODE)).count() > 0) {
            authorizationInfo.addRole(Constants.VIRTUAL_ADMIN_ROLE_CODE);
        }

        permissionList.stream().filter(permission -> permission.getExpression() != null && StringUtils.hasText(permission.getExpression()))
                .forEach(permission -> authorizationInfo.addStringPermission(permission.getExpression()));

		return authorizationInfo;
	}

	/**
	 * 重写权限缓存key，key为当前staffId
	 * @param principals
	 * @return
	 */
	@Override
	protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
		Object principal = super.getAvailablePrincipal(principals);

		if (principal == null) throw new UnauthenticatedException("principal not exists !!!");
		UserInfo user = ((UserInfo) principal);

		StaffInfo info = user.getStaffInfoByCompanyId(user.getCurrentCompanyId());

        if (info == null) throw new UnauthorizedException("userId:" + user.getId() + ", companyId: " + user.getCurrentCompanyId() + " not exists !!!");

		return info.getId();
	}

    /**
     * 当多授权（基于staffId） 清除授权缓存
     * @param principals
     */
    @Override
    protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        Object principal = super.getAvailablePrincipal(principals);

        if (principal == null) throw new UnauthenticatedException("principal not exists !!!");
        UserInfo user = ((UserInfo) principal);

        if (user.getStaffs() == null
                || user.getStaffs().isEmpty()) {
            super.clearCachedAuthorizationInfo(principals);
        } else {
            user.getStaffs().forEach(staffInfo -> {
                user.setCurrentCompanyId(staffInfo.getCompanyId());
                super.clearCachedAuthorizationInfo(principals);
            });
        }

    }
}
