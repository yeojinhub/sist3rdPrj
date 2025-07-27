package kr.co.sist.user.util;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalLoginCheck {

	@ModelAttribute("isLoggedIn") 
    public boolean isLoggedIn(@AuthenticationPrincipal UserDetails currentUser) {
        return currentUser != null;
    }
    
    @ModelAttribute("username")
    public String getUsername(@AuthenticationPrincipal UserDetails currentUser) {
        return currentUser != null ? currentUser.getUsername() : null;
    }
	
}
