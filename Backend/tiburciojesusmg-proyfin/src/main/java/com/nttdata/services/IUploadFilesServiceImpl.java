//package com.nttdata.services;
//
//import java.io.File;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.UUID;
//
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//@Service
//public class IUploadFilesServiceImpl implements IUploadFilesServiceI {
//
//	@Override
//	public String handleFileUpload(MultipartFile file) throws Exception {
//
//		try {
//
//			String fileName = UUID.randomUUID().toString();
//			byte[] bytes = file.getBytes();
//			String fileOriginalName = file.getOriginalFilename();
//
//			long fileSize = file.getSize();
//			long maxFileSize = 5 * 1024 * 1024;
//
//			if (fileSize > maxFileSize) {
//				return "File size mayor que el tamaño maximo";
//			}
//
//			if (!fileOriginalName.endsWith(".jpg") && !fileOriginalName.endsWith(".jpeg")
//					&& !fileOriginalName.endsWith(".png")) {
//				return "El archivo no es de formato, jpg, jpeg o png";
//			}
//
//			String fileExtension = fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
//			String newFileName = fileName + fileExtension;
//
//			String currentDir = System.getProperty("user.dir"); // Obtiene el directorio actual
//			System.out.println(currentDir);
//			String folderPath = currentDir + File.separator + "src" + File.separator + "main" + File.separator
//					+ "resources" + File.separator + "picture";
//			File folder = new File(folderPath);
//
//			System.out.println(folderPath);
//
//			if (!folder.exists()) {
//				folder.mkdirs();
//			}
//
//			String newFilePath = folderPath + File.separator + newFileName;
//
//			System.out.println(newFilePath);
//
//			Path path = Paths.get(newFilePath);
//			Files.write(path, bytes);
//			return "File subido correctamente";
//
//		} catch (Exception e) {
//			throw new Exception(e.getMessage());
//		}
//	}
//
//}
