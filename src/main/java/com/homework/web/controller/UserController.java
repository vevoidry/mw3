package com.homework.web.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.homework.web.pojo.Appointment;
import com.homework.web.pojo.Appointment_time;
import com.homework.web.pojo.Article;
import com.homework.web.pojo.Check_project;
import com.homework.web.pojo.Drug;
import com.homework.web.pojo.User;
import com.homework.web.service.impl.AppointmentServiceImpl;
import com.homework.web.service.impl.Appointment_timeServiceImpl;
import com.homework.web.service.impl.ArticleServiceImpl;
import com.homework.web.service.impl.Check_projectServiceImpl;
import com.homework.web.service.impl.DrugServiceImpl;
import com.homework.web.service.impl.UserServiceImpl;

@Controller
@RequestMapping("/users")
public class UserController<V> {

	@Autowired
	UserServiceImpl userServiceImpl;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	ArticleServiceImpl articleServiceImpl;
	@Autowired
	DrugServiceImpl drugServiceImpl;
	@Autowired
	Appointment_timeServiceImpl appointment_timeServiceImpl;
	@Autowired
	AppointmentServiceImpl appointmentServiceImpl;
	@Autowired
	Check_projectServiceImpl check_projectServiceImpl;

	// 注册
	@PostMapping
	public String post(User user, HttpServletRequest request) {
		// 获取用户名与密码并去除两侧空格
		String username = user.getUsername().trim();
		String password = user.getPassword().trim();
		// 判断位数是否符合要求
		if (username.length() < 6 || username.length() > 15 || password.length() < 6 || password.length() > 15) {
			throw new RuntimeException("用户名与密码的长度必须为6-15位");
		}
		// 判断输入的数据是否符合要求（必须为大小写字母或数字）
		Pattern p = Pattern.compile("[0-9A-Za-z]*");
		Matcher m1 = p.matcher(username);
		Matcher m2 = p.matcher(password);
		if (!m1.matches() || !m2.matches()) {
			throw new RuntimeException("用户名与密码只能为数字或大小写字母");
		}
		// 判断用户名是否已经被使用
		User user2 = userServiceImpl.selectByUsername(username);
		if (user2 != null) {
			throw new RuntimeException("该用户名已被使用，请重新输入");
		}
		// 根据注册信息创建新用户
		User user3 = userServiceImpl.insert(user);
		// 以下为实现自动登录功能
		// 生成Authentication
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user3.getUsername(),
				user3.getPassword());
		try {
			token.setDetails(new WebAuthenticationDetails(request));
			Authentication authenticatedUser = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
			request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
					SecurityContextHolder.getContext());
			// 设置session的user数据
			request.getSession().setAttribute("user", user3);
		} catch (Exception e) {
			System.out.println("Authentication failed: " + e.getMessage());
		}
		// 重定向
		return "redirect:/patient";
	}

	// 修改个人信息
	@PostMapping("/change")
	@ResponseBody
	public HashMap<String, String> change(User user, MultipartFile file_name, HttpServletRequest request,
			String birthday_string) throws Exception {
		User user2 = userServiceImpl.selectById(user.getId());
		user2.setGender(user.getGender());
		user2.setNickname(user.getNickname().trim());
		user2.setPhone_number(user.getPhone_number().trim());
		// 判断传输的文件名是否为空
		if (!file_name.getOriginalFilename().equals("")) {
			// 使用UUID新建随机字符串，作为文件保存时的真实命名，避免冲突
			String real_name = UUID.randomUUID().toString() + file_name.getOriginalFilename();
			// 获取用于保存上传文件的文件夹路径
			String directoryPath = ResourceUtils.getURL("src").getPath() + "main/resources/static/image/";
			// 保存文件
			file_name.transferTo(new File(directoryPath, real_name));
			user2.setImage(real_name);
			System.out.println(real_name);
		}
		if (user2.getNickname().equals("")) {
			throw new RuntimeException("姓名不可为空");
		}
		if (user2.getPhone_number().equals("")) {
			throw new RuntimeException("手机号码不可为空");
		}
		if(!birthday_string.trim().equals("")) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = simpleDateFormat.parse(birthday_string);
			user2.setBirthday(date);
		}
		User user3 = userServiceImpl.update(user2);
		request.getSession().setAttribute("user", user3);
		return new HashMap<String, String>();
	}

//	// 病人
//	@GetMapping("/patient/information/{id:[0-9]*}")
//	public String patient_information(@PathVariable Integer id, Model model) {
//		User user = userServiceImpl.selectById(id);
//		model.addAttribute("user", user);
//		return "template/user_patient_template::user_patient_information";
//	}
//
//	@GetMapping("/patient/appointment_time/select")
//	public String patient_appointment_time_select(Model model) {
//		return "template/user_patient_template::user_patient_appointment_time_select";
//	}
//
//	@GetMapping("/patient/appointment_time/select/time_day")
//	public String patient_appointment_time_select_time_day(Model model, String time_day) throws Exception {
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		Date date = simpleDateFormat.parse(time_day);
//		List<Appointment_time> appointment_timeList = appointment_timeServiceImpl.selectByTime_day(date);
//		model.addAttribute("appointment_timeList", appointment_timeList);
//		return "template/user_patient_template::user_patient_appointment_time_select_time_day";
//	}
//
//	@GetMapping("/patient/appointment/select")
//	public String patient_appointment_select(Model model, Integer id) {
//		List<Appointment> appointmentList = appointmentServiceImpl.selectByPatient_id(id);
//		model.addAttribute("appointmentList", appointmentList);
//		return "template/user_patient_template::user_patient_appointment_select";
//	}
//
//	// 工作人员
//	@GetMapping("/worker/information/{id:[0-9]*}")
//	public String worker_information(@PathVariable Integer id, Model model) {
//		User user = userServiceImpl.selectById(id);
//		model.addAttribute("user", user);
//		return "template/user_worker_template::user_worker_information";
//	}
//
//	@GetMapping("/worker/appointment_time/insert")
//	public String worker_appointment_time_insert(Model model) {
//		return "template/user_worker_template::user_worker_appointment_time_insert";
//	}
//
//	@GetMapping("/worker/appointment_time/manage/{id:[0-9]}")
//	public String worker_appointment_time_manage(Model model, @PathVariable Integer id) {
//		List<Appointment_time> appointment_timeList = appointment_timeServiceImpl.selectByWorker_id(id);
//		model.addAttribute("appointment_timeList", appointment_timeList);
//		return "template/user_worker_template::user_worker_appointment_time_manage";
//	}
//
//	@GetMapping("/worker/appointment/manage/{id:[0-9]*}")
//	public String worker_appointment_manage(Model model, @PathVariable Integer id) {
//		HashMap<Appointment_time, List<Appointment>> map = new HashMap<Appointment_time, List<Appointment>>();
//		// 取出该医生的所有挂号设置
//		List<Appointment_time> appointment_timeList = appointment_timeServiceImpl.selectByWorker_id(id);
//		if (appointment_timeList.size() != 0) {
//			Iterator<Appointment_time> iterator = appointment_timeList.iterator();
//			while (iterator.hasNext()) {
//				// 取出挂号设置所对应的病人挂号
//				Appointment_time next = iterator.next();
//				List<Appointment> appointmentList = appointmentServiceImpl.selectByAppointment_time_id(next.getId());
//				map.put(next, appointmentList);
//			}
//		}
//		model.addAttribute("map", map);
//		return "template/user_worker_template::user_worker_appointment_manage";
//	}
//
//	@GetMapping("/worker/check_list/insert/{id:[0-9]}")
//	public String worker_chech_list_insert(Model model, @PathVariable Integer id) {
//		// 取出所有检查项目
//		List<Check_project> check_projectList = check_projectServiceImpl.selectAll();
//		model.addAttribute("check_projectList", check_projectList);
//		model.addAttribute("id", id);
//		return "template/user_worker_template::user_worker_check_list_insert";
//	}
//
//	// 管理员
//	@GetMapping("/admin/information/{id:[0-9]*}")
//	public String admin_information(@PathVariable Integer id, Model model) {
//		User user = userServiceImpl.selectById(id);
//		model.addAttribute("user", user);
//		return "template/user_admin_template::user_admin_information";
//	}
//
//	@GetMapping("/admin/user/patient")
//	public String admin_user_patient(Model model) {
//		// 取出所有病人数据
//		List<User> userList = userServiceImpl.selectByRole("patient");
//		model.addAttribute("userList", userList);
//		return "template/user_admin_template::user_admin_user_patient";
//	}
//
//	@GetMapping("/admin/user/worker")
//	public String admin_user_worker(Model model) {
//		// 取出所有工作人员数据
//		List<User> userList = userServiceImpl.selectByRole("worker");
//		model.addAttribute("userList", userList);
//		return "template/user_admin_template::user_admin_user_worker";
//	}
//
//	@PostMapping("/admin/{id:[0-9]}/role/{role:.*}")
//	@ResponseBody
//	@Transactional
//	public HashMap<String, String> admin_patient_to_worker(@PathVariable Integer id, @PathVariable String role) {
//		userServiceImpl.updateRoleById(id, role);
//		return new HashMap<String, String>();
//	}
//
//	@GetMapping("/admin/workder/{id:[0-9]*}/is_leader/{is_leader}")
//	@ResponseBody
//	public HashMap<String, String> updateIs_leader(@PathVariable Integer id, @PathVariable Boolean is_leader) {
//		userServiceImpl.updateIs_leaderById(id, is_leader);
//		return new HashMap<String, String>();
//	}
//
//	@GetMapping("/admin/article/insert")
//	public String admin_article_insert(Model model) {
//		return "template/user_admin_template::user_admin_article_insert";
//	}
//
//	@GetMapping("/admin/article/manage")
//	public String admin_article_manage(Model model) {
//		// 取出所有无分类文章
//		List<Article> articleList1 = articleServiceImpl.selectByCategory(0);
//		// 取出所有科普文章
//		List<Article> articleList2 = articleServiceImpl.selectByCategory(1);
//		// 取出所有公告文章
//		List<Article> articleList3 = articleServiceImpl.selectByCategory(2);
//		// 将所有数据放入model
//		model.addAttribute("articleList1", articleList1);
//		model.addAttribute("articleList2", articleList2);
//		model.addAttribute("articleList3", articleList3);
//		return "template/user_admin_template::user_admin_article_manage";
//	}
//
//	@GetMapping("/admin/department/insert")
//	public String admin_department_insert(Model model) {
//		return "template/user_admin_template::user_admin_department_insert";
//	}
//
//	@GetMapping("/admin/department/manage")
//	public String admin_department_manage(Model model) {
//		HashMap<Department, User> departmentUserMap = new HashMap<Department, User>();
//		// 取出所有科室和相应的科长
//		List<Department> departmentList = departmentServiceImpl.selectAll();
//		Iterator<Department> iterator = departmentList.iterator();
//		while (iterator.hasNext()) {
//			Department next = iterator.next();
//			User user = userServiceImpl.selectByDepartment_idIs_leader(next.getId(), true);
//			departmentUserMap.put(next, user);
//		}
//		// 将所有数据放入model
//		model.addAttribute("departmentUserMap", departmentUserMap);
//		return "template/user_admin_template::user_admin_department_manage";
//	}
//
//	@GetMapping("/admin/drug/insert")
//	public String admin_drug_insert(Model model) {
//		return "template/user_admin_template::user_admin_drug_insert";
//	}
//
//	@GetMapping("/admin/drug/manage")
//	public String admin_drug_manage(Model model) {
//		// 取出所有药品
//		List<Drug> drugList = drugServiceImpl.selectAll();
//		model.addAttribute("drugList", drugList);
//		return "template/user_admin_template::user_admin_drug_manage";
//	}
//
//	@GetMapping("/admin/disease1/insert")
//	public String admin_disease1_insert(Model model) {
//		return "template/user_admin_template::user_admin_disease1_insert";
//	}
//
//	@GetMapping("/admin/disease1/manage")
//	public String admin_disease1_manage(Model model) {
//		// 取出所有疾病分类
////		List<Disease1> disease1List = disease1ServiceImpl.selectAll();
////		model.addAttribute("disease1List", disease1List);
//		return "template/user_admin_template::user_admin_disease1_manage";
//	}
//
//	@GetMapping("/admin/disease2/insert")
//	public String admin_disease2_insert(Model model) {
//		// 取出所有疾病分类
////		List<Disease1> disease1List = disease1ServiceImpl.selectAll();
////		model.addAttribute("disease1List", disease1List);
//		return "template/user_admin_template::user_admin_disease2_insert";
//	}
//
//	@GetMapping("/admin/disease2/manage")
//	public String admin_disease2_manage(Model model) {
////		HashMap<Disease1, List<Disease2>> map = new HashMap<Disease1, List<Disease2>>();
////		// 取出所有疾病分类
////		List<Disease1> disease1List = disease1ServiceImpl.selectAll();
////		if (disease1List.size() != 0) {
////			Iterator<Disease1> iterator = disease1List.iterator();
////			while (iterator.hasNext()) {
////				Disease1 next = iterator.next();
////				// 根据疾病分类取出所有疾病
////				List<Disease2> disease2List = disease2ServiceImpl.selectByDisease1_id(next.getId());
////				map.put(next, disease2List);
////			}
////		}
////		model.addAttribute("map", map);
//		return "template/user_admin_template::user_admin_disease2_manage";
//	}
//
//	@GetMapping("/admin/check_project/insert")
//	public String admin_check_project_insert(Model model) {
//		// 取出所有科室
//		List<Department> departmentList = departmentServiceImpl.selectAll();
//		model.addAttribute("departmentList", departmentList);
//		return "template/user_admin_template::user_admin_check_project_insert";
//	}
//
//	@GetMapping("/admin/check_project/manage")
//	public String admin_check_project_manage(Model model) {
//		HashMap<Department, List<Check_project>> map = new HashMap<Department, List<Check_project>>();
//		// 取出所有科室
//		List<Department> departmentList = departmentServiceImpl.selectAll();
//		if (departmentList.size() != 0) {
//			Iterator<Department> iterator = departmentList.iterator();
//			while (iterator.hasNext()) {
//				Department next = iterator.next();
//				// 根据科室取出其下所有检查科目
//				List<Check_project> check_projectList = check_projectServiceImpl.selectByDepartment_id(next.getId());
//				map.put(next, check_projectList);
//			}
//		}
//		model.addAttribute("map", map);
//		return "template/user_admin_template::user_admin_check_project_manage";
//	}
}
