package com.example.demo.crolling;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.model.Resort;
import com.example.demo.model.Slope;
import com.example.demo.service.server.ResortService;
import com.example.demo.service.server.SlopeService;

@Component
public class Vivaldy {
	
	@Autowired
	private ResortService resortService;
	
	@Autowired
	private SlopeService slopeService;

	@Scheduled(fixedDelay = 30000)
	public void doCrolling() throws IOException {
		
		// 한국 오늘 날짜 추출
		LocalDate date = LocalDate.now(ZoneId.of("Asia/Seoul"));
		
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
		
		// 실행할때마다 브라우저가 안 뜨도록 추가 설정
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");  // Run Chrome in headless mode (without opening the browser window)
		//options.addArguments("--remote-allow-orgins=*");
		// 기본설정
		
		WebDriver driver= new ChromeDriver(options);
		try {
			driver.get("https://www.sonohotelsresorts.com/daemyung.vp.skiworld.04_02_04.ds/dmparse.dm");
			
			// 각종 변수들
			String slopeName="";//0번
			String difficulty="";//1번
			String day="";//2번
			String night="";//3번
			String dawn="";//4번
			
			// 리조트 세팅
			Resort resort=null;
			String resortName = "Vivaldy";
	
			
	
			if(resortService.findResort(resortName)!=null) {
				resort = resortService.findResort(resortName);
			}
			else{
				// 에러가 발생했다 -> 최초의 실행이다.
				// insert를 해준다.
				resort = new Resort();
				resort.setSlopeName(resortName);
				resortService.saveResort(resort);
			}
			
			
			List<String> databox = new ArrayList<String>();
			
			WebElement tbodyElement = driver.findElement(By.tagName("tbody"));
			List<WebElement> trElement = tbodyElement.findElements(By.tagName("tr"));
			
			for(WebElement e: trElement) {
				
				// 1. 슬로프명, 난이도만을 추출한다.
				List<WebElement> tdElement = e.findElements(By.tagName("td"));
				for(int i=0; i<2; i++) {
					// 슬로프명, 난이도 : 0,1
					databox.add(tdElement.get(i).getText());
				}
				slopeName=databox.get(0);
				difficulty=databox.get(1);
				databox.clear();// 2,3,4는 img가 담기는데 볼 수 없음 -> 쓰레기 값이라서 리스트 비우기.
				
				// OPEN,CLOSE가 이미로 되어있으므로 다르게 처리를 한다.
				List<WebElement> tds = e.findElements(By.cssSelector("td:has(img)"));
				for (WebElement imgTd : tds) {
				    WebElement imgTag = imgTd.findElement(By.tagName("img"));
				    String srcValue = imgTag.getAttribute("alt");
				    databox.add(srcValue);// 주간 야간 심야 순으로   
				}
				day=databox.get(0);
				night=databox.get(1);
				dawn=databox.get(2);
				
				Slope slope = new Slope()
						  .buildResort(resort)
						  .buildSlopeName(slopeName)
						  .buildDifficulty(difficulty)
						  .buildDay(day)
						  .buildNight(night)
						  .buildDawn(dawn)
						  .buildDate(date)
						  ;
				slopeService.update(slope,resort);
				databox.clear();// 다음 요소를 위해서 비우기.
				
	
			}
			slopeService.deleteSlope();
			}catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				driver.quit();
			}
	}
}
