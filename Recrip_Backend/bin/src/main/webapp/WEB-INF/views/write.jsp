<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

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
					<form id="form-register" method="POST" action="">
						<input type="hidden" id="nowid" value="${userinfo.userid}">
						<div class="mb-3">
							<label for="subject" class="form-label">제목 : </label> <input
								type="text" class="form-control" id="subject" name="subject"
								placeholder="제목..." />
						</div>
						<div class="mb-3">
							<label for="content" class="form-label">내용 : </label>
							<textarea class="form-control" id="content" name="content"
								rows="7"></textarea>
						</div>
						<div class="col-auto text-center">
							<button type="button" id="btn-register"
								class="btn btn-outline-primary mb-3">글작성</button>
							<button type="reset" class="btn btn-outline-danger mb-3">초기화</button>
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
      document.querySelector("#btn-register").addEventListener("click", function () {
        if (!document.querySelector("#subject").value) {
          alert("제목 입력!!");
          return;
        } else if (!document.querySelector("#content").value) {
          alert("내용 입력!!");
          return;
        } else {
        	var url = "<%= root%>/boardwrite";
        	var userid = document.querySelector("#nowid").value;
        	var subject = document.querySelector("#subject").value;
        	var content = document.querySelector("#content").value;
        	
            fetch(url, {
            	method: "POST",
            	headers: {
            		"Content-Type": "application/json",
            	},
            	body: JSON.stringify({
            		userid: userid,
            		subject: subject,
            		content: content
            	}),
            })
            .then((response) => response.json())
            .then((result) => {
            	console.log(result);
            	alert("등록 완료");
            	window.location.href = "<%=root%>/board";
            });
        }
      });
    </script>

</body>
</html>
