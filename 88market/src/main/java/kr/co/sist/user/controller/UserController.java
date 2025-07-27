package kr.co.sist.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.sist.DTO.UserDTO;
import kr.co.sist.user.Service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userNum}")
    public ResponseEntity<UserDTO> findByUserNum(@PathVariable String userNum) {
        UserDTO dto = userService.findByUserNum(userNum);
        return ResponseEntity.ok(dto);
    }
    
    
    
    
}//침ㄴㄴ
