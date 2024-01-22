package com.example.demo.dummy.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dummy.model.PhyungChangSlope;
import com.example.demo.dummy.model.YongPyongSlope;
import com.example.demo.dummy.repository.PhyungChangRepository;

import jakarta.transaction.Transactional;

@Service
public class PhyungChangService {
	
	@Autowired
	private PhyungChangRepository phyungChangRepo;
	Boolean init=true;
	
	List<PhyungChangSlope> lst;
	Map<String, Boolean> isSlopeInlist;
	
	// 1. 초기세팅에만 사용
	@Transactional
	public void setSlope(PhyungChangSlope phyungChang) {
		phyungChangRepo.save(phyungChang);
		
	}
	
	// 2. 업데이트
	@Transactional
	public void update(PhyungChangSlope slope) {
	
		if(init==true) {
			initMap();
		}
		
		isSlopein(slope.getSlopeName());
		
		String slopeName = slope.getSlopeName();
		try {
			//Drity checking을 통한 update
			PhyungChangSlope origSlope = phyungChangRepo.findByslopeName(slopeName);
			origSlope.BuildDifficulty(slope.getDifficulty());
			origSlope.BuildslopeName(slope.getSlopeName());
			origSlope.Build경사도(slope.get경사도());
			origSlope.Build야간(slope.get야간());
			origSlope.Build연장(slope.get연장());
			origSlope.Build주간(slope.get주간());
			origSlope.Build표고차(slope.get표고차());
		}
		catch (NullPointerException e) {
			// 리조트에서 새로운 슬로프를 추가한 경우 -> Exception 발생
			// insert 쿼리 발생
			phyungChangRepo.save(slope);
		}

	}
	
	// 3. Map 초기화
	// DB의 슬로프 이름을 가져온다.
	private void initMap() {
		
		lst = phyungChangRepo.findAll();
		isSlopeInlist = new HashMap<String,Boolean>();
		
		
		for(PhyungChangSlope p: lst) {
			System.out.println(p.getSlopeName());
			isSlopeInlist.put(p.getSlopeName(), false);
		}
	
		init=false;
	}
	
	//3-1. 슬로프가 있나요?
	private void isSlopein(String s) {
		
		if(isSlopeInlist.get(s)!=null) {
			// 해당 데이터가 있다 -> true로 바꾸자
			isSlopeInlist.replace(s, true);
		}
		
			
	}
	
	//3-2. 존재하지 않는 슬로프만 삭제를 한다.
	// 언제 함? -> 위 insert, update과정 다 끝난 후 진행
	@Transactional
	public void deleteSlope() {
		System.out.println("Error occur");
		for (String key : isSlopeInlist.keySet()) {
			Boolean value = isSlopeInlist.get(key);
			if(!value) {
				// 삭제를 한다.
				phyungChangRepo.deleteByslopeName(key);
			}
		}
		// 다음 사이클(1일 뒤)를 위해서 초기화를 한다.
		init=true;
	}

}
