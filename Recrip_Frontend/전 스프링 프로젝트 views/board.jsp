<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> <% String root = request.getContextPath(); %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
      crossorigin="anonymous"
    />
    <link rel="stylesheet" href="./css/style.css" />
    <link rel="stylesheet" href="css/main.css" />
    <title>Document</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script type="text/javascript">
      window.onload = () => {
        var url = "<%=root%>/boardlist?pgno=1";

        fetch(url)
          .then((response) => response.json())
          .then((json) => {
            data = json.resdata;
            console.log(json);
            let html = "";
            data.forEach((elements) => {
              console.log(elements);
              html += `
					<tr class="text-center">
					<th scope="row">\${elements.articleno}</th>
					<td class="text-start"><a href="<%=root%>/view?articleno=\${elements.articleno}"
						class="article-title link-dark" data-no="1"
						style="text-decoration: none"> \${elements.subject } </a></td>
					<td>\${elements.userid }</td>
					<td>\${elements.date }</td>
					</tr>
				`;
            });
            document.querySelector("#boardlist").innerHTML = html;
          });

        var nav = "<%= root%>/makenav?pgno=1";

        fetch(nav)
          .then((response) => response.text())
          .then((nav) => {
            document.querySelector("#nav").innerHTML = nav;
          });
      };
    </script>
  </head>
  <body>
    <jsp:include page="./header.jsp"></jsp:include>

    <main>
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-lg-8 col-md-10 col-sm-12">
            <h2 class="my-3 py-3 shadow-sm bg-light text-center">
              <mark class="sky">글목록</mark>
            </h2>
          </div>

          <div class="col-lg-8 col-md-10 col-sm-12">
            <div class="row align-self-center mb-2">
              <c:if test="${!empty userinfo }">
                <div class="col-md-2 text-start">
                  <button type="button" id="btn-mv-register" class="btn btn-outline-primary btn-sm">
                    글쓰기
                  </button>
                </div>
              </c:if>
            </div>
            <table class="table table-hover">
              <thead>
                <tr class="text-center">
                  <th scope="col">글번호</th>
                  <th scope="col">제목</th>
                  <th scope="col">작성자</th>
                  <th scope="col">작성일</th>
                </tr>
              </thead>
              <tbody id="boardlist"></tbody>
            </table>
          </div>
          <table class="text-center">
            <tr>
              <td id="nav"></td>
            </tr>
          </table>
          <form id="form-param" method="get" action="${root }/board">
            <input type="hidden" id="p-pgno" name="pgno" value="" />
          </form>
        </div>
      </div>
    </main>

    <jsp:include page="./footer.jsp"></jsp:include>

    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
      crossorigin="anonymous"
    ></script>

    <script>
      let titles = document.querySelectorAll(".article-title");
      titles.forEach(function (title) {
        title.addEventListener("click", function () {
          console.log(this.getAttribute("data-no"));
          location.href = "<%=root%>/article?action=view&articleno=" + this.getAttribute("data-no");
        });
      });

      document.querySelector("#btn-mv-register")?.addEventListener("click", function () {
        location.href = "<%=root%>/write";
      });

      document.querySelector("#btn-search")?.addEventListener("click", function () {
        let form = document.querySelector("#search-form");
        form.setAttribute("action", "<%= root %>/board");
        form.submit();
      });

      let pages = document.querySelectorAll(".page-link");
      pages.forEach(function (page) {
        page.addEventListener("click", function () {
          console.log(this.parentNode.getAttribute("data-pg"));
          document.querySelector("#p-action").value = "search";
          document.querySelector("#p-pgno").value = this.parentNode.getAttribute("data-pg");
          document.querySelector("#p-key").value = "${key}";
          document.querySelector("#p-word").value = "${word}";
          document.querySelector("#form-param").submit();
        });
      });
    </script>
  </body>
</html>
