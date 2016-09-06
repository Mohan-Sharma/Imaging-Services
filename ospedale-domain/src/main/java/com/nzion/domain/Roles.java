package com.nzion.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.nzion.util.Infrastructure;
import com.nzion.util.UtilValidator;

/**
 * @author Sandeep Prusty
 * Dec 10, 2010
 */
public class Roles {
    //549755813888
	public static final long SUPER_ADMIN = 			0x0800000000000000l; // 576460752303423488
	public static final long ADMIN = 				0x0080000000000000l; // 36028797018963968
	public static final long PROVIDER = 			0x0008000000000000l; // 2251799813685248
	public static final long MEDICAL_ASSISTANT = 	0x0000800000000000l; // 140737488355328
	public static final long NURSE = 				0x0000080000000000l; // 8796093022208
	public static final long TECHNICIAN = 			0x0000008000000000l; // 549755813888
	public static final long BILLING = 				0x0000000800000000l; // 34359738368
	public static final long RECEPTION = 			0x0000000080000000l; // 2147483648
	public static final long CASE_MANAGER = 		0x0000000008000000l; // 134217728
	//public static final long ADJUSTER = 			0x0000000000800000l; // 8388608
	public static final long PATIENT = 				0x0000000000080000l; // 524288
	public static final long EMERGENCY_ACCESS = 	0x0000000000008000l; // 32768
	public static final long HOUSE_KEEPING = 		0x0000000000000800l; // 2048
	public static final long ORDER_MANAGEMENT =     0x0000000000000080l; // 128
	public static final long STORE_MANAGEMENT =   	0x0000000000000008l; // 8
	public static final long PHLEBOTOMIST =  		0x0000000000800000l;

	public static final long[] PRACTICE_DEPENDENT_ROLES = 
		new long[]{ADMIN, PROVIDER, MEDICAL_ASSISTANT, PATIENT, NURSE, BILLING, CASE_MANAGER, RECEPTION, TECHNICIAN, STORE_MANAGEMENT,HOUSE_KEEPING,ORDER_MANAGEMENT,PHLEBOTOMIST};

	public static final long[] PRACTICE_INDEPENDENT_ROLES = new long[]{SUPER_ADMIN};
	
	private static final Map<String, Long> ROLENAMEANDVALUEMAP = new TreeMap<String, Long>(String.CASE_INSENSITIVE_ORDER);
	
	private static final Map<Long, String> ROLEVALUEANDNAMEMAP = new HashMap<Long, String>();

	static {
		ROLENAMEANDVALUEMAP.put("SUPER_ADMIN", SUPER_ADMIN);
		ROLENAMEANDVALUEMAP.put("ADMIN", ADMIN);
		ROLENAMEANDVALUEMAP.put("STORE_MANAGEMENT", STORE_MANAGEMENT);
		ROLENAMEANDVALUEMAP.put("PROVIDER", PROVIDER);
		ROLENAMEANDVALUEMAP.put("MEDICAL_ASSISTANT", MEDICAL_ASSISTANT);
		ROLENAMEANDVALUEMAP.put("NURSE", NURSE);
		ROLENAMEANDVALUEMAP.put("TECHNICIAN", TECHNICIAN);
		ROLENAMEANDVALUEMAP.put("BILLING", BILLING);
		ROLENAMEANDVALUEMAP.put("RECEPTION", RECEPTION);
		ROLENAMEANDVALUEMAP.put("CASE_MANAGER", CASE_MANAGER);
		//ROLENAMEANDVALUEMAP.put("ADJUSTER", ADJUSTER);
		ROLENAMEANDVALUEMAP.put("PATIENT", PATIENT);
		ROLENAMEANDVALUEMAP.put("EMERGENCY_ACCESS", EMERGENCY_ACCESS);
		ROLENAMEANDVALUEMAP.put("HOUSE_KEEPING", HOUSE_KEEPING);
		ROLENAMEANDVALUEMAP.put("ORDER_MANAGEMENT", ORDER_MANAGEMENT);
		ROLENAMEANDVALUEMAP.put("PHLEBOTOMIST", PHLEBOTOMIST);
		
		ROLEVALUEANDNAMEMAP.put(SUPER_ADMIN, "SUPER_ADMIN");
		ROLEVALUEANDNAMEMAP.put(ADMIN ,"ADMIN");
		ROLEVALUEANDNAMEMAP.put(STORE_MANAGEMENT , "STORE_MANAGEMENT");
		ROLEVALUEANDNAMEMAP.put(PROVIDER , "PROVIDER");
		ROLEVALUEANDNAMEMAP.put(MEDICAL_ASSISTANT , "MEDICAL_ASSISTANT");
		ROLEVALUEANDNAMEMAP.put(NURSE , "NURSE");
		ROLEVALUEANDNAMEMAP.put(TECHNICIAN , "TECHNICIAN");
		ROLEVALUEANDNAMEMAP.put(BILLING , "BILLING");
		ROLEVALUEANDNAMEMAP.put(RECEPTION , "RECEPTION");
		ROLEVALUEANDNAMEMAP.put(CASE_MANAGER , "CASE_MANAGER");
		//ROLEVALUEANDNAMEMAP.put(ADJUSTER , "ADJUSTER");
		ROLEVALUEANDNAMEMAP.put(PATIENT , "PATIENT");
		ROLEVALUEANDNAMEMAP.put(EMERGENCY_ACCESS, "EMERGENCY_ACCESS");
		ROLEVALUEANDNAMEMAP.put(HOUSE_KEEPING, "HOUSE_KEEPING");
		ROLEVALUEANDNAMEMAP.put(ORDER_MANAGEMENT, "ORDER_MANAGEMENT");
		ROLEVALUEANDNAMEMAP.put(PHLEBOTOMIST, "PHLEBOTOMIST");
	}
	
	public static long getRoleValue(String commaSeperatedRoleNames){
	String[] roleNames = commaSeperatedRoleNames.split(",");
	long result = 0;
	for(String role : roleNames)
		result = result | ROLENAMEANDVALUEMAP.get(role.trim());
	return result;
	}

	public static String getRoleName(Long role){
	return ROLEVALUEANDNAMEMAP.get(role);
	}
	
	public static boolean hasAllRoles(long... roles){
	return Infrastructure.getUserLogin().hasAllRoles(roles);
	}

	public static boolean hasRole(long role){
	return Infrastructure.getUserLogin().hasRole(role);
	}
	
	public static boolean hasAnyRole(long... roles){
	return Infrastructure.getUserLogin().hasAnyRole(roles);
	}
	
	public static long getMostPriorRoleForLoggedInUser(){
	return Infrastructure.getUserLogin().getAuthorization().getMostPriorRole();
	}
	
	public static void appendRole(Authorization authorization, long role, boolean append){
	if(append){
		authorization.addRole(role);
		return;
	}
	authorization.removeRole(role);
	}
	
	public  static <T extends Authorizable> List<T> filterByGrantedAuthority(Collection<T> authorizables, Authorization authorization){
	List<T> authorizeds = new ArrayList<T>();
	if(UtilValidator.isEmpty(authorizables))
		return authorizeds; 
	for(T t : authorizables)
		if(t.check(authorization))
			authorizeds.add(t);
	return authorizeds;
	}
	
	public static void main(String[] args) {
		/*Authorization  auth = new Authorization(2251799813685248L);
		
		 
		System.out.println("Xx" +auth.getRoles1(NURSE) + "\n");
		
		System.out.println( (549755813888L)| (140737488355328L) );*/
		
	System.out.println(ORDER_MANAGEMENT);
	}
}