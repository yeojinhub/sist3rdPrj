document.addEventListener('DOMContentLoaded', function() {
	// .signup-form 내부의 모든 input 요소 선택
	const inputs = document.querySelectorAll('.signup-form input');

	inputs.forEach(input => {
		const errorMsg = input.nextElementSibling;
		
		input.addEventListener('input', function() {
			if (this.value.trim() !== '') {
				if (errorMsg && errorMsg.classList.contains('error-message')) {
					errorMsg.style.display = 'none';
				} //end if
				
				// 텍스트 색상 변경
				this.style.color = 'blue';
				// 배경 색상 변경
				this.style.backgroundColor = '#e0f7fa';
				// 테두리 색상 변경
				this.style.borderColor = 'black';
				this.style.borderWidth = '1px';
				this.style.borderStyle = 'solid';
				
			} else {
				
				if (errorMsg && errorMsg.classList.contains('error-message')) {
					errorMsg.style.display = 'block';
				} //end if
				
				// 값이 없을 땐 원래대로
				this.style.color = '';
				this.style.backgroundColor = '';
				this.style.borderColor = 'red';
				this.style.borderWidth = '1px';
				this.style.borderStyle = 'solid';
				
			} //end if else
			
			}); //addEventListener
	}); //forEach
	
}); //addEventListener