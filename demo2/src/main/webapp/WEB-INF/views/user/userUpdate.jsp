<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>userUpdate.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <style>
    th, td {
      padding: 10px !important;
    }
    th {
      text-align: center;
      background-color: #eee !important;
    }
  </style>
</head>
<body>
<p><br/></p>
<div class="container">
  <form method="post">
    <h2 class="text-center mb-4">회 원 정 보 수 정</h2>
    <table class="table table-bordered">
      <tr>
        <th>아이디</th><td>${dto.mid}</td>
      </tr>
      <tr>
        <th>성명</th><td><input type="text" name="name" id="name" value="${dto.name}" class="form-control" /></td>
      </tr>
      <tr>
        <th>나이</th><td><input type="number" name="age" id="age" value="${dto.age}" class="form-control" /></td>
      </tr>
      <tr>
        <th>성별</th>
        <td>
          <input type="radio" name="gender" id="gender1" value="남자" ${dto.gender == '남자' ? 'checked' : '' } />남자 &nbsp;
          <input type="radio" name="gender" id="gender2" value="여자" ${dto.gender == '여자' ? 'checked' : '' } />여자
        </td>
      </tr>
      <tr>
        <th>주소</th><td><input type="text" name="address" id="address" value="${dto.address}" class="form-control" /></td>
      </tr>
      <tr>
        <td colspan="2" class="text-center">
          <input type="submit" value="회원정보수정" class="btn btn-success mr-3" />
          <input type="reset" value="다시입력" class="btn btn-warning mr-3" />
          <input type="button" value="돌아가기" onclick="location.href='/user/userList';" class="btn btn-info" />
        </td>
      </tr>
    </table>
    <input type="hidden" name="idx" value="${dto.idx}"/>
  </form>
</div>
</body>
</html>