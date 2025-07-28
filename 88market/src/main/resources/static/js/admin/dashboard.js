console.log("Dashboard.js 파일 로드됨 - " + new Date().toLocaleTimeString());

var AdminDashboard = {
  _initialized: false,  // 초기화 상태 플래그
  _userChart: null,     // 가입자 차트 객체 저장용
  _tradeChart: null,    // 거래 차트 객체 저장용

  // 서버 데이터 파싱
  parseServerData: () => {
    console.log("데이터 파싱 시작");
    var chartsGrid = document.querySelector(".charts-grid");
    var userRegistrations = [];
    var tradeData = [];

    if (chartsGrid) {
      var userDataAttr = chartsGrid.getAttribute("data-user-registrations");
      var tradeDataAttr = chartsGrid.getAttribute("data-trade-data");

      console.log("Raw user data attr:", userDataAttr);
      console.log("Raw trade data attr:", tradeDataAttr);

      if (userDataAttr && userDataAttr !== "null" && userDataAttr !== "[]") {
        try {
          userRegistrations = JSON.parse(userDataAttr);
          console.log("Parsed userRegistrations:", userRegistrations);
        } catch (e) {
          console.error("가입자 데이터 파싱 오류:", e);
        }
      }

      if (tradeDataAttr && tradeDataAttr !== "null" && tradeDataAttr !== "[]") {
        try {
          tradeData = JSON.parse(tradeDataAttr);
          console.log("Parsed tradeData:", tradeData);
        } catch (e) {
          console.error("거래 데이터 파싱 오류:", e);
        }
      }
    }

    return { userRegistrations, tradeData };
  },

  // 가입자 수 라인 차트 생성
  createUserChart: function(canvasId, userData) {
    console.log("가입자 차트 생성 시작:", canvasId);
    var canvas = document.getElementById(canvasId);
    if (!canvas) {
      console.error("캔버스를 찾을 수 없음:", canvasId);
      return null;
    }

    var ctx = canvas.getContext("2d");

    var labels = [];
    var data = [];

    if (userData && userData.length > 0) {
      for (var i = 0; i < userData.length; i++) {
        var item = userData[i];
        labels.push(item.JOIN_DATE || item.join_date || "날짜없음");
        data.push(Number.parseInt(item.JOIN_COUNT || item.join_count || 0));
      }
    } else {
      labels = ["01-22", "01-23", "01-24", "01-25", "01-26", "01-27", "01-28"];
      data = [9, 2, 9, 4, 10, 5, 5];
    }

    try {
      if (this._userChart) {
        console.log("기존 가입자 차트 업데이트");
        this._userChart.data.labels = labels;
        this._userChart.data.datasets[0].data = data;
        this._userChart.update();
        return this._userChart;
      }

      this._userChart = new Chart(ctx, {
        type: "line",
        data: {
          labels: labels,
          datasets: [
            {
              label: "가입자 수",
              data: data,
              borderColor: "#4BC0C0",
              backgroundColor: "rgba(75, 192, 192, 0.2)",
              tension: 0.4,
              fill: true,
              pointBackgroundColor: "#4BC0C0",
              pointBorderColor: "#ffffff",
              pointBorderWidth: 2,
              pointRadius: 6,
            },
          ],
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            title: { display: false },
            legend: { display: true, position: "top" },
          },
          scales: {
            y: {
              beginAtZero: true,
              ticks: { stepSize: 1 },
              grid: { color: "rgba(0,0,0,0.1)" },
            },
            x: {
              grid: { color: "rgba(0,0,0,0.1)" },
            },
          },
        },
      });

      console.log("가입자 차트 생성 완료:", this._userChart);
      return this._userChart;
    } catch (error) {
      console.error("가입자 차트 생성 오류:", error);
      return null;
    }
  },

  // 거래 수 바 차트 생성
  createTradeChart: function(canvasId, tradeData) {
    console.log("거래 차트 생성 시작:", canvasId);
    var canvas = document.getElementById(canvasId);
    if (!canvas) {
      console.error("캔버스를 찾을 수 없음:", canvasId);
      return null;
    }

    var ctx = canvas.getContext("2d");

    var labels = [];
    var userTrades = [];
    var companyTrades = [];

    if (tradeData && tradeData.length > 0) {
      for (var i = 0; i < tradeData.length; i++) {
        var item = tradeData[i];
        labels.push(item.TRADE_DATE || item.trade_date || "날짜없음");
        userTrades.push(Number.parseInt(item.USER_TRADES || item.user_trades || 0));
        companyTrades.push(Number.parseInt(item.COMPANY_TRADES || item.company_trades || 0));
      }
    } else {
      labels = ["01-22", "01-23", "01-24", "01-25", "01-26", "01-27", "01-28"];
      userTrades = [3, 5, 4, 2, 1, 1, 5];
      companyTrades = [2, 2, 1, 2, 2, 2, 1];
    }

    try {
      if (this._tradeChart) {
        console.log("기존 거래 차트 업데이트");
        this._tradeChart.data.labels = labels;
        this._tradeChart.data.datasets[0].data = userTrades;
        this._tradeChart.data.datasets[1].data = companyTrades;
        this._tradeChart.update();
        return this._tradeChart;
      }

      this._tradeChart = new Chart(ctx, {
        type: "bar",
        data: {
          labels: labels,
          datasets: [
            {
              label: "일반 회원 거래",
              data: userTrades,
              backgroundColor: "rgba(255, 99, 132, 0.8)",
              borderColor: "#FF6384",
              borderWidth: 1,
            },
            {
              label: "기업 회원 거래",
              data: companyTrades,
              backgroundColor: "rgba(54, 162, 235, 0.8)",
              borderColor: "#36A2EB",
              borderWidth: 1,
            },
          ],
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            title: { display: false },
            legend: { display: true, position: "top" },
          },
          scales: {
            y: {
              beginAtZero: true,
              ticks: { stepSize: 1 },
              grid: { color: "rgba(0,0,0,0.1)" },
            },
            x: {
              grid: { color: "rgba(0,0,0,0.1)" },
            },
          },
        },
      });

      console.log("거래 차트 생성 완료:", this._tradeChart);
      return this._tradeChart;
    } catch (error) {
      console.error("거래 차트 생성 오류:", error);
      return null;
    }
  },

  // 상태 메시지 업데이트
  updateStatus: function(elementId, message, isSuccess = true) {
    var element = document.getElementById(elementId);
    if (element) {
      element.textContent = message;
      element.style.color = isSuccess ? "green" : "red";
      element.style.fontWeight = "bold";
    }
  },

  // 대시보드 초기화
  init: function() {
    if (this._initialized) {
      console.log("이미 초기화됨, 다시 생성하지 않음.");
      return true; // 이미 초기화 되었으면 바로 리턴
    }
    console.log("AdminDashboard 초기화 시작");

    if (typeof Chart === "undefined") {
      console.error("Chart.js가 로드되지 않았습니다.");
      this.updateStatus("chartStatus", "Chart.js 로드 실패", false);
      return false;
    }

    console.log("Chart.js 로드 확인, 버전:", Chart.version);
    this.updateStatus("chartStatus", "Chart.js 로드 성공 (v" + Chart.version + ")");

    var userCanvas = document.getElementById("userChart");
    var tradeCanvas = document.getElementById("tradeChart");

    if (!userCanvas) {
      console.error("userChart 캔버스를 찾을 수 없습니다.");
      this.updateStatus("createStatus", "userChart 캔버스 없음", false);
      return false;
    }
    if (!tradeCanvas) {
      console.error("tradeChart 캔버스를 찾을 수 없습니다.");
      this.updateStatus("createStatus", "tradeChart 캔버스 없음", false);
      return false;
    }

    this.updateStatus("jsStatus", "Dashboard.js 로드 성공");

    try {
      var parsedData = this.parseServerData();
      var userRegistrations = parsedData.userRegistrations;
      var tradeData = parsedData.tradeData;

      this.updateStatus("createStatus", "차트 생성 중...");

      this.createUserChart("userChart", userRegistrations);
      this.createTradeChart("tradeChart", tradeData);

      this.updateStatus("createStatus", "차트 생성 완료");
      this._initialized = true; // 초기화 완료 플래그 설정

      return true;
    } catch (error) {
      console.error("차트 생성 중 오류:", error);
      this.updateStatus("createStatus", "차트 생성 오류: " + error.message, false);
      return false;
    }
  },
};

// Chart.js 로드 대기 및 초기화
function initializeCharts() {
  console.log("차트 초기화 함수 실행");

  if (typeof Chart !== "undefined") {
    console.log("Chart.js 로드 완료, 대시보드 초기화 시작");
    AdminDashboard.init();
  } else {
    console.log("Chart.js 아직 로드되지 않음, 200ms 후 재시도");
    AdminDashboard.updateStatus("chartStatus", "Chart.js 로딩 중...");
    setTimeout(initializeCharts, 200);
  }
}

// 페이지 로드 상태에 따른 초기화
function startInitialization() {
  console.log("초기화 시작, 문서 상태:", document.readyState);

  initializeCharts();
}

// DOM 로드 완료 후 초기화
if (document.readyState === "loading") {
  document.addEventListener("DOMContentLoaded", startInitialization);
} else {
  startInitialization();
}

// 윈도우 로드 완료 후에도 한번 더 시도 (필요 시)
// window.addEventListener("load", () => {
//   console.log("Window load 이벤트 발생");
//   initializeCharts();
// });

window.AdminDashboard = AdminDashboard;

console.log("Dashboard.js 파일 로드 완료 - " + new Date().toLocaleTimeString());
