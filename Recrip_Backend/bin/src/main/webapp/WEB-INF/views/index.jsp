<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<title>Title</title>
<link rel="stylesheet" href="./css/main.css" />
<link rel="stylesheet" href="./css/slider.css" />
<link rel="stylesheet" href="./css/plan.css" />
<link rel="stylesheet" href="./css/hot_place.css" />
<link rel="stylesheet" href="./css/bottom.css" />
<link rel="stylesheet" href="./css/button.css" />
<link rel="stylesheet" href="./css/style.css" />
<script src="script/slider.js"></script>
</head>
<body>
	<jsp:include page="./header.jsp"></jsp:include>

	<!--슬라이더-->
	<div class="image-slideshow">
		<div class="image fade">
			<img src="images/top.png" alt="Mountain Top" />
		</div>
		<div class="image fade">
			<img src="images/top2.png" alt="Palm Trees" />
		</div>
		<div class="image fade">
			<img src="images/top.png" alt="Neon Sign" />
		</div>
		<div class="image-on-text">
			<p>지금</p>
			<p>Enjoy!!!Trip</p>
			<p>과 함께 우리지역의 관광지를 알아보고 나만의 여행 계획을 만들어보세요!!!</p>
		</div>
	</div>
	<div class="wrapper">
		<div class="sidebar">
			<div class="divider"></div>
			<div class="love">
				<p>지역사랑!</p>
				<h2>우리 지역 관광지</h2>
				<p>우리지역의 숨어있는 아름다운 관광지를 알려드립니다.</p>
				<p>관광지 주변의 맛집, 숙박업소와 여행코스, 지역 축제등을 보실수도 있습니다.</p>
			</div>
			<div style="height: 16px"></div>
			<button class="custom-button">더보기..</button>
		</div>
		<div class="box wrapper2">
			<div class="main-image-container">
				<div class="center-grid-image"
					style="background-image: url(http://tong.visitkorea.or.kr/cms/resource/25/2747925_image2_1.JPG)"></div>
				<p>멋 진</p>
			</div>
			<div class="main-image-container">
				<div class="center-grid-image"
					style="background-image: url(http://tong.visitkorea.or.kr/cms/resource/62/1588262_image2_1.jpg)"></div>
				<p>곳으로</p>
			</div>
			<div class="main-image-container">
				<div class="center-grid-image"
					style="background-image: url(http://tong.visitkorea.or.kr/cms/resource/87/2782287_image2_1.jpg)"></div>
				<p>놀 러</p>
			</div>
			<div class="main-image-container">
				<div class="center-grid-image"
					style="background-image: url(http://tong.visitkorea.or.kr/cms/resource/91/1944891_image2_1.jpg)"></div>
				<p>가보자</p>
			</div>
			<div class="main-image-container">
				<div class="center-grid-image"
					style="background-image: url(http://tong.visitkorea.or.kr/cms/resource/86/2762786_image2_1.JPG)"></div>
				<p>야</p>
			</div>
			<div class="main-image-container">
				<div class="center-grid-image"
					style="background-image: url(http://tong.visitkorea.or.kr/cms/resource/96/2705196_image2_1.jpg)"></div>
				<p>호</p>
			</div>
		</div>
	</div>
	<div class="divider-container" style="background-color: #ffffff">
		<div class="divider"></div>
		<h2>나만의 여행 계획!!!</h2>
		<h3>여행 경로, 숙박, 광광지, 예상금액등 나만의 멋진 계획을 세워 공유해 주세요!!!</h3>
	</div>
	<div class="plan-wrapper">
		<div class="card">
			<img src="images/maps/map1.png" alt="Avatar" style="width: 100%" />
			<div class="card-info">
				<h4>
					<b>플랜 A</b>
				</h4>
				<p>즐거운 여행</p>
			</div>
		</div>
		<div class="card">
			<img src="images/maps/map2.png" alt="Avatar" style="width: 100%" />
			<div class="card-info">
				<h4>
					<b>플랜 B</b>
				</h4>
				<p>즐거운 여행</p>
			</div>
		</div>
		<div class="card">
			<img src="images/maps/map3.png" alt="Avatar" style="width: 100%" />
			<div class="card-info">
				<h4>
					<b>플랜 C</b>
				</h4>
				<p>즐거운 여행</p>
			</div>
		</div>
		<div class="card">
			<img src="images/maps/map4.png" alt="Avatar" style="width: 100%" />
			<div class="card-info">
				<h4>
					<b>플랜 D</b>
				</h4>
				<p>즐거운 여행</p>
			</div>
		</div>
	</div>
	<div class="divider-container">
		<div class="divider"></div>
		<h2>핫플 자랑하기</h2>
		<h3>나만 알고있는 핫플!!! 자랑해 주세요.</h3>
	</div>
	<div class="place-wrapper">
		<div class="place-card"
			style="background-image: url(images/hotplace/1.jpg); background-size: cover">
			<div class="place-info-container">
				<h4>거북이 마을</h4>
				<p>거북이 마을 수선화 축제에요 시간 나면 꼭 한번 가보세요 너무너무 이뻐염~</p>
			</div>
		</div>
		<div class="place-card"
			style="background-image: url(images/hotplace/2.jpg); background-size: cover">
			<div class="place-info-container">
				<h4>거북이 마을</h4>
				<p>거북이 마을 수선화 축제에요 시간 나면 꼭 한번 가보세요 너무너무 이뻐염~</p>
			</div>
		</div>
		<div class="place-card"
			style="background-image: url(images/hotplace/3.jpg); background-size: cover">
			<div class="place-info-container">
				<h4>멀티 캠퍼스</h4>
				<p>좋아</p>
			</div>
		</div>
		<div class="place-card"
			style="background-image: url(images/hotplace/4.jpg); background-size: cover">
			<div class="place-info-container">
				<h4>멀티 캠퍼스</h4>
				<p>좋아</p>
			</div>
		</div>
		<div class="place-card"
			style="background-image: url(images/hotplace/5.jpg); background-size: cover">
			<div class="place-info-container">
				<h4>대동집</h4>
				<p>맛나요</p>
			</div>
		</div>
		<div class="place-card"
			style="background-image: url(images/hotplace/6.jpg); background-size: cover">
			<div class="place-info-container">
				<h4>거북이마을</h4>
				<p>거북이 마을 수선화 축제에요 시간 나면 꼭 한번 가보세요 너무너무 이뻐염~</p>
			</div>
		</div>
		<div class="place-card"
			style="background-image: url(images/hotplace/7.jpg); background-size: cover">
			<div class="place-info-container">
				<h4>고척 스카이돔</h4>
				<p>고척 스카이돔 야구장입니다~~~~~ 키움 히어로즈 경기보러오세요~~ 여름에 아주 시원합니다 크림새우가
					맛있으니까 와서 먹어보세용~~!!!!!!!!</p>
			</div>
		</div>
		<div class="place-card"
			style="background-image: url(images/hotplace/8.jpg); background-size: cover">
			<div class="place-info-container">
				<h4>영월 한반도 지형</h4>
				<p>한반도 모양인 지형을 볼 수 있어용</p>
			</div>
		</div>
		<div class="place-card"
			style="background-image: url(images/hotplace/9.jpg); background-size: cover">
			<div class="place-info-container">
				<h4>멀캠</h4>
				<p>키보드</p>
			</div>
		</div>
	</div>
	<div class="bottom_contact">
		<p>언제든 연락주세요.!</p>
		<p>Enjoy!!! Trip은 여러분의 많은 참여를 기다립니다.</p>
		<p>숨어있는 지역 명소와 함께 즐기고 싶은 여행일정이 있다면 언제든지 환영입니다.</p>
		<p>02-1234-5678</p>
	</div>

	<jsp:include page="./footer.jsp"></jsp:include>

</body>
</html>
