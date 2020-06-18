if ( CKEDITOR.env.ie && CKEDITOR.env.version < 9 )
	CKEDITOR.tools.enableHtml5Elements( document );

CKEDITOR.config.height = 300;
CKEDITOR.config.width = 'auto';

var editorInit = ( function(editorId) {
	return function(editorId) {
		CKEDITOR.replace(editorId);
	};
} )();

