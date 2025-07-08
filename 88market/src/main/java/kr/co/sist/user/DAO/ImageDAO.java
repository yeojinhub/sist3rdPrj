package kr.co.sist.user.DAO;

import org.apache.ibatis.annotations.Mapper;
import kr.co.sist.DTO.ImageDTO;

@Mapper
public interface ImageDAO {
    public void insertImage(ImageDTO image);
    public int getNextImageSeq();
}
