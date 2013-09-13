<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF8">
<title>Tail</title>
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/jquery.highlight-4.js"></script>
<script type="text/javascript" src="js/purl.js"></script>
<script>
	jQuery(document).ready(function() {
		/*
		 * The interval is optional - default is 30000 (30 seconds)
		 */
		var interval = jQuery.url().param('interval');
		if (null == interval) {
			interval = 30000;
		}
		setInterval(function() {
			/*
			 * The src must be provided as request parameter
			 */
			var src = jQuery.url().param('src');
			/*
			 * The type must be provided as request parameter
			 */
			var type = jQuery.url().param('type');
			if (src != null && type != null) {
				jQuery.get('tail/data/' + type + '?src=' + src, function(data) {
					jQuery('#content').append(data);
				});
			}

			location.href = "#footer";

		}, interval);
	});

	function highlight() {
		var pattern = jQuery('#pattern').val();
		if (pattern != null) {
			jQuery('pre').highlight(pattern);
		}
	}
	function removehighlight() {
		jQuery('pre').removeHighlight();
	}
</script>
<style type="text/css">
body {
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	margin-left: 0px
}

div, input, a {
    color: #000000;
    font-family: sans-serif;
    font-size: 10pt;
    text-decoration: none;
}

a, input {
    border: 1px solid black;
    padding: 2px;
}

.highlight {
	background-color: yellow;
}

div.header {
	background: #FFFFFF;
	position: fixed;
	width: 100%;
	height: 50px;
    padding-top: 5px;
	z-index: 1;
}

div.content {
	position: absolute;
    top: 10px;
}
</style>
</head>
<body>
  <div id="header" class="header">
    Pattern: <input id="pattern" type="text" /> <a href="javascript:highlight();">highlight</a> <a
      href="javascript:removehighlight();">clear</a>
    <hr />
  </div>
  <div id="contentWrapper" class="content">
    <pre id="content"></pre>
  </div>
  <div id="footer">
    <a href="#footer"></a>
  </div>
</body>
</html>