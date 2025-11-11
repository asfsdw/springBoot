<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% pageContext.setAttribute("ctp", request.getContextPath()); %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/WEB-INF/views/include/bs5.jsp" />
	<title>userList</title>
	<script>
    'use strict';
    
    let message = '${message}';
    if(message != "") alert(message);
    
    function userDeleteCheck(idx) {
    	let ans = confirm("정말로 회원을 삭제하시겠습니까?");
    	if(ans) {
    		$.ajax({
    			url : "/user/userDelete",
    			type: "post",
    			data: {"idx" : idx},
    			success : (res) => {
    				if(res != 0) {
    					alert("회원이 삭제되었습니다.");
    					location.reload();
    				}
    				else alert("잠시 후, 다시 시도해주세요.");
    			},
    			error : () => alert("전송오류")
    		});
    	}
    }
  </script>
  <style>
    th {
      background-color: #eee !important;
      text-align: center;
    }
  </style>
</head>
<body>
	<p><br/></p>
	<div class="container">
		<h2>전체 회원 리스트</h2>
		<p></p>
		<div class="row">
			<div class="col">
				<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#myModalSearch">개별조회</button>
			</div>
			<div class="col"></div>
			<div class="col">
				<input type="button" value="회원가입" onclick="location.href='/user/userInput'" class="btn btn-success" />
				<input type="button" value="돌아가기" onclick="location.href='/'" class="btn btn-warning" />
			</div>
		</div>
		<hr/>
		<div>
			<c:if test="${flagSw == 'search'}">
				<c:if test="${empty dto}">
					<h4 class="text-center">검색된 회원이 없습니다.</h4>
				</c:if>
				<c:if test="${!empty dto}">
					<hr/>
					<h4 class="text-center">검색된 회원</h4>
					<table class="table table-bordered text-center">
						<tr>
							<th class="searchTh">아이디</th><td>${dto.mid}</td>
							</tr>
						<tr>
							<th class="searchTh">성명</th><td>${dto.name}</td>
							</tr>
						<tr>
							<th class="searchTh">나이</th><td>${dto.age}</td>
						</tr>
						<tr>
							<th>성별</th><td>${dto.gender}</td>
						</tr>
						<tr>
							<th class="searchTh">주소</th><td>${dto.address}</td>
						</tr>
					</table>
				</c:if>
			</c:if>
			<table class="table table-hover text-center">
		    <tr class="table-secondary">
		      <th>번호</th>
		      <th>아이디</th>
		      <th>성명</th>
		      <th>나이</th>
		      <th>성별</th>
		      <th>주소</th>
		      <th>비고</th>
		    </tr>
		    <c:forEach var="vo" items="${vos}">
		      <tr>
		        <td>${vo.idx}</td>
		        <td>${vo.mid}</td>
		        <td>${vo.name}</td>
		        <td>${vo.age}</td>
		        <td>${vo.gender}</td>
		        <td>${vo.address}</td>
		        <td>
		          <a href="javascript:userDeleteCheck(${vo.idx})" class="btn btn-sm btn-danger">삭제</a>
		          <a href="userUpdate?mid=${vo.mid}" class="btn btn-sm btn-warning">수정</a>
		        </td>
		      </tr>
		    </c:forEach>
		  </table>
		  <br/>
		  <div>
		    <a href="/" class="btn btn-success">돌아가기</a>
		  </div>
		</div>
	</div>
	<div class="modal fade" id="myModalSearch">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-titleSearch">개별조회</h4>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<!-- Modal body -->
				<div class="modal-body">
					<form method="post">
						개별 조회할 아이디를 입력하세요.<br/>
						<input type="text" name="mid" value="hkd1234" class="form-control mb-2" />
						<input type="submit" value="아이디개별검색" class="btn btn-success form-control" />
						<input type="hidden" name="flagSw" value="search"/>
					</form>
				</div>
				<!-- Modal footer -->
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>