package com.solbangul.file;

import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ImageController {

	private final FileStore fileStore;

	@GetMapping("/images/{fileName}")
	public Resource downloadImage(@PathVariable String fileName) throws MalformedURLException {
		// "file:/Users/.../9d670ced-959f-42ce-b93d-24f2dbba16c3.png"
		return new UrlResource("file:" + fileStore.getFullPath(fileName));
	}
}
