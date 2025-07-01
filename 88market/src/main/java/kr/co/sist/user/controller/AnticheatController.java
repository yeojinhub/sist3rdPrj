package kr.co.sist.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.sist.DTO.ScamDTO;
import kr.co.sist.user.Service.AnticheatService;

@Controller
public class AnticheatController {

    @Autowired
    private AnticheatService anticheatService;

    @GetMapping("/api/anticheat/real-time-monitoring")
    @ResponseBody
    public List<ScamDTO> getRealTimeMonitoring() {
        return anticheatService.selectRealTimeMonitoring();
    }
    
    @GetMapping("/anticheat")
	public String antiCheat(Model model) {
		int totalprevention = anticheatService.selectTotalprevention();
		int todayLookUpScam = anticheatService.selectTodayLookUpScam();
		int totalLookUpScam = anticheatService.selectTotalLookUpScam();

		String totalpreventionStr = String.format("%,d", totalprevention);
		String todayLookUpScamStr = String.format("%,d", todayLookUpScam);
		String totalLookUpScamStr = String.format("%,d", totalLookUpScam);

		model.addAttribute("totalprevention", totalpreventionStr);
		model.addAttribute("todayLookUpScam", todayLookUpScamStr);
		model.addAttribute("totalLookUpScam", totalLookUpScamStr);
		return "user/anticheat/anti_cheat";
	}

	@PostMapping("/anticheatResult")
	public String antiCheatResult(
		@RequestParam("inputValue") String inputValue,
		Model model
	) {
		// 사기조회 기록 추가가
		anticheatService.insertScam(inputValue);
		// inputValue의 앞 3자리만 남기고 나머지는 *로 마스킹 처리
		String maskedInputValue = inputValue.substring(0, 3) + "*".repeat(inputValue.length() - 3);

		// 사용자 정보로 사기 조회 결과 조회
		int scamResultWithUsers = anticheatService.selectScamResultWithUsers(inputValue);
		// 계좌 정보로 사기 조회 결과 조회
		int scamResultWithBank = anticheatService.selectScamResultWithBank(inputValue);

		// 조회 결과 총 건수
		int totalScamResult = scamResultWithUsers + scamResultWithBank;

		model.addAttribute("inputValue", maskedInputValue);
		model.addAttribute("totalScamResult", totalScamResult);
		return "user/anticheat/anti_cheat_result";
	}
}
