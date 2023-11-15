<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- header -->
	<header class="header">
		<div class="flex">
			<div class="flex">
				<!-- service icon -->
				<a href="${root }"> <img class="service-logo"
					src="./images/service_title.png" alt="enjoy trip logo" />
				</a>

				<!-- nav menus -->
				<nav class="header-nav">
					<ul class="flex">
						<li class="gap"><a href="${root }/map" class="flex">
								<img class="nav-img" src="./images/nav/map.png" alt="map" />
								<p>지역별여행지</p>
						</a></li>
						<li class="gap"><a href="" class="flex"> <img
								class="nav-img" src="./images/nav/calendar.png" alt="calendar" />
								<p>나의여행계획</p>
						</a></li>
						<li class="gap"><a href="" class="flex"> <img
								class="nav-img" src="./images/nav/camera.png" alt="camera" />
								<p>핫플자랑하기</p>
						</a></li>
						<li class="gap"><a href="${root }/board"
							class="flex"> <img class="nav-img"
								src="./images/nav/chatting.png" alt="chatting" />
								<p>게시판</p>
						</a></li>
					</ul>
				</nav>

			</div>

			<!-- auth menus (dropdown) -->
			<nav>
				<c:if test="${!empty userinfo}">
					<label style="margin-right: 100px">${userinfo.username} (${userinfo.userid }) 님
						안녕하세요.</label>
				</c:if>
				<img class="header-users-img" src="./images/nav/users.png"
					alt="users" />
				<c:if test="${empty userinfo}">
					<div class="header-dropdown" style="display: none">
						<button onclick="javascript:modalOn('.modal-signin')">
							로그인</button>
						<button onclick="javascript:modalOn('.modal-signup')">
							회원가입</button>
					</div>
				</c:if>
				<c:if test="${!empty userinfo}">
					<div class="header-dropdown" style="display: none">
						<button onclick="javascript:modalOn('.modal-edit-userinfo')">
							내정보</button>
						<a href="${root }/logout" style="font-size: 14px">로그아웃</a>
					</div>
				</c:if>
			</nav>
		</div>
	</header>

	<div class="modal-container">
		<div class="modal-wrap modal-signin" style="display: none">
			<div class="modal1">
				<header class="modal-header">
					<h2>로그인</h2>
					<button class="modal-close-btn"
						onclick="javascript:modalOff('.modal-signin')">X</button>
				</header>
				<form action="${root}/login" method="post">
					<div class="modal-input-wrap">
						<label class="modal-label" for="modal-id">아이디</label> <input
							class="modal-input" type="text" placeholder="아이디" id="userid"
							name="userid" />
					</div>
					<div class="modal-input-wrap">
						<label class="modal-label" for="modal-pw">비밀번호</label> <input
							class="modal-input" type="password" placeholder="비밀번호"
							id="userpwd" name="userpwd" />
					</div>
					<div class="modal-input-wrap">
						<button type="submit" class="modal-submit login">로그인</button>
						<button class="modal-cancel">취소</button>
					</div>
				</form>
			</div>
		</div>

		<div class="modal-wrap modal-signup" style="display: none">
			<div class="modal1">
				<header class="modal-header">
					<h2>회원가입</h2>
					<button class="modal-close-btn"
						onclick="javascript:modalOff('.modal-signup')">X</button>
				</header>
				<form action="${root }/meminsert" method="post">
					<input type="hidden" name="action" value="register" />
					<div class="modal-input-wrap">
						<label class="modal-label" for="signin-name">이름</label> <input
							class="modal-input" type="text" placeholder="이름" id="signin-name"
							name="username" />
					</div>
					<div class="modal-input-wrap">
						<label class="modal-label" for=signin-id>아이디</label> <input
							class="modal-input" type="text" placeholder="아이디" id="signin-id"
							name="userid" />
					</div>
					<div class="modal-input-wrap">
						<label class="modal-label" for="signin-pw">비밀번호</label> <input
							class="modal-input" type="password" placeholder="비밀번호"
							id="signin-pw" name="userpwd" />
					</div>
					<div class="modal-input-wrap">
						<label class="modal-label" for="signin-email">이메일</label> <input
							class="modal-input" type="text" placeholder="이메일"
							id="signin-email" name="email" />
					</div>
					<div class="modal-input-wrap">
						<button type="submit" class="modal-submit signin">회원가입</button>
						<button class="modal-cancel">취소</button>
					</div>
				</form>
			</div>
		</div>

		<div class="modal-wrap modal-edit-userinfo" style="display: none">
			<div class="modal1">
				<header class="modal-header">
					<h2>회원정보수정</h2>
					<button class="modal-close-btn"
						onclick="javascript:modalOff('.modal-edit-userinfo')">X</button>
				</header>
				<form action="${root }/memupdate" method="post">
					<input type="hidden" name="action" value="modify" /> <input
						type="hidden" name="userinfo" value="${userinfo }" />
					<div class="modal-input-wrap">
						<label class="modal-label" for="edit-name">이름</label> <input
							class="modal-input" type="text" placeholder="이름" id="edit-name"
							name="username" value="${userinfo.username}" />
					</div>
					<div class="modal-input-wrap">
						<label class="modal-label" for="edit-id">아이디</label> <input
							class="modal-input" type="text" placeholder="아이디" id="edit-id"
							name="userid" value="${userinfo.userid}" readonly="readonly"/>
					</div>
					<div class="modal-input-wrap">
						<label class="modal-label" for="edit-pw">비밀번호</label> <input
							class="modal-input" type="password" placeholder="비밀번호"
							id="edit-pw" name="userpwd" value="${userinfo.userpwd}" />
					</div>
					<div class="modal-input-wrap">
						<label class="modal-label" for="edit-email">이메일</label> <input
							class="modal-input" type="text" placeholder="이메일" id="edit-email"
							name="email" value="${userinfo.email}" />
					</div>
					<div class="modal-input-wrap">
						<button type="submit" class="modal-submit modify">수정</button>
						<a href="${root }/memdelete?userid=${userinfo.userid}">탈퇴</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.js"></script>
	<script src="./script/modal.js"></script>
</body>
</html>