<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>Galerija</title>
<link rel="stylesheet" type="text/css" href="/galerija/css/mystyle.css">
</head>
<body>
	<h1>Tagovi u galeriji:</h1>
	<div>
		
		<div class="tagButtons" id="tagButtons">&nbsp;</div>
		<div id="tagThumbnails">&nbsp;</div>
		<div id="picture">&nbsp;</div>
		<div id="descriptionmessage">&nbsp;</div>
		<div id="description">&nbsp;</div>
		<div id="tagsmessage">&nbsp;</div>
		<div id="tags">&nbsp;</div>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>
	<script type="text/javascript">
		function clearImage() {
			$("#picture").html("&nbsp;");
			$("#description").html("&nbsp;");
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
								content += "<span class='tagbutton'><input type=\"button\" id=" + value
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
					content += "<img id='" + value + "' src='showthumbnail?name="
							+ value + "' onclick='retrieveFull(this.id)'/>"
				});
				$("#tagThumbnails").html(content);
			}, "json")
		}

		function retrieveFull(id) {
			$("#picture").html("<img src='showpicture?name=" + id + "'>");
			console.log("nesto");
			$.get("pictureinfo?name=" + id, function(data) {
				var content = data;
				var tagstext = "";
				$("#descriptionmessage").html("Picture description:");
				$("#description").html(content[0]);
				$.each(content[1], function(key, value) {
					tagstext += "<span id='picturetag'>" + value + "</span>"
				});
				$("#tagsmessage").html("Tags associated with the message:");
				$("#tags").html(tagstext);
			}, "json");
		}
	</script>

</body>
</html>