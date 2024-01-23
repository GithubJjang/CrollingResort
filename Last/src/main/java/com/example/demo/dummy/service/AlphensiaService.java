package com.example.demo.dummy.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dummy.model.AlpensiaSlope;
import com.example.demo.dummy.model.GonjiSlope;
import com.example.demo.dummy.repository.AlpensiaRepository;
import com.example.demo.dummy.repository.GonjiRepository;

import jakarta.transaction.Transactional;

@Service
public class AlphensiaService {
	@Autowired
	private AlpensiaRepository alpensiaRepository;
	Map<String, Boolean> isSlopeInlist;
	List<AlpensiaSlope> lst;
	Boolean init=true;
	
	@Transactional
	public void update(AlpensiaSlope slope) {
		String slopeName = slope.getSlopeName();
		
		if(init) {
			initMap();
		}
		// Slope가 있으면 true로 초기화를 한다.
		isSlopein(slope.getSlopeName());

		try {
			// DB에 찾아보고 있으면 Dirty checking을 통한 업데이트를 한다.(update)
			// 아니면, 새로 추가된 슬로프이므로, DB에 추가를 한다.(insert)
			AlpensiaSlope orig = alpensiaRepository.findBySlopeName(slopeName);
			orig.setSlopeName(slope.getSlopeName());
			orig.buildDifficulty(slope.getDifficulty());
			orig.build주간(slope.get주간());
			orig.build야간(slope.get야간());
		} catch (NullPointerException e) {
			isSlopeInlist.put(slope.getSlopeName(), true);
			alpensiaRepository.save(slope);

		}
	}
	
	
	// 3. Map 초기화
	// DB의 슬로프 이름을 가져온다.
	private void initMap() {
		lst = alpensiaRepository.findAll();//DB의 슬로프 이름을 가져온다.
		isSlopeInlist = new HashMap<String,Boolean>();
		
		// Map 초기화
		for(AlpensiaSlope g: lst) {
			isSlopeInlist.put(g.getSlopeName(), false);
		}
		init=false; // 이거 설정안하면 슬로프 정보 매 확인마다 Map 만들고 지우는 바보같은 과정을 반복한다.
		
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
		for (String key : isSlopeInlist.keySet()) {
			Boolean value = isSlopeInlist.get(key);
			if(!value) {
				// 삭제를 한다.
				alpensiaRepository.deleteByslopeName(key);
			}
		}
		init=true; // 다음 사이클을 위해서 초기화를 한다.
	}
}
