<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div class="container-fluid" id="header">
    <div class="col-md-8">
        <h3><spring:message code="label.name.part1"/></h3>
        <h3><spring:message code="label.name.part2"/></h3>
    </div>
    <div class="col-md-4">
        <div class="col-md-7"> Welcome, %username%</div>
        <div class="col-md-5">
            <ul class="languages">
                <li><a href="?lang=ua"><img src="resource/img/lang/ua.png"></a></li>
                <li><a href="?lang=ru"><img src="resource/img/lang/ru.png"></a></li>
                <li><a href="?lang=en"><img src="resource/img/lang/en.png"></a></li>
            </ul>
        </div>
    </div>
</div>