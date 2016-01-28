jQuery(document).ready(function($) {

$('#owner_search').autocomplete({
    serviceUrl: 'owners',
    paramName: "ownerDesc",
    delimiter: ",",
    minChars: 2,
    autoSelectFirst: true,
    deferRequestBy: 100,
    type: "POST",
    transformResult: function(response) {
    	return {
    		suggestions: $.map($.parseJSON(response), function(item) {
    			return { value: item.lastName + " " + item.firstName + " " + item.middleName, data: item.login };
    			})
          };
    },
    onSelect: function (suggestion) {
        $.ajax({
            url: "getOwnerInfo",
            data: { ownerLogin : suggestion.data},
            dataType: "json",
            type: "GET",
            success: function(data){
            	reasonInclusionControl(data);
            	// for input inquiry
            	$("#owner_login").val(data.login);
            }
        });
    }


    });

function reasonInclusionControl(owner) {
    if( owner.willDocument !== null) {
        $('#will').removeAttr('disabled');        
    }
    if( owner.passport !== null) {
        $('#pass').removeAttr('disabled');        
    } 
    if( owner.otherDocuments !== null) {
        $('#otherDocs').removeAttr('disabled');       
    } 
    
    $(".checkbox input").click(function(){
        $("#reasonInclusion").text('');
        $(".checkbox :checked").each(function(){
            var id =  $(this).attr('id');
             if(id === "pass") {                 
                 var seria = owner.passport.seria + " " + owner.passport.number;
                 var name = owner.firstName + " " +owner.middleName + " " + owner.lastName;
                 var published = owner.passport.published_by_data;
                 var passportInfo = "паспорт громадянина України " 
                     + seria +", виданий на ім’я " + name + " " + published;               
                if(owner.passport.comment !== null) {
                    var comment =owner.passport.comment;
                    passportInfo = passportInfo + "; " + comment;
                }
                $("#reasonInclusion").append(passportInfo + ";\n");
            }
            if(id === "will") {
                var date = owner.willDocument.accessionDate;
                var stringDate = $.datepicker.formatDate('dd.mm.yy', new Date(date));
                var willDocument = "документ волевиявлення від " + stringDate;
                if(owner.willDocument.comment !==  null) {
                    var comment =owner.willDocument.comment;
                    willDocument = willDocument + "; " + comment;
                }
                $("#reasonInclusion").append( willDocument+ ";\n");                
            }
            if(id === "otherDocs") {
                var docs = owner.otherDocuments;
                $("#reasonInclusion").append(docs + ";\n");                
            }
             else if(id === "tytul"){
                 var inputString = "титул власності на природні ресурси України від 02 квітня 2015 року;";
                 $("#reasonInclusion").append( inputString + "\n");
             }
             else if(id === "delivery"){
                 var inputDelivery = "доручення;";
                 $("#reasonInclusion").append(inputDelivery + "\n");                
            }
        });
   });        

}

});
