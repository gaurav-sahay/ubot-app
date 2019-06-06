package com.sap.ubot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sap.ubot.notification.sender.Book;
import com.sap.ubot.notification.sender.TestingCache;

//@RestController
//@RequestMapping("/api")
public class TestController {/*
	
	@Autowired
	private TestingCache testingCache;
	@Autowired
	private ConcurrentMapCacheManager concurrentMapCacheManager;
	
	@RequestMapping(value = "/test/{id}",produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.GET)
	public List<Book> test(@PathVariable String id) {
		Cache cache = concurrentMapCacheManager.getCache("mycache");
		cache.get("1");
		return testingCache.getBook(id);
	}
	
	@RequestMapping(value = "/test",produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.PUT)
	public List<Book> test(@RequestBody Book book) throws InterruptedException {
		return testingCache.updateBook(book);
	}
	
	@RequestMapping(value = "/test",produces = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.DELETE)
	public ResponseEntity<Object>List<Book> testDelete(@RequestBody Book book) {
		return new ResponseEntity<>(testingCache.deleteBook(book),HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "/test/{attributeId}",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE},
			method = RequestMethod.POST)
	public ResponseEntity<List<Map<String, String>>> testMap(@PathVariable String attributeId,@RequestBody List<Map<String, String>> attributes){
		List<Map<String, String>> maps = new ArrayList<>();
		
		for(Map<String, String> attribute : attributes) {
			System.out.println(attribute);
		}
		
		return new ResponseEntity<>(attributes, HttpStatus.OK);
		
	}
	
	

*/}
