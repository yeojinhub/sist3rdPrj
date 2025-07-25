package kr.co.sist.user.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.DTO.ScamDTO;
import kr.co.sist.user.DAO.AnticheatDAO;

@Service
public class AnticheatService {
    @Autowired
    private AnticheatDAO anticheatDAO;

    public int selectTotalprevention() {
        return anticheatDAO.selectTotalprevention();
    }

    public int selectTodayLookUpScam() {
        return anticheatDAO.selectTodayLookUpScam();
    }

    public int selectTotalLookUpScam() {
        return anticheatDAO.selectTotalLookUpScam();
    }

    public List<ScamDTO> selectRealTimeMonitoring() {
        return anticheatDAO.selectRealTimeMonitoring();
    }

    public void insertScam(String inputValue) {
        anticheatDAO.insertScam(inputValue);
    }

    public int selectScamResultWithUsers(String inputValue) {
        return anticheatDAO.selectScamResultWithUsers(inputValue);
    }

    public int selectScamResultWithBank(String inputValue) {
        return anticheatDAO.selectScamResultWithBank(inputValue);
    }
}
