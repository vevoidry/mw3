package com.homework.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homework.web.pojo.Appointment;
import com.homework.web.pojo.Appointment_time;
import com.homework.web.pojo.Article;
import com.homework.web.pojo.Check_department;
import com.homework.web.pojo.Check_list;
import com.homework.web.pojo.Check_project;
import com.homework.web.pojo.Diagnosis;
import com.homework.web.pojo.Disease;
import com.homework.web.pojo.Drug;
import com.homework.web.pojo.Prescription;
import com.homework.web.pojo.User;
import com.homework.web.pojo.json.Disease_nameQuantity;
import com.homework.web.pojo.json.Drug_nameQuantity;
import com.homework.web.repository.Appointment_timeRepository;
import com.homework.web.repository.DiagnosisRepository;
import com.homework.web.repository.PrescriptionRepository;
import com.homework.web.service.impl.AppointmentServiceImpl;
import com.homework.web.service.impl.Appointment_timeServiceImpl;
import com.homework.web.service.impl.ArticleServiceImpl;
import com.homework.web.service.impl.Check_departmentServiceImpl;
import com.homework.web.service.impl.Check_listServiceImpl;
import com.homework.web.service.impl.Check_projectServiceImpl;
import com.homework.web.service.impl.DiagnosisServiceImpl;
import com.homework.web.service.impl.DiseaseServiceImpl;
import com.homework.web.service.impl.DrugServiceImpl;
import com.homework.web.service.impl.PrescriptionServiceImpl;
import com.homework.web.service.impl.UserServiceImpl;

@Controller
public class PageController {

	@Autowired
	ArticleServiceImpl articleServiceImpl;
	@Autowired
	UserServiceImpl userServiceImpl;
	@Autowired
	Check_departmentServiceImpl check_departmentServiceImpl;
	@Autowired
	Check_projectServiceImpl check_projectServiceImpl;
	@Autowired
	DrugServiceImpl drugServiceImpl;
	@Autowired
	DiseaseServiceImpl diseaseServiceImpl;
	@Autowired
	Appointment_timeServiceImpl appointment_timeServiceImpl;
	@Autowired
	AppointmentServiceImpl appointmentServiceImpl;
	@Autowired
	Check_listServiceImpl check_listServiceImpl;
	@Autowired
	DiagnosisServiceImpl diagnosisServiceImpl;
	@Autowired
	PrescriptionServiceImpl prescriptionServiceImpl;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/login_register")
	public String login_register() {
		return "template/index_template::login_register";
	}

	@GetMapping("/popular_science")
	public String popular_science(Model model) {
		List<Article> articleList = articleServiceImpl.selectByCategory(1);
		model.addAttribute("articleList", articleList);
		return "template/index_template::popular_science";
	}

	@GetMapping("/public_notice")
	public String public_notice(Model model) {
		List<Article> articleList = articleServiceImpl.selectByCategory(2);
		model.addAttribute("articleList", articleList);
		return "template/index_template::public_notice";
	}

	@GetMapping("/report_form")
	public String report_form() {
		return "template/index_template::report_form";
	}

	@GetMapping("/patient")
	public String patient() {
		return "patient";
	}

	@GetMapping("/worker")
	public String worker() {
		return "worker";
	}

	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}

	// 管理员
	@GetMapping("/admin/information/{id:[0-9]*}")
	public String admin_information(@PathVariable Integer id, Model model) {
//		User user = userServiceImpl.selectById(id);
//		model.addAttribute("user", user);
		return "template/admin_template::admin_information";
	}

	@GetMapping("/admin/patient")
	public String admin_patient(Model model) {
		// 取出病人数据
		List<User> userList = userServiceImpl.selectByRole("patient");
		model.addAttribute("userList", userList);
		// 取出疾病的二级分类数据
		ArrayList<Disease> diseaseList = new ArrayList<Disease>();
		List<Disease> diseaseList2 = diseaseServiceImpl.selectByRank(1);
		Iterator<Disease> iterator = diseaseList2.iterator();
		while (iterator.hasNext()) {
			Disease next = iterator.next();
			List<Disease> diseaseList3 = diseaseServiceImpl.selectByParent_id(next.getId());
			diseaseList.addAll(diseaseList3);
		}
		model.addAttribute("diseaseList", diseaseList);
		return "template/admin_template::admin_patient";
	}

	@PostMapping("/admin/role_to_worker/{id:[0-9]*}")
	@ResponseBody
	@Transactional
	public HashMap<String, String> admin_role_to_worker(@PathVariable Integer id, Model model, Integer department_id) {
		if (department_id == null) {
			throw new RuntimeException("请先设置好科室");
		}
		User user = userServiceImpl.selectById(id);
		user.setRole("worker");
		user.setDepartment_id(department_id);
		user.setHiredate(new Date());
		userServiceImpl.update(user);
		return new HashMap<String, String>();
	}

	@GetMapping("/admin/worker")
	public String admin_worker(Model model) {
		HashMap<User, String> userNameMap = new HashMap<User, String>();
		List<User> userList = userServiceImpl.selectByRole("worker");
		Iterator<User> iterator = userList.iterator();
		while (iterator.hasNext()) {
			User next = iterator.next();
			Disease disease = diseaseServiceImpl.selectById(next.getDepartment_id());
			Disease disease2 = diseaseServiceImpl.selectById(disease.getParent_id());
			String name = disease2.getName() + "-" + disease.getName();
			userNameMap.put(next, name);
		}
		model.addAttribute("userNameMap", userNameMap);
		return "template/admin_template::admin_worker";
	}

	@GetMapping("/admin/article_manage")
	public String admin_article_manage(Model model) {
		List<Article> articleList1 = articleServiceImpl.selectByCategory(0);
		List<Article> articleList2 = articleServiceImpl.selectByCategory(1);
		List<Article> articleList3 = articleServiceImpl.selectByCategory(2);
		model.addAttribute("articleList1", articleList1);
		model.addAttribute("articleList2", articleList2);
		model.addAttribute("articleList3", articleList3);
		return "template/admin_template::admin_article_manage";
	}

	@GetMapping("/admin/check_department_manage")
	public String admin_check_department_manage(Model model) {
		HashMap<Check_department, User> map = new HashMap<Check_department, User>();
		List<Check_department> check_departmentList = check_departmentServiceImpl.selectAll();
		Iterator<Check_department> iterator = check_departmentList.iterator();
		while (iterator.hasNext()) {
			Check_department next = iterator.next();
			if (next.getLeader_id() != null) {
				User user = userServiceImpl.selectById(next.getLeader_id());
				map.put(next, user);
			} else {
				map.put(next, new User());
			}
		}
		model.addAttribute("map", map);
		// 取出所有工作人员信息
		List<User> userList = userServiceImpl.selectByRole("worker");
		model.addAttribute("userList", userList);
		return "template/admin_template::admin_check_department_manage";
	}

	@GetMapping("/admin/check_department/leader")
	@ResponseBody
	public HashMap<String, String> admin_check_department_leader(Model model, Integer worker_id,
			Integer check_department_id) {
		// 保存修改
		Check_department check_department = check_departmentServiceImpl.selectById(check_department_id);
		check_department.setLeader_id(worker_id);
		check_departmentServiceImpl.update(check_department);
		// 刷新页面
		return new HashMap<String, String>();
	}

	@GetMapping("/admin/check_project_manage")
	public String admin_check_project_manage(Model model) {
		HashMap<Check_department, List<Check_project>> map = new HashMap<Check_department, List<Check_project>>();
		List<Check_department> check_departmentList = check_departmentServiceImpl.selectAll();
		Iterator<Check_department> iterator = check_departmentList.iterator();
		while (iterator.hasNext()) {
			Check_department next = iterator.next();
			List<Check_project> check_projectList = check_projectServiceImpl.selectByCheck_department_id(next.getId());
			map.put(next, check_projectList);
		}
		model.addAttribute("map", map);
		return "template/admin_template::admin_check_project_manage";
	}

	@GetMapping("/admin/drug_manage")
	public String admin_drug_manage(Model model) {
		List<Drug> drugList = drugServiceImpl.selectAll();
		model.addAttribute("drugList", drugList);
		return "template/admin_template::admin_drug_manage";
	}

	@GetMapping("/admin/disease_manage/{id:[0-9]*}")
	public String admin_disease_manage(@PathVariable Integer id, Model model) {
		if (id == 1) {
			// 取出所有初级分类
			List<Disease> diseaseList = diseaseServiceImpl.selectByRank(1);
			model.addAttribute("diseaseList", diseaseList);
			return "template/admin_template::admin_disease_manage1";
		} else if (id == 2) {
			HashMap<Disease, List<Disease>> map = new HashMap<Disease, List<Disease>>();
			List<Disease> diseaseList = diseaseServiceImpl.selectByRank(1);// 取出所有初级分类
			Iterator<Disease> iterator = diseaseList.iterator();
			// 循环每一个初级分类并取出相应的所有二级分类
			while (iterator.hasNext()) {
				Disease next = iterator.next();
				List<Disease> diseaseList2 = diseaseServiceImpl.selectByParent_id(next.getId());
				// 将初级分类和二级分类放入map
				map.put(next, diseaseList2);
			}
			model.addAttribute("map", map);
			return "template/admin_template::admin_disease_manage2";
		} else {
			HashMap<Disease, HashMap<Disease, List<Disease>>> map = new HashMap<Disease, HashMap<Disease, List<Disease>>>();
			List<Disease> diseaseList = diseaseServiceImpl.selectByRank(1);// 取出所有初级分类
			Iterator<Disease> iterator = diseaseList.iterator();
			// 循环每一个初级分类并取出相应的所有二级分类
			while (iterator.hasNext()) {
				HashMap<Disease, List<Disease>> map2 = new HashMap<Disease, List<Disease>>();
				Disease next = iterator.next();
				List<Disease> diseaseList2 = diseaseServiceImpl.selectByParent_id(next.getId());
				Iterator<Disease> iterator2 = diseaseList2.iterator();
				while (iterator2.hasNext()) {
					Disease next2 = iterator2.next();
					List<Disease> diseaseList3 = diseaseServiceImpl.selectByParent_id(next2.getId());
					map2.put(next2, diseaseList3);
				}
				map.put(next, map2);
			}
			model.addAttribute("map", map);
			return "template/admin_template::admin_disease_manage3";
		}
	}

	@GetMapping("/admin/disease_manage/api/{id:[0-9]*}")
	public String admin_disease_manage_api(Model model, @PathVariable Integer id) {
		List<Disease> diseaseList = diseaseServiceImpl.selectByParent_id(id);
		model.addAttribute("diseaseList", diseaseList);
		return "template/admin_template::admin_disease_manage_api";
	}

	// 医护人员
	@GetMapping("/worker/information/{id:[0-9]*}")
	public String worker_information(@PathVariable Integer id, Model model) {
		User user = userServiceImpl.selectById(id);
		Disease disease = diseaseServiceImpl.selectById(user.getDepartment_id());
		Disease disease2 = diseaseServiceImpl.selectById(disease.getParent_id());
		String name = disease2.getName() + "-" + disease.getName();
		model.addAttribute("name", name);
		return "template/worker_template::worker_information";
	}

	@GetMapping("/worker/appointment_time/{id:[0-9]*}")
	public String worker_appointment_time(@PathVariable Integer id, Model model) {
		List<Appointment_time> appointment_timeList = appointment_timeServiceImpl.selectByWorker_id(id);
		model.addAttribute("appointment_timeList", appointment_timeList);
		return "template/worker_template::worker_appointment_time_manage";
	}

	@GetMapping("/worker/appointment/manage/{id:[0-9]*}")
	public String worker_appointment_manage(@PathVariable Integer id, Model model) {
		HashMap<Appointment_time, HashMap<Appointment, User>> map = new HashMap<Appointment_time, HashMap<Appointment, User>>();
		List<Appointment_time> appointment_timeList = appointment_timeServiceImpl.selectByWorker_id(id);
		Iterator<Appointment_time> iterator = appointment_timeList.iterator();
		while (iterator.hasNext()) {
			Appointment_time next = iterator.next();
			List<Appointment> appointmentList = appointmentServiceImpl.selectByAppointment_time_id(next.getId());
			Iterator<Appointment> iterator2 = appointmentList.iterator();
			HashMap<Appointment, User> map2 = new HashMap<Appointment, User>();
			while (iterator2.hasNext()) {
				Appointment next2 = iterator2.next();
				User user = userServiceImpl.selectById(next2.getPatient_id());
				map2.put(next2, user);
			}
			map.put(next, map2);
		}
		model.addAttribute("map", map);
		return "template/worker_template::worker_appointment_manage";
	}

	@GetMapping("/worker/check_list/{appointment_id:[0-9]*}")
	public String worker_check_list(@PathVariable Integer appointment_id, Model model) {
		// 获取检查项目数据
		List<Check_project> check_projectList = check_projectServiceImpl.selectAll();
		// 获取检查单数据
		Check_list check_list = check_listServiceImpl.selectByAppointment_id(appointment_id);
		if (check_list != null) {
			Appointment appointment = appointmentServiceImpl.selectById(check_list.getAppointment_id());
			User user = userServiceImpl.selectById(appointment.getPatient_id());
			Check_project check_project = check_projectServiceImpl.selectById(check_list.getCheck_project_id());
			model.addAttribute("user", user);
			model.addAttribute("check_project", check_project);
		}
		model.addAttribute("check_projectList", check_projectList);
		model.addAttribute("check_list", check_list);
		model.addAttribute("appointment_id", appointment_id);
		return "template/worker_template::worker_check_list";
	}

	@GetMapping("/worker/diagnosis/{appointment_id:[0-9]*}")
	public String worker_diagnosis(@PathVariable Integer appointment_id, Model model) {
		// 取出疾病的所有初级分类
		List<Disease> diseaseList = diseaseServiceImpl.selectByRank(1);
		// 取出诊疗单（若不存在则为null）
		Diagnosis diagnosis = diagnosisServiceImpl.selectByAppointment_id(appointment_id);
		if (diagnosis != null) {
			// 获取病人信息
			Appointment appointment = appointmentServiceImpl.selectById(appointment_id);
			User user = userServiceImpl.selectById(appointment.getPatient_id());
			// 获取疾病信息
			Disease disease = diseaseServiceImpl.selectById(diagnosis.getDisease_id());
			Disease disease2 = diseaseServiceImpl.selectById(disease.getParent_id());
			Disease disease3 = diseaseServiceImpl.selectById(disease2.getParent_id());
			String diseaseName = disease3.getName() + "-" + disease2.getName() + "-" + disease.getName();
			model.addAttribute("user", user);
			model.addAttribute("diseaseName", diseaseName);
		}
		model.addAttribute("diseaseList", diseaseList);
		model.addAttribute("diagnosis", diagnosis);
		model.addAttribute("appointment_id", appointment_id);
		return "template/worker_template::worker_diagnosis";
	}

	@GetMapping("/worker/diagnosis/disease2/{parent_id:[0-9]*}")
	public String worker_diagnosis_disease2(@PathVariable Integer parent_id, Model model) {
		List<Disease> diseaseList = diseaseServiceImpl.selectByParent_id(parent_id);
		model.addAttribute("diseaseList", diseaseList);
		return "template/worker_template::worker_diagnosis_disease2";
	}

	@GetMapping("/worker/diagnosis/disease3/{parent_id:[0-9]*}")
	public String worker_diagnosis_disease3(@PathVariable Integer parent_id, Model model) {
		List<Disease> diseaseList = diseaseServiceImpl.selectByParent_id(parent_id);
		model.addAttribute("diseaseList", diseaseList);
		return "template/worker_template::worker_diagnosis_disease3";
	}

	@GetMapping("/worker/prescription/{appointment_id:[0-9]*}")
	public String worker_prescription(@PathVariable Integer appointment_id, Model model) {
		// 取出所有药物
		List<Drug> drugList = drugServiceImpl.selectAll();
		HashMap<Prescription, Drug> map = new HashMap<Prescription, Drug>();
		// 取出该处方所有药物数据
		List<Prescription> prescriptionList = prescriptionServiceImpl.selectByAppointment_id(appointment_id);
		Iterator<Prescription> iterator = prescriptionList.iterator();
		while (iterator.hasNext()) {
			Prescription next = iterator.next();
			Drug drug = drugServiceImpl.selectById(next.getDrug_id());
			map.put(next, drug);
		}
		model.addAttribute("drugList", drugList);
		model.addAttribute("map", map);
		model.addAttribute("appointment_id", appointment_id);
		return "template/worker_template::worker_prescription";
	}

	@GetMapping("/worker/leader/{id:[0-9]*}")
	public String worker_leader(@PathVariable Integer id, Model model) {
		List<Check_department> check_departmentList = check_departmentServiceImpl.selectByLeader_id(id);
		model.addAttribute("check_departmentList", check_departmentList);
		return "leader";
	}

	@GetMapping("/worker/check_department/leader/{check_department_id:[0-9]*}")
	public String worker_check_department_leader(@PathVariable Integer check_department_id, Model model) {
		HashMap<Check_list, HashMap<User, Check_project>> map = new HashMap<Check_list, HashMap<User, Check_project>>();
		ArrayList<Check_list> check_listList = new ArrayList<Check_list>();
		// 获取该检查室的所有检查项目
		List<Check_project> check_projectList = check_projectServiceImpl
				.selectByCheck_department_id(check_department_id);
		// 获取每个检查项目下的所有检查单
		Iterator<Check_project> iterator = check_projectList.iterator();
		while (iterator.hasNext()) {
			Check_project next = iterator.next();
			List<Check_list> check_listList2 = check_listServiceImpl.selectByCheck_project_id(next.getId());
			check_listList.addAll(check_listList2);
		}
		Iterator<Check_list> iterator2 = check_listList.iterator();
		while (iterator2.hasNext()) {
			Check_list next = iterator2.next();
			Appointment appointment = appointmentServiceImpl.selectById(next.getAppointment_id());
			User user = userServiceImpl.selectById(appointment.getPatient_id());
			Check_project check_project = check_projectServiceImpl.selectById(next.getCheck_project_id());
			HashMap<User, Check_project> map2 = new HashMap<User, Check_project>();
			map2.put(user, check_project);
			map.put(next, map2);
		}
		model.addAttribute("map", map);
		model.addAttribute("check_department_id", check_department_id);
		return "template/leader_template::leader_check_list";
	}

	@GetMapping("/worker/check_list/update_result")
	@ResponseBody
	public HashMap<String, String> updateResult(String result, Integer check_list_id) {
		Check_list check_list = check_listServiceImpl.selectById(check_list_id);
		check_list.setResult(result);
		check_list.setTime_real(new Date());
		check_listServiceImpl.update(check_list);
		return new HashMap<String, String>();
	}

	// 病人
	@GetMapping("/patient/information/{id:[0-9]*}")
	public String patient_information(@PathVariable Integer id, Model model) {
		User user = userServiceImpl.selectById(id);
		model.addAttribute("user", user);
		return "template/patient_template::patient_information";
	}

	@GetMapping("/patient/appointment_time/select")
	public String patient_appointment_time_select(Model model) {
		return "template/patient_template::patient_appointment_time_select";
	}

	@GetMapping("/patient/appointment_time/select/time_day")
	public String patient_appointment_time_select_time_day(Model model, String time_day) throws Exception {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = simpleDateFormat.parse(time_day);
		List<Appointment_time> appointment_timeList = appointment_timeServiceImpl.selectByTime_day(date);
		Iterator<Appointment_time> iterator = appointment_timeList.iterator();
		HashMap<Appointment_time, User> map = new HashMap<Appointment_time, User>();
		while (iterator.hasNext()) {
			Appointment_time next = iterator.next();
			User user = userServiceImpl.selectById(next.getWorker_id());
			map.put(next, user);
		}
		model.addAttribute("map", map);
		return "template/patient_template::patient_appointment_time_select_time_day";
	}

	@GetMapping("/patient/appointment/select/{id:[0-9]*}")
	public String patient_appointment_select(Model model, @PathVariable Integer id) {
		HashMap<Appointment, HashMap<Appointment_time, User>> map = new HashMap<Appointment, HashMap<Appointment_time, User>>();
		List<Appointment> appointmentList = appointmentServiceImpl.selectByPatient_id(id);
		Iterator<Appointment> iterator = appointmentList.iterator();
		while (iterator.hasNext()) {
			Appointment next = iterator.next();
			Appointment_time appointment_time = appointment_timeServiceImpl.selectById(next.getAppointment_time_id());
			User user = userServiceImpl.selectById(appointment_time.getWorker_id());
			HashMap<Appointment_time, User> map2 = new HashMap<Appointment_time, User>();
			map2.put(appointment_time, user);
			map.put(next, map2);
		}
		model.addAttribute("map", map);
		return "template/patient_template::patient_appointment_select";
	}

	@GetMapping("/patient/appointment/check_list/{id:[0-9]*}")
	public String patient_appointment_check_list(Model model, @PathVariable Integer id) {
		Check_list check_list = check_listServiceImpl.selectByAppointment_id(id);
		Check_project check_project = check_projectServiceImpl.selectById(check_list.getCheck_project_id());
		model.addAttribute("check_list", check_list);
		model.addAttribute("check_project", check_project);
		return "template/patient_template::patient_appointment_check_list";
	}

	@GetMapping("/patient/appointment/diagnosis/{id:[0-9]*}")
	public String patient_appointment_diagnosis(Model model, @PathVariable Integer id) {
		Diagnosis diagnosis = diagnosisServiceImpl.selectByAppointment_id(id);
		Disease disease = diseaseServiceImpl.selectById(diagnosis.getDisease_id());
		Disease disease2 = diseaseServiceImpl.selectById(disease.getParent_id());
		Disease disease3 = diseaseServiceImpl.selectById(disease2.getParent_id());
		String disease_string = disease3.getName() + "-" + disease2.getName() + "-" + disease.getName();
		model.addAttribute("diagnosis", diagnosis);
		model.addAttribute("disease_string", disease_string);
		return "template/patient_template::patient_appointment_diagnosis";
	}

	@GetMapping("/patient/appointment/prescription/{id:[0-9]*}")
	public String patient_appointment_prescription(Model model, @PathVariable Integer id) {
		HashMap<Prescription, Drug> map = new HashMap<Prescription, Drug>();
		List<Prescription> prescriptionList = prescriptionServiceImpl.selectByAppointment_id(id);
		Iterator<Prescription> iterator = prescriptionList.iterator();
		while (iterator.hasNext()) {
			Prescription next = iterator.next();
			Drug drug = drugServiceImpl.selectById(next.getDrug_id());
			map.put(next, drug);
		}
		model.addAttribute("map", map);
		return "template/patient_template::patient_appointment_prescription";
	}

	@GetMapping("/user/information/edit")
	public String user_information_edit(Model model) {
		return "template/index_template::user_information_edit";
	}

	@GetMapping("/index/statistics/drug")
	@ResponseBody
	public List<Drug_nameQuantity> index_statistics_drug() {
		List<Drug_nameQuantity> list = new ArrayList<Drug_nameQuantity>();
		List<Object[]> drug_statisticsList = prescriptionServiceImpl.drug_statistics();
		for (int i = 0; i < drug_statisticsList.size(); i++) {
			Object[] next = drug_statisticsList.get(i);
			Integer drug_id = Integer.parseInt(next[0] + "");
			Integer quantity = Integer.parseInt(next[1] + "");
			Drug drug = drugServiceImpl.selectById(drug_id);
			System.out.println(drug.getName() + ":" + quantity);
			Drug_nameQuantity drug_nameQuantity = new Drug_nameQuantity();
			drug_nameQuantity.setDrug_name(drug.getName());
			drug_nameQuantity.setQuantity(quantity);
			list.add(drug_nameQuantity);
		}
		return list;
	}

	@GetMapping("/index/statistics/drug/year/{year:[0-9]*}")
	@ResponseBody
	public List<Drug_nameQuantity> index_statistics_drug_year(@PathVariable String year) {
		List<Drug_nameQuantity> list = new ArrayList<Drug_nameQuantity>();
		List<Object[]> drug_statisticsList = prescriptionServiceImpl.drug_statisticsByYear(year);
		for (int i = 0; i < drug_statisticsList.size(); i++) {
			Object[] next = drug_statisticsList.get(i);
			Integer drug_id = Integer.parseInt(next[0] + "");
			Integer quantity = Integer.parseInt(next[1] + "");
			Drug drug = drugServiceImpl.selectById(drug_id);
			System.out.println(drug.getName() + ":" + quantity);
			Drug_nameQuantity drug_nameQuantity = new Drug_nameQuantity();
			drug_nameQuantity.setDrug_name(drug.getName());
			drug_nameQuantity.setQuantity(quantity);
			list.add(drug_nameQuantity);
		}
		return list;
	}

	@GetMapping("/index/statistics/drug/year/{year:[0-9]*}/month/{month:[0-9]*}")
	@ResponseBody
	public List<Drug_nameQuantity> index_statistics_drug_year_month(@PathVariable String year,
			@PathVariable String month) {
		if (month.length() <= 1) {
			month = "0" + month;
		}
		String year_month = year + month;
		List<Drug_nameQuantity> list = new ArrayList<Drug_nameQuantity>();
		List<Object[]> drug_statisticsList = prescriptionServiceImpl.drug_statisticsByYearMonth(year_month);
		for (int i = 0; i < drug_statisticsList.size(); i++) {
			Object[] next = drug_statisticsList.get(i);
			Integer drug_id = Integer.parseInt(next[0] + "");
			Integer quantity = Integer.parseInt(next[1] + "");
			Drug drug = drugServiceImpl.selectById(drug_id);
			System.out.println(drug.getName() + ":" + quantity);
			Drug_nameQuantity drug_nameQuantity = new Drug_nameQuantity();
			drug_nameQuantity.setDrug_name(drug.getName());
			drug_nameQuantity.setQuantity(quantity);
			list.add(drug_nameQuantity);
		}
		return list;
	}

	@GetMapping("/index/statistics/disease")
	@ResponseBody
	public HashMap<String, List<Disease_nameQuantity>> index_statistics_disease() {
		HashMap<String, List<Disease_nameQuantity>> map = new HashMap<String, List<Disease_nameQuantity>>();
		List<Disease_nameQuantity> list1 = new ArrayList<Disease_nameQuantity>();// 总统计
		List<Disease_nameQuantity> list2 = new ArrayList<Disease_nameQuantity>();// 男性
		List<Disease_nameQuantity> list3 = new ArrayList<Disease_nameQuantity>();// 女性
		List<Object[]> drug_statistics = diagnosisServiceImpl.disease_statistics();
		Iterator<Object[]> iterator = drug_statistics.iterator();
		while (iterator.hasNext()) {
			Object[] next = iterator.next();
			Integer disease_id = Integer.parseInt(next[0] + "");
			Integer quantity = Integer.parseInt(next[1] + "");
			Disease disease = diseaseServiceImpl.selectById(disease_id);
			String disease_name = disease.getName();
			// 总统计
			Disease_nameQuantity disease_nameQuantity1 = new Disease_nameQuantity();
			disease_nameQuantity1.setDisease_name(disease_name);
			disease_nameQuantity1.setQuantity(quantity);
			list1.add(disease_nameQuantity1);
			// 男性
			Integer man = diagnosisServiceImpl.disease_statisticsByDisease_idGender(disease_id, true);
			Disease_nameQuantity disease_nameQuantity2 = new Disease_nameQuantity();
			disease_nameQuantity2.setDisease_name(disease_name);
			disease_nameQuantity2.setQuantity(man);
			list2.add(disease_nameQuantity2);
			// 女性
			Integer woman = diagnosisServiceImpl.disease_statisticsByDisease_idGender(disease_id, false);
			Disease_nameQuantity disease_nameQuantity3 = new Disease_nameQuantity();
			disease_nameQuantity3.setDisease_name(disease_name);
			disease_nameQuantity3.setQuantity(woman);
			list3.add(disease_nameQuantity3);
		}
		map.put("list1", list1);
		map.put("list2", list2);
		map.put("list3", list3);
		return map;
	}

	@GetMapping("/index/statistics/disease/year/{year:[0-9]*}")
	@ResponseBody
	public HashMap<String, List<Disease_nameQuantity>> index_statistics_disease_year(@PathVariable String year) {
		HashMap<String, List<Disease_nameQuantity>> map = new HashMap<String, List<Disease_nameQuantity>>();
		List<Disease_nameQuantity> list1 = new ArrayList<Disease_nameQuantity>();// 总统计
		List<Disease_nameQuantity> list2 = new ArrayList<Disease_nameQuantity>();// 男性
		List<Disease_nameQuantity> list3 = new ArrayList<Disease_nameQuantity>();// 女性
		List<Object[]> drug_statistics = diagnosisServiceImpl.disease_statisticsByYear(year);
		Iterator<Object[]> iterator = drug_statistics.iterator();
		while (iterator.hasNext()) {
			Object[] next = iterator.next();
			Integer disease_id = Integer.parseInt(next[0] + "");
			Integer quantity = Integer.parseInt(next[1] + "");
			Disease disease = diseaseServiceImpl.selectById(disease_id);
			String disease_name = disease.getName();
			// 总统计
			Disease_nameQuantity disease_nameQuantity1 = new Disease_nameQuantity();
			disease_nameQuantity1.setDisease_name(disease_name);
			disease_nameQuantity1.setQuantity(quantity);
			list1.add(disease_nameQuantity1);
			// 男性
			Integer man = diagnosisServiceImpl.disease_statisticsByYearDisease_idGender(year, disease_id, true);
			Disease_nameQuantity disease_nameQuantity2 = new Disease_nameQuantity();
			disease_nameQuantity2.setDisease_name(disease_name);
			disease_nameQuantity2.setQuantity(man);
			list2.add(disease_nameQuantity2);
			// 女性
			Integer woman = diagnosisServiceImpl.disease_statisticsByYearDisease_idGender(year, disease_id, false);
			Disease_nameQuantity disease_nameQuantity3 = new Disease_nameQuantity();
			disease_nameQuantity3.setDisease_name(disease_name);
			disease_nameQuantity3.setQuantity(woman);
			list3.add(disease_nameQuantity3);
		}
		map.put("list1", list1);
		map.put("list2", list2);
		map.put("list3", list3);
		return map;
	}

	@GetMapping("/index/statistics/disease/year/{year:[0-9]*}/month/{month:[0-9]*}")
	@ResponseBody
	public HashMap<String, List<Disease_nameQuantity>> index_statistics_disease_year_month(@PathVariable String year,
			@PathVariable String month) {
		if (month.length() <= 1) {
			month = "0" + month;
		}
		String year_month = year + month;
		HashMap<String, List<Disease_nameQuantity>> map = new HashMap<String, List<Disease_nameQuantity>>();
		List<Disease_nameQuantity> list1 = new ArrayList<Disease_nameQuantity>();// 总统计
		List<Disease_nameQuantity> list2 = new ArrayList<Disease_nameQuantity>();// 男性
		List<Disease_nameQuantity> list3 = new ArrayList<Disease_nameQuantity>();// 女性
		List<Object[]> drug_statistics = diagnosisServiceImpl.disease_statisticsByYearMonth(year_month);
		Iterator<Object[]> iterator = drug_statistics.iterator();
		while (iterator.hasNext()) {
			Object[] next = iterator.next();
			Integer disease_id = Integer.parseInt(next[0] + "");
			Integer quantity = Integer.parseInt(next[1] + "");
			Disease disease = diseaseServiceImpl.selectById(disease_id);
			String disease_name = disease.getName();
			// 总统计
			Disease_nameQuantity disease_nameQuantity1 = new Disease_nameQuantity();
			disease_nameQuantity1.setDisease_name(disease_name);
			disease_nameQuantity1.setQuantity(quantity);
			list1.add(disease_nameQuantity1);
			// 男性
			Integer man = diagnosisServiceImpl.disease_statisticsByYear_monthDisease_idGender(year_month, disease_id, true);
			Disease_nameQuantity disease_nameQuantity2 = new Disease_nameQuantity();
			disease_nameQuantity2.setDisease_name(disease_name);
			disease_nameQuantity2.setQuantity(man);
			list2.add(disease_nameQuantity2);
			// 女性
			Integer woman = diagnosisServiceImpl.disease_statisticsByYear_monthDisease_idGender(year_month, disease_id,
					false);
			Disease_nameQuantity disease_nameQuantity3 = new Disease_nameQuantity();
			disease_nameQuantity3.setDisease_name(disease_name);
			disease_nameQuantity3.setQuantity(woman);
			list3.add(disease_nameQuantity3);
		}
		map.put("list1", list1);
		map.put("list2", list2);
		map.put("list3", list3);
		return map;
	}
}
