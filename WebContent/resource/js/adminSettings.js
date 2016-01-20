$(document).ready(function(){
    $("#confirmRegistrationMethod").click(function() {

        var url = "settings"; 

        $.ajax({
               type: "POST",
               url: url,
               data: $("#сhangeReg").serialize(), 
               success: function(data)
               {
            	   bootbox.alert("Метод реєстрації успішно змінено!");
               }
             });

        return false; 
    });
});