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
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous" />
<link rel="stylesheet" href="./css/style.css" />
<link rel="stylesheet" href="css/main.css" />
<title>Document</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="./header.jsp"></jsp:include>

	<main>
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-lg-8 col-md-10 col-sm-12">
					<h2 class="my-3 py-3 shadow-sm bg-light text-center">
						<mark class="sky">글쓰기</mark>
					</h2>
				</div>
				<div class="col-lg-8 col-md-10 col-sm-12">
					<form id="form-update" method="POST" action="">
						<input type="hidden" id="userid" value="${view.userid}">
						<input type="hidden" id="articleno" value="${view.articleno}">
						<div class="mb-3">
							<label for="subject" class="form-label">제목 : </label>
							<input
								type="text" class="form-control" id="subject" name="subject"
								placeholder="제목..." value="${view.subject}"/>
						</div>
						<div class="mb-3">
							<label for="content" class="form-label">내용 : </label>
							<textarea class="form-control" id="content" name="content"
								rows="7">${view.content}</textarea>
						</div>
						<div class="col-auto text-center">
							<button type="button" id="btn-update"
								class="btn btn-outline-primary mb-3">수정</button>
							<button type="button" class="btn btn-outline-danger mb-3" id="btn-cancel">취소</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</main>

	<jsp:include page="./footer.jsp"></jsp:include>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
		crossorigin="anonymous"></script>

	<script>
      document.querySelector("#btn-update").addEventListener("click", function () {
       	var url = "<%= root%>/boardupdate";
    	var userid = document.querySelector("#userid").value;
    	var articleno = document.querySelector("#articleno").value;
    	var subject = document.querySelector("#subject").value;
    	var content = document.querySelector("#content").value;
    	
        fetch(url, {
        	method: "PUT",
        	headers: {
        		"Content-Type": "application/json",
        	},
        	body: JSON.stringify({
        		articleno: articleno,
        		userid: userid,
        		subject: subject,
        		content: content
        	}),
        })
        .then((response) => response.json())
        .then((result) => {
        	console.log(result);
        	alert("수정완료");
        	window.location.href = "<%=root%>/view?articleno="+articleno;
        });
        
      });
    </script>

</body>
</html>
