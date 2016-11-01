$(function () {
    var POLLLING_INVERVAL_TIME_IN_MILLIS = 1000;//1s
    (function polling() {
        getCountUp();
        window.setTimeout(polling, POLLLING_INVERVAL_TIME_IN_MILLIS);
    }());
    function getCountUp() {
        $.ajax({
            type: "GET",
            url: CONTEXT_PATH + "api/tasks",
            dataType: "json",
        }).done(function (data) {
            $("tbody tr:not(:first)").remove();

            var updatedList = "";
            data.forEach(function (element, index, array) {
                updatedList += `<tr><td>${element.id}</td><td>${element.amount}</td><td>${element.done}</td></tr>`;
            });

            $("tbody tr:first").after(updatedList);
        }).fail(function (jqXHR, textStatus) {
            $("button").after().remove();
            $("button").after("<p>error occured</p>");
        });
    }

    $("#createBtn").on("click", function () {
        $.ajax({
            type: "POST",
            url: CONTEXT_PATH + "api/tasks",
            dataType: "json"
        }).done(function (data) {
            getCountUp();
        }).fail(function () {
            window.alert("登録に失敗しました");
        });
    });

});
