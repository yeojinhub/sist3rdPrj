document.addEventListener("DOMContentLoaded", function () {
	
	const select = document.getElementById('select');
	const input  = document.getElementById('email-domain');

	select.addEventListener('change', function() {
		const val = this.value;
		
		if (val === 'other' || val === '') {
			input.value = '';
			input.removeAttribute('readonly');
			input.focus();
		} else {
			input.value = val;
			input.setAttribute('readonly', 'readonly');
		} //end if else
	}); //addEventListener
	
	// 이름 실시간 유효성 검사
	const nameInput = document.getElementById("name");
	nameInput.addEventListener('input', function() {
	    validateName(nameInput.value);
	}); //idInput
		
	// 이름 유효성 검사 함수
	function validateName(name) {
		const nameText = /^[가-힣]+$/;
		// null 검사 실패
		if (!name){
			showErrorMessage("name", "이름을 입력하세요.");
			return false;
		} //end if
			
		// 길이 검사 실패
		if(name.length < 1) {
			showErrorMessage("name", "이름은 최소 2자리, 최대 10자리 가능합니다.");
			return false;
		} //end if
		
		// 한글 검사 실패
		if (!nameText.test(name)) {
			showErrorMessage("name", "이름은 한글만 가능합니다.");
			return false;
		} //end if
		
		// 유효성 검사 성공
		hideErrorMessage("name");
		return true;
	} //validateName

	// 닉네임 실시간 유효성 검사
	const nickInput = document.getElementById("nickname");
	nickInput.addEventListener('input', function() {
	    validateNick(nickInput.value);
	}); //nickInput
		
	// 닉네임 유효성 검사 함수
	function validateNick(nick) {
		// null 검사 실패
		if (!nick){
			showErrorMessage("nickname", "닉네임을 입력하세요.");
			return false;
		} //end if
			
		// 길이 검사 실패
		if(nick.length < 1) {
			showErrorMessage("nickname", "닉네임은 최소 2자리, 최대 50자리 가능합니다.");
			return false;
		} //end if
		
		// 유효성 검사 성공
		hideErrorMessage("nickname");
		return true;
	} //validateNick
	
	// 이메일 실시간 유효성 검사
	const emailInput = document.getElementById("email-id");
	emailInput.addEventListener('input', function() {
	    validateEmail(emailInput.value);
	}); //emailInput
	
	// 이메일 유효성 검사 함수
	function validateEmail(email) {
	  const emailRegex = /^[a-zA-Z0-9]+$/;

	  // null 검사 실패
	  if (!email) {
		styleError("email-id");
	    showErrorMessage("email-message", "이메일을 입력하세요.");
	    return false;
	  } //end if

	  // 길이 검사 실패
	  if (email.length < 4){
		styleError("email-id");
		showErrorMessage("email-message","이메일은 최소 4자리, 최대 15자리 가능합니다.")
		return false;
	  } //end if
	  
	  // 영문, 숫자 검사 실패
	  if (!emailRegex.test(email)) {
		styleError("email-id");
	    showErrorMessage("email-message", "이메일은 영문 포함 숫자만 가능합니다.");
	    return false;
	  } //end if

	  // 유효성 검사 성공
	  hideErrorMessage("email-id");
	  hideErrorMessage("email-message");
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
		styleError("email-domain");
	    showErrorMessage("email-message", "도메인을 입력하세요.");
	    return false;
	  } //end if

	  // 길이 검사 실패
	  if (domain.length < 4){
		styleError("email-domain");
		showErrorMessage("email-message","도메인을 최소 4자리, 최대 15자리 가능합니다.")
		return false;
	  } //end if
	  
	  // 유효성 검사 성공
	  hideErrorMessage("email-domain");
	  hideErrorMessage("email-message");
	  return true;
	} //validateDomain
	
	// 비밀번호와 비밀번호 확인 실시간 유효성 검사
	const passInput = document.getElementById("pass-id");
	const passCheckInput = document.getElementById("pass-check");
	passInput.addEventListener('input', function() {
	    validatePass(passInput.value, passCheckInput.value);
	}); //passInput

	passCheckInput.addEventListener('input', function() {
	    validatePass(passInput.value, passCheckInput.value);
	}); //passCheckInput

	// 비밀번호 유효성 검사 함수
	function validatePass(pass, passCheck){
		
		// null 검사 실패
		if (!pass) {
			styleError("pass-id");
			showErrorMessage("pass-check", "비밀번호를 입력하세요.");
			return false;
		} //end if
		
		// null 검사 실패
		if (!passCheck) {
			styleError("pass-check");
			showErrorMessage("pass-check", "비밀번호 확인을 입력하세요.");
			return false;
		} //end if
		
		// 길이 검사 실패
		if (pass.length < 4){
			styleError("pass-id");
			showErrorMessage("pass-check","비밀번호는 최소 4자리, 최대 16자리 가능합니다.")
			return false;
		} //end if
		// 길이 검사 실패
		if (passCheck.length < 4){
			styleError("pass-check");
			showErrorMessage("pass-check","비밀번호 확인은 최소 4자리, 최대 16자리 가능합니다.")
			return false;
		} //end if
		
		if(pass != passCheck){
			styleError("pass-id");
			showErrorMessage("pass-check","비밀번호와 비밀번호 확인이 일치하지 않습니다.\n비밀번호를 다시 확인해주세요.");
			return false;
		} //end if
		
		// 유효성 검사 성공
		hideErrorMessage("pass-id");
		hideErrorMessage("pass-check");
		return true;
	} //validatePass
	
	// 전화번호 실시간 유효성 검사
	const telInput = document.getElementById("tel");
	telInput.addEventListener('input', function() {
	    validateTel(telInput.value);
	}); //telInput

	// 전화번호 유효성 검사
	function validateTel(tel) {
		const telText = /^\d{10,11}$/;
		
		// null 검사 실패
		if (!tel) {
			showErrorMessage("tel","전화번호를 입력하세요.");
			return false;
		} //end if
		
		// 길이 검사 실패
		if (!telText.test(tel)) {
			showErrorMessage("tel","전화번호는 숫자만 입력 가능, 최소 10자리, 최대 11자리만 가능합니다.");
			return false;
		} //end if
		
		// 유효성 검사 성공
		hideErrorMessage("tel");
		return true;
	} //validateTel
	
	// 우편번호 실시간 유효성 검사
	const zipcodeInput = document.getElementById("zipcode");
	zipcodeInput.addEventListener('input', function() {
	    validateZipcode(zipcodeInput.value);
	}); //zipcodeInput

	// 우편번호 유효성 검사 함수
	function validateZipcode(zipcode){
		// null 검사 실패
		if (!zipcode) {
		  showErrorMessage("zipcode", "우편번호를 입력하세요.");
		  return false;
		} //end if
		
		// 유효성 검사 성공
		hideErrorMessage("zipcode");
		return true;
	} //validateZipcode

	// 주소 실시간 유효성 검사
	const addressInput = document.getElementById("road-address");
	addressInput.addEventListener('input', function() {
	    validateAddress(addressInput.value);
	}); //passCheckInput

	// 주소 유효성 검사 함수
	function validateAddress(address){
		
		// null 검사 실패
		if (!address) {
			styleError("road-address");
			showErrorMessage("extra-address", "도로명 주소를 입력하세요.");
			return false;
		} //end if
		
		// 유효성 검사 성공
		hideErrorMessage("road-address");
		hideErrorMessage("extra-address");
		return true;
	} //validateAddress
	
	// 상세주소 실시간 유효성 검사
	const detailAddressInput = document.getElementById("detail-address");
	detailAddressInput.addEventListener('input', function() {
	    validateDetailAddress(detailAddressInput.value);
	}); //detailAddressInput

	// 상세주소 유효성 검사 함수
	function validateDetailAddress(detail){
		
		// null 검사 실패
		if (!detail) {
			styleError("detail-address");
			showErrorMessage("extra-address", "상세 주소를 입력하세요.");
			return false;
		} //end if
		
		// 유효성 검사 성공
		hideErrorMessage("detail-address");
		hideErrorMessage("extra-address");
		return true;
	} //validateDetailAddress
	
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
		if (!value || !value.trim() === "") {
			alert(`${fieldName}을 입력해주세요.`);
			// 체크 실패
			return false;
		} //end if
		// 체크 성공
		return true;
	} //validateNullEul

	// null 체크 함수('를')
	function validateNullRul(value, fieldName) {
		if (!value || !value.trim() === "") {
			alert(`${fieldName}를 입력해주세요.`);
			// 체크 실패
			return false;
		} //end if
		// 체크 성공
		return true;
	} //validateNullRul
	
	// 전화번호 포맷 함수
	function formatTel(tel) {
		// 10자리
		if(tel.length === 10){
			return tel.replace(/(\d{3})(\d{3})(\d{4})/,'$1-$2-$3')
		} //end if
		
		// 11자리
		if(tel.length === 11){
			return tel.replace(/(\d{3})(\d{4})(\d{4})/,'$1-$2-$3')
		} //end if
		
		return tel;
	} //formatTel
	
	const form = document.querySelector(".signup-form");
	form.addEventListener("submit", function (e) {
		e.preventDefault();
		handleSignup();
	});
	
	function handleSignup(){
		const name = document.getElementById("name").value;
	    const nickname = document.getElementById("nickname").value;
	    const email = document.getElementById("email-id").value + "@" + document.getElementById("email-domain").value;
	    const pass = document.getElementById("pass-id").value;
	    const tel = document.getElementById("tel").value;
	    const zipcode = document.getElementById("zipcode").value;
	    const address =
	      document.getElementById("road-address").value + "/" +
	      document.getElementById("detail-address").value + "/" +
	      document.getElementById("extra-address").value;
		
		// 이름 null 검사
		if( !validateNullEul(name, "이름") ){
			// null 검사 실패
			return;
		} //end if
		
		// 닉네임 null 검사
		if( !validateNullEul(nickname, "닉네임") ){
			// null 검사 실패
			return;
		} //end if
		
		// 이메일 null 검사
		if( !validateNullEul(document.getElementById("email-id").value, "이메일") ){
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
		
		// 비밀번호 확인 null 검사
		if( !validateNullEul(document.getElementById("pass-check").value, "비밀번호 확인") ){
			// null 검사 실패
			return;
		} //end if
		  
		// 전화번호 null 검사
		if( !validateNullRul(tel, "전화번호") ){
			// null 검사 실패
			return;
		} //end if
		
		// 우편번호 null 검사
		if( !validateNullRul(zipcode, "우편번호") ){
			// null 검사 실패
			return;
		} //end if
		
		// 도로명주소 null 검사
		if( !validateNullRul(document.getElementById("road-address").value, "도로명주소") ){
			// null 검사 실패
			return;
		} //end if
		
		// 상세주소 null 검사
		if( !validateNullRul(document.getElementById("detail-address").value, "상세주소") ){
			// null 검사 실패
			return;
		} //end if
		
		const telString = formatTel(tel);
		
	    const jsonParam = {
			name : name,
			nickname : nickname,
			email : email,
			pass : pass,
			tel : telString,
			zipcode : zipcode,
			address : address,
	    };
	
	    fetch('/signUpProcess', {
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
	    	if(data.success){
				// 회원가입 성공
	        	alert("회원가입이 완료되었습니다.");
	    		// 메인화면으로 이동
	        	window.location.href = '/';
	    	} else {
	    		// 회원가입 실패
	    		alert(data.message);
	        } //end if else
		}) // 응답 처리
		.catch((err) => {
			console.error("회원가입 오류:", err);
	        alert("이메일 또는 비밀번호가 잘못되었습니다.");
		}); // 에러 처리
	} //handleSignup
}); //addEventListener