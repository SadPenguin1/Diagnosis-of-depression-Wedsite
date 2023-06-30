package com.linkin.web.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linkin.model.ResponseDTO;
import com.linkin.model.SearchUserDTO;
import com.linkin.model.UserDTO;
import com.linkin.service.UserService;

@Controller
@RequestMapping(value = "/admin")
public class UserAdminController extends BaseWebController {

	@Autowired
	private UserService userService;

	@GetMapping("/accounts")
	public String listUser() {
		return "admin/userAccount/listUser";
	}

	@PostMapping(value = "/accounts")
	public ResponseEntity<ResponseDTO<UserDTO>> finds(@RequestBody SearchUserDTO searchUserDTO) {
		ResponseDTO<UserDTO> responseDTO = new ResponseDTO<UserDTO>();
		responseDTO.setData(userService.findUsers(searchUserDTO));
		responseDTO.setRecordsTotal(userService.countTotalUsers(searchUserDTO));
		responseDTO.setRecordsFiltered(userService.countUsers(searchUserDTO));
		return new ResponseEntity<ResponseDTO<UserDTO>>(responseDTO, HttpStatus.OK);
	}

	@GetMapping("/account/add")
	public String addUser(Model model) {
		UserDTO userDTO = new UserDTO();
		model.addAttribute("userAccountDTO", userDTO);
		return "admin/userAccount/addUser";
	}

	@PostMapping("/account/add")
	public String addUser(@ModelAttribute(name = "userAccountDTO") UserDTO userDTO, BindingResult bindingResult,
			Model model) {
		validateAddUser(userDTO, bindingResult);
		if (bindingResult.hasErrors()) {
			return "admin/userAccount/addUser";
		}
		try {
			userDTO.setEnabled(true);
			userService.addUser(userDTO);

		} catch (DataIntegrityViolationException ex) {
			bindingResult.rejectValue("phone", "error.msg.existed.account.phone");
			return "admin/userAccount/addUser";
		}
		return "redirect:/admin/accounts";
	}

	@GetMapping("/account/update/{id}")
	public String updateUser(Model model, @PathVariable(name = "id") Long id) {
		UserDTO userAccountDTO = userService.getUserById(id);
		model.addAttribute("userAccountDTO", userAccountDTO);

		return "admin/userAccount/updateUser";
	}

	@PostMapping("/account/update")
	public String updateUser(@ModelAttribute(name = "userAccountDTO") UserDTO userDTO, BindingResult bindingResult) {
		validateUpdateUser(userDTO, bindingResult);
		if (bindingResult.hasErrors()) {
			return "admin/userAccount/updateUser";
		}
		try {
			// save database
			userService.updateUser(userDTO);
		} catch (DataIntegrityViolationException ex) {
			bindingResult.rejectValue("phone", "error.msg.existed.account.phone");
			return "admin/userAccount/updateUser";
		}

		return "redirect:/admin/accounts";
	}

	@GetMapping("/account/change-lock/{id}")
	public ResponseEntity<String> changeLockedUserStatus(@PathVariable(name = "id") Long id) {
		userService.changeAccountLock(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@GetMapping("/account/delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long id) {
		userService.deleteUser(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@GetMapping("/account/delete-multi/{ids}")
	public ResponseEntity<String> deleteMultiUser(@PathVariable(name = "ids") List<Long> ids) {
		for (long id : ids) {
			try {
				userService.deleteUser(id);
			} catch (Exception e) {
			}
		}
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@GetMapping("/account/reset-password/{id}")
	public String resetPassword(Model model, @PathVariable(name = "id") Long id) {
		UserDTO userDTO = userService.getUserById(id);
		model.addAttribute("userAccountDTO", userDTO);
		return "admin/userAccount/resetPassword";
	}

	@PostMapping("/account/reset-password")
	public String resetPassword(@ModelAttribute(name = "userAccountDTO") UserDTO userDTO, BindingResult bindingResult) {
		validateUserPassword(userDTO, bindingResult);
		if (bindingResult.hasErrors()) {
			return "admin/userAccount/resetPassword";
		}
		userService.setupUserPassword(userDTO);

		return "redirect:/admin/accounts";
	}

	@PostMapping("/excel/export")
	public @ResponseBody String exportOwner(@RequestBody SearchUserDTO searchUserDTO) {
		searchUserDTO.setStart(null);
		List<UserDTO> userDTOs = userService.findUsers(searchUserDTO);
		String fileName = "user.xlsx";

		XSSFWorkbook workbook = new XSSFWorkbook();
		// add date to name of exel file
		XSSFSheet sheet = workbook.createSheet("user");
		int rowNum = 0;
		Row firstRow = sheet.createRow(rowNum++);
		firstRow.createCell(0).setCellValue("id");
		firstRow.createCell(1).setCellValue("name");
		firstRow.createCell(2).setCellValue("phone");
		firstRow.createCell(3).setCellValue("age");
		firstRow.createCell(4).setCellValue("gender");
		firstRow.createCell(5).setCellValue("address");
		firstRow.createCell(6).setCellValue("Khối");
		firstRow.createCell(7).setCellValue("Lớp");

		for (UserDTO userDTO : userDTOs) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(userDTO.getId());
			row.createCell(1).setCellValue(userDTO.getName());
			row.createCell(2).setCellValue(userDTO.getPhone());
			row.createCell(3).setCellValue(userDTO.getAge());

		}
		try {
			FileOutputStream outputStream = new FileOutputStream(fileName);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Done");
		return fileName;
	}

	private void validateAddUser(Object object, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "error.msg.empty.account.phone");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.msg.empty.account.password");
	}

	private void validateUpdateUser(Object object, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "error.msg.empty.account.phone");
	}

	private void validateUserPassword(Object object, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.msg.empty.account.password");
	}

}
