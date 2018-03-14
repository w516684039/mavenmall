<%@ page language="java" contentType="text/html; charset=utf-8"
     pageEncoding="utf-8"%>
 <%@ include file="../common/header.jsp" %>
 <!DOCTYPE html>
 <html lang="en">
 <head>
     <meta charset="utf-8">
     <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
     <title> - 分类列表</title>
 </head>
 <body>
    <div class="demoTable">
    <!-- <form id = "form_add" class="layui-form layui-form-pane" action="" method="post" enctype="multipart/form-data"> -->
 	  名称：
 	  <div class="layui-inline">
 	    <input class="layui-input" name="id" id="searchName" autocomplete="off">
 	  </div>
 	   <button class="layui-btn" data-type="search">搜索</button>
 	   <button class="layui-btn layui-btn-danger" data-type="deleteAll">批量删除</button>
 	   &nbsp;
 	    <div class="layui-input-inline">
 	   <select name="quiz1" id="Category" lay-filter="topCategoryFilter">
	        <option value="">请选择分类</option>
	        <option value="one">一级分类</option>
	        <option value="two">二级分类</option>
	    </select>
	     </div>
 	    <button class="layui-btn" data-type="add">添加</button>
 	  <!--  </form> -->
 	 </div>
 	 <table class="layui-hide" id="datagrid" lay-filter="datagrid"></table>
 	 <script type="text/html" id="toolbar">
   		<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
  		<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
  		<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
 	</script>
 	<script>
 		layui.use('table', function(){
 		  var table = layui.table;
 		  
 		  table.render({
 		    elem: '#datagrid', //要渲染哪个表格
 		    url:'${ctx}/category/pageList.action', //异步数据接口
 		    cellMinWidth: 50, //列宽自动分配，全局定义常规单元格的最小宽度
 		    cols: [[
              {type:'checkbox'},
 		      {field:'id', title: 'ID', sort: true},
 		      {field:'parentId', title: 'parentId', sort: true},
 		      {field:'name', title: '名称', sort: true},
 		      {field:'status', title: '状态', sort: true},
 		      {field:'sortOrder', title: '排序', sort: true},
 		      {field:'createTime', title: '创建时间',sort: true},
 		      {field:'updateTime', title: '更新时间',sort: true},
 		     {fixed: 'right', width : 170, toolbar: '#toolbar'} //这里的toolbar值是模板元素的选择器
 		    ]],
 		    page: true,
 		   id : "listReload" //参数 ID 即为基础参数id对应的值
 		  });
 		 active = {
	 	    search: function(){
	 		      //var demoReload = $('#searchName');
	 		      //执行重载
	 		      table.reload('listReload', {
	 	    	  	where: { //设定异步数据接口的额外参数，任意设
	 	    		    name : $('#searchName').val(),
	 	    		},
	 	    		page: {
	 	    		   curr: 1 //重新从第 1 页开始
	 	    		}
	 		      });
	 		    },
	 		   deleteAll : function() {
  		    	var checkStatus = table.checkStatus('listReload');
  		    	var data = checkStatus.data;
  		    	console.log(checkStatus.data) //获取选中行的数据
  		    	console.log(checkStatus.data.length) //获取选中行数量，可作为是否有选中行的条件
  		    	console.log(checkStatus.isAll) //表格是否全选
  		    	layer.confirm('确定要删除这' + data.length + '条数据吗？', function(index){
  			    	var ids = util.getSelectedIds(data);
  			    	$.ajax({
  			    		url : '${ctx}/category/deleteAll.action',
  			    		data : {'ids' : ids},
  			    		dataType : 'json',
  			    		success : function(jsonData) {
  			    			if(jsonData.code == util.SUCCESS) {
  		       					mylayer.success(jsonData.msg);
  		       					active.search();
  		       				} else {
  		       					mylayer.errorMsg(jsonData.msg);
  		       				}
  			    		}
  		    	});
  	    	});
   		    },
   		 add: function(){
   			  /* location.href = "${ctx}/category/getAddPage.action"  */
   		     if($('#Category').val()=="one"){   
   		 		 location.href = "${ctx}/category/getAddOnePage.action"
   			}else if($('#Category').val()=="two"){   
				location.href = "${ctx}/category/getAddTwoPage.action"
			}else{
				mylayer.errorMsg("请先选择");
			}  
		    },
		    
	 		  };
 		  //监听工具条
 				  table.on('tool(datagrid)', function(obj){ //注：tool是工具条事件名，datagrid是table原始容器的属性 lay-filter="对应的值"
 				    var data = obj.data; //获得当前行数据
 				    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
 				    var tr = obj.tr; //获得当前行 tr 的DOM对象
 				   
 				    if(layEvent === 'detail'){ //查看
 				    	layer.msg("查看：" + data.id);
 				    } else if(layEvent === 'del'){ //删除
 				    	layer.confirm('真的删除行么', function(index){
 				       		$.ajax({
 				       			url : '${ctx}/category/deleteById.action',
 				       			data : {'id' : data.id},
 				       			dataType : 'json',
 				       			success : function(jsonData) {
 				       				if(jsonData.code == util.SUCCESS) {
 				       					mylayer.success(jsonData.msg);
 				       					active.search();
 				       				} else {
 				       					mylayer.errorMsg(jsonData.msg);
 				       				}
 				       				layer.close(index);
 				       			}
 				       		});
 				      	});
 				    } else if(layEvent === 'edit'){ //编辑
 				    	layer.msg("查看：" + data.id);
 				        layer.open({
 				        	type : 2,
 				        	title : '商品编辑',
 				        	area :['1000px','650px'],
 				        	offset : '10px',//只定义top坐标，水平保持居中
 				        	content : '${ctx}/category/getEditPage.action?id=' + data.id
 				        });
 				    }
 				  });
 		$('.demoTable .layui-btn').on('click', function(){
 			 	  var type = $(this).data('type');
 			 	  active[type] ? active[type].call(this) : '';
 			  		  }); 
 		});
 	</script>
 </body>
 </html>