$ (document).ready(function () {
    $('div[id^="raise"]').click(function(){
        console.log("sfsagh");
        var id = this.id.substring(5,this.id.length);
        console.log(id);
        $.ajax({url: "/addToQuantity/" + id, success: function(result){
            console.log("Amount raised");
        }});
    });
});
