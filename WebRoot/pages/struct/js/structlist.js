  function getData() {
  	var path = $('#path').attr("value");
      jQuery("#searchone").empty();
      $.ajax({
          type: "POST",
          url: path + "/struct/getData?data_pid=" + $("#searchtwo").val(),
          async: false,
          dataType: 'json',
          success: function (data) {
              jQuery("<option value=''>请选择</option>").appendTo("#searchone");
              for (var i = 0; i < data.length; i++) {
                  jQuery(
 "<option value='" + data[i].DATA_ID + "'>" + data[i].DATA_NAME + "</option>").appendTo("#searchone");
              }
          }
      });
  }
