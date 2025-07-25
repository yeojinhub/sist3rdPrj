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
	
	const btnModify = document.getElementById("btnModify");
	
	btnModify.addEventListener("click", function () {
		const userNum = document.getElementById("userNum").value;
		const name = document.getElementById("name").value;
	    const nickname = document.getElementById("nickname").value;
	    const tel = document.getElementById("tel").value;
	    const email = document.getElementById("email-id").value + "@" + document.getElementById("email-domain").value;
	    const zipcode = document.getElementById("zipcode").value;
	    const address =
	      document.getElementById("road-address").value +"/"+
	      document.getElementById("detail-address").value + "/"+
	      document.getElementById("extra-address").value;
		const banValue = document.querySelector('input[name="ban"]:checked').value;
		const withdrawValue = document.querySelector('input[name="withdraw"]:checked').value;

	    const jsonParam = {
			userNum : userNum,
			name : name,
			nickname : nickname,
			tel : tel,
			email : email,
			zipcode : zipcode,
			address : address,
			banType : banValue,
			withdraw : withdrawValue
		};
		
		fetch('/admin/account/userModifyProcess', {
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
				// 수정 성공
				alert("회원 정보 수정이 완료되었습니다.");
	    		// 이전 화면으로 이동
				window.location.href = '/admin/account/users';
	    	} else {
	    		// 수정 실패
	    		alert(data.msg);
	        } //end if else
		}).catch((err) => {
	        console.error("수정 오류:", err);
		}); // 에러 처리
	}); //btnModify
	
	const btnBack = document.getElementById("btnBack");
	btnBack.addEventListener("click", function () {
		// 이전 화면으로 이동
		window.location.href = '/admin/account/users';
	}); //btnBack
	
}); //DOMContentLoaded