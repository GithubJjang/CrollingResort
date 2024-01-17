package com.example.demo.dummy.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dummy.model.YongPyongSlope;
import com.example.demo.dummy.repository.YongPyongSRepository;

import jakarta.transaction.Transactional;

@Service
public class YongPyongService {
	// insert, update는 성공
	// 만약에, 슬로프 정보가 브라우저가 없는 경우 -> DB에 Delete 쿼리를 보내서 지움.
	
	@Autowired
	private YongPyongSRepository slopeRepository;
	Boolean init=true;
	
	// 슬로프의 유무를 담는 Map객체
	// 할때마다 생성됨? -> x. 문제는 갱신이 불가능하다! -> 참조변수로 설정
	List<YongPyongSlope> lst;
	Map<String, Boolean> isSlopeInlist;
	
	// 1. DB 처음으로 초기화 + DB에 저장(insert)
	@Transactional
	public void setSlope(YongPyongSlope slope) {
		slopeRepository.save(slope);
	}
	
	// 2. DB업데이트(삭제 및 업데이트)
	@Transactional
	public void update(YongPyongSlope slope) {
		
		// 첫 실행시에만 작동 <- Map value모두 false로 초기화
		if(init) {
			initMap();
		}
		
		// 브라우저에서 찾은 슬로프가 DB에 있나요?
		isSlopein(slope.getSlopeName());
		
		
		// 슬로프 새로 업데이트 <- Dirty checking을 사용(update)
		String slopeName = slope.getSlopeName();
		try {
			// Dirty checking을 통한 update
			YongPyongSlope origSlope = slopeRepository.findByslopeName(slopeName);
			origSlope.set주간(slope.get주간());
			origSlope.set야간(slope.get야간());
			origSlope.set심야(slope.get심야());
			origSlope.set비고(slope.get비고());
		} catch (NullPointerException e) {
			// 리조트에서 새로운 슬로프를 추가한 경우 -> Exception 발생
			// insert 쿼리 발생
			
			// 신규 슬로프 삽입시에 Map에 등록을 한 해주면, 마지막에 삭제된다.
			isSlopeInlist.put(slope.getSlopeName(), true);
			slopeRepository.save(slope);
		}

	}
	
	// 3. Map 초기화
	// DB의 슬로프 이름을 가져온다.
	private void initMap() {
		lst = slopeRepository.findAll();
		isSlopeInlist = new HashMap<String,Boolean>();
		
		
		for(YongPyongSlope y: lst) {
			isSlopeInlist.put(y.getSlopeName(), false);
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

		for (String key : isSlopeInlist.keySet()) {
			Boolean value = isSlopeInlist.get(key);
			if(!value) {
				// 삭제를 한다.
				slopeRepository.deleteByslopeName(key);
			}
		}
		// 다음 사이클(1일 뒤)를 위해서 초기화를 한다.
		init=true;
	}
}
