document.addEventListener('DOMContentLoaded', function() {
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
		
	// 이름 유효성 검사 함수
	function validateNick(nick) {
		const nickText = /^[가-힣]+$/;
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
		
		// 한글 검사 실패
		if (!nickText.test(nick)) {
			showErrorMessage("nickname", "닉네임은 한글만 가능합니다.");
			return false;
		} //end if
		
		// 유효성 검사 성공
		hideErrorMessage("nickname");
		return true;
	} //validateNick
/*	
	// 전화번호 실시간 유효성 검사
	const telInput = document.getElementById("tel");
	telInput.addEventListener('input', function() {
	    // 전화번호 포맷
	    const formattedTel = formatTel(telInput.value);
	    telInput.value = formattedTel;

	    // 포맷 후 전화번호 유효성 검사
	    validateTel(formattedTel);
	}); //telInput

	// 전화번호 포맷 함수
	function formatTel(tel) {
	    // 숫자만 추출
	    let cleaned = tel.replace(/[^0-9]/g, '');
	    if (cleaned.length < 4) {
	        return cleaned;
	    } else if (cleaned.length < 7) {
	        return cleaned.replace(/(\d{3})(\d{0,4})/, "$1-$2");
	    } else {
	        return cleaned.replace(/(\d{3})(\d{3})(\d{0,4})/, "$1-$2-$3");
	    }
	}
	// 전화번호 유효성 검사
	function validateTel(tel) {
		const telText = /^\d{3}-\d{3,4}-\d{4}$/;
		if (!tel || !telText.test(tel)) {
			showErrorMessage("tel","전화번호는 숫자만 입력하고, 10자리 또는 11자리만 가능합니다.");
			// 유효성 검사 실패
			return false;
		} //end if
		// 유효성 검사 성공
		hideErrorMessage("tel");
		return true;
	} //validateTel
*/	
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
	    showErrorMessage("email", "이메일을 입력하세요.");
	    return false;
	  } //end if

	  // 길이 검사 실패
	  if (email < 4){
		showErrorMessage("email","이메일은 최소 4자리, 최대 15자리 가능합니다.")
		return false;
	  } //end if
	  
	  // 영문, 숫자 검사 실패
	  if (!emailRegex.test(email)) {
	    showErrorMessage("email", "이메일은 영문 포함 숫자만 가능합니다.");
	    return false;
	  } //end if

	  // 유효성 검사 성공
	  hideErrorMessage("email");
	  return true;
	} //validateEmail
	
	// 비밀번호와 비밀번호 확인 실시간 유효성 검사
	const passInput = document.getElementById("pass");
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
		  showErrorMessage("pass-check", "비밀번호를 입력하세요.");
		  return false;
		} //end if
		// null 검사 실패
		if (!passCheck) {
		  showErrorMessage("pass-check", "비밀번호 확인을 입력하세요.");
		  return false;
		} //end if
		
		// 길이 검사 실패
		if (pass.length < 4){
			showErrorMessage("pass-check","비밀번호는 최소 4자리, 최대 16자리 가능합니다.")
			return false;
		} //end if
		// 길이 검사 실패
		if (passCheck.length < 4){
			showErrorMessage("pass-check","비밀번호 확인은 최소 4자리, 최대 16자리 가능합니다.")
			return false;
		} //end if
		
		if(pass != passCheck){
			showErrorMessage("pass-check","비밀번호와 비밀번호 확인이 일치하지 않습니다.\n비밀번호를 다시 확인해주세요.");
			return false;
		} //end if
		
		// 유효성 검사 성공
		hideErrorMessage("pass");
		hideErrorMessage("pass-check");
		return true;
	} //validatePass
	
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
		  showErrorMessage("address", "주소를 입력하세요.");
		  return false;
		} //end if
		
		// 유효성 검사 성공
		hideErrorMessage("address");
		return true;
	} //validateAddress
	
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
	} //showErrorMessage

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
			
	const btnAdd = document.getElementById("btnAdd");
	
	btnAdd.addEventListener("click", function () {
		const name = document.getElementById("name").value;
	    const nickname = document.getElementById("nickname").value;
	    const tel = document.getElementById("tel").value;
	    const email = document.getElementById("email-id").value + "@" + document.getElementById("email-domain").value;
	    const pass = document.getElementById("pass").value;
	    const zipcode = document.getElementById("zipcode").value;
	    const address =
	      document.getElementById("road-address").value +"/"+
	      document.getElementById("detail-address").value + "/"+
	      document.getElementById("extra-address").value;
	    const regDate = document.getElementById("reg-date").value;
		const banValue = document.querySelector('input[name="ban"]:checked').value;
		const withdrawValue = document.querySelector('input[name="withdraw"]:checked').value;

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
		
		// 전화번호 null 검사
		if(!validateNullRul(tel, "전화번호")){
			// null 검사 실패
			return;
		} //end if
		
		// 이메일 null 검사
		if(!validateNullRul(document.getElementById("email-id").value, "이메일")){
			// null 검사 실패
			return;
		} //end if
		
		// 비밀번호, 비밀번호 확인 null 검사
		if(!validateNullRul(pass, "비밀번호") || !validateNullEul(passCheck, "비밀번호 확인")){
			// null 검사 실패
			return;
		} //end if
		
	    const jsonParam = {
			name : name,
			nickname : nickname,
			tel : tel,
			email : email,
			pass : pass,
			zipcode : zipcode,
			address : address,
			inputDate : regDate,
			banType : banValue,
			withdraw : withdrawValue
		};
		
		fetch('/admin/account/userAddProcess', {
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
			if(data.result){
				// 등록 성공
				alert("회원 정보 등록이 완료되었습니다.");
	    		// 이전 화면으로 이동
	        	window.location.href = '/admin/account/users';
	    	} else {
	    		// 등록 실패
	    		alert(data.msg);
	        } //end if else
		}).catch((err) => {
	        console.error("등록 오류:", err);
		}); // 에러 처리
	}); //btnAdd
	
	const btnBack = document.getElementById("btnBack");
	btnBack.addEventListener("click", function () {
		// 이전 화면으로 이동
		window.location.href = '/admin/account/users';
	}); //btnBack
	
}); //DOMContentLoaded