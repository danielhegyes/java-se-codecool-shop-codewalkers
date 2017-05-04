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
        var id = this.id.substring(5,this.id.length);
        console.log(id);
        $.ajax({url: "/addToQuantity/" + id, success: function(result){
            console.log(result);
            var json1 = JSON.parse(result);
            console.log(json1);
            var quantity = $("#quantity"+id).text();
            $("#quantity"+id).text(json1.quantity);
            //console.log(json1.quantity);
            var linePrice = $("#linePrice"+id).text();
            $("#linePrice"+id).text(json1.linePrice);
            var total = $("#total").text();
            $("#total").text(json1.total);



        }});
    });

    $('div[id^="sub"]').click(function(){
        var id = this.id.substring(3,this.id.length);
        console.log(id);
        $.ajax({url: "/subFromQuantity/" + id, success: function(result){
            console.log(result);
            var json1 = JSON.parse(result);
            console.log(json1);
            var quantity = $("#quantity"+id).text();
            $("#quantity"+id).text(json1.quantity);
            //console.log(json1.quantity);
            var linePrice = $("#linePrice"+id).text();
            $("#linePrice"+id).text(json1.linePrice);
            var total = $("#total").text();
            $("#total").text(json1.total);

            if (json1.quantity == 0) {
                $("#row"+id).remove();
                // $("#quantity"+id).remove();
                // $("#linePrice"+id).remove();
            }

        }});
    });
});