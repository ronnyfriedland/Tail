<%@ page language="java" contentType="text/html; charset=UTF8" pageEncoding="UTF8" import="de.ronnyfriedland.tail.web.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF8">
<title>Tail</title>
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/purl.js"></script>
<script>
	jQuery(document).ready(
			function() {
				var interval;
				setInterval(function() {
					/*
					 * The url must be provided as request parameter
					 */
					var url = jQuery.url().param('url');
					/*
					 * The interval is optional - default is 30000 (30 seconds)
					 */
					interval = jQuery.url().param('interval');
					if(null == interval) {
						interval = 30000;
					}
					if(url != null) {
	                    jQuery.get('tail/data?url=' + url, function(data) {
	                        jQuery('#content').append(
	                                data);
	                        });
					}
				}, interval);
			}
	);
</script>
</head>
<body>
 <div id="contentWrapper">
  <pre id="content"></pre>
 </div>
</body>
</html>