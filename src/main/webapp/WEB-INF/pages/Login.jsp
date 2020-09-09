<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>Struts 2 - Login Application</title>
<link rel='stylesheet prefetch'
	href='css/bootstrap.min.css'>

<link rel="stylesheet" href="css/style.css">

</head>

<body>
	<s:actionerror />
	<div class="wrapper">
		<form class="form-signin" action="loginAction" method="post">
			<h2 class="form-signin-heading" style="text-align: center;">Đăng
				nhập</h2>
			<div class="imgcontainer">
				<img src="image/img_avatar2.png" alt="Avatar" class="avatar"
					width="60px" height="60px"
					style="margin-left: auto; margin-right: auto; display: block;">
			</div>


			<%-- <div style="padding: 10px; text-align: center;">
				<select name="role" class="form-control" placeholder="Role"
					required="" autofocus="">
					<option value="maker">maker</option>
					<option value="checker">checker</option>

				</select>

			</div>
 --%>

			<div style="padding: 10px; text-align: center;">
				<input type="text" class="form-control" name="userName"
					placeholder="Tên đăng nhập" required="" autofocus="" />
			</div>
			<div style="padding: 10px; text-align: center;">
				<input type="password" class="form-control" name="password"
					placeholder="Mật khẩu" required="" />
			</div>

			<div style="padding: 10px; text-align: center;">
				<button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
			</div>
			<div style="color: red;">
				<s:property value="message" />
			</div>
		</form>
	</div>



</body>
</html>