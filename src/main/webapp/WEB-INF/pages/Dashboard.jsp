
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<title>upload</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous" />
<!-- Generic page styles -->
<link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/layout.css">

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
<link rel="stylesheet"
	href="https://blueimp.github.io/Gallery/css/blueimp-gallery.min.css" />
<!-- CSS to style the file input field as button and adjust the Bootstrap progress bars -->
<link rel="stylesheet" href="css/jquery.fileupload.css" />
<link rel="stylesheet" href="css/jquery.fileupload-ui.css" />
</head>

<body style="background: #EEF5F9">

	<%-- <h3 align="center">
		<a href="downloadFile">Download this file</a>
	</h3>
	<h3>
		<a href="confirm">confirm this file</a>
	</h3>
	
	<h3>
		<a href="convertToCsv">convert this file</a>
	</h3> --%>

	<div class="page-content">
		<div style="margin-left: 30px; margin-right: 30px"
			class="page-wrapper chiller-theme toggled">
			<h2 style="width: 400px; margin: 0 auto">Màn hình upload danh
				sách</h2>

			<div class="row">

				<!-- The file upload form used as target for the file upload widget -->
				<form id="fileupload" method="POST" enctype="multipart/form-data">
					<!-- Redirect browsers with JavaScript disabled to the origin page -->
					<br /> <label for="to">Email Address</label><br /> <input
						type="text" name="to" /><br />
					<!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
					<div class="row fileupload-buttonbar">
						<div class="col-lg-7">
							<!-- The fileinput-button span is used to style the file input field as button -->
							<span class="btn btn-success fileinput-button"> <i
								class="glyphicon glyphicon-plus"></i> <span>Add files...</span>
								<input type="file" name="files" multiple />
							</span>
							<button type="submit" class="btn btn-primary start">
								<i class="glyphicon glyphicon-upload"></i> <span>Start
									upload</span>
							</button>
							<button type="reset" class="btn btn-warning cancel">
								<i class="glyphicon glyphicon-ban-circle"></i> <span>Cancel
									upload</span>
							</button>
							<button type="button" class="btn btn-danger delete">
								<i class="glyphicon glyphicon-trash"></i> <span>Delete
									selected</span>
							</button>
							<!-- 							<input type="checkbox" class="toggle" />
 -->
							<!-- The global file processing state -->
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
										<th>STT.</th>
										<th>name</th>
										<th>lưu file</th>
										<th>size</th>
										<th>full</th>
										<th>type</th>
										<th>download</th>
										<th>Full</th>
										<th>Delta</th>


									</tr>
								</thead>
								<tbody>
									<s:iterator id="fi" value="fileDataList" status="st">
										<tr id="<s:property value="#st.count"/>">
											<td><s:property value="#fi.id" /></td>
											<td><s:property value="#fi.name" /></td>
											<td><s:property value="#fi.dir" /></td>
											<td><s:property value="#fi.size" /></td>

											<td><s:property value="#fi.full" /></td>
											<td><s:property value="#fi.fileType" /></td>
											<td><a
												href="/load_ds_wl/downloadFile.action?idName=<s:property value="name"/>">Download</a>
											</td>
											<td><a id="<s:property value="name"/>"
												href="/load_ds_wl/confirm.action?idName=<s:property value="name"/>&idType='full'">Confirm
													Full</a></td>
											<td><a id="<s:property value="name"/>"
												href="/load_ds_wl/confirm.action?idName=<s:property value="name"/>&idType='delta'">Confirm
													Delta</a></td>
											<td><a id="<s:property value="name"/>"
												href="/load_ds_wl/reject.action?idName=<s:property value="name"/>">
													Reject</a></td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
							<%-- <div class="control-btn">
								<s:submit name="delete" value="Delete"
									onclick="this.form.action='fi_delete'" />
							</div> --%>
						</div>
					</div>

				</div>



			</div>

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
	</script>
	<!-- The template to display files available for upload -->
	<script id="template-upload" type="text/x-tmpl">
      {% for (var i=0, file; file=o.files[i]; i++) { %}
          <tr class="template-upload fade{%=o.options.loadImageFileTypes.test(file.type)?' image':'image'%}">
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
                          <span>Start</span>
                      </button>
                  {% } %}
                  {% if (!i) { %}
                      <button class="btn btn-warning cancel">
                          <i class="glyphicon glyphicon-ban-circle"></i>
                          <span>Cancel</span>
                      </button>
					  <input id="idCheck" type="checkbox" class="toggle" />
					 
                  {% } %}
              </td>
          </tr>
      {% } %}
    </script>
	<!-- The template to display files available for download -->
	<script id="template-download" type="text/x-tmpl">
      {% for (var i=0, file; file=o.files[i]; i++) { %}
          <tr class="template-download fade{%=file.thumbnailUrl?' image':' image'%}">
              <td>
                  <span class="preview">
                      {% if (file.thumbnailUrl) { %}
                          <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" data-gallery><img src="{%=file.thumbnailUrl%}"></a>
                      {% } %}
                  </span>
              </td>
              <td>
                 
                  {% if (file.error) { %}
                      <div><span class="label label-danger">Error</span> {%=file.error%}</div>
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
                      <input type="checkbox" name="delete" value="1" class="toggle">
                  {% } else { %}
                      <button class="btn btn-warning cancel">
                          <i class="glyphicon glyphicon-ban-circle"></i>
                          <span>Cancel</span>
                      </button>
                  {% } %}
              </td>
          </tr>
      {% } %}

    </script>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"
		integrity="sha384-nvAa0+6Qg9clwYCGGPpDQLVpLNn0fRaROjHqs13t4Ggj3Ez50XnGQqc/r8MhnRDZ"
		crossorigin="anonymous"></script>
	<!-- The jQuery UI widget factory, can be omitted if jQuery UI is already included -->
	<script src="js/vendor/jquery.ui.widget.js"></script>
	<!-- The Templates plugin is included to render the upload/download listings -->
	<script
		src="https://blueimp.github.io/JavaScript-Templates/js/tmpl.min.js"></script>
	<!-- The Load Image plugin is included for the preview images and image resizing functionality -->
	<script
		src="https://blueimp.github.io/JavaScript-Load-Image/js/load-image.all.min.js"></script>
	<!-- The Canvas to Blob plugin is included for image resizing functionality -->
	<script
		src="https://blueimp.github.io/JavaScript-Canvas-to-Blob/js/canvas-to-blob.min.js"></script>
	<!-- blueimp Gallery script -->
	<script
		src="https://blueimp.github.io/Gallery/js/jquery.blueimp-gallery.min.js"></script>
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