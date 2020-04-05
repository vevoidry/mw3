package com.homework.web.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homework.web.pojo.Appointment;
import com.homework.web.pojo.Appointment_time;
import com.homework.web.pojo.Article;
import com.homework.web.pojo.Drug;
import com.homework.web.pojo.Prescription;
import com.homework.web.service.impl.AppointmentServiceImpl;
import com.homework.web.service.impl.Appointment_timeServiceImpl;
import com.homework.web.service.impl.DrugServiceImpl;
import com.homework.web.service.impl.PrescriptionServiceImpl;

@Controller
@RequestMapping("/prescriptions")
public class PrescriptionController {

	@Autowired
	PrescriptionServiceImpl prescriptionServiceImpl;
	@Autowired
	DrugServiceImpl drugServiceImpl;
	@Autowired
	Appointment_timeServiceImpl 	appointment_timeServiceImpl;
	@Autowired
	AppointmentServiceImpl 	appointmentServiceImpl;

	@PostMapping
	@ResponseBody
	public HashMap<String, String> post(Integer drug_id, Integer quantity, Integer appointment_id) {
		if (drug_id == null) {
			throw new RuntimeException("请选择药物");
		}
		if (quantity == null) {
			throw new RuntimeException("请输入数量");
		}
		Drug drug = drugServiceImpl.selectById(drug_id);
		if (quantity > drug.getQuantity()) {
			throw new RuntimeException("该药物的库存只有" + drug.getQuantity() + drug.getUnit() + "，请输入合法数量");
		}
		Prescription prescription2 = prescriptionServiceImpl.selectByAppointment_idDrug_id(appointment_id, drug_id);
		if (prescription2 == null) {
			Prescription prescription = new Prescription();
			Appointment appointment = appointmentServiceImpl.selectById(appointment_id);
			Appointment_time appointment_time = appointment_timeServiceImpl.selectById(appointment.getAppointment_time_id());
			prescription.setAppointment_id(appointment_id);
			prescription.setDrug_id(drug_id);
			prescription.setQuantity(quantity);
			prescription.setTime_day(appointment_time.getTime_day());
			prescriptionServiceImpl.insert(prescription);
		} else {
			prescription2.setQuantity(prescription2.getQuantity() + quantity);
			prescriptionServiceImpl.update(prescription2);
		}
		drug.setQuantity(drug.getQuantity() - quantity);
		drugServiceImpl.update(drug);
		return new HashMap<String, String>();
	}

}
