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
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous" />
<link rel="stylesheet" href="./css/style.css" />
<link rel="stylesheet" href="css/main.css" />
</head>
<body>
	<jsp:include page="./header.jsp"></jsp:include>

	<!-- main -->
	<main>
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-lg-8 col-md-10 col-sm-12">
					<h2 class="my-3 py-3 shadow-sm bg-light text-center">
						<mark class="sky" id="subject">${view.subject}</mark>
					</h2>
				</div>
				<div class="col-lg-8 col-md-10 col-sm-12">
					<div class="row">
						<div class="col-md-8">
							<div class="clearfix align-content-center">
								<img class="avatar me-2 float-md-start bg-light p-2"
									src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg" />
								<p>
									<span class="fw-bold" id="user">${view.userid }</span> <br />
									<span class="text-secondary fw-light" id="date"> ${view.date }
									</span>
								</p>
							</div>
						</div>
						<div class="divider mb-3"></div>
						<div class="text-secondary" id="content">
							${view.content}
						</div>
						<div class="divider mt-3 mb-3"></div>
						<div class="d-flex justify-content-end">
							<button type="button" id="btn-list"
								class="btn btn-outline-primary mb-3">글목록</button>
							<c:if test="${view.userid eq userinfo.userid }">
							<button type="button" id="btn-mv-modify"
								class="btn btn-outline-success mb-3 ms-1">글수정</button>
							<button type="button" id="btn-delete"
								class="btn btn-outline-danger mb-3 ms-1">글삭제</button>
							</c:if>
						</div>
					</div>
				</div>
				
				<!-- 댓글 작성 -->
				<c:if test="${!empty userinfo }">
				<form id="comment" class="col-lg-8 col-md-10 col-sm-12">
					<div class="input-group mb-3">
						<input type="hidden" id="articleno" value="${view.articleno}"/>
						<input type="hidden" id="nowid" value="${userinfo.userid}"/>
						<span class="input-group-text">${userinfo.userid }</span>
						<div class="form-floating">
						    <textarea id="commentarea" class="form-control" placeholder="Leave a comment here" id="floatingTextarea" style="height: 150px"></textarea>
						    <label for="floatingInputGroup1">댓글</label>
					    </div>
					    <button id="btn-cm-regist" type="button" class="btn btn-primary">작성</button>
					</div>
				</form>
				</c:if>
				<!-- 댓글 목록 -->
				<div id="comment-area" class="col-lg-8 col-md-10 col-sm-12">
					<ul class="list-group">
						<c:forEach items="${commentlist}" var="comment">
					    <li class="list-group-item">
					  		<div class="media mt-3 mb-3">
						  		<div class="media-body">
						  			<h5 class="mt-0">${comment.userid}</h5>
						  			${comment.content}
						  			${comment.date}
						  		</div>
					  		</div>
					  		<c:if test="${userinfo.userid eq comment.userid }">
					  		<div class="d-flex justify-content-end">
								<button type="button"
									class="btn btn-outline-danger ms-1" 
								id="btn-cm-delete" onclick="javascript:cmdel(${comment.commentno},${comment.articleno })">댓글삭제</button>
							</div>
							<</c:if>
					    </li>
					    </c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</main>

	<jsp:include page="./footer.jsp"></jsp:include>

	<!-- scripts -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
		crossorigin="anonymous"></script>
		
	<script>
      document.querySelector("#btn-list")?.addEventListener("click", function () {
        location.href = "<%=root%>/board";
      });
      document.querySelector("#btn-mv-modify")?.addEventListener("click", function () {
        location.href = "<%=root%>/modify?articleno=${view.articleno}";
      });
      document.querySelector("#btn-delete")?.addEventListener("click", function () {
        var url = "<%=root%>/boarddelete?articleno=${view.articleno}";
        
        fetch(url , {
        	method: "DELETE",
        })
        .then((response) => response.json())
        .then((result) => {
        	console.log(result);
        	alert("삭제 완료");
        	window.location.href = "<%=root%>/board";
        })
	  });
      
      document.querySelector("#btn-cm-regist").addEventListener("click", function () {
       	var url = "<%= root%>/commentwrite";
    	var userid = document.querySelector("#nowid").value;
    	var articleno = document.querySelector("#articleno").value;
    	var content = document.querySelector("#commentarea").value;
    	
    	console.log(userid);
    	
        fetch(url, {
        	method: "POST",
        	headers: {
        		"Content-Type": "application/json",
        	},
        	body: JSON.stringify({
        		articleno: articleno,
        		userid: userid,
        		content: content
        	}),
        })
        .then((response) => response.json())
        .then((result) => {
        	console.log(result);
        	alert("댓글 작성 완료");
        });
        window.location.href = "<%=root%>/view?articleno="+articleno;
      });
      
      const cmdel = function(commentno,articleno) {
		  var url = "<%= root%>/commentdelete?commentno="+commentno;
    	  
    	  fetch(url , {
          	method: "DELETE",
          })
          .then((response) => response.json())
          .then((result) => {
          	console.log(result);
          	alert("댓글 삭제 완료");
          	window.location.href = "<%=root%>/view?articleno="+articleno;
          })
      }
	</script>
	
</body>
</html>
