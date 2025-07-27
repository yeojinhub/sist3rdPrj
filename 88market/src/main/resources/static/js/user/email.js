document.addEventListener("DOMContentLoaded", function () {
	
	// 이메일 실시간 유효성 검사
	const emailInput = document.getElementById("email");
	emailInput.addEventListener('input', function() {
	    validateEmail(emailInput.value);
	}); //emailInput

	// 이메일 유효성 검사 함수
	function validateEmail(email) {
	  const emailRegex = /^[a-zA-Z0-9]+$/;

	  // null 검사 실패
	  if (!email) {
		styleError("email");
	    showErrorMessage("email-domain", "이메일을 입력하세요.");
	    return false;
	  } //end if

	  // 길이 검사 실패
	  if (email.length < 4){
		styleError("email");
		showErrorMessage("email-domain","이메일은 최소 4자리, 최대 15자리 가능합니다.")
		return false;
	  } //end if
	  
	  // 영문, 숫자 검사 실패
	  if (!emailRegex.test(email)) {
		styleError("email");
	    showErrorMessage("email-domain", "이메일은 영문 포함 숫자만 가능합니다.");
	    return false;
	  } //end if

	  // 유효성 검사 성공
	  hideErrorMessage("email");
	  hideErrorMessage("email-domain");
	  return true;
	} //validateEmail

	// 도메인 실시간 유효성 검사
	const domainInput = document.getElementById("email-domain");
	domainInput.addEventListener('input', function() {
	    validateDomain(domainInput.value);
	}); //domainInput

	// 도메인 유효성 검사 함수
	function validateDomain(domain) {

	  // null 검사 실패
	  if (!domain) {
	    showErrorMessage("email-domain", "도메인을 입력하세요.");
	    return false;
	  } //end if

	  // 길이 검사 실패
	  if (domain.length < 4){
		showErrorMessage("email-domain","도메인을 최소 4자리, 최대 15자리 가능합니다.")
		return false;
	  } //end if
	  
	  // 유효성 검사 성공
	  hideErrorMessage("email-domain");
	  return true;
	} //validateDomain
	
	// 비밀번호 실시간 유효성 검사
	const passInput = document.getElementById("pass");
	passInput.addEventListener('input', function() {
	    validatePass(passInput.value);
	}); //passInput

	// 비밀번호 유효성 검사 함수
	function validatePass(pass){
		
		// null 검사 실패
		if (!pass) {
			showErrorMessage("pass", "비밀번호를 입력하세요.");
			return false;
		} //end if
		
		// 유효성 검사 성공
		hideErrorMessage("pass");
		return true;
	} //validatePass
	
	// 에러 메시지를 출력하는 함수
	function showErrorMessage(fieldName, message) {
	    const errorDiv = document.querySelector(`#${fieldName} + .error-message`);

	    if (errorDiv && errorDiv.classList.contains('error-message')) {
	        errorDiv.textContent = message;
	        errorDiv.style.display = 'block';
			errorDiv.style.color = 'red';
	    } //end if
		// 입력값이 없을 때
	    const inputElement = document.getElementById(fieldName);
	    inputElement.style.borderColor = 'red';
	    inputElement.style.borderWidth = '1px';
	    inputElement.style.borderStyle = 'solid';
		inputElement.style.backgroundColor = '';
	} //showErrorMessage

	function styleError(inputId) {
	  const el = document.getElementById(inputId);
	  el.style.border       = '1px solid red';
	  el.style.backgroundColor = ''; // 배경색 제거
	}

	// 에러 메시지를 숨기는 함수
	function hideErrorMessage(fieldName) {
	    const errorDiv =  document.querySelector(`#${fieldName} + .error-message`);

	    if (errorDiv && errorDiv.classList.contains('error-message')) {
	        errorDiv.style.display = 'none';
	    } //end if
		// 입력값이 있을 때
	    const inputElement = document.getElementById(fieldName);
		inputElement.style.borderColor = 'black';
		inputElement.style.borderWidth = '1px';
		inputElement.style.borderStyle = 'solid';
		inputElement.style.backgroundColor = '#e0f7fa';
	} //hideErrorMessage

	// null 체크 함수('을')
	function validateNullEul(value, fieldName) {
		if (!value || value.trim() === "") {
			alert(`${fieldName}을 입력해주세요.`);
			// 체크 실패
			return false;
		} //end if
		// 체크 성공
		return true;
	} //validateNullEul

	// null 체크 함수('를')
	function validateNullRul(value, fieldName) {
		if (!value || value.trim() === "") {
			alert(`${fieldName}를 입력해주세요.`);
			// 체크 실패
			return false;
		} //end if
		// 체크 성공
		return true;
	} //validateNullRul
	
	const form = document.querySelector(".frm");
	form.addEventListener("submit", function (e) {
		e.preventDefault();
		handleLogin();
	});
	
	function handleLogin(){
		const email = document.getElementById("email").value + "@" + document.getElementById("email-domain").value;
	    const pass = document.getElementById("pass").value;
		
		// 이메일 null 검사
		if( !validateNullEul(document.getElementById("email").value, "이메일") ){
			// null 검사 실패
			return;
		} //end if
		
		// 도메인 null 검사
		if( !validateNullEul(document.getElementById("email-domain").value, "도메인") ){
			// null 검사 실패
			return;
		} //end if
		
		// 비밀번호 null 검사
		if( !validateNullRul(pass, "비밀번호") ){
			// null 검사 실패
			return;
		} //end if
		
	    const jsonParam = {
			email : email,
			pass : pass
	    };
	
	    fetch('/loginProcess', {
			method: "POST",
			headers: {
	        "Content-Type": "application/json; charset=UTF-8",
		},
		body: JSON.stringify(jsonParam),
		}).then((res) => {
			if (!res.ok) throw new Error(`HTTP 에러 ${res.status}`);
			return res.json();
		}).then((data) => {
			console.log(data);
	    	if(data.token){
				// 로그인 성공
	        	alert("로그인이 완료되었습니다.");
	    		// 메인화면으로 이동
	        	window.location.href = '/';
	    	} else {
	    		// 로그인 실패
	    		alert(data.message);
	        } //end if else
		}) // 응답 처리
		.catch((err) => {
			console.error("로그인 오류:", err);
	        alert("이메일 또는 비밀번호가 잘못되었습니다.");
		}); // 에러 처리
	} //handleLogin
	
}); //addEventListener
