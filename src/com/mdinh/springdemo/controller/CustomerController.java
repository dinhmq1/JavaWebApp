package com.mdinh.springdemo.controller;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mdinh.springdemo.entity.Customer;
import com.mdinh.springdemo.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	// need to inject the customer dao
	@Autowired
	private CustomerService customerService;
	
	private Logger logger = Logger.getLogger(getClass());

	@GetMapping("/list")
	public String listCustomer(Model theModel) {
		// get customers from the dao
		List<Customer> theCustomers = customerService.getCustomers();
				
		// add the customers to the model
		theModel.addAttribute("customers", theCustomers);
		
		return "list-customers";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer) {

		logger.info("saving customer: " + theCustomer);
		
		customerService.saveCustomer(theCustomer);
		
		return "redirect:/customer/list";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		Customer theCustomer = new Customer();
		
		theModel.addAttribute("customer", theCustomer);
		
		return "customer-form";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel) {
		
		logger.info("showing form for update");
		
		// get customer from database
		Customer theCustomer = customerService.getCustomer(theId);
		
		// set customer as model attribute to pre-populate the form
		theModel.addAttribute("customer", theCustomer);
		
		return "customer-form";
	}
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int theId) {

		logger.info("deleting customer id: " + theId);
		
		// delete the customer
		customerService.deleteCustomer(theId);
		
		return "redirect:/customer/list";		
	}
}
