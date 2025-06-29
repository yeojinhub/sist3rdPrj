package kr.co.sist.user.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.sist.dto.FaqDTO;

@Mapper
public interface FaqDAO {
    public List<FaqDTO> selectFaqList();
    public List<FaqDTO> selectFaqListByType(String type);
    public List<FaqDTO> selectFaqListByKeyword(String keyword);
}
