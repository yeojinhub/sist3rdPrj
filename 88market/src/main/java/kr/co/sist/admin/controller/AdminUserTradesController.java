package kr.co.sist.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.sist.DTO.TradesDTO;
import kr.co.sist.DTO.PaymentsDTO;
import kr.co.sist.admin.Service.AdminUserTradeService;
import kr.co.sist.admin.util.AdminSearchUserTradeDTO;
import kr.co.sist.admin.util.UserProductPagination;

@Controller
@RequestMapping("/admin/userTrade")
public class AdminUserTradesController {

    @Autowired
    private AdminUserTradeService tradeService;

    //trade List 출력
    @GetMapping({"", "/list"})
    public String list(@ModelAttribute("search") AdminSearchUserTradeDTO searchDTO, Model model) {
        if (searchDTO.getKeyword() == null) {
            searchDTO.setKeyword("");
        }
        if (searchDTO.getSearchType() == null) {
            searchDTO.setSearchType("");
        }
        UserProductPagination<TradesDTO> page =
            tradeService.findByAdminSearch(searchDTO);

        model.addAttribute("pagination",   page);
        model.addAttribute("userTradeList", page.getItems());
        return "admin/userTrade/userTradeList";
    }
    
    // 거래 상세 
	/*
	 * @GetMapping("/detail/{tradeId}") public String
	 * detail(@PathVariable("tradeId") Integer tradeId, Model model) { TradesDTO
	 * trade = tradeService.findById(tradeId); model.addAttribute("trade", trade);
	 * return "admin/userTrade/userTradeDetail"; }
	 */
    @GetMapping("/detail/{tradeId}")
    public String detail(@PathVariable Integer tradeId, Model model) {
      TradesDTO trade = tradeService.findById(tradeId);
      PaymentsDTO pay= tradeService.findPaymentByTradeId(tradeId);
      model.addAttribute("trade",   trade);
      model.addAttribute("payment", pay);
      return "admin/userTrade/userTradeDetail";
    }
	
}
