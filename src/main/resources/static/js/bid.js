// 입찰 상태를 한국어로 변환
function getBidStatusText(status) {
    const statusMap = {
        'WINNING': '최고가',
        'LOSING': '경쟁 중',
        'WON': '낙찰 성공',
        'LOST': '낙찰 실패',
        'ACTIVE': '진행 중'
    };
    return statusMap[status] || status;
}

// 내 입찰 로드
async function loadMyBids() {
    if (!currentUser) {
        showMessage('로그인이 필요합니다.', 'error');
        return;
    }

    const container = document.getElementById('myBidList');
    container.innerHTML = '<div class="loading"><div class="spinner"></div>로딩 중...</div>';

    try {
        const response = await fetch(`${API_BASE}/bids/my`, {
            headers: { 
                'Authorization': `Bearer ${accessToken}`
            }
        });
        const bids = await response.json();

        if (bids.length === 0) {
            container.innerHTML = '<p>입찰 내역이 없습니다.</p>';
            return;
        }

        container.innerHTML = bids.map(bid => `
            <div class="bid-item ${bid.status === 'WINNING' ? 'winning' : ''}">
                <h4>${bid.auctionItemTitle}</h4>
                <p>입찰가: <strong>${bid.bidAmount.toLocaleString()}원</strong></p>
                <p>입찰 시간: ${new Date(bid.bidTime).toLocaleString()}</p>
                <p>상태: <span class="status ${bid.status}">${getBidStatusText(bid.status)}</span></p>
            </div>
        `).join('');
    } catch (error) {
        container.innerHTML = '<p>입찰 내역을 불러오는 중 오류가 발생했습니다.</p>';
    }
}

// 입찰 모달 열기 (입찰 가능)
async function openBidModal(auctionId) {
    if (!currentUser) {
        showMessage('로그인이 필요합니다.', 'error');
        return;
    }

    await loadBidModalContent(auctionId, true);
}

// 입찰 내역만 보기 (입찰 불가)
async function viewAuctionBids(auctionId) {
    if (!currentUser) {
        showMessage('로그인이 필요합니다.', 'error');
        return;
    }

    await loadBidModalContent(auctionId, false);
}

// 입찰 모달 컨텐츠 로드
async function loadBidModalContent(auctionId, canBid) {
    document.getElementById('bidAuctionId').value = auctionId;
    
    try {
        // 경매 정보 로드
        const auctionResponse = await fetch(`${API_BASE}/items/${auctionId}?itemId=${auctionId}`, {
            headers: { 
                'Authorization': `Bearer ${accessToken}`
            }
        });
        const auction = await auctionResponse.json();

        // 입찰 내역만 보기인 경우 경매 정보를 표시하지 않음
        if (canBid) {
            document.getElementById('modalAuctionInfo').innerHTML = `
                <h3>${auction.title}</h3>
                <p>${auction.description}</p>
                <p class="price">현재가: ${auction.currentPrice.toLocaleString()}원</p>
                <p>최소 입찰가: ${(auction.currentPrice + 1000).toLocaleString()}원</p>
            `;
        } else {
            document.getElementById('modalAuctionInfo').innerHTML = '';
        }

        // 입찰 폼 표시/숨김
        const bidForm = document.getElementById('bidForm');
        if (canBid) {
            bidForm.style.display = 'block';
            document.getElementById('bidAmount').min = auction.currentPrice + 1000;
            document.getElementById('bidAmount').value = auction.currentPrice + 10000;
        } else {
            bidForm.style.display = 'none';
        }

        // 입찰 내역 로드
        const bidsResponse = await fetch(`${API_BASE}/bids?auctionId=${auctionId}`, {
            headers: { 
                'Authorization': `Bearer ${accessToken}`
            }
        });
        const bids = await bidsResponse.json();

        document.getElementById('bidHistory').innerHTML = `
            <h4>입찰 내역 (${bids.length}건)</h4>
            ${bids.length === 0 ? '<p>아직 입찰 내역이 없습니다.</p>' : bids.map(bid => `
                <div class="bid-item ${bid.status === 'WINNING' ? 'winning' : ''}">
                    <p>${bid.bidderName}: <strong>${bid.bidAmount.toLocaleString()}원</strong></p>
                    <p>${new Date(bid.bidTime).toLocaleString()}</p>
                </div>
            `).join('')}
        `;

        document.getElementById('bidModal').classList.add('active');
    } catch (error) {
        showMessage('경매 정보를 불러오는 중 오류가 발생했습니다.', 'error');
    }
}

// 입찰 모달 닫기
function closeBidModal() {
    document.getElementById('bidModal').classList.remove('active');
}

// 입찰하기
async function placeBid(e) {
    e.preventDefault();
    
    const data = {
        auctionItemId: parseInt(document.getElementById('bidAuctionId').value),
        bidAmount: parseInt(document.getElementById('bidAmount').value)
    };

    try {
        const response = await fetch(`${API_BASE}/bids`, {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${accessToken}`
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            showMessage('입찰에 성공했습니다!');
            closeBidModal();
            loadAuctions();
        } else {
            const error = await response.text();
            showMessage(`입찰 실패: ${error}`, 'error');
        }
    } catch (error) {
        showMessage('입찰 중 오류가 발생했습니다.', 'error');
    }
}
