// API 설정
const API_BASE = '/auction';
let currentUser = null;
let accessToken = null;

// 페이지 로드 시 실행
window.onload = function() {
    // 로컬 스토리지에서 토큰 복원
    const savedToken = localStorage.getItem('accessToken');
    const savedUser = localStorage.getItem('currentUser');
    
    if (savedToken && savedUser) {
        accessToken = savedToken;
        currentUser = JSON.parse(savedUser);
        updateUserInfo();
    }
    
    // 현재 시간 설정
    setDefaultDateTime();
};

// 메시지 표시
function showMessage(message, type = 'success') {
    const msgEl = document.getElementById('message');
    msgEl.textContent = message;
    msgEl.className = `message ${type} active`;
    
    setTimeout(() => {
        msgEl.classList.remove('active');
    }, 5000);
}

// 기본 날짜/시간 설정
function setDefaultDateTime() {
    const now = new Date();
    const startTime = new Date(now.getTime() + 60 * 60 * 1000); // 1시간 후
    const endTime = new Date(now.getTime() + 7 * 24 * 60 * 60 * 1000); // 7일 후

    const startElement = document.getElementById('auctionStartTime');
    const endElement = document.getElementById('auctionEndTime');

    if (startElement) {
        startElement.value = formatDateTime(startTime);
    }
    if (endElement) {
        endElement.value = formatDateTime(endTime);
    }
}

function formatDateTime(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    return `${year}-${month}-${day}T${hours}:${minutes}`;
}
