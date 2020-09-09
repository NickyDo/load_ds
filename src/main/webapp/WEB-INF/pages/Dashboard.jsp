
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<html>
<head>
<title>upload</title>

<!-- Generic page styles -->

<link rel="stylesheet" type="text/css" href="css/layout.css">
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">

<link rel="stylesheet" type="text/css" href="css/all.css">

<style>
#navigation {
	margin: 10px 0;
}

@media ( max-width : 767px) {
	#title, #description {
		display: none;
	}
}
</style>
<link rel="stylesheet" href="css/blueimp-gallery.min.css" />
<!-- CSS to style the file input field as button and adjust the Bootstrap progress bars -->
<link rel="stylesheet" href="css/jquery.fileupload.css" />
<link rel="stylesheet" href="css/jquery.fileupload-ui.css" />
</head>

<body style="background: #EEF5F9, font-size: 0.9rem">
	<div class="page-content">

		<nav class="navbar navbar-inverse navbar-static-top">
			<div class="container-fluid">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header"">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
						aria-expanded="false">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">VIB</a>
				</div>

				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">

					<ul class="nav navbar-nav navbar-right">
						<li><a href="#">user: <s:property
									value="#session.userName" />
						</a></li>
						<li><a href="/load_ds_wl/logout.action">Logout</a></li>


					</ul>
				</div>
				<!-- /.navbar-collapse -->
			</div>
			<!-- /.container-fluid -->
		</nav>

		<%-- 	<div class="user-info">
			<span class="user-name"> <strong>User: <s:property
						value="#session.userName" /></strong> <i class="fa fa-circle"></i> <span>Online</span>
			</span> <span class="user-role">Role: <s:property
					value="#session.role" /></span>
		</div> --%>

		<div style="margin-left: 30px; margin-right: 30px"
			class="page-wrapper chiller-theme toggled">
			<s:if test="%{#session.role == 4}">

				<h2 style="width: 550px; margin: 0 auto">MÀN HÌNH PHÊ DUYỆT
					DANH SÁCH</h2>
				<form action="getDataChecker" method="post">
					<div class='row' style="margin-top: 10px">
						<input type="submit" class="btn btn-primary" value="Tìm kiếm"
							style="margin-top: 5px; margin-left: 15px" /> <span
							style="color: red; padding-left: 50px"><s:property
								value="msgError" /></span>
					</div>
				</form>

				<div class="row">
					<div class="col-lg-12">
						<div class="panel panel-default">
							<!-- /.panel-heading -->
							<div class="panel-body">
								<table id="dataTables-example"
									class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<!-- 											<th>STT.</th>
 -->
											<th>Date</th>
											<th>Watchlist</th>
											<th>Upload Type</th>
											<th>User Upload</th>
											<th>User Approve</th>
											<th>comment</th>
											<th>Status</th>
											<th>Download</th>
											<th>Confirm</th>
											<th>Reject</th>


										</tr>
									</thead>
									<tbody>
										<s:iterator id="fi" value="fileDataListChecker" status="st">
											<tr id="<s:property value="#st.count"/>">
												<%-- 												<td><s:property value="#fi.id" /></td>
 --%>
												<td><s:property value="#fi.dateUpload" /></td>
												<td><s:property value="#fi.name" /></td>
												<td><s:property value="#fi.full" /></td>
												<td><s:property value="#fi.userUpload" /></td>
												<td><s:property value="#fi.userApprove" /></td>
												<s:if test="#fi.status == 'pending'">
													<td><input class="form-control"
														id="<s:property value="name"/>" name="comment" type="text" /></td>
												</s:if>
												<s:else>
													<td><input class="form-control"
														id="<s:property value="name"/>" name="comment" type="text"
														disabled /></td>
												</s:else>

												<td><s:property value="#fi.status" /></td>
												<s:if test="#fi.status == 'pending'">
													<td><a
														href="/load_ds_wl/downloadFile.action?idName=<s:property value="name"/>">Download</a>
													</td>
												</s:if>
												<s:else>
													<td></td>
												</s:else>
												<s:if test="#fi.status == 'pending'">

													<td>
														<button class="btn btn-success"
															onClick="submitDs('<s:property value="name"/>', '<s:property value="full"/>', '<s:property value="userUpload"/>', '<s:property value="status"/>')">Confirm
														</button>
													</td>
												</s:if>
												<s:else>
													<td>
														<button class="btn btn-success" disabled
															onClick="submitDs('<s:property value="name"/>', '<s:property value="full"/>', '<s:property value="userUpload"/>', '<s:property value="status"/>')">Confirm
														</button>
													</td>
												</s:else>
												<%-- <td>
													<button class="btn btn-success"
														onClick="submitDs('<s:property value="name"/>', 'delta', '<s:property value="userUpload"/>', '<s:property value="status"/>')">Confirm
														Delta</button>
												</td> --%>
												<s:if test="#fi.status == 'pending'">

													<td>
														<button
															onClick="submitDelete('<s:property value="name"/>', '<s:property value="id"/>', '<s:property value="userUpload"/>', '<s:property value="status"/>')"
															type="button" class="btn btn-danger">Reject</button>

													</td>
												</s:if>
												<s:else>
													<td>
														<button disabled
															onClick="submitDelete('<s:property value="name"/>', '<s:property value="id"/>', '<s:property value="userUpload"/>', '<s:property value="status"/>')"
															type="button" class="btn btn-danger">Reject</button>

													</td>
												</s:else>
											</tr>
										</s:iterator>
									</tbody>
								</table>

							</div>
						</div>

					</div>



				</div>
			</s:if>

			<s:if test="%{#session.role == 2}">

				<h2 style="width: 500px; margin: 0 auto">MÀN HÌNH UPLOAD DANH
					SÁCH</h2>
				<div class="row">

					<!-- The file upload form used as target for the file upload widget -->
					<form id="fileupload" method="POST" enctype="multipart/form-data">
						<!-- <br /> <label for="to">Email Address</label><br /> <input
						class="form-control" style="width: 20%" type="email" name="to"
						required /><br /> -->

						<div class="col-lg-3" style="margin-top: 5px">
							<div class="input-group" style="width: 100%; margin-top: 5px">
								<span class="input-group-addon">Checker</span> <select name="to"
									class="form-control" id="typeId">
									<option value=""></option>
									<s:iterator value="userData">
										<option value='<s:property value="userName"/>'><s:property
												value="userName" /></option>
									</s:iterator>
								</select>
							</div>
						</div>
						<!-- Redirect browsers with JavaScript disabled to the origin page -->
						<div class="col-lg-3 row  fileupload-buttonbar"
							style="margin-top: 5px">
							<div class="col-lg-7">
								<!-- The fileinput-button span is used to style the file input field as button -->
								<span class="btn btn-success fileinput-button"
									style="margin-top: 5px"> <i
									class="glyphicon glyphicon-plus"></i> <span>Add files...</span>
									<input type="file" name="files" />
								</span>
								<button onClick="sendFileToChecker()" style="margin-top: 5px"
									class="btn btn-primary start sendFileToChecker" disabled>
									<i class="glyphicon glyphicon-upload"></i> <span> Send
										to checker</span>
								</button>


								<label class="radio-inline"> <input name="checktype"
									type="radio" value="Delta" checked>Delta
								</label> <label class="radio-inline"> <input name="checktype"
									type="radio" value="Full">Full
								</label>

								<%-- <button type="button" class="btn btn-danger delete">
									<i class="glyphicon glyphicon-trash"></i> <span>Delete
										selected</span>
								</button> --%>
								<span class="fileupload-process"></span>
							</div>
							<!-- The global progress state -->
							<div class="col-lg-5 fileupload-progress fade">
								<!-- The global progress bar -->
								<div class="progress progress-striped active" role="progressbar"
									aria-valuemin="0" aria-valuemax="100">
									<div class="progress-bar progress-bar-success"
										style="width: 0%;"></div>
								</div>
								<!-- The extended global progress state -->
								<div class="progress-extended">&nbsp;</div>
							</div>
						</div>
						<!-- The table listing the files available for upload/download -->
						<table role="presentation" class="table table-striped">
							<tbody class="files"></tbody>
						</table>
					</form>

				</div>
				<h5>LỊCH SỬ UPLOAD</h5>
				<form action="getDataAction" method="post">
					<div class='row' style="margin-top: 10px">
						<input type="submit" class="btn btn-primary" value="Tìm kiếm"
							style="margin-top: 5px; margin-left: 15px" /> <span
							style="color: red; padding-left: 50px"><s:property
								value="msgError" /></span>
					</div>
				</form>
				<div class="row">
					<div class="col-lg-12">
						<div class="panel panel-default">
							<!-- /.panel-heading -->
							<div class="panel-body">
								<table id="dataTables-example"
									class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<!-- 											<th>STT.</th>
 -->
											<th>Date</th>
											<th>Watchlist</th>
											<th>Upload Type</th>
											<th>User Upload</th>
											<th>User Approve</th>
											<th>comment</th>
											<th>Status Upload</th>
											<th>File Error</th>

										</tr>
									</thead>
									<tbody>
										<s:iterator id="fi" value="fileDataList" status="st">
											<tr id="<s:property value="#st.count"/>">
												<%-- 												<td><s:property value="#fi.id" /></td>
 --%>
												<td><s:property value="#fi.dateUpload" /></td>
												<td><s:property value="#fi.name" /></td>
												<td><s:property value="#fi.full" /></td>
												<td><s:property value="#fi.userUpload" /></td>
												<td><s:property value="#fi.userApprove" /></td>
												<td><s:property value="#fi.commentFile" /></td>
												<td><s:property value="#fi.status" /></td>
												<s:if test="#fi.status == 'failed'">
													<td><a
														href="/load_ds_wl/downloadErrorFile.action?idName=<s:property value="name"/>">Download</a>
													</td>
												</s:if>
												<s:else>
													<td></td>
												</s:else>
											</tr>
										</s:iterator>
									</tbody>
								</table>

							</div>
						</div>

					</div>



				</div>
			</s:if>

		</div>
	</div>
	<script>
		function chgFlag(isChecked, name) {
			console.log('isChecked: ' + isChecked);
			$.post($('#' + name).attr('href'), {
				valueToChange : isChecked
			}).done(function(data) {
				alert("Data Loaded: " + data);
			});

		}
	</script>
	<script>
		function submitForm(name) {
			console.log("success", name)

			$.ajax({
				type : "POST",
				url : "/load_ds_wl/downloadFile.action",
				data : "{'name':'" + name + "'}",
				contentType : "application/json",
				async : false,
				success : function(data) {
					console.log("success")
				}
			});
		}

		function submitDelete(idName, id, fileType, status) {
			console.log("success", idName)
			var inputData = document.getElementById(idName).value
			console.log("element", document.getElementById(idName).value);
			if (status == 'pending') {
				if (confirm('Are you sure you want to delete the record?')) {
					  // Save it!
					  console.log('Thing was saved to the database.');
					  $.ajax({
							type : "POST",
							url : "/load_ds_wl/reject.action?idName=" + idName + "&id="
									+ id + "&fileType=" + fileType + "&inputData="
									+ inputData,
							contentType : "application/json",
							async : false,
							success : function(data) {
								alert("delete success")
								location.reload();
							}
						});
					} else {
					  // Do nothing!
					  console.log('Thing was not saved to the database.');
					}
				
			}

		}

		function submitDs(idName, idType, fileType, status) {
			console.log("success", idName, idType, fileType, status)
			var inputData = document.getElementById(idName).value
			console.log("element", document.getElementById(idName).value);
			if (status == 'pending') {
				if (confirm('Are you sure you want to confirm Data?')) {
					// Save it!
					console.log('Thing was saved to the database.');
					$.ajax({
						type : "POST",
						url : "/load_ds_wl/confirm.action?idName=" + idName
								+ "&idType=" + idType + "&fileType=" + fileType
								+ "&inputData=" + inputData,
						contentType : "application/json",
						async : false,
						success : function(data) {
							alert("confirm success")
							location.reload();
						}
					});
				} else {
					// Do nothing!
					console.log('Thing was not saved to the database.');
				}

			}
		}

		function sendFileToChecker() {
			var localStorage = window.localStorage;
			var fileName = localStorage.getItem("sendName");
			console.log(fileName)

			if (fileName) {

				$
						.ajax({
							type : "POST",
							url : "/load_ds_wl/sendToChecker.action?idName="
									+ fileName,
							contentType : "application/json",
							async : false,
							success : function(data) {
								alert("Send file success")
								localStorage.clear();
								location.reload();
							}
						});
			} else {
				alert("select file upload first!")

			}

		}
	</script>
	<!-- The template to display files available for upload -->
	<script id="template-upload" type="text/x-tmpl">
      {% for (var i=0, file; file=o.files[i]; i++) { %}
          <tr class="template-upload fade{%=o.options.loadImageFileTypes.test(file.type)?' image':''%}">
              <td>
                  <span class="preview"></span>
              </td>
              <td>
                  <p class="name">{%=file.name%}</p>
                  <strong class="error text-danger"></strong>
              </td>
              <td>
                  <p class="size">Processing...</p>
                  <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>
              </td>
              <td>
                  {% if (!o.options.autoUpload && o.options.edit && o.options.loadImageFileTypes.test(file.type)) { %}
                    <button class="btn btn-success edit" data-index="{%=i%}" disabled>
                        <i class="glyphicon glyphicon-edit"></i>
                        <span>Edit</span>
                    </button>

                  {% } %}
                  {% if (!i && !o.options.autoUpload) { %}
                      <button class="btn btn-primary start" disabled>
                          <i class="glyphicon glyphicon-upload"></i>
                          <span>Upload</span>
                      </button>
                  {% } %}
                  {% if (!i) { %}
                      <button class="btn btn-warning cancel">
                          <i class="glyphicon glyphicon-ban-circle"></i>
                          <span>Cancel</span>
                      </button>
					 
                  {% } %}
              </td>
          </tr>
      {% } %}
    </script>
	<!-- The template to display files available for download -->
	<script id="template-download" type="text/x-tmpl">
      {% for (var i=0, file; file=o.files[i]; i++) { %}
          <tr class="template-download fade{%=file.thumbnailUrl?' image':''%}">
              <td>
                  <span class="preview">
                      {% if (file.thumbnailUrl) { %}
                          <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" data-gallery><img src="{%=file.thumbnailUrl%}"></a>
                      {% } %}
                  </span>
              </td>
              <td>
                 
                  {% if (file.error) { %}
                      <div><span class="label label-danger">Error upload!</span> </div>
                  {% } %}
              </td>
              <td>
                  <span class="size">{%=o.formatFileSize(file.size)%}</span>
              </td>
              <td>
                  {% if (file.deleteUrl) { %}
                      <button class="btn btn-danger delete" data-type="{%=file.deleteType%}" data-url="{%=file.deleteUrl%}"{% if (file.deleteWithCredentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
                          <i class="glyphicon glyphicon-trash"></i>
                          <span>Delete</span>
                      </button>
                  {% } else { %}
                      <button  class="btn btn-warning cancel">
                          <i class="glyphicon glyphicon-ban-circle"></i>
                          <span>Done</span>
                      </button>
 					
 					
                  {% } %}
              </td>
          </tr>
      {% } %}


    </script>

	<script src="js/jquery.min.js"
		integrity="sha384-nvAa0+6Qg9clwYCGGPpDQLVpLNn0fRaROjHqs13t4Ggj3Ez50XnGQqc/r8MhnRDZ"
		crossorigin="anonymous"></script>
	<!-- The jQuery UI widget factory, can be omitted if jQuery UI is already included -->
	<script src="js/vendor/jquery.ui.widget.js"></script>
	<!-- The Templates plugin is included to render the upload/download listings -->
	<script src="js/tmpl.min.js"></script>
	<!-- The Load Image plugin is included for the preview images and image resizing functionality -->
	<script src="js/load-image.all.min.js"></script>
	<!-- The Canvas to Blob plugin is included for image resizing functionality -->
	<script src="js/canvas-to-blob.min.js"></script>
	<!-- blueimp Gallery script -->
	<script src="js/jquery.blueimp-gallery.min.js"></script>
	<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
	<script src="js/jquery.iframe-transport.js"></script>
	<!-- The basic File Upload plugin -->
	<script src="js/jquery.fileupload.js"></script>
	<!-- The File Upload processing plugin -->
	<script src="js/jquery.fileupload-process.js"></script>
	<!-- The File Upload image preview & resize plugin -->
	<script src="js/jquery.fileupload-image.js"></script>
	<!-- The File Upload audio preview plugin -->
	<script src="js/jquery.fileupload-audio.js"></script>
	<!-- The File Upload video preview plugin -->
	<script src="js/jquery.fileupload-video.js"></script>
	<!-- The File Upload validation plugin -->
	<script src="js/jquery.fileupload-validate.js"></script>
	<!-- The File Upload user interface plugin -->
	<script src="js/jquery.fileupload-ui.js"></script>
	<!-- The main application script -->
	<script src="js/demo.js"></script>
	<!-- The XDomainRequest Transport is included for cross-domain file deletion for IE 8 and IE 9 -->
	<!--[if (gte IE 8)&(lt IE 10)]>
      <script src="js/cors/jquery.xdr-transport.js"></script>
    <!<!-- [endif]-->
</body>
</html>