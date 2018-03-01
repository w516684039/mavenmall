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
<title>- 商品添加</title>
</head>
<body>
	<div class="main_div">
		<form id="form_add" class="layui-form layui-form-pane" action="" method="post" enctype="multipart/from-data">
			<div class="layui-form-item">
				<label class="layui-form-label">用户名</label>
				<div class="layui-input-block">
					<input type="text" name="name" autocomplete="off"
						placeholder="请输入用户名" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">密码</label>
				<div class="layui-input-block">
					<input type="text" name="subtitle" lay-verify="required"
						placeholder="请输入密码" autocomplete="off" class="layui-input">
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">邮箱</label>
				<div class="layui-input-block">
					<input type="text" name="price" autocomplete="off"
						placeholder="请输入邮箱" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">电话</label>
				<div class="layui-input-block">
					<input type="text" name="stock" autocomplete="off"
						placeholder="请输入电话" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item" pane="">
				<label class="layui-form-label">角色</label>
				<div class="layui-input-block">
					<input type="radio" name="status" value="1" title="0" checked="">
					<input type="radio" name="status" value="2" title="1">
				</div>
			</div>
			
			
			
			<div class="layui-form-item">
				<button type="button" class="layui-btn" onclick="submitForm()">添加</button>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="${ctx}/static/lib/jquery/jquery.form.js"></script>
	<script type="text/javascript" src="${ctx}/static/lib/kindeditor/kindeditor-all.js"></script>
	<script>
		layui.use([ 'form' ], function() {
			var form = layui.form;
			
			form.on('select(topCategoryFilter)', function(data) {
				console.log(data.elem); //得到select原始DOM对象
				console.log(data.value); //得到被选中的值
				console.log(data.othis); //得到美化后的DOM对象
				$.ajax({
					url : '${ctx}/manager/category/selectSecondCategory.action',
					data : 'topCategoryId=' + data.value,
					dataType : 'json',
					type : 'POST',
					success : function(jsonObj) {
						if (jsonObj.code == util.SUCCESS) {
							var html = '<option value="">请选二级分类</option>';
							var data = jsonObj.data;
							for (var i = 0; i < data.length; i++) {
								html += '<option value="'+data[i].id+'">'
										+ data[i].name + '</option>';
							}
							$('#secondCategory').html(html);
							form.render('select'); //刷新select选择框渲染,不然不显示
						} else {
							mylayer.errorMsg(jsonObj.msg);
						}
					}
				});
			});
		});
		$(function() {
			//加载一级分类
			$.ajax({
				url : '${ctx}/manager/category/selectTopCategory.action',
				type : "POST",
				dataType : "json",
				success : function(jsonObj) {
					console.log(jsonObj);
					if (jsonObj.code == util.SUCCESS) {
						var html = '<option value="">请选一级分类</option>';
						var data = jsonObj.data;
						for (var i = 0; i < data.length; i++) {
							html += '<option value="'+data[i].id+'">'
									+ data[i].name + '</option>';
							;
						}
						$('#topCategory').html(html);
					} else {
					}
				}
			});
		});
		//图片上传
		function uploadPic() {
			if($('#inputFile').val()==$('#inputFile').defaultValue
					||$('#inputFile').val()==""){
				alert("取消选择");
				return;
			}
			
			$('#form_add').ajaxSubmit({
				url : '${ctx}/manager/upload/uploadPic.action',
				type : 'POST',
				dataType : 'json',
				success : function(jsonObj) {
					console.log(jsonObj);
					$('#imgId').attr('src', jsonObj.url);
					$('#mainImage').val(jsonObj.fileName);
				}
			});
		}
		
		function submitForm(){
			$.ajax({
				url : '${ctx}/manager/product/add.action',
				data : $('#form_add').serialize(),
				type : 'POST',
				dataType : 'json',
				success : function(jsonObj) {
					if(jsonObj.code == util.SUCCESS) {
						//mylayer.success(jsonObj.msg);
						mylayer.confirm("添加成功，是够跳转到商品列表界面？", "${ctx}/manager/product/getProductPage.action");
					} else {
						mylayer.errorMsg(jsonObj.msg);
					}
				}
			});
		}
		
		
		var myKindEditor ;
        KindEditor.ready(function(K) {
            var kingEditorParams = {
                 //指定上传文件参数名称
                 filePostName  : "pictureFile",
                 //指定上传文件请求的url。
                 uploadJson : '${ctx}/manager/upload/multiPicUpload.action',
                 //上传类型，分别为image、flash、media、file
                 dir : "image",
                 afterBlur: function () { this.sync(); }
           }
            var editor = K.editor(kingEditorParams);
            //多图片上传
            K('#multiPicUpload').click(function() {
                editor.loadPlugin('multiimage', function() {
                    editor.plugin.multiImageDialog({
                         clickFn : function(urlList) {
                             console.log(urlList);
                             var div = K('#subImagesDiv');
                             var imgArray = [];
                             div.html('');
                             K.each(urlList, function(i, data) {
                                 imgArray.push(data.fileName);
                                 div.append('<img src="' + data.url + '" width="80" height="50">');
                             });
                             $("#subImages").val(imgArray.join(",")); 
                             editor.hideDialog();
                         }
                    });
                });
            });
            
          //富文本编辑器
          myKindEditor = KindEditor.create('#form_add[name=detail]', kingEditorParams);
        });
	</script>
</body>
</html>