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
				${tableSetting.tableTitle}
			</h4>
		</div>
	
	</c:if>

	<div class="dataTable_wrapper">
         <table class="table table-striped table-bordered table-hover" id="example">
			<thead>
				<tr>
					<c:forEach items="${tableSetting.columnsSetting}" var="columnSetting">
						<th>${columnSetting.title}</th>
					</c:forEach>
	           </tr>
			</thead>
	        <tfoot style="display: table-header-group">
	        	<tr>      	
	        		<c:forEach items="${tableSetting.columnsSetting}" var="columnSetting" varStatus="status">
	        			<c:if test="${columnSetting.type eq 'search'}">
	        				<th>
<!-- 		        				<span class="col-xs-2"> -->
<!-- 		        					<select class="form-control"> -->
<%-- 		        						<c:forEach items="${columnSetting.searchType}" var="searchType"> --%>
		        							
<%-- 		        							<c:if test="${searchType eq 'equal'}"> --%>
<%-- 												<option value="${searchType}">==</option> --%>
<%-- 											</c:if> --%>
											
<%-- 											<c:if test="${searchType eq 'like'}"> --%>
<%-- 												<option value="${searchType}">%%</option> --%>
<%-- 											</c:if> --%>
	
<%-- 				                        </c:forEach> --%>
<!-- 	                    			</select> -->
<!-- 	                			</span> -->
	                			<div class="col-md-12">
		        					<input id="inputIndex${status.count-1}" class="form-control"  type="text" placeholder="${columnSetting.title}" />
		        				</div>
	        				</th>
	        			</c:if>
					</c:forEach>		        	
		        	<th><input type="submit" id="bth-search" class="btn  btn-primary" value="Пошук"/></th>
		        </tr>  
	        </tfoot>
         </table>
    </div>
</div>

<script>
jQuery(document).ready(function($) {
	
	var createTableStatus = false;
		
	var oTable;
	function createTable() {	    	

		oTable = $('#example').DataTable({
    	
		"searching": false,
// 		"bSort" : false,
		"bSort" : true,
		"bDestroy": true,
    	"oLanguage": {   
            "sEmptyTable": "В таблиці немає даних",
            "sInfo": "_END_ із _TOTAL_",    
            "sInfoEmpty": "Немає даних для виводу",
            "sInfoFiltered": " - Фільтруються _MAX_ записів",
              "oPaginate": {
                "sFirst"   : "Перша",
                "sLast"    : "Остання",
                "sNext"    : "Наст.",
                "sPrevious": "Попер."
              },
              "sLengthMenu": "_MENU_ елементів на сторінку",
              "sLoadingRecords": "Почекайте будь ласка, йде загрузка...",
              "sProcessing"   : "Почекайте будь ласка, йде загрузка...",
              "sSearch": "Пошук:",
              "sZeroRecords": "Нічого не знайдено"
            },
        "serverSide": true,
//         "aoColumnDefs" : [
        "aoColumns" : [        
						<c:forEach items="${tableSetting.columnsSetting}" var="columnSetting" varStatus="status">
							<c:if test="${columnSetting.type eq 'search'}">
								{
						            "sTitle" : "${columnSetting.title}",
						            "mData" : "${columnSetting.data}"
						        },
							</c:if>
						    <c:if test="${columnSetting.type eq 'button'}">
						    {
							       	   "sTitle" : "${columnSetting.title}",
					                   "mData" : "action",
// 					                   "sClass" : "center",
// 					                   "sClass": "action",
					                   "bSortable": false,
// 					                   	"sWidth": "15%",
					                   	"mRender": function ( data, type, full ) {
					                        return '<a href="edit-registrated-user/?login='+full["login"]+'" class="btn btn-primary"'+
					                        		'role="button">Редагувати</a>';}
					                        ,
// 					                   	"mRender": function(data, type, full) {
// 					                   	    return '<a class="btn btn-info btn-sm" href=#/' + full[1] + '>' + data + '</a>';
// 					                   	  }
// 					                   "sDefaultContent" :   '<button id="action" class="btn btn-primary">Редагувати</button>'
					                	   
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
	    for (var i = 0; i < data.columns.length; i++) {
	        column = data.columns[i];
	        column.search.compareSign = "equal";
	        column.search.value = $('#inputIndex'+i).val();
	    }
	}
} );
</script>