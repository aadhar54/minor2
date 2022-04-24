package com.aadhar.minor;

import com.aadhar.minor.models.Admin;
import com.aadhar.minor.models.MyUser;
import com.aadhar.minor.requests.AdminCreateRequest;
import com.aadhar.minor.services.AdminService;
import com.aadhar.minor.services.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SpringBootApplication
public class MinorApplication implements CommandLineRunner {

	@Autowired
	AdminService adminService;

	@Autowired
	MyUserService myUserService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Value("${auth.delimeter.value}")
	private String DELIMETER;

	@Value("${auth.admin}")
	private String ADMIN_AUTHORITY;

	@Value("${auth.bookviwer}")
	private String BOOK_VIEWER_AUTHORITY;

	public static void main(String[] args) {
		SpringApplication.run(MinorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		MyUser myUser = MyUser.builder()
//				.username("supadmin")
//				.password(passwordEncoder.encode("123"))
//				.authorities(ADMIN_AUTHORITY+DELIMETER+BOOK_VIEWER_AUTHORITY)
//				.build();
//
//		myUser = myUserService.saveMyUser(myUser);
//
//		Admin admin = Admin.builder()
//				.name("Supa")
//				.email("supa@admin.com")
//				.myUser(myUser)
//				.build();
//
//		adminService.saveOrUpdateAdmin(admin);
	}
}
