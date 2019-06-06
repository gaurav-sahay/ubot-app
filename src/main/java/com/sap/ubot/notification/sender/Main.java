package com.sap.ubot.notification.sender;

import java.util.concurrent.CountDownLatch;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Main {/*

	private final static CountDownLatch countDownLatch  = new CountDownLatch(1);

	public static void main(String[] args) throws UnirestException {
		
		HttpResponse<String> response1 = Unirest.get("http://localhost:8080/api/v1/int/mdm/readAgency/7beff273-d15c-4a0f-bc7c-b98f37e65a06")
				  .header("content-type", "application/json")
				  .header("authorization", "Basic aTM1MTQ1NDpTYWlyYW1AODc=")
				  .header("cache-control", "no-cache")
				  .header("postman-token", "8a48df44-3610-9c5e-510b-76493dab80a0")
				  .asString();

		System.out.println("First Read for Subscriber 1 "+response1.getBody());

		HttpResponse<String> response2 = Unirest.get("http://localhost:8080/api/v1/int/mdm/readAgency/c5e14efb-adb9-4b50-9b0d-c7a455bb70c4")
				  .header("content-type", "application/json")
				  .header("authorization", "Basic aTM1MTQ1NDpTYWlyYW1AODc=")
				  .header("cache-control", "no-cache")
				  .header("postman-token", "8a48df44-3610-9c5e-510b-76493dab80a0")
				  .asString();

		System.out.println("First Read for Subscriber 2 "+response2.getBody());

		HttpResponse<String> response3 = Unirest.post("http://localhost:8080/api/v1/int/mdm/writeAgency/c5e14efb-adb9-4b50-9b0d-c7a455bb70c4")
				  .header("content-type", "application/json")
				  .header("authorization", "Basic aTM1MTQ1NDpTYWlyYW1AODc=")
				  .header("cache-control", "no-cache")
				  .header("postman-token", "ad4f6327-28f0-03dd-0d76-36420d7b3d5a")
				  .body("[\n    {\n        \"agencyId\": \"80848888888\",\n        \"agencyDesc\": \"gauravnew\"\n    }\n]")
				  .asString();

		System.out.println("Write for Subscriber 2 = "+response3.getStatus());

		HttpResponse<String> response4 = Unirest.get("http://localhost:8080/api/v1/int/mdm/readAgency/7beff273-d15c-4a0f-bc7c-b98f37e65a06")
				  .header("content-type", "application/json")
				  .header("authorization", "Basic aTM1MTQ1NDpTYWlyYW1AODc=")
				  .header("cache-control", "no-cache")
				  .header("postman-token", "8a48df44-3610-9c5e-510b-76493dab80a0")
				  .asString();

		System.out.println("Second Read for Subscriber 1 = "+response4.getBody());

		HttpResponse<String> response5 = Unirest.get("http://localhost:8080/api/v1/int/mdm/readAgency/c5e14efb-adb9-4b50-9b0d-c7a455bb70c4")
				  .header("content-type", "application/json")
				  .header("authorization", "Basic aTM1MTQ1NDpTYWlyYW1AODc=")
				  .header("cache-control", "no-cache")
				  .header("postman-token", "8a48df44-3610-9c5e-510b-76493dab80a0")
				  .asString();

		System.out.println("Second Read for Subscriber 2 = "+response5.getBody());



		Thread readThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					countDownLatch.await();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("Staring updateThread1");

				HttpResponse<String> response4 = null;
				try {
					response4 = Unirest.delete("http://localhost:8080/api/test/")
							.header("content-type", "application/json")
							.header("cache-control", "no-cache")
							.header("postman-token", "cb2888e0-a0ca-070d-60d4-32a6a7bef1d9")
							.body("{\n    \"id\": \"pid1\",\n    \"name\": \"Book_pid1\"\n}")
							.asString();
				} catch (UnirestException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("update pid1 "+response4.getBody());

			}
		},"updateThread1");

	// concurrent thread testing using countdown latch

		Thread updateThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					countDownLatch.await();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  

				System.out.println("Staring updateThread2");

				HttpResponse<String> response4 = null;
				try {
					response4 = Unirest.put("http://localhost:8080/api/test/")
							.header("content-type", "application/json")
							.header("cache-control", "no-cache")
							.header("postman-token", "cb2888e0-a0ca-070d-60d4-32a6a7bef1d9")
							.body("{\n    \"id\": \"pid1\",\n    \"name\": \"Book_pid11\"\n}")
							.asString();
				} catch (UnirestException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("update pid1 "+response4.getBody());
				
				HttpResponse<String> response1 = null;
				try {
					response1 = Unirest.get("http://localhost:8080/api/test/pid1")
							.header("content-type", "application/json")
							.header("cache-control", "no-cache")
							.header("postman-token", "f0d74a6f-1dce-773b-01d6-230ad8e91886")
							.asString();
				} catch (UnirestException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("read pid1 = "+response1.getBody());

			}
		},"updateThread2");

		updateThread.start();
		readThread.start();
		countDownLatch.countDown();

		HttpResponse<String> response1 = Unirest.get("http://localhost:8080/api/test/pid1")
				.header("content-type", "application/json")
				.header("cache-control", "no-cache")
				.header("postman-token", "f0d74a6f-1dce-773b-01d6-230ad8e91886")
				.asString();

		System.out.println("read pid1 = "+response1.getBody());


		HttpResponse<String> response2 = Unirest.get("http://localhost:8080/api/test/pid2")
				.header("content-type", "application/json")
				.header("cache-control", "no-cache")
				.header("postman-token", "16cbf032-1bae-116d-c16c-aa87f8a8caff")
				.asString();

		System.out.println("read pid2 = "+response2.getBody());



		HttpResponse<String> response4 = Unirest.put("http://localhost:8080/api/test/")
				.header("content-type", "application/json")
				.header("cache-control", "no-cache")
				.header("postman-token", "cb2888e0-a0ca-070d-60d4-32a6a7bef1d9")
				.body("{\n    \"id\": \"pid1\",\n    \"name\": \"Book_pid1\"\n}")
				.asString();

		System.out.println("update pid1 "+response4.getBody());

		HttpResponse<String> response3 = Unirest.delete("http://localhost:8080/api/test/")
				.header("content-type", "application/json")
				.header("cache-control", "no-cache")
				.header("postman-token", "9caf20f9-73a3-a360-e12c-2cd2dd4eac56")
				.body("{\n    \"id\": \"pid1\",\n    \"name\": \"Book_pid1\"\n}")
				.asString();
		System.out.println("delete pid1 "+response3.getBody());
		HttpResponse<String> response31 = Unirest.put("http://localhost:8080/api/test/")
				.header("content-type", "application/json")
				.header("cache-control", "no-cache")
				.header("postman-token", "cb2888e0-a0ca-070d-60d4-32a6a7bef1d9")
				.body("{\n    \"id\": \"pid1\",\n    \"name\": \"Book_pid1\"\n}")
				.asString();

		System.out.println("update pid1 "+response31.getBody());


		HttpResponse<String> response5 = Unirest.get("http://localhost:8080/api/test/pid1")
				.header("content-type", "application/json")
				.header("cache-control", "no-cache")
				.header("postman-token", "f0d74a6f-1dce-773b-01d6-230ad8e91886")
				.asString();

		System.out.println("read pid1 = "+response5.getBody());


		HttpResponse<String> response6 = Unirest.get("http://localhost:8080/api/test/pid2")
				.header("content-type", "application/json")
				.header("cache-control", "no-cache")
				.header("postman-token", "16cbf032-1bae-116d-c16c-aa87f8a8caff")
				.asString();

		System.out.println("read pid2 = "+response6.getBody());




	}

*/}
