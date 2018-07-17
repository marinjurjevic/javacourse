<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>Photo gallery</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<style type="text/css">
	img {
    	max-width:800px;
	}
	#picture {
		margin-top:10px;
	}
	body {
		background-color: #e5eaf7;
	}
</style>

</head>
<body>

	<div class="container-fluid text-center">
	<h2 class="text-center">
		Following photo tags are present in gallery
		</h2>
		<div class="tagButtons text-center" id="tagButtons">&nbsp;</div>
		<div class="text-center" id="tagThumbnails">&nbsp;</div>
		<div class="col-lg-7" id="picture">&nbsp;</div>
		<div class="col-lg-5" id="descriptionmessage">&nbsp;</div>
		<div class="col-lg-5" id="description">&nbsp;</div>
		<div class="col-lg-5" id="tagsmessage">&nbsp;</div>
		<div class="col-lg-5" id="tags">&nbsp;</div>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>
	<script type="text/javascript">
		function clearImage() {
			$("#picture").html("&nbsp;");
			$("#descriptionmessage").html("&nbsp;");
			$("#description").html("&nbsp;");
			$("#tagsmessage").html("&nbsp;");
			$("#tags").html("&nbsp;");
		}

		$(document).ready(
				function() {
					$.get({
						dataType : "json",
						url : "tags",
						success : function(data) {
							var content = "";
							$.each(data, function(key, value) {
								content += "<span class='tagbutton'><input class='btn btn-default' type=\"button\" id=" + value
										+ " value=" + value
										+ " onclick=\"retrieve(this.id)\" /></span>";
							});
							$("#tagButtons").html(content);
						}
					});
				});

		function retrieve(id) {
			clearImage();
			$.get("thumbnails?tag=" + id, function(data) {
				var content = ""
				$.each(data, function(key, value) {
					content += "<img class='img-thumbnail' id='" + value + "' src='showthumbnail?name="
							+ value + "' onclick='retrieveFull(this.id)'/>"
				});
				$("#tagThumbnails").html(content);
			}, "json")
		}

		function retrieveFull(id) {
			$("#picture").html("<img class='img-rounded' src='showpicture?name=" + id + "'>");
			console.log("nesto");
			$.get("pictureinfo?name=" + id, function(data) {
				var content = data;
				var tagstext = "";
				$("#descriptionmessage").html("<h3>Picture description</h3>");
				$("#description").html(content[0]);
				$.each(content[1], function(key, value) {
					tagstext += "<span class='label label-info' id='picturetag'>" + value + "</span>"
				});
				$("#tagsmessage").html("<h3>Tags associated with the picture</h3>");
				$("#tags").html(tagstext);
			}, "json");
		}
	</script>

</body>
</html>