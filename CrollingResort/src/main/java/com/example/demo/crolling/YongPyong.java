package com.example.demo.crolling;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.model.Resort;
import com.example.demo.model.Slope;
import com.example.demo.service.server.ResortService;
import com.example.demo.service.server.SlopeService;

@Component
public class YongPyong {
	
	@Autowired
	private ResortService resortService;
	
	@Autowired
	private SlopeService slopeService;
		
	@Scheduled(fixedDelay = 30000)
	public void crollingFunc1() throws IOException {
		
		// 한국 오늘 날짜 추출
		LocalDate date = LocalDate.now(ZoneId.of("Asia/Seoul"));
	
		Document d = Jsoup.connect("https://www.yongpyong.co.kr/kor/skiNboard/slope/openStatusBoard.do").get();
		
		// 2. slopeStatus 부분 추출하기
		Element tbody = d.select("tbody#slopeStatus").first();
		
		// 3. slopeStatus 내 tr태그들 추출하기(이름/주간/야간/심야/비고 정보 담고있음) 
		Elements trElements = tbody.select("tr");
		
		// 4. 각종 변수들 선언
		Element Difficulty = null;
		
		Resort resort=null;
		String resortName = "Yongpyong";

		

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
		
		String slopeName="";//0번
		String difficulty="";//1번
		String day="";//2번
		String night="";//3번
		String dawn="";//4번
		
		List<String> dataBox = new ArrayList<>();
		
		
		for(Element e: trElements) {
			
			//전체 내용 확인
			//System.out.println(e.text());
			
			//난이도 추출
			if(e.select("th").first()!=null) {
				Difficulty = e.select("th").first();
				difficulty = Difficulty.text();
			}


			// 1. 한줄에 기본정보(이름,주간,야간,심야,비고)에다가 난이도까지 있는 것을 특수케이스로 취급을 하자.
			if(e.select("td[class='']").first()!=null) {
				Element slopeNameElement = e.select("td.conLeft").first();
				slopeName = slopeNameElement.text();
				
				Elements tdElements = e.select("td[class='']");
				//System.out.println(slopeName);
				//System.out.println(difficulty);
				for (Element td : tdElements) {
				       String tdContent = td.text(); 
				       //System.out.print(tdContent);
				       //System.out.println();
				       // 주간/야간/심야/비고 순으로
				       dataBox.add(tdContent);
				}
				// 마지막 비고가 빈 경우는 빈문자 ""임.
				//System.out.println("크기:"+dataBox.size());
				//System.out.println();
				day=dataBox.get(0);
				night=dataBox.get(1);
				dawn=dataBox.get(2);
				//비고=dataBox.get(3);
				
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
				dataBox.clear();
			}
		

			// 따로 특이케이스로 처리
			if(e.select("td.lineBold").first()!=null) {
				
				Elements tsElements = e.select("td.lineBold");
				for (Element ts : tsElements) {
			        String tsContent = ts.text(); 
			        //System.out.print(tsContent);
			        //System.out.println();
			        // 이름/주간/야간/심야/비고  순으로
				    dataBox.add(tsContent);
			       
				}
				slopeName=dataBox.get(0);
				day=dataBox.get(1);
				night=dataBox.get(2);
				dawn=dataBox.get(3);
				//비고=dataBox.get(4);

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
				dataBox.clear();
			}
		}

		slopeService.deleteSlope();
	}
	

}
