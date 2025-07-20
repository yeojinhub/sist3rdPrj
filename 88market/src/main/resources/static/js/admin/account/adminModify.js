document.addEventListener('DOMContentLoaded', function() {
	
	const btnModify = document.getElementById("btnModify");
	
	btnModify.addEventListener("click", function () {
		const admNum = document.getElementById("admNum").value;
		const name = document.getElementById("name").value;
	    const tel = document.getElementById("tel").value;
	    const id = document.getElementById("id").value;
		const banValue = document.querySelector('input[name="ban"]:checked').value;

	    const jsonParam = {
			admNum : admNum,
			name : name,
			tel : tel,
			id : id,
			bantype : banValue
		};
		
		fetch('/admin/account/adminModifyProcess', {
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
				alert("관리자 정보 수정이 완료되었습니다.");
	    		// 이전 화면으로 이동
	        	window.history.back();
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
		window.history.back();
	}); //btnBack
	
}); //DOMContentLoaded