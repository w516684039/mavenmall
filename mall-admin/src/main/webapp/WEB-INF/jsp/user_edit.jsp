<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../common/header.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<style type="text/css">
.main_div {
	margin: 15px;
}
</style>
<title>- 用户添加</title>
</head>
<body>
	<div class="main_div">
		<form id="form_add" class="layui-form layui-form-pane" action=""
			method="post" enctype="multipart/form-data">
			<input type="hidden" name="id" value="${user.id }"/>
			<div class="layui-form-item">
				<label class="layui-form-label">用户名</label>
				<div class="layui-input-block">
					<input type="text" name="username" autocomplete="off" value="${user.username }"
						placeholder="请输入用户名" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">密码</label>
				<div class="layui-input-block">
					<input type="text" name="password" autocomplete="off" value="${user.password }"
						placeholder="请输入密码" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">邮箱</label>
				<div class="layui-input-block">
					<input type="text" name="email" autocomplete="off" value="${user.email }"
						placeholder="请输入邮箱" class="layui-input">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">电话</label>
				<div class="layui-input-block">
					<input type="text" name="phone" autocomplete="off" value="${user.phone }"
						placeholder="请输入电话" class="layui-input">
				</div>
			</div>

			<div class="layui-form-item" pane="">
				<label class="layui-form-label">问题</label>
				<div class="layui-input-block">
					<input type="text" name="question" autocomplete="off" value="${user.question }"
						placeholder="请输入问题" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item" pane="">
				<label class="layui-form-label">答案</label>
				<div class="layui-input-block">
					<input type="text" name="answer" autocomplete="off" value="${user.answer }"
						placeholder="请输入答案" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item" pane="">
				<label class="layui-form-label">用户类型</label>
				<div class="layui-input-block">
					<input type="text" name="role" autocomplete="off" value="${user.role }"
						placeholder="请输入角色" class="layui-input"> 
				</div>
			</div>

			<div class="layui-form-item">
				<button type="button" class="layui-btn" onclick="submitForm()">修改</button>
			</div>
		</form>
	</div>
	<script>
//Demo
   layui.use('form', function(){
  var form = layui.form;
});
function submitForm(){
	$.ajax({
		url : '${ctx}/user/update.action',
		data : $('#form_add').serialize(),
		type : 'POST',
		dataType : 'json',
		success : function(jsonObj){
			if(jsonObj.code == util.SUCCESS){
				/* mylayer.success(jsonObj.msg); */
				/* mylayer.confirm("添加成功，是够跳转到商品列表界面？", "${ctx}/manager/product/getProductPage.action"); */
				mylayer.success(jsonObj.msg);
				//当你在iframe页面关闭自身时
				var index = parent.layer.getFrameIndex(window.name);//先得到当前iframe层的索引
				setTimeout(function(){
					parent.layer.close(index);//在执行关闭
					window.parent.location.reload();//刷新父页面
				},1500);
			}else{
				mylayer.errorMsg(jsonObj.msg);
			}
		}
	});
}
</script>
</body>
</html>