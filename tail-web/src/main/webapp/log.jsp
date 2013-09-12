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
		}, interval);
	});
	
	function highlight() {
		var pattern = jQuery('#pattern').val();
		if(pattern != null) {
	        jQuery('pre').highlight(pattern);
		}
	}
    function removehighlight() {
        jQuery('pre').removeHighlight();
    }
</script>
<style type="text/css">
.highlight {
	background-color: yellow;
}
</style>
</head>
<body>
  <div id="contentWrapper">
    Pattern: <input id="pattern" type="text"/>
    <a href="javascript:highlight();">highlight</a>
    <a href="javascript:removehighlight();">clear</a>
    <hr/>
    <pre id="content"></pre>
  </div>
</body>
</html>