function summerImageUpload(selector, files){
	var formData = new FormData();
	formData.append("files", files[0]);
    $.ajax({
        url: '/imageUpload.ajax',
        processData: false,
        contentType: false,
        data: formData,
        type: 'POST',
        success: function(result){
          	var imgUrl = result;
			var image = $('<img>').attr("src", imgUrl);
			$(selector).summernote("insertNode", image[0]);
        }
	});
}