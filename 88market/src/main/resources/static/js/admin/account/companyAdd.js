document.addEventListener('DOMContentLoaded', function() {

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
	
	// 기업명 실시간 유효성 검사
	const comInput = document.getElementById("comName");
	comInput.addEventListener('input', function() {
	    validateCom(comInput.value);
	}); //nickInput
		
	// 기업명 유효성 검사 함수
	function validateCom(company) {
		// null 검사 실패
		if (!company){
			showErrorMessage("comName", "기업명을 입력하세요.");
			return false;
		} //end if
			
		// 길이 검사 실패
		if(company.length < 1) {
			showErrorMessage("comName", "기업명은 최소 2자리, 최대 50자리 가능합니다.");
			return false;
		} //end if
		
		// 유효성 검사 성공
		hideErrorMessage("comName");
		return true;
	} //validateCom
	
	// 대표자명 실시간 유효성 검사
	const ceoInput = document.getElementById("ceoName");
	ceoInput.addEventListener('input', function() {
	    validateCeo(ceoInput.value);
	}); //ceoInput
		
	// 대표자명 유효성 검사 함수
	function validateCeo(ceo) {
		const ceoText = /^[가-힣]+$/;
		// null 검사 실패
		if (!ceo){
			showErrorMessage("ceoName", "대표자명을 입력하세요.");
			return false;
		} //end if
			
		// 길이 검사 실패
		if(ceo.length < 1) {
			showErrorMessage("ceoName", "대표자명은 최소 2자리, 최대 50자리 가능합니다.");
			return false;
		} //end if
		
		// 한글 검사 실패
		if (!ceoText.test(ceo)) {
			showErrorMessage("ceoName", "대표자명은 한글만 가능합니다.");
			return false;
		} //end if
		
		// 유효성 검사 성공
		hideErrorMessage("ceoName");
		return true;
	} //validateCeo
	
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
	
	// 사업자등록번호 실시간 유효성 검사
	const businessInput = document.getElementById("businessNum");
	businessInput.addEventListener('input', function() {
	    validateBusiness(businessInput.value);
	}); //businessInput

	// 전화번호 유효성 검사
	function validateBusiness(tel) {
		const businessText = /^\d{10}$/;
		
		// null 검사 실패
		if (!tel) {
			showErrorMessage("businessNum","사업자번호를 입력하세요.");
			return false;
		} //end if
		
		// 길이 검사 실패
		if (!businessText.test(tel)) {
			showErrorMessage("businessNum","사업자번호는 숫자만 입력 가능, 최소 10자리만 가능합니다.");
			return false;
		} //end if
		
		// 유효성 검사 성공
		hideErrorMessage("businessNum");
		return true;
	} //validateBusiness
	
	// 아이디 실시간 유효성 검사
	const idInput = document.getElementById("id");
	idInput.addEventListener('input', function() {
	    validateId(idInput.value);
	}); //idInput
	
	// 아이디 유효성 검사 함수
	function validateId(id) {
	  const idRegex = /^[a-zA-Z0-9]+$/;

	  // null 검사 실패
	  if (!id) {
	    showErrorMessage("id", "아이디를 입력하세요.");
	    return false;
	  } //end if

	  // 길이 검사 실패
	  if (id.length < 4){
		showErrorMessage("id","아이디는 최소 4자리, 최대 15자리 가능합니다.")
		return false;
	  } //end if
	  
	  // 영문, 숫자 검사 실패
	  if (!idRegex.test(id)) {
	    showErrorMessage("id", "아이디는 영문 포함 숫자만 가능합니다.");
	    return false;
	  } //end if

	  // 유효성 검사 성공
	  hideErrorMessage("id");
	  return true;
	} //validateId
	
	
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
			styleError('pass');
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
			styleError('pass');
			showErrorMessage("pass-check","비밀번호는 최소 4자리, 최대 16자리 가능합니다.")
			return false;
		} //end if
		// 길이 검사 실패
		if (passCheck.length < 4){
			showErrorMessage("pass-check","비밀번호 확인은 최소 4자리, 최대 16자리 가능합니다.")
			return false;
		} //end if
		
		if(pass != passCheck){
			styleError('pass');
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
	
	// 도로명주소 실시간 유효성 검사
	const addressInput = document.getElementById("road-address");
	addressInput.addEventListener('input', function() {
	    validateAddress(addressInput.value);
	}); //passCheckInput

	// 도로명주소 유효성 검사 함수
	function validateAddress(address){
		
		// null 검사 실패
		if (!address) {
			showErrorMessage("road-address", "도로명 주소를 입력하세요.");
			return false;
		} //end if
		
		// 유효성 검사 성공
		hideErrorMessage("road-address");
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
			styleError('detail-address');
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
		const style = document.getElementById(inputId);
		style.style.border       = '1px solid red';
		style.style.backgroundColor = ''; // 배경색 제거
	} //styleError
	
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
	
	// 사업자등록번호 포맷 함수
	function formatBusiness(num) {
		// 10자리
		if(num.length === 10){
			return num.replace(/(\d{3})(\d{2})(\d{5})/,'$1-$2-$3')
		} //end if
		
		return num;
	} //formatBusiness
	
	const btnAdd = document.getElementById("btnAdd");
	btnAdd.addEventListener("click", function () {
		const comName = document.getElementById("comName").value;
		const ceoName = document.getElementById("ceoName").value;
		const name = document.getElementById("name").value;
	    const tel = document.getElementById("tel").value;
	    const businessNum = document.getElementById("businessNum").value;
	    const id = document.getElementById("id").value;
	    const pass = document.getElementById("pass").value;
	    const passCheck = document.getElementById("pass-check").value;
	    const zipcode = document.getElementById("zipcode").value;
	    const address =
	      document.getElementById("road-address").value +"/"+
	      document.getElementById("detail-address").value + "/"+
	      document.getElementById("extra-address").value;
	    const regDate = document.getElementById("reg-date").value;
		const withdrawValue = document.querySelector('input[name="withdraw"]:checked').value;

		// 기업명 null 검사
		if( !validateNullEul(comName, "기업명") ){
			// null 검사 실패
			return;
		} //end if
		
		// 대표자명 null 검사
		if( !validateNullEul(ceoName, "대표자명") ){
			// null 검사 실패
			return;
		} //end if
		
		// 담당자명 null 검사
		if( !validateNullEul(name, "담당자명") ){
			// null 검사 실패
			return;
		} //end if
		
		// 전화번호 null 검사
		if(!validateNullRul(tel, "전화번호")){
			// null 검사 실패
			return;
		} //end if
		
		// 사업자등록번호 null 검사
		if(!validateNullRul(businessNum, "사업자등록번호")){
			// null 검사 실패
			return;
		} //end if
		
		// 아이디 null 검사
		if(!validateNullRul(id, "아이디")){
			// null 검사 실패
			return;
		} //end if
		
		// 비밀번호, 비밀번호 확인 null 검사
		if(!validateNullRul(pass, "비밀번호") || !validateNullEul(passCheck, "비밀번호 확인")){
			// null 검사 실패
			return;
		} //end if
		
		// 우편번호 null 검사
		if(!validateNullRul(zipcode, "우편번호")){
			// null 검사 실패
			return;
		} //end if
		
		// 도로명주소 null 검사
		if(!validateNullRul(document.getElementById("road-address").value, "도로명 주소")){
			// null 검사 실패
			return;
		} //end if
		
		// 상세주소 null 검사
		if(!validateNullRul(document.getElementById("detail-address").value, "상세 주소")){
			// null 검사 실패
			return;
		} //end if
		
		const telString = formatTel(tel);
		const businessString = formatBusiness(businessNum);
		
	    const jsonParam = {
			comName : comName,
			ceoName : ceoName,
			name : name,
			nickname : nickname,
			tel : telString,
			businessNum : businessString,
			id : id,
			pass : pass,
			zipcode : zipcode,
			address : address,
			inputDate : regDate,
			withdraw : withdrawValue
		};
		
		fetch('/admin/account/companyAddProcess', {
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
				alert("기업 등록이 완료되었습니다.");
	    		// 이전 화면으로 이동
	        	window.location.href = '/admin/account/company';
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
		window.location.href = '/admin/account/company';
	}); //btnBack
	
}); //DOMContentLoaded