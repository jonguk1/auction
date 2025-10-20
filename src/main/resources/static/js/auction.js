// 경매 상태를 한국어로 변환
function getAuctionStatusText(status) {
    const statusMap = {
        'ACTIVE': '진행 중',
        'PENDING': '대기 중',
        'CLOSED': '마감',
        'COMPLETED': '종료'
    };
    return statusMap[status] || status;
}

// 경매 목록 로드
async function loadAuctions() {
    const container = document.getElementById('auctionList');
    container.innerHTML = '<div class="loading"><div class="spinner"></div>로딩 중...</div>';

    try {
        const response = await fetch(`${API_BASE}/items/active`, {
            headers: { 
                'Authorization': `Bearer ${accessToken}`
            }
        });
        const auctions = await response.json();

        if (auctions.length === 0) {
            container.innerHTML = '<p>진행 중인 경매가 없습니다.</p>';
            return;
        }

        container.innerHTML = auctions.map(auction => `
            <div class="auction-card" onclick="openBidModal(${auction.id})">
                <span class="status ${auction.status}">${getAuctionStatusText(auction.status)}</span>
                <h3>${auction.title}</h3>
                <p>${auction.description}</p>
                <p class="price">현재가: ${auction.currentPrice.toLocaleString()}원</p>
                <p>시작가: ${auction.startPrice.toLocaleString()}원</p>
                <p>판매자: ${auction.sellerName}</p>
                <p>종료: ${new Date(auction.endTime).toLocaleString()}</p>
            </div>
        `).join('');
    } catch (error) {
        container.innerHTML = '<p>경매 목록을 불러오는 중 오류가 발생했습니다.</p>';
    }
}

// 경매 등록
async function createAuction(e) {
    e.preventDefault();
    
    if (!currentUser) {
        showMessage('로그인이 필요합니다.', 'error');
        return;
    }

    const data = {
        title: document.getElementById('auctionTitle').value,
        description: document.getElementById('auctionDescription').value,
        startPrice: parseInt(document.getElementById('auctionStartPrice').value),
        startTime: document.getElementById('auctionStartTime').value,
        endTime: document.getElementById('auctionEndTime').value
    };

    try {
        const response = await fetch(`${API_BASE}/items/create?sellerId=${currentUser.id}`, {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${accessToken}`
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            showMessage('경매가 등록되었습니다!');
            e.target.reset();
            setDefaultDateTime();
            showTab('myAuctions');
        } else {
            const error = await response.text();
            showMessage(`등록 실패: ${error}`, 'error');
        }
    } catch (error) {
        showMessage('경매 등록 중 오류가 발생했습니다.', 'error');
    }
}

// 내 경매 로드
async function loadMyAuctions() {
    if (!currentUser) {
        showMessage('로그인이 필요합니다.', 'error');
        return;
    }

    const container = document.getElementById('myAuctionList');
    container.innerHTML = '<div class="loading"><div class="spinner"></div>로딩 중...</div>';

    try {
        const response = await fetch(`${API_BASE}/items/my?userId=${currentUser.id}`, {
            headers: { 
                'Authorization': `Bearer ${accessToken}`
            }
        });
        const auctions = await response.json();

        if (auctions.length === 0) {
            container.innerHTML = '<p>등록한 경매가 없습니다.</p>';
            return;
        }

        container.innerHTML = auctions.map(auction => `
            <div class="auction-card">
                <span class="status ${auction.status}">${auction.status}</span>
                <h3>${auction.title}</h3>
                <p>${auction.description}</p>
                <p class="price">현재가: ${auction.currentPrice.toLocaleString()}원</p>
                <p>시작가: ${auction.startPrice.toLocaleString()}원</p>
                <p>종료: ${new Date(auction.endTime).toLocaleString()}</p>
                <button class="btn" onclick="viewAuctionBids(${auction.id})">입찰 내역 보기</button>
            </div>
        `).join('');
    } catch (error) {
        container.innerHTML = '<p>경매 목록을 불러오는 중 오류가 발생했습니다.</p>';
    }
}
