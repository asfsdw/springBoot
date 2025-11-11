<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>userInput.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script>
    'use strict';
    
    let message = '${message}';
    if(message != "") alert(message);
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
  <form method="post">
	  <h2 class="text-center mb-4">회 원 가 입</h2>
	  <table class="table table-bordered">
	    <tr>
	      <th>아이디</th>
	      <td><input type="text" name="mid" class="form-control"/></td>
	    </tr>
	    <tr>
	      <th>성명</th>
	      <td><input type="text" name="name" class="form-control"/></td>
	    </tr>
	    <tr>
	      <th>나이</th>
	      <td><input type="number" name="age" value="20" class="form-control"/></td>
	    </tr>
      <tr>
        <th>성별</th>
        <td>
          <input type="radio" name="gender" id="gender1" value="남자"/>남자 &nbsp;
          <input type="radio" name="gender" id="gender2" value="여자" checked />여자
        </td>
      </tr>
	    <tr>
	      <th>주소</th>
	      <td><input type="text" name="address" class="form-control"/></td>
	    </tr>
	    <tr>
	      <td colspan="2" class="text-center">
	        <input type="submit" value="회원가입" class="btn btn-success"/> &nbsp;
	        <input type="reset" value="다시입력" class="btn btn-warning"/> &nbsp;
	        <input type="button" value="돌아가기" onclick="location.href='/user/userList'" class="btn btn-secondary"/>
	      </td>
	    </tr>
	  </table>
  </form>
</div>
<p><br/></p>
</body>
</html>