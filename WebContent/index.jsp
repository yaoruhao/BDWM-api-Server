<html>
<head>
	<title>BDWM-api-Server</title>
</head>
<body>
<h1>Welcome to BDWM-api-Server</h1>

<h2>Celebration! BDWM-api-Server release milestone 1.0</h2>
<ul>
<li>
/hottopics/{operation} -- Hot topics in bdwm index page
<ul>
<li>/hottopics/division -- division hot topics</li>
<li>/hottopics/school -- school hot topics</li>
<li>/hottopics/topten -- top ten hot topics</li>
<li>/hottopics/academic -- academic hot topics</li>
</ul>
</li>

<li>/division -- bdwm division info</li>
<li>/board/{boardId}/skip/{skipId} -- visit board
<ul>
<li>for example /board/EECS/skip/20 -- visit EECS board and skip 20 topics (The top topic is filterd when skip > 0)</li>
</li>
</ul>

</li>
<li>/topic/{topicPreUrl}/{topicSuffixUrl} -- visit topic (Remember to replace "?" by "/" in the topic url.)
<ul>
<li>/topic/bbstcon.php/board=EECS&threadid=13710399 -- visit regular topic:bbstcon.php?board=EECS&threadid=13710399</li>
<li>/topic/bbscon.php/board=EECS&file=Z1336569521A&num=10&attach=1&dig=13 -- visit top topic:bbscon.php?board=EECS&file=Z1336569521A&num=10&attach=1&dig=13</li>
</ul></li>
</ul>
</body>
</html>