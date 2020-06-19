if ( CKEDITOR.env.ie && CKEDITOR.env.version < 9 )
	CKEDITOR.tools.enableHtml5Elements( document );

CKEDITOR.config.height = 300;
CKEDITOR.config.width = 'auto';
CKEDITOR.config.extraPlugins = 'image2,uploadimage';
CKEDITOR.config.filebrowserUploadUrl      = '/editorImageUpload';
CKEDITOR.config.filebrowserImageUploadUrl = '/editorImageUpload';
CKEDITOR.config.filebrowserWindowWidth = '640';
CKEDITOR.config.filebrowserWindowHeight= '480';
CKEDITOR.config.uploadUrl = "/editorImageUpload";

var editorInit = ( function(editorId) {
	return function(editorId) {
		CKEDITOR.replace(editorId, {
		});
	};
} )();

