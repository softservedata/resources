<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${not empty tableSetting.script}">
	<script src="<c:url value='/resource/js/${tableSetting.script}.js'/>"></script>
</c:if>
<div class="container">

	<c:if test="${not empty tableSetting.tableTitle}">

		<div style="text-align: center;">
			<h4>
				<spring:message code="${tableSetting.tableTitle}" />
			</h4>
		</div>
		
		
	<div class="dataTable_wrapper">
		<table id="example"
			class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<c:forEach items="${tableSetting.columns}" var="entry">
						<th><spring:message code="${entry.value.title}" /></th>
					</c:forEach>
				</tr>
			</thead>
			<tfoot style="display: table-header-group">
				<tr>
					<c:forEach items="${tableSetting.columns}" var="entry"
						varStatus="status">
						<c:if test="${entry.value.type eq 'search'}">
							<th>
								<div class="form-group">
									<div class="col-md-12" style="padding:0">
											<input type="hidden" id="searchTypeIndex${entry.key}" name="category" value="like"/>
											<input maxlength="50" id="inputIndex${entry.key}" class="form-control" type="text" placeholder="<spring:message code="${entry.value.title}" />" />
									</div>
								</div>
							</th>
						</c:if>
					</c:forEach>
					<th><input type="submit" id="bth-search"
						class="btn  btn-primary" value="Пошук" /></th>
				</tr>
			</tfoot>
		</table>
	</div>

	</c:if>

</div>
<script>
var oTable;
jQuery(document).ready(function($) {
	
	var createTableStatus = false;
		
// 	var oTable;
// 	window.myDataTable=oTable;
	function createTable() {	    	

		oTable = $('#example').DataTable({
    	
		"searching": false,
// 		"bSort" : false,
		"bSort" : true,
		"bDestroy": true,
		"oLanguage" : {
			"sEmptyTable" : jQuery.i18n.prop('dataTable.sEmptyTable'),
			"sInfo" : jQuery.i18n.prop('dataTable.sInfo'),
			"sInfoEmpty" : jQuery.i18n.prop('dataTable.sInfoEmpty'),
			"sInfoFiltered" : jQuery.i18n.prop('dataTable.sInfoFiltered'),
			"oPaginate" : {
				"sFirst" : jQuery.i18n.prop('dataTable.sFirst'),
				"sLast" : jQuery.i18n.prop('dataTable.sLast'),
				"sNext" : jQuery.i18n.prop('dataTable.sNext'),
				"sPrevious" : jQuery.i18n.prop('dataTable.sPrevious')
			},
			"sLengthMenu" : jQuery.i18n.prop('dataTable.sLengthMenu'),
			"sLoadingRecords" : jQuery.i18n.prop('dataTable.sLoadingRecords'),
			"sProcessing" : jQuery.i18n.prop('dataTable.sProcessing'),
			"sSearch" : jQuery.i18n.prop('dataTable.sSearch'),
			"sZeroRecords" : jQuery.i18n.prop('dataTable.sZeroRecords')
		},
        "serverSide": true,
        "aoColumns" : [        
						<c:forEach items="${tableSetting.columns}" var="entry" varStatus="status">
							<c:if test="${entry.value.type eq 'search'}">
								{
						            "sTitle" : "<spring:message code="${entry.value.title}" />",
						            "mData" : '${entry.key}'
						        },
							</c:if>
						    <c:if test="${entry.value.type eq 'button'}">
						    {
							       	   "sTitle" : "<spring:message code="${entry.value.title}" />",
					                   "mData" : "action",
					                   "bSortable": false,
					                   "defaultContent": '<button  class="btn btn-primary" id="${entry.value.buttonId}">Редагувати</button>'
					                        ,                	   
					            },
							</c:if>
						</c:forEach>
						
                       ],
              "ajax": {
       		        	"url":"${tableSetting.url}",
       		        	"type":"POST",
       		        	dataType: "json",
       		        	contentType: 'application/json; charset=utf-8',
       		        	"data": function ( data ) {
       		        	  console.log("ajax url: "+"${tableSetting.url}");
       		        	  addSearchValue(data);
       	                  return JSON.stringify(data);
       	            	} 	
               }
    });	
	}

    $("#bth-search").click(function(event) {
    	if(createTableStatus==false){
    		createTable();
    		createTableStatus=true;
    	}else{
    		oTable.ajax.reload(null, false);
    	}
	});
   
    function addSearchValue(data) {
    	data.tableName = '${tableSetting.tableName}';
    	console.log("data.tableName = "+data.tableName);
	    for (var i = 0; i < data.columns.length; i++) {
	        column = data.columns[i];
	        column.search.compareSign = $('#searchTypeIndex'+i).val();
	        column.search.value = $('#inputIndex'+i).val();
	        console.log(column.search.key+". comparesign: "+column.search.compareSign+" value: "+column.search.value);
	    }
	}
} );
</script>