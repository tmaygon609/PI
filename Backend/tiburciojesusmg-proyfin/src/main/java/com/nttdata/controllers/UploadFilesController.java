//package com.nttdata.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.nttdata.services.IUploadFilesServiceI;
//
//@RestController
//@RequestMapping("/v1/uploads")
//public class UploadFilesController {
//
//	@Autowired
//	IUploadFilesServiceI uploadFilesService;
//
//	@PostMapping("/picture")
//	private ResponseEntity<String> uploadPicture(@RequestParam("file") MultipartFile file) throws Exception {
//
//		return ResponseEntity.ok().body(uploadFilesService.handleFileUpload(file));
//
//	}
//
//}
