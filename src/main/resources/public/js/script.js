/**
 * Created by tahin on 2017.05.03..
 */
$ (document).ready(function () {
    $('div[id^="add"]').click(function(){
        var id = this.id.substring(3,this.id.length);
        $.ajax({url: "/hello/" + id, success: function(result){
            console.log("Product added to cart");
        }});
    });

    $('div[id^="raise"]').click(function(){
        console.log("sfsagh");
        var id = this.id.substring(5,this.id.length);
        console.log(id);
        $.ajax({url: "/addToQuantity/" + id, success: function(result){
            console.log("Amount raised");
        }});
    });
});