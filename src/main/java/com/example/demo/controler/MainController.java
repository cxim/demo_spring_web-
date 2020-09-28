package com.example.demo.controler;

import com.example.demo.domain.Message;
import com.example.demo.domain.User;
import com.example.demo.repos.MessageRepo;
import com.example.demo.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;


@Controller
public class MainController {
	@Autowired
	private MessageRepo messageRepo;

	@Autowired
	private UserRepo userRepo;

	@Value("${upload.path}")
	private String uploadPath;

	@GetMapping
	public String greetingPage(Map<String, Object> model, @AuthenticationPrincipal User user) {
		try {
			model.put("user", user.getUsername());
		} catch (Exception e) {
			model.put("user", "guest");
		}
		return "greeting";
	}

	@GetMapping("/main")
	public String mainPage(@RequestParam(required = false) String filter, Model model) {
		Iterable<Message> messages = messageRepo.findAll();

		if (filter != null && !filter.isEmpty()) {
			messages = messageRepo.findByTag(filter);
		} else {
			messages = messageRepo.findAll();
		}

		model.addAttribute("messages", messages);
		model.addAttribute("filter", filter);
		return "main";
	}

	@PostMapping("/main")
	public String add(
			@RequestParam("file") MultipartFile file,
			@AuthenticationPrincipal User user,
			@RequestParam String text,
			@RequestParam String tag,
			Map<String, Object> model) throws IOException {
		Message message = new Message(user, text, tag);

		if (file != null && !file.getOriginalFilename().isEmpty()) {
			File uploadDir = new File(uploadPath);

			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}

			String uuidFile = UUID.randomUUID().toString();
			String resFileName = uuidFile + "." + file.getOriginalFilename();
			file.transferTo(new File(uploadPath + "/" + resFileName));

			message.setFilename(resFileName);
		}

		messageRepo.save(message);
		Iterable<Message> messages = messageRepo.findAll();
		model.put("messages", messages);
		return "main";
	}
}